package com.yuchengtech.bcrm.workplat.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the OCRM_F_WP_SCHEDULE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_WP_SCHEDULE")
public class OcrmFWpSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_WP_SCHEDULE_SCHID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_WP_SCHEDULE_SCHID_GENERATOR")
	@Column(name="SCH_ID")
	private Long schId;

	@Column(name="CREDIT_COUNT")
	private BigDecimal creditCount;

	@Column(name="LOAN_CHECK_COUNT")
	private BigDecimal loanCheckCount;

	@Column(name="MONTH_COUNT")
	private BigDecimal monthCount;

	@Column(name="OTHER_COUNT")
	private BigDecimal otherCount;

    @Temporal( TemporalType.DATE)
	@Column(name="SCH_DATE")
	private Date schDate;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="VISIT_COUNT")
	private BigDecimal visitCount;

	@Column(name="WEEK_COUNT")
	private BigDecimal weekCount;

    public OcrmFWpSchedule() {
    }

	public Long getSchId() {
		return this.schId;
	}

	public void setSchId(Long schId) {
		this.schId = schId;
	}

	public BigDecimal getCreditCount() {
		return this.creditCount;
	}

	public void setCreditCount(BigDecimal creditCount) {
		this.creditCount = creditCount;
	}

	public BigDecimal getLoanCheckCount() {
		return this.loanCheckCount;
	}

	public void setLoanCheckCount(BigDecimal loanCheckCount) {
		this.loanCheckCount = loanCheckCount;
	}

	public BigDecimal getMonthCount() {
		return this.monthCount;
	}

	public void setMonthCount(BigDecimal monthCount) {
		this.monthCount = monthCount;
	}

	public BigDecimal getOtherCount() {
		return this.otherCount;
	}

	public void setOtherCount(BigDecimal otherCount) {
		this.otherCount = otherCount;
	}

	public Date getSchDate() {
		return this.schDate;
	}

	public void setSchDate(Date schDate) {
		this.schDate = schDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getVisitCount() {
		return this.visitCount;
	}

	public void setVisitCount(BigDecimal visitCount) {
		this.visitCount = visitCount;
	}

	public BigDecimal getWeekCount() {
		return this.weekCount;
	}

	public void setWeekCount(BigDecimal weekCount) {
		this.weekCount = weekCount;
	}

}