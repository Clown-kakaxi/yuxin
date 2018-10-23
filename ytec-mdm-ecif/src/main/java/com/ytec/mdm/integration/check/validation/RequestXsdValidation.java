/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����RequestXsdValidation.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:51:41
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�RequestXsdValidation
 * @��������������XSD��֤
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:51:42   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:51:42
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class RequestXsdValidation extends AbstractValidationChain {

	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		String reqBodyXml = XMLUtils.elementToString(ecifData.getBodyNode());
		if (!validate(ecifData,reqBodyXml)) {
			log.warn("XML��ʽУ��ʧ��");
			return false;
		}
		return false;
	}
	
	/**
	 * @��������:validate
	 * @��������:������XSD��֤
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@param bodyXml
	 * 		@return
	 * @�㷨����:
	 */
	private boolean validate(EcifData ecifData,String bodyXml) {
		try {
			String checkInfo = TxModelHolder.getTxCheckInfo(ecifData.getTxCode());
			if (checkInfo == null || checkInfo.isEmpty()) {
				ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),
						"������񲻴��ڻ�δ����");
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
			log.warn("�����Ĳ����Ͻӿ�:{}",e.getLocalizedMessage());
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),"�����Ĳ����Ͻӿ�");
			return false;
		}
		return true;
	}

}
