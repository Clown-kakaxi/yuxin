package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_ORG_KEYFLAG database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ORG_KEYFLAG")
public class AcrmFCiOrgKeyflag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="BANK_CORP_FLAG")
	private String bankCorpFlag;

	@Column(name="BANKRUPT_FLAG")
	private String bankruptFlag;

	@Column(name="FREE_TAX_FLAG")
	private String freeTaxFlag;

	@Column(name="GROUP_CREDIT_FLAG")
	private String groupCreditFlag;

	@Column(name="HAS_BAD_LOAN")
	private String hasBadLoan;

	@Column(name="HAS_IE_RIGHT")
	private String hasIeRight;

	@Column(name="HAS_OTHER_BANK_LOAN")
	private String hasOtherBankLoan;

	@Column(name="IS_AREA_IMP_ENT")
	private String isAreaImpEnt;

	@Column(name="IS_ASSOCIATED_PARTY")
	private String isAssociatedParty;

	@Column(name="IS_CREDIT_CUST")
	private String isCreditCust;

	@Column(name="IS_EBANK_SIGN_CUST")
	private String isEbankSignCust;

	@Column(name="IS_FAX_TRANS_CUST")
	private String isFaxTransCust;

	@Column(name="IS_GROUP_CUST")
	private String isGroupCust;

	@Column(name="IS_HIGH_RISK_POLL")
	private String isHighRiskPoll;

	@Column(name="IS_HIGH_TECH_CORP")
	private String isHighTechCorp;

	@Column(name="IS_IMPORTANT_CUST")
	private String isImportantCust;

	@Column(name="IS_LEGAL_CORP")
	private String isLegalCorp;

	@Column(name="IS_LIMIT_INDUSTRY")
	private String isLimitIndustry;

	@Column(name="IS_LISTED_CORP")
	private String isListedCorp;

	@Column(name="IS_NEW_CORP")
	private String isNewCorp;

	@Column(name="IS_NOT_LOCAL_ENT")
	private String isNotLocalEnt;

	@Column(name="IS_NTNAL_MACRO_CTRL")
	private String isNtnalMacroCtrl;

	@Column(name="IS_PARTNER")
	private String isPartner;

	@Column(name="IS_PREP_ENT")
	private String isPrepEnt;

	@Column(name="IS_PRIVATE_CORP")
	private String isPrivateCorp;

	@Column(name="IS_RELA_GROUP")
	private String isRelaGroup;

	@Column(name="IS_RURAL")
	private String isRural;

	@Column(name="IS_RURAL_CORP")
	private String isRuralCorp;

	@Column(name="IS_SECRET")
	private String isSecret;

	@Column(name="IS_SEND_ECOMSTAT_FLAG")
	private String isSendEcomstatFlag;

	@Column(name="IS_SMALL_CORP")
	private String isSmallCorp;

	@Column(name="IS_SOE")
	private String isSoe;

	@Column(name="IS_STEEL_ENT")
	private String isSteelEnt;

	@Column(name="IS_TAIWAN_CORP")
	private String isTaiwanCorp;

	@Column(name="IS_THIS_BANK_CUST")
	private String isThisBankCust;

	@Column(name="IS_TOP_500")
	private String isTop500;

	@Column(name="IS_TWO_HIGH_ENT")
	private String isTwoHighEnt;

	private String iso14000;

	private String iso9000;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="PROJECT_FLAG")
	private String projectFlag;

	@Column(name="REALTY_FLAG")
	private String realtyFlag;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="UDIV_FLAG")
	private String udivFlag;

	public AcrmFCiOrgKeyflag() {
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBankCorpFlag() {
		return this.bankCorpFlag;
	}

	public void setBankCorpFlag(String bankCorpFlag) {
		this.bankCorpFlag = bankCorpFlag;
	}

	public String getBankruptFlag() {
		return this.bankruptFlag;
	}

	public void setBankruptFlag(String bankruptFlag) {
		this.bankruptFlag = bankruptFlag;
	}

	public String getFreeTaxFlag() {
		return this.freeTaxFlag;
	}

	public void setFreeTaxFlag(String freeTaxFlag) {
		this.freeTaxFlag = freeTaxFlag;
	}

	public String getGroupCreditFlag() {
		return this.groupCreditFlag;
	}

	public void setGroupCreditFlag(String groupCreditFlag) {
		this.groupCreditFlag = groupCreditFlag;
	}

	public String getHasBadLoan() {
		return this.hasBadLoan;
	}

	public void setHasBadLoan(String hasBadLoan) {
		this.hasBadLoan = hasBadLoan;
	}

	public String getHasIeRight() {
		return this.hasIeRight;
	}

	public void setHasIeRight(String hasIeRight) {
		this.hasIeRight = hasIeRight;
	}

	public String getHasOtherBankLoan() {
		return this.hasOtherBankLoan;
	}

	public void setHasOtherBankLoan(String hasOtherBankLoan) {
		this.hasOtherBankLoan = hasOtherBankLoan;
	}

	public String getIsAreaImpEnt() {
		return this.isAreaImpEnt;
	}

	public void setIsAreaImpEnt(String isAreaImpEnt) {
		this.isAreaImpEnt = isAreaImpEnt;
	}

	public String getIsAssociatedParty() {
		return this.isAssociatedParty;
	}

	public void setIsAssociatedParty(String isAssociatedParty) {
		this.isAssociatedParty = isAssociatedParty;
	}

	public String getIsCreditCust() {
		return this.isCreditCust;
	}

	public void setIsCreditCust(String isCreditCust) {
		this.isCreditCust = isCreditCust;
	}

	public String getIsEbankSignCust() {
		return this.isEbankSignCust;
	}

	public void setIsEbankSignCust(String isEbankSignCust) {
		this.isEbankSignCust = isEbankSignCust;
	}

	public String getIsFaxTransCust() {
		return this.isFaxTransCust;
	}

	public void setIsFaxTransCust(String isFaxTransCust) {
		this.isFaxTransCust = isFaxTransCust;
	}

	public String getIsGroupCust() {
		return this.isGroupCust;
	}

	public void setIsGroupCust(String isGroupCust) {
		this.isGroupCust = isGroupCust;
	}

	public String getIsHighRiskPoll() {
		return this.isHighRiskPoll;
	}

	public void setIsHighRiskPoll(String isHighRiskPoll) {
		this.isHighRiskPoll = isHighRiskPoll;
	}

	public String getIsHighTechCorp() {
		return this.isHighTechCorp;
	}

	public void setIsHighTechCorp(String isHighTechCorp) {
		this.isHighTechCorp = isHighTechCorp;
	}

	public String getIsImportantCust() {
		return this.isImportantCust;
	}

	public void setIsImportantCust(String isImportantCust) {
		this.isImportantCust = isImportantCust;
	}

	public String getIsLegalCorp() {
		return this.isLegalCorp;
	}

	public void setIsLegalCorp(String isLegalCorp) {
		this.isLegalCorp = isLegalCorp;
	}

	public String getIsLimitIndustry() {
		return this.isLimitIndustry;
	}

	public void setIsLimitIndustry(String isLimitIndustry) {
		this.isLimitIndustry = isLimitIndustry;
	}

	public String getIsListedCorp() {
		return this.isListedCorp;
	}

	public void setIsListedCorp(String isListedCorp) {
		this.isListedCorp = isListedCorp;
	}

	public String getIsNewCorp() {
		return this.isNewCorp;
	}

	public void setIsNewCorp(String isNewCorp) {
		this.isNewCorp = isNewCorp;
	}

	public String getIsNotLocalEnt() {
		return this.isNotLocalEnt;
	}

	public void setIsNotLocalEnt(String isNotLocalEnt) {
		this.isNotLocalEnt = isNotLocalEnt;
	}

	public String getIsNtnalMacroCtrl() {
		return this.isNtnalMacroCtrl;
	}

	public void setIsNtnalMacroCtrl(String isNtnalMacroCtrl) {
		this.isNtnalMacroCtrl = isNtnalMacroCtrl;
	}

	public String getIsPartner() {
		return this.isPartner;
	}

	public void setIsPartner(String isPartner) {
		this.isPartner = isPartner;
	}

	public String getIsPrepEnt() {
		return this.isPrepEnt;
	}

	public void setIsPrepEnt(String isPrepEnt) {
		this.isPrepEnt = isPrepEnt;
	}

	public String getIsPrivateCorp() {
		return this.isPrivateCorp;
	}

	public void setIsPrivateCorp(String isPrivateCorp) {
		this.isPrivateCorp = isPrivateCorp;
	}

	public String getIsRelaGroup() {
		return this.isRelaGroup;
	}

	public void setIsRelaGroup(String isRelaGroup) {
		this.isRelaGroup = isRelaGroup;
	}

	public String getIsRural() {
		return this.isRural;
	}

	public void setIsRural(String isRural) {
		this.isRural = isRural;
	}

	public String getIsRuralCorp() {
		return this.isRuralCorp;
	}

	public void setIsRuralCorp(String isRuralCorp) {
		this.isRuralCorp = isRuralCorp;
	}

	public String getIsSecret() {
		return this.isSecret;
	}

	public void setIsSecret(String isSecret) {
		this.isSecret = isSecret;
	}

	public String getIsSendEcomstatFlag() {
		return this.isSendEcomstatFlag;
	}

	public void setIsSendEcomstatFlag(String isSendEcomstatFlag) {
		this.isSendEcomstatFlag = isSendEcomstatFlag;
	}

	public String getIsSmallCorp() {
		return this.isSmallCorp;
	}

	public void setIsSmallCorp(String isSmallCorp) {
		this.isSmallCorp = isSmallCorp;
	}

	public String getIsSoe() {
		return this.isSoe;
	}

	public void setIsSoe(String isSoe) {
		this.isSoe = isSoe;
	}

	public String getIsSteelEnt() {
		return this.isSteelEnt;
	}

	public void setIsSteelEnt(String isSteelEnt) {
		this.isSteelEnt = isSteelEnt;
	}

	public String getIsTaiwanCorp() {
		return this.isTaiwanCorp;
	}

	public void setIsTaiwanCorp(String isTaiwanCorp) {
		this.isTaiwanCorp = isTaiwanCorp;
	}

	public String getIsThisBankCust() {
		return this.isThisBankCust;
	}

	public void setIsThisBankCust(String isThisBankCust) {
		this.isThisBankCust = isThisBankCust;
	}

	public String getIsTop500() {
		return this.isTop500;
	}

	public void setIsTop500(String isTop500) {
		this.isTop500 = isTop500;
	}

	public String getIsTwoHighEnt() {
		return this.isTwoHighEnt;
	}

	public void setIsTwoHighEnt(String isTwoHighEnt) {
		this.isTwoHighEnt = isTwoHighEnt;
	}

	public String getIso14000() {
		return this.iso14000;
	}

	public void setIso14000(String iso14000) {
		this.iso14000 = iso14000;
	}

	public String getIso9000() {
		return this.iso9000;
	}

	public void setIso9000(String iso9000) {
		this.iso9000 = iso9000;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getProjectFlag() {
		return this.projectFlag;
	}

	public void setProjectFlag(String projectFlag) {
		this.projectFlag = projectFlag;
	}

	public String getRealtyFlag() {
		return this.realtyFlag;
	}

	public void setRealtyFlag(String realtyFlag) {
		this.realtyFlag = realtyFlag;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getUdivFlag() {
		return this.udivFlag;
	}

	public void setUdivFlag(String udivFlag) {
		this.udivFlag = udivFlag;
	}

}