package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiPerKeyflag entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_KEYFLAG")
public class MCiPerKeyflag implements java.io.Serializable {

	// Fields

	private String custId;
	private String isMerchant;
	private String isPeasant;
	private String isCrediblePeasant;
	private String isShareholder;
	private String isEmployee;
	private String hasThisBankLoan;
	private String hasOtherBankLoan;
	private String hasBadLoan;
	private String isGuarantee;
	private String isNative;
	private String isOnJobWorker;
	private String hasCreditInfo;
	private Double creditAmount;
	private String isImportantCust;
	private String isCreditCust;
	private String isSecretCust;
	private String isPrivBankCust;
	private String isPayrollCust;
	private String isDebitCard;
	private String isEbankSignCust;
	private String hasEndoInsure;
	private String hasMediInsure;
	private String hasIdleInsure;
	private String hasInjuryInsure;
	private String hasBirthInsure;
	private String hasHouseFund;
	private String hasCar;
	private String hasPhoto;
	private String foreignPassportFlag;
	private String foreignHabitatioFlag;
	private String usaTaxFlag;
	private String isDividendCust;
	private String isFaxTransCust;
	private String isSendEcomstatFlag;
	private String isUptoViplevel;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerKeyflag() {
	}

	/** minimal constructor */
	public MCiPerKeyflag(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiPerKeyflag(String custId, String isMerchant, String isPeasant,
			String isCrediblePeasant, String isShareholder, String isEmployee,
			String hasThisBankLoan, String hasOtherBankLoan, String hasBadLoan,
			String isGuarantee, String isNative, String isOnJobWorker,
			String hasCreditInfo, Double creditAmount, String isImportantCust,
			String isCreditCust, String isSecretCust, String isPrivBankCust,
			String isPayrollCust, String isDebitCard, String isEbankSignCust,
			String hasEndoInsure, String hasMediInsure, String hasIdleInsure,
			String hasInjuryInsure, String hasBirthInsure, String hasHouseFund,
			String hasCar, String hasPhoto, String foreignPassportFlag,
			String foreignHabitatioFlag, String usaTaxFlag,
			String isDividendCust, String isFaxTransCust,
			String isSendEcomstatFlag, String isUptoViplevel,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custId = custId;
		this.isMerchant = isMerchant;
		this.isPeasant = isPeasant;
		this.isCrediblePeasant = isCrediblePeasant;
		this.isShareholder = isShareholder;
		this.isEmployee = isEmployee;
		this.hasThisBankLoan = hasThisBankLoan;
		this.hasOtherBankLoan = hasOtherBankLoan;
		this.hasBadLoan = hasBadLoan;
		this.isGuarantee = isGuarantee;
		this.isNative = isNative;
		this.isOnJobWorker = isOnJobWorker;
		this.hasCreditInfo = hasCreditInfo;
		this.creditAmount = creditAmount;
		this.isImportantCust = isImportantCust;
		this.isCreditCust = isCreditCust;
		this.isSecretCust = isSecretCust;
		this.isPrivBankCust = isPrivBankCust;
		this.isPayrollCust = isPayrollCust;
		this.isDebitCard = isDebitCard;
		this.isEbankSignCust = isEbankSignCust;
		this.hasEndoInsure = hasEndoInsure;
		this.hasMediInsure = hasMediInsure;
		this.hasIdleInsure = hasIdleInsure;
		this.hasInjuryInsure = hasInjuryInsure;
		this.hasBirthInsure = hasBirthInsure;
		this.hasHouseFund = hasHouseFund;
		this.hasCar = hasCar;
		this.hasPhoto = hasPhoto;
		this.foreignPassportFlag = foreignPassportFlag;
		this.foreignHabitatioFlag = foreignHabitatioFlag;
		this.usaTaxFlag = usaTaxFlag;
		this.isDividendCust = isDividendCust;
		this.isFaxTransCust = isFaxTransCust;
		this.isSendEcomstatFlag = isSendEcomstatFlag;
		this.isUptoViplevel = isUptoViplevel;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
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

	@Column(name = "IS_MERCHANT", length = 1)
	public String getIsMerchant() {
		return this.isMerchant;
	}

	public void setIsMerchant(String isMerchant) {
		this.isMerchant = isMerchant;
	}

	@Column(name = "IS_PEASANT", length = 1)
	public String getIsPeasant() {
		return this.isPeasant;
	}

	public void setIsPeasant(String isPeasant) {
		this.isPeasant = isPeasant;
	}

	@Column(name = "IS_CREDIBLE_PEASANT", length = 1)
	public String getIsCrediblePeasant() {
		return this.isCrediblePeasant;
	}

	public void setIsCrediblePeasant(String isCrediblePeasant) {
		this.isCrediblePeasant = isCrediblePeasant;
	}

	@Column(name = "IS_SHAREHOLDER", length = 1)
	public String getIsShareholder() {
		return this.isShareholder;
	}

	public void setIsShareholder(String isShareholder) {
		this.isShareholder = isShareholder;
	}

	@Column(name = "IS_EMPLOYEE", length = 1)
	public String getIsEmployee() {
		return this.isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}

	@Column(name = "HAS_THIS_BANK_LOAN", length = 1)
	public String getHasThisBankLoan() {
		return this.hasThisBankLoan;
	}

	public void setHasThisBankLoan(String hasThisBankLoan) {
		this.hasThisBankLoan = hasThisBankLoan;
	}

	@Column(name = "HAS_OTHER_BANK_LOAN", length = 1)
	public String getHasOtherBankLoan() {
		return this.hasOtherBankLoan;
	}

	public void setHasOtherBankLoan(String hasOtherBankLoan) {
		this.hasOtherBankLoan = hasOtherBankLoan;
	}

	@Column(name = "HAS_BAD_LOAN", length = 1)
	public String getHasBadLoan() {
		return this.hasBadLoan;
	}

	public void setHasBadLoan(String hasBadLoan) {
		this.hasBadLoan = hasBadLoan;
	}

	@Column(name = "IS_GUARANTEE", length = 1)
	public String getIsGuarantee() {
		return this.isGuarantee;
	}

	public void setIsGuarantee(String isGuarantee) {
		this.isGuarantee = isGuarantee;
	}

	@Column(name = "IS_NATIVE", length = 1)
	public String getIsNative() {
		return this.isNative;
	}

	public void setIsNative(String isNative) {
		this.isNative = isNative;
	}

	@Column(name = "IS_ON_JOB_WORKER", length = 1)
	public String getIsOnJobWorker() {
		return this.isOnJobWorker;
	}

	public void setIsOnJobWorker(String isOnJobWorker) {
		this.isOnJobWorker = isOnJobWorker;
	}

	@Column(name = "HAS_CREDIT_INFO", length = 1)
	public String getHasCreditInfo() {
		return this.hasCreditInfo;
	}

	public void setHasCreditInfo(String hasCreditInfo) {
		this.hasCreditInfo = hasCreditInfo;
	}

	@Column(name = "CREDIT_AMOUNT", precision = 17)
	public Double getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	@Column(name = "IS_IMPORTANT_CUST", length = 1)
	public String getIsImportantCust() {
		return this.isImportantCust;
	}

	public void setIsImportantCust(String isImportantCust) {
		this.isImportantCust = isImportantCust;
	}

	@Column(name = "IS_CREDIT_CUST", length = 1)
	public String getIsCreditCust() {
		return this.isCreditCust;
	}

	public void setIsCreditCust(String isCreditCust) {
		this.isCreditCust = isCreditCust;
	}

	@Column(name = "IS_SECRET_CUST", length = 1)
	public String getIsSecretCust() {
		return this.isSecretCust;
	}

	public void setIsSecretCust(String isSecretCust) {
		this.isSecretCust = isSecretCust;
	}

	@Column(name = "IS_PRIV_BANK_CUST", length = 1)
	public String getIsPrivBankCust() {
		return this.isPrivBankCust;
	}

	public void setIsPrivBankCust(String isPrivBankCust) {
		this.isPrivBankCust = isPrivBankCust;
	}

	@Column(name = "IS_PAYROLL_CUST", length = 1)
	public String getIsPayrollCust() {
		return this.isPayrollCust;
	}

	public void setIsPayrollCust(String isPayrollCust) {
		this.isPayrollCust = isPayrollCust;
	}

	@Column(name = "IS_DEBIT_CARD", length = 1)
	public String getIsDebitCard() {
		return this.isDebitCard;
	}

	public void setIsDebitCard(String isDebitCard) {
		this.isDebitCard = isDebitCard;
	}

	@Column(name = "IS_EBANK_SIGN_CUST", length = 1)
	public String getIsEbankSignCust() {
		return this.isEbankSignCust;
	}

	public void setIsEbankSignCust(String isEbankSignCust) {
		this.isEbankSignCust = isEbankSignCust;
	}

	@Column(name = "HAS_ENDO_INSURE", length = 1)
	public String getHasEndoInsure() {
		return this.hasEndoInsure;
	}

	public void setHasEndoInsure(String hasEndoInsure) {
		this.hasEndoInsure = hasEndoInsure;
	}

	@Column(name = "HAS_MEDI_INSURE", length = 1)
	public String getHasMediInsure() {
		return this.hasMediInsure;
	}

	public void setHasMediInsure(String hasMediInsure) {
		this.hasMediInsure = hasMediInsure;
	}

	@Column(name = "HAS_IDLE_INSURE", length = 1)
	public String getHasIdleInsure() {
		return this.hasIdleInsure;
	}

	public void setHasIdleInsure(String hasIdleInsure) {
		this.hasIdleInsure = hasIdleInsure;
	}

	@Column(name = "HAS_INJURY_INSURE", length = 1)
	public String getHasInjuryInsure() {
		return this.hasInjuryInsure;
	}

	public void setHasInjuryInsure(String hasInjuryInsure) {
		this.hasInjuryInsure = hasInjuryInsure;
	}

	@Column(name = "HAS_BIRTH_INSURE", length = 1)
	public String getHasBirthInsure() {
		return this.hasBirthInsure;
	}

	public void setHasBirthInsure(String hasBirthInsure) {
		this.hasBirthInsure = hasBirthInsure;
	}

	@Column(name = "HAS_HOUSE_FUND", length = 1)
	public String getHasHouseFund() {
		return this.hasHouseFund;
	}

	public void setHasHouseFund(String hasHouseFund) {
		this.hasHouseFund = hasHouseFund;
	}

	@Column(name = "HAS_CAR", length = 1)
	public String getHasCar() {
		return this.hasCar;
	}

	public void setHasCar(String hasCar) {
		this.hasCar = hasCar;
	}

	@Column(name = "HAS_PHOTO", length = 1)
	public String getHasPhoto() {
		return this.hasPhoto;
	}

	public void setHasPhoto(String hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	@Column(name = "FOREIGN_PASSPORT_FLAG", length = 1)
	public String getForeignPassportFlag() {
		return this.foreignPassportFlag;
	}

	public void setForeignPassportFlag(String foreignPassportFlag) {
		this.foreignPassportFlag = foreignPassportFlag;
	}

	@Column(name = "FOREIGN_HABITATIO_FLAG", length = 1)
	public String getForeignHabitatioFlag() {
		return this.foreignHabitatioFlag;
	}

	public void setForeignHabitatioFlag(String foreignHabitatioFlag) {
		this.foreignHabitatioFlag = foreignHabitatioFlag;
	}

	@Column(name = "USA_TAX_FLAG", length = 1)
	public String getUsaTaxFlag() {
		return this.usaTaxFlag;
	}

	public void setUsaTaxFlag(String usaTaxFlag) {
		this.usaTaxFlag = usaTaxFlag;
	}

	@Column(name = "IS_DIVIDEND_CUST", length = 1)
	public String getIsDividendCust() {
		return this.isDividendCust;
	}

	public void setIsDividendCust(String isDividendCust) {
		this.isDividendCust = isDividendCust;
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

	@Column(name = "IS_UPTO_VIPLEVEL", length = 1)
	public String getIsUptoViplevel() {
		return this.isUptoViplevel;
	}

	public void setIsUptoViplevel(String isUptoViplevel) {
		this.isUptoViplevel = isUptoViplevel;
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

}