/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.adapter.dao
 * @�ļ�����LogDAO.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:32:20
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */

package com.ytec.mdm.integration.adapter.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxDef4CRM;
import com.ytec.mdm.domain.txp.TxErr;
import com.ytec.mdm.domain.txp.TxLog;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�LogDAO
 * @����������־����
 * @��������:��־�н��׽��Ϊ2�ģ��Զ������쳣���ױ�
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:32:31
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:32:31
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class LogDAO {

	/**
	 * The base dao.
	 *
	 * @��������:
	 */
	private JPABaseDAO baseDAO;

	/**
	 * The log.
	 *
	 * @��������:
	 */
	private static Logger log = LoggerFactory.getLogger(LogDAO.class);

	/**
	 * @��������:saveLog
	 * @��������:��־����
	 * @�����뷵��˵��:
	 * @param txLog
	 * @throws Exception
	 * @�㷨����:
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void saveLog(TxLog txLog) throws Exception {
		String txCode = StringUtil.toString(txLog.getTxCode());
		TxModel4CRM txModel = null;
		if (txCode != null && !txCode.isEmpty()) {
			txModel = TxModelHolder.getTxModelFromCache(txCode);
		}
		TxDef4CRM def = null;
		if (txModel != null) {
			def = txModel.getTxDef();
		}
		if (txLog.getTxLogId() == null) {
			Long id = OIdUtils.getIdOfLong();
			txLog.setTxLogId(id);
		}
		if (def == null) {
			txLog.setTxId(0L);
		} else {
			// txLog.setTxId(def.getCode());
			txLog.setTxName(def.getName());
			txLog.setTxCnName(def.getCnName());
		}
		TxErr txErr = null;
		if (txLog.getTxResult().equals("2")) {
			txErr = new TxErr();
			txErr.setTxErrId(txLog.getTxLogId());
			txErr.setTxFwId(txLog.getTxFwId());// TX_FW_ID

			txErr.setTxId(txLog.getTxId());// TX_ID
			txErr.setTxCode(txLog.getTxCode());// TX_CODE
			txErr.setTxName(txLog.getTxName());// TX_NAME
			txErr.setTxCnName(txLog.getTxCnName());// TX_CN_NAME
			txErr.setTxMethod(txLog.getTxMethod());// TX_METHOD

			txErr.setTxDt(txLog.getTxDt());// TX_DT
			txErr.setTxReqTm(txLog.getTxReqTm());// TX_REQ_TM
			txErr.setTxResTm(txLog.getTxResTm());// TX_RES_TM
			txErr.setTxResult(txLog.getTxResult());// TX_RESULT
			txErr.setTxRtnCd(txLog.getTxRtnCd());// TX_RTN_CD
			txErr.setTxRtnMsg(txLog.getTxRtnMsg());// TX_RTN_MSG
			txErr.setTxSvrIp(txLog.getTxSvrIp());// TX_SVR_IP
			txErr.setSrcSysCd(txLog.getSrcSysCd());// SRC_SYS_CD
			txErr.setSrcSysNm(txLog.getSrcSysNm());// SRC_SYS_NM
			if (MdmConstants.txLogLev != 2) {
				txErr.setReqMsg(txLog.getReqMsg());// REQ_MSG
				txErr.setResMsg(txLog.getResMsg());// RES_MSG
			}
		}
		if (MdmConstants.txLogLev != 0) {
			txLog.setReqMsg(null);
			txLog.setResMsg(null);
		}
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		baseDAO.persist(txLog);
		if (txErr != null) {
			baseDAO.persist(txErr);
		}
		baseDAO.flush();

	}

}
