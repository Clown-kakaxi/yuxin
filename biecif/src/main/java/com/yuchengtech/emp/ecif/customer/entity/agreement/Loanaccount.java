package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the LOANACCOUNT database table.
 * 
 */
@Entity
@Table(name="LOANACCOUNT")
public class Loanaccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="ACCT_BAL", precision=17, scale=2)
	private BigDecimal acctBal;

	@Column(name="ACCT_CURR", length=20)
	private String acctCurr;

	@Column(name="ACCT_NO", length=32)
	private String acctNo;

	@Column(name="ACCT_STAT", length=20)
	private String acctStat;

	@Column(name="ACCT_TYPE", length=20)
	private String acctType;

	@Column(name="CANCEL_ACCT_BRANCH_NO", length=20)
	private String cancelAcctBranchNo;

	@Column(name="DUEBILL_NO", length=20)
	private String duebillNo;

	@Column(name="CANCEL_ACCT_DATE", length=20)
	private String cancelAcctDate;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="LEVEL_12_CLASSIFY", length=20)
	private String level12Classify;

	@Column(name="LEVEL_5_CLASSIFY", length=20)
	private String level5Classify;

	@Column(name="OPEN_ACCT_BRANCH_NO", length=9)
	private String openAcctBranchNo;

	@Column(name="OPEN_ACCT_DATE", length=20)
	private String openAcctDate;

	@Column(name="PROD_CODE", length=32)
	private String prodCode;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Loanaccount() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public BigDecimal getAcctBal() {
		return this.acctBal;
	}

	public void setAcctBal(BigDecimal acctBal) {
		this.acctBal = acctBal;
	}

	public String getAcctCurr() {
		return this.acctCurr;
	}

	public void setAcctCurr(String acctCurr) {
		this.acctCurr = acctCurr;
	}

	public String getAcctNo() {
		return this.acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctStat() {
		return this.acctStat;
	}

	public void setAcctStat(String acctStat) {
		this.acctStat = acctStat;
	}

	public String getAcctType() {
		return this.acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getCancelAcctBranchNo() {
		return this.cancelAcctBranchNo;
	}

	public void setCancelAcctBranchNo(String cancelAcctBranchNo) {
		this.cancelAcctBranchNo = cancelAcctBranchNo;
	}

	public String getDuebillNo() {
		return duebillNo;
	}

	public void setDuebillNo(String duebillNo) {
		this.duebillNo = duebillNo;
	}

	public String getCancelAcctDate() {
		return this.cancelAcctDate;
	}

	public void setCancelAcctDate(String cancelAcctDate) {
		this.cancelAcctDate = cancelAcctDate;
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

	public String getLevel12Classify() {
		return this.level12Classify;
	}

	public void setLevel12Classify(String level12Classify) {
		this.level12Classify = level12Classify;
	}

	public String getLevel5Classify() {
		return this.level5Classify;
	}

	public void setLevel5Classify(String level5Classify) {
		this.level5Classify = level5Classify;
	}

	public String getOpenAcctBranchNo() {
		return this.openAcctBranchNo;
	}

	public void setOpenAcctBranchNo(String openAcctBranchNo) {
		this.openAcctBranchNo = openAcctBranchNo;
	}

	public String getOpenAcctDate() {
		return this.openAcctDate;
	}

	public void setOpenAcctDate(String openAcctDate) {
		this.openAcctDate = openAcctDate;
	}

	public String getProdCode() {
		return this.prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}