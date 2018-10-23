/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����AbstractEcifDealEngine.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-6-11-13:55:45
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.core;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeFilter;
import com.ytec.mdm.integration.transaction.bs.TransVerif;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.integration.transaction.facade.IEcifDealEngine;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;
import com.ytec.mdm.plugins.xmlhelper.IResponseXmlFun;
/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�AbstractEcifDealEngine
 * @���������������������
 * @��������:�������������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:05:23
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:05:23
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
public abstract class AbstractEcifDealEngine extends TransVerif implements
		IEcifDealEngine {

	private static Logger log = LoggerFactory
			.getLogger(AbstractEcifDealEngine.class);
	/**
	 * @��������:bizLogic
	 * @��������:���״����߼�
	 * @since 1.0.0
	 */
	protected IEcifBizLogic bizLogic;
	/**
	 * @��������:convertStringToObject
	 * @��������:�ַ���ת��Ϊ����
	 * @�����뷵��˵��:
	 * 		@param typeClass ���Ͷ���
	 * 		@param value     ֵ
	 * 		@param dataFmt   ��ʽ�����ʽ
	 * 		@return
	 * 		@throws ParseException
	 * @�㷨����:
	 */
	protected Object convertStringToObject(Class typeClass, String value,
			String dataFmt) throws ParseException {
		Object newValue = null;
		SimpleDateFormat dateFormat = null;
		if (java.lang.String.class.equals(typeClass)) {// �ַ���
			newValue = value;
		} else if (java.lang.Long.class.equals(typeClass)
				|| long.class.equals(typeClass)) {// ����
			newValue = new Long(value);
		} else if (java.lang.Double.class.equals(typeClass)
				|| double.class.equals(typeClass)) {// ������
			newValue = new Double(value);
		} else if (java.util.Date.class.equals(typeClass)) {// ����
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(DATE_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			newValue = StringUtil.reverse2Date(value, dateFormat);
			if (newValue == null) {
				log.warn("{}:���ڸ�ʽ����", value);
				this.ecifData.setStatus(
						ErrorCode.ERR_RULE_INVALID_DATE_FORMAT.getCode(),
						"%s:���ڸ�ʽ����", value);
				return null;
			}
		} else if (Integer.class.equals(typeClass)
				|| int.class.equals(typeClass)) {
			newValue = new Integer(value);
		} else if (Time.class.equals(typeClass)) {// ʱ��
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(TIME_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			Date dd = StringUtil.reverse2Date(value, dateFormat);
			if (dd == null) {
				log.warn("{}:ʱ���ʽ����", value);
				this.ecifData.setStatus(
						ErrorCode.ERR_RULE_INVALID_TIME_FORMAT.getCode(),
						"%s:ʱ���ʽ����", value);
				return null;
			}
			newValue = new Time(dd.getTime());
		} else if (Timestamp.class.equals(typeClass)) {// ʱ���
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			Date dd = StringUtil.reverse2Date(value, dateFormat);
			if (dd == null) {
				log.warn("{}:ʱ�����ʽ����", value);
				this.ecifData.setStatus(
						ErrorCode.ERR_RULE_INVALID_TIMESTAMP_FORMAT.getCode(),
						"%s:ʱ�����ʽ����", value);
				return null;
			}
			newValue = new Timestamp(dd.getTime());
		} else if (byte.class.equals(typeClass) || Byte.class.equals(typeClass)) {// �����ƴ��ֶ�
			newValue = value.getBytes();
		} else if (BigDecimal.class.equals(typeClass)) { // BigDecimal
			newValue = BigDecimal.valueOf(Double.valueOf(value));
		} else if (BigInteger.class.equals(typeClass)) {// BigInteger
			newValue = BigInteger.valueOf(Long.valueOf(value));
		} else if (Boolean.class.equals(typeClass)
				|| boolean.class.equals(typeClass)) {// Boolean
			newValue = Boolean.valueOf(value);
		} else if (Short.class.equals(typeClass)
				|| short.class.equals(typeClass)) {// Short
			newValue = Short.valueOf(value);
		} else if (Float.class.equals(typeClass)
				|| float.class.equals(typeClass)) {// Float
			newValue = Float.valueOf(value);
		} else if (Character.class.equals(typeClass)
				|| char.class.equals(typeClass)) {// Character
			newValue = Character.valueOf(value.charAt(0));
		} else {
			String msg = typeClass.getName() + "������ϵͳ��֧��!";
			this.ecifData.setStatus(
					ErrorCode.ERR_XML_CFG_DATAFMT_NOT_FOUND.getCode(), msg);
			return null;
		}
		return newValue;
	}

	/**
	 * @��������:convertStringToObject
	 * @��������:���ַ�����������ת��Ϊʵ�����õ���������
	 * @�����뷵��˵��:
	 * @param nodeCode
	 * @param txMsgNodeFilter
	 * @param value
	 * @return
	 * @throws ParseException
	 * @�㷨����:
	 */
	protected Object convertStringToObject(String nodeCode,
			TxMsgNodeFilter txMsgNodeFilter, String value)
			throws ParseException {

		String attrCode = txMsgNodeFilter.getReqAttrCode();
		Object newValue = null;
		String dataType = txMsgNodeFilter.getReqAttrDataType();
		String dataFmt = txMsgNodeFilter.getReqAttrDataFmt();
		SimpleDateFormat dateFormat = null;
		if (MdmConstants.NODE_TYPE_FIX_STR.equals(dataType)
				|| MdmConstants.NODE_TYPE_VARI_STR.equals(dataType)) {// �ַ���
			if("like".equals(txMsgNodeFilter.getRel())){
				/***
				 * ��ѯ��ϵΪlike������ģ��ƥ���"%"
				 */
				newValue="%"+value+"%";
			}else{
				newValue = value;
			}
		} else if (MdmConstants.NODE_TYPE_INT.equals(dataType)) {// ����
			newValue = new Long(value);
		} else if (MdmConstants.NODE_TYPE_FLOAT.equals(dataType)) {// ������
			newValue = new Double(value);
		} else if (MdmConstants.NODE_TYPE_DATE.equals(dataType)) {// ����
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(DATE_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			newValue = dateFormat.parse(value);
		} else if (MdmConstants.NODE_TYPE_TIME.equals(dataType)) {// ʱ��
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(TIME_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			newValue = new Time(dateFormat.parse(value).getTime());
		} else if (MdmConstants.NODE_TYPE_TIMESTAMP.equals(dataType)) {// ʱ���
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			newValue = new Timestamp(dateFormat.parse(value).getTime());
		} else {
			log.error("���ر��Ľڵ�:{}������:{} ������ϵͳ��֧��!",nodeCode,attrCode);
			this.ecifData.setStatus(
					ErrorCode.ERR_XML_CFG_DATATYPE_NOT_SUPPORT.getCode(), "�������Ͳ�֧��");
			return null;
		}
		return newValue;
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ytec.mdm.integration.transaction.facade.IEcifDealEngine#execute(com
	 * .ytec.mdm.base.bo.EcifData)
	 */
	public abstract void execute(EcifData data);

	/**
	 * Gets the tx model.
	 *
	 * @return the tx model
	 */
	public TxModel4CRM getTxModel() {
		return txModel;
	}

	public void setTxModel(TxModel4CRM txModel) {
		this.txModel = txModel;
	}

	public IEcifBizLogic getBizLogic() {
		return bizLogic;
	}

	public void setBizLogic(IEcifBizLogic bizLogic) {
		this.bizLogic = bizLogic;
	}
	/**
	 * @��������:parseDefaultVal
	 * @��������:������Ĭ��ֵ����
	 * @�����뷵��˵��:
	 * 		@param parseRule  Ĭ��ֵ����
	 * 		@param context    ��ǰ���
	 * 		@return
	 * @�㷨����:
	 */
	protected String parseDefaultVal(String parseRule, Element context) {
		char firstChar;
		String parseText;
		firstChar = parseRule.charAt(0);
		if ('#' == firstChar) {
			/** ��һ����ǩ������ ***/
			parseText = parseRule.substring(2, parseRule.length() - 1);
			if (!StringUtil.isEmpty(parseText)) {
				Object opTextObj = getValueFromXmlObject(parseText,context);
				if (opTextObj != null) {
					if (opTextObj instanceof String) {
						return opTextObj.toString();
					} else {
						log.warn("��������������Ĭ��ֵ{}�������,ֵת����Ϊ����", parseRule);
						this.ecifData.setStatus(
								ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
								"�ӿ����ô���");
						return null;
					}
				} else {
					return null;
				}
			} else {
				log.error("��������������Ĭ��ֵ{}�������", parseRule);
				this.ecifData
						.setStatus(
								ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
								"�ӿ����ô���");
				return null;
			}
		} else if ('$' == firstChar) {
			/** �����ӹ� */
			parseText = parseRule.substring(2, parseRule.length() - 1);
			if (!StringUtil.isEmpty(parseText)) {
				String funName;
				Object[] arg = null;
				if (parseText.endsWith(")")) {// ������
					int f = parseText.indexOf('(');
					if (f < 0) {
						log.error("��������������Ĭ��ֵ{}�������", parseRule);
						this.ecifData.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),"�ӿ����ô���");
						return null;
					} else {
						funName = parseText.substring(0, f);
						String args = parseText.substring(f + 1,
								parseText.length() - 1);
						if (!StringUtil.isEmpty(args)) {
							String[] argtr = args.split("\\,");
							arg = new Object[argtr.length];
							for (int i = 0; i < argtr.length; i++) {
								if (argtr[i].toString().startsWith("#")) {
									/** �����ĵ�ֵ */
									argtr[i] = argtr[i].toString().substring(2,
											argtr[i].toString().length() - 1);
									arg[i] = getValueFromXmlObject(argtr[i]
											.toString(),context);
								} else {
									if ("this".equals(argtr[i])) {
										arg[i] = context;
									} else {
										arg[i] = argtr[i];
									}
								}
							}
						}
					}
				} else {// �޲���
					funName = parseText;
				}
				IResponseXmlFun ff = (IResponseXmlFun) SpringContextUtils
						.getBean(funName);
				if (ff != null) {
					Object opTextObj = ff.getValueByFun(arg);
					if (opTextObj instanceof String) {
						return opTextObj.toString();
					} else {
						log.error("��������������Ĭ��ֵ{}���庯�����ز�֧��", parseRule);
						this.ecifData.setStatus(
								ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
								"�ӿ����ô���");
						return null;
					}
				} else {
					log.error("��������������Ĭ��ֵ{}���庯��������", parseRule);
					this.ecifData.setStatus(
							ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
							"�ӿ����ô���");
					return null;
				}
			} else {
				log.error("��������������Ĭ��ֵ{}�������", parseRule);
				this.ecifData
						.setStatus(
								ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
								"�ӿ����ô���");
				return null;
			}
		} else {
			/** ��ͨ���� **/
			return parseRule;
		}
	}
	/**
	 * @��������:getValueFromXmlObject
	 * @��������:��XML�����л�ȡ����
	 * @�����뷵��˵��:
	 * 		@param parseText_   XPATH
	 * 		@param context      ��ǰ���
	 * 		@return
	 * @�㷨����:
	 */
	private Object getValueFromXmlObject(String parseText_,Element context) {
		Element point = null;
		if(parseText_.startsWith("this:")){
			parseText_ = parseText_.substring(5);
			return context.elementText(parseText_);
		}else if (parseText_.startsWith("bodyObj:")) {// ���ݶ���
			parseText_ = parseText_.substring(8);
			point = ecifData.getBodyNode();
			return point.selectNodes(parseText_);
		} else if (parseText_.startsWith("head:")) {// ������ͷ
			parseText_ = parseText_.substring(5);
			point = ecifData.getRequestHeader();
		} else if (parseText_.startsWith("body:")) {// ��������
			parseText_ = parseText_.substring(5);
			point = ecifData.getBodyNode();
		} else {
			log.warn("�ӱ����л�ȡ����{}������", parseText_);
			return "";
		}
		Element reqEle = (Element) point.selectSingleNode(parseText_);
		if (reqEle != null) {
			return reqEle.getText();
		} else {
			log.warn("�ӱ����л�ȡ����{}������", parseText_);
			return "";
		}
	}
}
