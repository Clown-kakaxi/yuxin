package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.sql.Timestamp;


/**
 * The persistent class for the TX_MSG_NODE_ATTR database table.
 * 
 */

public class TxMsgNodeFilterVO implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	private Long filterId;
	private Long attrId;
	private Integer colId;
	private Timestamp createTm;
	private String createUser;
	private Long nodeId;
	private String rel;
	private String state;
	private Integer tabId;
	private Timestamp updateTm;
	private String updateUser;	
	private String tabDesc;
	private String colChName;		
	private String attrName;	
	private String filterConditions;

    public String getFilterConditions() {
		return filterConditions;
	}


	public void setFilterConditions(String filterConditions) {
		this.filterConditions = filterConditions;
	}


	@JsonSerialize(using=BioneLongSerializer.class)
    public Long getFilterId() {
		return filterId;
	}


	public String getAttrName() {
		return attrName;
	}


	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}


	public void setFilterId(Long filterId) {
		this.filterId = filterId;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getAttrId() {
		return attrId;
	}


	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}


	public Integer getColId() {
		return colId;
	}


	public void setColId(Integer colId) {
		this.colId = colId;
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


	public Integer getTabId() {
		return tabId;
	}


	public void setTabId(Integer tabId) {
		this.tabId = tabId;
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


	public String getTabDesc() {
		return tabDesc;
	}


	public void setTabDesc(String tabDesc) {
		this.tabDesc = tabDesc;
	}


	public String getColChName() {
		return colChName;
	}


	public void setColChName(String colChName) {
		this.colChName = colChName;
	}


	public TxMsgNodeFilterVO() {
    }


}