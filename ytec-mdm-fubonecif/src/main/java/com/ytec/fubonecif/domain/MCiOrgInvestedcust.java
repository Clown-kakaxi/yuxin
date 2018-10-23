package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiOrgInvestedcust entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_INVESTEDCUST")
public class MCiOrgInvestedcust implements java.io.Serializable {

	// Fields

	private String investedCustId;
	private String custId;
	private String investedCustName;
	private String investedCustOrgCode;
	private String investedLoanCardNo;
	private String investedLoanCardStat;
	private String investedCustRegNo;
	private String investedCustRegAddr;
	private Double investedRegCapital;
	private String investedRegCurr;
	private String legalReprName;
	private String legalReprIdentType;
	private String legalReprIdentNo;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiOrgInvestedcust() {
	}

	/** minimal constructor */
	public MCiOrgInvestedcust(String investedCustId) {
		this.investedCustId = investedCustId;
	}

	/** full constructor */
	public MCiOrgInvestedcust(String investedCustId, String custId,
			String investedCustName, String investedCustOrgCode,
			String investedLoanCardNo, String investedLoanCardStat,
			String investedCustRegNo, String investedCustRegAddr,
			Double investedRegCapital, String investedRegCurr,
			String legalReprName, String legalReprIdentType,
			String legalReprIdentNo, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
		this.investedCustId = investedCustId;
		this.custId = custId;
		this.investedCustName = investedCustName;
		this.investedCustOrgCode = investedCustOrgCode;
		this.investedLoanCardNo = investedLoanCardNo;
		this.investedLoanCardStat = investedLoanCardStat;
		this.investedCustRegNo = investedCustRegNo;
		this.investedCustRegAddr = investedCustRegAddr;
		this.investedRegCapital = investedRegCapital;
		this.investedRegCurr = investedRegCurr;
		this.legalReprName = legalReprName;
		this.legalReprIdentType = legalReprIdentType;
		this.legalReprIdentNo = legalReprIdentNo;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "INVESTED_CUST_ID", unique = true, nullable = false, length = 20)
	public String getInvestedCustId() {
		return this.investedCustId;
	}

	public void setInvestedCustId(String investedCustId) {
		this.investedCustId = investedCustId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "INVESTED_CUST_NAME", length = 80)
	public String getInvestedCustName() {
		return this.investedCustName;
	}

	public void setInvestedCustName(String investedCustName) {
		this.investedCustName = investedCustName;
	}

	@Column(name = "INVESTED_CUST_ORG_CODE", length = 40)
	public String getInvestedCustOrgCode() {
		return this.investedCustOrgCode;
	}

	public void setInvestedCustOrgCode(String investedCustOrgCode) {
		this.investedCustOrgCode = investedCustOrgCode;
	}

	@Column(name = "INVESTED_LOAN_CARD_NO", length = 32)
	public String getInvestedLoanCardNo() {
		return this.investedLoanCardNo;
	}

	public void setInvestedLoanCardNo(String investedLoanCardNo) {
		this.investedLoanCardNo = investedLoanCardNo;
	}

	@Column(name = "INVESTED_LOAN_CARD_STAT", length = 20)
	public String getInvestedLoanCardStat() {
		return this.investedLoanCardStat;
	}

	public void setInvestedLoanCardStat(String investedLoanCardStat) {
		this.investedLoanCardStat = investedLoanCardStat;
	}

	@Column(name = "INVESTED_CUST_REG_NO", length = 20)
	public String getInvestedCustRegNo() {
		return this.investedCustRegNo;
	}

	public void setInvestedCustRegNo(String investedCustRegNo) {
		this.investedCustRegNo = investedCustRegNo;
	}

	@Column(name = "INVESTED_CUST_REG_ADDR", length = 200)
	public String getInvestedCustRegAddr() {
		return this.investedCustRegAddr;
	}

	public void setInvestedCustRegAddr(String investedCustRegAddr) {
		this.investedCustRegAddr = investedCustRegAddr;
	}

	@Column(name = "INVESTED_REG_CAPITAL", precision = 17)
	public Double getInvestedRegCapital() {
		return this.investedRegCapital;
	}

	public void setInvestedRegCapital(Double investedRegCapital) {
		this.investedRegCapital = investedRegCapital;
	}

	@Column(name = "INVESTED_REG_CURR", length = 20)
	public String getInvestedRegCurr() {
		return this.investedRegCurr;
	}

	public void setInvestedRegCurr(String investedRegCurr) {
		this.investedRegCurr = investedRegCurr;
	}

	@Column(name = "LEGAL_REPR_NAME", length = 80)
	public String getLegalReprName() {
		return this.legalReprName;
	}

	public void setLegalReprName(String legalReprName) {
		this.legalReprName = legalReprName;
	}

	@Column(name = "LEGAL_REPR_IDENT_TYPE", length = 20)
	public String getLegalReprIdentType() {
		return this.legalReprIdentType;
	}

	public void setLegalReprIdentType(String legalReprIdentType) {
		this.legalReprIdentType = legalReprIdentType;
	}

	@Column(name = "LEGAL_REPR_IDENT_NO", length = 40)
	public String getLegalReprIdentNo() {
		return this.legalReprIdentNo;
	}

	public void setLegalReprIdentNo(String legalReprIdentNo) {
		this.legalReprIdentNo = legalReprIdentNo;
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