package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxBizRuleConf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_BIZ_RULE_CONF")
public class TxBizRuleConf implements java.io.Serializable {

	// Fields

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

	// Constructors

	/** default constructor */
	public TxBizRuleConf() {
	}

	/** full constructor */
	public TxBizRuleConf(String ruleNo, String ruleVer, String ruleName,
			String ruleDefType, String ruleBizType, String ruleStat,
			String ruleDesc, String createOper, Timestamp createTime,
			String updateOper, Timestamp updateTime, String approvalOper,
			Timestamp approvalTime, Timestamp effectiveTime,
			Timestamp expiredTime, Long parentRuleId,
			Long ruleGroupId, String ruleDealType, String ruleIntfType,
			String rulePkgPath, String ruleDealClass, String ruleExpr,
			String ruleExprDesc) {
		this.ruleNo = ruleNo;
		this.ruleVer = ruleVer;
		this.ruleName = ruleName;
		this.ruleDefType = ruleDefType;
		this.ruleBizType = ruleBizType;
		this.ruleStat = ruleStat;
		this.ruleDesc = ruleDesc;
		this.createOper = createOper;
		this.createTime = createTime;
		this.updateOper = updateOper;
		this.updateTime = updateTime;
		this.approvalOper = approvalOper;
		this.approvalTime = approvalTime;
		this.effectiveTime = effectiveTime;
		this.expiredTime = expiredTime;
		this.parentRuleId = parentRuleId;
		this.ruleGroupId = ruleGroupId;
		this.ruleDealType = ruleDealType;
		this.ruleIntfType = ruleIntfType;
		this.rulePkgPath = rulePkgPath;
		this.ruleDealClass = ruleDealClass;
		this.ruleExpr = ruleExpr;
		this.ruleExprDesc = ruleExprDesc;
	}

	// Property accessors
	@Id
	@Column(name = "RULE_ID", unique = true, nullable = false)
	public Long getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	@Column(name = "RULE_NO", length = 32)
	public String getRuleNo() {
		return this.ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	@Column(name = "RULE_VER", length = 20)
	public String getRuleVer() {
		return this.ruleVer;
	}

	public void setRuleVer(String ruleVer) {
		this.ruleVer = ruleVer;
	}

	@Column(name = "RULE_NAME")
	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name = "RULE_DEF_TYPE", length = 20)
	public String getRuleDefType() {
		return this.ruleDefType;
	}

	public void setRuleDefType(String ruleDefType) {
		this.ruleDefType = ruleDefType;
	}

	@Column(name = "RULE_BIZ_TYPE", length = 20)
	public String getRuleBizType() {
		return this.ruleBizType;
	}

	public void setRuleBizType(String ruleBizType) {
		this.ruleBizType = ruleBizType;
	}

	@Column(name = "RULE_STAT", length = 1)
	public String getRuleStat() {
		return this.ruleStat;
	}

	public void setRuleStat(String ruleStat) {
		this.ruleStat = ruleStat;
	}

	@Column(name = "RULE_DESC")
	public String getRuleDesc() {
		return this.ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	@Column(name = "CREATE_OPER", length = 20)
	public String getCreateOper() {
		return this.createOper;
	}

	public void setCreateOper(String createOper) {
		this.createOper = createOper;
	}

	@Column(name = "CREATE_TIME", length = 11)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_OPER", length = 20)
	public String getUpdateOper() {
		return this.updateOper;
	}

	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper;
	}

	@Column(name = "UPDATE_TIME", length = 11)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "APPROVAL_OPER", length = 20)
	public String getApprovalOper() {
		return this.approvalOper;
	}

	public void setApprovalOper(String approvalOper) {
		this.approvalOper = approvalOper;
	}

	@Column(name = "APPROVAL_TIME", length = 11)
	public Timestamp getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Timestamp approvalTime) {
		this.approvalTime = approvalTime;
	}

	@Column(name = "EFFECTIVE_TIME", length = 11)
	public Timestamp getEffectiveTime() {
		return this.effectiveTime;
	}

	public void setEffectiveTime(Timestamp effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	@Column(name = "EXPIRED_TIME", length = 11)
	public Timestamp getExpiredTime() {
		return this.expiredTime;
	}

	public void setExpiredTime(Timestamp expiredTime) {
		this.expiredTime = expiredTime;
	}

	@Column(name = "PARENT_RULE_ID")
	public Long getParentRuleId() {
		return this.parentRuleId;
	}

	public void setParentRuleId(Long parentRuleId) {
		this.parentRuleId = parentRuleId;
	}

	@Column(name = "RULE_GROUP_ID")
	public Long getRuleGroupId() {
		return this.ruleGroupId;
	}

	public void setRuleGroupId(Long ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}

	@Column(name = "RULE_DEAL_TYPE", length = 20)
	public String getRuleDealType() {
		return this.ruleDealType;
	}

	public void setRuleDealType(String ruleDealType) {
		this.ruleDealType = ruleDealType;
	}

	@Column(name = "RULE_INTF_TYPE", length = 20)
	public String getRuleIntfType() {
		return this.ruleIntfType;
	}

	public void setRuleIntfType(String ruleIntfType) {
		this.ruleIntfType = ruleIntfType;
	}

	@Column(name = "RULE_PKG_PATH")
	public String getRulePkgPath() {
		return this.rulePkgPath;
	}

	public void setRulePkgPath(String rulePkgPath) {
		this.rulePkgPath = rulePkgPath;
	}

	@Column(name = "RULE_DEAL_CLASS", length = 128)
	public String getRuleDealClass() {
		return this.ruleDealClass;
	}

	public void setRuleDealClass(String ruleDealClass) {
		this.ruleDealClass = ruleDealClass;
	}

	@Column(name = "RULE_EXPR", length = 512)
	public String getRuleExpr() {
		return this.ruleExpr;
	}

	public void setRuleExpr(String ruleExpr) {
		this.ruleExpr = ruleExpr;
	}

	@Column(name = "RULE_EXPR_DESC")
	public String getRuleExprDesc() {
		return this.ruleExprDesc;
	}

	public void setRuleExprDesc(String ruleExprDesc) {
		this.ruleExprDesc = ruleExprDesc;
	}

}