/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����TxReqBodyValidation.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-15-����10:56:41
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.validation;

import org.dom4j.Element;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.model.TxModel;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxReqBodyValidation
 * @����������������ǿ���֤������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-15 ����10:56:41   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-15 ����10:56:41
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxReqBodyValidation extends AbstractValidationChain {

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.check.validation.AbstractValidationChain#reqMsgValidation(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		if(ecifData.getPrimalDoc()==null){
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),"���Ķ���Ϊ��");
			log.warn("����{}���Ķ���Ϊ��",ecifData.getTxCode());
			return false;
		}
		/** �������� */
		if (ecifData.getBodyNode() == null) {
			TxModel txModel=null;
			if((txModel=TxModelHolder.getTxModel(ecifData.getTxCode()))!=null){
				Element body=(Element)ecifData.getPrimalDoc().selectSingleNode("//"+txModel.getReqTxMsg().getMainMsgRoot());
				if(body!=null){
					ecifData.setBodyNode(body);
					return true;
				}
			}
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),"������Ϊ��");
			log.warn("����{}������Ϊ��",ecifData.getTxCode());
			return false;
		}
		return true;
	}

}
