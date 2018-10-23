/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.even
 * @�ļ�����SynchroEventDAO.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:33:39
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.even;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.integration.sync.listen.DataSynchroData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�SynchroEventDAO
 * @���������¼�֪ͨ���¼
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:33:43
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:33:43
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
public class SynchroEventDAO {
	/**
	 * @��������:synchroEvent
	 * @��������:�¼�֪ͨ���¼
	 * @�����뷵��˵��:
	 * @param ecifData
	 * @param txSyncConf
	 * @throws Exception
	 * @�㷨����:
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void synchroEvent(EcifData ecifData, TxSyncConf txSyncConf) throws Exception {
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		/** ͬһ�����ף�ͬһ���ͻ��Ĳ��������û�б���������Ҫ�ύͬ���¼� **/
		// List<TxEvtNotice> notices = baseDAO
		// .findWithIndexParam(
		// "FROM TxEvtNotice T where T.txCode=? and T.custNo=? and T.eventSysNo=? and T.eventType=? and eventDealStat=?",
		// ecifData.getTxCode(), ecifData.getEcifCustNo(),
		// ecifData.getOpChnlNo(), "0",
		// DataSynchroData.EVENTDEALSTAT_WAIT);

		List<TxEvtNotice> notices = baseDAO
				.findWithIndexParam(
						"FROM TxEvtNotice T where T.txCode=? and T.custNo=? and T.eventSysNo=? and T.eventType=? and eventDealStat=?",
						ecifData.getTxCode(), ecifData.getCustId(), ecifData.getOpChnlNo(), "0",
						DataSynchroData.EVENTDEALSTAT_WAIT);
		if (notices == null || notices.isEmpty()) {
			TxEvtNotice txEvtNotice = new TxEvtNotice();
			txEvtNotice.setEventId(OIdUtils.getIdOfLong());
			txEvtNotice.setEventName("����ͬ��");
			txEvtNotice.setEventType("0");// ����ͬ��
			txEvtNotice.setEventDesc(txSyncConf.getSyncConfDesc());
			txEvtNotice.setEventTime(new Timestamp(ecifData.getStopWatch().getStopTime()));
			txEvtNotice.setTxCode(ecifData.getTxCode());
			// OIdUtils.setCustIdValue(txEvtNotice,ecifData.getCustId());
			txEvtNotice.setCustNo(ecifData.getCustId());
			txEvtNotice.setCustId(Long.parseLong(ecifData.getCustId()));
			txEvtNotice.setTxFwId(ecifData.getReqSeqNo());
			txEvtNotice.setEventSysNo(ecifData.getOpChnlNo());
			txEvtNotice.setEventDealStat(DataSynchroData.EVENTDEALSTAT_WAIT);
			// ������ֶ������ʶ
			txEvtNotice.setManualFlag("0");// ϵͳ����
			txEvtNotice.setManualStat("0");// δ�ֶ�����
			baseDAO.persist(txEvtNotice);
			baseDAO.flush();
		}
	}
}
