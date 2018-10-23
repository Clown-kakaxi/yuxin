package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxBizRuleGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_BIZ_RULE_GROUP")
public class TxBizRuleGroup implements java.io.Serializable {

	// Fields

	private Long ruleGroupId;
	private String ruleGroupNo;
	private String ruleGroupName;
	private String ruleGroupDesc;

	// Constructors

	/** default constructor */
	public TxBizRuleGroup() {
	}

	/** full constructor */
	public TxBizRuleGroup(String ruleGroupNo, String ruleGroupName,
			String ruleGroupDesc) {
		this.ruleGroupNo = ruleGroupNo;
		this.ruleGroupName = ruleGroupName;
		this.ruleGroupDesc = ruleGroupDesc;
	}

	// Property accessors
	@Id
	@Column(name = "RULE_GROUP_ID", unique = true, nullable = false)
	public Long getRuleGroupId() {
		return this.ruleGroupId;
	}

	public void setRuleGroupId(Long ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}

	@Column(name = "RULE_GROUP_NO", length = 32)
	public String getRuleGroupNo() {
		return this.ruleGroupNo;
	}

	public void setRuleGroupNo(String ruleGroupNo) {
		this.ruleGroupNo = ruleGroupNo;
	}

	@Column(name = "RULE_GROUP_NAME", length = 40)
	public String getRuleGroupName() {
		return this.ruleGroupName;
	}

	public void setRuleGroupName(String ruleGroupName) {
		this.ruleGroupName = ruleGroupName;
	}

	@Column(name = "RULE_GROUP_DESC")
	public String getRuleGroupDesc() {
		return this.ruleGroupDesc;
	}

	public void setRuleGroupDesc(String ruleGroupDesc) {
		this.ruleGroupDesc = ruleGroupDesc;
	}

}