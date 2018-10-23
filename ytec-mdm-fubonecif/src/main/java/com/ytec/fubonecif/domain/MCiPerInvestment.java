package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiPerInvestment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_INVESTMENT")
public class MCiPerInvestment implements java.io.Serializable {

	// Fields

	private String investmentId;
	private String custId;
	private String investAim;
	private String investExpect;
	private String investType;
	private Double investAmt;
	private String investCurr;
	private Double investYield;
	private Double investIncome;
	private Date startDate;
	private Date endDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerInvestment() {
	}

	/** minimal constructor */
	public MCiPerInvestment(String investmentId) {
		this.investmentId = investmentId;
	}

	/** full constructor */
	public MCiPerInvestment(String investmentId, String custId,
			String investAim, String investExpect, String investType,
			Double investAmt, String investCurr, Double investYield,
			Double investIncome, Date startDate, Date endDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.investmentId = investmentId;
		this.custId = custId;
		this.investAim = investAim;
		this.investExpect = investExpect;
		this.investType = investType;
		this.investAmt = investAmt;
		this.investCurr = investCurr;
		this.investYield = investYield;
		this.investIncome = investIncome;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "INVESTMENT_ID", unique = true, nullable = false, length = 20)
	public String getInvestmentId() {
		return this.investmentId;
	}

	public void setInvestmentId(String investmentId) {
		this.investmentId = investmentId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "INVEST_AIM", length = 20)
	public String getInvestAim() {
		return this.investAim;
	}

	public void setInvestAim(String investAim) {
		this.investAim = investAim;
	}

	@Column(name = "INVEST_EXPECT", length = 20)
	public String getInvestExpect() {
		return this.investExpect;
	}

	public void setInvestExpect(String investExpect) {
		this.investExpect = investExpect;
	}

	@Column(name = "INVEST_TYPE", length = 20)
	public String getInvestType() {
		return this.investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	@Column(name = "INVEST_AMT", precision = 17)
	public Double getInvestAmt() {
		return this.investAmt;
	}

	public void setInvestAmt(Double investAmt) {
		this.investAmt = investAmt;
	}

	@Column(name = "INVEST_CURR", length = 20)
	public String getInvestCurr() {
		return this.investCurr;
	}

	public void setInvestCurr(String investCurr) {
		this.investCurr = investCurr;
	}

	@Column(name = "INVEST_YIELD", precision = 10, scale = 6)
	public Double getInvestYield() {
		return this.investYield;
	}

	public void setInvestYield(Double investYield) {
		this.investYield = investYield;
	}

	@Column(name = "INVEST_INCOME", precision = 17)
	public Double getInvestIncome() {
		return this.investIncome;
	}

	public void setInvestIncome(Double investIncome) {
		this.investIncome = investIncome;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}