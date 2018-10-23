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
 * The persistent class for the OCRM_F_WP_SCHEDULE_WEEK database table.
 * 
 */
@Entity
@Table(name="OCRM_F_WP_SCHEDULE_WEEK")
public class OcrmFWpScheduleWeek implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_WP_SCHEDULE_WEEK_WID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_WP_SCHEDULE_WEEK_WID_GENERATOR")
	@Column(name="W_ID")
	private Long wId;

    @Temporal( TemporalType.DATE)
	@Column(name="ACT_END_TIME")
	private Date actEndTime;

    @Temporal( TemporalType.DATE)
	@Column(name="ACT_START_TIME")
	private Date actStartTime;

	@Column(name="ARANGE_ID")
	private String arangeId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	private String remark;

	@Column(name="SCH_CONTENT")
	private String schContent;

    @Temporal( TemporalType.DATE)
	@Column(name="SCH_EDN_TIME")
	private Date schEdnTime;

	@Column(name="SCH_ID")
	private BigDecimal schId;

    @Temporal( TemporalType.DATE)
	@Column(name="SCH_START_TIME")
	private Date schStartTime;

	private String schedule;

	private String stat;

	@Column(name="UNFINISHED_REMARK")
	private String unfinishedRemark;

	@Column(name="WEEK_CYCLE")
	private String weekCycle;

    public OcrmFWpScheduleWeek() {
    }

	public Long getWId() {
		return this.wId;
	}

	public void setWId(Long wId) {
		this.wId = wId;
	}

	public Date getActEndTime() {
		return this.actEndTime;
	}

	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}

	public Date getActStartTime() {
		return this.actStartTime;
	}

	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}

	public String getArangeId() {
		return this.arangeId;
	}

	public void setArangeId(String arangeId) {
		this.arangeId = arangeId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSchContent() {
		return this.schContent;
	}

	public void setSchContent(String schContent) {
		this.schContent = schContent;
	}

	public Date getSchEdnTime() {
		return this.schEdnTime;
	}

	public void setSchEdnTime(Date schEdnTime) {
		this.schEdnTime = schEdnTime;
	}

	public BigDecimal getSchId() {
		return this.schId;
	}

	public void setSchId(BigDecimal schId) {
		this.schId = schId;
	}

	public Date getSchStartTime() {
		return this.schStartTime;
	}

	public void setSchStartTime(Date schStartTime) {
		this.schStartTime = schStartTime;
	}

	public String getSchedule() {
		return this.schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getStat() {
		return this.stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getUnfinishedRemark() {
		return this.unfinishedRemark;
	}

	public void setUnfinishedRemark(String unfinishedRemark) {
		this.unfinishedRemark = unfinishedRemark;
	}

	public String getWeekCycle() {
		return this.weekCycle;
	}

	public void setWeekCycle(String weekCycle) {
		this.weekCycle = weekCycle;
	}

}