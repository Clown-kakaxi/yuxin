/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����FixedToXmlChain.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-17-����10:57:00
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.interfaces.socket.normalsocket.coder.FixedRequestCoderHandler;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�FixedToXmlChain
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-17 ����10:57:00   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-17 ����10:57:00
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class FixedToXmlChain extends AbstractValidationChain {
	private Logger log = LoggerFactory.getLogger(FixedToXmlChain.class);
	private FixedRequestCoderHandler coderHandler;
	/**
	 *@���캯�� 
	 */
	public FixedToXmlChain() {
		// TODO Auto-generated constructor stub
		coderHandler=(FixedRequestCoderHandler)SpringContextUtils.getBean("fixedRequestXmlHandler");
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.check.validation.AbstractValidationChain#reqMsgValidation(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		if(ecifData.getPrimalDoc()!=null){
			return true;
		}
		try{
			coderHandler.requestFixedStringToXml(ecifData);
			return ecifData.isSuccess();
		}catch(Exception e){
			log.error("��������תXMLʧ��",e);
			ecifData.setStatus(ErrorCode.ERR_FIX_UNKNOWN_ERROR.getCode(),"�����������");
		}
		return false;
	}

}
