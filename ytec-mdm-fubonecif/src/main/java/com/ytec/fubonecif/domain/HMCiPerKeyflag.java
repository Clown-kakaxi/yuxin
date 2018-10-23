package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerKeyflag entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_KEYFLAG")
public class HMCiPerKeyflag implements java.io.Serializable {

	// Fields

	private HMCiPerKeyflagId id;

	// Constructors

	/** default constructor */
	public HMCiPerKeyflag() {
	}

	/** full constructor */
	public HMCiPerKeyflag(HMCiPerKeyflagId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "isMerchant", column = @Column(name = "IS_MERCHANT", length = 1)),
			@AttributeOverride(name = "isPeasant", column = @Column(name = "IS_PEASANT", length = 1)),
			@AttributeOverride(name = "isCrediblePeasant", column = @Column(name = "IS_CREDIBLE_PEASANT", length = 1)),
			@AttributeOverride(name = "isShareholder", column = @Column(name = "IS_SHAREHOLDER", length = 1)),
			@AttributeOverride(name = "isEmployee", column = @Column(name = "IS_EMPLOYEE", length = 1)),
			@AttributeOverride(name = "hasThisBankLoan", column = @Column(name = "HAS_THIS_BANK_LOAN", length = 1)),
			@AttributeOverride(name = "hasOtherBankLoan", column = @Column(name = "HAS_OTHER_BANK_LOAN", length = 1)),
			@AttributeOverride(name = "hasBadLoan", column = @Column(name = "HAS_BAD_LOAN", length = 1)),
			@AttributeOverride(name = "isGuarantee", column = @Column(name = "IS_GUARANTEE", length = 1)),
			@AttributeOverride(name = "isNative", column = @Column(name = "IS_NATIVE", length = 1)),
			@AttributeOverride(name = "isOnJobWorker", column = @Column(name = "IS_ON_JOB_WORKER", length = 1)),
			@AttributeOverride(name = "hasCreditInfo", column = @Column(name = "HAS_CREDIT_INFO", length = 1)),
			@AttributeOverride(name = "creditAmount", column = @Column(name = "CREDIT_AMOUNT", precision = 17)),
			@AttributeOverride(name = "isImportantCust", column = @Column(name = "IS_IMPORTANT_CUST", length = 1)),
			@AttributeOverride(name = "isCreditCust", column = @Column(name = "IS_CREDIT_CUST", length = 1)),
			@AttributeOverride(name = "isSecretCust", column = @Column(name = "IS_SECRET_CUST", length = 1)),
			@AttributeOverride(name = "isPrivBankCust", column = @Column(name = "IS_PRIV_BANK_CUST", length = 1)),
			@AttributeOverride(name = "isPayrollCust", column = @Column(name = "IS_PAYROLL_CUST", length = 1)),
			@AttributeOverride(name = "isDebitCard", column = @Column(name = "IS_DEBIT_CARD", length = 1)),
			@AttributeOverride(name = "isEbankSignCust", column = @Column(name = "IS_EBANK_SIGN_CUST", length = 1)),
			@AttributeOverride(name = "hasEndoInsure", column = @Column(name = "HAS_ENDO_INSURE", length = 1)),
			@AttributeOverride(name = "hasMediInsure", column = @Column(name = "HAS_MEDI_INSURE", length = 1)),
			@AttributeOverride(name = "hasIdleInsure", column = @Column(name = "HAS_IDLE_INSURE", length = 1)),
			@AttributeOverride(name = "hasInjuryInsure", column = @Column(name = "HAS_INJURY_INSURE", length = 1)),
			@AttributeOverride(name = "hasBirthInsure", column = @Column(name = "HAS_BIRTH_INSURE", length = 1)),
			@AttributeOverride(name = "hasHouseFund", column = @Column(name = "HAS_HOUSE_FUND", length = 1)),
			@AttributeOverride(name = "hasCar", column = @Column(name = "HAS_CAR", length = 1)),
			@AttributeOverride(name = "hasPhoto", column = @Column(name = "HAS_PHOTO", length = 1)),
			@AttributeOverride(name = "foreignPassportFlag", column = @Column(name = "FOREIGN_PASSPORT_FLAG", length = 1)),
			@AttributeOverride(name = "foreignHabitatioFlag", column = @Column(name = "FOREIGN_HABITATIO_FLAG", length = 1)),
			@AttributeOverride(name = "usaTaxFlag", column = @Column(name = "USA_TAX_FLAG", length = 1)),
			@AttributeOverride(name = "isDividendCust", column = @Column(name = "IS_DIVIDEND_CUST", length = 1)),
			@AttributeOverride(name = "isFaxTransCust", column = @Column(name = "IS_FAX_TRANS_CUST", length = 1)),
			@AttributeOverride(name = "isSendEcomstatFlag", column = @Column(name = "IS_SEND_ECOMSTAT_FLAG", length = 1)),
			@AttributeOverride(name = "isUptoViplevel", column = @Column(name = "IS_UPTO_VIPLEVEL", length = 1)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerKeyflagId getId() {
		return this.id;
	}

	public void setId(HMCiPerKeyflagId id) {
		this.id = id;
	}

}