package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiOrgInvestinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgInvestinfoId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiOrgInvestinfoId() {
	}

	/** minimal constructor */
	public HMCiOrgInvestinfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgInvestinfoId(String investmentId, String custId,
			String investedCustId, String investKind, Date investDate,
			String investCurr, Double investAmt, Double investPercent,
			Double investYield, String stockCertNo, String isLargestHolder,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "INVESTMENT_ID", length = 20)
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
		if (!(other instanceof HMCiOrgInvestinfoId))
			return false;
		HMCiOrgInvestinfoId castOther = (HMCiOrgInvestinfoId) other;

		return ((this.getInvestmentId() == castOther.getInvestmentId()) || (this
				.getInvestmentId() != null
				&& castOther.getInvestmentId() != null && this
				.getInvestmentId().equals(castOther.getInvestmentId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getInvestedCustId() == castOther.getInvestedCustId()) || (this
						.getInvestedCustId() != null
						&& castOther.getInvestedCustId() != null && this
						.getInvestedCustId().equals(
								castOther.getInvestedCustId())))
				&& ((this.getInvestKind() == castOther.getInvestKind()) || (this
						.getInvestKind() != null
						&& castOther.getInvestKind() != null && this
						.getInvestKind().equals(castOther.getInvestKind())))
				&& ((this.getInvestDate() == castOther.getInvestDate()) || (this
						.getInvestDate() != null
						&& castOther.getInvestDate() != null && this
						.getInvestDate().equals(castOther.getInvestDate())))
				&& ((this.getInvestCurr() == castOther.getInvestCurr()) || (this
						.getInvestCurr() != null
						&& castOther.getInvestCurr() != null && this
						.getInvestCurr().equals(castOther.getInvestCurr())))
				&& ((this.getInvestAmt() == castOther.getInvestAmt()) || (this
						.getInvestAmt() != null
						&& castOther.getInvestAmt() != null && this
						.getInvestAmt().equals(castOther.getInvestAmt())))
				&& ((this.getInvestPercent() == castOther.getInvestPercent()) || (this
						.getInvestPercent() != null
						&& castOther.getInvestPercent() != null && this
						.getInvestPercent()
						.equals(castOther.getInvestPercent())))
				&& ((this.getInvestYield() == castOther.getInvestYield()) || (this
						.getInvestYield() != null
						&& castOther.getInvestYield() != null && this
						.getInvestYield().equals(castOther.getInvestYield())))
				&& ((this.getStockCertNo() == castOther.getStockCertNo()) || (this
						.getStockCertNo() != null
						&& castOther.getStockCertNo() != null && this
						.getStockCertNo().equals(castOther.getStockCertNo())))
				&& ((this.getIsLargestHolder() == castOther
						.getIsLargestHolder()) || (this.getIsLargestHolder() != null
						&& castOther.getIsLargestHolder() != null && this
						.getIsLargestHolder().equals(
								castOther.getIsLargestHolder())))
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
				+ (getInvestmentId() == null ? 0 : this.getInvestmentId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getInvestedCustId() == null ? 0 : this.getInvestedCustId()
						.hashCode());
		result = 37
				* result
				+ (getInvestKind() == null ? 0 : this.getInvestKind()
						.hashCode());
		result = 37
				* result
				+ (getInvestDate() == null ? 0 : this.getInvestDate()
						.hashCode());
		result = 37
				* result
				+ (getInvestCurr() == null ? 0 : this.getInvestCurr()
						.hashCode());
		result = 37 * result
				+ (getInvestAmt() == null ? 0 : this.getInvestAmt().hashCode());
		result = 37
				* result
				+ (getInvestPercent() == null ? 0 : this.getInvestPercent()
						.hashCode());
		result = 37
				* result
				+ (getInvestYield() == null ? 0 : this.getInvestYield()
						.hashCode());
		result = 37
				* result
				+ (getStockCertNo() == null ? 0 : this.getStockCertNo()
						.hashCode());
		result = 37
				* result
				+ (getIsLargestHolder() == null ? 0 : this.getIsLargestHolder()
						.hashCode());
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