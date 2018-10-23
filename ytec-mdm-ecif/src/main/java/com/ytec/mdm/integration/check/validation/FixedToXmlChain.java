/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：FixedToXmlChain.java
 * @版本信息：1.0.0
 * @日期：2014-4-17-上午10:57:00
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：FixedToXmlChain
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-17 上午10:57:00   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-17 上午10:57:00
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class FixedToXmlChain extends AbstractValidationChain {
	private Logger log = LoggerFactory.getLogger(FixedToXmlChain.class);
	private FixedRequestCoderHandler coderHandler;
	/**
	 *@构造函数 
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
			log.error("定长报文转XML失败",e);
			ecifData.setStatus(ErrorCode.ERR_FIX_UNKNOWN_ERROR.getCode(),"请求解析错误");
		}
		return false;
	}

}
