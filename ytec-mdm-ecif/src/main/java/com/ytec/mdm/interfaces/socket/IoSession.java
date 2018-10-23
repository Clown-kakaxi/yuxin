/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����IoSession.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-14-����11:28:33
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.nio.channels.SocketChannel;

import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IoSession
 * @��������nio ����ͨ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-14 ����11:28:33   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-14 ����11:28:33
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class IoSession{
	/**
	 * @��������:timeOut
	 * @��������:��ʱʱ��(ms)
	 * @since 1.0.0
	 */
	private static long timeOut=ServerConfiger.getIntArg("socketTimeOut");
	private SocketChannel client;
	/**
	 * @��������:lastAccessTime
	 * @��������:������ʱ��
	 * @since 1.0.0
	 */
	private long lastAccessTime;
	/**
	 *@���캯�� 
	 * @param client
	 */
	public IoSession(SocketChannel client) {
		this.client = client;
		this.lastAccessTime = System.currentTimeMillis();
	}
	public SocketChannel getClient() {
		return client;
	}
	public void setClient(SocketChannel client) {
		this.client = client;
	}
	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	/**
	 * @��������:isTimeOut
	 * @��������:�Ƿ�ʱ
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public boolean isTimeOut(){
		long currTime = System.currentTimeMillis();
		return currTime-this.lastAccessTime>timeOut? true:false;
	}
	/**
	 * @��������:reSetAccessTime
	 * @��������:����ʱ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void reSetAccessTime(){
		this.lastAccessTime = System.currentTimeMillis();
	}

}
