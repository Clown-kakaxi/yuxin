/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：TxReqBodyValidation.java
 * @版本信息：1.0.0
 * @日期：2014-4-15-上午10:56:41
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.validation;

import org.dom4j.Element;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.model.TxModel;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxReqBodyValidation
 * @类描述：请求报文体非空验证与设置
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-15 上午10:56:41   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-15 上午10:56:41
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),"报文对象为空");
			log.warn("交易{}报文对象为空",ecifData.getTxCode());
			return false;
		}
		/** 请求报文体 */
		if (ecifData.getBodyNode() == null) {
			TxModel txModel=null;
			if((txModel=TxModelHolder.getTxModel(ecifData.getTxCode()))!=null){
				Element body=(Element)ecifData.getPrimalDoc().selectSingleNode("//"+txModel.getReqTxMsg().getMainMsgRoot());
				if(body!=null){
					ecifData.setBodyNode(body);
					return true;
				}
			}
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),"报文体为空");
			log.warn("交易{}报文体为空",ecifData.getTxCode());
			return false;
		}
		return true;
	}

}
