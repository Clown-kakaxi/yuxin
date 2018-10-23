package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiOrgBusiinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_BUSIINFO")
public class MCiOrgBusiinfo implements java.io.Serializable {

	// Fields

	private String custId;
	private String manageForm;
	private String manageAptitude;
	private String manageStat;
	private String mainMaterial;
	private String mainProduct;
	private String mainService;
	private String mainCust;
	private String mainMaterialArea;
	private String mainSaleMarket;
	private String mainSupplier;
	private String mainSale;
	private Double lastInco;
	private Double yearInco;
	private BigDecimal saleNum;
	private Double saleAmt;
	private String budgetAdminType;
	private String businessPlan;
	private String workFieldOwnership;
	private Double workFieldArea;
	private Double custBusiArea;
	private Double custOfficeArea;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;
	//add by liuming 20170629
	private String saleCcy;

	// Constructors

	/** default constructor */
	public MCiOrgBusiinfo() {
	}

	/** minimal constructor */
	public MCiOrgBusiinfo(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiOrgBusiinfo(String custId, String manageForm,
			String manageAptitude, String manageStat, String mainMaterial,
			String mainProduct, String mainService, String mainCust,
			String mainMaterialArea, String mainSaleMarket,
			String mainSupplier, String mainSale, Double lastInco,
			Double yearInco, BigDecimal saleNum, Double saleAmt,
			String budgetAdminType, String businessPlan,
			String workFieldOwnership, Double workFieldArea,
			Double custBusiArea, Double custOfficeArea, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo
			//add by liuming 20170629
			,String saleCcy) {
		this.custId = custId;
		this.manageForm = manageForm;
		this.manageAptitude = manageAptitude;
		this.manageStat = manageStat;
		this.mainMaterial = mainMaterial;
		this.mainProduct = mainProduct;
		this.mainService = mainService;
		this.mainCust = mainCust;
		this.mainMaterialArea = mainMaterialArea;
		this.mainSaleMarket = mainSaleMarket;
		this.mainSupplier = mainSupplier;
		this.mainSale = mainSale;
		this.lastInco = lastInco;
		this.yearInco = yearInco;
		this.saleNum = saleNum;
		this.saleAmt = saleAmt;
		this.budgetAdminType = budgetAdminType;
		this.businessPlan = businessPlan;
		this.workFieldOwnership = workFieldOwnership;
		this.workFieldArea = workFieldArea;
		this.custBusiArea = custBusiArea;
		this.custOfficeArea = custOfficeArea;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
		//add by liuming 20170629
		this.saleCcy = saleCcy;
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

	@Column(name = "MANAGE_FORM", length = 20)
	public String getManageForm() {
		return this.manageForm;
	}

	public void setManageForm(String manageForm) {
		this.manageForm = manageForm;
	}

	@Column(name = "MANAGE_APTITUDE", length = 20)
	public String getManageAptitude() {
		return this.manageAptitude;
	}

	public void setManageAptitude(String manageAptitude) {
		this.manageAptitude = manageAptitude;
	}

	@Column(name = "MANAGE_STAT", length = 20)
	public String getManageStat() {
		return this.manageStat;
	}

	public void setManageStat(String manageStat) {
		this.manageStat = manageStat;
	}

	@Column(name = "MAIN_MATERIAL", length = 20)
	public String getMainMaterial() {
		return this.mainMaterial;
	}

	public void setMainMaterial(String mainMaterial) {
		this.mainMaterial = mainMaterial;
	}

	@Column(name = "MAIN_PRODUCT", length = 20)
	public String getMainProduct() {
		return this.mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	@Column(name = "MAIN_SERVICE", length = 20)
	public String getMainService() {
		return this.mainService;
	}

	public void setMainService(String mainService) {
		this.mainService = mainService;
	}

	@Column(name = "MAIN_CUST", length = 20)
	public String getMainCust() {
		return this.mainCust;
	}

	public void setMainCust(String mainCust) {
		this.mainCust = mainCust;
	}

	@Column(name = "MAIN_MATERIAL_AREA")
	public String getMainMaterialArea() {
		return this.mainMaterialArea;
	}

	public void setMainMaterialArea(String mainMaterialArea) {
		this.mainMaterialArea = mainMaterialArea;
	}

	@Column(name = "MAIN_SALE_MARKET")
	public String getMainSaleMarket() {
		return this.mainSaleMarket;
	}

	public void setMainSaleMarket(String mainSaleMarket) {
		this.mainSaleMarket = mainSaleMarket;
	}

	@Column(name = "MAIN_SUPPLIER")
	public String getMainSupplier() {
		return this.mainSupplier;
	}

	public void setMainSupplier(String mainSupplier) {
		this.mainSupplier = mainSupplier;
	}

	@Column(name = "MAIN_SALE")
	public String getMainSale() {
		return this.mainSale;
	}

	public void setMainSale(String mainSale) {
		this.mainSale = mainSale;
	}

	@Column(name = "LAST_INCO", precision = 17)
	public Double getLastInco() {
		return this.lastInco;
	}

	public void setLastInco(Double lastInco) {
		this.lastInco = lastInco;
	}

	@Column(name = "YEAR_INCO", precision = 17)
	public Double getYearInco() {
		return this.yearInco;
	}

	public void setYearInco(Double yearInco) {
		this.yearInco = yearInco;
	}

	@Column(name = "SALE_NUM", scale = 0)
	public BigDecimal getSaleNum() {
		return this.saleNum;
	}

	public void setSaleNum(BigDecimal saleNum) {
		this.saleNum = saleNum;
	}

	@Column(name = "SALE_AMT", precision = 17)
	public Double getSaleAmt() {
		return this.saleAmt;
	}

	public void setSaleAmt(Double saleAmt) {
		this.saleAmt = saleAmt;
	}

	@Column(name = "BUDGET_ADMIN_TYPE", length = 20)
	public String getBudgetAdminType() {
		return this.budgetAdminType;
	}

	public void setBudgetAdminType(String budgetAdminType) {
		this.budgetAdminType = budgetAdminType;
	}

	@Column(name = "BUSINESS_PLAN")
	public String getBusinessPlan() {
		return this.businessPlan;
	}

	public void setBusinessPlan(String businessPlan) {
		this.businessPlan = businessPlan;
	}

	@Column(name = "WORK_FIELD_OWNERSHIP", length = 20)
	public String getWorkFieldOwnership() {
		return this.workFieldOwnership;
	}

	public void setWorkFieldOwnership(String workFieldOwnership) {
		this.workFieldOwnership = workFieldOwnership;
	}

	@Column(name = "WORK_FIELD_AREA", precision = 10)
	public Double getWorkFieldArea() {
		return this.workFieldArea;
	}

	public void setWorkFieldArea(Double workFieldArea) {
		this.workFieldArea = workFieldArea;
	}

	@Column(name = "CUST_BUSI_AREA", precision = 10)
	public Double getCustBusiArea() {
		return this.custBusiArea;
	}

	public void setCustBusiArea(Double custBusiArea) {
		this.custBusiArea = custBusiArea;
	}

	@Column(name = "CUST_OFFICE_AREA", precision = 10)
	public Double getCustOfficeArea() {
		return this.custOfficeArea;
	}

	public void setCustOfficeArea(Double custOfficeArea) {
		this.custOfficeArea = custOfficeArea;
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
	
	//add by liuming 20170629
	@Column(name = "SALE_CCY", length = 20)
	public String getSaleCcy() {
		return saleCcy;
	}

	public void setSaleCcy(String saleCcy) {
		this.saleCcy = saleCcy;
	}

}