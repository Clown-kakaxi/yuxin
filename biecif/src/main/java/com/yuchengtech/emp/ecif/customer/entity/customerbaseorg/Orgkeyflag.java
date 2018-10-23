package com.yuchengtech.emp.ecif.customer.entity.customerbaseorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

@Entity
@Table(name = "ORGKEYFLAG")
public class Orgkeyflag implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="BANKRUPT_FLAG", length=1)
	private String bankruptFlag;

	@Column(name="FINANCE_REPORT_FLAG", length=1)
	private String financeReportFlag;

	@Column(name="GROUP_CREDIT_FLAG", length=1)
	private String groupCreditFlag;

	@Column(name="HAS_BAD_LOAN", length=1)
	private String hasBadLoan;

	@Column(name="HAS_BOARD", length=1)
	private String hasBoard;

	@Column(name="HAS_IE_RIGHT", length=1)
	private String hasIeRight;

	@Column(name="HAS_OTHER_BANK_LOAN", length=1)
	private String hasOtherBankLoan;

	@Column(name="HAS_PARENT_CORP", length=1)
	private String hasParentCorp;

	@Column(name="HAS_SPECIAL_BUSI_LIC", length=1)
	private String hasSpecialBusiLic;

	@Column(name="IMPORTANT_CUST_TYPE", length=20)
	private String importantCustType;

	@Column(name="IS_ASSOCIATED_PARTY", length=1)
	private String isAssociatedParty;

	@Column(name="IS_CREDIT_CUST", length=1)
	private String isCreditCust;

	@Column(name="IS_EBANK_SIGN_CUST", length=1)
	private String isEbankSignCust;

	@Column(name="IS_GROUP_CUST", length=1)
	private String isGroupCust;

	@Column(name="IS_HIGH_TECH_CORP", length=1)
	private String isHighTechCorp;

	@Column(name="IS_IMPORTANT_CUST", length=1)
	private String isImportantCust;

	@Column(name="IS_LEGAL_CORP", length=1)
	private String isLegalCorp;

	@Column(name="IS_LIMIT_INDUSTRY", length=1)
	private String isLimitIndustry;

	@Column(name="IS_LISTED_CORP", length=1)
	private String isListedCorp;

	@Column(name="IS_NEW_CORP", length=1)
	private String isNewCorp;

	@Column(name="IS_PRIVATE_CORP", length=1)
	private String isPrivateCorp;

	@Column(name="IS_RELA_GROUP", length=1)
	private String isRelaGroup;

	@Column(name="IS_RURAL", length=1)
	private String isRural;

	@Column(name="IS_RURAL_CORP", length=1)
	private String isRuralCorp;

	@Column(name="IS_SECRET", length=1)
	private String isSecret;

	@Column(name="IS_SMALL_CORP", length=1)
	private String isSmallCorp;

	@Column(name="PROJECT_FLAG", length=1)
	private String projectFlag;

	@Column(name="REALTY_FLAG", length=1)
	private String realtyFlag;

	@Column(name="UDIV_FLAG", length=1)
	private String udivFlag;
	
	@Column(name="GRT_CUST_FLAG", length=1)
	private String grtCustFlag;
	
	@Column(name="BANK_CORP_FLAG", length=1)
	private String bankCorpFlag;
	
	@Column(name="PAY_LIST", length=1)
	private String payList;
	
	@Column(name="PAY_CREDIT", length=1)
	private String payCredit;
	
	@Column(name="FREE_TAX_FLAG", length=1)
	private String freeTaxFlag;

	@Column(name="IS_GUARANTY", length=1)
	private String isGuaranty;
	
	@Column(name="IS_EVALUATE", length=1)
	private String isEvaluate;
	
	@Column(name="IS_AUDIT", length=1)
	private String isAudit;
	
	@Column(name="IS_INSURANCE", length=1)
	private String isInsurance;
	
	@Column(name="IS_SUPERVISION", length=1)
	private String isSupervision;
	
	@Column(name="IS_TOP_500", length=1)
	private String isTop500;
	
	@Column(name="CASH_ADMIN_CUST_TYPE", length=1)
	private String cashAdminCustType;

	@Column(name="IS_AGENT_BANK_CUST", length=1)
	private String isAgentBankCust;

	@Column(name="IS_RELATED_PARTY", length=1)
	private String isRelatedParty;

	@Column(name="IS_PARTNER", length=1)
	private String isPartner;
	
	public String getFreeTaxFlag() {
		return freeTaxFlag;
	}

	public void setFreeTaxFlag(String freeTaxFlag) {
		this.freeTaxFlag = freeTaxFlag;
	}

	public String getGrtCustFlag() {
		return grtCustFlag;
	}

	public void setGrtCustFlag(String grtCustFlag) {
		this.grtCustFlag = grtCustFlag;
	}

	public String getBankCorpFlag() {
		return bankCorpFlag;
	}

	public void setBankCorpFlag(String bankCorpFlag) {
		this.bankCorpFlag = bankCorpFlag;
	}

	public String getPayList() {
		return payList;
	}

	public void setPayList(String payList) {
		this.payList = payList;
	}

	public String getPayCredit() {
		return payCredit;
	}

	public void setPayCredit(String payCredit) {
		this.payCredit = payCredit;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getBankruptFlag() {
		return bankruptFlag;
	}

	public void setBankruptFlag(String bankruptFlag) {
		this.bankruptFlag = bankruptFlag;
	}

	public String getFinanceReportFlag() {
		return financeReportFlag;
	}

	public void setFinanceReportFlag(String financeReportFlag) {
		this.financeReportFlag = financeReportFlag;
	}

	public String getGroupCreditFlag() {
		return groupCreditFlag;
	}

	public void setGroupCreditFlag(String groupCreditFlag) {
		this.groupCreditFlag = groupCreditFlag;
	}

	public String getHasBadLoan() {
		return hasBadLoan;
	}

	public void setHasBadLoan(String hasBadLoan) {
		this.hasBadLoan = hasBadLoan;
	}

	public String getHasBoard() {
		return hasBoard;
	}

	public void setHasBoard(String hasBoard) {
		this.hasBoard = hasBoard;
	}

	public String getHasIeRight() {
		return hasIeRight;
	}

	public void setHasIeRight(String hasIeRight) {
		this.hasIeRight = hasIeRight;
	}

	public String getHasOtherBankLoan() {
		return hasOtherBankLoan;
	}

	public void setHasOtherBankLoan(String hasOtherBankLoan) {
		this.hasOtherBankLoan = hasOtherBankLoan;
	}

	public String getHasParentCorp() {
		return hasParentCorp;
	}

	public void setHasParentCorp(String hasParentCorp) {
		this.hasParentCorp = hasParentCorp;
	}

	public String getHasSpecialBusiLic() {
		return hasSpecialBusiLic;
	}

	public void setHasSpecialBusiLic(String hasSpecialBusiLic) {
		this.hasSpecialBusiLic = hasSpecialBusiLic;
	}

	public String getImportantCustType() {
		return importantCustType;
	}

	public void setImportantCustType(String importantCustType) {
		this.importantCustType = importantCustType;
	}

	public String getIsAssociatedParty() {
		return isAssociatedParty;
	}

	public void setIsAssociatedParty(String isAssociatedParty) {
		this.isAssociatedParty = isAssociatedParty;
	}

	public String getIsCreditCust() {
		return isCreditCust;
	}

	public void setIsCreditCust(String isCreditCust) {
		this.isCreditCust = isCreditCust;
	}

	public String getIsEbankSignCust() {
		return isEbankSignCust;
	}

	public void setIsEbankSignCust(String isEbankSignCust) {
		this.isEbankSignCust = isEbankSignCust;
	}

	public String getIsGroupCust() {
		return isGroupCust;
	}

	public void setIsGroupCust(String isGroupCust) {
		this.isGroupCust = isGroupCust;
	}

	public String getIsHighTechCorp() {
		return isHighTechCorp;
	}

	public void setIsHighTechCorp(String isHighTechCorp) {
		this.isHighTechCorp = isHighTechCorp;
	}

	public String getIsImportantCust() {
		return isImportantCust;
	}

	public void setIsImportantCust(String isImportantCust) {
		this.isImportantCust = isImportantCust;
	}

	public String getIsLegalCorp() {
		return isLegalCorp;
	}

	public void setIsLegalCorp(String isLegalCorp) {
		this.isLegalCorp = isLegalCorp;
	}

	public String getIsLimitIndustry() {
		return isLimitIndustry;
	}

	public void setIsLimitIndustry(String isLimitIndustry) {
		this.isLimitIndustry = isLimitIndustry;
	}

	public String getIsListedCorp() {
		return isListedCorp;
	}

	public void setIsListedCorp(String isListedCorp) {
		this.isListedCorp = isListedCorp;
	}

	public String getIsNewCorp() {
		return isNewCorp;
	}

	public void setIsNewCorp(String isNewCorp) {
		this.isNewCorp = isNewCorp;
	}

	public String getIsPrivateCorp() {
		return isPrivateCorp;
	}

	public void setIsPrivateCorp(String isPrivateCorp) {
		this.isPrivateCorp = isPrivateCorp;
	}

	public String getIsRelaGroup() {
		return isRelaGroup;
	}

	public void setIsRelaGroup(String isRelaGroup) {
		this.isRelaGroup = isRelaGroup;
	}

	public String getIsRural() {
		return isRural;
	}

	public void setIsRural(String isRural) {
		this.isRural = isRural;
	}

	public String getIsRuralCorp() {
		return isRuralCorp;
	}

	public void setIsRuralCorp(String isRuralCorp) {
		this.isRuralCorp = isRuralCorp;
	}

	public String getIsSecret() {
		return isSecret;
	}

	public void setIsSecret(String isSecret) {
		this.isSecret = isSecret;
	}

	public String getIsSmallCorp() {
		return isSmallCorp;
	}

	public void setIsSmallCorp(String isSmallCorp) {
		this.isSmallCorp = isSmallCorp;
	}

	public String getProjectFlag() {
		return projectFlag;
	}

	public void setProjectFlag(String projectFlag) {
		this.projectFlag = projectFlag;
	}

	public String getRealtyFlag() {
		return realtyFlag;
	}

	public void setRealtyFlag(String realtyFlag) {
		this.realtyFlag = realtyFlag;
	}

	public String getUdivFlag() {
		return udivFlag;
	}

	public void setUdivFlag(String udivFlag) {
		this.udivFlag = udivFlag;
	}

	
	public String getIsGuaranty() {
		return isGuaranty;
	}

	public void setIsGuaranty(String isGuaranty) {
		this.isGuaranty = isGuaranty;
	}

	public String getIsEvaluate() {
		return isEvaluate;
	}

	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public String getIsInsurance() {
		return isInsurance;
	}

	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}

	public String getIsSupervision() {
		return isSupervision;
	}

	public void setIsSupervision(String isSupervision) {
		this.isSupervision = isSupervision;
	}

	public String getIsTop500() {
		return isTop500;
	}

	public void setIsTop500(String isTop500) {
		this.isTop500 = isTop500;
	}

	public String getCashAdminCustType() {
		return cashAdminCustType;
	}

	public void setCashAdminCustType(String cashAdminCustType) {
		this.cashAdminCustType = cashAdminCustType;
	}

	public String getIsAgentBankCust() {
		return isAgentBankCust;
	}

	public void setIsAgentBankCust(String isAgentBankCust) {
		this.isAgentBankCust = isAgentBankCust;
	}

	public String getIsRelatedParty() {
		return isRelatedParty;
	}

	public void setIsRelatedParty(String isRelatedParty) {
		this.isRelatedParty = isRelatedParty;
	}

	public String getIsPartner() {
		return isPartner;
	}

	public void setIsPartner(String isPartner) {
		this.isPartner = isPartner;
	}

}
