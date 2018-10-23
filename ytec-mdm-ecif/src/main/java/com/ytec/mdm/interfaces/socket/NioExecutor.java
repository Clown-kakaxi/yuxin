/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����NioExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:46:52
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�NioExecutor
 * @��������NIOִ�нӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:46:52   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:46:52
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface NioExecutor extends Runnable{
	/**
	 * ��ʼ������
	 * 
	 * @param session ����session
	 * @param selector ������selector
	 * @param pool �첽�����̳߳ش�С
	 */
	public void init(IoSession session, NioProcessor selector) ;
	
	/**
	 * @��������:rejectedExecution
	 * @��������:�������ƣ��ܾ�
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void rejectedExecution();

}
