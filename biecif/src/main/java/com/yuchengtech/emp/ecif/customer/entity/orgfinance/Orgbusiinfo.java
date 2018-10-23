package com.yuchengtech.emp.ecif.customer.entity.orgfinance;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgfinance.Orgbusiinfo")
@Table(name="M_CI_ORG_BUSIINFO")
public class Orgbusiinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ORG_BUSI_INFO_ID", unique=true, nullable=false)
	private Long orgBusiInfoId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="OCCU_INDUSTRY")
	private String occuIndustry;
	@Column(name="MANAGE_FORM")
	private String manageForm;
	@Column(name="MANAGE_APTITUDE")
	private String manageAptitude;
	@Column(name="MANAGE_STAT")
	private String manageStat;
	@Column(name="MAIN_MATERIAL")
	private String mainMaterial;
	@Column(name="MAIN_PRODUCT")
	private String mainProduct;
	@Column(name="MAIN_MATERIAL_AREA")
	private String mainMaterialArea;
	@Column(name="MAIN_SALE_MARKET")
	private String mainSaleMarket;
	@Column(name="MAIN_SUPPLIER")
	private String mainSupplier;
	@Column(name="MAIN_SALE")
	private String mainSale;
	@Column(name="LAST_INCO")
	private BigDecimal lastInco;
	@Column(name="YEAR_INCO")
	private BigDecimal yearInco;
	@Column(name="SALE_NUM")
	private Long saleNum;
	@Column(name="SALE_AMT")
	private BigDecimal saleAmt;
	@Column(name="BUDGET_ADMIN_TYPE")
	private String budgetAdminType;
	@Column(name="BUSINESS_PLAN")
	private String businessPlan;
	@Column(name="WORK_FIELD_OWNERSHIP")
	private String workFieldOwnership;
	@Column(name="WORK_FIELD_AREA")
	private BigDecimal workFieldArea;
	@Column(name="CUST_BUSI_AREA")
	private BigDecimal custBusiArea;
	@Column(name="CUST_OFFICE_AREA")
	private BigDecimal custOfficeArea;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getOrgBusiInfoId() {
 return this.orgBusiInfoId;
 }
 public void setOrgBusiInfoId(Long orgBusiInfoId) {
  this.orgBusiInfoId=orgBusiInfoId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getOccuIndustry() {
 return this.occuIndustry;
 }
 public void setOccuIndustry(String occuIndustry) {
  this.occuIndustry=occuIndustry;
 }
 public String getManageForm() {
 return this.manageForm;
 }
 public void setManageForm(String manageForm) {
  this.manageForm=manageForm;
 }
 public String getManageAptitude() {
 return this.manageAptitude;
 }
 public void setManageAptitude(String manageAptitude) {
  this.manageAptitude=manageAptitude;
 }
 public String getManageStat() {
 return this.manageStat;
 }
 public void setManageStat(String manageStat) {
  this.manageStat=manageStat;
 }
 public String getMainMaterial() {
 return this.mainMaterial;
 }
 public void setMainMaterial(String mainMaterial) {
  this.mainMaterial=mainMaterial;
 }
 public String getMainProduct() {
 return this.mainProduct;
 }
 public void setMainProduct(String mainProduct) {
  this.mainProduct=mainProduct;
 }
 public String getMainMaterialArea() {
 return this.mainMaterialArea;
 }
 public void setMainMaterialArea(String mainMaterialArea) {
  this.mainMaterialArea=mainMaterialArea;
 }
 public String getMainSaleMarket() {
 return this.mainSaleMarket;
 }
 public void setMainSaleMarket(String mainSaleMarket) {
  this.mainSaleMarket=mainSaleMarket;
 }
 public String getMainSupplier() {
 return this.mainSupplier;
 }
 public void setMainSupplier(String mainSupplier) {
  this.mainSupplier=mainSupplier;
 }
 public String getMainSale() {
 return this.mainSale;
 }
 public void setMainSale(String mainSale) {
  this.mainSale=mainSale;
 }
 public BigDecimal getLastInco() {
 return this.lastInco;
 }
 public void setLastInco(BigDecimal lastInco) {
  this.lastInco=lastInco;
 }
 public BigDecimal getYearInco() {
 return this.yearInco;
 }
 public void setYearInco(BigDecimal yearInco) {
  this.yearInco=yearInco;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getSaleNum() {
 return this.saleNum;
 }
 public void setSaleNum(Long saleNum) {
  this.saleNum=saleNum;
 }
 public BigDecimal getSaleAmt() {
 return this.saleAmt;
 }
 public void setSaleAmt(BigDecimal saleAmt) {
  this.saleAmt=saleAmt;
 }
 public String getBudgetAdminType() {
 return this.budgetAdminType;
 }
 public void setBudgetAdminType(String budgetAdminType) {
  this.budgetAdminType=budgetAdminType;
 }
 public String getBusinessPlan() {
 return this.businessPlan;
 }
 public void setBusinessPlan(String businessPlan) {
  this.businessPlan=businessPlan;
 }
 public String getWorkFieldOwnership() {
 return this.workFieldOwnership;
 }
 public void setWorkFieldOwnership(String workFieldOwnership) {
  this.workFieldOwnership=workFieldOwnership;
 }
 public BigDecimal getWorkFieldArea() {
 return this.workFieldArea;
 }
 public void setWorkFieldArea(BigDecimal workFieldArea) {
  this.workFieldArea=workFieldArea;
 }
 public BigDecimal getCustBusiArea() {
 return this.custBusiArea;
 }
 public void setCustBusiArea(BigDecimal custBusiArea) {
  this.custBusiArea=custBusiArea;
 }
 public BigDecimal getCustOfficeArea() {
 return this.custOfficeArea;
 }
 public void setCustOfficeArea(BigDecimal custOfficeArea) {
  this.custOfficeArea=custOfficeArea;
 }
 public String getLastUpdateSys() {
 return this.lastUpdateSys;
 }
 public void setLastUpdateSys(String lastUpdateSys) {
  this.lastUpdateSys=lastUpdateSys;
 }
 public String getLastUpdateUser() {
 return this.lastUpdateUser;
 }
 public void setLastUpdateUser(String lastUpdateUser) {
  this.lastUpdateUser=lastUpdateUser;
 }
 public String getLastUpdateTm() {
 return this.lastUpdateTm;
 }
 public void setLastUpdateTm(String lastUpdateTm) {
  this.lastUpdateTm=lastUpdateTm;
 }
 public String getTxSeqNo() {
 return this.txSeqNo;
 }
 public void setTxSeqNo(String txSeqNo) {
  this.txSeqNo=txSeqNo;
 }
 }

