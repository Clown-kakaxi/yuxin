package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG")
public class HMCiOrg implements java.io.Serializable {

	// Fields

	private HMCiOrgId id;

	// Constructors

	/** default constructor */
	public HMCiOrg() {
	}

	/** full constructor */
	public HMCiOrg(HMCiOrgId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "custName", column = @Column(name = "CUST_NAME", length = 80)),
			@AttributeOverride(name = "orgCustType", column = @Column(name = "ORG_CUST_TYPE", length = 20)),
			@AttributeOverride(name = "churcustype", column = @Column(name = "CHURCUSTYPE", length = 20)),
			@AttributeOverride(name = "orgBizCustType", column = @Column(name = "ORG_BIZ_CUST_TYPE", length = 20)),
			@AttributeOverride(name = "hqNationCode", column = @Column(name = "HQ_NATION_CODE", length = 20)),
			@AttributeOverride(name = "nationCode", column = @Column(name = "NATION_CODE", length = 20)),
			@AttributeOverride(name = "areaCode", column = @Column(name = "AREA_CODE", length = 20)),
			@AttributeOverride(name = "orgType", column = @Column(name = "ORG_TYPE", length = 20)),
			@AttributeOverride(name = "orgSubType", column = @Column(name = "ORG_SUB_TYPE", length = 20)),
			@AttributeOverride(name = "orgCode", column = @Column(name = "ORG_CODE", length = 20)),
			@AttributeOverride(name = "orgRegDate", column = @Column(name = "ORG_REG_DATE", length = 7)),
			@AttributeOverride(name = "orgExpDate", column = @Column(name = "ORG_EXP_DATE", length = 7)),
			@AttributeOverride(name = "orgCodeUnit", column = @Column(name = "ORG_CODE_UNIT", length = 60)),
			@AttributeOverride(name = "orgCodeAnnDate", column = @Column(name = "ORG_CODE_ANN_DATE", length = 7)),
			@AttributeOverride(name = "busiLicNo", column = @Column(name = "BUSI_LIC_NO", length = 32)),
			@AttributeOverride(name = "mainIndustry", column = @Column(name = "MAIN_INDUSTRY", length = 20)),
			@AttributeOverride(name = "minorIndustry", column = @Column(name = "MINOR_INDUSTRY", length = 20)),
			@AttributeOverride(name = "industryDivision", column = @Column(name = "INDUSTRY_DIVISION", length = 20)),
			@AttributeOverride(name = "industryChar", column = @Column(name = "INDUSTRY_CHAR", length = 20)),
			@AttributeOverride(name = "entProperty", column = @Column(name = "ENT_PROPERTY", length = 20)),
			@AttributeOverride(name = "entScale", column = @Column(name = "ENT_SCALE", length = 20)),
			@AttributeOverride(name = "entScaleRh", column = @Column(name = "ENT_SCALE_RH", length = 20)),
			@AttributeOverride(name = "entScaleCk", column = @Column(name = "ENT_SCALE_CK", length = 20)),
			@AttributeOverride(name = "assetsScale", column = @Column(name = "ASSETS_SCALE", length = 20)),
			@AttributeOverride(name = "employeeScale", column = @Column(name = "EMPLOYEE_SCALE", length = 20)),
			@AttributeOverride(name = "economicType", column = @Column(name = "ECONOMIC_TYPE", length = 20)),
			@AttributeOverride(name = "comHoldType", column = @Column(name = "COM_HOLD_TYPE", length = 20)),
			@AttributeOverride(name = "orgForm", column = @Column(name = "ORG_FORM", length = 20)),
			@AttributeOverride(name = "governStructure", column = @Column(name = "GOVERN_STRUCTURE", length = 20)),
			@AttributeOverride(name = "inCllType", column = @Column(name = "IN_CLL_TYPE", length = 20)),
			@AttributeOverride(name = "industryCategory", column = @Column(name = "INDUSTRY_CATEGORY", length = 20)),
			@AttributeOverride(name = "investType", column = @Column(name = "INVEST_TYPE", length = 20)),
			@AttributeOverride(name = "entBelong", column = @Column(name = "ENT_BELONG", length = 100)),
			@AttributeOverride(name = "buildDate", column = @Column(name = "BUILD_DATE", length = 7)),
			@AttributeOverride(name = "superDept", column = @Column(name = "SUPER_DEPT", length = 60)),
			@AttributeOverride(name = "mainBusiness", column = @Column(name = "MAIN_BUSINESS", length = 500)),
			@AttributeOverride(name = "minorBusiness", column = @Column(name = "MINOR_BUSINESS", length = 500)),
			@AttributeOverride(name = "businessMode", column = @Column(name = "BUSINESS_MODE", length = 20)),
			@AttributeOverride(name = "busiStartDate", column = @Column(name = "BUSI_START_DATE", length = 7)),
			@AttributeOverride(name = "induDeveProspect", column = @Column(name = "INDU_DEVE_PROSPECT", length = 20)),
			@AttributeOverride(name = "fundSource", column = @Column(name = "FUND_SOURCE", length = 200)),
			@AttributeOverride(name = "zoneCode", column = @Column(name = "ZONE_CODE", length = 20)),
			@AttributeOverride(name = "fexcPrmCode", column = @Column(name = "FEXC_PRM_CODE", length = 30)),
			@AttributeOverride(name = "topCorpLevel", column = @Column(name = "TOP_CORP_LEVEL", length = 20)),
			@AttributeOverride(name = "comSpBusiness", column = @Column(name = "COM_SP_BUSINESS", length = 1)),
			@AttributeOverride(name = "comSpLicNo", column = @Column(name = "COM_SP_LIC_NO", length = 80)),
			@AttributeOverride(name = "comSpDetail", column = @Column(name = "COM_SP_DETAIL", length = 80)),
			@AttributeOverride(name = "comSpLicOrg", column = @Column(name = "COM_SP_LIC_ORG", length = 80)),
			@AttributeOverride(name = "comSpStrDate", column = @Column(name = "COM_SP_STR_DATE", length = 7)),
			@AttributeOverride(name = "comSpEndDate", column = @Column(name = "COM_SP_END_DATE", length = 7)),
			@AttributeOverride(name = "loanCardFlag", column = @Column(name = "LOAN_CARD_FLAG", length = 1)),
			@AttributeOverride(name = "loanCardNo", column = @Column(name = "LOAN_CARD_NO", length = 32)),
			@AttributeOverride(name = "loanCardStat", column = @Column(name = "LOAN_CARD_STAT", length = 20)),
			@AttributeOverride(name = "loadCardPwd", column = @Column(name = "LOAD_CARD_PWD")),
			@AttributeOverride(name = "loadCardAuditDt", column = @Column(name = "LOAD_CARD_AUDIT_DT", length = 7)),
			@AttributeOverride(name = "partnerType", column = @Column(name = "PARTNER_TYPE", length = 20)),
			@AttributeOverride(name = "legalReprName", column = @Column(name = "LEGAL_REPR_NAME", length = 80)),
			@AttributeOverride(name = "legalReprGender", column = @Column(name = "LEGAL_REPR_GENDER", length = 20)),
			@AttributeOverride(name = "legalReprIdentType", column = @Column(name = "LEGAL_REPR_IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "legalReprIdentNo", column = @Column(name = "LEGAL_REPR_IDENT_NO", length = 32)),
			@AttributeOverride(name = "legalReprTel", column = @Column(name = "LEGAL_REPR_TEL", length = 20)),
			@AttributeOverride(name = "legalReprAddr", column = @Column(name = "LEGAL_REPR_ADDR", length = 200)),
			@AttributeOverride(name = "legalReprPhoto", column = @Column(name = "LEGAL_REPR_PHOTO")),
			@AttributeOverride(name = "legalReprNationCode", column = @Column(name = "LEGAL_REPR_NATION_CODE", length = 20)),
			@AttributeOverride(name = "finRepType", column = @Column(name = "FIN_REP_TYPE", length = 20)),
			@AttributeOverride(name = "totalAssets", column = @Column(name = "TOTAL_ASSETS", precision = 17)),
			@AttributeOverride(name = "totalDebt", column = @Column(name = "TOTAL_DEBT", precision = 17)),
			@AttributeOverride(name = "annualIncome", column = @Column(name = "ANNUAL_INCOME", precision = 17)),
			@AttributeOverride(name = "annualProfit", column = @Column(name = "ANNUAL_PROFIT", precision = 17)),
			@AttributeOverride(name = "industryPosition", column = @Column(name = "INDUSTRY_POSITION", length = 80)),
			@AttributeOverride(name = "isStockHolder", column = @Column(name = "IS_STOCK_HOLDER", length = 1)),
			@AttributeOverride(name = "holdStockAmt", column = @Column(name = "HOLD_STOCK_AMT", precision = 17)),
			@AttributeOverride(name = "orgAddr", column = @Column(name = "ORG_ADDR", length = 200)),
			@AttributeOverride(name = "orgZipcode", column = @Column(name = "ORG_ZIPCODE", length = 20)),
			@AttributeOverride(name = "orgCus", column = @Column(name = "ORG_CUS", length = 100)),
			@AttributeOverride(name = "orgTel", column = @Column(name = "ORG_TEL", length = 40)),
			@AttributeOverride(name = "orgFex", column = @Column(name = "ORG_FEX", length = 20)),
			@AttributeOverride(name = "orgEmail", column = @Column(name = "ORG_EMAIL", length = 50)),
			@AttributeOverride(name = "orgHomepage", column = @Column(name = "ORG_HOMEPAGE", length = 100)),
			@AttributeOverride(name = "orgWeibo", column = @Column(name = "ORG_WEIBO", length = 100)),
			@AttributeOverride(name = "orgWeixin", column = @Column(name = "ORG_WEIXIN", length = 100)),
			@AttributeOverride(name = "lastDealingsDesc", column = @Column(name = "LAST_DEALINGS_DESC", length = 200)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM")),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgId getId() {
		return this.id;
	}

	public void setId(HMCiOrgId id) {
		this.id = id;
	}

}