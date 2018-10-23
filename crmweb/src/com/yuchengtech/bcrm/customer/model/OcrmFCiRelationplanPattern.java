package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the OCRM_F_CI_RELATIONPLAN_PATTERN database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_RELATIONPLAN_PATTERN")
public class OcrmFCiRelationplanPattern implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_RELATIONPLAN_PATTERN_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_RELATIONPLAN_PATTERN_ID_GENERATOR")
	private String id;

	@Column(name="ACCOUNTS_PAYABLE_CURRENCE")
	private String accountsPayableCurrence;

	@Column(name="ACCOUNTS_PAYABLE_CYCLE")
	private String accountsPayableCycle;

	@Column(name="CB_LEVLE")
	private String cbLevle;

	@Column(name="CORP_CULTURE")
	private String corpCulture;

	@Column(name="CORP_PROFILE")
	private String corpProfile;

	@Column(name="CREDIT_LEVEL")
	private String creditLevel;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="EXPORT_VOLUME")
	private String exportVolume;

	@Column(name="IMPORT_VOLUME")
	private String importVolume;

	@Column(name="LINE_OF_CREDIT")
	private String lineOfCredit;

	@Column(name="MAIN_MATERIAL")
	private String mainMaterial;

	@Column(name="MATERIAL_AMMOUNT")
	private String materialAmmount;

	@Temporal(TemporalType.DATE)
	@Column(name="NEXT_ANNUAL_TIME")
	private Date nextAnnualTime;

	@Column(name="OUTSTANDING_LOAN")
	private String outstandingLoan;

	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="PURCHASE_AREA")
	private String purchaseArea;

	@Column(name="PURCHASE_TYPE_FIR")
	private String purchaseTypeFir;

	@Column(name="PURCHASE_TYPE_FIR_SCALE")
	private String purchaseTypeFirScale;

	@Column(name="PURCHASE_TYPE_SEC")
	private String purchaseTypeSec;

	@Column(name="PURCHASE_TYPE_SEC_SCALE")
	private String purchaseTypeSecScale;

	@Column(name="PURCHASE_TYPE_THIR")
	private String purchaseTypeThir;

	@Column(name="PURCHASE_TYPE_THIR_SCALE")
	private String purchaseTypeThirScale;

	@Column(name="RECEIVABLES_CURRENCE")
	private String receivablesCurrence;

	@Column(name="RECEIVABLES_CYCLE")
	private String receivablesCycle;

	@Column(name="SALE_AREA")
	private String saleArea;

	@Column(name="SALE_ESTIMATE")
	private String saleEstimate;

	@Column(name="SALE_RANGE_ESTIMATE")
	private String saleRangeEstimate;

	@Column(name="SETTLE_TYPE_FIR")
	private String settleTypeFir;

	@Column(name="SETTLE_TYPE_FIR_SCALE")
	private String settleTypeFirScale;

	@Column(name="SETTLE_TYPE_SEC")
	private String settleTypeSec;

	@Column(name="SETTLE_TYPE_SEC_SCALE")
	private String settleTypeSecScale;

	@Column(name="SETTLE_TYPE_THIR")
	private String settleTypeThir;

	@Column(name="SETTLE_TYPE_THIR_SCALE")
	private String settleTypeThirScale;

	public OcrmFCiRelationplanPattern() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountsPayableCurrence() {
		return this.accountsPayableCurrence;
	}

	public void setAccountsPayableCurrence(String accountsPayableCurrence) {
		this.accountsPayableCurrence = accountsPayableCurrence;
	}

	public String getAccountsPayableCycle() {
		return this.accountsPayableCycle;
	}

	public void setAccountsPayableCycle(String accountsPayableCycle) {
		this.accountsPayableCycle = accountsPayableCycle;
	}

	public String getCbLevle() {
		return this.cbLevle;
	}

	public void setCbLevle(String cbLevle) {
		this.cbLevle = cbLevle;
	}

	public String getCorpCulture() {
		return this.corpCulture;
	}

	public void setCorpCulture(String corpCulture) {
		this.corpCulture = corpCulture;
	}

	public String getCorpProfile() {
		return this.corpProfile;
	}

	public void setCorpProfile(String corpProfile) {
		this.corpProfile = corpProfile;
	}

	public String getCreditLevel() {
		return this.creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getExportVolume() {
		return this.exportVolume;
	}

	public void setExportVolume(String exportVolume) {
		this.exportVolume = exportVolume;
	}

	public String getImportVolume() {
		return this.importVolume;
	}

	public void setImportVolume(String importVolume) {
		this.importVolume = importVolume;
	}

	public String getLineOfCredit() {
		return this.lineOfCredit;
	}

	public void setLineOfCredit(String lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	public String getMainMaterial() {
		return this.mainMaterial;
	}

	public void setMainMaterial(String mainMaterial) {
		this.mainMaterial = mainMaterial;
	}

	public String getMaterialAmmount() {
		return this.materialAmmount;
	}

	public void setMaterialAmmount(String materialAmmount) {
		this.materialAmmount = materialAmmount;
	}

	public Date getNextAnnualTime() {
		return this.nextAnnualTime;
	}

	public void setNextAnnualTime(Date nextAnnualTime) {
		this.nextAnnualTime = nextAnnualTime;
	}

	public String getOutstandingLoan() {
		return this.outstandingLoan;
	}

	public void setOutstandingLoan(String outstandingLoan) {
		this.outstandingLoan = outstandingLoan;
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPurchaseArea() {
		return this.purchaseArea;
	}

	public void setPurchaseArea(String purchaseArea) {
		this.purchaseArea = purchaseArea;
	}

	public String getPurchaseTypeFir() {
		return this.purchaseTypeFir;
	}

	public void setPurchaseTypeFir(String purchaseTypeFir) {
		this.purchaseTypeFir = purchaseTypeFir;
	}

	public String getPurchaseTypeFirScale() {
		return this.purchaseTypeFirScale;
	}

	public void setPurchaseTypeFirScale(String purchaseTypeFirScale) {
		this.purchaseTypeFirScale = purchaseTypeFirScale;
	}

	public String getPurchaseTypeSec() {
		return this.purchaseTypeSec;
	}

	public void setPurchaseTypeSec(String purchaseTypeSec) {
		this.purchaseTypeSec = purchaseTypeSec;
	}

	public String getPurchaseTypeSecScale() {
		return this.purchaseTypeSecScale;
	}

	public void setPurchaseTypeSecScale(String purchaseTypeSecScale) {
		this.purchaseTypeSecScale = purchaseTypeSecScale;
	}

	public String getPurchaseTypeThir() {
		return this.purchaseTypeThir;
	}

	public void setPurchaseTypeThir(String purchaseTypeThir) {
		this.purchaseTypeThir = purchaseTypeThir;
	}

	public String getPurchaseTypeThirScale() {
		return this.purchaseTypeThirScale;
	}

	public void setPurchaseTypeThirScale(String purchaseTypeThirScale) {
		this.purchaseTypeThirScale = purchaseTypeThirScale;
	}

	public String getReceivablesCurrence() {
		return this.receivablesCurrence;
	}

	public void setReceivablesCurrence(String receivablesCurrence) {
		this.receivablesCurrence = receivablesCurrence;
	}

	public String getReceivablesCycle() {
		return this.receivablesCycle;
	}

	public void setReceivablesCycle(String receivablesCycle) {
		this.receivablesCycle = receivablesCycle;
	}

	public String getSaleArea() {
		return this.saleArea;
	}

	public void setSaleArea(String saleArea) {
		this.saleArea = saleArea;
	}

	public String getSaleEstimate() {
		return this.saleEstimate;
	}

	public void setSaleEstimate(String saleEstimate) {
		this.saleEstimate = saleEstimate;
	}

	public String getSaleRangeEstimate() {
		return this.saleRangeEstimate;
	}

	public void setSaleRangeEstimate(String saleRangeEstimate) {
		this.saleRangeEstimate = saleRangeEstimate;
	}

	public String getSettleTypeFir() {
		return this.settleTypeFir;
	}

	public void setSettleTypeFir(String settleTypeFir) {
		this.settleTypeFir = settleTypeFir;
	}

	public String getSettleTypeFirScale() {
		return this.settleTypeFirScale;
	}

	public void setSettleTypeFirScale(String settleTypeFirScale) {
		this.settleTypeFirScale = settleTypeFirScale;
	}

	public String getSettleTypeSec() {
		return this.settleTypeSec;
	}

	public void setSettleTypeSec(String settleTypeSec) {
		this.settleTypeSec = settleTypeSec;
	}

	public String getSettleTypeSecScale() {
		return this.settleTypeSecScale;
	}

	public void setSettleTypeSecScale(String settleTypeSecScale) {
		this.settleTypeSecScale = settleTypeSecScale;
	}

	public String getSettleTypeThir() {
		return this.settleTypeThir;
	}

	public void setSettleTypeThir(String settleTypeThir) {
		this.settleTypeThir = settleTypeThir;
	}

	public String getSettleTypeThirScale() {
		return this.settleTypeThirScale;
	}

	public void setSettleTypeThirScale(String settleTypeThirScale) {
		this.settleTypeThirScale = settleTypeThirScale;
	}

}