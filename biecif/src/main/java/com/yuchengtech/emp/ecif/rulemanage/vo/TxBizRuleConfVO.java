package com.yuchengtech.emp.ecif.rulemanage.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;

import com.yuchengtech.emp.ecif.rulemanage.entity.TxBizRuleConf;

public class TxBizRuleConfVO extends TxBizRuleConf {

	/**
	 * 规则VO 
	 */
	private static final long serialVersionUID = 1L;
	private String parentRuleName;

	private Long ruleId;


	private String ruleNo;

	private String ruleVer;

	private String ruleName;
	
	private String ruleDefType;

	private String ruleBizType;


	private String ruleStat;


	private String ruleDesc;


	private String createOper;


	private Timestamp createTime;


	private String updateOper;


	private Timestamp updateTime;

	private String approvalOper;

	private Timestamp approvalTime;
	
	private Timestamp effectiveTime;
	
	private Timestamp expiredTime;
	
	private Long parentRuleId;

	private Long ruleGroupId;
	
	private String ruleDealType;
	
	private String ruleIntfType;

	private String rulePkgPath;
	
	private String ruleDealClass;
	
	private String ruleExpr;
	
	private String ruleExprDesc;
	
	
	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getRuleVer() {
		return ruleVer;
	}

	public void setRuleVer(String ruleVer) {
		this.ruleVer = ruleVer;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDefType() {
		return ruleDefType;
	}

	public void setRuleDefType(String ruleDefType) {
		this.ruleDefType = ruleDefType;
	}

	public String getRuleBizType() {
		return ruleBizType;
	}

	public void setRuleBizType(String ruleBizType) {
		this.ruleBizType = ruleBizType;
	}

	public String getRuleStat() {
		return ruleStat;
	}

	public void setRuleStat(String ruleStat) {
		this.ruleStat = ruleStat;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getCreateOper() {
		return createOper;
	}

	public void setCreateOper(String createOper) {
		this.createOper = createOper;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUpdateOper() {
		return updateOper;
	}

	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getApprovalOper() {
		return approvalOper;
	}

	public void setApprovalOper(String approvalOper) {
		this.approvalOper = approvalOper;
	}

	public Timestamp getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Timestamp approvalTime) {
		this.approvalTime = approvalTime;
	}

	public Timestamp getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Timestamp effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Timestamp getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Timestamp expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Long getParentRuleId() {
		return parentRuleId;
	}

	public void setParentRuleId(Long parentRuleId) {
		this.parentRuleId = parentRuleId;
	}

	public Long getRuleGroupId() {
		return ruleGroupId;
	}

	public void setRuleGroupId(Long ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}

	public String getRuleDealType() {
		return ruleDealType;
	}

	public void setRuleDealType(String ruleDealType) {
		this.ruleDealType = ruleDealType;
	}

	public String getRuleIntfType() {
		return ruleIntfType;
	}

	public void setRuleIntfType(String ruleIntfType) {
		this.ruleIntfType = ruleIntfType;
	}

	public String getRulePkgPath() {
		return rulePkgPath;
	}

	public void setRulePkgPath(String rulePkgPath) {
		this.rulePkgPath = rulePkgPath;
	}

	public String getRuleDealClass() {
		return ruleDealClass;
	}

	public void setRuleDealClass(String ruleDealClass) {
		this.ruleDealClass = ruleDealClass;
	}

	public String getRuleExpr() {
		return ruleExpr;
	}

	public void setRuleExpr(String ruleExpr) {
		this.ruleExpr = ruleExpr;
	}

	public String getRuleExprDesc() {
		return ruleExprDesc;
	}

	public void setRuleExprDesc(String ruleExprDesc) {
		this.ruleExprDesc = ruleExprDesc;
	}

	public String getParentRuleName() {
		return parentRuleName;
	}

	public void setParentRuleName(String parentRuleName) {
		this.parentRuleName = parentRuleName;
	}

}
