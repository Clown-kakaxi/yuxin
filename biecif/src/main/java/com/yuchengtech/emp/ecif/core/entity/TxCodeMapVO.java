package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the TX_CODE_MAP database table.
 * 
 */
public class TxCodeMapVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String srcSysCd;
	
	private String srcCode;
	
	private String stdCate;
	
	private Timestamp createTm;

	private String createUser;

	private String srcCodeDesc;

	private String state;

	private String stdCode;

	private Timestamp updateTm;

	private String updateUser;

    public TxCodeMapVO() {
    }

	public String getSrcSysCd() {
		return this.srcSysCd;
	}
	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}
	public String getSrcCode() {
		return this.srcCode;
	}
	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
	}
	public String getStdCate() {
		return this.stdCate;
	}
	public void setStdCate(String stdCate) {
		this.stdCate = stdCate;
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

	public String getSrcCodeDesc() {
		return this.srcCodeDesc;
	}

	public void setSrcCodeDesc(String srcCodeDesc) {
		this.srcCodeDesc = srcCodeDesc;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStdCode() {
		return this.stdCode;
	}

	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
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