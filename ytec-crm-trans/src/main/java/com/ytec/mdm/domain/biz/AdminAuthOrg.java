package com.ytec.mdm.domain.biz;


import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADMIN_AUTH_ORG database table.
 * 
 */
@Entity
@Table(name="ADMIN_AUTH_ORG")
public class AdminAuthOrg implements Serializable {
	@Id
	private long id;

	@Column(name="ACCOUNT_ID")
	private BigDecimal accountId;

	@Column(name="APP_ID")
	private String appId;

    @Temporal( TemporalType.DATE)
	@Column(name="LAUNCH_DATE")
	private Date launchDate;

	@Column(name="ORG_ADDR")
	private String orgAddr;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="ORG_LEVEL")
	private String orgLevel;

	@Column(name="ORG_NAME")
	private String orgName;

	@Column(name="POST_CODE")
	private String postCode;

	@Column(name="UP_ORG_ID")
	private String upOrgId;

    public AdminAuthOrg() {
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

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Date getLaunchDate() {
		return this.launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public String getOrgAddr() {
		return this.orgAddr;
	}

	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getUpOrgId() {
		return this.upOrgId;
	}

	public void setUpOrgId(String upOrgId) {
		this.upOrgId = upOrgId;
	}

}