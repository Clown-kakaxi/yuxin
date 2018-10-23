package com.yuchengtech.emp.biappframe.authres.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the BIONE_DATA_RULE_ITEM database table.
 * 
 */
@Entity
@Table(name="BIONE_DATA_RULE_ITEM")
public class BioneDataRuleItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DATA_RULE_ITEM_ID", unique=true, nullable=false, length=32)
	private String dataRuleItemId;

	@Column(name="DATA_RULE_NO", nullable=false, length=32)
	private String dataRuleNo;

	@Column(name="IF_ALLOW_AUTH", length=1)
	private String ifAllowAuth;

	@Column(length=10)
	private String operator;

	@Column(length=500)
	private String remark;

	@Column(name="RULE_ITEM_NAME", length=100)
	private String ruleItemName;

	@Column(name="RULE_ITEM_NO", nullable=false, length=32)
	private String ruleItemNo;

    public BioneDataRuleItem() {
    }

	public String getDataRuleItemId() {
		return this.dataRuleItemId;
	}

	public void setDataRuleItemId(String dataRuleItemId) {
		this.dataRuleItemId = dataRuleItemId;
	}

	public String getDataRuleNo() {
		return this.dataRuleNo;
	}

	public void setDataRuleNo(String dataRuleNo) {
		this.dataRuleNo = dataRuleNo;
	}

	public String getIfAllowAuth() {
		return this.ifAllowAuth;
	}

	public void setIfAllowAuth(String ifAllowAuth) {
		this.ifAllowAuth = ifAllowAuth;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRuleItemName() {
		return this.ruleItemName;
	}

	public void setRuleItemName(String ruleItemName) {
		this.ruleItemName = ruleItemName;
	}

	public String getRuleItemNo() {
		return this.ruleItemNo;
	}

	public void setRuleItemNo(String ruleItemNo) {
		this.ruleItemNo = ruleItemNo;
	}

}