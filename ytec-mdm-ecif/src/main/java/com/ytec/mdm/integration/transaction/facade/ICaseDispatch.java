/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.facade
 * @�ļ�����ICaseDispatch.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:19:32
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ICaseDispatch
 * @����������֧�б�ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:19:32   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:19:32
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface ICaseDispatch {
	/**
	 * @��������:decide
	 * @��������:��֧�б�
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@return
	 * @�㷨����:
	 */
	public String decide(EcifData ecifData);
}
