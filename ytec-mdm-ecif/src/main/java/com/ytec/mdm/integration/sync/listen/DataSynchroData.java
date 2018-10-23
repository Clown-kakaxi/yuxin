/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.sync.listen
 * @�ļ�����DataSynchroData.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:56:24
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.sync.listen;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.domain.txp.TxSyncErr;
import com.ytec.mdm.domain.txp.TxSyncLog;
import com.ytec.mdm.integration.sync.ptsync.AbsSynchroExecutor;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�DataSynchroData
 * @��������ͬ����������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:56:25
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:56:25
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
public class DataSynchroData {
	private static Logger log = LoggerFactory.getLogger(DataSynchroData.class);
	private static ConcurrentHashMap<Long, TxEvtNotice> eventDealQueue = new ConcurrentHashMap<Long, TxEvtNotice>();
	private static ConcurrentHashMap<String, List<TxSyncConf>> txSyncConfMap = new ConcurrentHashMap<String, List<TxSyncConf>>();
	private static Map<String, String> clientMap = new TreeMap<String, String>();
	public static String EVENTDEALSTAT_WAIT = "1"; // �¼�������
	public static String EVENTDEALSTAT_OVER = "0"; // �¼���ɴ���
	public static String EVENTDEALSTAT_RUN = "2"; // �¼����ڴ���

	public void init() {
		txSyncConfMap.clear();
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<TxSyncConf> txSyncConfList = baseDAO.findWithIndexParam("FROM TxSyncConf C WHERE C.syncConfStat=?",
				EVENTDEALSTAT_WAIT);
		txSyncConfMap.clear();
		List pointer = null;
		String key = null;
		if (txSyncConfList != null) {
			for (TxSyncConf txSyncConf : txSyncConfList) {
				key = txSyncConf.getTxCode() + "_" + txSyncConf.getSrcSysNo();
				if ((pointer = txSyncConfMap.get(key)) != null) {
					pointer.add(txSyncConf);
				} else {
					pointer = new ArrayList<TxSyncConf>();
					pointer.add(txSyncConf);
					txSyncConfMap.put(key, pointer);
				}
			}
		}

		clientMap.clear();
		String[] clients = BusinessCfg.getStringArray("syncDealMethod");
		if (clients != null) {
			for (String client : clients) {
				String[] val = client.split("\\:");
				if (val != null && val.length == 2) {
					clientMap.put(val[0], val[1]);
				} else {
					log.warn("�ͻ���ӳ��[{}]���ô���", client);
				}
			}
		}
	}

	public void putEvtNotice(TxEvtNotice ev) {
		eventDealQueue.put(ev.getEventId(), ev);
	}

	public boolean hasEvtNotice(Long eventId) {
		return eventDealQueue.containsKey(eventId);
	}

	public void doEventDealStat(Long timeOut) {
		if (eventDealQueue.isEmpty()) { return; }
		Long timeNow = System.currentTimeMillis();
		Timestamp time = new Timestamp(timeNow - timeOut);
		Iterator<Entry<Long, TxEvtNotice>> it = eventDealQueue.entrySet().iterator();
		while (it.hasNext()) {
			TxEvtNotice bc = (TxEvtNotice) it.next().getValue();
			if (bc.getEventTime().before(time)) {
				it.remove();
				log.warn("�¼�[{}],�ͻ�[{}]ͬ����ʱ", bc.getEventId(), bc.getCustNo());
			}
		}
	}

