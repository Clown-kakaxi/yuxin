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
 * The persistent class for the ACRM_F_CI_PER_INVESTMENT database table.
 * 个人投资信息
 */
@Entity
@Table(name="ACRM_F_CI_PER_INVESTMENT")
public class AcrmFCiPerInvestment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_PER_INVESTMENT_INVESTMENT_ID_GENERATOR", sequenceName="SEQUENCE_AFCI_PER_INVESTMENT" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_PER_INVESTMENT_INVESTMENT_ID_GENERATOR")
	@Column(name="INVESTMENT_ID")
	private String investmentId;

	@Column(name="CUST_ID")
	private String custId;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="INVEST_AIM")
	private String investAim;

	@Column(name="INVEST_AMT")
	private BigDecimal investAmt;

	@Column(name="INVEST_CURR")
	private String investCurr;

	@Column(name="INVEST_EXPECT")
	private String investExpect;

	@Column(name="INVEST_INCOME")
	private BigDecimal investIncome;

	@Column(name="INVEST_TYPE")
	private String investType;

	@Column(name="INVEST_YIELD")
	private BigDecimal investYield;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

    public AcrmFCiPerInvestment() {
    }

	public String getInvestmentId() {
		return this.investmentId;
	}

	public void setInvestmentId(String investmentId) {
		this.investmentId = investmentId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getInvestAim() {
		return this.investAim;
	}

	public void setInvestAim(String investAim) {
		this.investAim = investAim;
	}

	public BigDecimal getInvestAmt() {
		return this.investAmt;
	}

	public void setInvestAmt(BigDecimal investAmt) {
		this.investAmt = investAmt;
	}

	public String getInvestCurr() {
		return this.investCurr;
	}

	public void setInvestCurr(String investCurr) {
		this.investCurr = investCurr;
	}

	public String getInvestExpect() {
		return this.investExpect;
	}

	public void setInvestExpect(String investExpect) {
		this.investExpect = investExpect;
	}

	public BigDecimal getInvestIncome() {
		return this.investIncome;
	}

	public void setInvestIncome(BigDecimal investIncome) {
		this.investIncome = investIncome;
	}

	public String getInvestType() {
		return this.investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public BigDecimal getInvestYield() {
		return this.investYield;
	}

	public void setInvestYield(BigDecimal investYield) {
		this.investYield = investYield;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}