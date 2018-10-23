package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TX_ALARM_CONF database table.
 * 
 */
public class TxAlarmConfVO implements Serializable {
	private static final Long serialVersionUID = 1L;

	private Long alarmConfId;

	private String alarmLevel;

	private String alarmMethod;

	private String alarmModule;

	private BigDecimal alarmObjectId;

	private String alarmObjectType;

	private String alarmSys;

	private String confStat;

	private Timestamp createTm;

	private String createUser;

	private Date endDate;

	private Date startDate;

	private Timestamp updateTm;

	private String updateUser;
	
	private String alarmObjectName;
	

    public String getAlarmObjectName() {
		return alarmObjectName;
	}

	public void setAlarmObjectName(String alarmObjectName) {
		this.alarmObjectName = alarmObjectName;
	}

	public TxAlarmConfVO() {
    }

	public Long getAlarmConfId() {
		return this.alarmConfId;
	}

	public void setAlarmConfId(Long alarmConfId) {
		this.alarmConfId = alarmConfId;
	}

	public String getAlarmLevel() {
		return this.alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getAlarmMethod() {
		return this.alarmMethod;
	}

	public void setAlarmMethod(String alarmMethod) {
		this.alarmMethod = alarmMethod;
	}

	public String getAlarmModule() {
		return this.alarmModule;
	}

	public void setAlarmModule(String alarmModule) {
		this.alarmModule = alarmModule;
	}

	public BigDecimal getAlarmObjectId() {
		return this.alarmObjectId;
	}

	public void setAlarmObjectId(BigDecimal alarmObjectId) {
		this.alarmObjectId = alarmObjectId;
	}

	public String getAlarmObjectType() {
		return this.alarmObjectType;
	}

	public void setAlarmObjectType(String alarmObjectType) {
		this.alarmObjectType = alarmObjectType;
	}

	public String getAlarmSys() {
		return this.alarmSys;
	}

	public void setAlarmSys(String alarmSys) {
		this.alarmSys = alarmSys;
	}

	public String getConfStat() {
		return this.confStat;
	}

	public void setConfStat(String confStat) {
		this.confStat = confStat;
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