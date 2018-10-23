package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxStdCate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STD_CATE")
public class TxStdCate implements java.io.Serializable {

	// Fields

	private String stdCate;
	private String stdCateDesc;
	private String errorCode;
	private String unknownCode;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxStdCate() {
	}

	/** full constructor */
	public TxStdCate(String stdCateDesc, String errorCode, String unknownCode,
			String state, Timestamp createTm, String createUser,
			Timestamp updateTm, String updateUser) {
		this.stdCateDesc = stdCateDesc;
		this.errorCode = errorCode;
		this.unknownCode = unknownCode;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "STD_CATE", unique = true, nullable = false, length = 10)
	public String getStdCate() {
		return this.stdCate;
	}

	public void setStdCate(String stdCate) {
		this.stdCate = stdCate;
	}

	@Column(name = "STD_CATE_DESC", length = 50)
	public String getStdCateDesc() {
		return this.stdCateDesc;
	}

	public void setStdCateDesc(String stdCateDesc) {
		this.stdCateDesc = stdCateDesc;
	}

	@Column(name = "ERROR_CODE", length = 20)
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "UNKNOWN_CODE", length = 20)
	public String getUnknownCode() {
		return this.unknownCode;
	}

	public void setUnknownCode(String unknownCode) {
		this.unknownCode = unknownCode;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CREATE_TM", length = 11)
	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	@Column(name = "CREATE_USER", length = 20)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "UPDATE_TM", length = 11)
	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	@Column(name = "UPDATE_USER", length = 20)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}