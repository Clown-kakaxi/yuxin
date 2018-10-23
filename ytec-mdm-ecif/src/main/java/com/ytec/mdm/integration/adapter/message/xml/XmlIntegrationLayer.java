/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.adapter.message.xml
 * @�ļ�����XmlIntegrationLayer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:35:18
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.adapter.message.xml;

import java.sql.Timestamp;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxLog;
import com.ytec.mdm.integration.adapter.dao.LogDAO;
import com.ytec.mdm.integration.adapter.message.ReqMsgValidation;
import com.ytec.mdm.integration.check.validation.IntegrationValidation;
import com.ytec.mdm.integration.transaction.core.DealDispatchEngine;
import com.ytec.mdm.integration.transaction.facade.IVerifChain;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�XmlIntegrationLayer
 * @�����������ɲ㴦��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:35:27
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:35:27
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class XmlIntegrationLayer extends ReqMsgValidation implements IntegrationLayer {
	private static Logger log = LoggerFactory.getLogger(XmlIntegrationLayer.class);
	private JPABaseDAO baseDAO;
	/**
	 * @��������:verifChain
	 * @��������:��ϢУ����
	 * @since 1.0.0
	 */
	private IVerifChain verifChain = IntegrationValidation.getInstance();

	// ��¼������־
	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.integration.adapter.message.xml.IntegrationLayer#setTxLog(java.lang.String)
	 */
	public void setTxLog(String resXml) {
		if (!MdmConstants.txDbLog) { return; }
		if (ecifData == null) { return; }
		TxLog txLog = new TxLog();
		if (ecifData.getTxLogId() != null) {
			txLog.setTxLogId(ecifData.getTxLogId());
		}
		if (ecifData.getReqSeqNo() != null) {
			txLog.setTxFwId(ecifData.getReqSeqNo());
		} else {
			txLog.setTxFwId("");
		}
		if (ecifData.getTxCode() != null) {
			txLog.setTxCode(ecifData.getTxCode());
		} else {
			txLog.setTxCode("");
		}
		txLog.setTxMethod(ecifData.getInterFaceType());
		txLog.setTxDt(new Timestamp(ecifData.getStopWatch().getStartTime()));
		txLog.setTxReqTm(new Timestamp(ecifData.getStopWatch().getStartTime()));
		txLog.setTxResTm(new Timestamp(ecifData.getStopWatch().getStopTime()));

		// TODO �޸�Ϊ������
		if (ecifData.getRepStateCd().startsWith("00")) {
			txLog.setTxResult("0"); // �ɹ�
		} else if (ecifData.getRepStateCd().startsWith("01")) {
			txLog.setTxResult("1"); // ����
		} else {
			txLog.setTxResult("2"); // ʧ��
		}

		txLog.setTxRtnCd(ecifData.getRepStateCd());
		if (ecifData.getDetailDes() != null && ecifData.getDetailDes().length() > 255) {
			txLog.setTxRtnMsg(ecifData.getDetailDes().substring(0, 210));
		} else {
			txLog.setTxRtnMsg(ecifData.getDetailDes());
		}
		txLog.setTxSvrIp(StringUtil.getLocalIp());
		try {
			txLog.setSrcSysCd(ecifData.getOpChnlNo());
		} catch (Exception e) {
			txLog.setSrcSysCd("99"); // δ֪Դϵͳ����
		}

		txLog.setReqMsg(ecifData.getPrimalMsg());
		txLog.setResMsg(resXml);
		try {
			LogDAO logDAO = (LogDAO) SpringContextUtils.getBean("logDAO");
			logDAO.saveLog(txLog);
		} catch (Exception e) {
			log.error("���״�����־����:", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.integration.adapter.message.xml.IntegrationLayer#process(com.ytec.mdm.base.bo.EcifData)
	 */
	public void process(EcifData data) {
		this.ecifData = data;

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<String> txLockStat = baseDAO.findByNativeSQLWithIndexParam("SELECT STATUS FROM TX_LOCK", null);

		if (txLockStat.size() != 0) {
			if (txLockStat.size() != 1) {
				this.ecifData.setSuccess(false);
				this.ecifData.setStatus(ErrorCode.ERR_TX_SERVER_STAT_BLOCK.getCode(), "��ѯ������״̬����,TX_LOCK�������ݴ���,�����޷���������");
				return;
			}
			if (!MdmConstants.TX_LOCK_STAT.equals(txLockStat.get(0))) {
				this.ecifData.setSuccess(false);
				this.ecifData.setStatus(ErrorCode.ERR_TX_SERVER_STAT_BLOCK);
				return;
			}
		} else {
			log.warn("������: {},����ϵͳ: {}, ���������: {}, ������Ϣ:{}", data.getOpChnlNo(), data.getTxCode(), ErrorCode.ERR_TX_SERVER_STAT_BLOCK.getCode(),
					"��ѯ������״̬����,TX_LOCK�������ݴ���Ϊ��,���׳����޷�������ȡETL����״̬,������������,���������ܴ���������");
		}

		try {
			/***
			 * ������У��
			 */
			log.info("���ڽ����������У��...");
			if (!verifChain.sendToChain(ecifData)) {
				log.info("�������У��ʧ��");
				return;
			}
			/***
			 * ����ҵ��У��
			 */
			log.info("���ڽ�������ҵ��У��...");
			if (!initTxModel()) {
				log.info("����ҵ��У��ʧ��");
				return;
			}
			if (!validateAndConvertReqMsg()) {
				log.info("����ҵ��У��ʧ��");
				return;
			}
			/***
			 * ���׵���
			 */
			DealDispatchEngine.DealDispatcher(data);
		} catch (Exception e) {
			log.error("���״������:", e);
			data.setStatus(ErrorCode.ERR_SERVER_PROG_ERROR);
		} finally {
			if (data.getRepNode() == null) {
				data.setRepNode(DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY));
			}
		}
		return;
	}

}
