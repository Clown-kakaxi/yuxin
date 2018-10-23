package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SRC_SYSTEM database table.
 * 
 */
@Entity
@Table(name="TX_SRC_SYSTEM")
public class SrcSystem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SRC_SYS_CD")
	private String srcSysCd;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="EDW_SYS_NM")
	private String edwSysNm;

	@Column(name="ESB_SYS_NM")
	private String esbSysNm;

	@Column(name="SRC_SYS_DESC")
	private String srcSysDesc;

	@Column(name="SRC_SYS_NAME")
	private String srcSysName;

	@Column(name="SRC_SYS_NM")
	private String srcSysNm;

	@Column(name="STATE")
	private String state;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public SrcSystem() {
    }

	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getEdwSysNm() {
		return this.edwSysNm;
	}

	public void setEdwSysNm(String edwSysNm) {
		this.edwSysNm = edwSysNm;
	}

	public String getEsbSysNm() {
		return this.esbSysNm;
	}

	public void setEsbSysNm(String esbSysNm) {
		this.esbSysNm = esbSysNm;
	}

	public String getSrcSysDesc() {
		return this.srcSysDesc;
	}

	public void setSrcSysDesc(String srcSysDesc) {
		this.srcSysDesc = srcSysDesc;
	}

	public String getSrcSysName() {
		return this.srcSysName;
	}

	public void setSrcSysName(String srcSysName) {
		this.srcSysName = srcSysName;
	}

	public String getSrcSysNm() {
		return this.srcSysNm;
	}

	public void setSrcSysNm(String srcSysNm) {
		this.srcSysNm = srcSysNm;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}