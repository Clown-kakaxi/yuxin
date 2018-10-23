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
 * TxAlarmConf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_ALARM_CONF")
public class TxAlarmConf implements java.io.Serializable {

	// Fields

	private Long alarmConfId;
	private String alarmObjectType;
	private Long alarmObjectId;
	private String alarmSys;
	private String alarmModule;
	private String alarmLevel;
	private String alarmMethod;
	private String confStat;
	private Date startDate;
	private Date endDate;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxAlarmConf() {
	}

	/** minimal constructor */
	public TxAlarmConf(Long alarmConfId) {
		this.alarmConfId = alarmConfId;
	}

	/** full constructor */
	public TxAlarmConf(Long alarmConfId, String alarmObjectType,
			Long alarmObjectId, String alarmSys, String alarmModule,
			String alarmLevel, String alarmMethod, String confStat,
			Date startDate, Date endDate, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.alarmConfId = alarmConfId;
		this.alarmObjectType = alarmObjectType;
		this.alarmObjectId = alarmObjectId;
		this.alarmSys = alarmSys;
		this.alarmModule = alarmModule;
		this.alarmLevel = alarmLevel;
		this.alarmMethod = alarmMethod;
		this.confStat = confStat;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "ALARM_CONF_ID", unique = true, nullable = false)
	public Long getAlarmConfId() {
		return this.alarmConfId;
	}

	public void setAlarmConfId(Long alarmConfId) {
		this.alarmConfId = alarmConfId;
	}

	@Column(name = "ALARM_OBJECT_TYPE", length = 20)
	public String getAlarmObjectType() {
		return this.alarmObjectType;
	}

	public void setAlarmObjectType(String alarmObjectType) {
		this.alarmObjectType = alarmObjectType;
	}

	@Column(name = "ALARM_OBJECT_ID")
	public Long getAlarmObjectId() {
		return this.alarmObjectId;
	}

	public void setAlarmObjectId(Long alarmObjectId) {
		this.alarmObjectId = alarmObjectId;
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

	@Column(name = "ALARM_LEVEL", length = 20)
	public String getAlarmLevel() {
		return this.alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	@Column(name = "ALARM_METHOD", length = 20)
	public String getAlarmMethod() {
		return this.alarmMethod;
	}

	public void setAlarmMethod(String alarmMethod) {
		this.alarmMethod = alarmMethod;
	}

	@Column(name = "CONF_STAT", length = 20)
	public String getConfStat() {
		return this.confStat;
	}

	public void setConfStat(String confStat) {
		this.confStat = confStat;
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