package com.yuchengtech.bcrm.system.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ADMIN_AUTH_ACCOUNT database table.
 * 
 */
@Entity
@Table(name="ADMIN_AUTH_ACCOUNT")
public class AdminAuthAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ADMIN_AUTH_ACCOUNT_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="ID")
	private Long id;

	@Column(name="ACCOUNT_ID")
	private Long accountId;

	@Column(name="ACCOUNT_NAME")
	private String accountName;

	@Column(name="APP_ID")
	private String appId;

    @Temporal( TemporalType.DATE)
	private Date birthday;

    @Temporal( TemporalType.DATE)
	private Date deadline;

	@Column(name="DIR_ID")
	private String dirId;

	@Column(name="EMAIL")
	private String email;

	@Column(name="MOBILEPHONE")
	private String mobilephone;

	private String officetel;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="PASSWORD")
	private String password;

	@Column(name="SEX")
	private String sex;

	@Column(name="TEMP1")
	private String temp1;

	@Column(name="TEMP2")
	private String temp2;

	@Column(name="USER_CODE")
	private String userCode;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_STATE")
	private String userState;
	
	@Column(name="OFFENIP")
	private String offenip;

	@Column(name="LASTLOGINTIME")
	private String lastLoginTime;
	
	@Column(name="EDUCATION")
	private String education;
	
	@Column(name="CERTIFICATE")
	private String certificate;
	
	@Column(name="ENTRANTS_DATE")
	private String entrantsDate;
	
	@Column(name="POSITION_TIME")
	private String positionTime;
	
	@Column(name="POSITION_DEGREE")
	private String positionDegree;
	
	@Column(name="BELONG_BUSI_LINE")
	private String belongBusiLine;
	
	@Column(name="BELONG_TEAM_HEAD")
	private String belongTeamHead;
	
	@Column(name="FINANCIAL_JOB_TIME")
	private String financialJobTime;
	
	//add by liuming 20170217
	@Column(name="CREATE_USER")
	private String createUser;//创建人
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_TM")
	private Date createTm;//创建时间
	
	@Column(name="UPDATE_USER")
	private String updateUser;//最近修改人
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TM")
	private Date updateTm;//最近修改时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getDirId() {
		return dirId;
	}

	public void setDirId(String dirId) {
		this.dirId = dirId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password =password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}
	
	public String getOffenip() {
		return offenip;
	}

	public void setOffenip(String offenip) {
		this.offenip = offenip;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getEntrantsDate() {
		return entrantsDate;
	}

	public void setEntrantsDate(String entrantsDate) {
		this.entrantsDate = entrantsDate;
	}

	public String getPositionTime() {
		return positionTime;
	}

	public void setPositionTime(String positionTime) {
		this.positionTime = positionTime;
	}

	public String getPositionDegree() {
		return positionDegree;
	}

	public void setPositionDegree(String positionDegree) {
		this.positionDegree = positionDegree;
	}

	public String getBelongBusiLine() {
		return belongBusiLine;
	}

	public void setBelongBusiLine(String belongBusiLine) {
		this.belongBusiLine = belongBusiLine;
	}

	public String getBelongTeamHead() {
		return belongTeamHead;
	}

	public void setBelongTeamHead(String belongTeamHead) {
		this.belongTeamHead = belongTeamHead;
	}

	public String getFinancialJobTime() {
		return financialJobTime;
	}

	public void setFinancialJobTime(String financialJobTime) {
		this.financialJobTime = financialJobTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}
	
	
}