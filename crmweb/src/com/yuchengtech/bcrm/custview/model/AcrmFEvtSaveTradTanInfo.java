package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
 * The persistent class for the ACRM_F_EVT_SAVE_TRAD_TANS database table.
 * 
 */
@Entity
@Table(name="ACRM_F_EVT_SAVE_TRAD_TANS")
public class AcrmFEvtSaveTradTanInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_EVT_SAVE_TRAD_TANS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_EVT_SAVE_TRAD_TANS_ID_GENERATOR")
	private long id;

    @Temporal( TemporalType.DATE)
	@Column(name="ACCOUNTIN_DATE")
	private Date accountinDate;

	private String acct;

	@Column(name="ACCT_BAL")
	private BigDecimal acctBal;

	@Column(name="ADVS_ACCT")
	private String advsAcct;

	@Column(name="ADVS_ACCT_NAME")
	private String advsAcctName;

	@Column(name="CASH_FLAG")
	private String cashFlag;

	@Column(name="CONTACT_TYPE")
	private BigDecimal contactType;

	private String cost;

	private String curr;

	@Column(name="CURR_TRAN_FLAG")
	private String currTranFlag;

	@Column(name="CUST_ID")
	private String custId;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	private BigDecimal handler;

	@Column(name="LOAN_FLAG")
	private String loanFlag;

	@Column(name="ORG_NO")
	private String orgNo;

	private BigDecimal review;

	@Column(name="TANS_NO")
	private String tansNo;

	@Column(name="TRAD_ABS")
	private String tradAbs;

	@Column(name="TRAD_CHN")
	private String tradChn;

    @Temporal( TemporalType.DATE)
	@Column(name="TRAD_DT")
	private Date tradDt;

	@Column(name="TRAD_MONEY")
	private BigDecimal tradMoney;

	@Column(name="TRAD_TELLER")
	private String tradTeller;

	@Column(name="TRAD_TIME")
	private Timestamp tradTime;

	@Column(name="TRAD_TYPE")
	private BigDecimal tradType;

    public AcrmFEvtSaveTradTanInfo() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getAccountinDate() {
		return this.accountinDate;
	}

	public void setAccountinDate(Date accountinDate) {
		this.accountinDate = accountinDate;
	}

	public String getAcct() {
		return this.acct;
	}

	public void setAcct(String acct) {
		this.acct = acct;
	}

	public BigDecimal getAcctBal() {
		return this.acctBal;
	}

	public void setAcctBal(BigDecimal acctBal) {
		this.acctBal = acctBal;
	}

	public String getAdvsAcct() {
		return this.advsAcct;
	}

	public void setAdvsAcct(String advsAcct) {
		this.advsAcct = advsAcct;
	}

	public String getAdvsAcctName() {
		return this.advsAcctName;
	}

	public void setAdvsAcctName(String advsAcctName) {
		this.advsAcctName = advsAcctName;
	}

	public String getCashFlag() {
		return this.cashFlag;
	}

	public void setCashFlag(String cashFlag) {
		this.cashFlag = cashFlag;
	}

	public BigDecimal getContactType() {
		return this.contactType;
	}

	public void setContactType(BigDecimal contactType) {
		this.contactType = contactType;
	}

	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCurr() {
		return this.curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getCurrTranFlag() {
		return this.currTranFlag;
	}

	public void setCurrTranFlag(String currTranFlag) {
		this.currTranFlag = currTranFlag;
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

	public BigDecimal getHandler() {
		return this.handler;
	}

	public void setHandler(BigDecimal handler) {
		this.handler = handler;
	}

	public String getLoanFlag() {
		return this.loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public BigDecimal getReview() {
		return this.review;
	}

	public void setReview(BigDecimal review) {
		this.review = review;
	}

	public String getTansNo() {
		return this.tansNo;
	}

	public void setTansNo(String tansNo) {
		this.tansNo = tansNo;
	}

	public String getTradAbs() {
		return this.tradAbs;
	}

	public void setTradAbs(String tradAbs) {
		this.tradAbs = tradAbs;
	}

	public String getTradChn() {
		return this.tradChn;
	}

	public void setTradChn(String tradChn) {
		this.tradChn = tradChn;
	}

	public Date getTradDt() {
		return this.tradDt;
	}

	public void setTradDt(Date tradDt) {
		this.tradDt = tradDt;
	}

	public BigDecimal getTradMoney() {
		return this.tradMoney;
	}

	public void setTradMoney(BigDecimal tradMoney) {
		this.tradMoney = tradMoney;
	}

	public String getTradTeller() {
		return this.tradTeller;
	}

	public void setTradTeller(String tradTeller) {
		this.tradTeller = tradTeller;
	}

	public Timestamp getTradTime() {
		return this.tradTime;
	}

	public void setTradTime(Timestamp tradTime) {
		this.tradTime = tradTime;
	}

	public BigDecimal getTradType() {
		return this.tradType;
	}

	public void setTradType(BigDecimal tradType) {
		this.tradType = tradType;
	}

}