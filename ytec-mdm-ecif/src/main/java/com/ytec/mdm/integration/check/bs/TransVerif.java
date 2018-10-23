/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����TransVerif.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:44:46
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�TransVerif
 * @��������ҵ�������֤ת��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:44:47
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:44:47
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class TransVerif {
	private static Logger log = LoggerFactory.getLogger(TransVerif.class);
	protected static String INT_FORMAT = "#";
	protected static String FLOAT_FORMAT = "#.##";
	protected static String DATE_FORMAT = "yyyy-MM-dd";
	protected static String TIME_FORMAT = "HH:mm:ss";
	protected static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	protected TxModel txModel;// ��������ģ�Ͷ���
	protected EcifData ecifData = null;

	/**
	 * @��������:processCTDR
	 * @��������:����ҵ��У�顢ת����ת�룬����ת��
	 * @�����뷵��˵��:
	 * @param txMsgNodeAttr
	 * @param value
	 * @return
	 * @�㷨����:
	 */
	protected String processCTDR(TxMsgNodeAttr txMsgNodeAttr, String value) {
		String attrName = StringUtil.isEmpty(txMsgNodeAttr.getAttrName()) ? txMsgNodeAttr.getAttrCode() : txMsgNodeAttr.getAttrName();
		if (StringUtils.isEmpty(value)) {
			if ("N".equals(txMsgNodeAttr.getNulls())) {
				String msg = String.format("%s(%s)����Ϊ��", attrName, txMsgNodeAttr.getAttrCode());
				ecifData.setStatus(ErrorCode.ERR_RULE_VALUE_IS_NULL.getCode(), msg);
				log.warn(msg);
				return null;
			} else {
				return value;
			}
		} else {
			if (MdmConstants.NODE_TYPE_VARI_STR.equals(txMsgNodeAttr.getDataType())) {
				/** �䳤�ַ��� **/
				/*** �����ֽڳ���,������Ч�� ****/
				int relLength = value.getBytes().length; // value.length();
				if (txMsgNodeAttr.getDataLen() != null && txMsgNodeAttr.getDataLen() > 0 && relLength > txMsgNodeAttr.getDataLen()) {
					ecifData.setStatus(ErrorCode.ERR_RULE_INVALID_LENGTH.getCode(), "%s(%s)���Ȳ��ɳ���%d", attrName, txMsgNodeAttr.getAttrCode(), txMsgNodeAttr.getDataLen());
					log.warn("{}({})���Ȳ��ɳ���{}", attrName, txMsgNodeAttr.getAttrCode(), txMsgNodeAttr.getDataLen());
					return null;
				}
			} else if (MdmConstants.NODE_TYPE_FIX_STR.equals(txMsgNodeAttr.getDataType())) {
				/** �������ַ��� **/
				int relLength = value.getBytes().length; // value.length();
				if (txMsgNodeAttr.getDataLen() != null && txMsgNodeAttr.getDataLen() > 0 && relLength != txMsgNodeAttr.getDataLen()) {
					ecifData.setStatus(ErrorCode.ERR_RULE_INVALID_LENGTH.getCode(), "%s(%s)Ӧ��Ϊ�̶�����%d", attrName, txMsgNodeAttr.getAttrCode(), txMsgNodeAttr.getDataLen());
					log.warn("{}({})Ӧ��Ϊ�̶�����{}", attrName, txMsgNodeAttr.getAttrCode(), txMsgNodeAttr.getDataLen());
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
	 * @��������:convertObjectToString
	 * @��������:��������������ת��Ϊ�ַ�����ʽ
	 * @�����뷵��˵��:
	 * @param nodeCode
	 * @param txMsgNodeAttr
	 * @param value
	 * @return
	 * @�㷨����:
	 */
	protected String convertObjectToString(Long nodeCode, TxMsgNodeAttr txMsgNodeAttr, Object value) {

		String attrName = StringUtil.isEmpty(txMsgNodeAttr.getAttrName()) ? txMsgNodeAttr.getAttrCode() : txMsgNodeAttr.getAttrName();
		String newValue = null;
		String dataType = txMsgNodeAttr.getDataType();
		String dataFmt = txMsgNodeAttr.getDataFmt();
		if (MdmConstants.NODE_TYPE_FIX_STR.equals(dataType) || MdmConstants.NODE_TYPE_VARI_STR.equals(dataType)) {// �ַ���
			newValue = value.toString();
		} else if (MdmConstants.NODE_TYPE_CLOB.equals(dataType)) {
			if (value instanceof Clob) {
				newValue = StringUtil.readClob((Clob) value);
			} else {
				log.warn("�ֶ�{}�����������Ͳ���", attrName);
				ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), "�ֶ�%s�����������Ͳ���", attrName);
				return null;
			}
		} else {// ���ַ���
			DecimalFormat decimalFormat = null;
			SimpleDateFormat dateformat = null;
			if (MdmConstants.NODE_TYPE_INT.equals(dataType)) {// ����
				newValue = value.toString();
			} else if (MdmConstants.NODE_TYPE_FLOAT.equals(dataType)) {// ������
				if (StringUtil.isEmpty(dataFmt)) {
					decimalFormat = new DecimalFormat(FLOAT_FORMAT);
				} else {
					decimalFormat = new DecimalFormat(dataFmt);
				}
			} else if (MdmConstants.NODE_TYPE_DATE.equals(dataType)) {// ����
				if (StringUtil.isEmpty(dataFmt)) {
					dateformat = new SimpleDateFormat(DATE_FORMAT);
				} else {
					dateformat = new SimpleDateFormat(dataFmt);
				}
			} else if (MdmConstants.NODE_TYPE_TIME.equals(dataType)) {// ʱ��
				if (StringUtil.isEmpty(dataFmt)) {
					dateformat = new SimpleDateFormat(TIME_FORMAT);
				} else {
					dateformat = new SimpleDateFormat(dataFmt);
				}
			} else if (MdmConstants.NODE_TYPE_TIMESTAMP.equals(dataType)) {// ʱ���
				if (StringUtil.isEmpty(dataFmt)) {
					dateformat = new SimpleDateFormat(TIMESTAMP_FORMAT);
				} else {
					dateformat = new SimpleDateFormat(dataFmt);
				}
			} else {
				log.warn("�ֶ�{}�����������Ͳ�֧��", attrName);
				ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), "�ֶ�%s�����������Ͳ�֧��", attrName);
				return null;
			}
			try {
				if (decimalFormat != null) {
					newValue = decimalFormat.format(value);
				} else if (dateformat != null) {
					newValue = dateformat.format(value);
				}
			} catch (Exception e) {
				String msg = "���ر��Ľڵ�:" + nodeCode + "������:" + attrName + " ��ʽ��ʧ��";
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
			log.error("��ȡ�������ö���ʧ��", e);
			ecifData.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), "��ȡ������Ϣʧ��");
		}
		return ecifData.isSuccess();
	}

}
