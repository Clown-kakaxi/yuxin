/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.facade
 * @�ļ�����IEcifDealEngine.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:21:35
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-crm
 * @�����ƣ�IEcifDealEngine
 * @����������������ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:21:35
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:21:35
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
public interface IEcifDealEngine {
	/**
	 * @��������:execute
	 * @��������:�������洦��
	 * @�����뷵��˵��:
	 * 		@param data
	 * @�㷨����:
	 */
	public void execute(EcifData data);

}
