package com.yuchengtech.emp.ecif.rulemanage.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_BIZ_RULE_CONF database table.
 * 
 */
@Entity
@Table(name="TX_BIZ_RULE_CONF")
public class TxBizRuleConf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RULE_ID",unique=true, nullable=false)
	private Long ruleId;

	@Column(name="RULE_NO", length=32)
	private String ruleNo;

	@Column(name="RULE_VER", length=20)
	private String ruleVer;

	@Column(name="RULE_NAME", length=256)
	private String ruleName;
	
	@Column(name="RULE_DEF_TYPE", length=20)
	private String ruleDefType;

	@Column(name="RULE_BIZ_TYPE", length=20)
	private String ruleBizType;

	@Column(name="RULE_STAT", length=5)
	private String ruleStat;
 
	@Column(name="RULE_DESC", length=256)
	private String ruleDesc;

	@Column(name="CREATE_OPER", length=20)
	private String createOper;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="UPDATE_OPER", length=20)
	private String updateOper;

	@Column(name="UPDATE_TIME")
	private Timestamp updateTime;

	@Column(name="APPROVAL_OPER", length=20)
	private String approvalOper;

	@Column(name="APPROVAL_TIME")
	private Timestamp approvalTime;
	
	@Column(name="EFFECTIVE_TIME")
	private Timestamp effectiveTime;
	
	@Column(name="EXPIRED_TIME")
	private Timestamp expiredTime;
	
	@Column(name="PARENT_RULE_ID", length=256)
	private Long parentRuleId;
	
	@Column(name="RULE_GROUP_ID", length=256)
	private Long ruleGroupId;
	
	@Column(name="RULE_DEAL_TYPE", length=20)
	private String ruleDealType;
	
	@Column(name="RULE_INTF_TYPE", length=20)
	private String ruleIntfType;

	@Column(name="RULE_PKG_PATH", length=256)
	private String rulePkgPath;
	
	@Column(name="RULE_DEAL_CLASS", length=128)
	private String ruleDealClass;
	
	@Column(name="RULE_EXPR", length=512)
	private String ruleExpr;
	
	@Column(name="RULE_EXPR_DESC", length=256)
	private String ruleExprDesc;

	@JsonSerialize(using=BioneLongSerializer.class)
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
	
	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getParentRuleId() {
		return parentRuleId;
	}
	
	public void setParentRuleId(Long parentRuleId) {
		this.parentRuleId = parentRuleId;
	}
	
	@JsonSerialize(using=BioneLongSerializer.class)
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
	




}