/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.adapter.message
 * @文件名：ReqMsgValidation.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:37:06
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.integration.adapter.message;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.integration.check.bs.TransVerif;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：ReqMsgValidation
 * @类描述：请求报文业务校验转换
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:37:26
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:37:26
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class ReqMsgValidation extends TransVerif {

	private static Logger log = LoggerFactory.getLogger(ReqMsgValidation.class);
	/**
	 * @属性名称:excludeDates
	 * @属性描述:默认日期排除
	 * @since 1.0.0
	 */
	public static String[] excludeDates = BusinessCfg.getStringArray("ExcludeDate");

	/**
	 * @函数名称:validateAndConvertReqMsg
	 * @函数描述:对请求报文的内容进行校验、转换
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	public boolean validateAndConvertReqMsg() {
		try {
			// 将请求报文节点以 父节点为关键字 存在在Map中，方便构造报文树
			List<TxMsgNode> tsMsgNodeList = this.txModel.getReqTxMsgNodeList();
			Map<Long, List<TxMsgNode>> txMsgNodeMap = new HashMap<Long, List<TxMsgNode>>();
			List<TxMsgNode> txMsgNodeList = null;
			// 根节点
			TxMsgNode rootTxMsgNode = null;
			for (TxMsgNode txMsgNode : tsMsgNodeList) {
				if (txMsgNode.getUpNodeId() == -1) {
					rootTxMsgNode = txMsgNode;
				}
				txMsgNodeList = txMsgNodeMap.get(txMsgNode.getUpNodeId());
				if (txMsgNodeList == null) {
					txMsgNodeList = new ArrayList<TxMsgNode>();
					txMsgNodeMap.put(txMsgNode.getUpNodeId(), txMsgNodeList);
				}
				txMsgNodeList.add(txMsgNode);
			}
			// 开始进行校验、转码
			// 校验内容： 数字、日期格式是否合法
			validateOneNode(ecifData.getBodyNode(), "", rootTxMsgNode, txMsgNodeMap);
		} catch (Exception e) {
			log.error("校验、转码错误", e);
			ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR);
			return false;
		}
		return ecifData.isSuccess();
	}

	/**
	 * @函数名称:validateOneNode
	 * @函数描述:验证节点属性内容格式是否合法
	 * @参数与返回说明:
	 * @param context
	 * @param xpath
	 * @param currentxMsgNode
	 * @param txMsgNodeMap
	 * @算法描述:
	 */
	// TODO 添加不符合值规范的“节点”描述
	@SuppressWarnings("unchecked")
	private void validateOneNode(Element context, String xpath, TxMsgNode currentxMsgNode,
			Map<Long, List<TxMsgNode>> txMsgNodeMap) {
		Long nodeId = currentxMsgNode.getNodeId();
		String nodeCode = currentxMsgNode.getNodeCode();
		int nodeNum;
		int attrNum;
		// 节点属性
		List<TxMsgNodeAttr> txMsgNodeAttrList = this.txModel.getTxMsgNodeAttrMap().get(nodeId);
		String dataType = null;
		String dataFmt = null;
		String attrCode = null;
		DecimalFormat decimalFormat = null;
		SimpleDateFormat dateFormat = null;
		String value = null;
		boolean isFormat = false;
		if (!"".equals(xpath) && !"0".equals(currentxMsgNode.getNodeDisplay())) {// ------------------------------
			// 判定结点数量
			List ll = context.selectNodes(xpath);
			nodeNum = ll == null ? 0 : ll.size();
			if (nodeNum > 1 && !MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup())
					&& "1".equals(this.txModel.getTxDef().getTxCheckXsd()) && MdmConstants.XSD_CHECK_NULL_ATTR) {
				ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID);
				log.warn("{}:{}不是结点组", ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(), nodeCode);
				return;
			}
			xpath += "/";
		} else {
			nodeNum = 1;
		}
		if (nodeNum == 0 && !MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup())
				&& "1".equals(this.txModel.getTxDef().getTxCheckXsd()) && MdmConstants.XSD_CHECK_NULL_ATTR) {
			String msg = String.format("%s(%s)不存在", ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(), nodeCode);
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), msg);
			log.warn(msg);
			return;
		}
		for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrList) {
			dataType = txMsgNodeAttr.getDataType();
			dataFmt = txMsgNodeAttr.getDataFmt();
			attrCode = txMsgNodeAttr.getAttrCode();
			decimalFormat = null;
			dateFormat = null;
			// 对字段进行内部校验转换（此为系统自动转换，偏技术处理）
			if (MdmConstants.NODE_TYPE_VARI_STR.equals(dataType) || MdmConstants.NODE_TYPE_FIX_STR.equals(dataType)) {
				isFormat = false;// 字符型的不需要格式化,进入验证转换过程
			} else {
				if (MdmConstants.NODE_TYPE_INT.equals(dataType)) {// 整型
					if (StringUtil.isEmpty(dataFmt)) {
						decimalFormat = new DecimalFormat(INT_FORMAT);
					} else {
						decimalFormat = new DecimalFormat(dataFmt);
					}
					isFormat = true;
				} else if (MdmConstants.NODE_TYPE_FLOAT.equals(dataType)) {// 浮点型
					if (StringUtil.isEmpty(dataFmt)) {
						decimalFormat = new DecimalFormat(FLOAT_FORMAT);
					} else {
						decimalFormat = new DecimalFormat(dataFmt);
					}
					isFormat = true;
				} else if (MdmConstants.NODE_TYPE_DATE.equals(dataType)) {// 日期
					if (StringUtil.isEmpty(dataFmt)) {
						dateFormat = new SimpleDateFormat(DATE_FORMAT);
					} else {
						dateFormat = new SimpleDateFormat(dataFmt);
					}
					isFormat = true;
				} else if (MdmConstants.NODE_TYPE_TIME.equals(dataType)) {// 时间
					if (StringUtil.isEmpty(dataFmt)) {
						dateFormat = new SimpleDateFormat(TIME_FORMAT);
					} else {
						dateFormat = new SimpleDateFormat(dataFmt);
					}
					isFormat = true;
				} else if (MdmConstants.NODE_TYPE_TIMESTAMP.equals(dataType)) {// 时间戳
					if (StringUtil.isEmpty(dataFmt)) {
						dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
					} else {
						dateFormat = new SimpleDateFormat(dataFmt);
					}
					isFormat = true;
				} else {
					String msg = String.format("报文节点:%s 属性:%s 的数据类型:%s 系统不支持", nodeCode, attrCode, dataType);
					ecifData.setStatus(ErrorCode.ERR_XML_CFG_DATATYPE_NOT_SUPPORT.getCode(), "数据类型不支持");
					log.warn(msg);
					return;
				}
			}
			List<Element> elementList;
			elementList = (List<Element>) context.selectNodes(xpath + attrCode);
			attrNum = elementList == null ? 0 : elementList.size();

			// 开户交易XSD校验后，针对节点默认值非空容错严格限定,XSD_CHECK_NULL_ATTR为true则严格校验,报文中节点不可缺少
			if (attrNum != nodeNum && "1".equals(this.txModel.getTxDef().getTxCheckXsd())
					&& MdmConstants.XSD_CHECK_NULL_ATTR) {
				if (StringUtil.isEmpty(txMsgNodeAttr.getDefaultVal())) {
					String msg = String.format("%s(%s),请求报文中无该节点", ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(),
							attrCode);
					ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), msg);
					log.warn(msg);
				}
			}
			if (elementList != null) {
				for (Element element : elementList) {
					value = element.getTextTrim();
					/** 去前后空白 ***/
					value = StringUtil.StringTrim(value);

					if (isFormat) {
						/** 数值日期型格式化 ***/
						value = formatValue(element.getName(), decimalFormat, dateFormat, value);
					}
					if (!ecifData.isSuccess()) { return; }
					/** 校验 ***/
					value = processCTDR(txMsgNodeAttr, value);
					if (!ecifData.isSuccess()) { return; }
					element.setText(value);
				}
			}
		}
		// 当前节点下的所有子节点
		List<TxMsgNode> txMsgNodeList = txMsgNodeMap.get(currentxMsgNode.getNodeId());
		if (txMsgNodeList != null) {
			for (TxMsgNode txMsgNode : txMsgNodeList) {
				String newXpath = xpath;
				if (MdmConstants.NODE_GROUP_M.equals(txMsgNode.getNodeGroup())) {
					if ("0".equals(txMsgNode.getNodeDisplay())) {
						ecifData.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), "请求报文配置错误");
						log.warn("{}:{}节点组,不可配置为影式标签", ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(),
								txMsgNode.getNodeCode());
						return;
					}
					if (MdmConstants.NODE_GROUP_NO_LABEL.equals(txMsgNode.getNodeLabel())) {
						newXpath += txMsgNode.getNodeCode();
					} else {
						List ll = context.selectNodes(xpath + txMsgNode.getNodeCode() + MdmConstants.NODE_GROUP_SUFFIX);
						nodeNum = ll == null ? 0 : ll.size();

						if (nodeNum == 0 && "1".equals(this.txModel.getTxDef().getTxCheckXsd())
								&& MdmConstants.XSD_CHECK_NULL_ATTR) {
							String msg = String.format("%s,请求报文中节点%s(%s%s)不存在,该节点为节点组",
									ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(), txMsgNode.getNodeName(), attrCode,
									MdmConstants.NODE_GROUP_SUFFIX);
							ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), msg);
							log.warn(msg);
							return;
						}
						newXpath += txMsgNode.getNodeCode() + MdmConstants.NODE_GROUP_SUFFIX + "/"
								+ txMsgNode.getNodeCode();
					}
				} else {// 非节点组或者没有父节点//------------------------------
					if (!"0".equals(txMsgNode.getNodeDisplay())) {
						newXpath += txMsgNode.getNodeCode();
					}
				}
				validateOneNode(context, newXpath, txMsgNode, txMsgNodeMap);
				if (!ecifData.isSuccess()) { return; }
			}
		}

	}

	/**
	 * @函数名称:formatValue
	 * @函数描述:格式化内容
	 * @参数与返回说明:
	 * @param decimalFormat
	 * @param dateFormat
	 * @param value
	 * @return
	 * @算法描述:
	 */
	private String formatValue(String attrName, DecimalFormat decimalFormat, SimpleDateFormat dateFormat, String value) {
		if (!StringUtils.isEmpty(value)) {
			if (decimalFormat != null) {
				try {
					/** 整型或小数，都按有小数来解析，再格式化 */
					value = decimalFormat.format(Double.parseDouble(value));
				} catch (Exception e) {
					ecifData.setStatus(ErrorCode.ERR_RULE_INVALID_LONG.getCode(), "%s(%s)数值类型格式不正确", attrName, value);
					log.warn("{}({}):数值类型格式不正确", attrName, value);
					return null;
				}
			} else if (dateFormat != null) {
				/****
				 * 时间:格式化，排除几个默认值，前台处理可能会传来 0000-00-00,0000-01-01的默认值，对他们来说，这就是空
				 */
				if (excludeDates != null) {
					for (String excludeDate : excludeDates) {
						if (value.equals(excludeDate)) {
							log.info("过滤掉日期时间{}({})", attrName, value);
							return null;
						}
					}
				}
				// ///////////////////////////////////////////////////////////////////////
				dateFormat.setLenient(false);
				Date dateval = StringUtil.reverse2Date(value, dateFormat);
				if (dateval != null) {
					value = dateFormat.format(dateval);
				} else {
					ecifData.setStatus(ErrorCode.ERR_RULE_INVALID_DATE_FORMAT.getCode(), "%s(%s):日期时间格式不正确", attrName,
							value);
					log.warn("{}({}):日期时间格式不正确", attrName, value);
					return null;
				}
			}
		}
		return value;
	}

}
