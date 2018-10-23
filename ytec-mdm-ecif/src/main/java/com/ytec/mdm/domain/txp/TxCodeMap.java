package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxCodeMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_CODE_MAP")
public class TxCodeMap implements java.io.Serializable {

	// Fields

	private TxCodeMapId id;
	private String stdCode;
	private String srcCodeDesc;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxCodeMap() {
	}

	/** minimal constructor */
	public TxCodeMap(TxCodeMapId id) {
		this.id = id;
	}

	/** full constructor */
	public TxCodeMap(TxCodeMapId id, String stdCode, String srcCodeDesc,
			String state, Timestamp createTm, String createUser,
			Timestamp updateTm, String updateUser) {
		this.id = id;
		this.stdCode = stdCode;
		this.srcCodeDesc = srcCodeDesc;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "srcSysCd", column = @Column(name = "SRC_SYS_CD", nullable = false, length = 20)),
			@AttributeOverride(name = "srcCode", column = @Column(name = "SRC_CODE", nullable = false, length = 30)),
			@AttributeOverride(name = "stdCate", column = @Column(name = "STD_CATE", nullable = false, length = 10)) })
	public TxCodeMapId getId() {
		return this.id;
	}

	public void setId(TxCodeMapId id) {
		this.id = id;
	}

	@Column(name = "STD_CODE", length = 20)
	public String getStdCode() {
		return this.stdCode;
	}

	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
	}

	@Column(name = "SRC_CODE_DESC", length = 200)
	public String getSrcCodeDesc() {
		return this.srcCodeDesc;
	}

	public void setSrcCodeDesc(String srcCodeDesc) {
		this.srcCodeDesc = srcCodeDesc;
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