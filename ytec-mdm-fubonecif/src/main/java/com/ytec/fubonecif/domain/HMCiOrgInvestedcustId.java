package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiOrgInvestedcustId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgInvestedcustId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiOrgInvestedcustId() {
	}

	/** minimal constructor */
	public HMCiOrgInvestedcustId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgInvestedcustId(String investedCustId, String custId,
			String investedCustName, String investedCustOrgCode,
			String investedLoanCardNo, String investedLoanCardStat,
			String investedCustRegNo, String investedCustRegAddr,
			Double investedRegCapital, String investedRegCurr,
			String legalReprName, String legalReprIdentType,
			String legalReprIdentNo, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "INVESTED_CUST_ID", length = 20)
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

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiOrgInvestedcustId))
			return false;
		HMCiOrgInvestedcustId castOther = (HMCiOrgInvestedcustId) other;

		return ((this.getInvestedCustId() == castOther.getInvestedCustId()) || (this
				.getInvestedCustId() != null
				&& castOther.getInvestedCustId() != null && this
				.getInvestedCustId().equals(castOther.getInvestedCustId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getInvestedCustName() == castOther
						.getInvestedCustName()) || (this.getInvestedCustName() != null
						&& castOther.getInvestedCustName() != null && this
						.getInvestedCustName().equals(
								castOther.getInvestedCustName())))
				&& ((this.getInvestedCustOrgCode() == castOther
						.getInvestedCustOrgCode()) || (this
						.getInvestedCustOrgCode() != null
						&& castOther.getInvestedCustOrgCode() != null && this
						.getInvestedCustOrgCode().equals(
								castOther.getInvestedCustOrgCode())))
				&& ((this.getInvestedLoanCardNo() == castOther
						.getInvestedLoanCardNo()) || (this
						.getInvestedLoanCardNo() != null
						&& castOther.getInvestedLoanCardNo() != null && this
						.getInvestedLoanCardNo().equals(
								castOther.getInvestedLoanCardNo())))
				&& ((this.getInvestedLoanCardStat() == castOther
						.getInvestedLoanCardStat()) || (this
						.getInvestedLoanCardStat() != null
						&& castOther.getInvestedLoanCardStat() != null && this
						.getInvestedLoanCardStat().equals(
								castOther.getInvestedLoanCardStat())))
				&& ((this.getInvestedCustRegNo() == castOther
						.getInvestedCustRegNo()) || (this
						.getInvestedCustRegNo() != null
						&& castOther.getInvestedCustRegNo() != null && this
						.getInvestedCustRegNo().equals(
								castOther.getInvestedCustRegNo())))
				&& ((this.getInvestedCustRegAddr() == castOther
						.getInvestedCustRegAddr()) || (this
						.getInvestedCustRegAddr() != null
						&& castOther.getInvestedCustRegAddr() != null && this
						.getInvestedCustRegAddr().equals(
								castOther.getInvestedCustRegAddr())))
				&& ((this.getInvestedRegCapital() == castOther
						.getInvestedRegCapital()) || (this
						.getInvestedRegCapital() != null
						&& castOther.getInvestedRegCapital() != null && this
						.getInvestedRegCapital().equals(
								castOther.getInvestedRegCapital())))
				&& ((this.getInvestedRegCurr() == castOther
						.getInvestedRegCurr()) || (this.getInvestedRegCurr() != null
						&& castOther.getInvestedRegCurr() != null && this
						.getInvestedRegCurr().equals(
								castOther.getInvestedRegCurr())))
				&& ((this.getLegalReprName() == castOther.getLegalReprName()) || (this
						.getLegalReprName() != null
						&& castOther.getLegalReprName() != null && this
						.getLegalReprName()
						.equals(castOther.getLegalReprName())))
				&& ((this.getLegalReprIdentType() == castOther
						.getLegalReprIdentType()) || (this
						.getLegalReprIdentType() != null
						&& castOther.getLegalReprIdentType() != null && this
						.getLegalReprIdentType().equals(
								castOther.getLegalReprIdentType())))
				&& ((this.getLegalReprIdentNo() == castOther
						.getLegalReprIdentNo()) || (this.getLegalReprIdentNo() != null
						&& castOther.getLegalReprIdentNo() != null && this
						.getLegalReprIdentNo().equals(
								castOther.getLegalReprIdentNo())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getInvestedCustId() == null ? 0 : this.getInvestedCustId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getInvestedCustName() == null ? 0 : this
						.getInvestedCustName().hashCode());
		result = 37
				* result
				+ (getInvestedCustOrgCode() == null ? 0 : this
						.getInvestedCustOrgCode().hashCode());
		result = 37
				* result
				+ (getInvestedLoanCardNo() == null ? 0 : this
						.getInvestedLoanCardNo().hashCode());
		result = 37
				* result
				+ (getInvestedLoanCardStat() == null ? 0 : this
						.getInvestedLoanCardStat().hashCode());
		result = 37
				* result
				+ (getInvestedCustRegNo() == null ? 0 : this
						.getInvestedCustRegNo().hashCode());
		result = 37
				* result
				+ (getInvestedCustRegAddr() == null ? 0 : this
						.getInvestedCustRegAddr().hashCode());
		result = 37
				* result
				+ (getInvestedRegCapital() == null ? 0 : this
						.getInvestedRegCapital().hashCode());
		result = 37
				* result
				+ (getInvestedRegCurr() == null ? 0 : this.getInvestedRegCurr()
						.hashCode());
		result = 37
				* result
				+ (getLegalReprName() == null ? 0 : this.getLegalReprName()
						.hashCode());
		result = 37
				* result
				+ (getLegalReprIdentType() == null ? 0 : this
						.getLegalReprIdentType().hashCode());
		result = 37
				* result
				+ (getLegalReprIdentNo() == null ? 0 : this
						.getLegalReprIdentNo().hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}