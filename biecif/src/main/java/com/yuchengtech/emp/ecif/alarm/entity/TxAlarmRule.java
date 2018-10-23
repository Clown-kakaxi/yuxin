package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the TX_ALARM_RULE database table.
 * 
 */
@Entity
@Table(name="TX_ALARM_RULE")
public class TxAlarmRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_ALARM_RULE_GENERATOR")
	@GenericGenerator(name = "TX_ALARM_RULE_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_ALARM_RULE") })
	@Column(name="ALARM_RULE_ID")
	private Long alarmRuleId;

	@Column(name="ALARM_LEVEL")
	private String alarmLevel;

	@Column(name="ALARM_MODULE")
	private String alarmModule;

	@Column(name="ALARM_SYS")
	private String alarmSys;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="ERROR_CODE")
	private String errorCode;

	@Column(name="IS_ALARM")
	private String isAlarm;

	@Column(name="RULE_STAT")
	private String ruleStat;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public TxAlarmRule() {
    }

	public Long getAlarmRuleId() {
		return this.alarmRuleId;
	}

	public void setAlarmRuleId(Long alarmRuleId) {
		this.alarmRuleId = alarmRuleId;
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

	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getIsAlarm() {
		return this.isAlarm;
	}

	public void setIsAlarm(String isAlarm) {
		this.isAlarm = isAlarm;
	}

	public String getRuleStat() {
		return this.ruleStat;
	}

	public void setRuleStat(String ruleStat) {
		this.ruleStat = ruleStat;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}