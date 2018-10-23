package com.yuchengtech.emp.biappframe.authobj.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the BIONE_ROLE_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_ROLE_INFO")
public class BioneRoleInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROLE_ID", unique=true, nullable=false, length=32)
	private String roleId;

	@Column(name="LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime;

	@Column(name="LAST_UPDATE_USER", length=100)
	private String lastUpdateUser;

	@Column(name="LOGIC_SYS_NO", nullable=false, length=32)
	private String logicSysNo;

	@Column(length=500)
	private String remark;

	@Column(name="ROLE_NAME", length=100)
	private String roleName;

	@Column(name="ROLE_NO", nullable=false, length=32)
	private String roleNo;

	@Column(name="ROLE_STS", length=1)
	private String roleSts;

	@Column(name="ROLE_TYPE", length=10)
	private String roleType;

    public BioneRoleInfo() {
    }

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleNo() {
		return this.roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getRoleSts() {
		return this.roleSts;
	}

	public void setRoleSts(String roleSts) {
		this.roleSts = roleSts;
	}

	public String getRoleType() {
		return this.roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}