package com.yuchengtech.emp.biappframe.logicsys.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the BIONE_LOGIC_SYS_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_LOGIC_SYS_INFO")
public class BioneLogicSysInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LOGIC_SYS_ID", unique=true, nullable=false, length=32)
	private String logicSysId;

	@Column(name="AUTH_TYPE_NO", length=32)
	private String authTypeNo;

	@Column(name="BASIC_DEPT_STS", length=1)
	private String basicDeptSts;

	@Column(name="BASIC_ORG_STS", length=1)
	private String basicOrgSts;
	
	@Column(name="LOGIC_SYS_ICON", length=500)
	private String logicSysIcon;
	
	@Column(name="CN_COPYRIGHT", length=200)
	private String cnCopyright;

	@Column(name="EN_COPYRIGHT", length=200)
	private String enCopyright;
	
	@Column(name="SYSTEM_VERSION", length=200)
	private String systemVersion;

	@Column(name="IS_BUILTIN", length=1)
	private String isBuiltin;

	@Column(name="LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime;

	@Column(name="LAST_UPDATE_USER", length=100)
	private String lastUpdateUser;

	@Column(name="LOGIC_SYS_NAME", length=100)
	private String logicSysName;

	@Column(name="LOGIC_SYS_NO", nullable=false, length=32)
	private String logicSysNo;

	@Column(name="LOGIC_SYS_STS", length=1)
	private String logicSysSts;

	@Column(name="ORDER_NO", length=5)
	private Long orderNo;

	@Column(length=500)
	private String remark;

    public BioneLogicSysInfo() {
    }

	public String getLogicSysId() {
		return this.logicSysId;
	}

	public void setLogicSysId(String logicSysId) {
		this.logicSysId = logicSysId;
	}

	public String getAuthTypeNo() {
		return this.authTypeNo;
	}

	public void setAuthTypeNo(String authTypeNo) {
		this.authTypeNo = authTypeNo;
	}

	public String getBasicDeptSts() {
		return this.basicDeptSts;
	}

	public void setBasicDeptSts(String basicDeptSts) {
		this.basicDeptSts = basicDeptSts;
	}

	public String getBasicOrgSts() {
		return this.basicOrgSts;
	}

	public void setBasicOrgSts(String basicOrgSts) {
		this.basicOrgSts = basicOrgSts;
	}

	public String getCnCopyright() {
		return this.cnCopyright;
	}

	public void setCnCopyright(String cnCopyright) {
		this.cnCopyright = cnCopyright;
	}

	public String getEnCopyright() {
		return this.enCopyright;
	}

	public void setEnCopyright(String enCopyright) {
		this.enCopyright = enCopyright;
	}

	public String getIsBuiltin() {
		return this.isBuiltin;
	}

	public void setIsBuiltin(String isBuiltin) {
		this.isBuiltin = isBuiltin;
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

	public String getLogicSysName() {
		return this.logicSysName;
	}

	public void setLogicSysName(String logicSysName) {
		this.logicSysName = logicSysName;
	}

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getLogicSysSts() {
		return this.logicSysSts;
	}

	public void setLogicSysSts(String logicSysSts) {
		this.logicSysSts = logicSysSts;
	}

	public Long getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getLogicSysIcon() {
		return logicSysIcon;
	}

	public void setLogicSysIcon(String logicSysIcon) {
		this.logicSysIcon = logicSysIcon;
	}

}