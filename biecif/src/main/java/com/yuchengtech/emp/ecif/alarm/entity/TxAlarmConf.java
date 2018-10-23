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
@Entity
@Table(name="TX_ALARM_CONF")
public class TxAlarmConf implements Serializable {
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_ALARM_CONF_GENERATOR")
	@GenericGenerator(name = "TX_ALARM_CONF_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_ALARM_CONF") })
	@Column(name="ALARM_CONF_ID")
	private Long alarmConfId;

	@Column(name="ALARM_LEVEL")
	private String alarmLevel;

	@Column(name="ALARM_METHOD")
	private String alarmMethod;

	@Column(name="ALARM_MODULE")
	private String alarmModule;

	@Column(name="ALARM_OBJECT_ID")
	private BigDecimal alarmObjectId;

	@Column(name="ALARM_OBJECT_TYPE")
	private String alarmObjectType;

	@Column(name="ALARM_SYS")
	private String alarmSys;

	@Column(name="CONF_STAT")
	private String confStat;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public TxAlarmConf() {
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