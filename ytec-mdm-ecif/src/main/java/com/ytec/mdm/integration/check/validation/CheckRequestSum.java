/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����CheckRequestSum.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:50:47
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.validation;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.interfaces.common.xmlcheck.RequestCheckSum;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CheckRequestSum
 * @������������ǩ����֤(MD5ʵ��)
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:50:48   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:50:48
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CheckRequestSum extends AbstractValidationChain {

	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		// һ����У��
//		if (ecifData.isOpCheckSum()) {
//			if (!RequestCheckSum.CheckRequestSum(ecifData.getReqCheckSum(),
//					ecifData.getReqCheckSumBody())) {
//				log.warn("У����Ϣһ����Ϣʧ��");
//				ecifData.setStatus(ErrorCode.ERR_XML_CHECKSUM_ERR);
//				return false;
//			}
//		}
		return true;
	}

}
