package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxTabDef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_TAB_DEF")
public class TxTabDef implements java.io.Serializable {

	// Fields

	private Long tabId;
	private String tabSchema;
	private String tabName;
	private String tabDesc;
	private String objName;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxTabDef() {
	}

	/** full constructor */
	public TxTabDef(String tabSchema, String tabName, String tabDesc,
			String objName, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.tabSchema = tabSchema;
		this.tabName = tabName;
		this.tabDesc = tabDesc;
		this.objName = objName;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "TAB_ID", unique = true, nullable = false, precision = 22)
	public Long getTabId() {
		return this.tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	@Column(name = "TAB_SCHEMA", length = 50)
	public String getTabSchema() {
		return this.tabSchema;
	}

	public void setTabSchema(String tabSchema) {
		this.tabSchema = tabSchema;
	}

	@Column(name = "TAB_NAME", length = 50)
	public String getTabName() {
		return this.tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	@Column(name = "TAB_DESC", length = 100)
	public String getTabDesc() {
		return this.tabDesc;
	}

	public void setTabDesc(String tabDesc) {
		this.tabDesc = tabDesc;
	}

	@Column(name = "OBJ_NAME", length = 50)
	public String getObjName() {
		return this.objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
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