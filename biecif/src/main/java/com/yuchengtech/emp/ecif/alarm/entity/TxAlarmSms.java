package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TX_ALARM_SMS database table.
 * 
 */
@Entity
@Table(name="TX_ALARM_SMS")
public class TxAlarmSms implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ALARM_ID")
	private long alarmId;

	@Column(name="ALARM_INFO")
	private String alarmInfo;

	@Column(name="ALARM_LEVEL")
	private String alarmLevel;

	@Column(name="ALARM_MODULE")
	private String alarmModule;

	@Column(name="ALARM_STAT")
	private String alarmStat;

	@Column(name="ALARM_SYS")
	private String alarmSys;

	@Column(name="ERROR_CODE")
	private String errorCode;

    @Temporal( TemporalType.DATE)
	@Column(name="OCCUR_DATE")
	private Date occurDate;

	@Column(name="OCCUR_TIME")
	private Timestamp occurTime;

    @Temporal( TemporalType.DATE)
	@Column(name="SEND_DATE")
	private Date sendDate;

	@Column(name="SEND_NUM")
	private BigDecimal sendNum;

	@Column(name="SEND_TIME")
	private Timestamp sendTime;

	@Column(name="SMS_ADDR")
	private String smsAddr;

	@Column(name="SRC_SYS_CD")
	private String srcSysCd;

	@Column(name="USER_ID")
	private BigDecimal userId;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_TITLE")
	private String userTitle;

    public TxAlarmSms() {
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

	public String getSmsAddr() {
		return this.smsAddr;
	}

	public void setSmsAddr(String smsAddr) {
		this.smsAddr = smsAddr;
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