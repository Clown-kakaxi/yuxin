package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_ORG_BUSIINFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ORG_BUSIINFO")
public class AcrmFCiOrgBusiinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="BUDGET_ADMIN_TYPE")
	private String budgetAdminType;

	@Column(name="BUSINESS_PLAN")
	private String businessPlan;

	@Column(name="CUST_BUSI_AREA")
	private BigDecimal custBusiArea;

	@Column(name="CUST_OFFICE_AREA")
	private BigDecimal custOfficeArea;

	@Column(name="LAST_INCO")
	private BigDecimal lastInco;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="MAIN_CUST")
	private String mainCust;

	@Column(name="MAIN_MATERIAL")
	private String mainMaterial;

	@Column(name="MAIN_MATERIAL_AREA")
	private String mainMaterialArea;

	@Column(name="MAIN_PRODUCT")
	private String mainProduct;

	@Column(name="MAIN_SALE")
	private String mainSale;

	@Column(name="MAIN_SALE_MARKET")
	private String mainSaleMarket;

	@Column(name="MAIN_SERVICE")
	private String mainService;

	@Column(name="MAIN_SUPPLIER")
	private String mainSupplier;

	@Column(name="MANAGE_APTITUDE")
	private String manageAptitude;

	@Column(name="MANAGE_FORM")
	private String manageForm;

	@Column(name="MANAGE_STAT")
	private String manageStat;

	@Column(name="SALE_AMT")
	private BigDecimal saleAmt;

	@Column(name="SALE_NUM")
	private BigDecimal saleNum;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="WORK_FIELD_AREA")
	private BigDecimal workFieldArea;

	@Column(name="WORK_FIELD_OWNERSHIP")
	private String workFieldOwnership;

	@Column(name="YEAR_INCO")
	private BigDecimal yearInco;

	public AcrmFCiOrgBusiinfo() {
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBudgetAdminType() {
		return this.budgetAdminType;
	}

	public void setBudgetAdminType(String budgetAdminType) {
		this.budgetAdminType = budgetAdminType;
	}

	public String getBusinessPlan() {
		return this.businessPlan;
	}

	public void setBusinessPlan(String businessPlan) {
		this.businessPlan = businessPlan;
	}

	public BigDecimal getCustBusiArea() {
		return this.custBusiArea;
	}

	public void setCustBusiArea(BigDecimal custBusiArea) {
		this.custBusiArea = custBusiArea;
	}

	public BigDecimal getCustOfficeArea() {
		return this.custOfficeArea;
	}

	public void setCustOfficeArea(BigDecimal custOfficeArea) {
		this.custOfficeArea = custOfficeArea;
	}

	public BigDecimal getLastInco() {
		return this.lastInco;
	}

	public void setLastInco(BigDecimal lastInco) {
		this.lastInco = lastInco;
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

	public String getMainCust() {
		return this.mainCust;
	}

	public void setMainCust(String mainCust) {
		this.mainCust = mainCust;
	}

	public String getMainMaterial() {
		return this.mainMaterial;
	}

	public void setMainMaterial(String mainMaterial) {
		this.mainMaterial = mainMaterial;
	}

	public String getMainMaterialArea() {
		return this.mainMaterialArea;
	}

	public void setMainMaterialArea(String mainMaterialArea) {
		this.mainMaterialArea = mainMaterialArea;
	}

	public String getMainProduct() {
		return this.mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getMainSale() {
		return this.mainSale;
	}

	public void setMainSale(String mainSale) {
		this.mainSale = mainSale;
	}

	public String getMainSaleMarket() {
		return this.mainSaleMarket;
	}

	public void setMainSaleMarket(String mainSaleMarket) {
		this.mainSaleMarket = mainSaleMarket;
	}

	public String getMainService() {
		return this.mainService;
	}

	public void setMainService(String mainService) {
		this.mainService = mainService;
	}

	public String getMainSupplier() {
		return this.mainSupplier;
	}

	public void setMainSupplier(String mainSupplier) {
		this.mainSupplier = mainSupplier;
	}

	public String getManageAptitude() {
		return this.manageAptitude;
	}

	public void setManageAptitude(String manageAptitude) {
		this.manageAptitude = manageAptitude;
	}

	public String getManageForm() {
		return this.manageForm;
	}

	public void setManageForm(String manageForm) {
		this.manageForm = manageForm;
	}

	public String getManageStat() {
		return this.manageStat;
	}

	public void setManageStat(String manageStat) {
		this.manageStat = manageStat;
	}

	public BigDecimal getSaleAmt() {
		return this.saleAmt;
	}

	public void setSaleAmt(BigDecimal saleAmt) {
		this.saleAmt = saleAmt;
	}

	public BigDecimal getSaleNum() {
		return this.saleNum;
	}

	public void setSaleNum(BigDecimal saleNum) {
		this.saleNum = saleNum;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public BigDecimal getWorkFieldArea() {
		return this.workFieldArea;
	}

	public void setWorkFieldArea(BigDecimal workFieldArea) {
		this.workFieldArea = workFieldArea;
	}

	public String getWorkFieldOwnership() {
		return this.workFieldOwnership;
	}

	public void setWorkFieldOwnership(String workFieldOwnership) {
		this.workFieldOwnership = workFieldOwnership;
	}

	public BigDecimal getYearInco() {
		return this.yearInco;
	}

	public void setYearInco(BigDecimal yearInco) {
		this.yearInco = yearInco;
	}

}