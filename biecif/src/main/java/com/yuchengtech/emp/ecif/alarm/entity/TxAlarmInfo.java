package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the TX_ALARM_INFO database table.
 * 
 */
@Entity
@Table(name="TX_ALARM_INFO")
public class TxAlarmInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ALARM_INFO_ID")
	private long alarmInfoId;

	@Column(name="ALARM_INFO")
	private String alarmInfo;

	@Column(name="ALARM_LEVEL")
	private String alarmLevel;

	@Column(name="ALARM_MODULE")
	private String alarmModule;

	@Column(name="ALARM_SYS")
	private String alarmSys;

	@Column(name="ERROR_CODE")
	private String errorCode;

    @Temporal( TemporalType.DATE)
	@Column(name="OCCUR_DATE")
	private Date occurDate;

	@Column(name="OCCUR_TIME")
	private Timestamp occurTime;

	@Column(name="SRC_SYS_CD")
	private String srcSysCd;

	@Column(name="ALARM_STAT")
	private String alarmStat;
	
    public TxAlarmInfo() {
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