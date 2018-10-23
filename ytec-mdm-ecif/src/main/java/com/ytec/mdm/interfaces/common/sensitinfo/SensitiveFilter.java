/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.sensitinfo
 * @�ļ�����SensitiveFilter.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-3-25-����11:52:58
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.sensitinfo;

import java.util.Map;
import java.util.Set;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SensitiveFilter
 * @��������������Ϣ���˽ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-3-25 ����11:52:58   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-3-25 ����11:52:58
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface SensitiveFilter {
	
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void init(Map arg)throws Exception;
	/**
	 * @��������:doInforFilter
	 * @��������:
	 * @�����뷵��˵��:
	 * 		@param xmlStr ��Ϣ
	 * 		@param sensitInforSet  ������Ϣ����
	 * 		@return  ���˺����Ϣ
	 * @�㷨����:
	 */
	public String doInforFilter(String str,Set sensitInforSet);
	
	/**
	 * @��������:isSensitiveInfo
	 * @��������:�Ƿ�������Ϣ
	 * @�����뷵��˵��:
	 * 		@param info
	 * 		@return
	 * @�㷨����:
	 */
	public boolean isSensitiveInfo(String info);
}
