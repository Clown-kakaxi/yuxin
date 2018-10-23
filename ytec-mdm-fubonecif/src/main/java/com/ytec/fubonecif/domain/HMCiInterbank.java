package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiInterbank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_INTERBANK")
public class HMCiInterbank implements java.io.Serializable {

	// Fields

	private HMCiInterbankId id;

	// Constructors

	/** default constructor */
	public HMCiInterbank() {
	}

	/** full constructor */
	public HMCiInterbank(HMCiInterbankId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", nullable = false, scale = 0)),
			@AttributeOverride(name = "finaCustType", column = @Column(name = "FINA_CUST_TYPE", length = 20)),
			@AttributeOverride(name = "bankNo", column = @Column(name = "BANK_NO", length = 20)),
			@AttributeOverride(name = "bankName", column = @Column(name = "BANK_NAME", length = 60)),
			@AttributeOverride(name = "bankType", column = @Column(name = "BANK_TYPE", length = 20)),
			@AttributeOverride(name = "orgCode", column = @Column(name = "ORG_CODE", length = 32)),
			@AttributeOverride(name = "busiLicNo", column = @Column(name = "BUSI_LIC_NO", length = 32)),
			@AttributeOverride(name = "finaOrgType", column = @Column(name = "FINA_ORG_TYPE", length = 20)),
			@AttributeOverride(name = "finaLicNo", column = @Column(name = "FINA_LIC_NO", length = 32)),
			@AttributeOverride(name = "finaOrgCode", column = @Column(name = "FINA_ORG_CODE", length = 32)),
			@AttributeOverride(name = "nationalTax", column = @Column(name = "NATIONAL_TAX", length = 32)),
			@AttributeOverride(name = "localTax", column = @Column(name = "LOCAL_TAX", length = 32)),
			@AttributeOverride(name = "legalRepr", column = @Column(name = "LEGAL_REPR", length = 80)),
			@AttributeOverride(name = "zoneCode", column = @Column(name = "ZONE_CODE", length = 20)),
			@AttributeOverride(name = "zipCode", column = @Column(name = "ZIP_CODE", length = 32)),
			@AttributeOverride(name = "address", column = @Column(name = "ADDRESS", length = 80)),
			@AttributeOverride(name = "tel", column = @Column(name = "TEL", length = 32)),
			@AttributeOverride(name = "creditLevel", column = @Column(name = "CREDIT_LEVEL", length = 80)),
			@AttributeOverride(name = "creditLevelPeriod", column = @Column(name = "CREDIT_LEVEL_PERIOD", length = 7)),
			@AttributeOverride(name = "modernPaySysNo", column = @Column(name = "MODERN_PAY_SYS_NO", length = 32)),
			@AttributeOverride(name = "exchangeNo", column = @Column(name = "EXCHANGE_NO", length = 32)),
			@AttributeOverride(name = "swift", column = @Column(name = "SWIFT", length = 32)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiInterbankId getId() {
		return this.id;
	}

	public void setId(HMCiInterbankId id) {
		this.id = id;
	}

}