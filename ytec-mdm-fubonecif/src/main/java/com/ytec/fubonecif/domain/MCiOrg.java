package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiOrg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG")
public class MCiOrg implements java.io.Serializable {

	// Fields

	private String custId;
	private String custName;
	private String orgCustType;
	private String churcustype;
	private String orgBizCustType;
	private String nationCode;
	private String areaCode;
	private String orgType;
	private String orgSubType;
	private String ifOrgSubType;
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
	private String lncustp;
	private String creditCode;
	private String jointCustType;
	private String hqNationCode;
	//add by liuming 20170524
	private String loanOrgType;//组织机构类别（信贷）
	private String flagCapDtl;//组织机构类别细分（信贷）
	private String yearRate;//年化入账比例（信贷）
	private String orgState;//机构状态（信贷）
	private String basCusState;//基本户状态（信贷）
	// Constructors



	/** default constructor */
	public MCiOrg() {
	}

	/** minimal constructor */
	public MCiOrg(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiOrg(String custId, String custName, String orgCustType,
			String churcustype, String orgBizCustType, String nationCode,
			String areaCode, String orgType, String orgSubType, String ifOrgSubType, String orgCode,
			Date orgRegDate, Date orgExpDate, String orgCodeUnit,
			Date orgCodeAnnDate, String busiLicNo, String mainIndustry,
			String minorIndustry, String industryDivision, String industryChar,
			String entProperty, String entScale, String entScaleRh,
			String entScaleCk, String assetsScale, String employeeScale,
			String economicType, String comHoldType, String orgForm,
			String governStructure, String inCllType, String industryCategory,
			String investType, String entBelong, Date buildDate,
			String superDept, String mainBusiness, String minorBusiness,
			String businessMode, Date busiStartDate, String induDeveProspect,
			String fundSource, String zoneCode, String fexcPrmCode,
			String topCorpLevel, String comSpBusiness, String comSpLicNo,
			String comSpDetail, String comSpLicOrg, Date comSpStrDate,
			Date comSpEndDate, String loanCardFlag, String loanCardNo,
			String loanCardStat, String loadCardPwd, Date loadCardAuditDt,
			String partnerType, String legalReprName, String legalReprGender,
			String legalReprIdentType, String legalReprIdentNo,
			String legalReprTel, String legalReprAddr, String legalReprPhoto,
			String legalReprNationCode, String finRepType, Double totalAssets,
			Double totalDebt, Double annualIncome, Double annualProfit,
			String industryPosition, String isStockHolder, Double holdStockAmt,
			String orgAddr, String orgZipcode, String orgCus, String orgTel,
			String orgFex, String orgEmail, String orgHomepage,
			String orgWeibo, String orgWeixin, String lastDealingsDesc,
			String remark, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo,String lncustp,String jointCustType,String creditCode,String hqNationCode,
			String loanOrgType,String flagCapDtl,String yearRate,String orgState,String basCusState) {
		this.custId = custId;
		this.custName = custName;
		this.orgCustType = orgCustType;
		this.churcustype = churcustype;
		this.orgBizCustType = orgBizCustType;
		this.nationCode = nationCode;
		this.areaCode = areaCode;
		this.orgType = orgType;
		this.orgSubType = orgSubType;
		this.ifOrgSubType=ifOrgSubType;
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
		this.lncustp = lncustp;
		this.creditCode = creditCode;
		this.jointCustType = jointCustType;
		this.hqNationCode = hqNationCode;
		//add by liuming 20170524
		this.loanOrgType = loanOrgType;
		this.flagCapDtl = flagCapDtl;
		this.yearRate = yearRate;
		this.orgState = orgState;
		this.basCusState = basCusState;
		this.jointCustType = jointCustType;
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
    
	@Column(name = "IF_ORG_SUB_TYPE", length = 1)
	public String getIfOrgSubType() {
		return ifOrgSubType;
	}

	public void setIfOrgSubType(String ifOrgSubType) {
		this.ifOrgSubType = ifOrgSubType;
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

	@Column(name = "MAIN_BUSINESS", length = 200)
	public String getMainBusiness() {
		return this.mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	@Column(name = "MINOR_BUSINESS", length = 200)
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

	@Column(name = "ORG_TEL", length = 20)
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

	@Column(name = "ORG_EMAIL", length = 40)
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

	@Column(name = "LNCUSTP", length = 20)
	public String getLncustp() {
		return lncustp;
	}

	public void setLncustp(String lncustp) {
		this.lncustp = lncustp;
	}

	@Column(name = "CREDIT_CODE", length = 35)
	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	@Column(name = "JOINT_CUST_TYPE", length = 20)
	public String getJointCustType() {
		return jointCustType;
	}

	public void setJointCustType(String jointCustType) {
		this.jointCustType = jointCustType;
	}

	@Column(name = "HQ_NATION_CODE", length = 20)
	public String getHqNationCode() {
		return hqNationCode;
	}

	public void setHqNationCode(String hqNationCode) {
		this.hqNationCode = hqNationCode;
	}
	
	//add by liuming 20170524 
	@Column(name = "LOAN_ORG_TYPE", length = 20)
	public String getLoanOrgType() {
		return loanOrgType;
	}

	public void setLoanOrgType(String loanOrgType) {
		this.loanOrgType = loanOrgType;
	}
	
	@Column(name = "FLAG_CAP_DTL", length = 20)
	public String getFlagCapDtl() {
		return flagCapDtl;
	}

	public void setFlagCapDtl(String flagCapDtl) {
		this.flagCapDtl = flagCapDtl;
	}
	
	@Column(name = "YEAR_RATE", length = 20)
	public String getYearRate() {
		return yearRate;
	}

	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
    
	@Column(name = "ORG_STATE", length = 10)
	public String getOrgState() {
		return orgState;
	}

	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}
    
	@Column(name = "BAS_CUS_STATE", length = 10)
	public String getBasCusState() {
		return basCusState;
	}

	public void setBasCusState(String basCusState) {
		this.basCusState = basCusState;
	}	
}