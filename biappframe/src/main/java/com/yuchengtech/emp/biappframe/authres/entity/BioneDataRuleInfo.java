package com.yuchengtech.emp.biappframe.authres.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the BIONE_DATA_RULE_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_DATA_RULE_INFO")
public class BioneDataRuleInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DATA_RULE_ID", unique=true, nullable=false, length=32)
	private String dataRuleId;

	@Column(name="BEAN_NAME", length=100)
	private String beanName;

	@Column(name="DATA_RULE_NAME", length=100)
	private String dataRuleName;

	@Column(name="DATA_RULE_NO", nullable=false, length=32)
	private String dataRuleNo;

	@Column(name="FILTER_SCRIPT", length=1000)
	private String filterScript;

	@Column(name="METHOD_NAME", length=100)
	private String methodName;

	@Column(length=500)
	private String remark;

	@Column(name="RES_NO", nullable=false, length=32)
	private String resNo;

    public BioneDataRuleInfo() {
    }

	public String getDataRuleId() {
		return this.dataRuleId;
	}

	public void setDataRuleId(String dataRuleId) {
		this.dataRuleId = dataRuleId;
	}

	public String getBeanName() {
		return this.beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getDataRuleName() {
		return this.dataRuleName;
	}

	public void setDataRuleName(String dataRuleName) {
		this.dataRuleName = dataRuleName;
	}

	public String getDataRuleNo() {
		return this.dataRuleNo;
	}

	public void setDataRuleNo(String dataRuleNo) {
		this.dataRuleNo = dataRuleNo;
	}

	public String getFilterScript() {
		return this.filterScript;
	}

	public void setFilterScript(String filterScript) {
		this.filterScript = filterScript;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResNo() {
		return this.resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

}