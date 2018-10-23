/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：RequestXsdValidation.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:51:41
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.validation;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：RequestXsdValidation
 * @类描述：请求报文XSD验证
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:51:42   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:51:42
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class RequestXsdValidation extends AbstractValidationChain {

	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		String reqBodyXml = XMLUtils.elementToString(ecifData.getBodyNode());
		if (!validate(ecifData,reqBodyXml)) {
			log.warn("XML格式校验失败");
			return false;
		}
		return false;
	}
	
	/**
	 * @函数名称:validate
	 * @函数描述:请求报文XSD验证
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@param bodyXml
	 * 		@return
	 * @算法描述:
	 */
	private boolean validate(EcifData ecifData,String bodyXml) {
		try {
			String checkInfo = TxModelHolder.getTxCheckInfo(ecifData.getTxCode());
			if (checkInfo == null || checkInfo.isEmpty()) {
				ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),
						"请求服务不存在或未启用");
				return false;
			}
			if("0".equals(checkInfo)){
				return true;
			}
			SchemaFactory factory = SchemaFactory
					.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema;
			schema = factory.newSchema(new StreamSource(new StringReader(
					checkInfo)));
			Validator validator = schema.newValidator();
			Source source = new StreamSource(new StringReader(bodyXml));
			validator.validate(source);
		} catch (Exception e) {
			log.warn("请求报文不符合接口:{}",e.getLocalizedMessage());
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),"请求报文不符合接口");
			return false;
		}
		return true;
	}

}
