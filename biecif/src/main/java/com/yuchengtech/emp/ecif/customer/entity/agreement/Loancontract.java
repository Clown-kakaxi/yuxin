package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the LOANCONTRACT database table.
 * 
 */
@Entity
@Table(name="LOANCONTRACT")
public class Loancontract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="PROD_CODE", length=32)
	private String prodCode;

	@Column(name="BRANCH_NO", length=9)
	private String branchNo;

	@Column(name="CONTR_AMT", precision=17, scale=2)
	private BigDecimal contrAmt;

	@Column(name="CONTR_CURR", length=20)
	private String contrCurr;

	@Column(name="CONTR_EFFECTIVE_DATE", length=20)
	private String contrEffectiveDate;

	@Column(name="CONTR_END_DATE", length=20)
	private String contrEndDate;

	@Column(name="CONTR_KIND", length=20)
	private String contrKind;

	@Column(name="CONTR_LIMIT", precision=10)
	private BigDecimal contrLimit;

	@Column(name="CONTR_NO", length=32)
	private String contrNo;

	@Column(name="CONTR_SIGN_DATE", length=20)
	private String contrSignDate;

	@Column(name="CONTR_STAT", length=20)
	private String contrStat;

	@Column(name="CONTR_TYPE", length=20)
	private String contrType;

	@Column(name="CUST_MANAGER", length=20)
	private String custManager;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="OPER_DATE", length=20)
	private String operDate;

	@Column(name="TELLER_NO", length=20)
	private String tellerNo;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Loancontract() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public BigDecimal getContrAmt() {
		return this.contrAmt;
	}

	public void setContrAmt(BigDecimal contrAmt) {
		this.contrAmt = contrAmt;
	}

	public String getContrCurr() {
		return this.contrCurr;
	}

	public void setContrCurr(String contrCurr) {
		this.contrCurr = contrCurr;
	}

	public String getContrEffectiveDate() {
		return this.contrEffectiveDate;
	}

	public void setContrEffectiveDate(String contrEffectiveDate) {
		this.contrEffectiveDate = contrEffectiveDate;
	}

	public String getContrEndDate() {
		return this.contrEndDate;
	}

	public void setContrEndDate(String contrEndDate) {
		this.contrEndDate = contrEndDate;
	}

	public String getContrKind() {
		return this.contrKind;
	}

	public void setContrKind(String contrKind) {
		this.contrKind = contrKind;
	}

	public BigDecimal getContrLimit() {
		return this.contrLimit;
	}

	public void setContrLimit(BigDecimal contrLimit) {
		this.contrLimit = contrLimit;
	}

	public String getContrNo() {
		return this.contrNo;
	}

	public void setContrNo(String contrNo) {
		this.contrNo = contrNo;
	}

	public String getContrSignDate() {
		return this.contrSignDate;
	}

	public void setContrSignDate(String contrSignDate) {
		this.contrSignDate = contrSignDate;
	}

	public String getContrStat() {
		return this.contrStat;
	}

	public void setContrStat(String contrStat) {
		this.contrStat = contrStat;
	}

	public String getContrType() {
		return this.contrType;
	}

	public void setContrType(String contrType) {
		this.contrType = contrType;
	}

	public String getCustManager() {
		return this.custManager;
	}

	public void setCustManager(String custManager) {
		this.custManager = custManager;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getOperDate() {
		return this.operDate;
	}

	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}
	
	public String getTellerNo() {
		return this.tellerNo;
	}

	public void setTellerNo(String tellerNo) {
		this.tellerNo = tellerNo;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}