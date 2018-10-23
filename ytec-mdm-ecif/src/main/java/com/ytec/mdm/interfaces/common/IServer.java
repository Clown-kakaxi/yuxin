/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common
 * @�ļ�����IServer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:35:16
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common;

import java.util.Map;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IServer
 * @������������˽ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:35:04   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:35:04
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IServer {
	/**
	 * @��������:stop
	 * @��������:�����ֹͣ
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void stop() ;
	/**
	 * @��������:start
	 * @��������:���������
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void start();
	/**
	 * @��������:init
	 * @��������:����˳�ʼ��
	 * @�����뷵��˵��:
	 * 		@param arg
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public void init(Map arg) throws Exception;
}
