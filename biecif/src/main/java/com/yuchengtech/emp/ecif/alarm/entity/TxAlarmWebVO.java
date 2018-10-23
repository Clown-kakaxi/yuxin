package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * TxAlarmWebVO
 */
public class TxAlarmWebVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long alarmId;

	private String alarmInfo;

	private String alarmLevel;

	private String alarmModule;

	private String alarmStat;

	private String alarmSys;

	private String errorCode;

	private String loginName;

	private Date occurDate;

	private Timestamp occurTime;

	private Date sendDate;

	private BigDecimal sendNum;

	private Timestamp sendTime;

	private String srcSysCd;

	private BigDecimal userId;

	private String userName;

	private String userTitle;
	
	private String occurTimeStr;

    public String getOccurTimeStr() {
		return occurTimeStr;
	}

	public void setOccurTimeStr(String occurTimeStr) {
		this.occurTimeStr = occurTimeStr;
	}

	public TxAlarmWebVO() {
    }

	public long getAlarmId() {
		return this.alarmId;
	}

	public void setAlarmId(long alarmId) {
		this.alarmId = alarmId;
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

	public String getAlarmStat() {
		return this.alarmStat;
	}

	public void setAlarmStat(String alarmStat) {
		this.alarmStat = alarmStat;
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

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public BigDecimal getSendNum() {
		return this.sendNum;
	}

	public void setSendNum(BigDecimal sendNum) {
		this.sendNum = sendNum;
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTitle() {
		return this.userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

}