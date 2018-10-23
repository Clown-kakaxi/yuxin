package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxSyncErr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_SYNC_ERR")
public class TxSyncErr implements java.io.Serializable {

	// Fields

	private Long syncTaskId;
	private Long syncConfId;
	private String txCode;
	private String srcSysNo;
	private String descSysNo;
	private Long eventId;
	private Long txFwId;
	private Long custId;
	private String custNo;
	private String keyInfo;
	private Long syncBatchId;
	private String syncTaskStat;
	private Timestamp syncDealTime;
	private String syncDealResult;
	private String syncDealInfo;
	private String syncReqMsg;
	private String syncResMsg;

	// Constructors

	/** default constructor */
	public TxSyncErr() {
	}

	/** full constructor */
	public TxSyncErr(Long syncConfId, String txCode, String srcSysNo,
			String descSysNo, Long eventId, Long txFwId,
			Long custId, String custNo, String keyInfo,
			Long syncBatchId, String syncTaskStat,
			Timestamp syncDealTime, String syncDealResult, String syncDealInfo,
			String syncReqMsg, String syncResMsg) {
		this.syncConfId = syncConfId;
		this.txCode = txCode;
		this.srcSysNo = srcSysNo;
		this.descSysNo = descSysNo;
		this.eventId = eventId;
		this.txFwId = txFwId;
		this.custId = custId;
		this.custNo = custNo;
		this.keyInfo = keyInfo;
		this.syncBatchId = syncBatchId;
		this.syncTaskStat = syncTaskStat;
		this.syncDealTime = syncDealTime;
		this.syncDealResult = syncDealResult;
		this.syncDealInfo = syncDealInfo;
		this.syncReqMsg = syncReqMsg;
		this.syncResMsg = syncResMsg;
	}

	// Property accessors
	@Id
		@Column(name = "SYNC_TASK_ID", unique = true, nullable = false)
	public Long getSyncTaskId() {
		return this.syncTaskId;
	}

	public void setSyncTaskId(Long syncTaskId) {
		this.syncTaskId = syncTaskId;
	}

	@Column(name = "SYNC_CONF_ID")
	public Long getSyncConfId() {
		return this.syncConfId;
	}

	public void setSyncConfId(Long syncConfId) {
		this.syncConfId = syncConfId;
	}

	@Column(name = "TX_CODE", length = 32)
	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	@Column(name = "SRC_SYS_NO", length = 20)
	public String getSrcSysNo() {
		return this.srcSysNo;
	}

	public void setSrcSysNo(String srcSysNo) {
		this.srcSysNo = srcSysNo;
	}

	@Column(name = "DESC_SYS_NO", length = 20)
	public String getDescSysNo() {
		return this.descSysNo;
	}

	public void setDescSysNo(String descSysNo) {
		this.descSysNo = descSysNo;
	}

	@Column(name = "EVENT_ID")
	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Column(name = "TX_FW_ID")
	public Long getTxFwId() {
		return this.txFwId;
	}

	public void setTxFwId(Long txFwId) {
		this.txFwId = txFwId;
	}

	@Column(name = "CUST_ID")
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	@Column(name = "CUST_NO", length = 32)
	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	@Column(name = "KEY_INFO")
	public String getKeyInfo() {
		return this.keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}

	@Column(name = "SYNC_BATCH_ID")
	public Long getSyncBatchId() {
		return this.syncBatchId;
	}

	public void setSyncBatchId(Long syncBatchId) {
		this.syncBatchId = syncBatchId;
	}

	@Column(name = "SYNC_TASK_STAT", length = 20)
	public String getSyncTaskStat() {
		return this.syncTaskStat;
	}

	public void setSyncTaskStat(String syncTaskStat) {
		this.syncTaskStat = syncTaskStat;
	}

	@Column(name = "SYNC_DEAL_TIME", length = 11)
	public Timestamp getSyncDealTime() {
		return this.syncDealTime;
	}

	public void setSyncDealTime(Timestamp syncDealTime) {
		this.syncDealTime = syncDealTime;
	}

	@Column(name = "SYNC_DEAL_RESULT", length = 20)
	public String getSyncDealResult() {
		return this.syncDealResult;
	}

	public void setSyncDealResult(String syncDealResult) {
		this.syncDealResult = syncDealResult;
	}

	@Column(name = "SYNC_DEAL_INFO")
	public String getSyncDealInfo() {
		return this.syncDealInfo;
	}

	public void setSyncDealInfo(String syncDealInfo) {
		this.syncDealInfo = syncDealInfo;
	}

	@Column(name = "SYNC_REQ_MSG")
	public String getSyncReqMsg() {
		return this.syncReqMsg;
	}

	public void setSyncReqMsg(String syncReqMsg) {
		this.syncReqMsg = syncReqMsg;
	}

	@Column(name = "SYNC_RES_MSG")
	public String getSyncResMsg() {
		return this.syncResMsg;
	}

	public void setSyncResMsg(String syncResMsg) {
		this.syncResMsg = syncResMsg;
	}

}