package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiOrgBusiinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgBusiinfoId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiOrgBusiinfoId() {
	}

	/** minimal constructor */
	public HMCiOrgBusiinfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgBusiinfoId(String custId, String manageForm,
			String manageAptitude, String manageStat, String mainMaterial,
			String mainProduct, String mainService, String mainCust,
			String mainMaterialArea, String mainSaleMarket,
			String mainSupplier, String mainSale, Double lastInco,
			Double yearInco, BigDecimal saleNum, Double saleAmt,
			String budgetAdminType, String businessPlan,
			String workFieldOwnership, Double workFieldArea,
			Double custBusiArea, Double custOfficeArea, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
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
		if (!(other instanceof HMCiOrgBusiinfoId))
			return false;
		HMCiOrgBusiinfoId castOther = (HMCiOrgBusiinfoId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getManageForm() == castOther.getManageForm()) || (this
						.getManageForm() != null
						&& castOther.getManageForm() != null && this
						.getManageForm().equals(castOther.getManageForm())))
				&& ((this.getManageAptitude() == castOther.getManageAptitude()) || (this
						.getManageAptitude() != null
						&& castOther.getManageAptitude() != null && this
						.getManageAptitude().equals(
								castOther.getManageAptitude())))
				&& ((this.getManageStat() == castOther.getManageStat()) || (this
						.getManageStat() != null
						&& castOther.getManageStat() != null && this
						.getManageStat().equals(castOther.getManageStat())))
				&& ((this.getMainMaterial() == castOther.getMainMaterial()) || (this
						.getMainMaterial() != null
						&& castOther.getMainMaterial() != null && this
						.getMainMaterial().equals(castOther.getMainMaterial())))
				&& ((this.getMainProduct() == castOther.getMainProduct()) || (this
						.getMainProduct() != null
						&& castOther.getMainProduct() != null && this
						.getMainProduct().equals(castOther.getMainProduct())))
				&& ((this.getMainService() == castOther.getMainService()) || (this
						.getMainService() != null
						&& castOther.getMainService() != null && this
						.getMainService().equals(castOther.getMainService())))
				&& ((this.getMainCust() == castOther.getMainCust()) || (this
						.getMainCust() != null
						&& castOther.getMainCust() != null && this
						.getMainCust().equals(castOther.getMainCust())))
				&& ((this.getMainMaterialArea() == castOther
						.getMainMaterialArea()) || (this.getMainMaterialArea() != null
						&& castOther.getMainMaterialArea() != null && this
						.getMainMaterialArea().equals(
								castOther.getMainMaterialArea())))
				&& ((this.getMainSaleMarket() == castOther.getMainSaleMarket()) || (this
						.getMainSaleMarket() != null
						&& castOther.getMainSaleMarket() != null && this
						.getMainSaleMarket().equals(
								castOther.getMainSaleMarket())))
				&& ((this.getMainSupplier() == castOther.getMainSupplier()) || (this
						.getMainSupplier() != null
						&& castOther.getMainSupplier() != null && this
						.getMainSupplier().equals(castOther.getMainSupplier())))
				&& ((this.getMainSale() == castOther.getMainSale()) || (this
						.getMainSale() != null
						&& castOther.getMainSale() != null && this
						.getMainSale().equals(castOther.getMainSale())))
				&& ((this.getLastInco() == castOther.getLastInco()) || (this
						.getLastInco() != null
						&& castOther.getLastInco() != null && this
						.getLastInco().equals(castOther.getLastInco())))
				&& ((this.getYearInco() == castOther.getYearInco()) || (this
						.getYearInco() != null
						&& castOther.getYearInco() != null && this
						.getYearInco().equals(castOther.getYearInco())))
				&& ((this.getSaleNum() == castOther.getSaleNum()) || (this
						.getSaleNum() != null
						&& castOther.getSaleNum() != null && this.getSaleNum()
						.equals(castOther.getSaleNum())))
				&& ((this.getSaleAmt() == castOther.getSaleAmt()) || (this
						.getSaleAmt() != null
						&& castOther.getSaleAmt() != null && this.getSaleAmt()
						.equals(castOther.getSaleAmt())))
				&& ((this.getBudgetAdminType() == castOther
						.getBudgetAdminType()) || (this.getBudgetAdminType() != null
						&& castOther.getBudgetAdminType() != null && this
						.getBudgetAdminType().equals(
								castOther.getBudgetAdminType())))
				&& ((this.getBusinessPlan() == castOther.getBusinessPlan()) || (this
						.getBusinessPlan() != null
						&& castOther.getBusinessPlan() != null && this
						.getBusinessPlan().equals(castOther.getBusinessPlan())))
				&& ((this.getWorkFieldOwnership() == castOther
						.getWorkFieldOwnership()) || (this
						.getWorkFieldOwnership() != null
						&& castOther.getWorkFieldOwnership() != null && this
						.getWorkFieldOwnership().equals(
								castOther.getWorkFieldOwnership())))
				&& ((this.getWorkFieldArea() == castOther.getWorkFieldArea()) || (this
						.getWorkFieldArea() != null
						&& castOther.getWorkFieldArea() != null && this
						.getWorkFieldArea()
						.equals(castOther.getWorkFieldArea())))
				&& ((this.getCustBusiArea() == castOther.getCustBusiArea()) || (this
						.getCustBusiArea() != null
						&& castOther.getCustBusiArea() != null && this
						.getCustBusiArea().equals(castOther.getCustBusiArea())))
				&& ((this.getCustOfficeArea() == castOther.getCustOfficeArea()) || (this
						.getCustOfficeArea() != null
						&& castOther.getCustOfficeArea() != null && this
						.getCustOfficeArea().equals(
								castOther.getCustOfficeArea())))
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
				+ (getManageForm() == null ? 0 : this.getManageForm()
						.hashCode());
		result = 37
				* result
				+ (getManageAptitude() == null ? 0 : this.getManageAptitude()
						.hashCode());
		result = 37
				* result
				+ (getManageStat() == null ? 0 : this.getManageStat()
						.hashCode());
		result = 37
				* result
				+ (getMainMaterial() == null ? 0 : this.getMainMaterial()
						.hashCode());
		result = 37
				* result
				+ (getMainProduct() == null ? 0 : this.getMainProduct()
						.hashCode());
		result = 37
				* result
				+ (getMainService() == null ? 0 : this.getMainService()
						.hashCode());
		result = 37 * result
				+ (getMainCust() == null ? 0 : this.getMainCust().hashCode());
		result = 37
				* result
				+ (getMainMaterialArea() == null ? 0 : this
						.getMainMaterialArea().hashCode());
		result = 37
				* result
				+ (getMainSaleMarket() == null ? 0 : this.getMainSaleMarket()
						.hashCode());
		result = 37
				* result
				+ (getMainSupplier() == null ? 0 : this.getMainSupplier()
						.hashCode());
		result = 37 * result
				+ (getMainSale() == null ? 0 : this.getMainSale().hashCode());
		result = 37 * result
				+ (getLastInco() == null ? 0 : this.getLastInco().hashCode());
		result = 37 * result
				+ (getYearInco() == null ? 0 : this.getYearInco().hashCode());
		result = 37 * result
				+ (getSaleNum() == null ? 0 : this.getSaleNum().hashCode());
		result = 37 * result
				+ (getSaleAmt() == null ? 0 : this.getSaleAmt().hashCode());
		result = 37
				* result
				+ (getBudgetAdminType() == null ? 0 : this.getBudgetAdminType()
						.hashCode());
		result = 37
				* result
				+ (getBusinessPlan() == null ? 0 : this.getBusinessPlan()
						.hashCode());
		result = 37
				* result
				+ (getWorkFieldOwnership() == null ? 0 : this
						.getWorkFieldOwnership().hashCode());
		result = 37
				* result
				+ (getWorkFieldArea() == null ? 0 : this.getWorkFieldArea()
						.hashCode());
		result = 37
				* result
				+ (getCustBusiArea() == null ? 0 : this.getCustBusiArea()
						.hashCode());
		result = 37
				* result
				+ (getCustOfficeArea() == null ? 0 : this.getCustOfficeArea()
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