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
 * MCiOrgInvestinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_INVESTINFO")
public class MCiOrgInvestinfo implements java.io.Serializable {

	// Fields

	private String investmentId;
	private String custId;
	private String investedCustId;
	private String investKind;
	private Date investDate;
	private String investCurr;
	private Double investAmt;
	private Double investPercent;
	private Double investYield;
	private String stockCertNo;
	private String isLargestHolder;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiOrgInvestinfo() {
	}

	/** minimal constructor */
	public MCiOrgInvestinfo(String investmentId) {
		this.investmentId = investmentId;
	}

	/** full constructor */
	public MCiOrgInvestinfo(String investmentId, String custId,
			String investedCustId, String investKind, Date investDate,
			String investCurr, Double investAmt, Double investPercent,
			Double investYield, String stockCertNo, String isLargestHolder,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.investmentId = investmentId;
		this.custId = custId;
		this.investedCustId = investedCustId;
		this.investKind = investKind;
		this.investDate = investDate;
		this.investCurr = investCurr;
		this.investAmt = investAmt;
		this.investPercent = investPercent;
		this.investYield = investYield;
		this.stockCertNo = stockCertNo;
		this.isLargestHolder = isLargestHolder;
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

	@Column(name = "INVESTED_CUST_ID", length = 20)
	public String getInvestedCustId() {
		return this.investedCustId;
	}

	public void setInvestedCustId(String investedCustId) {
		this.investedCustId = investedCustId;
	}

	@Column(name = "INVEST_KIND", length = 20)
	public String getInvestKind() {
		return this.investKind;
	}

	public void setInvestKind(String investKind) {
		this.investKind = investKind;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INVEST_DATE", length = 7)
	public Date getInvestDate() {
		return this.investDate;
	}

	public void setInvestDate(Date investDate) {
		this.investDate = investDate;
	}

	@Column(name = "INVEST_CURR", length = 20)
	public String getInvestCurr() {
		return this.investCurr;
	}

	public void setInvestCurr(String investCurr) {
		this.investCurr = investCurr;
	}

	@Column(name = "INVEST_AMT", precision = 17)
	public Double getInvestAmt() {
		return this.investAmt;
	}

	public void setInvestAmt(Double investAmt) {
		this.investAmt = investAmt;
	}

	@Column(name = "INVEST_PERCENT", precision = 10, scale = 4)
	public Double getInvestPercent() {
		return this.investPercent;
	}

	public void setInvestPercent(Double investPercent) {
		this.investPercent = investPercent;
	}

	@Column(name = "INVEST_YIELD", precision = 17)
	public Double getInvestYield() {
		return this.investYield;
	}

	public void setInvestYield(Double investYield) {
		this.investYield = investYield;
	}

	@Column(name = "STOCK_CERT_NO", length = 32)
	public String getStockCertNo() {
		return this.stockCertNo;
	}

	public void setStockCertNo(String stockCertNo) {
		this.stockCertNo = stockCertNo;
	}

	@Column(name = "IS_LARGEST_HOLDER", length = 1)
	public String getIsLargestHolder() {
		return this.isLargestHolder;
	}

	public void setIsLargestHolder(String isLargestHolder) {
		this.isLargestHolder = isLargestHolder;
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