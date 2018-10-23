package com.ytec.mdm.domain.biz;


import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADMIN_AUTH_ACCOUNT database table.
 * 
 */
@Entity
@Table(name="ADMIN_AUTH_ACCOUNT")
public class AdminAuthAccount implements Serializable {
	@Id
	private long id;

	@Column(name="ACCOUNT_ID")
	private BigDecimal accountId;

	@Column(name="ACCOUNT_NAME")
	private String accountName;

	@Column(name="APP_ID")
	private String appId;

	@Column(name="BELONG_BUSI_LINE")
	private String belongBusiLine;

	@Column(name="BELONG_TEAM_HEAD")
	private String belongTeamHead;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	private String certificate;

    @Temporal( TemporalType.DATE)
	private Date deadline;

	@Column(name="DIR_ID")
	private String dirId;

	private String education;

	private String email;

    @Temporal( TemporalType.DATE)
	@Column(name="ENTRANTS_DATE")
	private Date entrantsDate;

	@Column(name="FINANCIAL_JOB_TIME")
	private String financialJobTime;

	private String lastlogintime;

	private String mobilephone;

	private String offenip;

	private String officetel;

	@Column(name="ORG_ID")
	private String orgId;

	private String password;

	@Column(name="POSITION_DEGREE")
	private String positionDegree;

	@Column(name="POSITION_TIME")
	private String positionTime;

	private String sex;

	private String temp1;

	private String temp2;

	@Column(name="UP_LEAD")
	private String upLead;

	@Column(name="USER_CODE")
	private String userCode;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_STATE")
	private String userState;

    public AdminAuthAccount() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAccountId() {
		return this.accountId;
	}

	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getBelongBusiLine() {
		return this.belongBusiLine;
	}

	public void setBelongBusiLine(String belongBusiLine) {
		this.belongBusiLine = belongBusiLine;
	}

	public String getBelongTeamHead() {
		return this.belongTeamHead;
	}

	public void setBelongTeamHead(String belongTeamHead) {
		this.belongTeamHead = belongTeamHead;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getDirId() {
		return this.dirId;
	}

	public void setDirId(String dirId) {
		this.dirId = dirId;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEntrantsDate() {
		return this.entrantsDate;
	}

	public void setEntrantsDate(Date entrantsDate) {
		this.entrantsDate = entrantsDate;
	}

	public String getFinancialJobTime() {
		return this.financialJobTime;
	}

	public void setFinancialJobTime(String financialJobTime) {
		this.financialJobTime = financialJobTime;
	}

	public String getLastlogintime() {
		return this.lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getOffenip() {
		return this.offenip;
	}

	public void setOffenip(String offenip) {
		this.offenip = offenip;
	}

	public String getOfficetel() {
		return this.officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPositionDegree() {
		return this.positionDegree;
	}

	public void setPositionDegree(String positionDegree) {
		this.positionDegree = positionDegree;
	}

	public String getPositionTime() {
		return this.positionTime;
	}

	public void setPositionTime(String positionTime) {
		this.positionTime = positionTime;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTemp1() {
		return this.temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return this.temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getUpLead() {
		return this.upLead;
	}

	public void setUpLead(String upLead) {
		this.upLead = upLead;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserState() {
		return this.userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

}