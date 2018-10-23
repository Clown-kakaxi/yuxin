package com.yuchengtech.emp.ecif.syncmanage.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TX_SYNC_ERR database table.
 * 
 */
@Entity
@Table(name="TX_SYNC_ERR")
public class TxSyncErr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SYNC_TASK_ID")
	private long syncTaskId;

	@Column(name="CUST_ID")
	private BigDecimal custId;

	@Column(name="CUST_NO")
	private String custNo;

	@Column(name="DESC_SYS_NO")
	private String descSysNo;

	@Column(name="EVENT_ID")
	private BigDecimal eventId;

	@Column(name="KEY_INFO")
	private String keyInfo;

	@Column(name="SRC_SYS_NO")
	private String srcSysNo;

	@Column(name="SYNC_BATCH_ID")
	private BigDecimal syncBatchId;

	@Column(name="SYNC_CONF_ID")
	private BigDecimal syncConfId;

	@Column(name="SYNC_DEAL_INFO")
	private String syncDealInfo;

	@Column(name="SYNC_DEAL_RESULT")
	private String syncDealResult;

	@Column(name="SYNC_DEAL_TIME")
	private Timestamp syncDealTime;

    @Lob()
	@Column(name="SYNC_REQ_MSG")
	private String syncReqMsg;

    @Lob()
	@Column(name="SYNC_RES_MSG")
	private String syncResMsg;

	@Column(name="SYNC_TASK_STAT")
	private String syncTaskStat;

	@Column(name="TX_CODE")
	private String txCode;

	@Column(name="TX_FW_ID")
	private BigDecimal txFwId;

    public TxSyncErr() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public long getSyncTaskId() {
		return this.syncTaskId;
	}

	public void setSyncTaskId(long syncTaskId) {
		this.syncTaskId = syncTaskId;
	}

	public BigDecimal getCustId() {
		return this.custId;
	}

	public void setCustId(BigDecimal custId) {
		this.custId = custId;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getDescSysNo() {
		return this.descSysNo;
	}

	public void setDescSysNo(String descSysNo) {
		this.descSysNo = descSysNo;
	}

	public BigDecimal getEventId() {
		return this.eventId;
	}

	public void setEventId(BigDecimal eventId) {
		this.eventId = eventId;
	}

	public String getKeyInfo() {
		return this.keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}

	public String getSrcSysNo() {
		return this.srcSysNo;
	}

	public void setSrcSysNo(String srcSysNo) {
		this.srcSysNo = srcSysNo;
	}

	public BigDecimal getSyncBatchId() {
		return this.syncBatchId;
	}

	public void setSyncBatchId(BigDecimal syncBatchId) {
		this.syncBatchId = syncBatchId;
	}

	public BigDecimal getSyncConfId() {
		return this.syncConfId;
	}

	public void setSyncConfId(BigDecimal syncConfId) {
		this.syncConfId = syncConfId;
	}

	public String getSyncDealInfo() {
		return this.syncDealInfo;
	}

	public void setSyncDealInfo(String syncDealInfo) {
		this.syncDealInfo = syncDealInfo;
	}

	public String getSyncDealResult() {
		return this.syncDealResult;
	}

	public void setSyncDealResult(String syncDealResult) {
		this.syncDealResult = syncDealResult;
	}

	public Timestamp getSyncDealTime() {
		return this.syncDealTime;
	}

	public void setSyncDealTime(Timestamp syncDealTime) {
		this.syncDealTime = syncDealTime;
	}

	public String getSyncReqMsg() {
		return this.syncReqMsg;
	}

	public void setSyncReqMsg(String syncReqMsg) {
		this.syncReqMsg = syncReqMsg;
	}

	public String getSyncResMsg() {
		return this.syncResMsg;
	}

	public void setSyncResMsg(String syncResMsg) {
		this.syncResMsg = syncResMsg;
	}

	public String getSyncTaskStat() {
		return this.syncTaskStat;
	}

	public void setSyncTaskStat(String syncTaskStat) {
		this.syncTaskStat = syncTaskStat;
	}

	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public BigDecimal getTxFwId() {
		return this.txFwId;
	}

	public void setTxFwId(BigDecimal txFwId) {
		this.txFwId = txFwId;
	}

}