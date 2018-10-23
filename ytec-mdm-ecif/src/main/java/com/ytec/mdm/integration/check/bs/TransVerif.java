/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：TransVerif.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:44:46
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.bs;

import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.model.TxModel;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：TransVerif
 * @类描述：业务规则验证转换
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:44:47
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:44:47
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class TransVerif {
	private static Logger log = LoggerFactory.getLogger(TransVerif.class);
	protected static String INT_FORMAT = "#";
	protected static String FLOAT_FORMAT = "#.##";
	protected static String DATE_FORMAT = "yyyy-MM-dd";
	protected static String TIME_FORMAT = "HH:mm:ss";
	protected static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	protected TxModel txModel;// 交易配置模型对象
	protected EcifData ecifData = null;

	/**
	 * @函数名称:processCTDR
	 * @函数描述:处理业务校验、转换、转码，逆向转码
	 * @参数与返回说明:
	 * @param txMsgNodeAttr
	 * @param value
	 * @return
	 * @算法描述:
	 */
	protected String processCTDR(TxMsgNodeAttr txMsgNodeAttr, String value) {
		String attrName = StringUtil.isEmpty(txMsgNodeAttr.getAttrName()) ? txMsgNodeAttr.getAttrCode() : txMsgNodeAttr.getAttrName();
		if (StringUtils.isEmpty(value)) {
			if ("N".equals(txMsgNodeAttr.getNulls())) {
				String msg = String.format("%s(%s)不可为空", attrName, txMsgNodeAttr.getAttrCode());
				ecifData.setStatus(ErrorCode.ERR_RULE_VALUE_IS_NULL.getCode(), msg);
				log.warn(msg);
				return null;
			} else {
				return value;
			}
		} else {
			if (MdmConstants.NODE_TYPE_VARI_STR.equals(txMsgNodeAttr.getDataType())) {
				/** 变长字符串 **/
				/*** 计算字节长度,会牺牲效率 ****/
				int relLength = value.getBytes().length; // value.length();
				if (txMsgNodeAttr.getDataLen() != null && txMsgNodeAttr.getDataLen() > 0 && relLength > txMsgNodeAttr.getDataLen()) {
					ecifData.setStatus(ErrorCode.ERR_RULE_INVALID_LENGTH.getCode(), "%s(%s)长度不可超过%d", attrName, txMsgNodeAttr.getAttrCode(), txMsgNodeAttr.getDataLen());
					log.warn("{}({})长度不可超过{}", attrName, txMsgNodeAttr.getAttrCode(), txMsgNodeAttr.getDataLen());
					return null;
				}
			} else if (MdmConstants.NODE_TYPE_FIX_STR.equals(txMsgNodeAttr.getDataType())) {
				/** 定长长字符串 **/
				int relLength = value.getBytes().length; // value.length();
				if (txMsgNodeAttr.getDataLen() != null && txMsgNodeAttr.getDataLen() > 0 && relLength != txMsgNodeAttr.getDataLen()) {
					ecifData.setStatus(ErrorCode.ERR_RULE_INVALID_LENGTH.getCode(), "%s(%s)应该为固定长度%d", attrName, txMsgNodeAttr.getAttrCode(), txMsgNodeAttr.getDataLen());
					log.warn("{}({})应该为固定长度{}", attrName, txMsgNodeAttr.getAttrCode(), txMsgNodeAttr.getDataLen());
					return null;
				}
			}
		}
		String checkRule = txMsgNodeAttr.getCheckRule();
		if (!StringUtil.isEmpty(checkRule)) {
			String cateId = txMsgNodeAttr.getCateId();
			String[] params = new String[] { ecifData.getOpChnlNo(), cateId };
			value = TxBizRuleFactory.doFilter(ecifData, checkRule, attrName, value, params);
		}
		// else{
		// List<TxMsgNodeAttrCt> txMsgNodeAttrCtList = this.txModel
		// .getTxMsgNodeAttrCtMap().get(txMsgNodeAttr.getAttrId());
		// if (txMsgNodeAttrCtList != null) {
		// for (TxMsgNodeAttrCt txMsgNodeAttrCt : txMsgNodeAttrCtList) {
		// String cateId = txMsgNodeAttr.getCateId();
		// String[] params = new String[] { ecifData.getOpChnlNo(), cateId };
		// value = TxBizRuleFactory.doFilter(ecifData,txMsgNodeAttrCt.getCtRule(), attrName,value, params);
		// if(!ecifData.isSuccess()){
		// return null;
		// }
		// }
		// }
		// }
		return value;
	}

	/**
	 * @函数名称:convertObjectToString
	 * @函数描述:将对象数据类型转换为字符串形式
	 * @参数与返回说明:
	 * @param nodeCode
	 * @param txMsgNodeAttr
	 * @param value
	 * @return
	 * @算法描述:
	 */
	protected String convertObjectToString(Long nodeCode, TxMsgNodeAttr txMsgNodeAttr, Object value) {

		String attrName = StringUtil.isEmpty(txMsgNodeAttr.getAttrName()) ? txMsgNodeAttr.getAttrCode() : txMsgNodeAttr.getAttrName();
		String newValue = null;
		String dataType = txMsgNodeAttr.getDataType();
		String dataFmt = txMsgNodeAttr.getDataFmt();
		if (MdmConstants.NODE_TYPE_FIX_STR.equals(dataType) || MdmConstants.NODE_TYPE_VARI_STR.equals(dataType)) {// 字符型
			newValue = value.toString();
		} else if (MdmConstants.NODE_TYPE_CLOB.equals(dataType)) {
			if (value instanceof Clob) {
				newValue = StringUtil.readClob((Clob) value);
			} else {
				log.warn("字段{}配置数据类型不对", attrName);
				ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), "字段%s配置数据类型不对", attrName);
				return null;
			}
		} else {// 非字符串
			DecimalFormat decimalFormat = null;
			SimpleDateFormat dateformat = null;
			if (MdmConstants.NODE_TYPE_INT.equals(dataType)) {// 整型
				newValue = value.toString();
			} else if (MdmConstants.NODE_TYPE_FLOAT.equals(dataType)) {// 浮点型
				if (StringUtil.isEmpty(dataFmt)) {
					decimalFormat = new DecimalFormat(FLOAT_FORMAT);
				} else {
					decimalFormat = new DecimalFormat(dataFmt);
				}
			} else if (MdmConstants.NODE_TYPE_DATE.equals(dataType)) {// 日期
				if (StringUtil.isEmpty(dataFmt)) {
					dateformat = new SimpleDateFormat(DATE_FORMAT);
				} else {
					dateformat = new SimpleDateFormat(dataFmt);
				}
			} else if (MdmConstants.NODE_TYPE_TIME.equals(dataType)) {// 时间
				if (StringUtil.isEmpty(dataFmt)) {
					dateformat = new SimpleDateFormat(TIME_FORMAT);
				} else {
					dateformat = new SimpleDateFormat(dataFmt);
				}
			} else if (MdmConstants.NODE_TYPE_TIMESTAMP.equals(dataType)) {// 时间戳
				if (StringUtil.isEmpty(dataFmt)) {
					dateformat = new SimpleDateFormat(TIMESTAMP_FORMAT);
				} else {
					dateformat = new SimpleDateFormat(dataFmt);
				}
			} else {
				log.warn("字段{}配置数据类型不支持", attrName);
				ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), "字段%s配置数据类型不支持", attrName);
				return null;
			}
			try {
				if (decimalFormat != null) {
					newValue = decimalFormat.format(value);
				} else if (dateformat != null) {
					newValue = dateformat.format(value);
				}
			} catch (Exception e) {
				String msg = "返回报文节点:" + nodeCode + "的属性:" + attrName + " 格式化失败";
				log.error(msg, e);
				ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
				return null;
			}
		}
		return newValue;
	}

	protected boolean initTxModel() {
		try {
			this.txModel = TxModelHolder.getTxModel(ecifData.getTxCode());
		} catch (Exception e) {
			log.error("获取交易配置对象失败", e);
			ecifData.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), "获取配置信息失败");
		}
		return ecifData.isSuccess();
	}

}
