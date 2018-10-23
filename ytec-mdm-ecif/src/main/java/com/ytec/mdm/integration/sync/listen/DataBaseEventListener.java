/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.sync.listen
 * @文件名：DataBaseEventListener.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:53:23
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：DataBaseEventListener
 * @类描述：数据同步事件监听
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:53:28
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:53:28
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class DataBaseEventListener implements IServer, Runnable {
	private static Logger log = LoggerFactory.getLogger(DataBaseEventListener.class);
	private JPABaseDAO baseDAO = null;
	/**
	 * @属性名称:pool
	 * @属性描述:工作线程池
	 * @since 1.0.0
	 */
	private ExecutorService pool;
	/**
	 * @属性名称:poolSize
	 * @属性描述:线程池大小
	 * @since 1.0.0
	 */
	private int poolSize;
	/**
	 * @属性名称:scheduler
	 * @属性描述:定时监听线程
	 * @since 1.0.0
	 */
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	/**
	 * @属性名称:startTime
	 * @属性描述:起始延迟
	 * @since 1.0.0
	 */
	private int startTime;
	/**
	 * @属性名称:each
	 * @属性描述:周期
	 * @since 1.0.0
	 */
	private int each;
	private DataSynchroData dataSynchrohelper;
	/**
	 * @属性名称:dealTimeOut
	 * @属性描述：事件超时时间
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
		log.info("同步监听退出");
	}

	public void start() {
		// TODO Auto-generated method stub
		log.info("同步事件监听启动");
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
			log.warn("处理线程池大小错误,启用默认设置,大小{}", poolSize);
		}
		pool = Executors.newFixedThreadPool(this.poolSize);
	}

	/**
	 * //更新事件处理列表，将正在处理的事件回写到事件通知表中，状态设为正在处理。
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
			// 查询事件通知表中事件状态为未处理的事件。
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			// 查找事件处理列表，将已处理的事件回写到事件通知表中，状态设为已处理。
			dataSynchrohelper.doEventDealStat(dealTimeOut);
			List<TxEvtNotice> txEvtNoticeList = baseDAO.findWithIndexParam("FROM TxEvtNotice where eventDealStat=?",
					DataSynchroData.EVENTDEALSTAT_WAIT);
			// 如果查询结果不为空，将事件分发事件信息到处理线程池。
			if (txEvtNoticeList != null && !txEvtNoticeList.isEmpty()) {
				for (TxEvtNotice txEvtNotice : txEvtNoticeList) {
					if (!dataSynchrohelper.hasEvtNotice(txEvtNotice.getEventId())) {
						log.info(">>>>分发同步事件[触发交易码{},触发系统{},同步客户号{}]", txEvtNotice.getTxCode(),
								txEvtNotice.getEventSysNo(), txEvtNotice.getCustNo());
						pool.execute(new SynchroPerformance(txEvtNotice));
						dataSynchrohelper.putEvtNotice(txEvtNotice);

//						lockEventDealStat(txEvtNotice);
					}
				}
			}

		} catch (Exception e) {
			log.error("同步事件监控业务错误", e);
			Subject evenSubject = EvenSubject.getInstance();
			/** 事件通知 */
			EcifData data = new EcifData();
			data.getStopWatch().start();
			data.setReqSeqNo("---");
			data.setStatus(ErrorCode.ERR_SYNCHRO_BIZLOGIC_ERROR.getCode(), "同步事件监控业务错误");
			data.getStopWatch().stop();
			evenSubject.eventNotify(data);
		}
	}

}
