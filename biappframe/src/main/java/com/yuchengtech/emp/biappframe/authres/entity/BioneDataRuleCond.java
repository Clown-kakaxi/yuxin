package com.yuchengtech.emp.biappframe.authres.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the BIONE_DATA_RULE_COND database table.
 * 
 */
@Entity
@Table(name="BIONE_DATA_RULE_COND")
public class BioneDataRuleCond implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DATA_RULECOND_ID", unique=true, nullable=false, length=32)
	private String dataRulecondId;

	@Column(name="COND_NAME", length=100)
	private String condName;

	@Column(name="COND_NO", nullable=false, length=32)
	private String condNo;

	@Column(name="DATA_RULE_NO", nullable=false, length=32)
	private String dataRuleNo;

	@Column(length=500)
	private String remark;

	@Column(name="RULE_ITEM_NO", nullable=false, length=32)
	private String ruleItemNo;

	@Column(name="SQL_SCRIPT", nullable=false, length=1000)
	private String sqlScript;

    public BioneDataRuleCond() {
    }

	public String getDataRulecondId() {
		return this.dataRulecondId;
	}

	public void setDataRulecondId(String dataRulecondId) {
		this.dataRulecondId = dataRulecondId;
	}

	public String getCondName() {
		return this.condName;
	}

	public void setCondName(String condName) {
		this.condName = condName;
	}

	public String getCondNo() {
		return this.condNo;
	}

	public void setCondNo(String condNo) {
		this.condNo = condNo;
	}

	public String getDataRuleNo() {
		return this.dataRuleNo;
	}

	public void setDataRuleNo(String dataRuleNo) {
		this.dataRuleNo = dataRuleNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRuleItemNo() {
		return this.ruleItemNo;
	}

	public void setRuleItemNo(String ruleItemNo) {
		this.ruleItemNo = ruleItemNo;
	}

	public String getSqlScript() {
		return this.sqlScript;
	}

	public void setSqlScript(String sqlScript) {
		this.sqlScript = sqlScript;
	}

}