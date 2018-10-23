package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiPerKeyflagId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerKeyflagId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiPerKeyflagId() {
	}

	/** minimal constructor */
	public HMCiPerKeyflagId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerKeyflagId(String custId, String isMerchant, String isPeasant,
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
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		if (!(other instanceof HMCiPerKeyflagId))
			return false;
		HMCiPerKeyflagId castOther = (HMCiPerKeyflagId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getIsMerchant() == castOther.getIsMerchant()) || (this
						.getIsMerchant() != null
						&& castOther.getIsMerchant() != null && this
						.getIsMerchant().equals(castOther.getIsMerchant())))
				&& ((this.getIsPeasant() == castOther.getIsPeasant()) || (this
						.getIsPeasant() != null
						&& castOther.getIsPeasant() != null && this
						.getIsPeasant().equals(castOther.getIsPeasant())))
				&& ((this.getIsCrediblePeasant() == castOther
						.getIsCrediblePeasant()) || (this
						.getIsCrediblePeasant() != null
						&& castOther.getIsCrediblePeasant() != null && this
						.getIsCrediblePeasant().equals(
								castOther.getIsCrediblePeasant())))
				&& ((this.getIsShareholder() == castOther.getIsShareholder()) || (this
						.getIsShareholder() != null
						&& castOther.getIsShareholder() != null && this
						.getIsShareholder()
						.equals(castOther.getIsShareholder())))
				&& ((this.getIsEmployee() == castOther.getIsEmployee()) || (this
						.getIsEmployee() != null
						&& castOther.getIsEmployee() != null && this
						.getIsEmployee().equals(castOther.getIsEmployee())))
				&& ((this.getHasThisBankLoan() == castOther
						.getHasThisBankLoan()) || (this.getHasThisBankLoan() != null
						&& castOther.getHasThisBankLoan() != null && this
						.getHasThisBankLoan().equals(
								castOther.getHasThisBankLoan())))
				&& ((this.getHasOtherBankLoan() == castOther
						.getHasOtherBankLoan()) || (this.getHasOtherBankLoan() != null
						&& castOther.getHasOtherBankLoan() != null && this
						.getHasOtherBankLoan().equals(
								castOther.getHasOtherBankLoan())))
				&& ((this.getHasBadLoan() == castOther.getHasBadLoan()) || (this
						.getHasBadLoan() != null
						&& castOther.getHasBadLoan() != null && this
						.getHasBadLoan().equals(castOther.getHasBadLoan())))
				&& ((this.getIsGuarantee() == castOther.getIsGuarantee()) || (this
						.getIsGuarantee() != null
						&& castOther.getIsGuarantee() != null && this
						.getIsGuarantee().equals(castOther.getIsGuarantee())))
				&& ((this.getIsNative() == castOther.getIsNative()) || (this
						.getIsNative() != null
						&& castOther.getIsNative() != null && this
						.getIsNative().equals(castOther.getIsNative())))
				&& ((this.getIsOnJobWorker() == castOther.getIsOnJobWorker()) || (this
						.getIsOnJobWorker() != null
						&& castOther.getIsOnJobWorker() != null && this
						.getIsOnJobWorker()
						.equals(castOther.getIsOnJobWorker())))
				&& ((this.getHasCreditInfo() == castOther.getHasCreditInfo()) || (this
						.getHasCreditInfo() != null
						&& castOther.getHasCreditInfo() != null && this
						.getHasCreditInfo()
						.equals(castOther.getHasCreditInfo())))
				&& ((this.getCreditAmount() == castOther.getCreditAmount()) || (this
						.getCreditAmount() != null
						&& castOther.getCreditAmount() != null && this
						.getCreditAmount().equals(castOther.getCreditAmount())))
				&& ((this.getIsImportantCust() == castOther
						.getIsImportantCust()) || (this.getIsImportantCust() != null
						&& castOther.getIsImportantCust() != null && this
						.getIsImportantCust().equals(
								castOther.getIsImportantCust())))
				&& ((this.getIsCreditCust() == castOther.getIsCreditCust()) || (this
						.getIsCreditCust() != null
						&& castOther.getIsCreditCust() != null && this
						.getIsCreditCust().equals(castOther.getIsCreditCust())))
				&& ((this.getIsSecretCust() == castOther.getIsSecretCust()) || (this
						.getIsSecretCust() != null
						&& castOther.getIsSecretCust() != null && this
						.getIsSecretCust().equals(castOther.getIsSecretCust())))
				&& ((this.getIsPrivBankCust() == castOther.getIsPrivBankCust()) || (this
						.getIsPrivBankCust() != null
						&& castOther.getIsPrivBankCust() != null && this
						.getIsPrivBankCust().equals(
								castOther.getIsPrivBankCust())))
				&& ((this.getIsPayrollCust() == castOther.getIsPayrollCust()) || (this
						.getIsPayrollCust() != null
						&& castOther.getIsPayrollCust() != null && this
						.getIsPayrollCust()
						.equals(castOther.getIsPayrollCust())))
				&& ((this.getIsDebitCard() == castOther.getIsDebitCard()) || (this
						.getIsDebitCard() != null
						&& castOther.getIsDebitCard() != null && this
						.getIsDebitCard().equals(castOther.getIsDebitCard())))
				&& ((this.getIsEbankSignCust() == castOther
						.getIsEbankSignCust()) || (this.getIsEbankSignCust() != null
						&& castOther.getIsEbankSignCust() != null && this
						.getIsEbankSignCust().equals(
								castOther.getIsEbankSignCust())))
				&& ((this.getHasEndoInsure() == castOther.getHasEndoInsure()) || (this
						.getHasEndoInsure() != null
						&& castOther.getHasEndoInsure() != null && this
						.getHasEndoInsure()
						.equals(castOther.getHasEndoInsure())))
				&& ((this.getHasMediInsure() == castOther.getHasMediInsure()) || (this
						.getHasMediInsure() != null
						&& castOther.getHasMediInsure() != null && this
						.getHasMediInsure()
						.equals(castOther.getHasMediInsure())))
				&& ((this.getHasIdleInsure() == castOther.getHasIdleInsure()) || (this
						.getHasIdleInsure() != null
						&& castOther.getHasIdleInsure() != null && this
						.getHasIdleInsure()
						.equals(castOther.getHasIdleInsure())))
				&& ((this.getHasInjuryInsure() == castOther
						.getHasInjuryInsure()) || (this.getHasInjuryInsure() != null
						&& castOther.getHasInjuryInsure() != null && this
						.getHasInjuryInsure().equals(
								castOther.getHasInjuryInsure())))
				&& ((this.getHasBirthInsure() == castOther.getHasBirthInsure()) || (this
						.getHasBirthInsure() != null
						&& castOther.getHasBirthInsure() != null && this
						.getHasBirthInsure().equals(
								castOther.getHasBirthInsure())))
				&& ((this.getHasHouseFund() == castOther.getHasHouseFund()) || (this
						.getHasHouseFund() != null
						&& castOther.getHasHouseFund() != null && this
						.getHasHouseFund().equals(castOther.getHasHouseFund())))
				&& ((this.getHasCar() == castOther.getHasCar()) || (this
						.getHasCar() != null
						&& castOther.getHasCar() != null && this.getHasCar()
						.equals(castOther.getHasCar())))
				&& ((this.getHasPhoto() == castOther.getHasPhoto()) || (this
						.getHasPhoto() != null
						&& castOther.getHasPhoto() != null && this
						.getHasPhoto().equals(castOther.getHasPhoto())))
				&& ((this.getForeignPassportFlag() == castOther
						.getForeignPassportFlag()) || (this
						.getForeignPassportFlag() != null
						&& castOther.getForeignPassportFlag() != null && this
						.getForeignPassportFlag().equals(
								castOther.getForeignPassportFlag())))
				&& ((this.getForeignHabitatioFlag() == castOther
						.getForeignHabitatioFlag()) || (this
						.getForeignHabitatioFlag() != null
						&& castOther.getForeignHabitatioFlag() != null && this
						.getForeignHabitatioFlag().equals(
								castOther.getForeignHabitatioFlag())))
				&& ((this.getUsaTaxFlag() == castOther.getUsaTaxFlag()) || (this
						.getUsaTaxFlag() != null
						&& castOther.getUsaTaxFlag() != null && this
						.getUsaTaxFlag().equals(castOther.getUsaTaxFlag())))
				&& ((this.getIsDividendCust() == castOther.getIsDividendCust()) || (this
						.getIsDividendCust() != null
						&& castOther.getIsDividendCust() != null && this
						.getIsDividendCust().equals(
								castOther.getIsDividendCust())))
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
				&& ((this.getIsUptoViplevel() == castOther.getIsUptoViplevel()) || (this
						.getIsUptoViplevel() != null
						&& castOther.getIsUptoViplevel() != null && this
						.getIsUptoViplevel().equals(
								castOther.getIsUptoViplevel())))
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
				+ (getIsMerchant() == null ? 0 : this.getIsMerchant()
						.hashCode());
		result = 37 * result
				+ (getIsPeasant() == null ? 0 : this.getIsPeasant().hashCode());
		result = 37
				* result
				+ (getIsCrediblePeasant() == null ? 0 : this
						.getIsCrediblePeasant().hashCode());
		result = 37
				* result
				+ (getIsShareholder() == null ? 0 : this.getIsShareholder()
						.hashCode());
		result = 37
				* result
				+ (getIsEmployee() == null ? 0 : this.getIsEmployee()
						.hashCode());
		result = 37
				* result
				+ (getHasThisBankLoan() == null ? 0 : this.getHasThisBankLoan()
						.hashCode());
		result = 37
				* result
				+ (getHasOtherBankLoan() == null ? 0 : this
						.getHasOtherBankLoan().hashCode());
		result = 37
				* result
				+ (getHasBadLoan() == null ? 0 : this.getHasBadLoan()
						.hashCode());
		result = 37
				* result
				+ (getIsGuarantee() == null ? 0 : this.getIsGuarantee()
						.hashCode());
		result = 37 * result
				+ (getIsNative() == null ? 0 : this.getIsNative().hashCode());
		result = 37
				* result
				+ (getIsOnJobWorker() == null ? 0 : this.getIsOnJobWorker()
						.hashCode());
		result = 37
				* result
				+ (getHasCreditInfo() == null ? 0 : this.getHasCreditInfo()
						.hashCode());
		result = 37
				* result
				+ (getCreditAmount() == null ? 0 : this.getCreditAmount()
						.hashCode());
		result = 37
				* result
				+ (getIsImportantCust() == null ? 0 : this.getIsImportantCust()
						.hashCode());
		result = 37
				* result
				+ (getIsCreditCust() == null ? 0 : this.getIsCreditCust()
						.hashCode());
		result = 37
				* result
				+ (getIsSecretCust() == null ? 0 : this.getIsSecretCust()
						.hashCode());
		result = 37
				* result
				+ (getIsPrivBankCust() == null ? 0 : this.getIsPrivBankCust()
						.hashCode());
		result = 37
				* result
				+ (getIsPayrollCust() == null ? 0 : this.getIsPayrollCust()
						.hashCode());
		result = 37
				* result
				+ (getIsDebitCard() == null ? 0 : this.getIsDebitCard()
						.hashCode());
		result = 37
				* result
				+ (getIsEbankSignCust() == null ? 0 : this.getIsEbankSignCust()
						.hashCode());
		result = 37
				* result
				+ (getHasEndoInsure() == null ? 0 : this.getHasEndoInsure()
						.hashCode());
		result = 37
				* result
				+ (getHasMediInsure() == null ? 0 : this.getHasMediInsure()
						.hashCode());
		result = 37
				* result
				+ (getHasIdleInsure() == null ? 0 : this.getHasIdleInsure()
						.hashCode());
		result = 37
				* result
				+ (getHasInjuryInsure() == null ? 0 : this.getHasInjuryInsure()
						.hashCode());
		result = 37
				* result
				+ (getHasBirthInsure() == null ? 0 : this.getHasBirthInsure()
						.hashCode());
		result = 37
				* result
				+ (getHasHouseFund() == null ? 0 : this.getHasHouseFund()
						.hashCode());
		result = 37 * result
				+ (getHasCar() == null ? 0 : this.getHasCar().hashCode());
		result = 37 * result
				+ (getHasPhoto() == null ? 0 : this.getHasPhoto().hashCode());
		result = 37
				* result
				+ (getForeignPassportFlag() == null ? 0 : this
						.getForeignPassportFlag().hashCode());
		result = 37
				* result
				+ (getForeignHabitatioFlag() == null ? 0 : this
						.getForeignHabitatioFlag().hashCode());
		result = 37
				* result
				+ (getUsaTaxFlag() == null ? 0 : this.getUsaTaxFlag()
						.hashCode());
		result = 37
				* result
				+ (getIsDividendCust() == null ? 0 : this.getIsDividendCust()
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
				+ (getIsUptoViplevel() == null ? 0 : this.getIsUptoViplevel()
						.hashCode());
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