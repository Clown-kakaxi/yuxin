package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_ORG database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ORG")
public class AcrmFCiOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="ANNUAL_INCOME")
	private BigDecimal annualIncome;

	@Column(name="ANNUAL_PROFIT")
	private BigDecimal annualProfit;

	@Column(name="AREA_CODE")
	private String areaCode;

	@Column(name="ASSETS_SCALE")
	private String assetsScale;

	@Temporal(TemporalType.DATE)
	@Column(name="BUILD_DATE")
	private Date buildDate;

	@Column(name="BUSI_LIC_NO")
	private String busiLicNo;

	@Temporal(TemporalType.DATE)
	@Column(name="BUSI_START_DATE")
	private Date busiStartDate;

	@Column(name="BUSINESS_MODE")
	private String businessMode;

	private String churcustype;

	@Column(name="COM_HOLD_TYPE")
	private String comHoldType;

	@Column(name="COM_SP_BUSINESS")
	private String comSpBusiness;

	@Column(name="COM_SP_DETAIL")
	private String comSpDetail;

	@Temporal(TemporalType.DATE)
	@Column(name="COM_SP_END_DATE")
	private Date comSpEndDate;

	@Column(name="COM_SP_LIC_NO")
	private String comSpLicNo;

	@Column(name="COM_SP_LIC_ORG")
	private String comSpLicOrg;

	@Temporal(TemporalType.DATE)
	@Column(name="COM_SP_STR_DATE")
	private Date comSpStrDate;

//	@Column(name="CUS_BANK_REL")
//	private String cusBankRel;

//	@Column(name="CUS_CORP_REL")
//	private String cusCorpRel;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="ECONOMIC_TYPE")
	private String economicType;

	@Column(name="EMPLOYEE_SCALE")
	private String employeeScale;

	@Column(name="ENT_BELONG")
	private String entBelong;

	@Column(name="ENT_PROPERTY")
	private String entProperty;

	@Column(name="ENT_SCALE")
	private String entScale;

	@Column(name="ENT_SCALE_CK")
	private String entScaleCk;

	@Column(name="ENT_SCALE_RH")
	private String entScaleRh;

	@Column(name="FEXC_PRM_CODE")
	private String fexcPrmCode;

	@Column(name="FIN_REP_TYPE")
	private String finRepType;

	@Column(name="FUND_SOURCE")
	private String fundSource;

	@Column(name="GOVERN_STRUCTURE")
	private String governStructure;

	@Column(name="HOLD_STOCK_AMT")
	private BigDecimal holdStockAmt;

	@Column(name="IN_CLL_TYPE")
	private String inCllType;

	@Column(name="INDU_DEVE_PROSPECT")
	private String induDeveProspect;

	@Column(name="INDUSTRY_CATEGORY")
	private String industryCategory;

	@Column(name="INDUSTRY_CHAR")
	private String industryChar;

	@Column(name="INDUSTRY_DIVISION")
	private String industryDivision;

	@Column(name="INDUSTRY_POSITION")
	private String industryPosition;

	@Column(name="INVEST_TYPE")
	private String investType;

	@Column(name="IS_STOCK_HOLDER")
	private String isStockHolder;

	@Column(name="LAST_DEALINGS_DESC")
	private String lastDealingsDesc;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LEGAL_REPR_ADDR")
	private String legalReprAddr;

	@Column(name="LEGAL_REPR_GENDER")
	private String legalReprGender;

	@Column(name="LEGAL_REPR_IDENT_NO")
	private String legalReprIdentNo;

	@Column(name="LEGAL_REPR_IDENT_TYPE")
	private String legalReprIdentType;

	@Column(name="LEGAL_REPR_NAME")
	private String legalReprName;

	@Column(name="LEGAL_REPR_NATION_CODE")
	private String legalReprNationCode;

	@Column(name="LEGAL_REPR_PHOTO")
	private String legalReprPhoto;

	@Column(name="LEGAL_REPR_TEL")
	private String legalReprTel;

	@Temporal(TemporalType.DATE)
	@Column(name="LOAD_CARD_AUDIT_DT")
	private Date loadCardAuditDt;

	@Column(name="LOAD_CARD_PWD")
	private String loadCardPwd;

	@Column(name="LOAN_CARD_FLAG")
	private String loanCardFlag;

	@Column(name="LOAN_CARD_NO")
	private String loanCardNo;

	@Column(name="LOAN_CARD_STAT")
	private String loanCardStat;

