package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TxMsgNodeTabMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_MSG_NODE_TAB_MAP")
public class TxMsgNodeTabMap implements java.io.Serializable {

	// Fields

	private Long nodeTabMapId;
	private Long nodeId;
	private Long tabId;
	private String tabRoleTp;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;
	
	// 以下字段为非持久化字段
	private String tabName;// 表名
	private String objName;

	// Constructors

	/** default constructor */
	public TxMsgNodeTabMap() {
	}

	/** full constructor */
	public TxMsgNodeTabMap(Long nodeId, Long tabId,
			String tabRoleTp, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.nodeId = nodeId;
		this.tabId = tabId;
		this.tabRoleTp = tabRoleTp;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "NODE_TAB_MAP_ID", unique = true, nullable = false)
	public Long getNodeTabMapId() {
		return this.nodeTabMapId;
	}

	public void setNodeTabMapId(Long nodeTabMapId) {
		this.nodeTabMapId = nodeTabMapId;
	}

	@Column(name = "NODE_ID")
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "TAB_ID", precision = 22)
	public Long getTabId() {
		return this.tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	@Column(name = "TAB_ROLE_TP", length = 20)
	public String getTabRoleTp() {
		return this.tabRoleTp;
	}

	public void setTabRoleTp(String tabRoleTp) {
		this.tabRoleTp = tabRoleTp;
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

	@Transient
	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	@Transient
	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}
	
	

}