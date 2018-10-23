/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.sync.ptsync
 * @�ļ�����AbsSynchroExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:58:41
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.sync.ptsync;

import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.domain.txp.TxSyncErr;
import com.ytec.mdm.domain.txp.TxSyncLog;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�AbsSynchroExecutor
 * @��������ͬ������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:58:42
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:58:42
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public abstract class AbsSynchroExecutor implements ISynchroExecute {
	/**
	 * @��������:synchroRequestMsg
	 * @��������:������
	 * @since 1.0.0
	 */
	protected String synchroRequestMsg;
	/**
	 * @��������:synchroResponseMsg
	 * @��������:��Ӧ����
	 * @since 1.0.0
	 */
	protected String synchroResponseMsg;

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.integration.sync.ptsync.ISynchroExecute#execute(com.ytec.mdm.domain.txp.TxSyncConf, com.ytec.mdm.domain.txp.TxEvtNotice)
	 */
	public abstract boolean execute(TxSyncConf txSyncConf, TxEvtNotice txEvtNotice);

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.integration.sync.ptsync.ISynchroExecute#executeResult()
	 */
	public abstract boolean executeResult();

	public String getSynchroRequestMsg() {
		return synchroRequestMsg;
	}

	/**
	 * @��������:setSynchroRequestMsg
	 * @��������:������Ӧ����
	 * @�����뷵��˵��:
	 * @param synchroRequestMsg
	 * @�㷨����:
	 */
	public void setSynchroRequestMsg(String synchroRequestMsg) {
		this.synchroRequestMsg = synchroRequestMsg;
	}

	public String getSynchroResponseMsg() {
		return synchroResponseMsg;
	}

	public void setSynchroResponseMsg(String synchroResponseMsg) {
		this.synchroResponseMsg = synchroResponseMsg;
	}

	// ����TX_SYNC_LOG/TX_SYNC_ERR��������ģ��,ͨ��JPAʵ����,��ͬ���������Ĳ�����Ϣ���,���ڼ�¼ͬ����־
	protected TxSyncLog syncLog;
	protected TxSyncErr syncErr;

	public TxSyncLog getSyncLog() {
		return syncLog;
	}

	public TxSyncErr getSyncErr() {
		return syncErr;
	}

}
