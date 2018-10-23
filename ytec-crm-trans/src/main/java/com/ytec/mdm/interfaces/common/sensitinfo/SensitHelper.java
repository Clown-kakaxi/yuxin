/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.sensitinfo
 * @�ļ�����SensitHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-3-25-����1:44:23
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.sensitinfo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ytec.mdm.base.util.StringUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SensitHelper
 * @��������������Ϣ������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-3-25 ����1:44:23   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-3-25 ����1:44:23
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SensitHelper extends AbsSensitiveFilter{
	/**
	 * @��������:filter
	 * @��������:����������Ϣ����
	 * @since 1.0.0
	 */
	private SensitiveFilter filter;
	private static SensitHelper sensitHelper=new SensitHelper();
	/**
	 * @��������:getInstance
	 * @��������:������ȡ
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public static SensitHelper getInstance(){
		return sensitHelper;
	}
	public void init(Map arg) throws Exception{
		String sensitFilterClass=(String)arg.get("sensitFilterClass");
		if(!StringUtil.isEmpty(sensitFilterClass)){
			filter=(SensitiveFilter)Class.forName(sensitFilterClass).newInstance();
			filter.init(arg);
		}
		super.initDbSensitive(arg);
		
	}
	/**
	 * @��������:doInforFilter
	 * @��������:����XML����
	 * @�����뷵��˵��:
	 * 		@param str
	 * 		@param sensitInforSet
	 * 		@return
	 * @�㷨����:
	 */
	public String doInforFilter(String str,Set sensitInforSet){
		if(filter==null){
			return str;
		}else{
			return filter.doInforFilter(str, sensitInforSet);
		}
	}
	
	/**
	 * @��������:isDbSensitiveInfor
	 * @��������:�Ƿ������ݿ��ֶ�������Ϣ
	 * @�����뷵��˵��:
	 * 		@param colum
	 * 		@return
	 * @�㷨����:
	 */
	public boolean isDbSensitiveInfor(String colum){
		return this.sensitInforMap.contains(colum);
	}
	public boolean isXmlSensitiveInfor(String colum){
		if(filter==null){
			return false;
		}else{
			return filter.isSensitiveInfo(colum);
		}
	}

}
