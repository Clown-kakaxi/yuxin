package com.yuchengtech.emp.ecif.rulemanage.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_BIZ_RULE_GROUP database table.
 * 
 */
@Entity
@Table(name="TX_BIZ_RULE_GROUP")
public class TxBizRuleGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RULE_GROUP_ID",unique=true, nullable=false)
	private Long ruleGroupId;

	@Column(name="RULE_GROUP_NO", length=32)
	private String ruleGroupNo;

	@Column(name="RULE_GROUP_NAME", length=40)
	private String ruleGroupName;

	@Column(name="RULE_GROUP_DESC", length=256)
	private String ruleGroupDesc;
	
	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getRuleGroupId() {
		return ruleGroupId;
	}

	public void setRuleGroupId(Long ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}

	public String getRuleGroupNo() {
		return ruleGroupNo;
	}

	public void setRuleGroupNo(String ruleGroupNo) {
		this.ruleGroupNo = ruleGroupNo;
	}

	public String getRuleGroupName() {
		return ruleGroupName;
	}

	public void setRuleGroupName(String ruleGroupName) {
		this.ruleGroupName = ruleGroupName;
	}

	public String getRuleGroupDesc() {
		return ruleGroupDesc;
	}

	public void setRuleGroupDesc(String ruleGroupDesc) {
		this.ruleGroupDesc = ruleGroupDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}