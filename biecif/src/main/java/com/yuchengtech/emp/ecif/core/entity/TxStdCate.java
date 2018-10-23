package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TX_STD_CATE database table.
 * 
 */
@Entity
@Table(name="TX_STD_CATE")
public class TxStdCate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="STD_CATE")
	private String stdCate;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="ERROR_CODE")
	private String errorCode;

	@Column(name="STATE")
	private String state;

	@Column(name="STD_CATE_DESC")
	private String stdCateDesc;

	@Column(name="UNKNOWN_CODE")
	private String unknownCode;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public TxStdCate() {
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

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStdCateDesc() {
		return this.stdCateDesc;
	}

	public void setStdCateDesc(String stdCateDesc) {
		this.stdCateDesc = stdCateDesc;
	}

	public String getUnknownCode() {
		return this.unknownCode;
	}

	public void setUnknownCode(String unknownCode) {
		this.unknownCode = unknownCode;
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