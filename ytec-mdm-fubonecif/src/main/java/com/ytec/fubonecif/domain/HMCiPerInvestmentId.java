package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiPerInvestmentId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerInvestmentId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiPerInvestmentId() {
	}

	/** minimal constructor */
	public HMCiPerInvestmentId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerInvestmentId(String investmentId, String custId,
			String investAim, String investExpect, String investType,
			Double investAmt, String investCurr, Double investYield,
			Double investIncome, Date startDate, Date endDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		if (!(other instanceof HMCiPerInvestmentId))
			return false;
		HMCiPerInvestmentId castOther = (HMCiPerInvestmentId) other;

		return ((this.getInvestmentId() == castOther.getInvestmentId()) || (this
				.getInvestmentId() != null
				&& castOther.getInvestmentId() != null && this
				.getInvestmentId().equals(castOther.getInvestmentId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getInvestAim() == castOther.getInvestAim()) || (this
						.getInvestAim() != null
						&& castOther.getInvestAim() != null && this
						.getInvestAim().equals(castOther.getInvestAim())))
				&& ((this.getInvestExpect() == castOther.getInvestExpect()) || (this
						.getInvestExpect() != null
						&& castOther.getInvestExpect() != null && this
						.getInvestExpect().equals(castOther.getInvestExpect())))
				&& ((this.getInvestType() == castOther.getInvestType()) || (this
						.getInvestType() != null
						&& castOther.getInvestType() != null && this
						.getInvestType().equals(castOther.getInvestType())))
				&& ((this.getInvestAmt() == castOther.getInvestAmt()) || (this
						.getInvestAmt() != null
						&& castOther.getInvestAmt() != null && this
						.getInvestAmt().equals(castOther.getInvestAmt())))
				&& ((this.getInvestCurr() == castOther.getInvestCurr()) || (this
						.getInvestCurr() != null
						&& castOther.getInvestCurr() != null && this
						.getInvestCurr().equals(castOther.getInvestCurr())))
				&& ((this.getInvestYield() == castOther.getInvestYield()) || (this
						.getInvestYield() != null
						&& castOther.getInvestYield() != null && this
						.getInvestYield().equals(castOther.getInvestYield())))
				&& ((this.getInvestIncome() == castOther.getInvestIncome()) || (this
						.getInvestIncome() != null
						&& castOther.getInvestIncome() != null && this
						.getInvestIncome().equals(castOther.getInvestIncome())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this
						.getStartDate() != null
						&& castOther.getStartDate() != null && this
						.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this
						.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate()
						.equals(castOther.getEndDate())))
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
		result = 37 * result
				+ (getInvestAim() == null ? 0 : this.getInvestAim().hashCode());
		result = 37
				* result
				+ (getInvestExpect() == null ? 0 : this.getInvestExpect()
						.hashCode());
		result = 37
				* result
				+ (getInvestType() == null ? 0 : this.getInvestType()
						.hashCode());
		result = 37 * result
				+ (getInvestAmt() == null ? 0 : this.getInvestAmt().hashCode());
		result = 37
				* result
				+ (getInvestCurr() == null ? 0 : this.getInvestCurr()
						.hashCode());
		result = 37
				* result
				+ (getInvestYield() == null ? 0 : this.getInvestYield()
						.hashCode());
		result = 37
				* result
				+ (getInvestIncome() == null ? 0 : this.getInvestIncome()
						.hashCode());
		result = 37 * result
				+ (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37 * result
				+ (getEndDate() == null ? 0 : this.getEndDate().hashCode());
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