//	@Column(name="LOAN_CUST_RANK")
//	private String loanCustRank;

//	@Column(name="LOAN_CUST_STAT")
//	private String loanCustStat;

	@Column(name="MAIN_BUSINESS")
	private String mainBusiness;

	@Column(name="MAIN_INDUSTRY")
	private String mainIndustry;

	@Column(name="MINOR_BUSINESS")
	private String minorBusiness;

	@Column(name="MINOR_INDUSTRY")
	private String minorIndustry;

	@Column(name="NATION_CODE")
	private String nationCode;

	@Column(name="ORG_ADDR")
	private String orgAddr;

	@Column(name="ORG_CODE")
	private String orgCode;

	@Temporal(TemporalType.DATE)
	@Column(name="ORG_CODE_ANN_DATE")
	private Date orgCodeAnnDate;

	@Column(name="ORG_CODE_UNIT")
	private String orgCodeUnit;

	@Column(name="ORG_CUS")
	private String orgCus;

	@Column(name="ORG_CUST_TYPE")
	private String orgCustType;

	@Column(name="ORG_EMAIL")
	private String orgEmail;

	@Temporal(TemporalType.DATE)
	@Column(name="ORG_EXP_DATE")
	private Date orgExpDate;

	@Column(name="ORG_FEX")
	private String orgFex;

	@Column(name="ORG_FORM")
	private String orgForm;

	@Column(name="ORG_HOMEPAGE")
	private String orgHomepage;

	@Temporal(TemporalType.DATE)
	@Column(name="ORG_REG_DATE")
	private Date orgRegDate;

	@Column(name="ORG_SUB_TYPE")
	private String orgSubType;
	
	@Column(name="IF_ORG_SUB_TYPE")
	private String ifOrgSubType;

	@Column(name="ORG_TEL")
	private String orgTel;

	@Column(name="ORG_TYPE")
	private String orgType;

	@Column(name="ORG_WEIBO")
	private String orgWeibo;

	@Column(name="ORG_WEIXIN")
	private String orgWeixin;

	@Column(name="ORG_ZIPCODE")
	private String orgZipcode;

	private String remark;

	@Column(name="SUPER_DEPT")
	private String superDept;

	@Column(name="TOP_CORP_LEVEL")
	private String topCorpLevel;

	@Column(name="TOTAL_ASSETS")
	private BigDecimal totalAssets;

	@Column(name="TOTAL_DEBT")
	private BigDecimal totalDebt;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="ZONE_CODE")
	private String zoneCode;

	public AcrmFCiOrg() {
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public BigDecimal getAnnualIncome() {
		return this.annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	public BigDecimal getAnnualProfit() {
		return this.annualProfit;
	}

	public void setAnnualProfit(BigDecimal annualProfit) {
		this.annualProfit = annualProfit;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAssetsScale() {
		return this.assetsScale;
	}

	public void setAssetsScale(String assetsScale) {
		this.assetsScale = assetsScale;
	}

	public Date getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public String getBusiLicNo() {
		return this.busiLicNo;
	}

	public void setBusiLicNo(String busiLicNo) {
		this.busiLicNo = busiLicNo;
	}

	public Date getBusiStartDate() {
		return this.busiStartDate;
	}

	public void setBusiStartDate(Date busiStartDate) {
		this.busiStartDate = busiStartDate;
	}

	public String getBusinessMode() {
		return this.businessMode;
	}

	public void setBusinessMode(String businessMode) {
		this.businessMode = businessMode;
	}

	public String getChurcustype() {
		return this.churcustype;
	}

	public void setChurcustype(String churcustype) {
		this.churcustype = churcustype;
	}

	public String getComHoldType() {
		return this.comHoldType;
	}

	public void setComHoldType(String comHoldType) {
		this.comHoldType = comHoldType;
	}

	public String getComSpBusiness() {
		return this.comSpBusiness;
	}

	public void setComSpBusiness(String comSpBusiness) {
		this.comSpBusiness = comSpBusiness;
	}

	public String getComSpDetail() {
		return this.comSpDetail;
	}

	public void setComSpDetail(String comSpDetail) {
		this.comSpDetail = comSpDetail;
	}

	public Date getComSpEndDate() {
		return this.comSpEndDate;
	}

	public void setComSpEndDate(Date comSpEndDate) {
		this.comSpEndDate = comSpEndDate;
	}

	public String getComSpLicNo() {
		return this.comSpLicNo;
	}

	public void setComSpLicNo(String comSpLicNo) {
		this.comSpLicNo = comSpLicNo;
	}

	public String getComSpLicOrg() {
		return this.comSpLicOrg;
	}

	public void setComSpLicOrg(String comSpLicOrg) {
		this.comSpLicOrg = comSpLicOrg;
	}

	public Date getComSpStrDate() {
		return this.comSpStrDate;
	}

	public void setComSpStrDate(Date comSpStrDate) {
		this.comSpStrDate = comSpStrDate;
	}

//	public String getCusBankRel() {
//		return this.cusBankRel;
//	}
//
//	public void setCusBankRel(String cusBankRel) {
//		this.cusBankRel = cusBankRel;
//	}

//	public String getCusCorpRel() {
//		return this.cusCorpRel;
//	}
//
//	public void setCusCorpRel(String cusCorpRel) {
//		this.cusCorpRel = cusCorpRel;
//	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getEconomicType() {
		return this.economicType;
	}

	public void setEconomicType(String economicType) {
		this.economicType = economicType;
	}

	public String getEmployeeScale() {
		return this.employeeScale;
	}

	public void setEmployeeScale(String employeeScale) {
		this.employeeScale = employeeScale;
	}

	public String getEntBelong() {
		return this.entBelong;
	}

	public void setEntBelong(String entBelong) {
		this.entBelong = entBelong;
	}

	public String getEntProperty() {
		return this.entProperty;
	}

	public void setEntProperty(String entProperty) {
		this.entProperty = entProperty;
	}

	public String getEntScale() {
		return this.entScale;
	}

	public void setEntScale(String entScale) {
		this.entScale = entScale;
	}

	public String getEntScaleCk() {
		return this.entScaleCk;
	}

	public void setEntScaleCk(String entScaleCk) {
		this.entScaleCk = entScaleCk;
	}

	public String getEntScaleRh() {
		return this.entScaleRh;
	}

	public void setEntScaleRh(String entScaleRh) {
		this.entScaleRh = entScaleRh;
	}

	public String getFexcPrmCode() {
		return this.fexcPrmCode;
	}

	public void setFexcPrmCode(String fexcPrmCode) {
		this.fexcPrmCode = fexcPrmCode;
	}

	public String getFinRepType() {
		return this.finRepType;
	}

	public void setFinRepType(String finRepType) {
		this.finRepType = finRepType;
	}

	public String getFundSource() {
		return this.fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public String getGovernStructure() {
		return this.governStructure;
	}

	public void setGovernStructure(String governStructure) {
		this.governStructure = governStructure;
	}

	public BigDecimal getHoldStockAmt() {
		return this.holdStockAmt;
	}

	public void setHoldStockAmt(BigDecimal holdStockAmt) {
		this.holdStockAmt = holdStockAmt;
	}

	public String getInCllType() {
		return this.inCllType;
	}

	public void setInCllType(String inCllType) {
		this.inCllType = inCllType;
	}

	public String getInduDeveProspect() {
		return this.induDeveProspect;
	}

	public void setInduDeveProspect(String induDeveProspect) {
		this.induDeveProspect = induDeveProspect;
	}

	public String getIndustryCategory() {
		return this.industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getIndustryChar() {
		return this.industryChar;
	}

	public void setIndustryChar(String industryChar) {
		this.industryChar = industryChar;
	}

	public String getIndustryDivision() {
		return this.industryDivision;
	}

	public void setIndustryDivision(String industryDivision) {
		this.industryDivision = industryDivision;
	}

	public String getIndustryPosition() {
		return this.industryPosition;
	}

	public void setIndustryPosition(String industryPosition) {
		this.industryPosition = industryPosition;
	}

	public String getInvestType() {
		return this.investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public String getIsStockHolder() {
		return this.isStockHolder;
	}

	public void setIsStockHolder(String isStockHolder) {
		this.isStockHolder = isStockHolder;
	}

	public String getLastDealingsDesc() {
		return this.lastDealingsDesc;
	}

	public void setLastDealingsDesc(String lastDealingsDesc) {
		this.lastDealingsDesc = lastDealingsDesc;
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

	public String getLegalReprAddr() {
		return this.legalReprAddr;
	}

	public void setLegalReprAddr(String legalReprAddr) {
		this.legalReprAddr = legalReprAddr;
	}

	public String getLegalReprGender() {
		return this.legalReprGender;
	}

	public void setLegalReprGender(String legalReprGender) {
		this.legalReprGender = legalReprGender;
	}

	public String getLegalReprIdentNo() {
		return this.legalReprIdentNo;
	}

	public void setLegalReprIdentNo(String legalReprIdentNo) {
		this.legalReprIdentNo = legalReprIdentNo;
	}

	public String getLegalReprIdentType() {
		return this.legalReprIdentType;
	}

	public void setLegalReprIdentType(String legalReprIdentType) {
		this.legalReprIdentType = legalReprIdentType;
	}

	public String getLegalReprName() {
		return this.legalReprName;
	}

	public void setLegalReprName(String legalReprName) {
		this.legalReprName = legalReprName;
	}

	public String getLegalReprNationCode() {
		return this.legalReprNationCode;
	}

	public void setLegalReprNationCode(String legalReprNationCode) {
		this.legalReprNationCode = legalReprNationCode;
	}

	public String getLegalReprPhoto() {
		return this.legalReprPhoto;
	}

	public void setLegalReprPhoto(String legalReprPhoto) {
		this.legalReprPhoto = legalReprPhoto;
	}

	public String getLegalReprTel() {
		return this.legalReprTel;
	}

	public void setLegalReprTel(String legalReprTel) {
		this.legalReprTel = legalReprTel;
	}

	public Date getLoadCardAuditDt() {
		return this.loadCardAuditDt;
	}

	public void setLoadCardAuditDt(Date loadCardAuditDt) {
		this.loadCardAuditDt = loadCardAuditDt;
	}

	public String getLoadCardPwd() {
		return this.loadCardPwd;
	}

	public void setLoadCardPwd(String loadCardPwd) {
		this.loadCardPwd = loadCardPwd;
	}

	public String getLoanCardFlag() {
		return this.loanCardFlag;
	}

	public void setLoanCardFlag(String loanCardFlag) {
		this.loanCardFlag = loanCardFlag;
	}

	public String getLoanCardNo() {
		return this.loanCardNo;
	}

	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}

	public String getLoanCardStat() {
		return this.loanCardStat;
	}

	public void setLoanCardStat(String loanCardStat) {
		this.loanCardStat = loanCardStat;
	}

//	public String getLoanCustRank() {
//		return this.loanCustRank;
//	}
//
//	public void setLoanCustRank(String loanCustRank) {
//		this.loanCustRank = loanCustRank;
//	}

//	public String getLoanCustStat() {
//		return this.loanCustStat;
//	}
//
//	public void setLoanCustStat(String loanCustStat) {
//		this.loanCustStat = loanCustStat;
//	}

	public String getMainBusiness() {
		return this.mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getMainIndustry() {
		return this.mainIndustry;
	}

	public void setMainIndustry(String mainIndustry) {
		this.mainIndustry = mainIndustry;
	}

	public String getMinorBusiness() {
		return this.minorBusiness;
	}

	public void setMinorBusiness(String minorBusiness) {
		this.minorBusiness = minorBusiness;
	}

	public String getMinorIndustry() {
		return this.minorIndustry;
	}

	public void setMinorIndustry(String minorIndustry) {
		this.minorIndustry = minorIndustry;
	}

	public String getNationCode() {
		return this.nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getOrgAddr() {
		return this.orgAddr;
	}

	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Date getOrgCodeAnnDate() {
		return this.orgCodeAnnDate;
	}

	public void setOrgCodeAnnDate(Date orgCodeAnnDate) {
		this.orgCodeAnnDate = orgCodeAnnDate;
	}

	public String getOrgCodeUnit() {
		return this.orgCodeUnit;
	}

	public void setOrgCodeUnit(String orgCodeUnit) {
		this.orgCodeUnit = orgCodeUnit;
	}

	public String getOrgCus() {
		return this.orgCus;
	}

	public void setOrgCus(String orgCus) {
		this.orgCus = orgCus;
	}

	public String getOrgCustType() {
		return this.orgCustType;
	}

	public void setOrgCustType(String orgCustType) {
		this.orgCustType = orgCustType;
	}

	public String getOrgEmail() {
		return this.orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public Date getOrgExpDate() {
		return this.orgExpDate;
	}

	public void setOrgExpDate(Date orgExpDate) {
		this.orgExpDate = orgExpDate;
	}

	public String getOrgFex() {
		return this.orgFex;
	}

	public void setOrgFex(String orgFex) {
		this.orgFex = orgFex;
	}

	public String getOrgForm() {
		return this.orgForm;
	}

	public void setOrgForm(String orgForm) {
		this.orgForm = orgForm;
	}

	public String getOrgHomepage() {
		return this.orgHomepage;
	}

	public void setOrgHomepage(String orgHomepage) {
		this.orgHomepage = orgHomepage;
	}

	public Date getOrgRegDate() {
		return this.orgRegDate;
	}

	public void setOrgRegDate(Date orgRegDate) {
		this.orgRegDate = orgRegDate;
	}

	public String getOrgSubType() {
		return this.orgSubType;
	}

	public void setOrgSubType(String orgSubType) {
		this.orgSubType = orgSubType;
	}

	public String getOrgTel() {
		return this.orgTel;
	}

	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgWeibo() {
		return this.orgWeibo;
	}

	public void setOrgWeibo(String orgWeibo) {
		this.orgWeibo = orgWeibo;
	}

	public String getOrgWeixin() {
		return this.orgWeixin;
	}

	public void setOrgWeixin(String orgWeixin) {
		this.orgWeixin = orgWeixin;
	}

	public String getOrgZipcode() {
		return this.orgZipcode;
	}

	public void setOrgZipcode(String orgZipcode) {
		this.orgZipcode = orgZipcode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSuperDept() {
		return this.superDept;
	}

	public void setSuperDept(String superDept) {
		this.superDept = superDept;
	}

	public String getTopCorpLevel() {
		return this.topCorpLevel;
	}

	public void setTopCorpLevel(String topCorpLevel) {
		this.topCorpLevel = topCorpLevel;
	}

	public BigDecimal getTotalAssets() {
		return this.totalAssets;
	}

	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}

	public BigDecimal getTotalDebt() {
		return this.totalDebt;
	}

	public void setTotalDebt(BigDecimal totalDebt) {
		this.totalDebt = totalDebt;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getZoneCode() {
		return this.zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getIfOrgSubType() {
		return ifOrgSubType;
	}

	public void setIfOrgSubType(String ifOrgSubType) {
		this.ifOrgSubType = ifOrgSubType;
	}
	

}