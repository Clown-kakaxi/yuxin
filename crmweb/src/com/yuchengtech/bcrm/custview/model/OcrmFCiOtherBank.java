package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CI_OTHER_BANK database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_OTHER_BANK")
public class OcrmFCiOtherBank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_OTHER_BANK_MXTID_GENERATOR", sequenceName="ID_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_OTHER_BANK_MXTID_GENERATOR")
	@Column(name="MXTID")
	private Long mxtid;

	@Column(name="BAIL_VAL")
	private BigDecimal bailVal;

	@Column(name="CRED_AMT")
	private BigDecimal credAmt;

	@Column(name="CRED_LIMIT")
	private String credLimit;

    @Temporal( TemporalType.DATE)
	@Column(name="CRM_DT")
	private Date crmDt;

	@Column(name="CURRENT_VAL")
	private BigDecimal currentVal;

	@Column(name="CUST_ID")
	private String custId;

	private String hzyears;

    @Temporal( TemporalType.DATE)
	@Column(name="INPUT_DT")
	private Date inputDt;

	@Column(name="INSTN_NAME")
	private String instnName;

	@Column(name="IS_BASIC_BANK")
	private String isBasicBank;

	@Column(name="LON_VAL")
	private BigDecimal lonVal;

	@Column(name="PERIODCIAL_VAL")
	private BigDecimal periodcialVal;

	@Column(name="PRD_USE")
	private String prdUse;

	private String remark;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDT_DT")
	private Date updtDt;

	private String userid;

	private String username;

    public OcrmFCiOtherBank() {
    }

	public Long getMxtid() {
		return this.mxtid;
	}

	public void setMxtid(Long mxtid) {
		this.mxtid = mxtid;
	}

	public BigDecimal getBailVal() {
		return this.bailVal;
	}

	public void setBailVal(BigDecimal bailVal) {
		this.bailVal = bailVal;
	}

	public BigDecimal getCredAmt() {
		return this.credAmt;
	}

	public void setCredAmt(BigDecimal credAmt) {
		this.credAmt = credAmt;
	}

	public String getCredLimit() {
		return this.credLimit;
	}

	public void setCredLimit(String credLimit) {
		this.credLimit = credLimit;
	}

	public Date getCrmDt() {
		return this.crmDt;
	}

	public void setCrmDt(Date crmDt) {
		this.crmDt = crmDt;
	}

	public BigDecimal getCurrentVal() {
		return this.currentVal;
	}

	public void setCurrentVal(BigDecimal currentVal) {
		this.currentVal = currentVal;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getHzyears() {
		return this.hzyears;
	}

	public void setHzyears(String hzyears) {
		this.hzyears = hzyears;
	}

	public Date getInputDt() {
		return this.inputDt;
	}

	public void setInputDt(Date inputDt) {
		this.inputDt = inputDt;
	}

	public String getInstnName() {
		return this.instnName;
	}

	public void setInstnName(String instnName) {
		this.instnName = instnName;
	}

	public String getIsBasicBank() {
		return this.isBasicBank;
	}

	public void setIsBasicBank(String isBasicBank) {
		this.isBasicBank = isBasicBank;
	}

	public BigDecimal getLonVal() {
		return this.lonVal;
	}

	public void setLonVal(BigDecimal lonVal) {
		this.lonVal = lonVal;
	}

	public BigDecimal getPeriodcialVal() {
		return this.periodcialVal;
	}

	public void setPeriodcialVal(BigDecimal periodcialVal) {
		this.periodcialVal = periodcialVal;
	}

	public String getPrdUse() {
		return this.prdUse;
	}

	public void setPrdUse(String prdUse) {
		this.prdUse = prdUse;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdtDt() {
		return this.updtDt;
	}

	public void setUpdtDt(Date updtDt) {
		this.updtDt = updtDt;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}