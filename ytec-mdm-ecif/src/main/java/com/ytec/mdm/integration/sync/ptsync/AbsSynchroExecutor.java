/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.sync.ptsync
 * @文件名：AbsSynchroExecutor.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:58:41
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.sync.ptsync;

import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.domain.txp.TxSyncErr;
import com.ytec.mdm.domain.txp.TxSyncLog;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：AbsSynchroExecutor
 * @类描述：同步处理
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:58:42
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:58:42
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public abstract class AbsSynchroExecutor implements ISynchroExecute {
	/**
	 * @属性名称:synchroRequestMsg
	 * @属性描述:请求报文
	 * @since 1.0.0
	 */
	protected String synchroRequestMsg;
	/**
	 * @属性名称:synchroResponseMsg
	 * @属性描述:响应报文
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
	 * @函数名称:setSynchroRequestMsg
	 * @函数描述:设置响应报文
	 * @参数与返回说明:
	 * @param synchroRequestMsg
	 * @算法描述:
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

	// 借用TX_SYNC_LOG/TX_SYNC_ERR两个数据模型,通过JPA实体类,将同步处理结果的部分信息存放,用于记录同步日志
	protected TxSyncLog syncLog;
	protected TxSyncErr syncErr;

	public TxSyncLog getSyncLog() {
		return syncLog;
	}

	public TxSyncErr getSyncErr() {
		return syncErr;
	}

}
