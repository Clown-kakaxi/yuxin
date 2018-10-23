/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：TxDefValidation.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:53:07
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.validation;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxDefValidation
 * @类描述：请求服务存在校验
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:53:07   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:53:07
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxDefValidation extends AbstractValidationChain {

	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		if(!TxModelHolder.txDefCheck(ecifData.getTxCode())){
			log.warn("交易{}不存在或已停用",ecifData.getTxCode());
			ecifData.setStatus(ErrorCode.ERR_CLIENT_REQUEST_NOT_FOUND);
			return false;
		}
		return true;
	}

}
