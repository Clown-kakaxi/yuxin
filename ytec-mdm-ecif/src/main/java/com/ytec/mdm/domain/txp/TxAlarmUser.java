package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxAlarmUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_ALARM_USER")
public class TxAlarmUser implements java.io.Serializable {

	// Fields

	private Long userId;
	private String loginName;
	private String userName;
	private String userTitle;
	private String userDesc;
	private String userBranch;
	private String userDept;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxAlarmUser() {
	}

	/** minimal constructor */
	public TxAlarmUser(Long userId) {
		this.userId = userId;
	}

	/** full constructor */
	public TxAlarmUser(Long userId, String loginName, String userName,
			String userTitle, String userDesc, String userBranch,
			String userDept, Timestamp createTm, String createUser,
			Timestamp updateTm, String updateUser) {
		this.userId = userId;
		this.loginName = loginName;
		this.userName = userName;
		this.userTitle = userTitle;
		this.userDesc = userDesc;
		this.userBranch = userBranch;
		this.userDept = userDept;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "LOGIN_NAME", length = 40)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "USER_NAME", length = 40)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_TITLE", length = 20)
	public String getUserTitle() {
		return this.userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	@Column(name = "USER_DESC")
	public String getUserDesc() {
		return this.userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	@Column(name = "USER_BRANCH", length = 20)
	public String getUserBranch() {
		return this.userBranch;
	}

	public void setUserBranch(String userBranch) {
		this.userBranch = userBranch;
	}

	@Column(name = "USER_DEPT", length = 20)
	public String getUserDept() {
		return this.userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
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