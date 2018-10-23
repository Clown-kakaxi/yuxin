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
 * The persistent class for the TX_MSG_NODE_FILTER database table.
 * 
 */
@Entity
@Table(name="TX_MSG_NODE_FILTER")
public class TxMsgNodeFilter implements Serializable {
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_MSG_NODE_FILTER_FILTERID_GENERATOR")
//	@GenericGenerator(name = "TX_MSG_NODE_FILTER_FILTERID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_MSG_NODE_FILTER") })
	@GenericGenerator(name = "TX_MSG_NODE_FILTER_FILTERID_GENERATOR", strategy = "com.yuchengtech.emp.ecif.base.util.IncrementGenerator")
	@Column(name="FILTER_ID", unique=true, nullable=false)
	private Long filterId;

	@Column(name="ATTR_ID")
	private Long attrId;

	@Column(name="COL_ID")
	private Integer colId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER", length=20)
	private String createUser;

	@Column(name="NODE_ID")
	private Long nodeId;

	@Column(length=20)
	private String rel;

	@Column(name="STATE", length=1)
	private String state;

	@Column(name="TAB_ID")
	private Integer tabId;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER", length=20)
	private String updateUser;

	@Column(name="FILTER_CONDITIONS", length=255)
	private String filterConditions;
	
    public String getFilterConditions() {
		return filterConditions;
	}

	public void setFilterConditions(String filterConditions) {
		this.filterConditions = filterConditions;
	}

	public TxMsgNodeFilter() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getFilterId() {
		return this.filterId;
	}

	public void setFilterId(Long filterId) {
		this.filterId = filterId;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getAttrId() {
		return this.attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public Integer getColId() {
		return this.colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
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

	public Integer getTabId() {
		return this.tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
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