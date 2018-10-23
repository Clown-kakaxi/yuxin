package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the TX_ALARM_USER_CONT database table.
 * 
 */
@Entity

@Table(name="TX_ALARM_USER_CONT")
public class TxAlarmUserCont implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_ALARM_USER_CONT_GENERATOR")
	@GenericGenerator(name = "TX_ALARM_USER_CONT_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_ALARM_USER_CONT") })	
	@Column(name="CONTMETH_ID")
	private Long contmethId;

	@Column(name="CONTMETH_INFO")
	private String contmethInfo;

	@Column(name="CONTMETH_TYPE")
	private String contmethType;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

	@Column(name="USER_ID")
	private double userId;

    public TxAlarmUserCont() {
    }

	public Long getContmethId() {
		return this.contmethId;
	}

	public void setContmethId(Long contmethId) {
		this.contmethId = contmethId;
	}

	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	public String getContmethType() {
		return this.contmethType;
	}

	public void setContmethType(String contmethType) {
		this.contmethType = contmethType;
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

	public double getUserId() {
		return this.userId;
	}

	public void setUserId(double userId) {
		this.userId = userId;
	}

}