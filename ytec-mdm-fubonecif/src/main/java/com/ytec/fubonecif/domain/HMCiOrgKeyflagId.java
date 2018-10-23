package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiOrgKeyflagId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgKeyflagId implements java.io.Serializable {

	// Fields

	private String custId;
	private String isLegalCorp;
	private String isListedCorp;
	private String isHighTechCorp;
	private String isRuralCorp;
	private String isSmallCorp;
	private String isPrivateCorp;
	private String isNewCorp;
	private String hasBadLoan;
	private String hasOtherBankLoan;
	private String isGroupCust;
	private String isRelaGroup;
	private String isSecret;
	private String isRural;
	private String isLimitIndustry;
	private String isCreditCust;
	private String groupCreditFlag;
	private String isAssociatedParty;
	private String isEbankSignCust;
	private String bankruptFlag;
	private String hasIeRight;
	private String realtyFlag;
	private String projectFlag;
	private String udivFlag;
	private String isImportantCust;
	private String freeTaxFlag;
	private String bankCorpFlag;
	private String isTop500;
	private String isPartner;
	private String iso9000;
	private String iso14000;
	private String isSoe;
	private String isTaiwanCorp;
	private String isAreaImpEnt;
	private String isPrepEnt;
	private String isTwoHighEnt;
	private String isSteelEnt;
	private String isNotLocalEnt;
	private String isHighRiskPoll;
	private String isNtnalMacroCtrl;
	private String isThisBankCust;
	private String isFaxTransCust;
	private String isSendEcomstatFlag;
	private String isCenterFactory;
	private String isSetProfitRate;
	private String isCheckLoanLimit;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiOrgKeyflagId() {
	}

	/** minimal constructor */
	public HMCiOrgKeyflagId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgKeyflagId(String custId, String isLegalCorp,
			String isListedCorp, String isHighTechCorp, String isRuralCorp,
			String isSmallCorp, String isPrivateCorp, String isNewCorp,
			String hasBadLoan, String hasOtherBankLoan, String isGroupCust,
			String isRelaGroup, String isSecret, String isRural,
			String isLimitIndustry, String isCreditCust,
			String groupCreditFlag, String isAssociatedParty,
			String isEbankSignCust, String bankruptFlag, String hasIeRight,
			String realtyFlag, String projectFlag, String udivFlag,
			String isImportantCust, String freeTaxFlag, String bankCorpFlag,
			String isTop500, String isPartner, String iso9000, String iso14000,
			String isSoe, String isTaiwanCorp, String isAreaImpEnt,
			String isPrepEnt, String isTwoHighEnt, String isSteelEnt,
			String isNotLocalEnt, String isHighRiskPoll,
			String isNtnalMacroCtrl, String isThisBankCust,
			String isFaxTransCust, String isSendEcomstatFlag,
			String isCenterFactory, String isSetProfitRate,
			String isCheckLoanLimit, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.custId = custId;
		this.isLegalCorp = isLegalCorp;
		this.isListedCorp = isListedCorp;
		this.isHighTechCorp = isHighTechCorp;
		this.isRuralCorp = isRuralCorp;
		this.isSmallCorp = isSmallCorp;
		this.isPrivateCorp = isPrivateCorp;
		this.isNewCorp = isNewCorp;
		this.hasBadLoan = hasBadLoan;
		this.hasOtherBankLoan = hasOtherBankLoan;
		this.isGroupCust = isGroupCust;
		this.isRelaGroup = isRelaGroup;
		this.isSecret = isSecret;
		this.isRural = isRural;
		this.isLimitIndustry = isLimitIndustry;
		this.isCreditCust = isCreditCust;
		this.groupCreditFlag = groupCreditFlag;
		this.isAssociatedParty = isAssociatedParty;
		this.isEbankSignCust = isEbankSignCust;
		this.bankruptFlag = bankruptFlag;
		this.hasIeRight = hasIeRight;
		this.realtyFlag = realtyFlag;
		this.projectFlag = projectFlag;
		this.udivFlag = udivFlag;
		this.isImportantCust = isImportantCust;
		this.freeTaxFlag = freeTaxFlag;
		this.bankCorpFlag = bankCorpFlag;
		this.isTop500 = isTop500;
		this.isPartner = isPartner;
		this.iso9000 = iso9000;
		this.iso14000 = iso14000;
		this.isSoe = isSoe;
		this.isTaiwanCorp = isTaiwanCorp;
		this.isAreaImpEnt = isAreaImpEnt;
		this.isPrepEnt = isPrepEnt;
		this.isTwoHighEnt = isTwoHighEnt;
		this.isSteelEnt = isSteelEnt;
		this.isNotLocalEnt = isNotLocalEnt;
		this.isHighRiskPoll = isHighRiskPoll;
		this.isNtnalMacroCtrl = isNtnalMacroCtrl;
		this.isThisBankCust = isThisBankCust;
		this.isFaxTransCust = isFaxTransCust;
		this.isSendEcomstatFlag = isSendEcomstatFlag;
		this.isCenterFactory = isCenterFactory;
		this.isSetProfitRate = isSetProfitRate;
		this.isCheckLoanLimit = isCheckLoanLimit;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "IS_LEGAL_CORP", length = 1)
	public String getIsLegalCorp() {
		return this.isLegalCorp;
	}

	public void setIsLegalCorp(String isLegalCorp) {
		this.isLegalCorp = isLegalCorp;
	}

	@Column(name = "IS_LISTED_CORP", length = 1)
	public String getIsListedCorp() {
		return this.isListedCorp;
	}

	public void setIsListedCorp(String isListedCorp) {
		this.isListedCorp = isListedCorp;
	}

	@Column(name = "IS_HIGH_TECH_CORP", length = 1)
	public String getIsHighTechCorp() {
		return this.isHighTechCorp;
	}

	public void setIsHighTechCorp(String isHighTechCorp) {
		this.isHighTechCorp = isHighTechCorp;
	}

	@Column(name = "IS_RURAL_CORP", length = 1)
	public String getIsRuralCorp() {
		return this.isRuralCorp;
	}

	public void setIsRuralCorp(String isRuralCorp) {
		this.isRuralCorp = isRuralCorp;
	}

	@Column(name = "IS_SMALL_CORP", length = 1)
	public String getIsSmallCorp() {
		return this.isSmallCorp;
	}

	public void setIsSmallCorp(String isSmallCorp) {
		this.isSmallCorp = isSmallCorp;
	}

	@Column(name = "IS_PRIVATE_CORP", length = 1)
	public String getIsPrivateCorp() {
		return this.isPrivateCorp;
	}

	public void setIsPrivateCorp(String isPrivateCorp) {
		this.isPrivateCorp = isPrivateCorp;
	}

	@Column(name = "IS_NEW_CORP", length = 1)
	public String getIsNewCorp() {
		return this.isNewCorp;
	}

	public void setIsNewCorp(String isNewCorp) {
		this.isNewCorp = isNewCorp;
	}

	@Column(name = "HAS_BAD_LOAN", length = 1)
	public String getHasBadLoan() {
		return this.hasBadLoan;
	}

	public void setHasBadLoan(String hasBadLoan) {
		this.hasBadLoan = hasBadLoan;
	}

	@Column(name = "HAS_OTHER_BANK_LOAN", length = 1)
	public String getHasOtherBankLoan() {
		return this.hasOtherBankLoan;
	}

	public void setHasOtherBankLoan(String hasOtherBankLoan) {
		this.hasOtherBankLoan = hasOtherBankLoan;
	}

	@Column(name = "IS_GROUP_CUST", length = 1)
	public String getIsGroupCust() {
		return this.isGroupCust;
	}

	public void setIsGroupCust(String isGroupCust) {
		this.isGroupCust = isGroupCust;
	}

	@Column(name = "IS_RELA_GROUP", length = 1)
	public String getIsRelaGroup() {
		return this.isRelaGroup;
	}

	public void setIsRelaGroup(String isRelaGroup) {
		this.isRelaGroup = isRelaGroup;
	}

	@Column(name = "IS_SECRET", length = 1)
	public String getIsSecret() {
		return this.isSecret;
	}

	public void setIsSecret(String isSecret) {
		this.isSecret = isSecret;
	}

	@Column(name = "IS_RURAL", length = 1)
	public String getIsRural() {
		return this.isRural;
	}

	public void setIsRural(String isRural) {
		this.isRural = isRural;
	}

	@Column(name = "IS_LIMIT_INDUSTRY", length = 1)
	public String getIsLimitIndustry() {
		return this.isLimitIndustry;
	}

	public void setIsLimitIndustry(String isLimitIndustry) {
		this.isLimitIndustry = isLimitIndustry;
	}

	@Column(name = "IS_CREDIT_CUST", length = 1)
	public String getIsCreditCust() {
		return this.isCreditCust;
	}

	public void setIsCreditCust(String isCreditCust) {
		this.isCreditCust = isCreditCust;
	}

	@Column(name = "GROUP_CREDIT_FLAG", length = 1)
	public String getGroupCreditFlag() {
		return this.groupCreditFlag;
	}

	public void setGroupCreditFlag(String groupCreditFlag) {
		this.groupCreditFlag = groupCreditFlag;
	}

	@Column(name = "IS_ASSOCIATED_PARTY", length = 1)
	public String getIsAssociatedParty() {
		return this.isAssociatedParty;
	}

	public void setIsAssociatedParty(String isAssociatedParty) {
		this.isAssociatedParty = isAssociatedParty;
	}

	@Column(name = "IS_EBANK_SIGN_CUST", length = 1)
	public String getIsEbankSignCust() {
		return this.isEbankSignCust;
	}

	public void setIsEbankSignCust(String isEbankSignCust) {
		this.isEbankSignCust = isEbankSignCust;
	}

	@Column(name = "BANKRUPT_FLAG", length = 1)
	public String getBankruptFlag() {
		return this.bankruptFlag;
	}

	public void setBankruptFlag(String bankruptFlag) {
		this.bankruptFlag = bankruptFlag;
	}

	@Column(name = "HAS_IE_RIGHT", length = 1)
	public String getHasIeRight() {
		return this.hasIeRight;
	}

	public void setHasIeRight(String hasIeRight) {
		this.hasIeRight = hasIeRight;
	}

	@Column(name = "REALTY_FLAG", length = 1)
	public String getRealtyFlag() {
		return this.realtyFlag;
	}

	public void setRealtyFlag(String realtyFlag) {
		this.realtyFlag = realtyFlag;
	}

	@Column(name = "PROJECT_FLAG", length = 1)
	public String getProjectFlag() {
		return this.projectFlag;
	}

	public void setProjectFlag(String projectFlag) {
		this.projectFlag = projectFlag;
	}

	@Column(name = "UDIV_FLAG", length = 1)
	public String getUdivFlag() {
		return this.udivFlag;
	}

	public void setUdivFlag(String udivFlag) {
		this.udivFlag = udivFlag;
	}

	@Column(name = "IS_IMPORTANT_CUST", length = 1)
	public String getIsImportantCust() {
		return this.isImportantCust;
	}

	public void setIsImportantCust(String isImportantCust) {
		this.isImportantCust = isImportantCust;
	}

	@Column(name = "FREE_TAX_FLAG", length = 1)
	public String getFreeTaxFlag() {
		return this.freeTaxFlag;
	}

	public void setFreeTaxFlag(String freeTaxFlag) {
		this.freeTaxFlag = freeTaxFlag;
	}

	@Column(name = "BANK_CORP_FLAG", length = 1)
	public String getBankCorpFlag() {
		return this.bankCorpFlag;
	}

	public void setBankCorpFlag(String bankCorpFlag) {
		this.bankCorpFlag = bankCorpFlag;
	}

	@Column(name = "IS_TOP_500", length = 1)
	public String getIsTop500() {
		return this.isTop500;
	}

	public void setIsTop500(String isTop500) {
		this.isTop500 = isTop500;
	}

	@Column(name = "IS_PARTNER", length = 1)
	public String getIsPartner() {
		return this.isPartner;
	}

	public void setIsPartner(String isPartner) {
		this.isPartner = isPartner;
	}

	@Column(name = "ISO9000", length = 1)
	public String getIso9000() {
		return this.iso9000;
	}

	public void setIso9000(String iso9000) {
		this.iso9000 = iso9000;
	}

	@Column(name = "ISO14000", length = 1)
	public String getIso14000() {
		return this.iso14000;
	}

	public void setIso14000(String iso14000) {
		this.iso14000 = iso14000;
	}

	@Column(name = "IS_SOE", length = 1)
	public String getIsSoe() {
		return this.isSoe;
	}

	public void setIsSoe(String isSoe) {
		this.isSoe = isSoe;
	}

	@Column(name = "IS_TAIWAN_CORP", length = 1)
	public String getIsTaiwanCorp() {
		return this.isTaiwanCorp;
	}

	public void setIsTaiwanCorp(String isTaiwanCorp) {
		this.isTaiwanCorp = isTaiwanCorp;
	}

	@Column(name = "IS_AREA_IMP_ENT", length = 1)
	public String getIsAreaImpEnt() {
		return this.isAreaImpEnt;
	}

	public void setIsAreaImpEnt(String isAreaImpEnt) {
		this.isAreaImpEnt = isAreaImpEnt;
	}

	@Column(name = "IS_PREP_ENT", length = 1)
	public String getIsPrepEnt() {
		return this.isPrepEnt;
	}

	public void setIsPrepEnt(String isPrepEnt) {
		this.isPrepEnt = isPrepEnt;
	}

	@Column(name = "IS_TWO_HIGH_ENT", length = 1)
	public String getIsTwoHighEnt() {
		return this.isTwoHighEnt;
	}

	public void setIsTwoHighEnt(String isTwoHighEnt) {
		this.isTwoHighEnt = isTwoHighEnt;
	}

	@Column(name = "IS_STEEL_ENT", length = 1)
	public String getIsSteelEnt() {
		return this.isSteelEnt;
	}

	public void setIsSteelEnt(String isSteelEnt) {
		this.isSteelEnt = isSteelEnt;
	}

	@Column(name = "IS_NOT_LOCAL_ENT", length = 1)
	public String getIsNotLocalEnt() {
		return this.isNotLocalEnt;
	}

	public void setIsNotLocalEnt(String isNotLocalEnt) {
		this.isNotLocalEnt = isNotLocalEnt;
	}

	@Column(name = "IS_HIGH_RISK_POLL", length = 1)
	public String getIsHighRiskPoll() {
		return this.isHighRiskPoll;
	}

	public void setIsHighRiskPoll(String isHighRiskPoll) {
		this.isHighRiskPoll = isHighRiskPoll;
	}

	@Column(name = "IS_NTNAL_MACRO_CTRL", length = 1)
	public String getIsNtnalMacroCtrl() {
		return this.isNtnalMacroCtrl;
	}

	public void setIsNtnalMacroCtrl(String isNtnalMacroCtrl) {
		this.isNtnalMacroCtrl = isNtnalMacroCtrl;
	}

	@Column(name = "IS_THIS_BANK_CUST", length = 1)
	public String getIsThisBankCust() {
		return this.isThisBankCust;
	}

	public void setIsThisBankCust(String isThisBankCust) {
		this.isThisBankCust = isThisBankCust;
	}

	@Column(name = "IS_FAX_TRANS_CUST", length = 1)
	public String getIsFaxTransCust() {
		return this.isFaxTransCust;
	}

	public void setIsFaxTransCust(String isFaxTransCust) {
		this.isFaxTransCust = isFaxTransCust;
	}

	@Column(name = "IS_SEND_ECOMSTAT_FLAG", length = 1)
	public String getIsSendEcomstatFlag() {
		return this.isSendEcomstatFlag;
	}

	public void setIsSendEcomstatFlag(String isSendEcomstatFlag) {
		this.isSendEcomstatFlag = isSendEcomstatFlag;
	}

	@Column(name = "IS_CENTER_FACTORY", length = 1)
	public String getIsCenterFactory() {
		return this.isCenterFactory;
	}

	public void setIsCenterFactory(String isCenterFactory) {
		this.isCenterFactory = isCenterFactory;
	}

	@Column(name = "IS_SET_PROFIT_RATE", length = 1)
	public String getIsSetProfitRate() {
		return this.isSetProfitRate;
	}

	public void setIsSetProfitRate(String isSetProfitRate) {
		this.isSetProfitRate = isSetProfitRate;
	}

	@Column(name = "IS_CHECK_LOAN_LIMIT", length = 1)
	public String getIsCheckLoanLimit() {
		return this.isCheckLoanLimit;
	}

	public void setIsCheckLoanLimit(String isCheckLoanLimit) {
		this.isCheckLoanLimit = isCheckLoanLimit;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiOrgKeyflagId))
			return false;
		HMCiOrgKeyflagId castOther = (HMCiOrgKeyflagId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getIsLegalCorp() == castOther.getIsLegalCorp()) || (this
						.getIsLegalCorp() != null
						&& castOther.getIsLegalCorp() != null && this
						.getIsLegalCorp().equals(castOther.getIsLegalCorp())))
				&& ((this.getIsListedCorp() == castOther.getIsListedCorp()) || (this
						.getIsListedCorp() != null
						&& castOther.getIsListedCorp() != null && this
						.getIsListedCorp().equals(castOther.getIsListedCorp())))
				&& ((this.getIsHighTechCorp() == castOther.getIsHighTechCorp()) || (this
						.getIsHighTechCorp() != null
						&& castOther.getIsHighTechCorp() != null && this
						.getIsHighTechCorp().equals(
								castOther.getIsHighTechCorp())))
				&& ((this.getIsRuralCorp() == castOther.getIsRuralCorp()) || (this
						.getIsRuralCorp() != null
						&& castOther.getIsRuralCorp() != null && this
						.getIsRuralCorp().equals(castOther.getIsRuralCorp())))
				&& ((this.getIsSmallCorp() == castOther.getIsSmallCorp()) || (this
						.getIsSmallCorp() != null
						&& castOther.getIsSmallCorp() != null && this
						.getIsSmallCorp().equals(castOther.getIsSmallCorp())))
				&& ((this.getIsPrivateCorp() == castOther.getIsPrivateCorp()) || (this
						.getIsPrivateCorp() != null
						&& castOther.getIsPrivateCorp() != null && this
						.getIsPrivateCorp()
						.equals(castOther.getIsPrivateCorp())))
				&& ((this.getIsNewCorp() == castOther.getIsNewCorp()) || (this
						.getIsNewCorp() != null
						&& castOther.getIsNewCorp() != null && this
						.getIsNewCorp().equals(castOther.getIsNewCorp())))
				&& ((this.getHasBadLoan() == castOther.getHasBadLoan()) || (this
						.getHasBadLoan() != null
						&& castOther.getHasBadLoan() != null && this
						.getHasBadLoan().equals(castOther.getHasBadLoan())))
				&& ((this.getHasOtherBankLoan() == castOther
						.getHasOtherBankLoan()) || (this.getHasOtherBankLoan() != null
						&& castOther.getHasOtherBankLoan() != null && this
						.getHasOtherBankLoan().equals(
								castOther.getHasOtherBankLoan())))
				&& ((this.getIsGroupCust() == castOther.getIsGroupCust()) || (this
						.getIsGroupCust() != null
						&& castOther.getIsGroupCust() != null && this
						.getIsGroupCust().equals(castOther.getIsGroupCust())))
				&& ((this.getIsRelaGroup() == castOther.getIsRelaGroup()) || (this
						.getIsRelaGroup() != null
						&& castOther.getIsRelaGroup() != null && this
						.getIsRelaGroup().equals(castOther.getIsRelaGroup())))
				&& ((this.getIsSecret() == castOther.getIsSecret()) || (this
						.getIsSecret() != null
						&& castOther.getIsSecret() != null && this
						.getIsSecret().equals(castOther.getIsSecret())))
				&& ((this.getIsRural() == castOther.getIsRural()) || (this
						.getIsRural() != null
						&& castOther.getIsRural() != null && this.getIsRural()
						.equals(castOther.getIsRural())))
				&& ((this.getIsLimitIndustry() == castOther
						.getIsLimitIndustry()) || (this.getIsLimitIndustry() != null
						&& castOther.getIsLimitIndustry() != null && this
						.getIsLimitIndustry().equals(
								castOther.getIsLimitIndustry())))
				&& ((this.getIsCreditCust() == castOther.getIsCreditCust()) || (this
						.getIsCreditCust() != null
						&& castOther.getIsCreditCust() != null && this
						.getIsCreditCust().equals(castOther.getIsCreditCust())))
				&& ((this.getGroupCreditFlag() == castOther
						.getGroupCreditFlag()) || (this.getGroupCreditFlag() != null
						&& castOther.getGroupCreditFlag() != null && this
						.getGroupCreditFlag().equals(
								castOther.getGroupCreditFlag())))
				&& ((this.getIsAssociatedParty() == castOther
						.getIsAssociatedParty()) || (this
						.getIsAssociatedParty() != null
						&& castOther.getIsAssociatedParty() != null && this
						.getIsAssociatedParty().equals(
								castOther.getIsAssociatedParty())))
				&& ((this.getIsEbankSignCust() == castOther
						.getIsEbankSignCust()) || (this.getIsEbankSignCust() != null
						&& castOther.getIsEbankSignCust() != null && this
						.getIsEbankSignCust().equals(
								castOther.getIsEbankSignCust())))
				&& ((this.getBankruptFlag() == castOther.getBankruptFlag()) || (this
						.getBankruptFlag() != null
						&& castOther.getBankruptFlag() != null && this
						.getBankruptFlag().equals(castOther.getBankruptFlag())))
				&& ((this.getHasIeRight() == castOther.getHasIeRight()) || (this
						.getHasIeRight() != null
						&& castOther.getHasIeRight() != null && this
						.getHasIeRight().equals(castOther.getHasIeRight())))
				&& ((this.getRealtyFlag() == castOther.getRealtyFlag()) || (this
						.getRealtyFlag() != null
						&& castOther.getRealtyFlag() != null && this
						.getRealtyFlag().equals(castOther.getRealtyFlag())))
				&& ((this.getProjectFlag() == castOther.getProjectFlag()) || (this
						.getProjectFlag() != null
						&& castOther.getProjectFlag() != null && this
						.getProjectFlag().equals(castOther.getProjectFlag())))
				&& ((this.getUdivFlag() == castOther.getUdivFlag()) || (this
						.getUdivFlag() != null
						&& castOther.getUdivFlag() != null && this
						.getUdivFlag().equals(castOther.getUdivFlag())))
				&& ((this.getIsImportantCust() == castOther
						.getIsImportantCust()) || (this.getIsImportantCust() != null
						&& castOther.getIsImportantCust() != null && this
						.getIsImportantCust().equals(
								castOther.getIsImportantCust())))
				&& ((this.getFreeTaxFlag() == castOther.getFreeTaxFlag()) || (this
						.getFreeTaxFlag() != null
						&& castOther.getFreeTaxFlag() != null && this
						.getFreeTaxFlag().equals(castOther.getFreeTaxFlag())))
				&& ((this.getBankCorpFlag() == castOther.getBankCorpFlag()) || (this
						.getBankCorpFlag() != null
						&& castOther.getBankCorpFlag() != null && this
						.getBankCorpFlag().equals(castOther.getBankCorpFlag())))
				&& ((this.getIsTop500() == castOther.getIsTop500()) || (this
						.getIsTop500() != null
						&& castOther.getIsTop500() != null && this
						.getIsTop500().equals(castOther.getIsTop500())))
				&& ((this.getIsPartner() == castOther.getIsPartner()) || (this
						.getIsPartner() != null
						&& castOther.getIsPartner() != null && this
						.getIsPartner().equals(castOther.getIsPartner())))
				&& ((this.getIso9000() == castOther.getIso9000()) || (this
						.getIso9000() != null
						&& castOther.getIso9000() != null && this.getIso9000()
						.equals(castOther.getIso9000())))
				&& ((this.getIso14000() == castOther.getIso14000()) || (this
						.getIso14000() != null
						&& castOther.getIso14000() != null && this
						.getIso14000().equals(castOther.getIso14000())))
				&& ((this.getIsSoe() == castOther.getIsSoe()) || (this
						.getIsSoe() != null
						&& castOther.getIsSoe() != null && this.getIsSoe()
						.equals(castOther.getIsSoe())))
				&& ((this.getIsTaiwanCorp() == castOther.getIsTaiwanCorp()) || (this
						.getIsTaiwanCorp() != null
						&& castOther.getIsTaiwanCorp() != null && this
						.getIsTaiwanCorp().equals(castOther.getIsTaiwanCorp())))
				&& ((this.getIsAreaImpEnt() == castOther.getIsAreaImpEnt()) || (this
						.getIsAreaImpEnt() != null
						&& castOther.getIsAreaImpEnt() != null && this
						.getIsAreaImpEnt().equals(castOther.getIsAreaImpEnt())))
				&& ((this.getIsPrepEnt() == castOther.getIsPrepEnt()) || (this
						.getIsPrepEnt() != null
						&& castOther.getIsPrepEnt() != null && this
						.getIsPrepEnt().equals(castOther.getIsPrepEnt())))
				&& ((this.getIsTwoHighEnt() == castOther.getIsTwoHighEnt()) || (this
						.getIsTwoHighEnt() != null
						&& castOther.getIsTwoHighEnt() != null && this
						.getIsTwoHighEnt().equals(castOther.getIsTwoHighEnt())))
				&& ((this.getIsSteelEnt() == castOther.getIsSteelEnt()) || (this
						.getIsSteelEnt() != null
						&& castOther.getIsSteelEnt() != null && this
						.getIsSteelEnt().equals(castOther.getIsSteelEnt())))
				&& ((this.getIsNotLocalEnt() == castOther.getIsNotLocalEnt()) || (this
						.getIsNotLocalEnt() != null
						&& castOther.getIsNotLocalEnt() != null && this
						.getIsNotLocalEnt()
						.equals(castOther.getIsNotLocalEnt())))
				&& ((this.getIsHighRiskPoll() == castOther.getIsHighRiskPoll()) || (this
						.getIsHighRiskPoll() != null
						&& castOther.getIsHighRiskPoll() != null && this
						.getIsHighRiskPoll().equals(
								castOther.getIsHighRiskPoll())))
				&& ((this.getIsNtnalMacroCtrl() == castOther
						.getIsNtnalMacroCtrl()) || (this.getIsNtnalMacroCtrl() != null
						&& castOther.getIsNtnalMacroCtrl() != null && this
						.getIsNtnalMacroCtrl().equals(
								castOther.getIsNtnalMacroCtrl())))
				&& ((this.getIsThisBankCust() == castOther.getIsThisBankCust()) || (this
						.getIsThisBankCust() != null
						&& castOther.getIsThisBankCust() != null && this
						.getIsThisBankCust().equals(
								castOther.getIsThisBankCust())))
				&& ((this.getIsFaxTransCust() == castOther.getIsFaxTransCust()) || (this
						.getIsFaxTransCust() != null
						&& castOther.getIsFaxTransCust() != null && this
						.getIsFaxTransCust().equals(
								castOther.getIsFaxTransCust())))
				&& ((this.getIsSendEcomstatFlag() == castOther
						.getIsSendEcomstatFlag()) || (this
						.getIsSendEcomstatFlag() != null
						&& castOther.getIsSendEcomstatFlag() != null && this
						.getIsSendEcomstatFlag().equals(
								castOther.getIsSendEcomstatFlag())))
				&& ((this.getIsCenterFactory() == castOther
						.getIsCenterFactory()) || (this.getIsCenterFactory() != null
						&& castOther.getIsCenterFactory() != null && this
						.getIsCenterFactory().equals(
								castOther.getIsCenterFactory())))
				&& ((this.getIsSetProfitRate() == castOther
						.getIsSetProfitRate()) || (this.getIsSetProfitRate() != null
						&& castOther.getIsSetProfitRate() != null && this
						.getIsSetProfitRate().equals(
								castOther.getIsSetProfitRate())))
				&& ((this.getIsCheckLoanLimit() == castOther
						.getIsCheckLoanLimit()) || (this.getIsCheckLoanLimit() != null
						&& castOther.getIsCheckLoanLimit() != null && this
						.getIsCheckLoanLimit().equals(
								castOther.getIsCheckLoanLimit())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getIsLegalCorp() == null ? 0 : this.getIsLegalCorp()
						.hashCode());
		result = 37
				* result
				+ (getIsListedCorp() == null ? 0 : this.getIsListedCorp()
						.hashCode());
		result = 37
				* result
				+ (getIsHighTechCorp() == null ? 0 : this.getIsHighTechCorp()
						.hashCode());
		result = 37
				* result
				+ (getIsRuralCorp() == null ? 0 : this.getIsRuralCorp()
						.hashCode());
		result = 37
				* result
				+ (getIsSmallCorp() == null ? 0 : this.getIsSmallCorp()
						.hashCode());
		result = 37
				* result
				+ (getIsPrivateCorp() == null ? 0 : this.getIsPrivateCorp()
						.hashCode());
		result = 37 * result
				+ (getIsNewCorp() == null ? 0 : this.getIsNewCorp().hashCode());
		result = 37
				* result
				+ (getHasBadLoan() == null ? 0 : this.getHasBadLoan()
						.hashCode());
		result = 37
				* result
				+ (getHasOtherBankLoan() == null ? 0 : this
						.getHasOtherBankLoan().hashCode());
		result = 37
				* result
				+ (getIsGroupCust() == null ? 0 : this.getIsGroupCust()
						.hashCode());
		result = 37
				* result
				+ (getIsRelaGroup() == null ? 0 : this.getIsRelaGroup()
						.hashCode());
		result = 37 * result
				+ (getIsSecret() == null ? 0 : this.getIsSecret().hashCode());
		result = 37 * result
				+ (getIsRural() == null ? 0 : this.getIsRural().hashCode());
		result = 37
				* result
				+ (getIsLimitIndustry() == null ? 0 : this.getIsLimitIndustry()
						.hashCode());
		result = 37
				* result
				+ (getIsCreditCust() == null ? 0 : this.getIsCreditCust()
						.hashCode());
		result = 37
				* result
				+ (getGroupCreditFlag() == null ? 0 : this.getGroupCreditFlag()
						.hashCode());
		result = 37
				* result
				+ (getIsAssociatedParty() == null ? 0 : this
						.getIsAssociatedParty().hashCode());
		result = 37
				* result
				+ (getIsEbankSignCust() == null ? 0 : this.getIsEbankSignCust()
						.hashCode());
		result = 37
				* result
				+ (getBankruptFlag() == null ? 0 : this.getBankruptFlag()
						.hashCode());
		result = 37
				* result
				+ (getHasIeRight() == null ? 0 : this.getHasIeRight()
						.hashCode());
		result = 37
				* result
				+ (getRealtyFlag() == null ? 0 : this.getRealtyFlag()
						.hashCode());
		result = 37
				* result
				+ (getProjectFlag() == null ? 0 : this.getProjectFlag()
						.hashCode());
		result = 37 * result
				+ (getUdivFlag() == null ? 0 : this.getUdivFlag().hashCode());
		result = 37
				* result
				+ (getIsImportantCust() == null ? 0 : this.getIsImportantCust()
						.hashCode());
		result = 37
				* result
				+ (getFreeTaxFlag() == null ? 0 : this.getFreeTaxFlag()
						.hashCode());
		result = 37
				* result
				+ (getBankCorpFlag() == null ? 0 : this.getBankCorpFlag()
						.hashCode());
		result = 37 * result
				+ (getIsTop500() == null ? 0 : this.getIsTop500().hashCode());
		result = 37 * result
				+ (getIsPartner() == null ? 0 : this.getIsPartner().hashCode());
		result = 37 * result
				+ (getIso9000() == null ? 0 : this.getIso9000().hashCode());
		result = 37 * result
				+ (getIso14000() == null ? 0 : this.getIso14000().hashCode());
		result = 37 * result
				+ (getIsSoe() == null ? 0 : this.getIsSoe().hashCode());
		result = 37
				* result
				+ (getIsTaiwanCorp() == null ? 0 : this.getIsTaiwanCorp()
						.hashCode());
		result = 37
				* result
				+ (getIsAreaImpEnt() == null ? 0 : this.getIsAreaImpEnt()
						.hashCode());
		result = 37 * result
				+ (getIsPrepEnt() == null ? 0 : this.getIsPrepEnt().hashCode());
		result = 37
				* result
				+ (getIsTwoHighEnt() == null ? 0 : this.getIsTwoHighEnt()
						.hashCode());
		result = 37
				* result
				+ (getIsSteelEnt() == null ? 0 : this.getIsSteelEnt()
						.hashCode());
		result = 37
				* result
				+ (getIsNotLocalEnt() == null ? 0 : this.getIsNotLocalEnt()
						.hashCode());
		result = 37
				* result
				+ (getIsHighRiskPoll() == null ? 0 : this.getIsHighRiskPoll()
						.hashCode());
		result = 37
				* result
				+ (getIsNtnalMacroCtrl() == null ? 0 : this
						.getIsNtnalMacroCtrl().hashCode());
		result = 37
				* result
				+ (getIsThisBankCust() == null ? 0 : this.getIsThisBankCust()
						.hashCode());
		result = 37
				* result
				+ (getIsFaxTransCust() == null ? 0 : this.getIsFaxTransCust()
						.hashCode());
		result = 37
				* result
				+ (getIsSendEcomstatFlag() == null ? 0 : this
						.getIsSendEcomstatFlag().hashCode());
		result = 37
				* result
				+ (getIsCenterFactory() == null ? 0 : this.getIsCenterFactory()
						.hashCode());
		result = 37
				* result
				+ (getIsSetProfitRate() == null ? 0 : this.getIsSetProfitRate()
						.hashCode());
		result = 37
				* result
				+ (getIsCheckLoanLimit() == null ? 0 : this
						.getIsCheckLoanLimit().hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}