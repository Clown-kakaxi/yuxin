package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiGrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_GRADE")
public class HMCiGrade implements java.io.Serializable {

	// Fields

	private HMCiGradeId id;

	// Constructors

	/** default constructor */
	public HMCiGrade() {
	}

	/** full constructor */
	public HMCiGrade(HMCiGradeId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custGradeId", column = @Column(name = "CUST_GRADE_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "orgCode", column = @Column(name = "ORG_CODE", length = 20)),
			@AttributeOverride(name = "orgName", column = @Column(name = "ORG_NAME", length = 20)),
			@AttributeOverride(name = "custGradeType", column = @Column(name = "CUST_GRADE_TYPE", length = 20)),
			@AttributeOverride(name = "custGrade", column = @Column(name = "CUST_GRADE", length = 20)),
			@AttributeOverride(name = "evaluateDate", column = @Column(name = "EVALUATE_DATE", length = 7)),
			@AttributeOverride(name = "effectiveDate", column = @Column(name = "EFFECTIVE_DATE", length = 7)),
			@AttributeOverride(name = "expiredDate", column = @Column(name = "EXPIRED_DATE", length = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiGradeId getId() {
		return this.id;
	}

	public void setId(HMCiGradeId id) {
		this.id = id;
	}

}