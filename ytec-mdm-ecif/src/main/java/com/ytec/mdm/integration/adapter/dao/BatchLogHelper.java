/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.adapter.dao
 * @�ļ�����BatchLogHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:29:37
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.adapter.dao;

import java.sql.Timestamp;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxBatchDetail;
import com.ytec.mdm.domain.txp.TxBatchLog;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�BatchLogHelper
 * @������������������־��¼������
 * @��������:���ڸ����������׸�����־���������״�����ϸ��
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:29:49   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:29:49
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
public class BatchLogHelper {
	
	/**
	 * The log.
	 * 
	 * @��������:
	 */
	private static Logger log = LoggerFactory.getLogger(BatchLogHelper.class);
	
	/**
	 * The WAITIN g_ stat.
	 * 
	 * @��������:�ȴ�����
	 */
	private static String WAITING_STAT = "0";
	
	/**
	 * The PROCESSIN g_ stat.
	 * 
	 * @��������:���ڴ���
	 */
	private static String PROCESSING_STAT = "1";
	
	/**
	 * The FINIS h_ stat.
	 * 
	 * @��������:��ɴ���
	 */
	private static String FINISH_STAT = "2";
	
	/**
	 * The ERRO r_ stat.
	 * 
	 * @��������:����ʧ��
	 */
	private static String ERROR_STAT = "3";
	
	/**
	 * The WAITIN g_ sta t_ asyn.
	 * 
	 * @��������:�ȴ���һ���̴߳���
	 */
	private static String WAITING_STAT_ASYN = "00";
	
	/**
	 * The base dao.
	 * 
	 * @��������:
	 */
	private JPABaseDAO baseDAO;

	
	/**
	 * @��������:txBatchBefore
	 * @��������: ���������ӵ�������¼��־
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public TxBatchLog txBatchBefore(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		TxBatchLog txBatchLog = new TxBatchLog();
		Long id = OIdUtils.getIdOfLong();
		txBatchLog.setTxLogId(id);
		txBatchLog.setTxFwId(ecifData.getReqSeqNo());
		txBatchLog.setTxDate(new Timestamp(ecifData.getStopWatch().getStartTime()));
		txBatchLog.setTxBatchStat(WAITING_STAT);
		baseDAO.persist(txBatchLog);
		baseDAO.flush();
		ecifData.setTxLogId(id);
		return txBatchLog;
	}

	
	/**
	 * @��������:txBatchBegin
	 * @��������:����������ʼ����¼״̬
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@param txBatchLog
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public TxBatchLog txBatchBegin(EcifData ecifData, TxBatchLog txBatchLog)
			throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		if (txBatchLog == null) {
			txBatchLog = new TxBatchLog();
			Long id = OIdUtils.getIdOfLong();
			txBatchLog.setTxLogId(id);
			txBatchLog.setTxFwId(ecifData.getReqSeqNo());
			txBatchLog.setTxDate(new Timestamp(ecifData.getStopWatch().getStartTime()));
			ecifData.setTxLogId(id);
		}
		txBatchLog.setTxBatchStat(PROCESSING_STAT);
		baseDAO.merge(txBatchLog);
		baseDAO.flush();
		return txBatchLog;
	}

	
	/**
	 * @��������:txBatchEnd
	 * @��������:����������ɣ���¼״̬
	 * @�����뷵��˵��:
	 * 		@param txBatchLog
	 * 		@param success
	 * 		@param needAnother
	 * @�㷨����:
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void txBatchEnd(TxBatchLog txBatchLog, boolean success,
			boolean needAnother) {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		if (success) {
			if (needAnother) {
				txBatchLog.setTxBatchStat(WAITING_STAT_ASYN);
			} else {
				txBatchLog.setTxBatchStat(FINISH_STAT);
			}
		} else {
			txBatchLog.setTxBatchStat(ERROR_STAT);
		}
		baseDAO.merge(txBatchLog);
		baseDAO.flush();
	}

	
	/**
	 * @��������:getTxBatchByTxFwId
	 * @��������:ͨ����ˮ�Ų�ѯ����������־��¼.
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@return
	 * @�㷨����:
	 */
	public TxBatchLog getTxBatchByTxFwId(EcifData ecifData) {
		try {
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			TxBatchLog txBatchLog = null;
			List<TxBatchLog> logList = baseDAO.findWithIndexParam(
					"FROM TxBatchLog T where T.txDate=? AND T.txFwId=? ",
					new Timestamp(ecifData.getStopWatch().getStartTime()), ecifData.getReqSeqNo());
			if (logList != null && !logList.isEmpty()) {
				txBatchLog = logList.get(0);
			}
			return txBatchLog;
		} catch (Exception e) {
			log.error("ͨ����ˮ�Ų�ѯ����������־��¼ʧ��,��ˮ��{}", ecifData.getReqSeqNo());
			log.error("����:", e);
		}
		return null;
	}

	
	/**
	 * @��������:noteTxBatchDetail
	 * @��������: �������״�����ϸ��
	 * @�����뷵��˵��:
	 * 		@param txBatchDetailList
	 * 		@param ecifData
	 * 		@throws Exception
	 * @�㷨����:
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void noteTxBatchDetail(List<TxBatchDetail> txBatchDetailList,
			EcifData ecifData) throws Exception {

		if (txBatchDetailList != null && !txBatchDetailList.isEmpty()) {
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			for (TxBatchDetail txBatchDetail : txBatchDetailList) {
				txBatchDetail.setTxBatchDealId(OIdUtils.getIdOfLong());
				txBatchDetail.setTxDate(new Timestamp(ecifData.getStopWatch().getStartTime()));
				txBatchDetail.setTxFwId(ecifData.getReqSeqNo());
				baseDAO.persist(txBatchDetail);
			}
			baseDAO.flush();
		}
	}

	
	/**
	 * @��������:isAsynBatchLog
	 * @��������:�ж��Ƿ����̷ֹ߳�����
	 * @�����뷵��˵��:
	 * 		@param txBatchLog
	 * 		@return
	 * @�㷨����:
	 */
	public boolean isAsynBatchLog(TxBatchLog txBatchLog) {
		if (WAITING_STAT_ASYN.equals(txBatchLog.getTxBatchStat())) {
			return true;
		} else {
			return false;
		}

	}

}
