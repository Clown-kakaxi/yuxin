/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����TxAuthValidationChain.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:52:38
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.validation;

import java.util.List;
import com.ytec.mdm.base.bo.AuthModel;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxClientAuth;
import com.ytec.mdm.domain.txp.TxServiceAuth;
import com.ytec.mdm.integration.auth.IAuthVerify;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxAuthValidationChain
 * @���������ͻ���Ȩ����֤
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:52:01   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:52:01
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxAuthValidationChain extends AbstractValidationChain {

	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		AuthModel authModel = new AuthModel();
		authModel.setSrcSysCd(ecifData.getOpChnlNo());
		authModel.setTxCode(ecifData.getTxCode());
		authModel.setUserName(ecifData.getOpChnlNo());
		if (!authVerify(ecifData,authModel)) {
			log.warn("��������{},����{}����ͻ���δ��Ȩ", ecifData.getOpChnlNo(),ecifData.getTxCode());
			return false;
		} else {
			return true;
		}
	}
	/**
	 * @��������:authVerify
	 * @��������:����Ȩ����֤
	 * @�����뷵��˵��:
	 * 		@param authModel
	 * 		@return
	 * @�㷨����:
	 */
	private boolean authVerify(EcifData ecifData,AuthModel authModel) {
		IAuthVerify authVerify = (IAuthVerify) SpringContextUtils.getBean("authVerify");
		List<TxClientAuth> txClientAuths = authVerify.clientAuth(authModel);
		if (txClientAuths.size() >0) {
			authModel.setClientAuthId(txClientAuths.get(0).getClientAuthId());
			List<TxServiceAuth> txServiceAuths = authVerify.serviceAuth(authModel);
			if (txServiceAuths.size() >0) {
				if(!"1".equals(txServiceAuths.get(0).getAuthType())){
					ecifData.setStatus(ErrorCode.ERR_CLIENT_WAS_SUSPENDED);
					log.warn("����:{}������ͣ", authModel.getTxCode());
					return false;
				}else{
					return true;
				}
			} else {
				ecifData.setStatus(ErrorCode.ERR_CLIENT_REQUEST_NOT_AUTHORIZED);
				log.warn("����:{}����δ��Ȩ", authModel.getTxCode());
				return false;
			}
		} else {
			ecifData.setStatus(ErrorCode.ERR_CLIENT_NOT_AUTHORIZED);
			log.warn("����:{}�ͻ���δ��Ȩ", authModel.getTxCode());
			return false;
		}
	}

}
