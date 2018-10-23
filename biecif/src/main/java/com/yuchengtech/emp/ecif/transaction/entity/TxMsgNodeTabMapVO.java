package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.sql.Timestamp;


/**
 * The persistent class for the TX_MSG_NODE_TAB_MAP database table.
 * 
 */

public class TxMsgNodeTabMapVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private long nodeTabMapId;
	private Timestamp createTm;
	private String createUser;
	private Long nodeId;
	private String state;
	private Integer tabId;
	private String tabRoleTp;
	private Timestamp updateTm;
	private String updateUser;
	private String tabName;
	private String tabDesc;

    @JsonSerialize(using=BioneLongSerializer.class)
    public long getNodeTabMapId() {
		return nodeTabMapId;
	}

	public void setNodeTabMapId(long nodeTabMapId) {
		this.nodeTabMapId = nodeTabMapId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getTabId() {
		return tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}

	public String getTabRoleTp() {
		return tabRoleTp;
	}

	public void setTabRoleTp(String tabRoleTp) {
		this.tabRoleTp = tabRoleTp;
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

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTabDesc() {
		return tabDesc;
	}

	public void setTabDesc(String tabDesc) {
		this.tabDesc = tabDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TxMsgNodeTabMapVO() {
    }



}