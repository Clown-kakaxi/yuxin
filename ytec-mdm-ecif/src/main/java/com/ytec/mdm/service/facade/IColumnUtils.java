/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.facade
 * @�ļ�����IColumnUtils.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:00:20
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IColumnUtils
 * @��������ͨ���ֶδ���ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:00:20   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:00:20
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IColumnUtils {
	/**
	 * ����������Ϣ��ͨ���ֶ�
	 * @param ecifData
	 * @param obj
	 * @return
	 */
	public Object setCreateGeneralColumns(EcifData ecifData, Object obj);
	/**
	 * �����޸���Ϣ��ͨ���ֶ�
	 * @param ecifData
	 * @param obj
	 * @return
	 */
	public Object setUpdateGeneralColumns(EcifData ecifData, Object obj);
	/**
	 *  ��ҵ��ģ��ת�ɶ�Ӧ����ʷģ��
	 * @param oldObj
	 * @param hisOperSys
	 * @param hisOperType
	 * @return
	 */
	public Object toHistoryObj(Object oldObj,String hisOperSys,String hisOperType);
	
	/**
	 * ����ʷģ��ת�ɶ�Ӧ��ҵ��ģ��
	 * @param oldObj
	 * @param objName
	 * @return
	 */
	public  Object backFromHistoryObj(Object oldObj,String objName);

}
