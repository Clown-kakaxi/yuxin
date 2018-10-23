/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.adapter.message
 * @�ļ�����ReqMsgValidation.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:37:06
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�ReqMsgValidation
 * @��������������ҵ��У��ת��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:37:26
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:37:26
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class ReqMsgValidation extends TransVerif {

	private static Logger log = LoggerFactory.getLogger(ReqMsgValidation.class);
	/**
	 * @��������:excludeDates
	 * @��������:Ĭ�������ų�
	 * @since 1.0.0
	 */
	public static String[] excludeDates = BusinessCfg.getStringArray("ExcludeDate");

	/**
	 * @��������:validateAndConvertReqMsg
	 * @��������:�������ĵ����ݽ���У�顢ת��
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	public boolean validateAndConvertReqMsg() {
		try {
			// �������Ľڵ��� ���ڵ�Ϊ�ؼ��� ������Map�У����㹹�챨����
			List<TxMsgNode> tsMsgNodeList = this.txModel.getReqTxMsgNodeList();
			Map<Long, List<TxMsgNode>> txMsgNodeMap = new HashMap<Long, List<TxMsgNode>>();
			List<TxMsgNode> txMsgNodeList = null;
			// ���ڵ�
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
			// ��ʼ����У�顢ת��
			// У�����ݣ� ���֡����ڸ�ʽ�Ƿ�Ϸ�
			validateOneNode(ecifData.getBodyNode(), "", rootTxMsgNode, txMsgNodeMap);
		} catch (Exception e) {
			log.error("У�顢ת�����", e);
			ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR);
			return false;
		}
		return ecifData.isSuccess();
	}

	/**
	 * @��������:validateOneNode
	 * @��������:��֤�ڵ��������ݸ�ʽ�Ƿ�Ϸ�
	 * @�����뷵��˵��:
	 * @param context
	 * @param xpath
	 * @param currentxMsgNode
	 * @param txMsgNodeMap
	 * @�㷨����:
	 */
	// TODO ��Ӳ�����ֵ�淶�ġ��ڵ㡱����
	@SuppressWarnings("unchecked")
	private void validateOneNode(Element context, String xpath, TxMsgNode currentxMsgNode,
			Map<Long, List<TxMsgNode>> txMsgNodeMap) {
		Long nodeId = currentxMsgNode.getNodeId();
		String nodeCode = currentxMsgNode.getNodeCode();
		int nodeNum;
		int attrNum;
		// �ڵ�����
		List<TxMsgNodeAttr> txMsgNodeAttrList = this.txModel.getTxMsgNodeAttrMap().get(nodeId);
		String dataType = null;
		String dataFmt = null;
		String attrCode = null;
		DecimalFormat decimalFormat = null;
		SimpleDateFormat dateFormat = null;
		String value = null;
		boolean isFormat = false;
		if (!"".equals(xpath) && !"0".equals(currentxMsgNode.getNodeDisplay())) {// ------------------------------
			// �ж��������
			List ll = context.selectNodes(xpath);
			nodeNum = ll == null ? 0 : ll.size();
			if (nodeNum > 1 && !MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup())
					&& "1".equals(this.txModel.getTxDef().getTxCheckXsd()) && MdmConstants.XSD_CHECK_NULL_ATTR) {
				ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID);
				log.warn("{}:{}���ǽ����", ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(), nodeCode);
				return;
			}
			xpath += "/";
		} else {
			nodeNum = 1;
		}
		if (nodeNum == 0 && !MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup())
				&& "1".equals(this.txModel.getTxDef().getTxCheckXsd()) && MdmConstants.XSD_CHECK_NULL_ATTR) {
			String msg = String.format("%s(%s)������", ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(), nodeCode);
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
			// ���ֶν����ڲ�У��ת������Ϊϵͳ�Զ�ת����ƫ��������
			if (MdmConstants.NODE_TYPE_VARI_STR.equals(dataType) || MdmConstants.NODE_TYPE_FIX_STR.equals(dataType)) {
				isFormat = false;// �ַ��͵Ĳ���Ҫ��ʽ��,������֤ת������
			} else {
				if (MdmConstants.NODE_TYPE_INT.equals(dataType)) {// ����
					if (StringUtil.isEmpty(dataFmt)) {
						decimalFormat = new DecimalFormat(INT_FORMAT);
					} else {
						decimalFormat = new DecimalFormat(dataFmt);
					}
					isFormat = true;
				} else if (MdmConstants.NODE_TYPE_FLOAT.equals(dataType)) {// ������
					if (StringUtil.isEmpty(dataFmt)) {
						decimalFormat = new DecimalFormat(FLOAT_FORMAT);
					} else {
						decimalFormat = new DecimalFormat(dataFmt);
					}
					isFormat = true;
				} else if (MdmConstants.NODE_TYPE_DATE.equals(dataType)) {// ����
					if (StringUtil.isEmpty(dataFmt)) {
						dateFormat = new SimpleDateFormat(DATE_FORMAT);
					} else {
						dateFormat = new SimpleDateFormat(dataFmt);
					}
					isFormat = true;
				} else if (MdmConstants.NODE_TYPE_TIME.equals(dataType)) {// ʱ��
					if (StringUtil.isEmpty(dataFmt)) {
						dateFormat = new SimpleDateFormat(TIME_FORMAT);
					} else {
						dateFormat = new SimpleDateFormat(dataFmt);
					}
					isFormat = true;
				} else if (MdmConstants.NODE_TYPE_TIMESTAMP.equals(dataType)) {// ʱ���
					if (StringUtil.isEmpty(dataFmt)) {
						dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
					} else {
						dateFormat = new SimpleDateFormat(dataFmt);
					}
					isFormat = true;
				} else {
					String msg = String.format("���Ľڵ�:%s ����:%s ����������:%s ϵͳ��֧��", nodeCode, attrCode, dataType);
					ecifData.setStatus(ErrorCode.ERR_XML_CFG_DATATYPE_NOT_SUPPORT.getCode(), "�������Ͳ�֧��");
					log.warn(msg);
					return;
				}
			}
			List<Element> elementList;
			elementList = (List<Element>) context.selectNodes(xpath + attrCode);
			attrNum = elementList == null ? 0 : elementList.size();

			// ��������XSDУ�����Խڵ�Ĭ��ֵ�ǿ��ݴ��ϸ��޶�,XSD_CHECK_NULL_ATTRΪtrue���ϸ�У��,�����нڵ㲻��ȱ��
			if (attrNum != nodeNum && "1".equals(this.txModel.getTxDef().getTxCheckXsd())
					&& MdmConstants.XSD_CHECK_NULL_ATTR) {
				if (StringUtil.isEmpty(txMsgNodeAttr.getDefaultVal())) {
					String msg = String.format("%s(%s),���������޸ýڵ�", ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(),
							attrCode);
					ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), msg);
					log.warn(msg);
				}
			}
			if (elementList != null) {
				for (Element element : elementList) {
					value = element.getTextTrim();
					/** ȥǰ��հ� ***/
					value = StringUtil.StringTrim(value);

					if (isFormat) {
						/** ��ֵ�����͸�ʽ�� ***/
						value = formatValue(element.getName(), decimalFormat, dateFormat, value);
					}
					if (!ecifData.isSuccess()) { return; }
					/** У�� ***/
					value = processCTDR(txMsgNodeAttr, value);
					if (!ecifData.isSuccess()) { return; }
					element.setText(value);
				}
			}
		}
		// ��ǰ�ڵ��µ������ӽڵ�
		List<TxMsgNode> txMsgNodeList = txMsgNodeMap.get(currentxMsgNode.getNodeId());
		if (txMsgNodeList != null) {
			for (TxMsgNode txMsgNode : txMsgNodeList) {
				String newXpath = xpath;
				if (MdmConstants.NODE_GROUP_M.equals(txMsgNode.getNodeGroup())) {
					if ("0".equals(txMsgNode.getNodeDisplay())) {
						ecifData.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), "���������ô���");
						log.warn("{}:{}�ڵ���,��������ΪӰʽ��ǩ", ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(),
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
							String msg = String.format("%s,�������нڵ�%s(%s%s)������,�ýڵ�Ϊ�ڵ���",
									ErrorCode.ERR_XML_FORMAT_INVALID.getChDesc(), txMsgNode.getNodeName(), attrCode,
									MdmConstants.NODE_GROUP_SUFFIX);
							ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), msg);
							log.warn(msg);
							return;
						}
						newXpath += txMsgNode.getNodeCode() + MdmConstants.NODE_GROUP_SUFFIX + "/"
								+ txMsgNode.getNodeCode();
					}
				} else {// �ǽڵ������û�и��ڵ�//------------------------------
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
	 * @��������:formatValue
	 * @��������:��ʽ������
	 * @�����뷵��˵��:
	 * @param decimalFormat
	 * @param dateFormat
	 * @param value
	 * @return
	 * @�㷨����:
	 */
	private String formatValue(String attrName, DecimalFormat decimalFormat, SimpleDateFormat dateFormat, String value) {
		if (!StringUtils.isEmpty(value)) {
			if (decimalFormat != null) {
				try {
					/** ���ͻ�С����������С�����������ٸ�ʽ�� */
					value = decimalFormat.format(Double.parseDouble(value));
				} catch (Exception e) {
					ecifData.setStatus(ErrorCode.ERR_RULE_INVALID_LONG.getCode(), "%s(%s)��ֵ���͸�ʽ����ȷ", attrName, value);
					log.warn("{}({}):��ֵ���͸�ʽ����ȷ", attrName, value);
					return null;
				}
			} else if (dateFormat != null) {
				/****
				 * ʱ��:��ʽ�����ų�����Ĭ��ֵ��ǰ̨������ܻᴫ�� 0000-00-00,0000-01-01��Ĭ��ֵ����������˵������ǿ�
				 */
				if (excludeDates != null) {
					for (String excludeDate : excludeDates) {
						if (value.equals(excludeDate)) {
							log.info("���˵�����ʱ��{}({})", attrName, value);
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
					ecifData.setStatus(ErrorCode.ERR_RULE_INVALID_DATE_FORMAT.getCode(), "%s(%s):����ʱ���ʽ����ȷ", attrName,
							value);
					log.warn("{}({}):����ʱ���ʽ����ȷ", attrName, value);
					return null;
				}
			}
		}
		return value;
	}

}
