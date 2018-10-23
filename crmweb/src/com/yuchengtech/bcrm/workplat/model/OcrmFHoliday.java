package com.yuchengtech.bcrm.workplat.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the OCRM_F_HOLIDAY database table.
 * 
 */
@Entity
@Table(name="OCRM_F_HOLIDAY")
public class OcrmFHoliday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_HOLIDAY_ID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_HOLIDAY_ID_GENERATOR")
	private Long id;

	@Column(name="ACCOUNT_NAME")
	private String accountName;

    @Temporal( TemporalType.DATE)
	@Column(name="BEGIN_DATE")
	private Date beginDate;

	@Column(name="BEGIN_TIME")
	private String beginTime;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="END_TIME")
	private String endTime;

	@Column(name="HOLIDAY_TYPE")
	private String holidayType;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

    public OcrmFHoliday() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHolidayType() {
		return this.holidayType;
	}

	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}

	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
}