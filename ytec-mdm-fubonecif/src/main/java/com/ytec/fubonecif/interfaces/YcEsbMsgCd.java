/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.interfaces
 * @�ļ�����YcEsbMsgCd.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-19-����4:55:20
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.xmlhelper.IResponseXmlFun;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�YcEsbMsgCd
 * @��������YC ESB ��Ӧ���ı��ı�żӹ�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-19 ����4:55:20   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-19 ����4:55:20
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class YcEsbMsgCd implements IResponseXmlFun {
	private Logger log = LoggerFactory.getLogger(YcEsbMsgCd.class);

	
	/**
	 *@���캯�� 
	 */
	public YcEsbMsgCd() {
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.xmlhelper.IResponseXmlFun#getValueByFun(java.lang.Object[])
	 */
	@Override
	public String getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==1){
			String msgCd = arg[0].toString();
			if (msgCd != null && msgCd != "") {
				msgCd = msgCd.trim();
				return msgCd.substring(0, msgCd.length() - 1).concat("1");
			} else {
				log.warn("��Ӧ���ı��ı�żӹ���������ֵΪ��");
				return "";
			}
		}else{
			log.warn("��Ӧ���ı��ı�żӹ���������Ϊ��");
			return "";
		}
	}

}
