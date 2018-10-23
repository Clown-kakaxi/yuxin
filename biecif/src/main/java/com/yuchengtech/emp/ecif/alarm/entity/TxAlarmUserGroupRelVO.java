package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the TX_ALARM_USER_GROUP_REL database table.
 * 
 */
public class TxAlarmUserGroupRelVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long relId;

	private Timestamp createTm;

	private String createUser;

	private Long groupId;

	private Timestamp updateTm;

	private String updateUser;

	private Long userId;

	private String userName;
	
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public TxAlarmUserGroupRelVO() {
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