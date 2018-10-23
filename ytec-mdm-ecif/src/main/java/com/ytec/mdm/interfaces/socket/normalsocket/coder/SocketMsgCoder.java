/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @�ļ�����SocketMsgCoder.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-15-����4:21:10
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.coder;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.TreeMap;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SocketMsgCoder
 * @��������socket���Ľ���������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-15 ����4:21:10   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-15 ����4:21:10
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class SocketMsgCoder {
	/**
	 * @��������:headLength
	 * @��������:ͷ������
	 * @since 1.0.0
	 */
	protected int headLength;
	/**
	 * @��������:contentLength
	 * @��������:�峤��
	 * @since 1.0.0
	 */
	protected int contentLength = 0;
	/**
	 * @��������:content
	 * @��������:������
	 * @since 1.0.0
	 */
	protected byte[] content;
	protected Map<String, String> headers = new TreeMap<String, String>();
	/**
	 * @��������:charSet
	 * @��������:�����ַ���
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
	 * @��������:decode
	 * @��������:����
	 * @�����뷵��˵��:
	 * 		@param buffer
	 * @�㷨����:
	 */
	public abstract boolean decode(ByteBuffer buffer) throws Exception;
	
	/**
	 * @��������:encode
	 * @��������:����
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public abstract String encode(String body) throws Exception;

}
