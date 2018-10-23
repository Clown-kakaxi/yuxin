/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.adapter.dao
 * @文件名：BatchLogHelper.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:29:37
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：BatchLogHelper
 * @类描述：联机批量日志记录帮助类
 * @功能描述:用于更新批量交易附加日志表，批量交易处理明细表
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:29:49   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:29:49
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
public class BatchLogHelper {
	
	/**
	 * The log.
	 * 
	 * @属性描述:
	 */
	private static Logger log = LoggerFactory.getLogger(BatchLogHelper.class);
	
	/**
	 * The WAITIN g_ stat.
	 * 
	 * @属性描述:等待处理
	 */
	private static String WAITING_STAT = "0";
	
	/**
	 * The PROCESSIN g_ stat.
	 * 
	 * @属性描述:正在处理
	 */
	private static String PROCESSING_STAT = "1";
	
	/**
	 * The FINIS h_ stat.
	 * 
	 * @属性描述:完成处理
	 */
	private static String FINISH_STAT = "2";
	
	/**
	 * The ERRO r_ stat.
	 * 
	 * @属性描述:处理失败
	 */
	private static String ERROR_STAT = "3";
	
	/**
	 * The WAITIN g_ sta t_ asyn.
	 * 
	 * @属性描述:等待另一个线程处理
	 */
	private static String WAITING_STAT_ASYN = "00";
	
	/**
	 * The base dao.
	 * 
	 * @属性描述:
	 */
	private JPABaseDAO baseDAO;

	
	/**
	 * @函数名称:txBatchBefore
	 * @函数描述: 联机批量接到任务后记录日志
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
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
	 * @函数名称:txBatchBegin
	 * @函数描述:联机批量开始，记录状态
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@param txBatchLog
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
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
	 * @函数名称:txBatchEnd
	 * @函数描述:联机批量完成，记录状态
	 * @参数与返回说明:
	 * 		@param txBatchLog
	 * 		@param success
	 * 		@param needAnother
	 * @算法描述:
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
	 * @函数名称:getTxBatchByTxFwId
	 * @函数描述:通过流水号查询联机批量日志记录.
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@return
	 * @算法描述:
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
			log.error("通过流水号查询联机批量日志记录失败,流水号{}", ecifData.getReqSeqNo());
			log.error("错误:", e);
		}
		return null;
	}

	
	/**
	 * @函数名称:noteTxBatchDetail
	 * @函数描述: 批量交易处理明细表
	 * @参数与返回说明:
	 * 		@param txBatchDetailList
	 * 		@param ecifData
	 * 		@throws Exception
	 * @算法描述:
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
	 * @函数名称:isAsynBatchLog
	 * @函数描述:判断是否多个线程分工处理
	 * @参数与返回说明:
	 * 		@param txBatchLog
	 * 		@return
	 * @算法描述:
	 */
	public boolean isAsynBatchLog(TxBatchLog txBatchLog) {
		if (WAITING_STAT_ASYN.equals(txBatchLog.getTxBatchStat())) {
			return true;
		} else {
			return false;
		}

	}

}
