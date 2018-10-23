package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerRelativeinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_RELATIVEINFO")
public class HMCiPerRelativeinfo implements java.io.Serializable {

	// Fields

	private HMCiPerRelativeinfoId id;

	// Constructors

	/** default constructor */
	public HMCiPerRelativeinfo() {
	}

	/** full constructor */
	public HMCiPerRelativeinfo(HMCiPerRelativeinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "relativeId", column = @Column(name = "RELATIVE_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "relativeType", column = @Column(name = "RELATIVE_TYPE", length = 20)),
			@AttributeOverride(name = "relativeName", column = @Column(name = "RELATIVE_NAME", length = 80)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "gender", column = @Column(name = "GENDER", length = 20)),
			@AttributeOverride(name = "birthday", column = @Column(name = "BIRTHDAY", length = 7)),
			@AttributeOverride(name = "health", column = @Column(name = "HEALTH", length = 80)),
			@AttributeOverride(name = "monthIncomeScope", column = @Column(name = "MONTH_INCOME_SCOPE", length = 20)),
			@AttributeOverride(name = "monthIncome", column = @Column(name = "MONTH_INCOME", precision = 17)),
			@AttributeOverride(name = "officeTel", column = @Column(name = "OFFICE_TEL", length = 20)),
			@AttributeOverride(name = "homeTel", column = @Column(name = "HOME_TEL", length = 20)),
			@AttributeOverride(name = "mobile", column = @Column(name = "MOBILE", length = 20)),
			@AttributeOverride(name = "email", column = @Column(name = "EMAIL", length = 40)),
			@AttributeOverride(name = "address", column = @Column(name = "ADDRESS", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerRelativeinfoId getId() {
		return this.id;
	}

	public void setId(HMCiPerRelativeinfoId id) {
		this.id = id;
	}

}