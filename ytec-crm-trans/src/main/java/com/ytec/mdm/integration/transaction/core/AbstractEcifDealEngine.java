/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：AbstractEcifDealEngine.java
 * @版本信息：1.0.0
 * @日期：2014-6-11-13:55:45
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：AbstractEcifDealEngine
 * @类描述：交易引擎抽象类
 * @功能描述:交易引擎抽象类
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:05:23
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:05:23
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
public abstract class AbstractEcifDealEngine extends TransVerif implements
		IEcifDealEngine {

	private static Logger log = LoggerFactory
			.getLogger(AbstractEcifDealEngine.class);
	/**
	 * @属性名称:bizLogic
	 * @属性描述:交易处理逻辑
	 * @since 1.0.0
	 */
	protected IEcifBizLogic bizLogic;
	/**
	 * @函数名称:convertStringToObject
	 * @函数描述:字符串转化为对象
	 * @参数与返回说明:
	 * 		@param typeClass 类型对象
	 * 		@param value     值
	 * 		@param dataFmt   格式化表达式
	 * 		@return
	 * 		@throws ParseException
	 * @算法描述:
	 */
	protected Object convertStringToObject(Class typeClass, String value,
			String dataFmt) throws ParseException {
		Object newValue = null;
		SimpleDateFormat dateFormat = null;
		if (java.lang.String.class.equals(typeClass)) {// 字符型
			newValue = value;
		} else if (java.lang.Long.class.equals(typeClass)
				|| long.class.equals(typeClass)) {// 整型
			newValue = new Long(value);
		} else if (java.lang.Double.class.equals(typeClass)
				|| double.class.equals(typeClass)) {// 浮点型
			newValue = new Double(value);
		} else if (java.util.Date.class.equals(typeClass)) {// 日期
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(DATE_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			newValue = StringUtil.reverse2Date(value, dateFormat);
			if (newValue == null) {
				log.warn("{}:日期格式不对", value);
				this.ecifData.setStatus(
						ErrorCode.ERR_RULE_INVALID_DATE_FORMAT.getCode(),
						"%s:日期格式不对", value);
				return null;
			}
		} else if (Integer.class.equals(typeClass)
				|| int.class.equals(typeClass)) {
			newValue = new Integer(value);
		} else if (Time.class.equals(typeClass)) {// 时间
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(TIME_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			Date dd = StringUtil.reverse2Date(value, dateFormat);
			if (dd == null) {
				log.warn("{}:时间格式不对", value);
				this.ecifData.setStatus(
						ErrorCode.ERR_RULE_INVALID_TIME_FORMAT.getCode(),
						"%s:时间格式不对", value);
				return null;
			}
			newValue = new Time(dd.getTime());
		} else if (Timestamp.class.equals(typeClass)) {// 时间戳
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			Date dd = StringUtil.reverse2Date(value, dateFormat);
			if (dd == null) {
				log.warn("{}:时间戳格式不对", value);
				this.ecifData.setStatus(
						ErrorCode.ERR_RULE_INVALID_TIMESTAMP_FORMAT.getCode(),
						"%s:时间戳格式不对", value);
				return null;
			}
			newValue = new Timestamp(dd.getTime());
		} else if (byte.class.equals(typeClass) || Byte.class.equals(typeClass)) {// 二进制大字段
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
			String msg = typeClass.getName() + "的类型系统不支持!";
			this.ecifData.setStatus(
					ErrorCode.ERR_XML_CFG_DATAFMT_NOT_FOUND.getCode(), msg);
			return null;
		}
		return newValue;
	}

	/**
	 * @函数名称:convertStringToObject
	 * @函数描述:将字符串对象类型转换为实际配置的数据类型
	 * @参数与返回说明:
	 * @param nodeCode
	 * @param txMsgNodeFilter
	 * @param value
	 * @return
	 * @throws ParseException
	 * @算法描述:
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
				|| MdmConstants.NODE_TYPE_VARI_STR.equals(dataType)) {// 字符型
			if("like".equals(txMsgNodeFilter.getRel())){
				/***
				 * 查询关系为like，加入模糊匹配的"%"
				 */
				newValue="%"+value+"%";
			}else{
				newValue = value;
			}
		} else if (MdmConstants.NODE_TYPE_INT.equals(dataType)) {// 整型
			newValue = new Long(value);
		} else if (MdmConstants.NODE_TYPE_FLOAT.equals(dataType)) {// 浮点型
			newValue = new Double(value);
		} else if (MdmConstants.NODE_TYPE_DATE.equals(dataType)) {// 日期
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(DATE_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			newValue = dateFormat.parse(value);
		} else if (MdmConstants.NODE_TYPE_TIME.equals(dataType)) {// 时间
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(TIME_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			newValue = new Time(dateFormat.parse(value).getTime());
		} else if (MdmConstants.NODE_TYPE_TIMESTAMP.equals(dataType)) {// 时间戳
			if (StringUtil.isEmpty(dataFmt)) {
				dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
			} else {
				dateFormat = new SimpleDateFormat(dataFmt);
			}
			newValue = new Timestamp(dateFormat.parse(value).getTime());
		} else {
			log.error("返回报文节点:{}的属性:{} 的类型系统不支持!",nodeCode,attrCode);
			this.ecifData.setStatus(
					ErrorCode.ERR_XML_CFG_DATATYPE_NOT_SUPPORT.getCode(), "数据类型不支持");
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
	 * @函数名称:parseDefaultVal
	 * @函数描述:配置中默认值解析
	 * @参数与返回说明:
	 * 		@param parseRule  默认值数据
	 * 		@param context    当前结点
	 * 		@return
	 * @算法描述:
	 */
	protected String parseDefaultVal(String parseRule, Element context) {
		char firstChar;
		String parseText;
		firstChar = parseRule.charAt(0);
		if ('#' == firstChar) {
			/** 另一个标签的数据 ***/
			parseText = parseRule.substring(2, parseRule.length() - 1);
			if (!StringUtil.isEmpty(parseText)) {
				Object opTextObj = getValueFromXmlObject(parseText,context);
				if (opTextObj != null) {
					if (opTextObj instanceof String) {
						return opTextObj.toString();
					} else {
						log.warn("请求报文配置属性默认值{}定义错误,值转换后为对象", parseRule);
						this.ecifData.setStatus(
								ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
								"接口配置错误");
						return null;
					}
				} else {
					return null;
				}
			} else {
				log.error("请求报文配置属性默认值{}定义错误", parseRule);
				this.ecifData
						.setStatus(
								ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
								"接口配置错误");
				return null;
			}
		} else if ('$' == firstChar) {
			/** 函数加工 */
			parseText = parseRule.substring(2, parseRule.length() - 1);
			if (!StringUtil.isEmpty(parseText)) {
				String funName;
				Object[] arg = null;
				if (parseText.endsWith(")")) {// 带参数
					int f = parseText.indexOf('(');
					if (f < 0) {
						log.error("请求报文配置属性默认值{}定义错误", parseRule);
						this.ecifData.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),"接口配置错误");
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
									/** 请求报文的值 */
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
				} else {// 无参数
					funName = parseText;
				}
				IResponseXmlFun ff = (IResponseXmlFun) SpringContextUtils
						.getBean(funName);
				if (ff != null) {
					Object opTextObj = ff.getValueByFun(arg);
					if (opTextObj instanceof String) {
						return opTextObj.toString();
					} else {
						log.error("请求报文配置属性默认值{}定义函数返回不支持", parseRule);
						this.ecifData.setStatus(
								ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
								"接口配置错误");
						return null;
					}
				} else {
					log.error("请求报文配置属性默认值{}定义函数不存在", parseRule);
					this.ecifData.setStatus(
							ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
							"接口配置错误");
					return null;
				}
			} else {
				log.error("请求报文配置属性默认值{}定义错误", parseRule);
				this.ecifData
						.setStatus(
								ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),
								"接口配置错误");
				return null;
			}
		} else {
			/** 普通数据 **/
			return parseRule;
		}
	}
	/**
	 * @函数名称:getValueFromXmlObject
	 * @函数描述:重XML对象中获取数据
	 * @参数与返回说明:
	 * 		@param parseText_   XPATH
	 * 		@param context      当前结点
	 * 		@return
	 * @算法描述:
	 */
	private Object getValueFromXmlObject(String parseText_,Element context) {
		Element point = null;
		if(parseText_.startsWith("this:")){
			parseText_ = parseText_.substring(5);
			return context.elementText(parseText_);
		}else if (parseText_.startsWith("bodyObj:")) {// 数据对象
			parseText_ = parseText_.substring(8);
			point = ecifData.getBodyNode();
			return point.selectNodes(parseText_);
		} else if (parseText_.startsWith("head:")) {// 请求报文头
			parseText_ = parseText_.substring(5);
			point = ecifData.getRequestHeader();
		} else if (parseText_.startsWith("body:")) {// 请求报文体
			parseText_ = parseText_.substring(5);
			point = ecifData.getBodyNode();
		} else {
			log.warn("从报文中获取不到{}的属性", parseText_);
			return "";
		}
		Element reqEle = (Element) point.selectSingleNode(parseText_);
		if (reqEle != null) {
			return reqEle.getText();
		} else {
			log.warn("从报文中获取不到{}的属性", parseText_);
			return "";
		}
	}
}
