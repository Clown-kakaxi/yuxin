/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����TxDefValidation.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:53:07
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.validation;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxDefValidation
 * @������������������У��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:53:07   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:53:07
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxDefValidation extends AbstractValidationChain {

	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		if(!TxModelHolder.txDefCheck(ecifData.getTxCode())){
			log.warn("����{}�����ڻ���ͣ��",ecifData.getTxCode());
			ecifData.setStatus(ErrorCode.ERR_CLIENT_REQUEST_NOT_FOUND);
			return false;
		}
		return true;
	}

}
