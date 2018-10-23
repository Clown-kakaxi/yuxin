package com.yuchengtech.emp.ecif.customer.entity.customerevaluate;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.math.BigDecimal;


/**
 * The persistent class for the PERSONPOINT database table.
 * 
 */
@Entity
@Table(name="PERSONPOINT")
public class Personpoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PERSON_POINT_ID", unique=true, nullable=false)
	private Long personPointId;

	@Column(name="AWARD_POINT_SUM", precision=10, scale=2)
	private BigDecimal awardPointSum;

	@Column(name="BASE_POINT_SUM", precision=10, scale=2)
	private BigDecimal basePointSum;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="MONTH_USED_POINT_SUM", precision=10, scale=2)
	private BigDecimal monthUsedPointSum;

	@Column(name="POINT_PERIOD", length=8)
	private String pointPeriod;

	@Column(name="PROFIT_SUM", precision=10, scale=2)
	private BigDecimal profitSum;

	@Column(name="START_DATE",length=20)
	private String startDate;

	@Column(name="SUM_POINT", precision=10, scale=2)
	private BigDecimal sumPoint;

	@Column(name="TOTAL_POINT", precision=10, scale=2)
	private BigDecimal totalPoint;

	@Column(name="USABLE_POINT", precision=10, scale=2)
	private BigDecimal usablePoint;

	@Column(name="USED_POINT_SUM", precision=10, scale=2)
	private BigDecimal usedPointSum;

    public Personpoint() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPersonPointId() {
		return this.personPointId;
	}

	public void setPersonPointId(Long personPointId) {
		this.personPointId = personPointId;
	}

	public BigDecimal getAwardPointSum() {
		return this.awardPointSum;
	}

	public void setAwardPointSum(BigDecimal awardPointSum) {
		this.awardPointSum = awardPointSum;
	}

	public BigDecimal getBasePointSum() {
		return this.basePointSum;
	}

	public void setBasePointSum(BigDecimal basePointSum) {
		this.basePointSum = basePointSum;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public BigDecimal getMonthUsedPointSum() {
		return this.monthUsedPointSum;
	}

	public void setMonthUsedPointSum(BigDecimal monthUsedPointSum) {
		this.monthUsedPointSum = monthUsedPointSum;
	}

	public String getPointPeriod() {
		return this.pointPeriod;
	}

	public void setPointPeriod(String pointPeriod) {
		this.pointPeriod = pointPeriod;
	}

	public BigDecimal getProfitSum() {
		return this.profitSum;
	}

	public void setProfitSum(BigDecimal profitSum) {
		this.profitSum = profitSum;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getSumPoint() {
		return this.sumPoint;
	}

	public void setSumPoint(BigDecimal sumPoint) {
		this.sumPoint = sumPoint;
	}

	public BigDecimal getTotalPoint() {
		return this.totalPoint;
	}

	public void setTotalPoint(BigDecimal totalPoint) {
		this.totalPoint = totalPoint;
	}

	public BigDecimal getUsablePoint() {
		return this.usablePoint;
	}

	public void setUsablePoint(BigDecimal usablePoint) {
		this.usablePoint = usablePoint;
	}

	public BigDecimal getUsedPointSum() {
		return this.usedPointSum;
	}

	public void setUsedPointSum(BigDecimal usedPointSum) {
		this.usedPointSum = usedPointSum;
	}

}