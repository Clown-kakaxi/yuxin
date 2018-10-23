package com.yuchengtech.emp.biappframe.schedule.web.vo;

import java.io.Serializable;

public class BioneTriggerInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String triggerId;

	private String cron;

	private String execFreq;

	private String execTime;

	private String execType;

	private String logicSysNo;

	private String remark;

	private String spaceTime;

	private String triggerName;

	private String defType;

	private String spaceUnit;

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