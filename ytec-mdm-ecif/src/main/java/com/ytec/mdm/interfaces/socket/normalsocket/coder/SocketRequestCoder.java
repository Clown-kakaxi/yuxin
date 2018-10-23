/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @�ļ�����FixedRequestCoder.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-15-����4:37:22
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�SocketRequestCoder
 * @��������socket���Ľ��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-15 ����4:37:22
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-15 ����4:37:22
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 */
public abstract class SocketRequestCoder extends SocketMsgCoder {
	protected static Logger log = LoggerFactory.getLogger(SocketRequestCoder.class);

	public enum State {
		ALL_READ, READ_INITIAL, READ_HEADER, READ_FIXED_LENGTH_CONTENT
	}

	/**
	 * @��������:state
	 * @��������:����״̬
	 * @since 1.0.0
	 */
	private State state = State.READ_INITIAL;
	/**
	 * @��������:readRemaining
	 * @��������:������ʣ���ֽ���
	 * @since 1.0.0
	 */
	private int readRemaining = 0;
	/**
	 * @��������:readCount
	 * @��������:�������Ѷ��ֽ���
	 * @since 1.0.0
	 */
	private int readCount = 0;
	/**
	 * @��������:headBuffer
	 * @��������:����ͷ����
	 * @since 1.0.0
	 */
	protected byte[] headBuffer;

	/**
	 * @��������:maxBody
	 * @��������:����������ֽ���
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
			case ALL_READ:// �������
				return true;
			case READ_INITIAL:
				if (headLength <= buffer.remaining()) {
					headBuffer = new byte[headLength];// ��ȡ����ͷ
					buffer.get(headBuffer, 0, headLength);
					state = State.READ_HEADER;
				} else {
					return false;
				}
				break;
			case READ_HEADER:// ��������ͷ
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
			case READ_FIXED_LENGTH_CONTENT:// ��ȡ������
				readFixedLength(buffer);
				// if (readRemaining == 0) {
				// �޸�Bug�������Ѷ��굫״̬δ�л�
				log.info("@@@readRemaining:"+readRemaining+",buffer.hasRemaining():"+buffer.hasRemaining());
//				if (readRemaining == 0 || !buffer.hasRemaining()) {
				if (readRemaining == 0 ) {			//ȥ��!buffer.hasRemaining()
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
	 * @��������:readFixedLength
	 * @��������:��ȡ������
	 * @�����뷵��˵��:
	 * @param buffer
	 * @�㷨����:
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
	 * @��������:throwIfBodyIsTooLarge
	 * @��������:�жϱ����峬��
	 * @�����뷵��˵��:
	 * @throws Exception
	 * @�㷨����:
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
	 * @��������:reset
	 * @��������:����
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void reset() {
		state = State.READ_INITIAL;
		headers = new TreeMap<String, String>();
		readCount = 0;
		content = null;
		headBuffer = null;
	}

	/**
	 * @��������:getBodyString
	 * @��������:��ȡ�������ַ���
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
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
	 * @��������:readHeaders
	 * @��������:��������ͷ����������ӱ���ͷ�н�������峤��
	 * @�����뷵��˵��:
	 * @throws Exception
	 * @�㷨����:
	 */
	protected abstract void readHeaders() throws Exception;

}
