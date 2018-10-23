package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiOrgId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgId implements java.io.Serializable {

	// Fields

	private String custId;
	private String custName;
	private String orgCustType;
	private String churcustype;
	private String orgBizCustType;
	private String hqNationCode;
	private String nationCode;
	private String areaCode;
	private String orgType;
	private String orgSubType;
	private String orgCode;
	private Date orgRegDate;
	private Date orgExpDate;
	private String orgCodeUnit;
	private Date orgCodeAnnDate;
	private String busiLicNo;
	private String mainIndustry;
	private String minorIndustry;
	private String industryDivision;
	private String industryChar;
	private String entProperty;
	private String entScale;
	private String entScaleRh;
	private String entScaleCk;
	private String assetsScale;
	private String employeeScale;
	private String economicType;
	private String comHoldType;
	private String orgForm;
	private String governStructure;
	private String inCllType;
	private String industryCategory;
	private String investType;
	private String entBelong;
	private Date buildDate;
	private String superDept;
	private String mainBusiness;
	private String minorBusiness;
	private String businessMode;
	private Date busiStartDate;
	private String induDeveProspect;
	private String fundSource;
	private String zoneCode;
	private String fexcPrmCode;
	private String topCorpLevel;
	private String comSpBusiness;
	private String comSpLicNo;
	private String comSpDetail;
	private String comSpLicOrg;
	private Date comSpStrDate;
	private Date comSpEndDate;
	private String loanCardFlag;
	private String loanCardNo;
	private String loanCardStat;
	private String loadCardPwd;
	private Date loadCardAuditDt;
	private String partnerType;
	private String legalReprName;
	private String legalReprGender;
	private String legalReprIdentType;
	private String legalReprIdentNo;
	private String legalReprTel;
	private String legalReprAddr;
	private String legalReprPhoto;
	private String legalReprNationCode;
	private String finRepType;
	private Double totalAssets;
	private Double totalDebt;
	private Double annualIncome;
	private Double annualProfit;
	private String industryPosition;
	private String isStockHolder;
	private Double holdStockAmt;
	private String orgAddr;
	private String orgZipcode;
	private String orgCus;
	private String orgTel;
	private String orgFex;
	private String orgEmail;
	private String orgHomepage;
	private String orgWeibo;
	private String orgWeixin;
	private String lastDealingsDesc;
	private String remark;
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
	public HMCiOrgId() {
	}

	/** minimal constructor */
	public HMCiOrgId(String custId, Timestamp hisOperTime) {
		this.custId = custId;
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgId(String custId, String custName, String orgCustType,
			String churcustype, String orgBizCustType, String hqNationCode,
			String nationCode, String areaCode, String orgType,
			String orgSubType, String orgCode, Date orgRegDate,
			Date orgExpDate, String orgCodeUnit, Date orgCodeAnnDate,
			String busiLicNo, String mainIndustry, String minorIndustry,
			String industryDivision, String industryChar, String entProperty,
			String entScale, String entScaleRh, String entScaleCk,
			String assetsScale, String employeeScale, String economicType,
			String comHoldType, String orgForm, String governStructure,
			String inCllType, String industryCategory, String investType,
			String entBelong, Date buildDate, String superDept,
			String mainBusiness, String minorBusiness, String businessMode,
			Date busiStartDate, String induDeveProspect, String fundSource,
			String zoneCode, String fexcPrmCode, String topCorpLevel,
			String comSpBusiness, String comSpLicNo, String comSpDetail,
			String comSpLicOrg, Date comSpStrDate, Date comSpEndDate,
			String loanCardFlag, String loanCardNo, String loanCardStat,
			String loadCardPwd, Date loadCardAuditDt, String partnerType,
			String legalReprName, String legalReprGender,
			String legalReprIdentType, String legalReprIdentNo,
			String legalReprTel, String legalReprAddr, String legalReprPhoto,
			String legalReprNationCode, String finRepType, Double totalAssets,
			Double totalDebt, Double annualIncome, Double annualProfit,
			String industryPosition, String isStockHolder, Double holdStockAmt,
			String orgAddr, String orgZipcode, String orgCus, String orgTel,
			String orgFex, String orgEmail, String orgHomepage,
			String orgWeibo, String orgWeixin, String lastDealingsDesc,
			String remark, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.custId = custId;
		this.custName = custName;
		this.orgCustType = orgCustType;
		this.churcustype = churcustype;
		this.orgBizCustType = orgBizCustType;
		this.hqNationCode = hqNationCode;
		this.nationCode = nationCode;
		this.areaCode = areaCode;
		this.orgType = orgType;
		this.orgSubType = orgSubType;
		this.orgCode = orgCode;
		this.orgRegDate = orgRegDate;
		this.orgExpDate = orgExpDate;
		this.orgCodeUnit = orgCodeUnit;
		this.orgCodeAnnDate = orgCodeAnnDate;
		this.busiLicNo = busiLicNo;
		this.mainIndustry = mainIndustry;
		this.minorIndustry = minorIndustry;
		this.industryDivision = industryDivision;
		this.industryChar = industryChar;
		this.entProperty = entProperty;
		this.entScale = entScale;
		this.entScaleRh = entScaleRh;
		this.entScaleCk = entScaleCk;
		this.assetsScale = assetsScale;
		this.employeeScale = employeeScale;
		this.economicType = economicType;
		this.comHoldType = comHoldType;
		this.orgForm = orgForm;
		this.governStructure = governStructure;
		this.inCllType = inCllType;
		this.industryCategory = industryCategory;
		this.investType = investType;
		this.entBelong = entBelong;
		this.buildDate = buildDate;
		this.superDept = superDept;
		this.mainBusiness = mainBusiness;
		this.minorBusiness = minorBusiness;
		this.businessMode = businessMode;
		this.busiStartDate = busiStartDate;
		this.induDeveProspect = induDeveProspect;
		this.fundSource = fundSource;
		this.zoneCode = zoneCode;
		this.fexcPrmCode = fexcPrmCode;
		this.topCorpLevel = topCorpLevel;
		this.comSpBusiness = comSpBusiness;
		this.comSpLicNo = comSpLicNo;
		this.comSpDetail = comSpDetail;
		this.comSpLicOrg = comSpLicOrg;
		this.comSpStrDate = comSpStrDate;
		this.comSpEndDate = comSpEndDate;
		this.loanCardFlag = loanCardFlag;
		this.loanCardNo = loanCardNo;
		this.loanCardStat = loanCardStat;
		this.loadCardPwd = loadCardPwd;
		this.loadCardAuditDt = loadCardAuditDt;
		this.partnerType = partnerType;
		this.legalReprName = legalReprName;
		this.legalReprGender = legalReprGender;
		this.legalReprIdentType = legalReprIdentType;
		this.legalReprIdentNo = legalReprIdentNo;
		this.legalReprTel = legalReprTel;
		this.legalReprAddr = legalReprAddr;
		this.legalReprPhoto = legalReprPhoto;
		this.legalReprNationCode = legalReprNationCode;
		this.finRepType = finRepType;
		this.totalAssets = totalAssets;
		this.totalDebt = totalDebt;
		this.annualIncome = annualIncome;
		this.annualProfit = annualProfit;
		this.industryPosition = industryPosition;
		this.isStockHolder = isStockHolder;
		this.holdStockAmt = holdStockAmt;
		this.orgAddr = orgAddr;
		this.orgZipcode = orgZipcode;
		this.orgCus = orgCus;
		this.orgTel = orgTel;
		this.orgFex = orgFex;
		this.orgEmail = orgEmail;
		this.orgHomepage = orgHomepage;
		this.orgWeibo = orgWeibo;
		this.orgWeixin = orgWeixin;
		this.lastDealingsDesc = lastDealingsDesc;
		this.remark = remark;
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

	@Column(name = "CUST_ID", nullable = false, length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "CUST_NAME", length = 80)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name = "ORG_CUST_TYPE", length = 20)
	public String getOrgCustType() {
		return this.orgCustType;
	}

	public void setOrgCustType(String orgCustType) {
		this.orgCustType = orgCustType;
	}

	@Column(name = "CHURCUSTYPE", length = 20)
	public String getChurcustype() {
		return this.churcustype;
	}

	public void setChurcustype(String churcustype) {
		this.churcustype = churcustype;
	}

	@Column(name = "ORG_BIZ_CUST_TYPE", length = 20)
	public String getOrgBizCustType() {
		return this.orgBizCustType;
	}

	public void setOrgBizCustType(String orgBizCustType) {
		this.orgBizCustType = orgBizCustType;
	}

	@Column(name = "HQ_NATION_CODE", length = 20)
	public String getHqNationCode() {
		return this.hqNationCode;
	}

	public void setHqNationCode(String hqNationCode) {
		this.hqNationCode = hqNationCode;
	}

	@Column(name = "NATION_CODE", length = 20)
	public String getNationCode() {
		return this.nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	@Column(name = "AREA_CODE", length = 20)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "ORG_TYPE", length = 20)
	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name = "ORG_SUB_TYPE", length = 20)
	public String getOrgSubType() {
		return this.orgSubType;
	}

	public void setOrgSubType(String orgSubType) {
		this.orgSubType = orgSubType;
	}

	@Column(name = "ORG_CODE", length = 20)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ORG_REG_DATE", length = 7)
	public Date getOrgRegDate() {
		return this.orgRegDate;
	}

	public void setOrgRegDate(Date orgRegDate) {
		this.orgRegDate = orgRegDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ORG_EXP_DATE", length = 7)
	public Date getOrgExpDate() {
		return this.orgExpDate;
	}

	public void setOrgExpDate(Date orgExpDate) {
		this.orgExpDate = orgExpDate;
	}

	@Column(name = "ORG_CODE_UNIT", length = 60)
	public String getOrgCodeUnit() {
		return this.orgCodeUnit;
	}

	public void setOrgCodeUnit(String orgCodeUnit) {
		this.orgCodeUnit = orgCodeUnit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ORG_CODE_ANN_DATE", length = 7)
	public Date getOrgCodeAnnDate() {
		return this.orgCodeAnnDate;
	}

	public void setOrgCodeAnnDate(Date orgCodeAnnDate) {
		this.orgCodeAnnDate = orgCodeAnnDate;
	}

	@Column(name = "BUSI_LIC_NO", length = 32)
	public String getBusiLicNo() {
		return this.busiLicNo;
	}

	public void setBusiLicNo(String busiLicNo) {
		this.busiLicNo = busiLicNo;
	}

	@Column(name = "MAIN_INDUSTRY", length = 20)
	public String getMainIndustry() {
		return this.mainIndustry;
	}

	public void setMainIndustry(String mainIndustry) {
		this.mainIndustry = mainIndustry;
	}

	@Column(name = "MINOR_INDUSTRY", length = 20)
	public String getMinorIndustry() {
		return this.minorIndustry;
	}

	public void setMinorIndustry(String minorIndustry) {
		this.minorIndustry = minorIndustry;
	}

	@Column(name = "INDUSTRY_DIVISION", length = 20)
	public String getIndustryDivision() {
		return this.industryDivision;
	}

	public void setIndustryDivision(String industryDivision) {
		this.industryDivision = industryDivision;
	}

	@Column(name = "INDUSTRY_CHAR", length = 20)
	public String getIndustryChar() {
		return this.industryChar;
	}

	public void setIndustryChar(String industryChar) {
		this.industryChar = industryChar;
	}

	@Column(name = "ENT_PROPERTY", length = 20)
	public String getEntProperty() {
		return this.entProperty;
	}

	public void setEntProperty(String entProperty) {
		this.entProperty = entProperty;
	}

	@Column(name = "ENT_SCALE", length = 20)
	public String getEntScale() {
		return this.entScale;
	}

	public void setEntScale(String entScale) {
		this.entScale = entScale;
	}

	@Column(name = "ENT_SCALE_RH", length = 20)
	public String getEntScaleRh() {
		return this.entScaleRh;
	}

	public void setEntScaleRh(String entScaleRh) {
		this.entScaleRh = entScaleRh;
	}

	@Column(name = "ENT_SCALE_CK", length = 20)
	public String getEntScaleCk() {
		return this.entScaleCk;
	}

	public void setEntScaleCk(String entScaleCk) {
		this.entScaleCk = entScaleCk;
	}

	@Column(name = "ASSETS_SCALE", length = 20)
	public String getAssetsScale() {
		return this.assetsScale;
	}

	public void setAssetsScale(String assetsScale) {
		this.assetsScale = assetsScale;
	}

	@Column(name = "EMPLOYEE_SCALE", length = 20)
	public String getEmployeeScale() {
		return this.employeeScale;
	}

	public void setEmployeeScale(String employeeScale) {
		this.employeeScale = employeeScale;
	}

	@Column(name = "ECONOMIC_TYPE", length = 20)
	public String getEconomicType() {
		return this.economicType;
	}

	public void setEconomicType(String economicType) {
		this.economicType = economicType;
	}

	@Column(name = "COM_HOLD_TYPE", length = 20)
	public String getComHoldType() {
		return this.comHoldType;
	}

	public void setComHoldType(String comHoldType) {
		this.comHoldType = comHoldType;
	}

	@Column(name = "ORG_FORM", length = 20)
	public String getOrgForm() {
		return this.orgForm;
	}

	public void setOrgForm(String orgForm) {
		this.orgForm = orgForm;
	}

	@Column(name = "GOVERN_STRUCTURE", length = 20)
	public String getGovernStructure() {
		return this.governStructure;
	}

	public void setGovernStructure(String governStructure) {
		this.governStructure = governStructure;
	}

	@Column(name = "IN_CLL_TYPE", length = 20)
	public String getInCllType() {
		return this.inCllType;
	}

	public void setInCllType(String inCllType) {
		this.inCllType = inCllType;
	}

	@Column(name = "INDUSTRY_CATEGORY", length = 20)
	public String getIndustryCategory() {
		return this.industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	@Column(name = "INVEST_TYPE", length = 20)
	public String getInvestType() {
		return this.investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	@Column(name = "ENT_BELONG", length = 100)
	public String getEntBelong() {
		return this.entBelong;
	}

	public void setEntBelong(String entBelong) {
		this.entBelong = entBelong;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BUILD_DATE", length = 7)
	public Date getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	@Column(name = "SUPER_DEPT", length = 60)
	public String getSuperDept() {
		return this.superDept;
	}

	public void setSuperDept(String superDept) {
		this.superDept = superDept;
	}

	@Column(name = "MAIN_BUSINESS", length = 500)
	public String getMainBusiness() {
		return this.mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	@Column(name = "MINOR_BUSINESS", length = 500)
	public String getMinorBusiness() {
		return this.minorBusiness;
	}

	public void setMinorBusiness(String minorBusiness) {
		this.minorBusiness = minorBusiness;
	}

	@Column(name = "BUSINESS_MODE", length = 20)
	public String getBusinessMode() {
		return this.businessMode;
	}

	public void setBusinessMode(String businessMode) {
		this.businessMode = businessMode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BUSI_START_DATE", length = 7)
	public Date getBusiStartDate() {
		return this.busiStartDate;
	}

	public void setBusiStartDate(Date busiStartDate) {
		this.busiStartDate = busiStartDate;
	}

	@Column(name = "INDU_DEVE_PROSPECT", length = 20)
	public String getInduDeveProspect() {
		return this.induDeveProspect;
	}

	public void setInduDeveProspect(String induDeveProspect) {
		this.induDeveProspect = induDeveProspect;
	}

	@Column(name = "FUND_SOURCE", length = 200)
	public String getFundSource() {
		return this.fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	@Column(name = "ZONE_CODE", length = 20)
	public String getZoneCode() {
		return this.zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	@Column(name = "FEXC_PRM_CODE", length = 30)
	public String getFexcPrmCode() {
		return this.fexcPrmCode;
	}

	public void setFexcPrmCode(String fexcPrmCode) {
		this.fexcPrmCode = fexcPrmCode;
	}

	@Column(name = "TOP_CORP_LEVEL", length = 20)
	public String getTopCorpLevel() {
		return this.topCorpLevel;
	}

	public void setTopCorpLevel(String topCorpLevel) {
		this.topCorpLevel = topCorpLevel;
	}

	@Column(name = "COM_SP_BUSINESS", length = 1)
	public String getComSpBusiness() {
		return this.comSpBusiness;
	}

	public void setComSpBusiness(String comSpBusiness) {
		this.comSpBusiness = comSpBusiness;
	}

	@Column(name = "COM_SP_LIC_NO", length = 80)
	public String getComSpLicNo() {
		return this.comSpLicNo;
	}

	public void setComSpLicNo(String comSpLicNo) {
		this.comSpLicNo = comSpLicNo;
	}

	@Column(name = "COM_SP_DETAIL", length = 80)
	public String getComSpDetail() {
		return this.comSpDetail;
	}

	public void setComSpDetail(String comSpDetail) {
		this.comSpDetail = comSpDetail;
	}

	@Column(name = "COM_SP_LIC_ORG", length = 80)
	public String getComSpLicOrg() {
		return this.comSpLicOrg;
	}

	public void setComSpLicOrg(String comSpLicOrg) {
		this.comSpLicOrg = comSpLicOrg;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COM_SP_STR_DATE", length = 7)
	public Date getComSpStrDate() {
		return this.comSpStrDate;
	}

	public void setComSpStrDate(Date comSpStrDate) {
		this.comSpStrDate = comSpStrDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COM_SP_END_DATE", length = 7)
	public Date getComSpEndDate() {
		return this.comSpEndDate;
	}

	public void setComSpEndDate(Date comSpEndDate) {
		this.comSpEndDate = comSpEndDate;
	}

	@Column(name = "LOAN_CARD_FLAG", length = 1)
	public String getLoanCardFlag() {
		return this.loanCardFlag;
	}

	public void setLoanCardFlag(String loanCardFlag) {
		this.loanCardFlag = loanCardFlag;
	}

	@Column(name = "LOAN_CARD_NO", length = 32)
	public String getLoanCardNo() {
		return this.loanCardNo;
	}

	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}

	@Column(name = "LOAN_CARD_STAT", length = 20)
	public String getLoanCardStat() {
		return this.loanCardStat;
	}

	public void setLoanCardStat(String loanCardStat) {
		this.loanCardStat = loanCardStat;
	}

	@Column(name = "LOAD_CARD_PWD")
	public String getLoadCardPwd() {
		return this.loadCardPwd;
	}

	public void setLoadCardPwd(String loadCardPwd) {
		this.loadCardPwd = loadCardPwd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LOAD_CARD_AUDIT_DT", length = 7)
	public Date getLoadCardAuditDt() {
		return this.loadCardAuditDt;
	}

	public void setLoadCardAuditDt(Date loadCardAuditDt) {
		this.loadCardAuditDt = loadCardAuditDt;
	}

	@Column(name = "PARTNER_TYPE", length = 20)
	public String getPartnerType() {
		return this.partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	@Column(name = "LEGAL_REPR_NAME", length = 80)
	public String getLegalReprName() {
		return this.legalReprName;
	}

	public void setLegalReprName(String legalReprName) {
		this.legalReprName = legalReprName;
	}

	@Column(name = "LEGAL_REPR_GENDER", length = 20)
	public String getLegalReprGender() {
		return this.legalReprGender;
	}

	public void setLegalReprGender(String legalReprGender) {
		this.legalReprGender = legalReprGender;
	}

	@Column(name = "LEGAL_REPR_IDENT_TYPE", length = 20)
	public String getLegalReprIdentType() {
		return this.legalReprIdentType;
	}

	public void setLegalReprIdentType(String legalReprIdentType) {
		this.legalReprIdentType = legalReprIdentType;
	}

	@Column(name = "LEGAL_REPR_IDENT_NO", length = 32)
	public String getLegalReprIdentNo() {
		return this.legalReprIdentNo;
	}

	public void setLegalReprIdentNo(String legalReprIdentNo) {
		this.legalReprIdentNo = legalReprIdentNo;
	}

	@Column(name = "LEGAL_REPR_TEL", length = 20)
	public String getLegalReprTel() {
		return this.legalReprTel;
	}

	public void setLegalReprTel(String legalReprTel) {
		this.legalReprTel = legalReprTel;
	}

	@Column(name = "LEGAL_REPR_ADDR", length = 200)
	public String getLegalReprAddr() {
		return this.legalReprAddr;
	}

	public void setLegalReprAddr(String legalReprAddr) {
		this.legalReprAddr = legalReprAddr;
	}

	@Column(name = "LEGAL_REPR_PHOTO")
	public String getLegalReprPhoto() {
		return this.legalReprPhoto;
	}

	public void setLegalReprPhoto(String legalReprPhoto) {
		this.legalReprPhoto = legalReprPhoto;
	}

	@Column(name = "LEGAL_REPR_NATION_CODE", length = 20)
	public String getLegalReprNationCode() {
		return this.legalReprNationCode;
	}

	public void setLegalReprNationCode(String legalReprNationCode) {
		this.legalReprNationCode = legalReprNationCode;
	}

	@Column(name = "FIN_REP_TYPE", length = 20)
	public String getFinRepType() {
		return this.finRepType;
	}

	public void setFinRepType(String finRepType) {
		this.finRepType = finRepType;
	}

	@Column(name = "TOTAL_ASSETS", precision = 17)
	public Double getTotalAssets() {
		return this.totalAssets;
	}

	public void setTotalAssets(Double totalAssets) {
		this.totalAssets = totalAssets;
	}

	@Column(name = "TOTAL_DEBT", precision = 17)
	public Double getTotalDebt() {
		return this.totalDebt;
	}

	public void setTotalDebt(Double totalDebt) {
		this.totalDebt = totalDebt;
	}

	@Column(name = "ANNUAL_INCOME", precision = 17)
	public Double getAnnualIncome() {
		return this.annualIncome;
	}

	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}

	@Column(name = "ANNUAL_PROFIT", precision = 17)
	public Double getAnnualProfit() {
		return this.annualProfit;
	}

	public void setAnnualProfit(Double annualProfit) {
		this.annualProfit = annualProfit;
	}

	@Column(name = "INDUSTRY_POSITION", length = 80)
	public String getIndustryPosition() {
		return this.industryPosition;
	}

	public void setIndustryPosition(String industryPosition) {
		this.industryPosition = industryPosition;
	}

	@Column(name = "IS_STOCK_HOLDER", length = 1)
	public String getIsStockHolder() {
		return this.isStockHolder;
	}

	public void setIsStockHolder(String isStockHolder) {
		this.isStockHolder = isStockHolder;
	}

	@Column(name = "HOLD_STOCK_AMT", precision = 17)
	public Double getHoldStockAmt() {
		return this.holdStockAmt;
	}

	public void setHoldStockAmt(Double holdStockAmt) {
		this.holdStockAmt = holdStockAmt;
	}

	@Column(name = "ORG_ADDR", length = 200)
	public String getOrgAddr() {
		return this.orgAddr;
	}

	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}

	@Column(name = "ORG_ZIPCODE", length = 20)
	public String getOrgZipcode() {
		return this.orgZipcode;
	}

	public void setOrgZipcode(String orgZipcode) {
		this.orgZipcode = orgZipcode;
	}

	@Column(name = "ORG_CUS", length = 100)
	public String getOrgCus() {
		return this.orgCus;
	}

	public void setOrgCus(String orgCus) {
		this.orgCus = orgCus;
	}

	@Column(name = "ORG_TEL", length = 40)
	public String getOrgTel() {
		return this.orgTel;
	}

	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}

	@Column(name = "ORG_FEX", length = 20)
	public String getOrgFex() {
		return this.orgFex;
	}

	public void setOrgFex(String orgFex) {
		this.orgFex = orgFex;
	}

	@Column(name = "ORG_EMAIL", length = 50)
	public String getOrgEmail() {
		return this.orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	@Column(name = "ORG_HOMEPAGE", length = 100)
	public String getOrgHomepage() {
		return this.orgHomepage;
	}

	public void setOrgHomepage(String orgHomepage) {
		this.orgHomepage = orgHomepage;
	}

	@Column(name = "ORG_WEIBO", length = 100)
	public String getOrgWeibo() {
		return this.orgWeibo;
	}

	public void setOrgWeibo(String orgWeibo) {
		this.orgWeibo = orgWeibo;
	}

	@Column(name = "ORG_WEIXIN", length = 100)
	public String getOrgWeixin() {
		return this.orgWeixin;
	}

	public void setOrgWeixin(String orgWeixin) {
		this.orgWeixin = orgWeixin;
	}

	@Column(name = "LAST_DEALINGS_DESC", length = 200)
	public String getLastDealingsDesc() {
		return this.lastDealingsDesc;
	}

	public void setLastDealingsDesc(String lastDealingsDesc) {
		this.lastDealingsDesc = lastDealingsDesc;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "LAST_UPDATE_TM")
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

	@Column(name = "HIS_OPER_TIME", nullable = false)
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
		if (!(other instanceof HMCiOrgId))
			return false;
		HMCiOrgId castOther = (HMCiOrgId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getCustName() == castOther.getCustName()) || (this
						.getCustName() != null
						&& castOther.getCustName() != null && this
						.getCustName().equals(castOther.getCustName())))
				&& ((this.getOrgCustType() == castOther.getOrgCustType()) || (this
						.getOrgCustType() != null
						&& castOther.getOrgCustType() != null && this
						.getOrgCustType().equals(castOther.getOrgCustType())))
				&& ((this.getChurcustype() == castOther.getChurcustype()) || (this
						.getChurcustype() != null
						&& castOther.getChurcustype() != null && this
						.getChurcustype().equals(castOther.getChurcustype())))
				&& ((this.getOrgBizCustType() == castOther.getOrgBizCustType()) || (this
						.getOrgBizCustType() != null
						&& castOther.getOrgBizCustType() != null && this
						.getOrgBizCustType().equals(
								castOther.getOrgBizCustType())))
				&& ((this.getHqNationCode() == castOther.getHqNationCode()) || (this
						.getHqNationCode() != null
						&& castOther.getHqNationCode() != null && this
						.getHqNationCode().equals(castOther.getHqNationCode())))
				&& ((this.getNationCode() == castOther.getNationCode()) || (this
						.getNationCode() != null
						&& castOther.getNationCode() != null && this
						.getNationCode().equals(castOther.getNationCode())))
				&& ((this.getAreaCode() == castOther.getAreaCode()) || (this
						.getAreaCode() != null
						&& castOther.getAreaCode() != null && this
						.getAreaCode().equals(castOther.getAreaCode())))
				&& ((this.getOrgType() == castOther.getOrgType()) || (this
						.getOrgType() != null
						&& castOther.getOrgType() != null && this.getOrgType()
						.equals(castOther.getOrgType())))
				&& ((this.getOrgSubType() == castOther.getOrgSubType()) || (this
						.getOrgSubType() != null
						&& castOther.getOrgSubType() != null && this
						.getOrgSubType().equals(castOther.getOrgSubType())))
				&& ((this.getOrgCode() == castOther.getOrgCode()) || (this
						.getOrgCode() != null
						&& castOther.getOrgCode() != null && this.getOrgCode()
						.equals(castOther.getOrgCode())))
				&& ((this.getOrgRegDate() == castOther.getOrgRegDate()) || (this
						.getOrgRegDate() != null
						&& castOther.getOrgRegDate() != null && this
						.getOrgRegDate().equals(castOther.getOrgRegDate())))
				&& ((this.getOrgExpDate() == castOther.getOrgExpDate()) || (this
						.getOrgExpDate() != null
						&& castOther.getOrgExpDate() != null && this
						.getOrgExpDate().equals(castOther.getOrgExpDate())))
				&& ((this.getOrgCodeUnit() == castOther.getOrgCodeUnit()) || (this
						.getOrgCodeUnit() != null
						&& castOther.getOrgCodeUnit() != null && this
						.getOrgCodeUnit().equals(castOther.getOrgCodeUnit())))
				&& ((this.getOrgCodeAnnDate() == castOther.getOrgCodeAnnDate()) || (this
						.getOrgCodeAnnDate() != null
						&& castOther.getOrgCodeAnnDate() != null && this
						.getOrgCodeAnnDate().equals(
								castOther.getOrgCodeAnnDate())))
				&& ((this.getBusiLicNo() == castOther.getBusiLicNo()) || (this
						.getBusiLicNo() != null
						&& castOther.getBusiLicNo() != null && this
						.getBusiLicNo().equals(castOther.getBusiLicNo())))
				&& ((this.getMainIndustry() == castOther.getMainIndustry()) || (this
						.getMainIndustry() != null
						&& castOther.getMainIndustry() != null && this
						.getMainIndustry().equals(castOther.getMainIndustry())))
				&& ((this.getMinorIndustry() == castOther.getMinorIndustry()) || (this
						.getMinorIndustry() != null
						&& castOther.getMinorIndustry() != null && this
						.getMinorIndustry()
						.equals(castOther.getMinorIndustry())))
				&& ((this.getIndustryDivision() == castOther
						.getIndustryDivision()) || (this.getIndustryDivision() != null
						&& castOther.getIndustryDivision() != null && this
						.getIndustryDivision().equals(
								castOther.getIndustryDivision())))
				&& ((this.getIndustryChar() == castOther.getIndustryChar()) || (this
						.getIndustryChar() != null
						&& castOther.getIndustryChar() != null && this
						.getIndustryChar().equals(castOther.getIndustryChar())))
				&& ((this.getEntProperty() == castOther.getEntProperty()) || (this
						.getEntProperty() != null
						&& castOther.getEntProperty() != null && this
						.getEntProperty().equals(castOther.getEntProperty())))
				&& ((this.getEntScale() == castOther.getEntScale()) || (this
						.getEntScale() != null
						&& castOther.getEntScale() != null && this
						.getEntScale().equals(castOther.getEntScale())))
				&& ((this.getEntScaleRh() == castOther.getEntScaleRh()) || (this
						.getEntScaleRh() != null
						&& castOther.getEntScaleRh() != null && this
						.getEntScaleRh().equals(castOther.getEntScaleRh())))
				&& ((this.getEntScaleCk() == castOther.getEntScaleCk()) || (this
						.getEntScaleCk() != null
						&& castOther.getEntScaleCk() != null && this
						.getEntScaleCk().equals(castOther.getEntScaleCk())))
				&& ((this.getAssetsScale() == castOther.getAssetsScale()) || (this
						.getAssetsScale() != null
						&& castOther.getAssetsScale() != null && this
						.getAssetsScale().equals(castOther.getAssetsScale())))
				&& ((this.getEmployeeScale() == castOther.getEmployeeScale()) || (this
						.getEmployeeScale() != null
						&& castOther.getEmployeeScale() != null && this
						.getEmployeeScale()
						.equals(castOther.getEmployeeScale())))
				&& ((this.getEconomicType() == castOther.getEconomicType()) || (this
						.getEconomicType() != null
						&& castOther.getEconomicType() != null && this
						.getEconomicType().equals(castOther.getEconomicType())))
				&& ((this.getComHoldType() == castOther.getComHoldType()) || (this
						.getComHoldType() != null
						&& castOther.getComHoldType() != null && this
						.getComHoldType().equals(castOther.getComHoldType())))
				&& ((this.getOrgForm() == castOther.getOrgForm()) || (this
						.getOrgForm() != null
						&& castOther.getOrgForm() != null && this.getOrgForm()
						.equals(castOther.getOrgForm())))
				&& ((this.getGovernStructure() == castOther
						.getGovernStructure()) || (this.getGovernStructure() != null
						&& castOther.getGovernStructure() != null && this
						.getGovernStructure().equals(
								castOther.getGovernStructure())))
				&& ((this.getInCllType() == castOther.getInCllType()) || (this
						.getInCllType() != null
						&& castOther.getInCllType() != null && this
						.getInCllType().equals(castOther.getInCllType())))
				&& ((this.getIndustryCategory() == castOther
						.getIndustryCategory()) || (this.getIndustryCategory() != null
						&& castOther.getIndustryCategory() != null && this
						.getIndustryCategory().equals(
								castOther.getIndustryCategory())))
				&& ((this.getInvestType() == castOther.getInvestType()) || (this
						.getInvestType() != null
						&& castOther.getInvestType() != null && this
						.getInvestType().equals(castOther.getInvestType())))
				&& ((this.getEntBelong() == castOther.getEntBelong()) || (this
						.getEntBelong() != null
						&& castOther.getEntBelong() != null && this
						.getEntBelong().equals(castOther.getEntBelong())))
				&& ((this.getBuildDate() == castOther.getBuildDate()) || (this
						.getBuildDate() != null
						&& castOther.getBuildDate() != null && this
						.getBuildDate().equals(castOther.getBuildDate())))
				&& ((this.getSuperDept() == castOther.getSuperDept()) || (this
						.getSuperDept() != null
						&& castOther.getSuperDept() != null && this
						.getSuperDept().equals(castOther.getSuperDept())))
				&& ((this.getMainBusiness() == castOther.getMainBusiness()) || (this
						.getMainBusiness() != null
						&& castOther.getMainBusiness() != null && this
						.getMainBusiness().equals(castOther.getMainBusiness())))
				&& ((this.getMinorBusiness() == castOther.getMinorBusiness()) || (this
						.getMinorBusiness() != null
						&& castOther.getMinorBusiness() != null && this
						.getMinorBusiness()
						.equals(castOther.getMinorBusiness())))
				&& ((this.getBusinessMode() == castOther.getBusinessMode()) || (this
						.getBusinessMode() != null
						&& castOther.getBusinessMode() != null && this
						.getBusinessMode().equals(castOther.getBusinessMode())))
				&& ((this.getBusiStartDate() == castOther.getBusiStartDate()) || (this
						.getBusiStartDate() != null
						&& castOther.getBusiStartDate() != null && this
						.getBusiStartDate()
						.equals(castOther.getBusiStartDate())))
				&& ((this.getInduDeveProspect() == castOther
						.getInduDeveProspect()) || (this.getInduDeveProspect() != null
						&& castOther.getInduDeveProspect() != null && this
						.getInduDeveProspect().equals(
								castOther.getInduDeveProspect())))
				&& ((this.getFundSource() == castOther.getFundSource()) || (this
						.getFundSource() != null
						&& castOther.getFundSource() != null && this
						.getFundSource().equals(castOther.getFundSource())))
				&& ((this.getZoneCode() == castOther.getZoneCode()) || (this
						.getZoneCode() != null
						&& castOther.getZoneCode() != null && this
						.getZoneCode().equals(castOther.getZoneCode())))
				&& ((this.getFexcPrmCode() == castOther.getFexcPrmCode()) || (this
						.getFexcPrmCode() != null
						&& castOther.getFexcPrmCode() != null && this
						.getFexcPrmCode().equals(castOther.getFexcPrmCode())))
				&& ((this.getTopCorpLevel() == castOther.getTopCorpLevel()) || (this
						.getTopCorpLevel() != null
						&& castOther.getTopCorpLevel() != null && this
						.getTopCorpLevel().equals(castOther.getTopCorpLevel())))
				&& ((this.getComSpBusiness() == castOther.getComSpBusiness()) || (this
						.getComSpBusiness() != null
						&& castOther.getComSpBusiness() != null && this
						.getComSpBusiness()
						.equals(castOther.getComSpBusiness())))
				&& ((this.getComSpLicNo() == castOther.getComSpLicNo()) || (this
						.getComSpLicNo() != null
						&& castOther.getComSpLicNo() != null && this
						.getComSpLicNo().equals(castOther.getComSpLicNo())))
				&& ((this.getComSpDetail() == castOther.getComSpDetail()) || (this
						.getComSpDetail() != null
						&& castOther.getComSpDetail() != null && this
						.getComSpDetail().equals(castOther.getComSpDetail())))
				&& ((this.getComSpLicOrg() == castOther.getComSpLicOrg()) || (this
						.getComSpLicOrg() != null
						&& castOther.getComSpLicOrg() != null && this
						.getComSpLicOrg().equals(castOther.getComSpLicOrg())))
				&& ((this.getComSpStrDate() == castOther.getComSpStrDate()) || (this
						.getComSpStrDate() != null
						&& castOther.getComSpStrDate() != null && this
						.getComSpStrDate().equals(castOther.getComSpStrDate())))
				&& ((this.getComSpEndDate() == castOther.getComSpEndDate()) || (this
						.getComSpEndDate() != null
						&& castOther.getComSpEndDate() != null && this
						.getComSpEndDate().equals(castOther.getComSpEndDate())))
				&& ((this.getLoanCardFlag() == castOther.getLoanCardFlag()) || (this
						.getLoanCardFlag() != null
						&& castOther.getLoanCardFlag() != null && this
						.getLoanCardFlag().equals(castOther.getLoanCardFlag())))
				&& ((this.getLoanCardNo() == castOther.getLoanCardNo()) || (this
						.getLoanCardNo() != null
						&& castOther.getLoanCardNo() != null && this
						.getLoanCardNo().equals(castOther.getLoanCardNo())))
				&& ((this.getLoanCardStat() == castOther.getLoanCardStat()) || (this
						.getLoanCardStat() != null
						&& castOther.getLoanCardStat() != null && this
						.getLoanCardStat().equals(castOther.getLoanCardStat())))
				&& ((this.getLoadCardPwd() == castOther.getLoadCardPwd()) || (this
						.getLoadCardPwd() != null
						&& castOther.getLoadCardPwd() != null && this
						.getLoadCardPwd().equals(castOther.getLoadCardPwd())))
				&& ((this.getLoadCardAuditDt() == castOther
						.getLoadCardAuditDt()) || (this.getLoadCardAuditDt() != null
						&& castOther.getLoadCardAuditDt() != null && this
						.getLoadCardAuditDt().equals(
								castOther.getLoadCardAuditDt())))
				&& ((this.getPartnerType() == castOther.getPartnerType()) || (this
						.getPartnerType() != null
						&& castOther.getPartnerType() != null && this
						.getPartnerType().equals(castOther.getPartnerType())))
				&& ((this.getLegalReprName() == castOther.getLegalReprName()) || (this
						.getLegalReprName() != null
						&& castOther.getLegalReprName() != null && this
						.getLegalReprName()
						.equals(castOther.getLegalReprName())))
				&& ((this.getLegalReprGender() == castOther
						.getLegalReprGender()) || (this.getLegalReprGender() != null
						&& castOther.getLegalReprGender() != null && this
						.getLegalReprGender().equals(
								castOther.getLegalReprGender())))
				&& ((this.getLegalReprIdentType() == castOther
						.getLegalReprIdentType()) || (this
						.getLegalReprIdentType() != null
						&& castOther.getLegalReprIdentType() != null && this
						.getLegalReprIdentType().equals(
								castOther.getLegalReprIdentType())))
				&& ((this.getLegalReprIdentNo() == castOther
						.getLegalReprIdentNo()) || (this.getLegalReprIdentNo() != null
						&& castOther.getLegalReprIdentNo() != null && this
						.getLegalReprIdentNo().equals(
								castOther.getLegalReprIdentNo())))
				&& ((this.getLegalReprTel() == castOther.getLegalReprTel()) || (this
						.getLegalReprTel() != null
						&& castOther.getLegalReprTel() != null && this
						.getLegalReprTel().equals(castOther.getLegalReprTel())))
				&& ((this.getLegalReprAddr() == castOther.getLegalReprAddr()) || (this
						.getLegalReprAddr() != null
						&& castOther.getLegalReprAddr() != null && this
						.getLegalReprAddr()
						.equals(castOther.getLegalReprAddr())))
				&& ((this.getLegalReprPhoto() == castOther.getLegalReprPhoto()) || (this
						.getLegalReprPhoto() != null
						&& castOther.getLegalReprPhoto() != null && this
						.getLegalReprPhoto().equals(
								castOther.getLegalReprPhoto())))
				&& ((this.getLegalReprNationCode() == castOther
						.getLegalReprNationCode()) || (this
						.getLegalReprNationCode() != null
						&& castOther.getLegalReprNationCode() != null && this
						.getLegalReprNationCode().equals(
								castOther.getLegalReprNationCode())))
				&& ((this.getFinRepType() == castOther.getFinRepType()) || (this
						.getFinRepType() != null
						&& castOther.getFinRepType() != null && this
						.getFinRepType().equals(castOther.getFinRepType())))
				&& ((this.getTotalAssets() == castOther.getTotalAssets()) || (this
						.getTotalAssets() != null
						&& castOther.getTotalAssets() != null && this
						.getTotalAssets().equals(castOther.getTotalAssets())))
				&& ((this.getTotalDebt() == castOther.getTotalDebt()) || (this
						.getTotalDebt() != null
						&& castOther.getTotalDebt() != null && this
						.getTotalDebt().equals(castOther.getTotalDebt())))
				&& ((this.getAnnualIncome() == castOther.getAnnualIncome()) || (this
						.getAnnualIncome() != null
						&& castOther.getAnnualIncome() != null && this
						.getAnnualIncome().equals(castOther.getAnnualIncome())))
				&& ((this.getAnnualProfit() == castOther.getAnnualProfit()) || (this
						.getAnnualProfit() != null
						&& castOther.getAnnualProfit() != null && this
						.getAnnualProfit().equals(castOther.getAnnualProfit())))
				&& ((this.getIndustryPosition() == castOther
						.getIndustryPosition()) || (this.getIndustryPosition() != null
						&& castOther.getIndustryPosition() != null && this
						.getIndustryPosition().equals(
								castOther.getIndustryPosition())))
				&& ((this.getIsStockHolder() == castOther.getIsStockHolder()) || (this
						.getIsStockHolder() != null
						&& castOther.getIsStockHolder() != null && this
						.getIsStockHolder()
						.equals(castOther.getIsStockHolder())))
				&& ((this.getHoldStockAmt() == castOther.getHoldStockAmt()) || (this
						.getHoldStockAmt() != null
						&& castOther.getHoldStockAmt() != null && this
						.getHoldStockAmt().equals(castOther.getHoldStockAmt())))
				&& ((this.getOrgAddr() == castOther.getOrgAddr()) || (this
						.getOrgAddr() != null
						&& castOther.getOrgAddr() != null && this.getOrgAddr()
						.equals(castOther.getOrgAddr())))
				&& ((this.getOrgZipcode() == castOther.getOrgZipcode()) || (this
						.getOrgZipcode() != null
						&& castOther.getOrgZipcode() != null && this
						.getOrgZipcode().equals(castOther.getOrgZipcode())))
				&& ((this.getOrgCus() == castOther.getOrgCus()) || (this
						.getOrgCus() != null
						&& castOther.getOrgCus() != null && this.getOrgCus()
						.equals(castOther.getOrgCus())))
				&& ((this.getOrgTel() == castOther.getOrgTel()) || (this
						.getOrgTel() != null
						&& castOther.getOrgTel() != null && this.getOrgTel()
						.equals(castOther.getOrgTel())))
				&& ((this.getOrgFex() == castOther.getOrgFex()) || (this
						.getOrgFex() != null
						&& castOther.getOrgFex() != null && this.getOrgFex()
						.equals(castOther.getOrgFex())))
				&& ((this.getOrgEmail() == castOther.getOrgEmail()) || (this
						.getOrgEmail() != null
						&& castOther.getOrgEmail() != null && this
						.getOrgEmail().equals(castOther.getOrgEmail())))
				&& ((this.getOrgHomepage() == castOther.getOrgHomepage()) || (this
						.getOrgHomepage() != null
						&& castOther.getOrgHomepage() != null && this
						.getOrgHomepage().equals(castOther.getOrgHomepage())))
				&& ((this.getOrgWeibo() == castOther.getOrgWeibo()) || (this
						.getOrgWeibo() != null
						&& castOther.getOrgWeibo() != null && this
						.getOrgWeibo().equals(castOther.getOrgWeibo())))
				&& ((this.getOrgWeixin() == castOther.getOrgWeixin()) || (this
						.getOrgWeixin() != null
						&& castOther.getOrgWeixin() != null && this
						.getOrgWeixin().equals(castOther.getOrgWeixin())))
				&& ((this.getLastDealingsDesc() == castOther
						.getLastDealingsDesc()) || (this.getLastDealingsDesc() != null
						&& castOther.getLastDealingsDesc() != null && this
						.getLastDealingsDesc().equals(
								castOther.getLastDealingsDesc())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())))
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
		result = 37 * result
				+ (getCustName() == null ? 0 : this.getCustName().hashCode());
		result = 37
				* result
				+ (getOrgCustType() == null ? 0 : this.getOrgCustType()
						.hashCode());
		result = 37
				* result
				+ (getChurcustype() == null ? 0 : this.getChurcustype()
						.hashCode());
		result = 37
				* result
				+ (getOrgBizCustType() == null ? 0 : this.getOrgBizCustType()
						.hashCode());
		result = 37
				* result
				+ (getHqNationCode() == null ? 0 : this.getHqNationCode()
						.hashCode());
		result = 37
				* result
				+ (getNationCode() == null ? 0 : this.getNationCode()
						.hashCode());
		result = 37 * result
				+ (getAreaCode() == null ? 0 : this.getAreaCode().hashCode());
		result = 37 * result
				+ (getOrgType() == null ? 0 : this.getOrgType().hashCode());
		result = 37
				* result
				+ (getOrgSubType() == null ? 0 : this.getOrgSubType()
						.hashCode());
		result = 37 * result
				+ (getOrgCode() == null ? 0 : this.getOrgCode().hashCode());
		result = 37
				* result
				+ (getOrgRegDate() == null ? 0 : this.getOrgRegDate()
						.hashCode());
		result = 37
				* result
				+ (getOrgExpDate() == null ? 0 : this.getOrgExpDate()
						.hashCode());
		result = 37
				* result
				+ (getOrgCodeUnit() == null ? 0 : this.getOrgCodeUnit()
						.hashCode());
		result = 37
				* result
				+ (getOrgCodeAnnDate() == null ? 0 : this.getOrgCodeAnnDate()
						.hashCode());
		result = 37 * result
				+ (getBusiLicNo() == null ? 0 : this.getBusiLicNo().hashCode());
		result = 37
				* result
				+ (getMainIndustry() == null ? 0 : this.getMainIndustry()
						.hashCode());
		result = 37
				* result
				+ (getMinorIndustry() == null ? 0 : this.getMinorIndustry()
						.hashCode());
		result = 37
				* result
				+ (getIndustryDivision() == null ? 0 : this
						.getIndustryDivision().hashCode());
		result = 37
				* result
				+ (getIndustryChar() == null ? 0 : this.getIndustryChar()
						.hashCode());
		result = 37
				* result
				+ (getEntProperty() == null ? 0 : this.getEntProperty()
						.hashCode());
		result = 37 * result
				+ (getEntScale() == null ? 0 : this.getEntScale().hashCode());
		result = 37
				* result
				+ (getEntScaleRh() == null ? 0 : this.getEntScaleRh()
						.hashCode());
		result = 37
				* result
				+ (getEntScaleCk() == null ? 0 : this.getEntScaleCk()
						.hashCode());
		result = 37
				* result
				+ (getAssetsScale() == null ? 0 : this.getAssetsScale()
						.hashCode());
		result = 37
				* result
				+ (getEmployeeScale() == null ? 0 : this.getEmployeeScale()
						.hashCode());
		result = 37
				* result
				+ (getEconomicType() == null ? 0 : this.getEconomicType()
						.hashCode());
		result = 37
				* result
				+ (getComHoldType() == null ? 0 : this.getComHoldType()
						.hashCode());
		result = 37 * result
				+ (getOrgForm() == null ? 0 : this.getOrgForm().hashCode());
		result = 37
				* result
				+ (getGovernStructure() == null ? 0 : this.getGovernStructure()
						.hashCode());
		result = 37 * result
				+ (getInCllType() == null ? 0 : this.getInCllType().hashCode());
		result = 37
				* result
				+ (getIndustryCategory() == null ? 0 : this
						.getIndustryCategory().hashCode());
		result = 37
				* result
				+ (getInvestType() == null ? 0 : this.getInvestType()
						.hashCode());
		result = 37 * result
				+ (getEntBelong() == null ? 0 : this.getEntBelong().hashCode());
		result = 37 * result
				+ (getBuildDate() == null ? 0 : this.getBuildDate().hashCode());
		result = 37 * result
				+ (getSuperDept() == null ? 0 : this.getSuperDept().hashCode());
		result = 37
				* result
				+ (getMainBusiness() == null ? 0 : this.getMainBusiness()
						.hashCode());
		result = 37
				* result
				+ (getMinorBusiness() == null ? 0 : this.getMinorBusiness()
						.hashCode());
		result = 37
				* result
				+ (getBusinessMode() == null ? 0 : this.getBusinessMode()
						.hashCode());
		result = 37
				* result
				+ (getBusiStartDate() == null ? 0 : this.getBusiStartDate()
						.hashCode());
		result = 37
				* result
				+ (getInduDeveProspect() == null ? 0 : this
						.getInduDeveProspect().hashCode());
		result = 37
				* result
				+ (getFundSource() == null ? 0 : this.getFundSource()
						.hashCode());
		result = 37 * result
				+ (getZoneCode() == null ? 0 : this.getZoneCode().hashCode());
		result = 37
				* result
				+ (getFexcPrmCode() == null ? 0 : this.getFexcPrmCode()
						.hashCode());
		result = 37
				* result
				+ (getTopCorpLevel() == null ? 0 : this.getTopCorpLevel()
						.hashCode());
		result = 37
				* result
				+ (getComSpBusiness() == null ? 0 : this.getComSpBusiness()
						.hashCode());
		result = 37
				* result
				+ (getComSpLicNo() == null ? 0 : this.getComSpLicNo()
						.hashCode());
		result = 37
				* result
				+ (getComSpDetail() == null ? 0 : this.getComSpDetail()
						.hashCode());
		result = 37
				* result
				+ (getComSpLicOrg() == null ? 0 : this.getComSpLicOrg()
						.hashCode());
		result = 37
				* result
				+ (getComSpStrDate() == null ? 0 : this.getComSpStrDate()
						.hashCode());
		result = 37
				* result
				+ (getComSpEndDate() == null ? 0 : this.getComSpEndDate()
						.hashCode());
		result = 37
				* result
				+ (getLoanCardFlag() == null ? 0 : this.getLoanCardFlag()
						.hashCode());
		result = 37
				* result
				+ (getLoanCardNo() == null ? 0 : this.getLoanCardNo()
						.hashCode());
		result = 37
				* result
				+ (getLoanCardStat() == null ? 0 : this.getLoanCardStat()
						.hashCode());
		result = 37
				* result
				+ (getLoadCardPwd() == null ? 0 : this.getLoadCardPwd()
						.hashCode());
		result = 37
				* result
				+ (getLoadCardAuditDt() == null ? 0 : this.getLoadCardAuditDt()
						.hashCode());
		result = 37
				* result
				+ (getPartnerType() == null ? 0 : this.getPartnerType()
						.hashCode());
		result = 37
				* result
				+ (getLegalReprName() == null ? 0 : this.getLegalReprName()
						.hashCode());
		result = 37
				* result
				+ (getLegalReprGender() == null ? 0 : this.getLegalReprGender()
						.hashCode());
		result = 37
				* result
				+ (getLegalReprIdentType() == null ? 0 : this
						.getLegalReprIdentType().hashCode());
		result = 37
				* result
				+ (getLegalReprIdentNo() == null ? 0 : this
						.getLegalReprIdentNo().hashCode());
		result = 37
				* result
				+ (getLegalReprTel() == null ? 0 : this.getLegalReprTel()
						.hashCode());
		result = 37
				* result
				+ (getLegalReprAddr() == null ? 0 : this.getLegalReprAddr()
						.hashCode());
		result = 37
				* result
				+ (getLegalReprPhoto() == null ? 0 : this.getLegalReprPhoto()
						.hashCode());
		result = 37
				* result
				+ (getLegalReprNationCode() == null ? 0 : this
						.getLegalReprNationCode().hashCode());
		result = 37
				* result
				+ (getFinRepType() == null ? 0 : this.getFinRepType()
						.hashCode());
		result = 37
				* result
				+ (getTotalAssets() == null ? 0 : this.getTotalAssets()
						.hashCode());
		result = 37 * result
				+ (getTotalDebt() == null ? 0 : this.getTotalDebt().hashCode());
		result = 37
				* result
				+ (getAnnualIncome() == null ? 0 : this.getAnnualIncome()
						.hashCode());
		result = 37
				* result
				+ (getAnnualProfit() == null ? 0 : this.getAnnualProfit()
						.hashCode());
		result = 37
				* result
				+ (getIndustryPosition() == null ? 0 : this
						.getIndustryPosition().hashCode());
		result = 37
				* result
				+ (getIsStockHolder() == null ? 0 : this.getIsStockHolder()
						.hashCode());
		result = 37
				* result
				+ (getHoldStockAmt() == null ? 0 : this.getHoldStockAmt()
						.hashCode());
		result = 37 * result
				+ (getOrgAddr() == null ? 0 : this.getOrgAddr().hashCode());
		result = 37
				* result
				+ (getOrgZipcode() == null ? 0 : this.getOrgZipcode()
						.hashCode());
		result = 37 * result
				+ (getOrgCus() == null ? 0 : this.getOrgCus().hashCode());
		result = 37 * result
				+ (getOrgTel() == null ? 0 : this.getOrgTel().hashCode());
		result = 37 * result
				+ (getOrgFex() == null ? 0 : this.getOrgFex().hashCode());
		result = 37 * result
				+ (getOrgEmail() == null ? 0 : this.getOrgEmail().hashCode());
		result = 37
				* result
				+ (getOrgHomepage() == null ? 0 : this.getOrgHomepage()
						.hashCode());
		result = 37 * result
				+ (getOrgWeibo() == null ? 0 : this.getOrgWeibo().hashCode());
		result = 37 * result
				+ (getOrgWeixin() == null ? 0 : this.getOrgWeixin().hashCode());
		result = 37
				* result
				+ (getLastDealingsDesc() == null ? 0 : this
						.getLastDealingsDesc().hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
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