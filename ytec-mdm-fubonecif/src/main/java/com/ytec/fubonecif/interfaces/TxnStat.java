/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.interfaces
 * @�ļ�����TxnStat.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-19-����11:05:28
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.xmlhelper.IResponseXmlFun;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�TxnStat
 * @��������״̬�����ж�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-19 ����11:05:28   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-19 ����11:05:28
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxnStat implements IResponseXmlFun {
	private Logger log = LoggerFactory.getLogger(TxnStat.class);

	/**
	 *@���캯�� 
	 */
	public TxnStat() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.xmlhelper.IResponseXmlFun#getValueByFun(java.lang.Object[])
	 */
	@Override
	public String getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==1){
			if("true".equals(arg[0].toString())){
				return "SUCCESS";
			}else{
				return "ERROR";
			}
		}else{
			log.warn("״̬���غ�������Ϊ��");
			return "ERROR";
		}
	}

}
