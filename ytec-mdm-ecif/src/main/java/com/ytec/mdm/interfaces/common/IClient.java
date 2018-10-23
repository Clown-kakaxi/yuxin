/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common
 * @�ļ�����IClient.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:34:12
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common;

import java.util.Map;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IClient
 * @���������ͻ��˽ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:34:12   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:34:12
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IClient {
	/**
	 * @��������:sendMsg
	 * @��������:������
	 * @�����뷵��˵��:
	 * 		@param ClientResponse
	 * 		@return
	 * @�㷨����:
	 */
	public ClientResponse sendMsg(String msg);
	/**
	 * @��������:init
	 * @��������:�ͻ��˳�ʼ��
	 * @�����뷵��˵��:
	 * 		@param arg
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public void init(Map arg)throws Exception;
}
