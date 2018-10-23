package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TX_COL_MAP database table.
 * 
 */
public class TxColMapVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String srcSysCd;
	private String srcTab;
	private String srcCol;

	public String getSrcSysCd() {
		return srcSysCd;
	}


	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}


	public String getSrcTab() {
		return srcTab;
	}


	public void setSrcTab(String srcTab) {
		this.srcTab = srcTab;
	}


	public String getSrcCol() {
		return srcCol;
	}


	public void setSrcCol(String srcCol) {
		this.srcCol = srcCol;
	}

	private Timestamp createTm;

	private String createUser;

	private String state;

	private String stdCate;

	private String stdCol;

	private String stdFlag;

	private String stdTab;

	private String trimFlag;

	private Timestamp updateTm;

	private String updateUser;

    public TxColMapVO() {
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStdCate() {
		return this.stdCate;
	}

	public void setStdCate(String stdCate) {
		this.stdCate = stdCate;
	}

	public String getStdCol() {
		return this.stdCol;
	}

	public void setStdCol(String stdCol) {
		this.stdCol = stdCol;
	}

	public String getStdFlag() {
		return this.stdFlag;
	}

	public void setStdFlag(String stdFlag) {
		this.stdFlag = stdFlag;
	}

	public String getStdTab() {
		return this.stdTab;
	}

	public void setStdTab(String stdTab) {
		this.stdTab = stdTab;
	}

	public String getTrimFlag() {
		return this.trimFlag;
	}

	public void setTrimFlag(String trimFlag) {
		this.trimFlag = trimFlag;
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