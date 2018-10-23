package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_MSG_NODE_TABS_REL database table.
 * 
 */
@Entity
@Table(name="TX_MSG_NODE_TABS_REL")
public class TxMsgNodeTabsRel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_MSG_NODE_TABS_REL_NODETABSRELID_GENERATOR")
//	@GenericGenerator(name = "TX_MSG_NODE_TABS_REL_NODETABSRELID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_MSG_NODE_TABS_REL") })
	@GenericGenerator(name = "TX_MSG_NODE_TABS_REL_NODETABSRELID_GENERATOR", strategy = "com.yuchengtech.emp.ecif.base.util.IncrementGenerator")
	@Column(name="NODE_TABS_REL_ID", unique=true, nullable=false)
	private Long nodeTabsRelId;

	@Column(name="COL_ID_1")
	private Integer colId1;

	@Column(name="COL_ID_2")
	private Integer colId2;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER", length=20)
	private String createUser;

	@Column(name="NODE_ID", nullable=false)
	private Long nodeId;

	@Column(length=20)
	private String rel;

	@Column(name="STATE", length=1)
	private String state;

	@Column(name="TAB_ID_1")
	private Integer tabId1;

	@Column(name="TAB_ID_2")
	private Integer tabId2;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER", length=20)
	private String updateUser;

    public TxMsgNodeTabsRel() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeTabsRelId() {
		return this.nodeTabsRelId;
	}

	public void setNodeTabsRelId(Long nodeTabsRelId) {
		this.nodeTabsRelId = nodeTabsRelId;
	}

	public Integer getColId1() {
		return this.colId1;
	}

	public void setColId1(Integer colId1) {
		this.colId1 = colId1;
	}

	public Integer getColId2() {
		return this.colId2;
	}

	public void setColId2(Integer colId2) {
		this.colId2 = colId2;
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

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getRel() {
		return this.rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getTabId1() {
		return this.tabId1;
	}

	public void setTabId1(Integer tabId1) {
		this.tabId1 = tabId1;
	}

	public Integer getTabId2() {
		return this.tabId2;
	}

	public void setTabId2(Integer tabId2) {
		this.tabId2 = tabId2;
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