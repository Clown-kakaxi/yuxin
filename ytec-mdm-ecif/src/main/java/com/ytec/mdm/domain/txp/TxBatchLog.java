package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxBatchLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_BATCH_LOG")
public class TxBatchLog implements java.io.Serializable {

	// Fields

	private Long txLogId;
	private Date txDate;
	private String txFwId;
	private String txBatchStat;
	private Long readNum;
	private Long skipNum;
	private Long dealNum;
	private Long succNum;
	private Long failNum;
	private String reqAttachLoc;
	private String resAttachLoc;

	// Constructors

	/** default constructor */
	public TxBatchLog() {
	}

	/** minimal constructor */
	public TxBatchLog(Long txLogId) {
		this.txLogId = txLogId;
	}

	/** full constructor */
	public TxBatchLog(Long txLogId, Date txDate, String txFwId,
			String txBatchStat, Long readNum, Long skipNum,
			Long dealNum, Long succNum, Long failNum,
			String reqAttachLoc, String resAttachLoc) {
		this.txLogId = txLogId;
		this.txDate = txDate;
		this.txFwId = txFwId;
		this.txBatchStat = txBatchStat;
		this.readNum = readNum;
		this.skipNum = skipNum;
		this.dealNum = dealNum;
		this.succNum = succNum;
		this.failNum = failNum;
		this.reqAttachLoc = reqAttachLoc;
		this.resAttachLoc = resAttachLoc;
	}

	// Property accessors
	@Id
	@Column(name = "TX_LOG_ID", unique = true, nullable = false)
	public Long getTxLogId() {
		return this.txLogId;
	}

	public void setTxLogId(Long txLogId) {
		this.txLogId = txLogId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TX_DATE", length = 7)
	public Date getTxDate() {
		return this.txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	@Column(name = "TX_FW_ID", length = 20)
	public String getTxFwId() {
		return this.txFwId;
	}

	public void setTxFwId(String txFwId) {
		this.txFwId = txFwId;
	}

	@Column(name = "TX_BATCH_STAT", length = 20)
	public String getTxBatchStat() {
		return this.txBatchStat;
	}

	public void setTxBatchStat(String txBatchStat) {
		this.txBatchStat = txBatchStat;
	}

	@Column(name = "READ_NUM", precision = 22)
	public Long getReadNum() {
		return this.readNum;
	}

	public void setReadNum(Long readNum) {
		this.readNum = readNum;
	}

	@Column(name = "SKIP_NUM", precision = 22)
	public Long getSkipNum() {
		return this.skipNum;
	}

	public void setSkipNum(Long skipNum) {
		this.skipNum = skipNum;
	}

	@Column(name = "DEAL_NUM", precision = 22)
	public Long getDealNum() {
		return this.dealNum;
	}

	public void setDealNum(Long dealNum) {
		this.dealNum = dealNum;
	}

	@Column(name = "SUCC_NUM", precision = 22)
	public Long getSuccNum() {
		return this.succNum;
	}

	public void setSuccNum(Long succNum) {
		this.succNum = succNum;
	}

	@Column(name = "FAIL_NUM", precision = 22)
	public Long getFailNum() {
		return this.failNum;
	}

	public void setFailNum(Long failNum) {
		this.failNum = failNum;
	}

	@Column(name = "REQ_ATTACH_LOC")
	public String getReqAttachLoc() {
		return this.reqAttachLoc;
	}

	public void setReqAttachLoc(String reqAttachLoc) {
		this.reqAttachLoc = reqAttachLoc;
	}

	@Column(name = "RES_ATTACH_LOC")
	public String getResAttachLoc() {
		return this.resAttachLoc;
	}

	public void setResAttachLoc(String resAttachLoc) {
		this.resAttachLoc = resAttachLoc;
	}

}