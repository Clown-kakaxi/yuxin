package com.yuchengtech.emp.biappframe.authobj.web.vo;

import java.sql.Timestamp;
import java.util.List;

import com.google.common.collect.Lists;

public class BioneDeptInfoVO {
	private String deptId;
	private String deptName;
	private String orgNo;
	private String deptNo;
	private String deptSts;
	private Timestamp lastUpdateTime;
	private String lastUpdateUser;
	private String logicSysNo;
	private String remark;
	private String upNo;
	private List<BioneDeptInfoVO> children;

	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptSts() {
		return deptSts;
	}

	public void setDeptSts(String deptSts) {
		this.deptSts = deptSts;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpNo() {
		return upNo;
	}

	public void setUpNo(String upNo) {
		this.upNo = upNo;
	}

	public List<BioneDeptInfoVO> getChildren() {
		if (children == null) {
			children = Lists.newArrayList();
		}
		return children;
	}

	public void setChildren(List<BioneDeptInfoVO> children) {
		this.children = children;
	}
}
