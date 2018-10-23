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

public class TxMsgNodeAttrVO implements Serializable {
	private static final Long serialVersionUID = 1L;
	private Long attrId;
	private String attrCode;
	private String attrName;
	private Integer attrSeq;
	private String cateId;
	private String checkRule;
	private Integer colId;
	private Timestamp createTm;
	private String createUser;
	private String dataFmt;
	private Integer dataLen;
	private String dataType;
	private String defaultVal;
	private Long fkAttrId;
	private String fkAttrName;	
	private Long nodeId;
	private String nulls;
	private String state;
	private Integer tabId;
	private Timestamp updateTm;
	private String updateUser;
	private String tabDesc;
	private String colChName;		
	private String ctRule;
	private String ctDesc;
	private String isSelected;	
	private String checkRuleDesc;
	
	public String getCheckRuleDesc() {
		return checkRuleDesc;
	}

	public void setCheckRuleDesc(String checkRuleDesc) {
		this.checkRuleDesc = checkRuleDesc;
	}

	public String getFkAttrName() {
		return fkAttrName;
	}

	public void setFkAttrName(String fkAttrName) {
		this.fkAttrName = fkAttrName;
	}
	
	
	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	
	public String getCtRule() {
		return ctRule;
	}

	public void setCtRule(String ctRule) {
		this.ctRule = ctRule;
	}

	public String getCtDesc() {
		return ctDesc;
	}

	public void setCtDesc(String ctDesc) {
		this.ctDesc = ctDesc;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Integer getAttrSeq() {
		return attrSeq;
	}

	public void setAttrSeq(Integer attrSeq) {
		this.attrSeq = attrSeq;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getCheckRule() {
		return checkRule;
	}

	public void setCheckRule(String checkRule) {
		this.checkRule = checkRule;
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

	public String getDataFmt() {
		return dataFmt;
	}

	public void setDataFmt(String dataFmt) {
		this.dataFmt = dataFmt;
	}

	public Integer getDataLen() {
		return dataLen;
	}

	public void setDataLen(Integer dataLen) {
		this.dataLen = dataLen;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	
	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getFkAttrId() {
		return fkAttrId;
	}

	public void setFkAttrId(Long fkAttrId) {
		this.fkAttrId = fkAttrId;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getNulls() {
		return nulls;
	}

	public void setNulls(String nulls) {
		this.nulls = nulls;
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

    public TxMsgNodeAttrVO() {
    }


}