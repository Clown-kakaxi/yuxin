package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgKeyflag entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_KEYFLAG")
public class HMCiOrgKeyflag implements java.io.Serializable {

	// Fields

	private HMCiOrgKeyflagId id;

	// Constructors

	/** default constructor */
	public HMCiOrgKeyflag() {
	}

	/** full constructor */
	public HMCiOrgKeyflag(HMCiOrgKeyflagId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "isLegalCorp", column = @Column(name = "IS_LEGAL_CORP", length = 1)),
			@AttributeOverride(name = "isListedCorp", column = @Column(name = "IS_LISTED_CORP", length = 1)),
			@AttributeOverride(name = "isHighTechCorp", column = @Column(name = "IS_HIGH_TECH_CORP", length = 1)),
			@AttributeOverride(name = "isRuralCorp", column = @Column(name = "IS_RURAL_CORP", length = 1)),
			@AttributeOverride(name = "isSmallCorp", column = @Column(name = "IS_SMALL_CORP", length = 1)),
			@AttributeOverride(name = "isPrivateCorp", column = @Column(name = "IS_PRIVATE_CORP", length = 1)),
			@AttributeOverride(name = "isNewCorp", column = @Column(name = "IS_NEW_CORP", length = 1)),
			@AttributeOverride(name = "hasBadLoan", column = @Column(name = "HAS_BAD_LOAN", length = 1)),
			@AttributeOverride(name = "hasOtherBankLoan", column = @Column(name = "HAS_OTHER_BANK_LOAN", length = 1)),
			@AttributeOverride(name = "isGroupCust", column = @Column(name = "IS_GROUP_CUST", length = 1)),
			@AttributeOverride(name = "isRelaGroup", column = @Column(name = "IS_RELA_GROUP", length = 1)),
			@AttributeOverride(name = "isSecret", column = @Column(name = "IS_SECRET", length = 1)),
			@AttributeOverride(name = "isRural", column = @Column(name = "IS_RURAL", length = 1)),
			@AttributeOverride(name = "isLimitIndustry", column = @Column(name = "IS_LIMIT_INDUSTRY", length = 1)),
			@AttributeOverride(name = "isCreditCust", column = @Column(name = "IS_CREDIT_CUST", length = 1)),
			@AttributeOverride(name = "groupCreditFlag", column = @Column(name = "GROUP_CREDIT_FLAG", length = 1)),
			@AttributeOverride(name = "isAssociatedParty", column = @Column(name = "IS_ASSOCIATED_PARTY", length = 1)),
			@AttributeOverride(name = "isEbankSignCust", column = @Column(name = "IS_EBANK_SIGN_CUST", length = 1)),
			@AttributeOverride(name = "bankruptFlag", column = @Column(name = "BANKRUPT_FLAG", length = 1)),
			@AttributeOverride(name = "hasIeRight", column = @Column(name = "HAS_IE_RIGHT", length = 1)),
			@AttributeOverride(name = "realtyFlag", column = @Column(name = "REALTY_FLAG", length = 1)),
			@AttributeOverride(name = "projectFlag", column = @Column(name = "PROJECT_FLAG", length = 1)),
			@AttributeOverride(name = "udivFlag", column = @Column(name = "UDIV_FLAG", length = 1)),
			@AttributeOverride(name = "isImportantCust", column = @Column(name = "IS_IMPORTANT_CUST", length = 1)),
			@AttributeOverride(name = "freeTaxFlag", column = @Column(name = "FREE_TAX_FLAG", length = 1)),
			@AttributeOverride(name = "bankCorpFlag", column = @Column(name = "BANK_CORP_FLAG", length = 1)),
			@AttributeOverride(name = "isTop500", column = @Column(name = "IS_TOP_500", length = 1)),
			@AttributeOverride(name = "isPartner", column = @Column(name = "IS_PARTNER", length = 1)),
			@AttributeOverride(name = "iso9000", column = @Column(name = "ISO9000", length = 1)),
			@AttributeOverride(name = "iso14000", column = @Column(name = "ISO14000", length = 1)),
			@AttributeOverride(name = "isSoe", column = @Column(name = "IS_SOE", length = 1)),
			@AttributeOverride(name = "isTaiwanCorp", column = @Column(name = "IS_TAIWAN_CORP", length = 1)),
			@AttributeOverride(name = "isAreaImpEnt", column = @Column(name = "IS_AREA_IMP_ENT", length = 1)),
			@AttributeOverride(name = "isPrepEnt", column = @Column(name = "IS_PREP_ENT", length = 1)),
			@AttributeOverride(name = "isTwoHighEnt", column = @Column(name = "IS_TWO_HIGH_ENT", length = 1)),
			@AttributeOverride(name = "isSteelEnt", column = @Column(name = "IS_STEEL_ENT", length = 1)),
			@AttributeOverride(name = "isNotLocalEnt", column = @Column(name = "IS_NOT_LOCAL_ENT", length = 1)),
			@AttributeOverride(name = "isHighRiskPoll", column = @Column(name = "IS_HIGH_RISK_POLL", length = 1)),
			@AttributeOverride(name = "isNtnalMacroCtrl", column = @Column(name = "IS_NTNAL_MACRO_CTRL", length = 1)),
			@AttributeOverride(name = "isThisBankCust", column = @Column(name = "IS_THIS_BANK_CUST", length = 1)),
			@AttributeOverride(name = "isFaxTransCust", column = @Column(name = "IS_FAX_TRANS_CUST", length = 1)),
			@AttributeOverride(name = "isSendEcomstatFlag", column = @Column(name = "IS_SEND_ECOMSTAT_FLAG", length = 1)),
			@AttributeOverride(name = "isCenterFactory", column = @Column(name = "IS_CENTER_FACTORY", length = 1)),
			@AttributeOverride(name = "isSetProfitRate", column = @Column(name = "IS_SET_PROFIT_RATE", length = 1)),
			@AttributeOverride(name = "isCheckLoanLimit", column = @Column(name = "IS_CHECK_LOAN_LIMIT", length = 1)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgKeyflagId getId() {
		return this.id;
	}

	public void setId(HMCiOrgKeyflagId id) {
		this.id = id;
	}

}