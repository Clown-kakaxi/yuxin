package com.yuchengtech.emp.ecif.core.entity;


import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the TAB_DEF database table.
 * 
 */
@Entity
@Table(name="TX_TAB_DEF")
public class TabDef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TAB_DEF_TABID_GENERATOR")
	@GenericGenerator(name = "TAB_DEF_TABID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_TAB_DEF") })
	@Column(name="TAB_ID")
	private Long tabId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="OBJ_NAME")
	private String objName;

	@Column(name="STATE")
	private String state;

	@Column(name="TAB_DESC")
	private String tabDesc;

	@Column(name="TAB_NAME")
	private String tabName;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

	@Column(name="TAB_SCHEMA")
	private String tabSchema;
	
    public String getTabSchema() {
		return tabSchema;
	}

	public void setTabSchema(String tabSchema) {
		this.tabSchema = tabSchema;
	}

	public TabDef() {
    }

	public Long getTabId() {
		return this.tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
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

	public String getObjName() {
		return this.objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTabDesc() {
		return this.tabDesc;
	}

	public void setTabDesc(String tabDesc) {
		this.tabDesc = tabDesc;
	}

	public String getTabName() {
		return this.tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
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