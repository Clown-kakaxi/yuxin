package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TX_CODE_MAP database table.
 * 
 */
@Entity
@Table(name="TX_CODE_MAP")
public class TxCodeMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TxCodeMapPK id;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="SRC_CODE_DESC")
	private String srcCodeDesc;

	@Column(name="STATE")
	private String state;

	@Column(name="STD_CODE")
	private String stdCode;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public TxCodeMap() {
    }

	public TxCodeMapPK getId() {
		return this.id;
	}

	public void setId(TxCodeMapPK id) {
		this.id = id;
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