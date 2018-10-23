package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxSrcSystem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_SRC_SYSTEM")
public class TxSrcSystem implements java.io.Serializable {

	// Fields

	private String srcSysCd;
	private String srcSysNm;
	private String esbSysNm;
	private String edwSysNm;
	private String srcSysName;
	private String srcSysDesc;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxSrcSystem() {
	}

	/** full constructor */
	public TxSrcSystem(String srcSysNm, String esbSysNm, String edwSysNm,
			String srcSysName, String srcSysDesc, String state,
			Timestamp createTm, String createUser, Timestamp updateTm,
			String updateUser) {
		this.srcSysNm = srcSysNm;
		this.esbSysNm = esbSysNm;
		this.edwSysNm = edwSysNm;
		this.srcSysName = srcSysName;
		this.srcSysDesc = srcSysDesc;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "SRC_SYS_CD", unique = true, nullable = false, length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	@Column(name = "SRC_SYS_NM", length = 20)
	public String getSrcSysNm() {
		return this.srcSysNm;
	}

	public void setSrcSysNm(String srcSysNm) {
		this.srcSysNm = srcSysNm;
	}

	@Column(name = "ESB_SYS_NM", length = 20)
	public String getEsbSysNm() {
		return this.esbSysNm;
	}

	public void setEsbSysNm(String esbSysNm) {
		this.esbSysNm = esbSysNm;
	}

	@Column(name = "EDW_SYS_NM", length = 20)
	public String getEdwSysNm() {
		return this.edwSysNm;
	}

	public void setEdwSysNm(String edwSysNm) {
		this.edwSysNm = edwSysNm;
	}

	@Column(name = "SRC_SYS_NAME", length = 30)
	public String getSrcSysName() {
		return this.srcSysName;
	}

	public void setSrcSysName(String srcSysName) {
		this.srcSysName = srcSysName;
	}

	@Column(name = "SRC_SYS_DESC", length = 80)
	public String getSrcSysDesc() {
		return this.srcSysDesc;
	}

	public void setSrcSysDesc(String srcSysDesc) {
		this.srcSysDesc = srcSysDesc;
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