package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiPerPointId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerPointId implements java.io.Serializable {

	// Fields

	private String personPointId;
	private String custId;
	private Double usablePoint;
	private Double sumPoint;
	private Double totalPoint;
	private BigDecimal pointPeriod;
	private Date startDate;
	private Double profitSum;
	private Double basePointSum;
	private Double awardPointSum;
	private Double usedPointSum;
	private Double monthUsedPointSum;
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
	public HMCiPerPointId() {
	}

	/** minimal constructor */
	public HMCiPerPointId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerPointId(String personPointId, String custId,
			Double usablePoint, Double sumPoint, Double totalPoint,
			BigDecimal pointPeriod, Date startDate, Double profitSum,
			Double basePointSum, Double awardPointSum, Double usedPointSum,
			Double monthUsedPointSum, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.personPointId = personPointId;
		this.custId = custId;
		this.usablePoint = usablePoint;
		this.sumPoint = sumPoint;
		this.totalPoint = totalPoint;
		this.pointPeriod = pointPeriod;
		this.startDate = startDate;
		this.profitSum = profitSum;
		this.basePointSum = basePointSum;
		this.awardPointSum = awardPointSum;
		this.usedPointSum = usedPointSum;
		this.monthUsedPointSum = monthUsedPointSum;
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

	@Column(name = "PERSON_POINT_ID", length = 20)
	public String getPersonPointId() {
		return this.personPointId;
	}

	public void setPersonPointId(String personPointId) {
		this.personPointId = personPointId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "USABLE_POINT", precision = 10)
	public Double getUsablePoint() {
		return this.usablePoint;
	}

	public void setUsablePoint(Double usablePoint) {
		this.usablePoint = usablePoint;
	}

	@Column(name = "SUM_POINT", precision = 10)
	public Double getSumPoint() {
		return this.sumPoint;
	}

	public void setSumPoint(Double sumPoint) {
		this.sumPoint = sumPoint;
	}

	@Column(name = "TOTAL_POINT", precision = 10)
	public Double getTotalPoint() {
		return this.totalPoint;
	}

	public void setTotalPoint(Double totalPoint) {
		this.totalPoint = totalPoint;
	}

	@Column(name = "POINT_PERIOD", precision = 22, scale = 0)
	public BigDecimal getPointPeriod() {
		return this.pointPeriod;
	}

	public void setPointPeriod(BigDecimal pointPeriod) {
		this.pointPeriod = pointPeriod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "PROFIT_SUM", precision = 10)
	public Double getProfitSum() {
		return this.profitSum;
	}

	public void setProfitSum(Double profitSum) {
		this.profitSum = profitSum;
	}

	@Column(name = "BASE_POINT_SUM", precision = 10)
	public Double getBasePointSum() {
		return this.basePointSum;
	}

	public void setBasePointSum(Double basePointSum) {
		this.basePointSum = basePointSum;
	}

	@Column(name = "AWARD_POINT_SUM", precision = 10)
	public Double getAwardPointSum() {
		return this.awardPointSum;
	}

	public void setAwardPointSum(Double awardPointSum) {
		this.awardPointSum = awardPointSum;
	}

	@Column(name = "USED_POINT_SUM", precision = 10)
	public Double getUsedPointSum() {
		return this.usedPointSum;
	}

	public void setUsedPointSum(Double usedPointSum) {
		this.usedPointSum = usedPointSum;
	}

	@Column(name = "MONTH_USED_POINT_SUM", precision = 10)
	public Double getMonthUsedPointSum() {
		return this.monthUsedPointSum;
	}

	public void setMonthUsedPointSum(Double monthUsedPointSum) {
		this.monthUsedPointSum = monthUsedPointSum;
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
		if (!(other instanceof HMCiPerPointId))
			return false;
		HMCiPerPointId castOther = (HMCiPerPointId) other;

		return ((this.getPersonPointId() == castOther.getPersonPointId()) || (this
				.getPersonPointId() != null
				&& castOther.getPersonPointId() != null && this
				.getPersonPointId().equals(castOther.getPersonPointId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getUsablePoint() == castOther.getUsablePoint()) || (this
						.getUsablePoint() != null
						&& castOther.getUsablePoint() != null && this
						.getUsablePoint().equals(castOther.getUsablePoint())))
				&& ((this.getSumPoint() == castOther.getSumPoint()) || (this
						.getSumPoint() != null
						&& castOther.getSumPoint() != null && this
						.getSumPoint().equals(castOther.getSumPoint())))
				&& ((this.getTotalPoint() == castOther.getTotalPoint()) || (this
						.getTotalPoint() != null
						&& castOther.getTotalPoint() != null && this
						.getTotalPoint().equals(castOther.getTotalPoint())))
				&& ((this.getPointPeriod() == castOther.getPointPeriod()) || (this
						.getPointPeriod() != null
						&& castOther.getPointPeriod() != null && this
						.getPointPeriod().equals(castOther.getPointPeriod())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this
						.getStartDate() != null
						&& castOther.getStartDate() != null && this
						.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getProfitSum() == castOther.getProfitSum()) || (this
						.getProfitSum() != null
						&& castOther.getProfitSum() != null && this
						.getProfitSum().equals(castOther.getProfitSum())))
				&& ((this.getBasePointSum() == castOther.getBasePointSum()) || (this
						.getBasePointSum() != null
						&& castOther.getBasePointSum() != null && this
						.getBasePointSum().equals(castOther.getBasePointSum())))
				&& ((this.getAwardPointSum() == castOther.getAwardPointSum()) || (this
						.getAwardPointSum() != null
						&& castOther.getAwardPointSum() != null && this
						.getAwardPointSum()
						.equals(castOther.getAwardPointSum())))
				&& ((this.getUsedPointSum() == castOther.getUsedPointSum()) || (this
						.getUsedPointSum() != null
						&& castOther.getUsedPointSum() != null && this
						.getUsedPointSum().equals(castOther.getUsedPointSum())))
				&& ((this.getMonthUsedPointSum() == castOther
						.getMonthUsedPointSum()) || (this
						.getMonthUsedPointSum() != null
						&& castOther.getMonthUsedPointSum() != null && this
						.getMonthUsedPointSum().equals(
								castOther.getMonthUsedPointSum())))
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
				+ (getPersonPointId() == null ? 0 : this.getPersonPointId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getUsablePoint() == null ? 0 : this.getUsablePoint()
						.hashCode());
		result = 37 * result
				+ (getSumPoint() == null ? 0 : this.getSumPoint().hashCode());
		result = 37
				* result
				+ (getTotalPoint() == null ? 0 : this.getTotalPoint()
						.hashCode());
		result = 37
				* result
				+ (getPointPeriod() == null ? 0 : this.getPointPeriod()
						.hashCode());
		result = 37 * result
				+ (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37 * result
				+ (getProfitSum() == null ? 0 : this.getProfitSum().hashCode());
		result = 37
				* result
				+ (getBasePointSum() == null ? 0 : this.getBasePointSum()
						.hashCode());
		result = 37
				* result
				+ (getAwardPointSum() == null ? 0 : this.getAwardPointSum()
						.hashCode());
		result = 37
				* result
				+ (getUsedPointSum() == null ? 0 : this.getUsedPointSum()
						.hashCode());
		result = 37
				* result
				+ (getMonthUsedPointSum() == null ? 0 : this
						.getMonthUsedPointSum().hashCode());
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