package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxTabKeyDef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_TAB_KEY_DEF")
public class TxTabKeyDef implements java.io.Serializable {

	// Fields

	private Long keyId;
	private String keyName;
	private String keyType;
	private String tabName;
	private String colName;
	private Long keySeq;
	private String refTabName;
	private String refColName;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxTabKeyDef() {
	}

	/** full constructor */
	public TxTabKeyDef(String keyName, String keyType, String tabName,
			String colName, Long keySeq, String refTabName,
			String refColName, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.keyName = keyName;
		this.keyType = keyType;
		this.tabName = tabName;
		this.colName = colName;
		this.keySeq = keySeq;
		this.refTabName = refTabName;
		this.refColName = refColName;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "KEY_ID", unique = true, nullable = false, precision = 22)
	public Long getKeyId() {
		return this.keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	@Column(name = "KEY_NAME", length = 50)
	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Column(name = "KEY_TYPE", length = 10)
	public String getKeyType() {
		return this.keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	@Column(name = "TAB_NAME", length = 50)
	public String getTabName() {
		return this.tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	@Column(name = "COL_NAME", length = 50)
	public String getColName() {
		return this.colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	@Column(name = "KEY_SEQ", precision = 22)
	public Long getKeySeq() {
		return this.keySeq;
	}

	public void setKeySeq(Long keySeq) {
		this.keySeq = keySeq;
	}

	@Column(name = "REF_TAB_NAME", length = 50)
	public String getRefTabName() {
		return this.refTabName;
	}

	public void setRefTabName(String refTabName) {
		this.refTabName = refTabName;
	}

	@Column(name = "REF_COL_NAME", length = 50)
	public String getRefColName() {
		return this.refColName;
	}

	public void setRefColName(String refColName) {
		this.refColName = refColName;
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