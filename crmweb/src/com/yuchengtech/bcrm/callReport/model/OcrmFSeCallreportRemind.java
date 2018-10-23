package com.yuchengtech.bcrm.callReport.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_SE_CALLREPORT_REMIND database table.
 * call report(大额流失)
 */
@Entity
@Table(name="OCRM_F_SE_CALLREPORT_REMIND")
public class OcrmFSeCallreportRemind implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="ID",unique=true, nullable=false)
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="BACKFLOW_DATE")
	private Date backflowDate;

	@Column(name="CALL_ID")
	private BigDecimal callId;
	
	@Column(name="CUST_ID")
	private String custId;
	
    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="RECEIVE_BANK")
	private String receiveBank;

	private String receiver;

	@Column(name="REMIND_AMOUNT")
	private BigDecimal remindAmount;

	@Column(name="REMIND_CASE_STAGE")
	private String remindCaseStage;

    @Temporal( TemporalType.DATE)
	@Column(name="REMIND_DATE")
	private Date remindDate;

	@Column(name="REMIND_REASON")
	private String remindReason;

	@Column(name="REMIND_REMARK")
	private String remindRemark;

	@Column(name="BACKFLOW_AMOUNT")
	private String backflowAmount;
	
    public OcrmFSeCallreportRemind() {
    }

	public String getBackflowAmount() {
		return backflowAmount;
	}

	public void setBackflowAmount(String backflowAmount) {
		this.backflowAmount = backflowAmount;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBackflowDate() {
		return this.backflowDate;
	}

	public void setBackflowDate(Date backflowDate) {
		this.backflowDate = backflowDate;
	}

	public BigDecimal getCallId() {
		return this.callId;
	}

	public void setCallId(BigDecimal callId) {
		this.callId = callId;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getReceiveBank() {
		return this.receiveBank;
	}

	public void setReceiveBank(String receiveBank) {
		this.receiveBank = receiveBank;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public BigDecimal getRemindAmount() {
		return this.remindAmount;
	}

	public void setRemindAmount(BigDecimal remindAmount) {
		this.remindAmount = remindAmount;
	}

	public String getRemindCaseStage() {
		return this.remindCaseStage;
	}

	public void setRemindCaseStage(String remindCaseStage) {
		this.remindCaseStage = remindCaseStage;
	}

	public Date getRemindDate() {
		return this.remindDate;
	}

	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}

	public String getRemindReason() {
		return this.remindReason;
	}

	public void setRemindReason(String remindReason) {
		this.remindReason = remindReason;
	}

	public String getRemindRemark() {
		return this.remindRemark;
	}

	public void setRemindRemark(String remindRemark) {
		this.remindRemark = remindRemark;
	}

}