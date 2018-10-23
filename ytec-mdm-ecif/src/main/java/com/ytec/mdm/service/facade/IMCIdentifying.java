/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.facade
 * @�ļ�����IMCIdentifying.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:01:03
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.facade;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IMCIdentifying
 * @���������ͻ��Ź���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:01:15   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:01:15
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IMCIdentifying {
	
	/**
	 * �ͻ��Ź���
	 * @param String custType �ͻ�����
	 * @param String ecif�ͻ���
	 * **/
	public String getEcifCustNo(String custType) throws Exception;
	/**
	 * �����������ɹ���
	 * @param String arrtName, ��������, ��Ӧ���ݱ�Ϊ���������ֶ� (����),�����淶�����ݱ��ֶ���ͬ
	 * @return String AttrId
	 * */
	public String getPriIdByAttrName(String attrName) throws Exception;
	
	/**
	 * @��������:getEcifCustId
	 * @��������:�ͻ���ʶ���ɹ���
	 * @�����뷵��˵��:
	 * 		@param custType �ͻ�����
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public String getEcifCustId(String custType) throws Exception;

}
