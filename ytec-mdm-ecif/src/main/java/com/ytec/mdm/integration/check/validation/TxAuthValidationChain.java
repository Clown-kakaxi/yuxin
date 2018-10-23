/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：TxAuthValidationChain.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:52:38
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxAuthValidationChain
 * @类描述：客户端权限验证
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:52:01   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:52:01
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
			log.warn("请求渠道{},交易{}请求客户端未授权", ecifData.getOpChnlNo(),ecifData.getTxCode());
			return false;
		} else {
			return true;
		}
	}
	/**
	 * @函数名称:authVerify
	 * @函数描述:服务权限认证
	 * @参数与返回说明:
	 * 		@param authModel
	 * 		@return
	 * @算法描述:
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
					log.warn("报文:{}服务被暂停", authModel.getTxCode());
					return false;
				}else{
					return true;
				}
			} else {
				ecifData.setStatus(ErrorCode.ERR_CLIENT_REQUEST_NOT_AUTHORIZED);
				log.warn("报文:{}服务未授权", authModel.getTxCode());
				return false;
			}
		} else {
			ecifData.setStatus(ErrorCode.ERR_CLIENT_NOT_AUTHORIZED);
			log.warn("报文:{}客户端未授权", authModel.getTxCode());
			return false;
		}
	}

}
