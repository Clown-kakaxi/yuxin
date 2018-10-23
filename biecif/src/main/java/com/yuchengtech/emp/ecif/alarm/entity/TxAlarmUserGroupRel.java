package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the TX_ALARM_USER_GROUP_REL database table.
 * 
 */
@Entity
@Table(name="TX_ALARM_USER_GROUP_REL")
public class TxAlarmUserGroupRel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_ALARM_USER_GROUP_REL_GENERATOR")
	@GenericGenerator(name = "TX_ALARM_USER_GROUP_REL_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_ALARM_USER_GROUP_REL") })	
	@Column(name="REL_ID")
	private Long relId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="GROUP_ID")
	private Long groupId;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

	@Column(name="USER_ID")
	private Long userId;

    public TxAlarmUserGroupRel() {
    }

	public Long getRelId() {
		return this.relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
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

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}