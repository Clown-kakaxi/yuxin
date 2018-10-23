package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TxMsgNodeTabsRel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_MSG_NODE_TABS_REL")
public class TxMsgNodeTabsRel implements java.io.Serializable {

	// Fields

	private Long nodeTabsRelId;
	private Long nodeId;
	private Long tabId1;
	private Long colId1;
	private String rel;
	private Long tabId2;
	private Long colId2;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;
	
	//以下字段为非持久化字段
	private String tabName1;
	private String colName1;
	private String tabName2;
	private String colName2;

	// Constructors

	/** default constructor */
	public TxMsgNodeTabsRel() {
	}

	/** full constructor */
	public TxMsgNodeTabsRel(Long nodeId, Long tabId1,
			Long colId1, String rel, Long tabId2,
			Long colId2, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.nodeId = nodeId;
		this.tabId1 = tabId1;
		this.colId1 = colId1;
		this.rel = rel;
		this.tabId2 = tabId2;
		this.colId2 = colId2;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "NODE_TABS_REL_ID", unique = true, nullable = false)
	public Long getNodeTabsRelId() {
		return this.nodeTabsRelId;
	}

	public void setNodeTabsRelId(Long nodeTabsRelId) {
		this.nodeTabsRelId = nodeTabsRelId;
	}

	@Column(name = "NODE_ID")
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "TAB_ID_1", precision = 22)
	public Long getTabId1() {
		return this.tabId1;
	}

	public void setTabId1(Long tabId1) {
		this.tabId1 = tabId1;
	}

	@Column(name = "COL_ID_1", precision = 22)
	public Long getColId1() {
		return this.colId1;
	}

	public void setColId1(Long colId1) {
		this.colId1 = colId1;
	}

	@Column(name = "REL", length = 20)
	public String getRel() {
		return this.rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@Column(name = "TAB_ID_2", precision = 22)
	public Long getTabId2() {
		return this.tabId2;
	}

	public void setTabId2(Long tabId2) {
		this.tabId2 = tabId2;
	}

	@Column(name = "COL_ID_2", precision = 22)
	public Long getColId2() {
		return this.colId2;
	}

	public void setColId2(Long colId2) {
		this.colId2 = colId2;
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
	public String getTabName1() {
		return tabName1;
	}

	public void setTabName1(String tabName1) {
		this.tabName1 = tabName1;
	}

	@Transient
	public String getColName1() {
		return colName1;
	}

	public void setColName1(String colName1) {
		this.colName1 = colName1;
	}

	@Transient
	public String getTabName2() {
		return tabName2;
	}

	public void setTabName2(String tabName2) {
		this.tabName2 = tabName2;
	}

	@Transient
	public String getColName2() {
		return colName2;
	}

	public void setColName2(String colName2) {
		this.colName2 = colName2;
	}
	
	

}