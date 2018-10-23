package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the TX_ALARM_INFO database table.
 * 
 */
public class TxAlarmInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long alarmInfoId;

	private String alarmInfo;

	private String alarmLevel;

	private String alarmModule;

	private String alarmSys;

	private String errorCode;

	private Date occurDate;

	private Timestamp occurTime;

	private String srcSysCd;

	private String alarmStat;
	
    public TxAlarmInfoVO() {
    }

	public long getAlarmInfoId() {
		return this.alarmInfoId;
	}

	public String getAlarmStat() {
		return alarmStat;
	}

	public void setAlarmStat(String alarmStat) {
		this.alarmStat = alarmStat;
	}

	public void setAlarmInfoId(long alarmInfoId) {
		this.alarmInfoId = alarmInfoId;
	}

	public String getAlarmInfo() {
		return this.alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public String getAlarmLevel() {
		return this.alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getAlarmModule() {
		return this.alarmModule;
	}

	public void setAlarmModule(String alarmModule) {
		this.alarmModule = alarmModule;
	}

	public String getAlarmSys() {
		return this.alarmSys;
	}

	public void setAlarmSys(String alarmSys) {
		this.alarmSys = alarmSys;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Date getOccurDate() {
		return this.occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}

	public Timestamp getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Timestamp occurTime) {
		this.occurTime = occurTime;
	}

	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

}