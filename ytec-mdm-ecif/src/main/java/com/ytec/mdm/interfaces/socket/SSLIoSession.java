/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����SSLIoSession.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-3-����2:13:57
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.nio.channels.SocketChannel;

import com.ytec.mdm.interfaces.socket.ssl.SslHandler;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SSLIoSession
 * @��������SSL/TLS����Session
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-3 ����2:13:57   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-3 ����2:13:57
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SSLIoSession extends IoSession{
	/**
	 * @��������:sslHandler
	 * @��������:SSL/TLS����
	 * @since 1.0.0
	 */
	private SslHandler sslHandler;
	public SSLIoSession(SocketChannel client) {
		super(client);
	}
	public SslHandler getSslHandler() {
		return sslHandler;
	}
	public void setSslHandler(SslHandler sslHandler) {
		this.sslHandler = sslHandler;
	}
}
