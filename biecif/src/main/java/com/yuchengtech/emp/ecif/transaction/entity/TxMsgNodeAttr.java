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
 * The persistent class for the TX_MSG_NODE_ATTR database table.
 * 
 */
@Entity
@Table(name="TX_MSG_NODE_ATTR")
public class TxMsgNodeAttr implements Serializable {
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_MSG_NODE_ATTR_ATTRID_GENERATOR")
//	@GenericGenerator(name = "TX_MSG_NODE_ATTR_ATTRID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_MSG_NODE_ATTR") })
	@GenericGenerator(name = "TX_MSG_NODE_ATTR_ATTRID_GENERATOR", strategy = "com.yuchengtech.emp.ecif.base.util.IncrementGenerator")
	@Column(name="ATTR_ID", unique=true, nullable=false)
	private Long attrId;

	@Column(name="ATTR_CODE", nullable=false, length=40)
	private String attrCode;

	@Column(name="ATTR_NAME", length=255)
	private String attrName;

	@Column(name="ATTR_SEQ")
	private Integer attrSeq;

	@Column(name="CATE_ID")
	private String cateId;

	@Column(name="CHECK_RULE", length=255)
	private String checkRule;

	@Column(name="COL_ID")
	private Integer colId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER", length=20)
	private String createUser;

	@Column(name="DATA_FMT", length=255)
	private String dataFmt;

	@Column(name="DATA_LEN")
	private Integer dataLen;

	@Column(name="DATA_TYPE", length=4)
	private String dataType;

	@Column(name="DEFAULT_VAL", length=255)
	private String defaultVal;

	@Column(name="FK_ATTR_ID")
	private Long fkAttrId;

	@Column(name="NODE_ID", nullable=false)
	private Long nodeId;

	@Column(name="NULLS", length=1)
	private String nulls;

	@Column(name="STATE", length=1)
	private String state;

	@Column(name="TAB_ID")
	private Integer tabId;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER", length=20)
	private String updateUser;

    public TxMsgNodeAttr() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
    public Long getAttrId() {
		return this.attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public String getAttrCode() {
		return this.attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Integer getAttrSeq() {
		return this.attrSeq;
	}

	public void setAttrSeq(Integer attrSeq) {
		this.attrSeq = attrSeq;
	}
	
	public String getCateId() {
		return this.cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getCheckRule() {
		return this.checkRule;
	}

	public void setCheckRule(String checkRule) {
		this.checkRule = checkRule;
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

	public String getDataFmt() {
		return this.dataFmt;
	}

	public void setDataFmt(String dataFmt) {
		this.dataFmt = dataFmt;
	}

	public Integer getDataLen() {
		return this.dataLen;
	}

	public void setDataLen(Integer dataLen) {
		this.dataLen = dataLen;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDefaultVal() {
		return this.defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	
	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getFkAttrId() {
		return this.fkAttrId;
	}

	public void setFkAttrId(Long fkAttrId) {
		this.fkAttrId = fkAttrId;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getNulls() {
		return this.nulls;
	}

	public void setNulls(String nulls) {
		this.nulls = nulls;
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