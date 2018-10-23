/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.sync.listen
 * @�ļ�����DataBaseEventListener.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:53:23
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.sync.listen;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.integration.sync.ptsync.SynchroPerformance;
import com.ytec.mdm.interfaces.common.IServer;
import com.ytec.mdm.interfaces.common.even.EvenSubject;
import com.ytec.mdm.interfaces.common.even.Subject;
import com.ytec.mdm.server.common.DataSynchroConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�DataBaseEventListener
 * @������������ͬ���¼�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:53:28
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:53:28
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class DataBaseEventListener implements IServer, Runnable {
	private static Logger log = LoggerFactory.getLogger(DataBaseEventListener.class);
	private JPABaseDAO baseDAO = null;
	/**
	 * @��������:pool
	 * @��������:�����̳߳�
	 * @since 1.0.0
	 */
	private ExecutorService pool;
	/**
	 * @��������:poolSize
	 * @��������:�̳߳ش�С
	 * @since 1.0.0
	 */
	private int poolSize;
	/**
	 * @��������:scheduler
	 * @��������:��ʱ�����߳�
	 * @since 1.0.0
	 */
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	/**
	 * @��������:startTime
	 * @��������:��ʼ�ӳ�
	 * @since 1.0.0
	 */
	private int startTime;
	/**
	 * @��������:each
	 * @��������:����
	 * @since 1.0.0
	 */
	private int each;
	private DataSynchroData dataSynchrohelper;
	/**
	 * @��������:dealTimeOut
	 * @�����������¼���ʱʱ��
	 * @since 1.0.0
	 */
	private Long dealTimeOut;

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.integration.sync.listen.IDataSynchro#stop()
	 */
	public void stop() {
		// TODO Auto-generated method stub
		pool.shutdownNow();
		scheduler.shutdownNow();
		log.info("ͬ�������˳�");
	}

	public void start() {
		// TODO Auto-generated method stub
		log.info("ͬ���¼���������");
		scheduler.scheduleWithFixedDelay(this, startTime, each, TimeUnit.SECONDS);
	}

	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		this.poolSize = Integer.valueOf((String) DataSynchroConfiger.listenerArg.get("poolSize"));
		this.startTime = Integer.valueOf((String) DataSynchroConfiger.listenerArg.get("startTime"));
		this.each = Integer.valueOf((String) DataSynchroConfiger.listenerArg.get("each"));
		this.dealTimeOut = Long.valueOf((String) DataSynchroConfiger.listenerArg.get("dealTimeOut"));
		if (poolSize <= 0) {
			poolSize = Runtime.getRuntime().availableProcessors() * 2;
			log.warn("�����̳߳ش�С����,����Ĭ������,��С{}", poolSize);
		}
		pool = Executors.newFixedThreadPool(this.poolSize);
	}

	/**
	 * //�����¼������б������ڴ�����¼���д���¼�֪ͨ���У�״̬��Ϊ���ڴ���
	 * 
	 * @param txEvtNotice
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class },propagation = Propagation.SUPPORTS)
	public void lockEventDealStat(TxEvtNotice txEvtNotice) {
		txEvtNotice.setEventDealStat(DataSynchroData.EVENTDEALSTAT_WAIT);
		baseDAO.persist(txEvtNotice);
		baseDAO.flush();
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			dataSynchrohelper = (DataSynchroData) SpringContextUtils.getBean("dataSynchroData");
			// ��ѯ�¼�֪ͨ�����¼�״̬Ϊδ������¼���
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			// �����¼������б����Ѵ�����¼���д���¼�֪ͨ���У�״̬��Ϊ�Ѵ���
			dataSynchrohelper.doEventDealStat(dealTimeOut);
			List<TxEvtNotice> txEvtNoticeList = baseDAO.findWithIndexParam("FROM TxEvtNotice where eventDealStat=?",
					DataSynchroData.EVENTDEALSTAT_WAIT);
			// �����ѯ�����Ϊ�գ����¼��ַ��¼���Ϣ�������̳߳ء�
			if (txEvtNoticeList != null && !txEvtNoticeList.isEmpty()) {
				for (TxEvtNotice txEvtNotice : txEvtNoticeList) {
					if (!dataSynchrohelper.hasEvtNotice(txEvtNotice.getEventId())) {
						log.info(">>>>�ַ�ͬ���¼�[����������{},����ϵͳ{},ͬ���ͻ���{}]", txEvtNotice.getTxCode(),
								txEvtNotice.getEventSysNo(), txEvtNotice.getCustNo());
						pool.execute(new SynchroPerformance(txEvtNotice));
						dataSynchrohelper.putEvtNotice(txEvtNotice);

//						lockEventDealStat(txEvtNotice);
					}
				}
			}

		} catch (Exception e) {
			log.error("ͬ���¼����ҵ�����", e);
			Subject evenSubject = EvenSubject.getInstance();
			/** �¼�֪ͨ */
			EcifData data = new EcifData();
			data.getStopWatch().start();
			data.setReqSeqNo("---");
			data.setStatus(ErrorCode.ERR_SYNCHRO_BIZLOGIC_ERROR.getCode(), "ͬ���¼����ҵ�����");
			data.getStopWatch().stop();
			evenSubject.eventNotify(data);
		}
	}

}
