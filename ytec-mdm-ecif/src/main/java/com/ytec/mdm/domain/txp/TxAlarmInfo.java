package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxAlarmInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_ALARM_INFO")
public class TxAlarmInfo implements java.io.Serializable {

	// Fields

	private Long alarmInfoId;
	private String alarmSys;
	private String alarmModule;
	private String errorCode;
	private String alarmLevel;
	private String alarmInfo;
	private Date occurDate;
	private Timestamp occurTime;
	private String srcSysCd;
	private String alarmStat;

	// Constructors

	/** default constructor */
	public TxAlarmInfo() {
	}

	/** minimal constructor */
	public TxAlarmInfo(Long alarmInfoId) {
		this.alarmInfoId = alarmInfoId;
	}

	/** full constructor */
	public TxAlarmInfo(Long alarmInfoId, String alarmSys,
			String alarmModule, String errorCode, String alarmLevel,
			String alarmInfo, Date occurDate, Timestamp occurTime,
			String srcSysCd, String alarmStat) {
		this.alarmInfoId = alarmInfoId;
		this.alarmSys = alarmSys;
		this.alarmModule = alarmModule;
		this.errorCode = errorCode;
		this.alarmLevel = alarmLevel;
		this.alarmInfo = alarmInfo;
		this.occurDate = occurDate;
		this.occurTime = occurTime;
		this.srcSysCd = srcSysCd;
		this.alarmStat = alarmStat;
	}

	// Property accessors
	@Id
	@Column(name = "ALARM_INFO_ID", unique = true, nullable = false)
	public Long getAlarmInfoId() {
		return this.alarmInfoId;
	}

	public void setAlarmInfoId(Long alarmInfoId) {
		this.alarmInfoId = alarmInfoId;
	}

	@Column(name = "ALARM_SYS", length = 20)
	public String getAlarmSys() {
		return this.alarmSys;
	}

	public void setAlarmSys(String alarmSys) {
		this.alarmSys = alarmSys;
	}

	@Column(name = "ALARM_MODULE", length = 20)
	public String getAlarmModule() {
		return this.alarmModule;
	}

	public void setAlarmModule(String alarmModule) {
		this.alarmModule = alarmModule;
	}

	@Column(name = "ERROR_CODE", length = 20)
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "ALARM_LEVEL", length = 20)
	public String getAlarmLevel() {
		return this.alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	@Column(name = "ALARM_INFO")
	public String getAlarmInfo() {
		return this.alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "OCCUR_DATE", length = 7)
	public Date getOccurDate() {
		return this.occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}

	@Column(name = "OCCUR_TIME", length = 11)
	public Timestamp getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Timestamp occurTime) {
		this.occurTime = occurTime;
	}

	@Column(name = "SRC_SYS_CD", length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	@Column(name = "ALARM_STAT", length = 20)
	public String getAlarmStat() {
		return this.alarmStat;
	}

	public void setAlarmStat(String alarmStat) {
		this.alarmStat = alarmStat;
	}

}