package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiPerPoint entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_POINT")
public class MCiPerPoint implements java.io.Serializable {

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

	// Constructors

	/** default constructor */
	public MCiPerPoint() {
	}

	/** minimal constructor */
	public MCiPerPoint(String personPointId) {
		this.personPointId = personPointId;
	}

	/** full constructor */
	public MCiPerPoint(String personPointId, String custId, Double usablePoint,
			Double sumPoint, Double totalPoint, BigDecimal pointPeriod,
			Date startDate, Double profitSum, Double basePointSum,
			Double awardPointSum, Double usedPointSum,
			Double monthUsedPointSum, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
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
	}

	// Property accessors
	@Id
	@Column(name = "PERSON_POINT_ID", unique = true, nullable = false, length = 20)
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

}