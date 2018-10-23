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
 * TxAlarmRule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_ALARM_RULE")
public class TxAlarmRule implements java.io.Serializable {

	// Fields

	private Long alarmRuleId;
	private String alarmSys;
	private String alarmModule;
	private String errorCode;
	private String isAlarm;
	private String alarmLevel;
	private String ruleStat;
	private Date startDate;
	private Date endDate;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxAlarmRule() {
	}

	/** minimal constructor */
	public TxAlarmRule(Long alarmRuleId) {
		this.alarmRuleId = alarmRuleId;
	}

	/** full constructor */
	public TxAlarmRule(Long alarmRuleId, String alarmSys,
			String alarmModule, String errorCode, String isAlarm,
			String alarmLevel, String ruleStat, Date startDate, Date endDate,
			Timestamp createTm, String createUser, Timestamp updateTm,
			String updateUser) {
		this.alarmRuleId = alarmRuleId;
		this.alarmSys = alarmSys;
		this.alarmModule = alarmModule;
		this.errorCode = errorCode;
		this.isAlarm = isAlarm;
		this.alarmLevel = alarmLevel;
		this.ruleStat = ruleStat;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "ALARM_RULE_ID", unique = true, nullable = false)
	public Long getAlarmRuleId() {
		return this.alarmRuleId;
	}

	public void setAlarmRuleId(Long alarmRuleId) {
		this.alarmRuleId = alarmRuleId;
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

	@Column(name = "IS_ALARM", length = 1)
	public String getIsAlarm() {
		return this.isAlarm;
	}

	public void setIsAlarm(String isAlarm) {
		this.isAlarm = isAlarm;
	}

	@Column(name = "ALARM_LEVEL", length = 20)
	public String getAlarmLevel() {
		return this.alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	@Column(name = "RULE_STAT", length = 20)
	public String getRuleStat() {
		return this.ruleStat;
	}

	public void setRuleStat(String ruleStat) {
		this.ruleStat = ruleStat;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "CREATE_TM", length = 11)
	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	@Column(name = "CREATE_USER", length = 20)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "UPDATE_TM", length = 11)
	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	@Column(name = "UPDATE_USER", length = 20)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}