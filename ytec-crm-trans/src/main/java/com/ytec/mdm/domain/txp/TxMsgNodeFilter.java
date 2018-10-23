package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TxMsgNodeFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_MSG_NODE_FILTER")
public class TxMsgNodeFilter implements java.io.Serializable {

	// Fields

	private Long filterId;
	private Long nodeId;
	private Long tabId;
	private Long colId;
	private String rel;
	private Long attrId;
	private String filterConditions;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// 以下字段为非持久化字段
	private String tabName;
	private String colName;
	private String reqAttrCode;// 查询条件对应的请求属性编码
	private String reqAttrDataType;// 查询条件对应的请求属性的类型
	private String reqAttrDataFmt;// 查询条件对应的请求属性的格式化类型
	private String reqAttrNullAble;//查询条件对应的请求属性是否可空
	private String defaultVal;     //查询条件对应的请求属性的默认值
 
	// Constructors

	/** default constructor */
	public TxMsgNodeFilter() {
	}

	/** full constructor */
	public TxMsgNodeFilter(Long nodeId, Long tabId, Long colId, String rel,
			Long attrId,String filterConditions, String state, Timestamp createTm, String createUser,
			Timestamp updateTm, String updateUser) {
		this.nodeId = nodeId;
		this.tabId = tabId;
		this.colId = colId;
		this.rel = rel;
		this.attrId = attrId;
		this.filterConditions=filterConditions;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "FILTER_ID", unique = true, nullable = false)
	public Long getFilterId() {
		return this.filterId;
	}

	public void setFilterId(Long filterId) {
		this.filterId = filterId;
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

	@Column(name = "COL_ID", precision = 22)
	public Long getColId() {
		return this.colId;
	}

	public void setColId(Long colId) {
		this.colId = colId;
	}

	@Column(name = "REL", length = 20)
	public String getRel() {
		return this.rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@Column(name = "ATTR_ID")
	public Long getAttrId() {
		return this.attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}
	
	@Column(name = "FILTER_CONDITIONS", length = 255)
	public String getFilterConditions() {
		return filterConditions;
	}

	public void setFilterConditions(String filterConditions) {
		this.filterConditions = filterConditions;
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
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	@Transient
	public String getReqAttrCode() {
		return reqAttrCode;
	}

	public void setReqAttrCode(String reqAttrCode) {
		this.reqAttrCode = reqAttrCode;
	}

	@Transient
	public String getReqAttrDataType() {
		return reqAttrDataType;
	}

	public void setReqAttrDataType(String reqAttrDataType) {
		this.reqAttrDataType = reqAttrDataType;
	}

	@Transient
	public String getReqAttrDataFmt() {
		return reqAttrDataFmt;
	}

	public void setReqAttrDataFmt(String reqAttrDataFmt) {
		this.reqAttrDataFmt = reqAttrDataFmt;
	}

	@Transient
	public String getReqAttrNullAble() {
		return reqAttrNullAble;
	}

	public void setReqAttrNullAble(String reqAttrNullAble) {
		this.reqAttrNullAble = reqAttrNullAble;
	}

	@Transient
	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	
	
	
	

}