/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.facade
 * @�ļ�����ICoveringRule.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:00:44
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ICoveringRule
 * @�����������ǹ���ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:00:44   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:00:44
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface ICoveringRule {
	public Object cover(EcifData ecifData,Object oldObject, Object newObject) throws Exception;
}
