/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.sync.ptsync
 * @文件名：SynchroPerformance.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:00:46
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.sync.ptsync;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.integration.sync.listen.DataSynchroData;
import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;
import com.ytec.mdm.interfaces.common.even.EvenSubject;
import com.ytec.mdm.interfaces.common.even.Subject;
import com.ytec.mdm.server.common.DataSynchroConfiger;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：SynchroPerformance
 * @类描述：同步执行类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:00:47
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:00:47
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class SynchroPerformance implements Runnable {
	private static Logger log = LoggerFactory.getLogger(SynchroPerformance.class);
	private static String ISRETRY_TRUE = "1";
	/**
	 * @属性名称:txEvtNotice
	 * @属性描述:事件通知对象
	 * @since 1.0.0
	 */
	private TxEvtNotice txEvtNotice;
	private boolean eventDealSuccess = true;
	private Subject evenSubject = EvenSubject.getInstance();

	public SynchroPerformance() {
		// TODO Auto-generated constructor stub
	}

	public SynchroPerformance(TxEvtNotice txEvtNotice) {
		// TODO Auto-generated constructor stub
		this.txEvtNotice = txEvtNotice;
	}

	public void run() {
		// TODO Auto-generated method stub
		// 从配置信息中获取该交易同步到哪个系统，同步方式，同步处理类。
		log.info(">>>>处理同步事件开始[触发交易码{},触发系统{},同步客户号{}](START)", txEvtNotice.getTxCode(), txEvtNotice.getEventSysNo(), txEvtNotice.getCustNo());
		try {
			List<TxSyncConf> txSyncConfList = null;
			String IsRetry = null;
			long maxRetry;
			boolean syncSuccess = false;
			DataSynchroData dataSynchrohelper = (DataSynchroData) SpringContextUtils.getBean("dataSynchroData");
			if ((txSyncConfList = DataSynchroData.getTxSyncConf(txEvtNotice.getTxCode(), txEvtNotice.getEventSysNo())) != null) {
				for (TxSyncConf txSyncConf : txSyncConfList) {
					syncSuccess = true;
					String className = txSyncConf.getSyncDealClass();
					IsRetry = txSyncConf.getIsRetry();
					maxRetry = txSyncConf.getMaxRetry();
					if (maxRetry < 1) {
						maxRetry = 1;
					}
					// 获取同步客户端
					IClient client = null;
					String syncDealMethod = txSyncConf.getSyncDealMethod();
					log.info("<<<<<syncDealMethod:{}>>>>>", syncDealMethod);
					String destSysNo = txSyncConf.getDestSysNo();
					String clientName = DataSynchroData.getClientByType(syncDealMethod);
					log.info(">>>>同步数据到系统[{}](BEGIN)", destSysNo);
					if (StringUtil.isEmpty(clientName)) {
						log.error("获取同步发送客户端类型{}失败", syncDealMethod);
						txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CLIENT_ERROR.getCode());
						txEvtNotice.setEventDealInfo(String.format("通过同步发送客户端类型%s获取客户端失败", syncDealMethod));
					} else {
						client = getSyncClient(clientName, destSysNo);
					}
					if (client != null) {
						// 调用处理类组装报文。
						AbsSynchroExecutor executor = null;
						// client.init(arg);
						try {
							executor = (AbsSynchroExecutor) SpringContextUtils.getBean(className);
						} catch (Exception e) {
							log.error("获取同步处理类失败", e);
						}
						if (executor != null) {
							
							//TODO
							lockEnv();
							while (maxRetry-- >= 0) {
								if (executor.execute(txSyncConf, txEvtNotice)) {// 调用同步客户端发报文。
									// 发送
									String message = executor.getSynchroRequestMsg();
									ClientResponse clientResponse = client.sendMsg(message);
									
									if (!clientResponse.isSuccess()) {
										syncSuccess = false;
										txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_SEND_ERROR.getCode());
										if (clientResponse.isTimeout()) {
											txEvtNotice.setEventDealInfo(clientResponse.getResponseMsg());
										} else {
											txEvtNotice.setEventDealInfo(clientResponse.getResponseMsg());
										}
									} else {
										executor.setSynchroResponseMsg(clientResponse.getResponseMsg());
										if (executor.executeResult()) {// 调用处理类结果处理。
											syncSuccess = true;
										} else {
											syncSuccess = false;
											txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_RESPSONSE_ERROR.getCode());
											txEvtNotice.setEventDealInfo(String.format("调用同步处理类%s响应报文处理失败", className));
										}
									}
								} else {
									syncSuccess = false;
									if (txEvtNotice.getEventDealInfo() == null) {
										txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
										txEvtNotice.setEventDealInfo(String.format("调用同步处理类%s报文组装报文失败", className));
									}
								}
								// 记录数据同步日志表。
								try {
									dataSynchrohelper.logHelper(txEvtNotice, txSyncConf, executor, syncSuccess);
								} catch (Exception e) {
									log.error("同步日志异常", e);
								}
								if (syncSuccess) {
									break;
								} else {
									if (!ISRETRY_TRUE.equals(IsRetry)) {// 如果同步不成功，数据同步异常表，判定该同步是否需要从发和是否超过重发最大限制
										break;
									}
									log.info("同步不成功,判定需要重发");
									//TODO
//									Thread.currentThread().sleep(3000);
								}
							}
						} else {
							syncSuccess = false;
							txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CLASS_ERROR.getCode());
							txEvtNotice.setEventDealInfo(String.format("获取同步处理类%s失败", className));
							try {
								dataSynchrohelper.logHelper(txEvtNotice, txSyncConf, executor, syncSuccess);
							} catch (Exception e) {
								log.error("同步日志异常", e);
							}
						}
					} else {
						syncSuccess = false;
						txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CLIENT_ERROR.getCode());
						txEvtNotice.setEventDealInfo(String.format("获取客户端%s失败", clientName));
						try {
							dataSynchrohelper.logHelper(txEvtNotice, txSyncConf, null, syncSuccess);
						} catch (Exception e) {
							log.error("同步日志异常", e);
						}
					}
					// ////一个事件涉及到多个系统同步,如果有一个失败，则认为该事件同步失败。
					if (!syncSuccess) {
						eventDealSuccess = syncSuccess;
					}
					log.info(">>>>同步数据到系统[{}](END)", destSysNo);
				}
				// 修改事件为已处理。
				updataEnv(eventDealSuccess);
			} else {
				txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CFG_ERROR.getCode());
				txEvtNotice.setEventDealInfo("没有该交易的同步配置信息");
				// 修改事件为已处理。
				updataEnv(false);
			}
		} catch (Exception e) {
			log.error("同步处理异常", e);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_BIZLOGIC_ERROR.getCode());
			txEvtNotice.setEventDealInfo("同步处理异常");
			updataEnv(false);
		}
		/***
		 * 事件通知
		 */
		if (!eventDealSuccess) {
			EcifData data = new EcifData();
			data.getStopWatch().start();
			data.setReqSeqNo(txEvtNotice.getEventId().toString());
			data.setStatus(txEvtNotice.getEventDealResult(), txEvtNotice.getEventDealInfo());
			data.getStopWatch().stop();
			evenSubject.eventNotify(data);
		}
		log.info(">>>>处理同步事件结束(DONE)");

	}

	private void updataEnv(boolean flag) {
		txEvtNotice.setEventDealStat(DataSynchroData.EVENTDEALSTAT_OVER);
		if (flag) {
			txEvtNotice.setEventDealResult(ErrorCode.SUCCESS.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.SUCCESS.getChDesc());
		}
		txEvtNotice.setEventDealTime(new Timestamp(System.currentTimeMillis()));
		try {
			DataSynchroData dataSynchrohelper = (DataSynchroData) SpringContextUtils.getBean("dataSynchroData");
			dataSynchrohelper.doEventDealStatOver(txEvtNotice);
		} catch (Exception e) {
			log.error("处理完成事件错误", e);
		}

	}
	
	private void lockEnv() {
		txEvtNotice.setEventDealStat(DataSynchroData.EVENTDEALSTAT_RUN);
		try {
			DataSynchroData dataSynchrohelper = (DataSynchroData) SpringContextUtils.getBean("dataSynchroData");
			dataSynchrohelper.doEventDealStatRunning(txEvtNotice);
		} catch (Exception e) {
			log.error("更新事件状态错误,无法修改为正在处理[{}]\n{}",DataSynchroData.EVENTDEALSTAT_RUN, e);
		}

	}

	private IClient getSyncClient(String clientName, String destSysNo) {
		IClient client = null;
		try {
			client = (IClient) SpringContextUtils.getBean(clientName);
		} catch (Exception e) {
			log.error("获取同步发送客户端失败", e);
		}
		if (client == null) {
			log.error("获取同步发送客户端{}失败", clientName);
			return null;
		} else {
			Map clientArg = DataSynchroConfiger.getClientArg(clientName, destSysNo);
			if (clientArg == null) {
				log.error("获取同步发送客户端{}参数失败", clientName);
				return null;
			} else {
				try {
//					clientArg.put("destSys", destSysNo);
					client.init(clientArg);
				} catch (Exception e) {
					log.error("初始化同步发送客户端{}参数失败", clientName);
					return null;
				}
				return client;
			}
		}
	}

	public TxEvtNotice getTxEvtNotice() {
		return txEvtNotice;
	}

	public void setTxEvtNotice(TxEvtNotice txEvtNotice) {
		this.txEvtNotice = txEvtNotice;
	}

}
