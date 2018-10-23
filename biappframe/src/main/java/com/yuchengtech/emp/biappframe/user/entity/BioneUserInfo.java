package com.yuchengtech.emp.biappframe.user.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the BIONE_USER_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_USER_INFO")
public class BioneUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID", unique=true, nullable=false, length=32)
	private String userId;

	@Column(length=500)
	private String address;

	@Column(length=10)
	private String birthday;

	@Column(name="DEPT_NO", nullable=false, length=32)
	private String deptNo;

	@Column(length=100)
	private String email;

	@Column(name="LAST_PWD_UPDATE_TIME")
	private Timestamp lastPwdUpdateTime;

	@Column(name="LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime;

	@Column(name="LAST_UPDATE_USER", length=100)
	private String lastUpdateUser;

	@Column(length=20)
	private String mobile;

	@Column(name="ORG_NO", nullable=false, length=32)
	private String orgNo;

	@Column(length=10)
	private String postcode;

	@Column(length=500)
	private String remark;

	@Column(length=10)
	private String sex;

	@Column(length=20)
	private String tel;

	@Column(name="USER_NAME", length=100)
	private String userName;

	@Column(name="USER_NO", nullable=false, length=32)
	private String userNo;

	@Column(name="USER_PWD", nullable=false, length=100)
	private String userPwd;

	@Column(name="USER_STS", length=1)
	private String userSts;
	
	@Column(name="IS_BUILTIN", length=1)
	private String isBuiltin;
	
	@Column(name="USER_ICON", length=500)
	private String userIcon;

    public BioneUserInfo() {
    }

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getLastPwdUpdateTime() {
		return this.lastPwdUpdateTime;
	}

	public void setLastPwdUpdateTime(Timestamp lastPwdUpdateTime) {
		this.lastPwdUpdateTime = lastPwdUpdateTime;
	}

	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserSts() {
		return this.userSts;
	}

	public void setUserSts(String userSts) {
		this.userSts = userSts;
	}

	public String getIsBuiltin() {
		return isBuiltin;
	}

	public void setIsBuiltin(String isBuiltin) {
		this.isBuiltin = isBuiltin;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

}