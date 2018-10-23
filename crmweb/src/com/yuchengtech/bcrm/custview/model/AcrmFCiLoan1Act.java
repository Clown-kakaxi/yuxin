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
 * The persistent class for the ACRM_F_CI_LOAN_ACT database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_LOAN_ACT")
public class AcrmFCiLoan1Act implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ACCOUNT")
	private String account;

	@Column(name="ACCOUNT_NAME")
	private String accountName;

	@Column(name="ACCOUNT_STAT")
	private String accountStat;

	private BigDecimal amount;

	@Column(name="BEF_DEGREE_CONTRI")
	private BigDecimal befDegreeContri;

	@Column(name="CURR_FORMERLY")
	private BigDecimal currFormerly;

	@Column(name="CURRE_FIRM_INTEREST")
	private BigDecimal curreFirmInterest;

	@Column(name="CURRE_MUST_INTEREST")
	private BigDecimal curreMustInterest;

	@Column(name="CUST_ID")
	private String custId;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Column(name="FIVE_LEVEL_TYPE")
	private String fiveLevelType;

	private BigDecimal ftp;

	@Column(name="LOAN_AVG_M")
	private BigDecimal loanAvgM;

	@Column(name="LOAN_AVG_Q")
	private BigDecimal loanAvgQ;

	@Column(name="LOAN_AVG_Y")
	private BigDecimal loanAvgY;

    @Temporal( TemporalType.DATE)
	@Column(name="LOGOUT_ACCOUNT_DATE")
	private Date logoutAccountDate;

    @Temporal( TemporalType.DATE)
	@Column(name="MATURE_DATE")
	private Date matureDate;

	@Column(name="MONEY_TYPE")
	private String moneyType;

    @Temporal( TemporalType.DATE)
	@Column(name="OPEN_ACCOUNT_DATE")
	private Date openAccountDate;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="ORG_NAME")
	private String orgName;

	private BigDecimal rate;

	@Column(name="\"SEQUENCE\"")
	private String sequence;

    @Temporal( TemporalType.DATE)
	@Column(name="START_INTER_DATE")
	private Date startInterDate;

	private String subjects;

    public AcrmFCiLoan1Act() {
    }

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBefDegreeContri() {
		return this.befDegreeContri;
	}

	public void setBefDegreeContri(BigDecimal befDegreeContri) {
		this.befDegreeContri = befDegreeContri;
	}

	public BigDecimal getCurrFormerly() {
		return this.currFormerly;
	}

	public void setCurrFormerly(BigDecimal currFormerly) {
		this.currFormerly = currFormerly;
	}

	public BigDecimal getCurreFirmInterest() {
		return this.curreFirmInterest;
	}

	public void setCurreFirmInterest(BigDecimal curreFirmInterest) {
		this.curreFirmInterest = curreFirmInterest;
	}

	public BigDecimal getCurreMustInterest() {
		return this.curreMustInterest;
	}

	public void setCurreMustInterest(BigDecimal curreMustInterest) {
		this.curreMustInterest = curreMustInterest;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public String getFiveLevelType() {
		return this.fiveLevelType;
	}

	public void setFiveLevelType(String fiveLevelType) {
		this.fiveLevelType = fiveLevelType;
	}

	public BigDecimal getFtp() {
		return this.ftp;
	}

	public void setFtp(BigDecimal ftp) {
		this.ftp = ftp;
	}

	public BigDecimal getLoanAvgM() {
		return this.loanAvgM;
	}

	public void setLoanAvgM(BigDecimal loanAvgM) {
		this.loanAvgM = loanAvgM;
	}

	public BigDecimal getLoanAvgQ() {
		return this.loanAvgQ;
	}

	public void setLoanAvgQ(BigDecimal loanAvgQ) {
		this.loanAvgQ = loanAvgQ;
	}

	public BigDecimal getLoanAvgY() {
		return this.loanAvgY;
	}

	public void setLoanAvgY(BigDecimal loanAvgY) {
		this.loanAvgY = loanAvgY;
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

	public String getMoneyType() {
		return this.moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public Date getOpenAccountDate() {
		return this.openAccountDate;
	}

	public void setOpenAccountDate(Date openAccountDate) {
		this.openAccountDate = openAccountDate;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
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

}