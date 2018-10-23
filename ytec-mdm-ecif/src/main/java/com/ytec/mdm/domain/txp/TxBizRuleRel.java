package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxBizRuleRel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_BIZ_RULE_REL")
public class TxBizRuleRel implements java.io.Serializable {

	// Fields

	private Long ruleRelId;
	private Long srcRuleId;
	private Long destRuleId;
	private String relType;
	private String relStat;
	private Timestamp effectiveTime;
	private Timestamp expiredTime;

	// Constructors

	/** default constructor */
	public TxBizRuleRel() {
	}

	/** full constructor */
	public TxBizRuleRel(Long srcRuleId, Long destRuleId,
			String relType, String relStat, Timestamp effectiveTime,
			Timestamp expiredTime) {
		this.srcRuleId = srcRuleId;
		this.destRuleId = destRuleId;
		this.relType = relType;
		this.relStat = relStat;
		this.effectiveTime = effectiveTime;
		this.expiredTime = expiredTime;
	}

	// Property accessors
	@Id
	@Column(name = "RULE_REL_ID", unique = true, nullable = false)
	public Long getRuleRelId() {
		return this.ruleRelId;
	}

	public void setRuleRelId(Long ruleRelId) {
		this.ruleRelId = ruleRelId;
	}

	@Column(name = "SRC_RULE_ID")
	public Long getSrcRuleId() {
		return this.srcRuleId;
	}

	public void setSrcRuleId(Long srcRuleId) {
		this.srcRuleId = srcRuleId;
	}

	@Column(name = "DEST_RULE_ID")
	public Long getDestRuleId() {
		return this.destRuleId;
	}

	public void setDestRuleId(Long destRuleId) {
		this.destRuleId = destRuleId;
	}

	@Column(name = "REL_TYPE", length = 20)
	public String getRelType() {
		return this.relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	@Column(name = "REL_STAT", length = 20)
	public String getRelStat() {
		return this.relStat;
	}

	public void setRelStat(String relStat) {
		this.relStat = relStat;
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

}