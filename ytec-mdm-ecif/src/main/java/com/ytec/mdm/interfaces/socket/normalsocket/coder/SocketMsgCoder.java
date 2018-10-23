/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @文件名：SocketMsgCoder.java
 * @版本信息：1.0.0
 * @日期：2014-4-15-下午4:21:10
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.coder;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.TreeMap;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SocketMsgCoder
 * @类描述：socket报文解析抽象类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-15 下午4:21:10   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-15 下午4:21:10
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class SocketMsgCoder {
	/**
	 * @属性名称:headLength
	 * @属性描述:头部长度
	 * @since 1.0.0
	 */
	protected int headLength;
	/**
	 * @属性名称:contentLength
	 * @属性描述:体长度
	 * @since 1.0.0
	 */
	protected int contentLength = 0;
	/**
	 * @属性名称:content
	 * @属性描述:报文体
	 * @since 1.0.0
	 */
	protected byte[] content;
	protected Map<String, String> headers = new TreeMap<String, String>();
	/**
	 * @属性名称:charSet
	 * @属性描述:编码字符集
	 * @since 1.0.0
	 */
	protected String charSet;
	
	
	
	public SocketMsgCoder() {
	}

	public SocketMsgCoder(int headLength) {
		this.headLength = headLength;
	}

	public int getHeadLength() {
		return headLength;
	}

	public void setHeadLength(int headLength) {
		this.headLength = headLength;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	
	public String getheaderValue(String key){
		return headers.get(key);
	}
	
	

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	/**
	 * @函数名称:decode
	 * @函数描述:解码
	 * @参数与返回说明:
	 * 		@param buffer
	 * @算法描述:
	 */
	public abstract boolean decode(ByteBuffer buffer) throws Exception;
	
	/**
	 * @函数名称:encode
	 * @函数描述:编码
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public abstract String encode(String body) throws Exception;

}
