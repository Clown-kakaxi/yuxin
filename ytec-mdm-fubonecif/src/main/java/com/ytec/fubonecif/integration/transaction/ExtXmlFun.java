/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.integration.transaction
 * @�ļ�����ExtXmlFun.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-3-26-����2:21:12
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.integration.transaction;

import org.springframework.stereotype.Component;

import com.ytec.mdm.plugins.xmlhelper.IResponseXmlFun;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�ExtXmlFun
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-3-26 ����2:21:12   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-3-26 ����2:21:12
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Component
public class ExtXmlFun implements IResponseXmlFun {

	/* (non-Javadoc)
	 * @see com.ytec.mdm.plugins.xmlhelper.IResponseXmlFun#getValueByFun(java.lang.Object[])
	 */
	@Override
	public String getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg==null||arg.length==0){
			return "10202";
		}else{
			return arg[0].toString();
		}
	}

}
