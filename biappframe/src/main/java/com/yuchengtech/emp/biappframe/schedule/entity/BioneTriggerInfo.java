package com.yuchengtech.emp.biappframe.schedule.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the BIONE_TRIGGER_INFO database table.
 * 
 */
@Entity
@Table(name = "BIONE_TRIGGER_INFO")
public class BioneTriggerInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TRIGGER_ID", unique = true, nullable = false, length = 32)
	private String triggerId;

	@Column(length = 1000)
	private String cron;

	@Column(name = "END_TIME")
	private Timestamp endTime;

	@Column(name = "EXEC_FREQ", length = 10)
	private String execFreq;

	@Column(name = "EXEC_TIME", length = 10)
	private String execTime;

	@Column(name = "EXEC_TYPE", length = 10)
	private String execType;

	@Column(name = "LOGIC_SYS_NO", nullable = false, length = 64)
	private String logicSysNo;

	@Column(length = 1000)
	private String remark;

	@Column(name = "SPACE_TIME", length = 10)
	private String spaceTime;

	@Column(name = "START_TIME")
	private Timestamp startTime;

	@Column(name = "TRIGGER_NAME", length = 200)
	private String triggerName;

	@Column(name = "DEF_TYPE", length = 10)
	private String defType;

	@Column(name = "SPACE_UNIT", length = 10)
	private String spaceUnit;

	public BioneTriggerInfo() {
	}

	public String getTriggerId() {
		return this.triggerId;
	}

	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}

	public String getCron() {
		return this.cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getExecFreq() {
		return this.execFreq;
	}

	public void setExecFreq(String execFreq) {
		this.execFreq = execFreq;
	}

	public String getExecTime() {
		return this.execTime;
	}

	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}

	public String getExecType() {
		return this.execType;
	}

	public void setExecType(String execType) {
		this.execType = execType;
	}

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSpaceTime() {
		return this.spaceTime;
	}

	public void setSpaceTime(String spaceTime) {
		this.spaceTime = spaceTime;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getTriggerName() {
		return this.triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getDefType() {
		return defType;
	}

	public void setDefType(String defType) {
		this.defType = defType;
	}

	public String getSpaceUnit() {
		return spaceUnit;
	}

	public void setSpaceUnit(String spaceUnit) {
		this.spaceUnit = spaceUnit;
	}

}