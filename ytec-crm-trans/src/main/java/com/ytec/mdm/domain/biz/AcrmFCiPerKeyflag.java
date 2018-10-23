package com.ytec.mdm.domain.biz;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ACRM_F_CI_PER_KEYFLAG database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_PER_KEYFLAG")
@NamedQuery(name="AcrmFCiPerKeyflag.findAll", query="SELECT a FROM AcrmFCiPerKeyflag a")
public class AcrmFCiPerKeyflag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREDIT_AMOUNT")
	private BigDecimal creditAmount;

	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Temporal(TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Column(name="FOREIGN_HABITATIO_FLAG")
	private String foreignHabitatioFlag;

	@Column(name="FOREIGN_PASSPORT_FLAG")
	private String foreignPassportFlag;

	@Column(name="HAS_BAD_LOAN")
	private String hasBadLoan;

	@Column(name="HAS_BIRTH_INSURE")
	private String hasBirthInsure;

	@Column(name="HAS_CAR")
	private String hasCar;

	@Column(name="HAS_CREDIT_INFO")
	private String hasCreditInfo;

	@Column(name="HAS_ENDO_INSURE")
	private String hasEndoInsure;

	@Column(name="HAS_HOUSE_FUND")
	private String hasHouseFund;

	@Column(name="HAS_IDLE_INSURE")
	private String hasIdleInsure;

	@Column(name="HAS_INJURY_INSURE")
	private String hasInjuryInsure;

	@Column(name="HAS_MEDI_INSURE")
	private String hasMediInsure;

	@Column(name="HAS_OTHER_BANK_LOAN")
	private String hasOtherBankLoan;

	@Column(name="HAS_PHOTO")
	private String hasPhoto;

	@Column(name="HAS_THIS_BANK_LOAN")
	private String hasThisBankLoan;

	@Column(name="IS_CREDIBLE_PEASANT")
	private String isCrediblePeasant;

	@Column(name="IS_CREDIT_CUST")
	private String isCreditCust;

	@Column(name="IS_DEBIT_CARD")
	private String isDebitCard;

	@Column(name="IS_DIVIDEND_CUST")
	private String isDividendCust;

	@Column(name="IS_EBANK_SIGN_CUST")
	private String isEbankSignCust;

	@Column(name="IS_EMPLOYEE")
	private String isEmployee;

	@Column(name="IS_FAX_TRANS_CUST")
	private String isFaxTransCust;

	@Column(name="IS_GUARANTEE")
	private String isGuarantee;

	@Column(name="IS_IMPORTANT_CUST")
	private String isImportantCust;

	@Column(name="IS_MERCHANT")
	private String isMerchant;

	@Column(name="IS_NATIVE")
	private String isNative;

	@Column(name="IS_ON_JOB_WORKER")
	private String isOnJobWorker;

	@Column(name="IS_PAYROLL_CUST")
	private String isPayrollCust;

	@Column(name="IS_PEASANT")
	private String isPeasant;

	@Column(name="IS_PRIV_BANK_CUST")
	private String isPrivBankCust;

	@Column(name="IS_SECRET_CUST")
	private String isSecretCust;

	@Column(name="IS_SEND_ECOMSTAT_FLAG")
	private String isSendEcomstatFlag;

	@Column(name="IS_SHAREHOLDER")
	private String isShareholder;

	@Column(name="IS_UPTO_VIPLEVEL")
	private String isUptoViplevel;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="USA_TAX_FLAG")
	private String usaTaxFlag;

	public AcrmFCiPerKeyflag() {
	}

	public BigDecimal getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public String getForeignHabitatioFlag() {
		return this.foreignHabitatioFlag;
	}

	public void setForeignHabitatioFlag(String foreignHabitatioFlag) {
		this.foreignHabitatioFlag = foreignHabitatioFlag;
	}

	public String getForeignPassportFlag() {
		return this.foreignPassportFlag;
	}

	public void setForeignPassportFlag(String foreignPassportFlag) {
		this.foreignPassportFlag = foreignPassportFlag;
	}

	public String getHasBadLoan() {
		return this.hasBadLoan;
	}

	public void setHasBadLoan(String hasBadLoan) {
		this.hasBadLoan = hasBadLoan;
	}

	public String getHasBirthInsure() {
		return this.hasBirthInsure;
	}

	public void setHasBirthInsure(String hasBirthInsure) {
		this.hasBirthInsure = hasBirthInsure;
	}

	public String getHasCar() {
		return this.hasCar;
	}

	public void setHasCar(String hasCar) {
		this.hasCar = hasCar;
	}

	public String getHasCreditInfo() {
		return this.hasCreditInfo;
	}

	public void setHasCreditInfo(String hasCreditInfo) {
		this.hasCreditInfo = hasCreditInfo;
	}

	public String getHasEndoInsure() {
		return this.hasEndoInsure;
	}

	public void setHasEndoInsure(String hasEndoInsure) {
		this.hasEndoInsure = hasEndoInsure;
	}

	public String getHasHouseFund() {
		return this.hasHouseFund;
	}

	public void setHasHouseFund(String hasHouseFund) {
		this.hasHouseFund = hasHouseFund;
	}

	public String getHasIdleInsure() {
		return this.hasIdleInsure;
	}

	public void setHasIdleInsure(String hasIdleInsure) {
		this.hasIdleInsure = hasIdleInsure;
	}

	public String getHasInjuryInsure() {
		return this.hasInjuryInsure;
	}

	public void setHasInjuryInsure(String hasInjuryInsure) {
		this.hasInjuryInsure = hasInjuryInsure;
	}

	public String getHasMediInsure() {
		return this.hasMediInsure;
	}

	public void setHasMediInsure(String hasMediInsure) {
		this.hasMediInsure = hasMediInsure;
	}

	public String getHasOtherBankLoan() {
		return this.hasOtherBankLoan;
	}

	public void setHasOtherBankLoan(String hasOtherBankLoan) {
		this.hasOtherBankLoan = hasOtherBankLoan;
	}

	public String getHasPhoto() {
		return this.hasPhoto;
	}

	public void setHasPhoto(String hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	public String getHasThisBankLoan() {
		return this.hasThisBankLoan;
	}

	public void setHasThisBankLoan(String hasThisBankLoan) {
		this.hasThisBankLoan = hasThisBankLoan;
	}

	public String getIsCrediblePeasant() {
		return this.isCrediblePeasant;
	}

	public void setIsCrediblePeasant(String isCrediblePeasant) {
		this.isCrediblePeasant = isCrediblePeasant;
	}

	public String getIsCreditCust() {
		return this.isCreditCust;
	}

	public void setIsCreditCust(String isCreditCust) {
		this.isCreditCust = isCreditCust;
	}

	public String getIsDebitCard() {
		return this.isDebitCard;
	}

	public void setIsDebitCard(String isDebitCard) {
		this.isDebitCard = isDebitCard;
	}

	public String getIsDividendCust() {
		return this.isDividendCust;
	}

	public void setIsDividendCust(String isDividendCust) {
		this.isDividendCust = isDividendCust;
	}

	public String getIsEbankSignCust() {
		return this.isEbankSignCust;
	}

	public void setIsEbankSignCust(String isEbankSignCust) {
		this.isEbankSignCust = isEbankSignCust;
	}

	public String getIsEmployee() {
		return this.isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}

	public String getIsFaxTransCust() {
		return this.isFaxTransCust;
	}

	public void setIsFaxTransCust(String isFaxTransCust) {
		this.isFaxTransCust = isFaxTransCust;
	}

	public String getIsGuarantee() {
		return this.isGuarantee;
	}

	public void setIsGuarantee(String isGuarantee) {
		this.isGuarantee = isGuarantee;
	}

	public String getIsImportantCust() {
		return this.isImportantCust;
	}

	public void setIsImportantCust(String isImportantCust) {
		this.isImportantCust = isImportantCust;
	}

	public String getIsMerchant() {
		return this.isMerchant;
	}

	public void setIsMerchant(String isMerchant) {
		this.isMerchant = isMerchant;
	}

	public String getIsNative() {
		return this.isNative;
	}

	public void setIsNative(String isNative) {
		this.isNative = isNative;
	}

	public String getIsOnJobWorker() {
		return this.isOnJobWorker;
	}

	public void setIsOnJobWorker(String isOnJobWorker) {
		this.isOnJobWorker = isOnJobWorker;
	}

	public String getIsPayrollCust() {
		return this.isPayrollCust;
	}

	public void setIsPayrollCust(String isPayrollCust) {
		this.isPayrollCust = isPayrollCust;
	}

	public String getIsPeasant() {
		return this.isPeasant;
	}

	public void setIsPeasant(String isPeasant) {
		this.isPeasant = isPeasant;
	}

	public String getIsPrivBankCust() {
		return this.isPrivBankCust;
	}

	public void setIsPrivBankCust(String isPrivBankCust) {
		this.isPrivBankCust = isPrivBankCust;
	}

	public String getIsSecretCust() {
		return this.isSecretCust;
	}

	public void setIsSecretCust(String isSecretCust) {
		this.isSecretCust = isSecretCust;
	}

	public String getIsSendEcomstatFlag() {
		return this.isSendEcomstatFlag;
	}

	public void setIsSendEcomstatFlag(String isSendEcomstatFlag) {
		this.isSendEcomstatFlag = isSendEcomstatFlag;
	}

	public String getIsShareholder() {
		return this.isShareholder;
	}

	public void setIsShareholder(String isShareholder) {
		this.isShareholder = isShareholder;
	}

	public String getIsUptoViplevel() {
		return this.isUptoViplevel;
	}

	public void setIsUptoViplevel(String isUptoViplevel) {
		this.isUptoViplevel = isUptoViplevel;
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

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getUsaTaxFlag() {
		return this.usaTaxFlag;
	}

	public void setUsaTaxFlag(String usaTaxFlag) {
		this.usaTaxFlag = usaTaxFlag;
	}

}