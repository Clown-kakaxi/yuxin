/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.facade
 * @�ļ�����IModelFilter.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:22:14
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�IModelFilter
 * @��������д����ģ�͹��˽ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:22:14
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:22:14
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
public interface IModelFilter {
	/**
	 * @��������:execute
	 * @��������:ʵ�����
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@param entity
	 * 		@return
	 * @�㷨����:
	 */
	public boolean execute(EcifData ecifData,Object entity);
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void init(String ruleExpr);

}
