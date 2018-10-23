package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxBatchDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_BATCH_DETAIL")
public class TxBatchDetail implements java.io.Serializable {

	// Fields

	private Long txBatchDealId;
	private Date txDate;
	private String txFwId;
	private String recNo;
	private String recText;
	private String dealResult;
	private String dealDesc;

	// Constructors

	/** default constructor */
	public TxBatchDetail() {
	}

	/** minimal constructor */
	public TxBatchDetail(Long txBatchDealId) {
		this.txBatchDealId = txBatchDealId;
	}

	/** full constructor */
	public TxBatchDetail(Long txBatchDealId, Date txDate, String txFwId,
			String recNo, String recText, String dealResult, String dealDesc) {
		this.txBatchDealId = txBatchDealId;
		this.txDate = txDate;
		this.txFwId = txFwId;
		this.recNo = recNo;
		this.recText = recText;
		this.dealResult = dealResult;
		this.dealDesc = dealDesc;
	}

	// Property accessors
	@Id
	@Column(name = "TX_BATCH_DEAL_ID", unique = true, nullable = false)
	public Long getTxBatchDealId() {
		return this.txBatchDealId;
	}

	public void setTxBatchDealId(Long txBatchDealId) {
		this.txBatchDealId = txBatchDealId;
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

	@Column(name = "REC_NO", length = 64)
	public String getRecNo() {
		return this.recNo;
	}

	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}

	@Column(name = "REC_TEXT")
	public String getRecText() {
		return this.recText;
	}

	public void setRecText(String recText) {
		this.recText = recText;
	}

	@Column(name = "DEAL_RESULT", length = 20)
	public String getDealResult() {
		return this.dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	@Column(name = "DEAL_DESC")
	public String getDealDesc() {
		return this.dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

}