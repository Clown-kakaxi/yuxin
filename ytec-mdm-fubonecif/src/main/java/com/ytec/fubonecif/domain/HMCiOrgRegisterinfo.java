package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgRegisterinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_REGISTERINFO")
public class HMCiOrgRegisterinfo implements java.io.Serializable {

	// Fields

	private HMCiOrgRegisterinfoId id;

	// Constructors

	/** default constructor */
	public HMCiOrgRegisterinfo() {
	}

	/** full constructor */
	public HMCiOrgRegisterinfo(HMCiOrgRegisterinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "registerNo", column = @Column(name = "REGISTER_NO", length = 20)),
			@AttributeOverride(name = "registerType", column = @Column(name = "REGISTER_TYPE", length = 20)),
			@AttributeOverride(name = "registerName", column = @Column(name = "REGISTER_NAME", length = 80)),
			@AttributeOverride(name = "registerStat", column = @Column(name = "REGISTER_STAT", length = 20)),
			@AttributeOverride(name = "registerDate", column = @Column(name = "REGISTER_DATE", length = 7)),
			@AttributeOverride(name = "setupDate", column = @Column(name = "SETUP_DATE", length = 7)),
			@AttributeOverride(name = "businessLimit", column = @Column(name = "BUSINESS_LIMIT", precision = 22, scale = 0)),
			@AttributeOverride(name = "endDate", column = @Column(name = "END_DATE", length = 7)),
			@AttributeOverride(name = "regOrg", column = @Column(name = "REG_ORG", length = 80)),
			@AttributeOverride(name = "auditCon", column = @Column(name = "AUDIT_CON", length = 200)),
			@AttributeOverride(name = "auditDate", column = @Column(name = "AUDIT_DATE", length = 7)),
			@AttributeOverride(name = "auditEndDate", column = @Column(name = "AUDIT_END_DATE", length = 7)),
			@AttributeOverride(name = "registerCapitalCurr", column = @Column(name = "REGISTER_CAPITAL_CURR", length = 20)),
			@AttributeOverride(name = "registerCapital", column = @Column(name = "REGISTER_CAPITAL", precision = 17)),
			@AttributeOverride(name = "registerComposing", column = @Column(name = "REGISTER_COMPOSING", length = 60)),
			@AttributeOverride(name = "registerNationCode", column = @Column(name = "REGISTER_NATION_CODE", length = 20)),
			@AttributeOverride(name = "registerArea", column = @Column(name = "REGISTER_AREA", length = 20)),
			@AttributeOverride(name = "registerAddr", column = @Column(name = "REGISTER_ADDR", length = 200)),
			@AttributeOverride(name = "registerZipcode", column = @Column(name = "REGISTER_ZIPCODE", length = 20)),
			@AttributeOverride(name = "registerEnAddr", column = @Column(name = "REGISTER_EN_ADDR")),
			@AttributeOverride(name = "businessScope", column = @Column(name = "BUSINESS_SCOPE")),
			@AttributeOverride(name = "factCapitalCurr", column = @Column(name = "FACT_CAPITAL_CURR", length = 20)),
			@AttributeOverride(name = "factCapital", column = @Column(name = "FACT_CAPITAL", precision = 17)),
			@AttributeOverride(name = "apprOrg", column = @Column(name = "APPR_ORG", length = 80)),
			@AttributeOverride(name = "apprDocNo", column = @Column(name = "APPR_DOC_NO", length = 80)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgRegisterinfoId getId() {
		return this.id;
	}

	public void setId(HMCiOrgRegisterinfoId id) {
		this.id = id;
	}

}