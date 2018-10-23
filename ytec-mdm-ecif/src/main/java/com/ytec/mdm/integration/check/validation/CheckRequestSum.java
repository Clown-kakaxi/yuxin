/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：CheckRequestSum.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:50:47
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.validation;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.interfaces.common.xmlcheck.RequestCheckSum;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CheckRequestSum
 * @类描述：数字签名验证(MD5实现)
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:50:48   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:50:48
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class CheckRequestSum extends AbstractValidationChain {

	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		// 一致性校验
//		if (ecifData.isOpCheckSum()) {
//			if (!RequestCheckSum.CheckRequestSum(ecifData.getReqCheckSum(),
//					ecifData.getReqCheckSumBody())) {
//				log.warn("校验信息一致信息失败");
//				ecifData.setStatus(ErrorCode.ERR_XML_CHECKSUM_ERR);
//				return false;
//			}
//		}
		return true;
	}

}
