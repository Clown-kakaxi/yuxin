/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����TxBizRuleFun.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-3-28-����3:48:45
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.bs;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxBizRuleFun
 * @��������ҵ�����У��ת��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-3-28 ����3:48:45   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-3-28 ����3:48:45
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxBizRuleFun {
	
	/**
	 * @��������:doFilter
	 * @��������:ҵ�����У��
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@param ruler
	 * 		@param attrName
	 * 		@param value
	 * 		@return
	 * @�㷨����:
	 */
	public static String doFilter(EcifData ecifData, String ruler,String attrName, String value){
		return  TxBizRuleFactory.doFilter(ecifData,ruler, attrName,value, new String[] {});
	}

}
