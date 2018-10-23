package com.yuchengtech.emp.biappframe.authobj.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the BIONE_AUTH_OBJGRP_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_AUTH_OBJGRP_INFO")
public class BioneAuthObjgrpInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="OBJGRP_ID", unique=true, nullable=false, length=32)
	private String objgrpId;

	@Column(name="LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime;

	@Column(name="LAST_UPDATE_USER", length=100)
	private String lastUpdateUser;

	@Column(name="LOGIC_SYS_NO", nullable=false, length=32)
	private String logicSysNo;

	@Column(name="OBJGRP_NAME", length=100)
	private String objgrpName;

	@Column(name="OBJGRP_NO", nullable=false, length=32)
	private String objgrpNo;
	
	@Column(name="OBJGRP_STS", length=1)
	private String objgrpSts;
	
	@Column(length=500)
	private String remark;

    public BioneAuthObjgrpInfo() {
    }

	public String getObjgrpId() {
		return this.objgrpId;
	}

	public void setObjgrpId(String objgrpId) {
		this.objgrpId = objgrpId;
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

	public String getObjgrpName() {
		return this.objgrpName;
	}

	public void setObjgrpName(String objgrpName) {
		this.objgrpName = objgrpName;
	}

	public String getObjgrpNo() {
		return this.objgrpNo;
	}

	public void setObjgrpNo(String objgrpNo) {
		this.objgrpNo = objgrpNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getObjgrpSts() {
		return objgrpSts;
	}

	public void setObjgrpSts(String objgrpSts) {
		this.objgrpSts = objgrpSts;
	}

}