package com.yuchengtech.emp.biappframe.authobj.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the BIONE_DEPT_INFO database table.
 * 
 */
@Entity
@Table(name = "BIONE_DEPT_INFO")
public class BioneDeptInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DEPT_ID", unique = true, nullable = false, length = 32)
	private String deptId;

	@Column(name = "DEPT_NAME", length = 100)
	private String deptName;

	@Column(name = "ORG_NO", nullable = false, length = 32)
	private String orgNo;

	@Column(name = "DEPT_NO", nullable = false, length = 32)
	private String deptNo;

	@Column(name = "DEPT_STS", length = 1)
	private String deptSts;

	@Column(name = "LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime;

	@Column(name = "LAST_UPDATE_USER", length = 100)
	private String lastUpdateUser;

	@Column(name = "LOGIC_SYS_NO", nullable = false, length = 32)
	private String logicSysNo;

	@Column(length = 500)
	private String remark;

	@Column(name = "UP_NO", nullable = false, length = 32)
	private String upNo;

	public BioneDeptInfo() {
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptSts() {
		return this.deptSts;
	}

	public void setDeptSts(String deptSts) {
		this.deptSts = deptSts;
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

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

}