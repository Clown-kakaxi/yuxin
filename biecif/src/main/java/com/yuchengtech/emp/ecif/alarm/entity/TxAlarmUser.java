package com.yuchengtech.emp.ecif.alarm.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the TX_ALARM_USER database table.
 * 
 */
@Entity
@Table(name="TX_ALARM_USER")
public class TxAlarmUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_ALARM_USER_GENERATOR")
	@GenericGenerator(name = "TX_ALARM_USER_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_ALARM_USER") })
	@Column(name="USER_ID")
	private Long userId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="LOGIN_NAME")
	private String loginName;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

	@Column(name="USER_BRANCH")
	private String userBranch;

	@Column(name="USER_DEPT")
	private String userDept;

	@Column(name="USER_DESC")
	private String userDesc;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_TITLE")
	private String userTitle;

    public TxAlarmUser() {
    }

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getUserBranch() {
		return this.userBranch;
	}

	public void setUserBranch(String userBranch) {
		this.userBranch = userBranch;
	}

	public String getUserDept() {
		return this.userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getUserDesc() {
		return this.userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
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