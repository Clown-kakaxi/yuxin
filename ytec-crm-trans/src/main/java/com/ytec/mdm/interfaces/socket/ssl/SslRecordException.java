/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ssl
 * @�ļ�����SslRecordException.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-11-����10:52:22
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ssl;

import javax.net.ssl.SSLException;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SslRecordException
 * @����������ȫͨѶ�쳣
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-11 ����10:52:22   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-11 ����10:52:22
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SslRecordException extends SSLException {

	/**
	 * @��������:serialVersionUID
	 * @��������:TODO
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = -3630688855274851694L;

	
	
	/**
	 *@���캯�� 
	 * @param message
	 */
	public SslRecordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 *@���캯�� 
	 * @param cause
	 */
	public SslRecordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 *@���캯�� 
	 * @param message
	 * @param cause
	 */
	public SslRecordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
