/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.adapter.message.xml
 * @文件名：XmlIntegrationLayer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:35:18
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：XmlIntegrationLayer
 * @类描述：集成层处理
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:35:27
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:35:27
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class XmlIntegrationLayer extends ReqMsgValidation implements IntegrationLayer {
	private static Logger log = LoggerFactory.getLogger(XmlIntegrationLayer.class);
	private JPABaseDAO baseDAO;
	/**
	 * @属性名称:verifChain
	 * @属性描述:信息校验链
	 * @since 1.0.0
	 */
	private IVerifChain verifChain = IntegrationValidation.getInstance();

	// 记录交易日志
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

		// TODO 修改为可配置
		if (ecifData.getRepStateCd().startsWith("00")) {
			txLog.setTxResult("0"); // 成功
		} else if (ecifData.getRepStateCd().startsWith("01")) {
			txLog.setTxResult("1"); // 警告
		} else {
			txLog.setTxResult("2"); // 失败
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
			txLog.setSrcSysCd("99"); // 未知源系统代码
		}

		txLog.setReqMsg(ecifData.getPrimalMsg());
		txLog.setResMsg(resXml);
		try {
			LogDAO logDAO = (LogDAO) SpringContextUtils.getBean("logDAO");
			logDAO.saveLog(txLog);
		} catch (Exception e) {
			log.error("交易处理日志出错:", e);
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
				this.ecifData.setStatus(ErrorCode.ERR_TX_SERVER_STAT_BLOCK.getCode(), "查询交易锁状态错误,TX_LOCK表中数据错误,交易无法正常服务");
				return;
			}
			if (!MdmConstants.TX_LOCK_STAT.equals(txLockStat.get(0))) {
				this.ecifData.setSuccess(false);
				this.ecifData.setStatus(ErrorCode.ERR_TX_SERVER_STAT_BLOCK);
				return;
			}
		} else {
			log.warn("交易码: {},请求系统: {}, 警告错误码: {}, 警告信息:{}", data.getOpChnlNo(), data.getTxCode(), ErrorCode.ERR_TX_SERVER_STAT_BLOCK.getCode(),
					"查询交易锁状态错误,TX_LOCK表中数据错误为空,交易程序无法正常获取ETL处理状态,交易正常进行,处理结果可能存在脏数据");
		}

		try {
			/***
			 * 请求报文校验
			 */
			log.info("正在进行请求接入校验...");
			if (!verifChain.sendToChain(ecifData)) {
				log.info("请求接入校验失败");
				return;
			}
			/***
			 * 交易业务校验
			 */
			log.info("正在进行请求业务校验...");
			if (!initTxModel()) {
				log.info("请求业务校验失败");
				return;
			}
			if (!validateAndConvertReqMsg()) {
				log.info("请求业务校验失败");
				return;
			}
			/***
			 * 交易调度
			 */
			DealDispatchEngine.DealDispatcher(data);
		} catch (Exception e) {
			log.error("交易处理出错:", e);
			data.setStatus(ErrorCode.ERR_SERVER_PROG_ERROR);
		} finally {
			if (data.getRepNode() == null) {
				data.setRepNode(DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY));
			}
		}
		return;
	}

}