	/**
	 * //�����¼������б����Ѵ�����¼���д���¼�֪ͨ���У�״̬��Ϊ�Ѵ���
	 * 
	 * @param txEvtNotice
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void doEventDealStatOver(TxEvtNotice txEvtNotice) {
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		baseDAO.merge(txEvtNotice);
		if (eventDealQueue.containsKey(txEvtNotice.getEventId())) {
			eventDealQueue.remove(txEvtNotice.getEventId());
		}
		// �ж��Ƿ��ֶ�ͬ���¼���������ֶ�ͬ����Ҫ����ԭ�¼�״̬��
		if ("1".equals(txEvtNotice.getManualFlag())) {
			try {
				TxEvtNotice origEvt = (TxEvtNotice) baseDAO.findUniqueWithIndexParam(
						"FROM TxEvtNotice t where t.eventId=?", txEvtNotice.getManualEvtId());
				if (origEvt != null) {
					// ��ǰ����dealResultΪ"0"Ϊ�ɹ� ����Ϊʧ�ܡ�
					origEvt.setManualStat("000000".equals(txEvtNotice.getEventDealResult()) ? "2" : "3");
					baseDAO.persist(origEvt);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("ԭ�¼�����ʧ��", e);
			}
		}

		baseDAO.flush();
	}

	/**
	 * //�����¼������б������ڴ�����¼���д���¼�֪ͨ���У�״̬��Ϊ���ڴ���
	 * 
	 * @param txEvtNotice
	 */
	@Transactional
	// (rollbackFor = { Exception.class, RuntimeException.class })
	public void doEventDealStatRunning(TxEvtNotice txEvtNotice) {
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		baseDAO.merge(txEvtNotice);
		try {
			TxEvtNotice origEvt = (TxEvtNotice) baseDAO.findUniqueWithIndexParam(
					"FROM TxEvtNotice t where t.eventId=?", txEvtNotice.getEventId());
			if (origEvt != null) {
				// ��ǰ����dealResultΪ"0"Ϊ�ɹ� ����Ϊʧ�ܡ�
				origEvt.setEventDealStat(DataSynchroData.EVENTDEALSTAT_RUN);// .setManualStat("000000".equals(txEvtNotice.getEventDealResult()) ? "2" : "3");
				origEvt.setEventDealResult("Running");
				origEvt.setEventDealInfo("���ڴ���");
				baseDAO.persist(origEvt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ԭ�¼�����ʧ��", e);
		}

		baseDAO.flush();
	}

	public static List<TxSyncConf> getTxSyncConf(String txCode, String opSyscd) {
		return txSyncConfMap.get(txCode + "_" + opSyscd);
	}

	public static String getClientByType(String key) {
		return clientMap.get(key);
	}

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void logHelper(TxEvtNotice txEvtNotice, TxSyncConf txSyncConf, AbsSynchroExecutor executor, boolean flag)
			throws Exception {
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Long syncTaskId = OIdUtils.getIdOfLong();

		Timestamp syncDealTime = new Timestamp(System.currentTimeMillis());
		TxSyncLog txSyncLog = new TxSyncLog();
		txSyncLog.setCustId(txEvtNotice.getCustId());
		txSyncLog.setCustNo(txEvtNotice.getCustNo());
		txSyncLog.setDescSysNo(txSyncConf.getDestSysNo());
		txSyncLog.setEventId(txEvtNotice.getEventId());
		txSyncLog.setKeyInfo(txEvtNotice.getKeyInfo());
		txSyncLog.setSrcSysNo(txEvtNotice.getEventSysNo());
		txSyncLog.setSyncConfId(txSyncConf.getSyncConfId());
		txSyncLog.setSyncDealTime(syncDealTime);
		if (executor != null) {
			txSyncLog.setSyncReqMsg(executor.getSynchroRequestMsg());
			txSyncLog.setSyncResMsg(executor.getSynchroResponseMsg());
		}
		txSyncLog.setSyncTaskId(syncTaskId);
		txSyncLog.setTxCode(txEvtNotice.getTxCode());
		txSyncLog.setTxFwId(Long.valueOf(txEvtNotice.getTxFwId()));
		if (!flag) {
			TxSyncErr txSyncErr = new TxSyncErr();
			txSyncErr.setCustId(txEvtNotice.getCustId());
			txSyncErr.setCustNo(txEvtNotice.getCustNo());
			txSyncErr.setDescSysNo(txSyncConf.getDestSysNo());
			txSyncErr.setEventId(txEvtNotice.getEventId());
			txSyncErr.setKeyInfo(txEvtNotice.getKeyInfo());
			txSyncErr.setSrcSysNo(txEvtNotice.getEventSysNo());
			// txSyncErr.setSyncBatchId(syncBatchId);
			txSyncErr.setSyncConfId(txSyncConf.getSyncConfId());
			// txSyncErr.setSyncDealInfo(syncDealInfo);
			txSyncErr.setSyncDealTime(syncDealTime);
			if (executor != null) {
				txSyncErr.setSyncReqMsg(executor.getSynchroRequestMsg());
				txSyncErr.setSyncResMsg(executor.getSynchroResponseMsg());
			}
			txSyncErr.setSyncTaskId(syncTaskId);
			txSyncErr.setTxCode(txEvtNotice.getTxCode());
			txSyncErr.setTxFwId(Long.valueOf(txEvtNotice.getTxFwId()));
			txSyncErr.setSyncTaskStat(EVENTDEALSTAT_OVER);
			txSyncErr.setSyncDealResult(executor.getSyncErr() == null ? txEvtNotice.getEventDealResult() : executor
					.getSyncErr().getSyncDealResult());
			txSyncErr.setSyncDealInfo(executor.getSyncErr() == null ? txEvtNotice.getEventDealInfo() : executor
					.getSyncErr().getSyncDealInfo());
			txSyncLog.setSyncTaskStat(EVENTDEALSTAT_OVER);

			txSyncLog.setSyncDealResult(executor.getSyncErr() == null ? txEvtNotice.getEventDealResult() : executor
					.getSyncErr().getSyncDealResult());
			txSyncLog.setSyncDealInfo(executor.getSyncErr() == null ? txEvtNotice.getEventDealInfo() : executor
					.getSyncErr().getSyncDealInfo());

			baseDAO.persist(txSyncLog);
			baseDAO.persist(txSyncErr);
		} else {
			txSyncLog.setSyncTaskStat(EVENTDEALSTAT_OVER);
			txSyncLog.setSyncDealResult(executor.getSyncLog().getSyncDealResult());
			txSyncLog.setSyncDealInfo(executor.getSyncLog().getSyncDealInfo());
			baseDAO.persist(txSyncLog);
		}
		baseDAO.flush();
	}

}
