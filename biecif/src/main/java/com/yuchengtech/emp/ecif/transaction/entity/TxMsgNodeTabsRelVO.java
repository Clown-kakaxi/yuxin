package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.sql.Timestamp;


/**
 * The persistent class for the TX_MSG_NODE_TABS_REL database table.
 * 
 */

public class TxMsgNodeTabsRelVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long nodeTabsRelId;
	private Integer colId1;
	private Integer colId2;
	private Timestamp createTm;
	private String createUser;
	private Long nodeId;
	private String rel;
	private String state;
	private Integer tabId1;
	private Integer tabId2;
	private Timestamp updateTm;
	private String updateUser;
	private String tabDesc1;
	private String tabDesc2;
	private String colName1;
	private String colName2;

    public String getTabDesc1() {
		return tabDesc1;
	}


	public void setTabDesc1(String tabDesc1) {
		this.tabDesc1 = tabDesc1;
	}


	public String getTabDesc2() {
		return tabDesc2;
	}


	public void setTabDesc2(String tabDesc2) {
		this.tabDesc2 = tabDesc2;
	}


	public String getColName1() {
		return colName1;
	}


	public void setColName1(String colName1) {
		this.colName1 = colName1;
	}


	public String getColName2() {
		return colName2;
	}


	public void setColName2(String colName2) {
		this.colName2 = colName2;
	}


	public TxMsgNodeTabsRelVO() {
    }


    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeTabsRelId() {
		return nodeTabsRelId;
	}

	public void setNodeTabsRelId(Long nodeTabsRelId) {
		this.nodeTabsRelId = nodeTabsRelId;
	}

	public Integer getColId1() {
		return colId1;
	}

	public void setColId1(Integer colId1) {
		this.colId1 = colId1;
	}

	public Integer getColId2() {
		return colId2;
	}

	public void setColId2(Integer colId2) {
		this.colId2 = colId2;
	}

	public Timestamp getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getTabId1() {
		return tabId1;
	}

	public void setTabId1(Integer tabId1) {
		this.tabId1 = tabId1;
	}

	public Integer getTabId2() {
		return tabId2;
	}

	public void setTabId2(Integer tabId2) {
		this.tabId2 = tabId2;
	}

	public Timestamp getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}