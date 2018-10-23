/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.sync.ptsync
 * @�ļ�����SynchroPerformance.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:00:46
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�SynchroPerformance
 * @��������ͬ��ִ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:00:47
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:00:47
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class SynchroPerformance implements Runnable {
	private static Logger log = LoggerFactory.getLogger(SynchroPerformance.class);
	private static String ISRETRY_TRUE = "1";
	/**
	 * @��������:txEvtNotice
	 * @��������:�¼�֪ͨ����
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
		// ��������Ϣ�л�ȡ�ý���ͬ�����ĸ�ϵͳ��ͬ����ʽ��ͬ�������ࡣ
		log.info(">>>>����ͬ���¼���ʼ[����������{},����ϵͳ{},ͬ���ͻ���{}](START)", txEvtNotice.getTxCode(), txEvtNotice.getEventSysNo(), txEvtNotice.getCustNo());
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
					// ��ȡͬ���ͻ���
					IClient client = null;
					String syncDealMethod = txSyncConf.getSyncDealMethod();
					log.info("<<<<<syncDealMethod:{}>>>>>", syncDealMethod);
					String destSysNo = txSyncConf.getDestSysNo();
					String clientName = DataSynchroData.getClientByType(syncDealMethod);
					log.info(">>>>ͬ�����ݵ�ϵͳ[{}](BEGIN)", destSysNo);
					if (StringUtil.isEmpty(clientName)) {
						log.error("��ȡͬ�����Ϳͻ�������{}ʧ��", syncDealMethod);
						txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CLIENT_ERROR.getCode());
						txEvtNotice.setEventDealInfo(String.format("ͨ��ͬ�����Ϳͻ�������%s��ȡ�ͻ���ʧ��", syncDealMethod));
					} else {
						client = getSyncClient(clientName, destSysNo);
					}
					if (client != null) {
						// ���ô�������װ���ġ�
						AbsSynchroExecutor executor = null;
						// client.init(arg);
						try {
							executor = (AbsSynchroExecutor) SpringContextUtils.getBean(className);
						} catch (Exception e) {
							log.error("��ȡͬ��������ʧ��", e);
						}
						if (executor != null) {
							
							//TODO
							lockEnv();
							while (maxRetry-- >= 0) {
								if (executor.execute(txSyncConf, txEvtNotice)) {// ����ͬ���ͻ��˷����ġ�
									// ����
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
										if (executor.executeResult()) {// ���ô�����������
											syncSuccess = true;
										} else {
											syncSuccess = false;
											txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_RESPSONSE_ERROR.getCode());
											txEvtNotice.setEventDealInfo(String.format("����ͬ��������%s��Ӧ���Ĵ���ʧ��", className));
										}
									}
								} else {
									syncSuccess = false;
									if (txEvtNotice.getEventDealInfo() == null) {
										txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
										txEvtNotice.setEventDealInfo(String.format("����ͬ��������%s������װ����ʧ��", className));
									}
								}
								// ��¼����ͬ����־��
								try {
									dataSynchrohelper.logHelper(txEvtNotice, txSyncConf, executor, syncSuccess);
								} catch (Exception e) {
									log.error("ͬ����־�쳣", e);
								}
								if (syncSuccess) {
									break;
								} else {
									if (!ISRETRY_TRUE.equals(IsRetry)) {// ���ͬ�����ɹ�������ͬ���쳣���ж���ͬ���Ƿ���Ҫ�ӷ����Ƿ񳬹��ط��������
										break;
									}
									log.info("ͬ�����ɹ�,�ж���Ҫ�ط�");
									//TODO
//									Thread.currentThread().sleep(3000);
								}
							}
						} else {
							syncSuccess = false;
							txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CLASS_ERROR.getCode());
							txEvtNotice.setEventDealInfo(String.format("��ȡͬ��������%sʧ��", className));
							try {
								dataSynchrohelper.logHelper(txEvtNotice, txSyncConf, executor, syncSuccess);
							} catch (Exception e) {
								log.error("ͬ����־�쳣", e);
							}
						}
					} else {
						syncSuccess = false;
						txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CLIENT_ERROR.getCode());
						txEvtNotice.setEventDealInfo(String.format("��ȡ�ͻ���%sʧ��", clientName));
						try {
							dataSynchrohelper.logHelper(txEvtNotice, txSyncConf, null, syncSuccess);
						} catch (Exception e) {
							log.error("ͬ����־�쳣", e);
						}
					}
					// ////һ���¼��漰�����ϵͳͬ��,�����һ��ʧ�ܣ�����Ϊ���¼�ͬ��ʧ�ܡ�
					if (!syncSuccess) {
						eventDealSuccess = syncSuccess;
					}
					log.info(">>>>ͬ�����ݵ�ϵͳ[{}](END)", destSysNo);
				}
				// �޸��¼�Ϊ�Ѵ���
				updataEnv(eventDealSuccess);
			} else {
				txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CFG_ERROR.getCode());
				txEvtNotice.setEventDealInfo("û�иý��׵�ͬ��������Ϣ");
				// �޸��¼�Ϊ�Ѵ���
				updataEnv(false);
			}
		} catch (Exception e) {
			log.error("ͬ�������쳣", e);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_BIZLOGIC_ERROR.getCode());
			txEvtNotice.setEventDealInfo("ͬ�������쳣");
			updataEnv(false);
		}
		/***
		 * �¼�֪ͨ
		 */
		if (!eventDealSuccess) {
			EcifData data = new EcifData();
			data.getStopWatch().start();
			data.setReqSeqNo(txEvtNotice.getEventId().toString());
			data.setStatus(txEvtNotice.getEventDealResult(), txEvtNotice.getEventDealInfo());
			data.getStopWatch().stop();
			evenSubject.eventNotify(data);
		}
		log.info(">>>>����ͬ���¼�����(DONE)");

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
			log.error("��������¼�����", e);
		}

	}
	
	private void lockEnv() {
		txEvtNotice.setEventDealStat(DataSynchroData.EVENTDEALSTAT_RUN);
		try {
			DataSynchroData dataSynchrohelper = (DataSynchroData) SpringContextUtils.getBean("dataSynchroData");
			dataSynchrohelper.doEventDealStatRunning(txEvtNotice);
		} catch (Exception e) {
			log.error("�����¼�״̬����,�޷��޸�Ϊ���ڴ���[{}]\n{}",DataSynchroData.EVENTDEALSTAT_RUN, e);
		}

	}

	private IClient getSyncClient(String clientName, String destSysNo) {
		IClient client = null;
		try {
			client = (IClient) SpringContextUtils.getBean(clientName);
		} catch (Exception e) {
			log.error("��ȡͬ�����Ϳͻ���ʧ��", e);
		}
		if (client == null) {
			log.error("��ȡͬ�����Ϳͻ���{}ʧ��", clientName);
			return null;
		} else {
			Map clientArg = DataSynchroConfiger.getClientArg(clientName, destSysNo);
			if (clientArg == null) {
				log.error("��ȡͬ�����Ϳͻ���{}����ʧ��", clientName);
				return null;
			} else {
				try {
//					clientArg.put("destSys", destSysNo);
					client.init(clientArg);
				} catch (Exception e) {
					log.error("��ʼ��ͬ�����Ϳͻ���{}����ʧ��", clientName);
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
