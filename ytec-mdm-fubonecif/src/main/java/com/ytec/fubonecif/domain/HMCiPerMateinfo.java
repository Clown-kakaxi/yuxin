package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerMateinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_MATEINFO")
public class HMCiPerMateinfo implements java.io.Serializable {

	// Fields

	private HMCiPerMateinfoId id;

	// Constructors

	/** default constructor */
	public HMCiPerMateinfo() {
	}

	/** full constructor */
	public HMCiPerMateinfo(HMCiPerMateinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "custIdMate", column = @Column(name = "CUST_ID_MATE", length = 20)),
			@AttributeOverride(name = "mateName", column = @Column(name = "MATE_NAME", length = 80)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "marrCertNo", column = @Column(name = "MARR_CERT_NO", length = 40)),
			@AttributeOverride(name = "gender", column = @Column(name = "GENDER", length = 20)),
			@AttributeOverride(name = "birthday", column = @Column(name = "BIRTHDAY", length = 7)),
			@AttributeOverride(name = "citizenship", column = @Column(name = "CITIZENSHIP", length = 20)),
			@AttributeOverride(name = "nationality", column = @Column(name = "NATIONALITY", length = 20)),
			@AttributeOverride(name = "nativeplace", column = @Column(name = "NATIVEPLACE", length = 20)),
			@AttributeOverride(name = "household", column = @Column(name = "HOUSEHOLD", length = 20)),
			@AttributeOverride(name = "hukouPlace", column = @Column(name = "HUKOU_PLACE", length = 60)),
			@AttributeOverride(name = "health", column = @Column(name = "HEALTH", length = 20)),
			@AttributeOverride(name = "highestSchooling", column = @Column(name = "HIGHEST_SCHOOLING", length = 20)),
			@AttributeOverride(name = "workUnit", column = @Column(name = "WORK_UNIT", length = 100)),
			@AttributeOverride(name = "workUnitChar", column = @Column(name = "WORK_UNIT_CHAR", length = 20)),
			@AttributeOverride(name = "workStartDate", column = @Column(name = "WORK_START_DATE", length = 7)),
			@AttributeOverride(name = "industry", column = @Column(name = "INDUSTRY", length = 20)),
			@AttributeOverride(name = "career", column = @Column(name = "CAREER", length = 20)),
			@AttributeOverride(name = "duty", column = @Column(name = "DUTY", length = 20)),
			@AttributeOverride(name = "jobTitle", column = @Column(name = "JOB_TITLE", length = 20)),
			@AttributeOverride(name = "monthIncomeType", column = @Column(name = "MONTH_INCOME_TYPE", length = 20)),
			@AttributeOverride(name = "monthIncome", column = @Column(name = "MONTH_INCOME", precision = 17)),
			@AttributeOverride(name = "annualIncomeType", column = @Column(name = "ANNUAL_INCOME_TYPE", length = 20)),
			@AttributeOverride(name = "annualIncome", column = @Column(name = "ANNUAL_INCOME", precision = 17)),
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
	public HMCiPerMateinfoId getId() {
		return this.id;
	}

	public void setId(HMCiPerMateinfoId id) {
		this.id = id;
	}

}