/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ext
 * @�ļ�����ExtIoSession.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-24-����1:24:53
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

import java.nio.channels.SocketChannel;

import com.ytec.mdm.interfaces.socket.IoSession;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ExtIoSession
 * @��������socket ��չSession
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-24 ����1:24:53   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-24 ����1:24:53
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ExtIoSession extends IoSession {
	/**
	 * @��������:serverAcceptPort
	 * @��������:����˽���˿�
	 * @since 1.0.0
	 */
	private int serverAcceptPort;
	/**
	 * @��������:clazz
	 * @��������:Э�����������
	 * @since 1.0.0
	 */
	private Class clazz;

	/**
	 *@���캯�� 
	 * @param client
	 */
	public ExtIoSession(SocketChannel client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	/**
	 *@���캯�� 
	 * @param client
	 * @param serverAcceptPort
	 */
	public ExtIoSession(SocketChannel client, int serverAcceptPort) {
		super(client);
		this.serverAcceptPort=serverAcceptPort;
		// TODO Auto-generated constructor stub
	}

	public int getServerAcceptPort() {
		return serverAcceptPort;
	}

	public void setServerAcceptPort(int serverAcceptPort) {
		this.serverAcceptPort = serverAcceptPort;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
	

}
