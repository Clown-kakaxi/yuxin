/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.facade
 * @�ļ�����IDispatchFun.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:20:11
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IDispatchFun
 * @��������ԭ�ӹ��ܺ����ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:20:11   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:20:11
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IDispatchFun {
	/**
	 * @��������:execute
	 * @��������:ԭ�ӹ��ܺ���ִ��
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * @�㷨����:
	 */
	public void execute(EcifData ecifData);
}
