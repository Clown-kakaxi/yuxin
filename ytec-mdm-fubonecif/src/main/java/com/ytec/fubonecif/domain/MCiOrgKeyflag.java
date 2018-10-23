package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiOrgKeyflag entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_KEYFLAG")
public class MCiOrgKeyflag implements java.io.Serializable {

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
	//add by liuming 20170626
	private String shippingInd;//是否为航运行业
	private String isGreenIndus;//是否绿色环保行业
	private String isMaterialRisk;//是否环境、安全等重大风险企业
	private String isScienceTech;//是否科技金融行业
	private String isHighPollute;//是否两高一剩
	private String energySaving;//节能环保项目及服务贷款
	private String scientificEnt;//科技型企业类型
	private String enviroPenalties;//是否发生过环保处罚事件

	// Constructors

	/** default constructor */
	public MCiOrgKeyflag() {
	}

	/** minimal constructor */
	public MCiOrgKeyflag(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiOrgKeyflag(String custId, String isLegalCorp,
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
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo
			//add by liumng 20170626
			,String shippingInd, String isGreenIndus,
			String isMaterialRisk, String isScienceTech,
			String isHighPollute, String energySaving,
			String scientificEnt, String enviroPenalties
			) {
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
		//add by liuming 20170626
		this.shippingInd = shippingInd;
		this.isGreenIndus = isGreenIndus;
		this.isMaterialRisk = isMaterialRisk;
		this.isScienceTech = isScienceTech;
		this.isHighPollute = isHighPollute;
		this.energySaving = energySaving;
		this.scientificEnt = scientificEnt;
		this.enviroPenalties = enviroPenalties;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_ID", unique = true, nullable = false, length = 20)
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

	//add by liuming 20170626
	@Column(name = "SHIPPING_IND", length = 4)
	public String getShippingInd() {
		return shippingInd;
	}

	public void setShippingInd(String shippingInd) {
		this.shippingInd = shippingInd;
	}

	@Column(name = "IS_GREEN_INDUS", length = 4)
	public String getIsGreenIndus() {
		return isGreenIndus;
	}

	public void setIsGreenIndus(String isGreenIndus) {
		this.isGreenIndus = isGreenIndus;
	}
	
	@Column(name = "IS_MATERIAL_RISK", length = 4)
	public String getIsMaterialRisk() {
		return isMaterialRisk;
	}

	public void setIsMaterialRisk(String isMaterialRisk) {
		this.isMaterialRisk = isMaterialRisk;
	}

	@Column(name = "IS_SCIENCE_TECH", length = 4)
	public String getIsScienceTech() {
		return isScienceTech;
	}

	public void setIsScienceTech(String isScienceTech) {
		this.isScienceTech = isScienceTech;
	}

	@Column(name = "IS_HIGH_POLLUTE", length = 4)
	public String getIsHighPollute() {
		return isHighPollute;
	}

	public void setIsHighPollute(String isHighPollute) {
		this.isHighPollute = isHighPollute;
	}

	@Column(name = "ENERGY_SAVING", length = 4)
	public String getEnergySaving() {
		return energySaving;
	}

	public void setEnergySaving(String energySaving) {
		this.energySaving = energySaving;
	}

	@Column(name = "SCIENTIFIC_ENT", length = 4)
	public String getScientificEnt() {
		return scientificEnt;
	}

	public void setScientificEnt(String scientificEnt) {
		this.scientificEnt = scientificEnt;
	}

	@Column(name = "ENVIRO_PENALTIES", length = 4)
	public String getEnviroPenalties() {
		return enviroPenalties;
	}

	public void setEnviroPenalties(String enviroPenalties) {
		this.enviroPenalties = enviroPenalties;
	}

}