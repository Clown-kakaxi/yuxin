package com.yuchengtech.bcrm.custview.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_DEPOSIT_ACT database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_DEPOSIT_ACT")
public class AcrmFciDepositAct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ACCT_NO")
	private String acctNo;

	@Column(name="ACCOUNT_NAME")
	private String accountName;

	@Column(name="ACCOUNT_STAT")
	private String accountStat;

	@Column(name="ACCT_NAME")
	private String acctName;

	@Column(name="ACCT_TYPE")
	private String acctType;

	private BigDecimal amount;

	@Column(name="AMOUNT_ORG_MONEY")
	private BigDecimal amountOrgMoney;

	@Column(name="AO_NO")
	private String aoNo;

	@Column(name="BEF_DEGREE_CONTRI")
	private BigDecimal befDegreeContri;

	@Column(name="CUR_TYPE")
	private String curType;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="DEPOSITE_AVG_M")
	private BigDecimal depositeAvgM;

	@Column(name="DEPOSITE_AVG_Q")
	private BigDecimal depositeAvgQ;

	@Column(name="DEPOSITE_AVG_Y")
	private BigDecimal depositeAvgY;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Column(name="FREEZE_AMOUNT")
	private BigDecimal freezeAmount;

	private BigDecimal ftp;

    @Temporal( TemporalType.DATE)
	@Column(name="LOGOUT_ACCOUNT_DATE")
	private Date logoutAccountDate;

    @Temporal( TemporalType.DATE)
	@Column(name="MATURE_DATE")
	private Date matureDate;

    @Temporal( TemporalType.DATE)
	@Column(name="OPEN_ACCOUNT_DATE")
	private Date openAccountDate;

	@Column(name="ORG_NAME")
	private String orgName;

	@Column(name="ORG_NO")
	private String orgNo;

	private BigDecimal rate;

	private BigDecimal roa;

	@Column(name="\"SEQUENCE\"")
	private String sequence;

    @Temporal( TemporalType.DATE)
	@Column(name="START_INTER_DATE")
	private Date startInterDate;

	private String subjects;

	@Column(name="TRANS_TIMES")
	private BigDecimal transTimes;

	@Column(name="ACCT_NO1")
	private String acctNo1;
	
    public AcrmFciDepositAct() {
    }

	public String getAcctNo1() {
		return acctNo1;
	}

	public void setAcctNo1(String acctNo1) {
		this.acctNo1 = acctNo1;
	}

	public String getAcctNo() {
		return this.acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountStat() {
		return this.accountStat;
	}

	public void setAccountStat(String accountStat) {
		this.accountStat = accountStat;
	}

	public String getAcctName() {
		return this.acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountOrgMoney() {
		return this.amountOrgMoney;
	}

	public void setAmountOrgMoney(BigDecimal amountOrgMoney) {
		this.amountOrgMoney = amountOrgMoney;
	}

	public String getAoNo() {
		return this.aoNo;
	}

	public void setAoNo(String aoNo) {
		this.aoNo = aoNo;
	}

	public BigDecimal getBefDegreeContri() {
		return this.befDegreeContri;
	}

	public void setBefDegreeContri(BigDecimal befDegreeContri) {
		this.befDegreeContri = befDegreeContri;
	}

	public String getCurType() {
		return this.curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public BigDecimal getDepositeAvgM() {
		return this.depositeAvgM;
	}

	public void setDepositeAvgM(BigDecimal depositeAvgM) {
		this.depositeAvgM = depositeAvgM;
	}

	public BigDecimal getDepositeAvgQ() {
		return this.depositeAvgQ;
	}

	public void setDepositeAvgQ(BigDecimal depositeAvgQ) {
		this.depositeAvgQ = depositeAvgQ;
	}

	public BigDecimal getDepositeAvgY() {
		return this.depositeAvgY;
	}

	public void setDepositeAvgY(BigDecimal depositeAvgY) {
		this.depositeAvgY = depositeAvgY;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public BigDecimal getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public BigDecimal getFtp() {
		return this.ftp;
	}

	public void setFtp(BigDecimal ftp) {
		this.ftp = ftp;
	}

	public Date getLogoutAccountDate() {
		return this.logoutAccountDate;
	}

	public void setLogoutAccountDate(Date logoutAccountDate) {
		this.logoutAccountDate = logoutAccountDate;
	}

	public Date getMatureDate() {
		return this.matureDate;
	}

	public void setMatureDate(Date matureDate) {
		this.matureDate = matureDate;
	}

	public Date getOpenAccountDate() {
		return this.openAccountDate;
	}

	public void setOpenAccountDate(Date openAccountDate) {
		this.openAccountDate = openAccountDate;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getRoa() {
		return this.roa;
	}

	public void setRoa(BigDecimal roa) {
		this.roa = roa;
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public Date getStartInterDate() {
		return this.startInterDate;
	}

	public void setStartInterDate(Date startInterDate) {
		this.startInterDate = startInterDate;
	}

	public String getSubjects() {
		return this.subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public BigDecimal getTransTimes() {
		return this.transTimes;
	}

	public void setTransTimes(BigDecimal transTimes) {
		this.transTimes = transTimes;
	}

}