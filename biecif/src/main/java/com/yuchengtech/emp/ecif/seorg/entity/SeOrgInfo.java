package com.yuchengtech.emp.ecif.seorg.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the BIONE_ORG_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_ORG_INFO")
public class SeOrgInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_ID", unique=true, nullable=false, length=32)
	private String orgId;

	@Column(name="LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime;

	@Column(name="LAST_UPDATE_USER", length=100)
	private String lastUpdateUser;

	@Column(name="LOGIC_SYS_NO", nullable=false, length=32)
	private String logicSysNo;

	@Column(name="ORG_NAME", length=100)
	private String orgName;

	@Column(name="ORG_NO", nullable=false, length=32)
	private String orgNo;

	@Column(name="ORG_STS", length=1)
	private String orgSts;

	@Column(length=500)
	private String remark;

	@Column(name="UP_NO", nullable=false, length=32)
	private String upNo;

    public SeOrgInfo() {
    }

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgSts() {
		return this.orgSts;
	}

	public void setOrgSts(String orgSts) {
		this.orgSts = orgSts;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpNo() {
		return this.upNo;
	}

	public void setUpNo(String upNo) {
		this.upNo = upNo;
	}

}