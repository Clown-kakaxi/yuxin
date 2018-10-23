package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TxMsgNodeAttr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_MSG_NODE_ATTR")
public class TxMsgNodeAttr implements java.io.Serializable {

	// Fields

	private Long attrId;
	private Long nodeId;
	private String attrCode;
	private String attrName;
	private Long tabId;
	private Long colId;
	private String nulls;
	private String dataType;
	private Long dataLen;
	private String dataFmt;
	private String checkRule;
	private String cateId;
	private String defaultVal;
	private Long fkAttrId;
	private Long attrSeq;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;
	
	//以下属性为非持久化属性
	private String tabName;//表名
	private String colName;//字段名称
	private String fkAttrCode;//外键属性编码
	private Long fkNodeId;//外键节点ID

	// Constructors

	/** default constructor */
	public TxMsgNodeAttr() {
	}

	/** full constructor */
	public TxMsgNodeAttr(Long nodeId, String attrCode, String attrName,
			Long tabId, Long colId, String nulls, String dataType,
			Long dataLen, String dataFmt, String checkRule,
			String cateId, String defaultVal, Long fkAttrId,
			Long attrSeq, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.nodeId = nodeId;
		this.attrCode = attrCode;
		this.attrName = attrName;
		this.tabId = tabId;
		this.colId = colId;
		this.nulls = nulls;
		this.dataType = dataType;
		this.dataLen = dataLen;
		this.dataFmt = dataFmt;
		this.checkRule = checkRule;
		this.cateId = cateId;
		this.defaultVal = defaultVal;
		this.fkAttrId = fkAttrId;
		this.attrSeq = attrSeq;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "ATTR_ID", unique = true, nullable = false)
	public Long getAttrId() {
		return this.attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	@Column(name = "NODE_ID")
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "ATTR_CODE", length = 40)
	public String getAttrCode() {
		return this.attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	@Column(name = "ATTR_NAME")
	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
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

	@Column(name = "NULLS", length = 1)
	public String getNulls() {
		return this.nulls;
	}

	public void setNulls(String nulls) {
		this.nulls = nulls;
	}

	@Column(name = "DATA_TYPE", length = 4)
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "DATA_LEN", precision = 22)
	public Long getDataLen() {
		return this.dataLen;
	}

	public void setDataLen(Long dataLen) {
		this.dataLen = dataLen;
	}

	@Column(name = "DATA_FMT")
	public String getDataFmt() {
		return this.dataFmt;
	}

	public void setDataFmt(String dataFmt) {
		this.dataFmt = dataFmt;
	}

	@Column(name = "CHECK_RULE")
	public String getCheckRule() {
		return this.checkRule;
	}

	public void setCheckRule(String checkRule) {
		this.checkRule = checkRule;
	}

	@Column(name = "CATE_ID", length = 10)
	public String getCateId() {
		return this.cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	@Column(name = "DEFAULT_VAL")
	public String getDefaultVal() {
		return this.defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	@Column(name = "FK_ATTR_ID")
	public Long getFkAttrId() {
		return this.fkAttrId;
	}

	public void setFkAttrId(Long fkAttrId) {
		this.fkAttrId = fkAttrId;
	}

	@Column(name = "ATTR_SEQ", precision = 22)
	public Long getAttrSeq() {
		return this.attrSeq;
	}

	public void setAttrSeq(Long attrSeq) {
		this.attrSeq = attrSeq;
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
	public String getFkAttrCode() {
		return fkAttrCode;
	}

	public void setFkAttrCode(String fkAttrCode) {
		this.fkAttrCode = fkAttrCode;
	}

	@Transient
	public Long getFkNodeId() {
		return fkNodeId;
	}

	public void setFkNodeId(Long fkNodeId) {
		this.fkNodeId = fkNodeId;
	}
	
	

}