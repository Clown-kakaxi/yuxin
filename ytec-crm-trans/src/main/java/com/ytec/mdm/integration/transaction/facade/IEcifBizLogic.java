/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.facade
 * @�ļ�����IEcifBizLogic.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:21:01
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;
public interface IEcifBizLogic {
	/**
	 * @��������:process
	 * @��������:����ҵ���߼�����
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public void process(EcifData data)  throws Exception ;

}
