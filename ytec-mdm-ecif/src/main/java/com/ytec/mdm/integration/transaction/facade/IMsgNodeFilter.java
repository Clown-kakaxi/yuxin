/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.facade
 * @�ļ�����IMsgNodeFilter.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:22:54
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.facade;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IMsgNodeFilter
 * @��������������˽ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:22:54   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:22:54
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IMsgNodeFilter {
	/**
	 * @��������:execute
	 * @��������:У��ת��
	 * @�����뷵��˵��:
	 * 		@param expression
	 * 		@param value
	 * 		@param params
	 * 		@return
	 * @�㷨����:
	 */
	public String execute(Object expression, String value,String... params);
}
