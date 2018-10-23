package com.yuchengtech.emp.ecif.customer.entity.customerbaseorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

@Entity
@Table(name = "M_CI_ORG")
public class Orgprofile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="BUSI_START_TIME", length=10)
	private String busiStartTime;

//	@Column(name="CUST_KIND", length=20)
//	private String custKind;

	@Column(name="ECONOMIC_TYPE", length=20)
	private String economicType;

	@Column(name="ENT_BELONG", length=100)
	private String entBelong;

	@Column(name="ENT_PROPERTY", length=20)
	private String entProperty;

	@Column(name="ENT_SCALE", length=20)
	private String entScale;

	@Column(name="FUND_SOURCE", length=200)
	private String fundSource;

	@Column(name="GOVERN_STRUCTURE", length=20)
	private String governStructure;

	@Column(name="INDU_DEVE_PROSPECT", length=1000)
	private String induDeveProspect;

	@Column(name="INDUSTRY",length=20)
	private String industry;

	@Column(name="INDUSTRY_CHAR", length=20)
	private String industryChar;

	@Column(name="INDUSTRY_DIVISION", length=20)
	private String industryDivision;

	@Column(name="LOCAL_TAX", length=40)
	private String localTax;

	@Column(name="LOCK_SITUATION", length=200)
	private String lockSituation;

	@Column(name="MAIN_BUSINESS", length=1000)
	private String mainBusiness;

//	@Column(name="MAIN_PROD_SERVICE", length=200)
//	private String mainProdService;

//	@Column(name="MANAGE_APTITUDE", length=20)
//	private String manageAptitude;

//	@Column(name="MANAGE_FORM", length=20)
//	private String manageForm;

//	@Column(name="MANAGE_STAT", length=20)
//	private String manageStat;

	@Column(name="NATION_CODE", length=20)
	private String nationCode;

	@Column(name="NATIONAL_TAX", length=40)
	private String nationalTax;

	@Column(name="ORG_FORM", length=20)
	private String orgForm;

	@Column(name="ORG_TYPE", length=20)
	private String orgType;

	@Column(name="SUPER_DEPT", length=60)
	private String superDept;

//	@Column(name="TOP_AGRI_CORP_LEVEL", length=20)
//	private String topAgriCorpLevel;
//
//	@Column(name="TRADEMARK",length=20)
//	private String trademark;

//	@Column(name="UP_CORP_CERT_TYPE", length=20)
//	private String upCorpCertType;

//	@Column(name="UP_CORP_NAME", length=80)
//	private String upCorpName;
//
//	@Column(name="UP_DEPT", length=80)
//	private String upDept;

	@Column(name="MAIN_CUST")
	private String mainCust;
	
	@Column(name="MAIN_SERVICE")
	private String mainService;
	@Column(name="MAIN_PRODUCT")
	private String mainProduct;
	@Column(name="ZONE_CODE")
	private String zoneCode;
	public String getMainCust() {
		return mainCust;
	}

	public void setMainCust(String mainCust) {
		this.mainCust = mainCust;
	}

	public String getMainService() {
		return mainService;
	}

	public void setMainService(String mainService) {
		this.mainService = mainService;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getEmployeeScale() {
		return employeeScale;
	}

	public void setEmployeeScale(String employeeScale) {
		this.employeeScale = employeeScale;
	}

	public String getAssetsScale() {
		return assetsScale;
	}

	public void setAssetsScale(String assetsScale) {
		this.assetsScale = assetsScale;
	}

	@Column(name="EMPLOYEE_SCALE")
	private String employeeScale;

	@Column(name="ASSETS_SCALE")
	private String assetsScale;

	
	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getBusiStartTime() {
		return busiStartTime;
	}

	public void setBusiStartTime(String busiStartTime) {
		this.busiStartTime = busiStartTime;
	}


	public String getEconomicType() {
		return economicType;
	}

	public void setEconomicType(String economicType) {
		this.economicType = economicType;
	}

	public String getEntBelong() {
		return entBelong;
	}

	public void setEntBelong(String entBelong) {
		this.entBelong = entBelong;
	}

	public String getEntProperty() {
		return entProperty;
	}

	public void setEntProperty(String entProperty) {
		this.entProperty = entProperty;
	}

	public String getEntScale() {
		return entScale;
	}

	public void setEntScale(String entScale) {
		this.entScale = entScale;
	}

	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public String getGovernStructure() {
		return governStructure;
	}

	public void setGovernStructure(String governStructure) {
		this.governStructure = governStructure;
	}

	public String getInduDeveProspect() {
		return induDeveProspect;
	}

	public void setInduDeveProspect(String induDeveProspect) {
		this.induDeveProspect = induDeveProspect;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIndustryChar() {
		return industryChar;
	}

	public void setIndustryChar(String industryChar) {
		this.industryChar = industryChar;
	}

	public String getIndustryDivision() {
		return industryDivision;
	}

	public void setIndustryDivision(String industryDivision) {
		this.industryDivision = industryDivision;
	}

	public String getLocalTax() {
		return localTax;
	}

	public void setLocalTax(String localTax) {
		this.localTax = localTax;
	}

	public String getLockSituation() {
		return lockSituation;
	}

	public void setLockSituation(String lockSituation) {
		this.lockSituation = lockSituation;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}



	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNationalTax() {
		return nationalTax;
	}

	public void setNationalTax(String nationalTax) {
		this.nationalTax = nationalTax;
	}

	public String getOrgForm() {
		return orgForm;
	}

	public void setOrgForm(String orgForm) {
		this.orgForm = orgForm;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getSuperDept() {
		return superDept;
	}

	public void setSuperDept(String superDept) {
		this.superDept = superDept;
	}

//	public String getTopAgriCorpLevel() {
//		return topAgriCorpLevel;
//	}
//
//	public void setTopAgriCorpLevel(String topAgriCorpLevel) {
//		this.topAgriCorpLevel = topAgriCorpLevel;
//	}
//
//	public String getTrademark() {
//		return trademark;
//	}
//
//	public void setTrademark(String trademark) {
//		this.trademark = trademark;
//	}
//
//	public String getUpCorpCertType() {
//		return upCorpCertType;
//	}
//
//	public void setUpCorpCertType(String upCorpCertType) {
//		this.upCorpCertType = upCorpCertType;
//	}
//
//	public String getUpCorpName() {
//		return upCorpName;
//	}
//
//	public void setUpCorpName(String upCorpName) {
//		this.upCorpName = upCorpName;
//	}
//
//	public String getUpDept() {
//		return upDept;
//	}
//
//	public void setUpDept(String upDept) {
//		this.upDept = upDept;
//	}

}
