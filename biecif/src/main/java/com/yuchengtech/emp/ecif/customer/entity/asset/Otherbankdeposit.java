package com.yuchengtech.emp.ecif.customer.entity.asset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the OTHERBANKDEPOSIT database table.
 * 
 */
@Entity
@Table(name="M_HL_OTHERBANK_DEPOSIT")
public class Otherbankdeposit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="ACCT_BANK", length=40)
	private String acctBank;

	@Column(name="ACCT_NAME", length=40)
	private String acctName;

	@Column(name="ACCT_NO", length=32)
	private String acctNo;

	@Column(name="ACCT_TYPE", length=20)
	private String acctType;

	@Column(precision=17, scale=2)
	private BigDecimal amt;

	@Column(name="CURRENCY",length=20)
	private String currency;

	@Column(name="DEPOSIT_LIMIT")
	private Long depositLimit;

	@Column(name="DEPSOIT_TYPE", length=20)
	private String depsoitType;

	@Column(name="END_DATE",length=20)
	private String endDate;

	@Column(name="INTEREST_RATE", precision=10, scale=4)
	private BigDecimal interestRate;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM",length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="OPER_BRANCH_NAME", length=80)
	private String operBranchName;

	@Column(name="PDT_SUM", precision=17, scale=2)
	private BigDecimal pdtSum;

	@Column(name="START_DATE",length=20)
	private String startDate;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Otherbankdeposit() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public String getAcctBank() {
		return this.acctBank;
	}

	public void setAcctBank(String acctBank) {
		this.acctBank = acctBank;
	}

	public String getAcctName() {
		return this.acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctNo() {
		return this.acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctType() {
		return this.acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getDepositLimit() {
		return this.depositLimit;
	}

	public void setDepositLimit(Long depositLimit) {
		this.depositLimit = depositLimit;
	}

	public String getDepsoitType() {
		return this.depsoitType;
	}

	public void setDepsoitType(String depsoitType) {
		this.depsoitType = depsoitType;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getInterestRate() {
		return this.interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
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

	public String getOperBranchName() {
		return this.operBranchName;
	}

	public void setOperBranchName(String operBranchName) {
		this.operBranchName = operBranchName;
	}

	public BigDecimal getPdtSum() {
		return this.pdtSum;
	}

	public void setPdtSum(BigDecimal pdtSum) {
		this.pdtSum = pdtSum;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}