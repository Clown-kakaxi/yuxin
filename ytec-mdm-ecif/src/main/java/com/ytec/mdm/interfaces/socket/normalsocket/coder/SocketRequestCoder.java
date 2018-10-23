/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @文件名：FixedRequestCoder.java
 * @版本信息：1.0.0
 * @日期：2014-4-15-下午4:37:22
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.coder;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.TreeMap;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.interfaces.socket.http.tools.RequestTooLargeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：SocketRequestCoder
 * @类描述：socket报文解码与编码
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-15 下午4:37:22
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-15 下午4:37:22
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 */
public abstract class SocketRequestCoder extends SocketMsgCoder {
	protected static Logger log = LoggerFactory.getLogger(SocketRequestCoder.class);

	public enum State {
		ALL_READ, READ_INITIAL, READ_HEADER, READ_FIXED_LENGTH_CONTENT
	}

	/**
	 * @属性名称:state
	 * @属性描述:解析状态
	 * @since 1.0.0
	 */
	private State state = State.READ_INITIAL;
	/**
	 * @属性名称:readRemaining
	 * @属性描述:报文体剩余字节数
	 * @since 1.0.0
	 */
	private int readRemaining = 0;
	/**
	 * @属性名称:readCount
	 * @属性描述:报文体已读字节数
	 * @since 1.0.0
	 */
	private int readCount = 0;
	/**
	 * @属性名称:headBuffer
	 * @属性描述:报文头数据
	 * @since 1.0.0
	 */
	protected byte[] headBuffer;

	/**
	 * @属性名称:maxBody
	 * @属性描述:报文体最大字节数
	 * @since 1.0.0
	 */
	private final int maxBody;

	public SocketRequestCoder(int maxBody) {
		this.maxBody = maxBody;
	}

	public SocketRequestCoder(int headLength, int maxBody) {
		super(headLength);
		this.maxBody = maxBody;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.ytec.mdm.interfaces.socket.fixedlength.FixedMsgCoder#decode(java.
	 * nio.ByteBuffer)
	 */
	@Override
	public boolean decode(ByteBuffer buffer) throws Exception {
		// TODO Auto-generated method stub
		while (buffer.hasRemaining()) {
			switch (state) {
			case ALL_READ:// 解析完毕
				return true;
			case READ_INITIAL:
				if (headLength <= buffer.remaining()) {
					headBuffer = new byte[headLength];// 读取报文头
					buffer.get(headBuffer, 0, headLength);
					state = State.READ_HEADER;
				} else {
					return false;
				}
				break;
			case READ_HEADER:// 解析报文头
				readHeaders();
				if (contentLength > 0) {
					throwIfBodyIsTooLarge();
					content = new byte[contentLength];
					readRemaining = contentLength;
					state = State.READ_FIXED_LENGTH_CONTENT;
				} else {
					state = State.ALL_READ;
				}
				break;
			case READ_FIXED_LENGTH_CONTENT:// 读取报文体
				readFixedLength(buffer);
				// if (readRemaining == 0) {
				// 修改Bug，报文已读完但状态未切换
				log.info("@@@readRemaining:"+readRemaining+",buffer.hasRemaining():"+buffer.hasRemaining());
//				if (readRemaining == 0 || !buffer.hasRemaining()) {
				if (readRemaining == 0 ) {			//去掉!buffer.hasRemaining()
					finish();
				}
				break;
			}
		}
		return state == State.ALL_READ ? true : false;
	}

	private void finish() {
		state = State.ALL_READ;
	}

	/**
	 * @函数名称:readFixedLength
	 * @函数描述:读取报文体
	 * @参数与返回说明:
	 * @param buffer
	 * @算法描述:
	 */
	private void readFixedLength(ByteBuffer buffer) {
		if (headLength == 4) {
			buffer.position(4);
		}
		log.info("@@@buffer.remaining():"+buffer.remaining()+",readRemaining:"+readRemaining);
		int toRead = Math.min(buffer.remaining(), readRemaining);
		log.info("@@@readCount:"+readCount+",toRead:"+toRead);
		buffer.get(content, readCount, toRead);
		readRemaining -= toRead;
		readCount += toRead;
	}

	/**
	 * @函数名称:throwIfBodyIsTooLarge
	 * @函数描述:判断报文体超长
	 * @参数与返回说明:
	 * @throws Exception
	 * @算法描述:
	 */
	private void throwIfBodyIsTooLarge() throws Exception {
		if (readCount + readRemaining > maxBody) { throw new RequestTooLargeException("request body "
				+ (readCount + readRemaining) + "; max request body " + maxBody); }
	}

	public boolean isFinish() {
		if (charSet == null) {
			charSet = MdmConstants.TX_XML_ENCODING;
		}
		return state == State.ALL_READ;
	}

	/**
	 * @函数名称:reset
	 * @函数描述:重置
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void reset() {
		state = State.READ_INITIAL;
		headers = new TreeMap<String, String>();
		readCount = 0;
		content = null;
		headBuffer = null;
	}

	/**
	 * @函数名称:getBodyString
	 * @函数描述:获取报文体字符串
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	public String getBodyString() {
		if (content != null) {
			try {
				String result = new String(content, charSet);
				return result;// new String(content,charSet);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				return new String(content);
			}
		}
		return null;
	}

	/**
	 * @函数名称:readHeaders
	 * @函数描述:定长报文头解析，必须从报文头中解出报文体长度
	 * @参数与返回说明:
	 * @throws Exception
	 * @算法描述:
	 */
	protected abstract void readHeaders() throws Exception;

}
