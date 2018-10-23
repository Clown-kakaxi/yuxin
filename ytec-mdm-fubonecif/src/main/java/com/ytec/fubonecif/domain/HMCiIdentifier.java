package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiIdentifier entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_IDENTIFIER")
public class HMCiIdentifier implements java.io.Serializable {

	// Fields

	private HMCiIdentifierId id;

	// Constructors

	/** default constructor */
	public HMCiIdentifier() {
	}

	/** full constructor */
	public HMCiIdentifier(HMCiIdentifierId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "identId", column = @Column(name = "IDENT_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "identCustName", column = @Column(name = "IDENT_CUST_NAME", length = 70)),
			@AttributeOverride(name = "identDesc", column = @Column(name = "IDENT_DESC", length = 80)),
			@AttributeOverride(name = "countryOrRegion", column = @Column(name = "COUNTRY_OR_REGION", length = 20)),
			@AttributeOverride(name = "identOrg", column = @Column(name = "IDENT_ORG", length = 40)),
			@AttributeOverride(name = "identApproveUnit", column = @Column(name = "IDENT_APPROVE_UNIT", length = 40)),
			@AttributeOverride(name = "identCheckFlag", column = @Column(name = "IDENT_CHECK_FLAG", length = 20)),
			@AttributeOverride(name = "idenRegDate", column = @Column(name = "IDEN_REG_DATE", length = 7)),
			@AttributeOverride(name = "identCheckingDate", column = @Column(name = "IDENT_CHECKING_DATE", length = 7)),
			@AttributeOverride(name = "identCheckedDate", column = @Column(name = "IDENT_CHECKED_DATE", length = 7)),
			@AttributeOverride(name = "identValidPeriod", column = @Column(name = "IDENT_VALID_PERIOD", precision = 22, scale = 0)),
			@AttributeOverride(name = "identEffectiveDate", column = @Column(name = "IDENT_EFFECTIVE_DATE", length = 7)),
			@AttributeOverride(name = "identExpiredDate", column = @Column(name = "IDENT_EXPIRED_DATE", length = 7)),
			@AttributeOverride(name = "identValidFlag", column = @Column(name = "IDENT_VALID_FLAG", length = 1)),
			@AttributeOverride(name = "identPeriod", column = @Column(name = "IDENT_PERIOD", precision = 22, scale = 0)),
			@AttributeOverride(name = "isOpenAccIdent", column = @Column(name = "IS_OPEN_ACC_IDENT", length = 1)),
			@AttributeOverride(name = "openAccIdentModifiedFlag", column = @Column(name = "OPEN_ACC_IDENT_MODIFIED_FLAG", length = 1)),
			@AttributeOverride(name = "identModifiedTime", column = @Column(name = "IDENT_MODIFIED_TIME", length = 11)),
			@AttributeOverride(name = "verifyDate", column = @Column(name = "VERIFY_DATE", length = 7)),
			@AttributeOverride(name = "verifyEmployee", column = @Column(name = "VERIFY_EMPLOYEE", length = 20)),
			@AttributeOverride(name = "verifyResult", column = @Column(name = "VERIFY_RESULT", length = 20)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiIdentifierId getId() {
		return this.id;
	}

	public void setId(HMCiIdentifierId id) {
		this.id = id;
	}

}