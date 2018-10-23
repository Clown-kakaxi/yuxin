package com.yuchengtech.bcrm.customer.customerView.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 这个模型用于全行客户查询  非授信页面的权页面数据接收    保存时需要各个单独数处理
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CUSTOMER")
public class FsxWholeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String 	custId	;   	//	客户号
	private String 	custName;   	//	中文名
	private String 	enName	;   	//	英文户名
	private String 	custType;   	//	客户类型
	
	private String 	identId;   		//	证件类型1ID
	private String 	identType;  	//	证件类型
	private String 	identNo;   		//	证件号码
	@Temporal(TemporalType.DATE)
	private Date 	identExpiredDate;   	//证件1失效日期
	
	private String 	identId1;   	//	证件类型2ID
	private String 	identType1;   	//	证件类型2
	private String 	identNo1;   	//	证件号码2
	@Temporal(TemporalType.DATE)
	private Date 	identExpiredDate1;   //	证件2失效日期
	
	private String 	gender;   		//	个人性别
	@Temporal(TemporalType.DATE)			
	private Date 	birthday;   	//	公司成立日/个人生日
	private String 	citizenship	;   //	国籍
	
	
	private String 	vipCustGrade;   	//	贵宾卡等级
	private String 	riskCustGrade;    	//	风险客户等级
	private String 	usaTaxFlag;   		//	是否美国纳税人
	private String 	usaTaxIdenNo;   	//	美国纳税人识别号
	private String 	birthlocale;   		//	出生地
	private String 	email;   			//	电子邮件地址
	private String 	inoutFlag;   		//	境内/外标志
	private String 	arCustFlag;   		//	是否AR客户标志
	private String 	riskNationCode;   	//	国别风险国别代码
	private String 	rate;   			//	国别风险准备金率
	private String  unitFex;			//	是否传真客户标志
	private String  isFaxTransCust;		//	是否传真客户标志
	
	private String 	mgrKeyId;   		//	客户经理主键ID
	private String 	mgrId;   			//	客户经理编号
	private String 	mgrName;   			//	客户经理名称
	@Temporal(TemporalType.DATE)	
	private Date  effectDate;			//客户经理生效日
	
	private String 	basicAcctBankNo;    //	开户行编号
	private String 	basicAcctBankName;  //	开户行
	@Temporal(TemporalType.DATE)			
	private Date	basicAcctOpenDate; 	//客户资料开立日
	private String 	isSendEcomstatFlag; //	是否发送综合对账单标志
	private String orgSubType;
	private String inCllType;
	private String orgType;
	private String registerNationCode;
	
	private String  vipFlag;               
	private String  cusBankRel;            
	private String  perCustType;           
	private String  personalName;          
	private String  loanCardNo;            
	private String  bankDuty;              
	private String  holdStockAmt;          
	private String  nationality;           
	private String  nativeplace;           
	private String  hukouPlace;           
	private String  politicalFace;         
	private String  highestSchooling;      
	private String  highestDegree;         
	private String  health;   
	private String  mateinfoKeyId;
	private String  poMarriage;            
	private String  poIdentType;          
	private String  poCustIdMate;          
	private String  poWorkUnit;            
	private String  poJobTitle;            
	private String  poOfficeTel;  
	@Temporal(TemporalType.DATE)
	private Date  poWorkStartDate;       
	private String  poMarrCertNo;          
	private String  poIdentNo;             
	private String  poCareer;              
	private String  poDuty;                
	private String  poMobile;              
	private String  poAnnualIncome;        
	private String  cusCorpRel;  
	@Temporal(TemporalType.DATE)
	private Date  firstLoanDate;         
	private String  holdAcct;              
	private String  foreignPassportFlag;   
	private String  custGrade;   
	@Temporal(TemporalType.DATE)
	private Date  evaluateDate;          
	private String  postAddr;              
	private String  postZipcode;           
	private String  homeAddr;              
	private String  residence;             
	private String  homeZipcode;           
	private String  homeTel;               
	private String  mobilePhone;           
	private String  careerType;            
	private String  unitName;              
	private String  unitChar;              
	private String  profession;            
	private String  unitAddr;              
	private String  unitZipcode;           
	private String  unitTel;               
	private String  cntName;    
	@Temporal(TemporalType.DATE)
	private Date  careerStartDate;       
	private String  duty;                  
	private String  careerTitle;           
	private String  salaryAcctBank;        
	private String  salaryAcctNo;          
	private String  resume;                
	private String  belongOrg;             
	private String  belongOrgName;         
	private String  orgKeyId;      

	private String shortName;
	private String arCustType;
	private String loanCustRank;
	private String orgCustType;
	private String nationCode;
	private String entProperty;
	private String investType;
	private String comHoldType;
	private String mainIndustry;
	private String industryCategory;
	private String employeeScale;
	private String entBelong;
	private String entScale;
	private String entScaleRh;
	@Temporal(TemporalType.DATE)
	private Date buildDate;
	private String orgCode;
	@Temporal(TemporalType.DATE)
	private Date orgExpDate;
	@Temporal(TemporalType.DATE)
	private Date orgCodeAnnDate;
	@Temporal(TemporalType.DATE)
	private Date orgRegDate;
	private String orgCodeUnit;
	private String superDept;
	private String minorBusiness;
	private String mainBusiness;
	private String comSpBusiness;
	private String loanCardFlag;
	private String loanCardStat;
	private String loadCardPwd;
	@Temporal(TemporalType.DATE)
	private Date loadCardAuditDt;
	private String legalReprIdentType;
	private String legalReprIdentNo;
	private String legalReprName;
	private String orgAddr;
	private String orgZipcode;
	private String orgFex;
	private String orgHomepage;
	private String orgTel;
	private String orgEmail;
	private String isBuildNew;
	private String facilityMain;
	private String prodCapacity;
	private String topCorpLevel;
	private String compOrg;
	private String finRepType;
	private String isNotLocalEnt;
	private String isRuralCorp;
	private String isListedCorp;
	private String hasIeRight;
	private String isPrepEnt;
	private String isAreaImpEnt;
	private String isNtnalMacroCtrl;
	private String isHighRiskPoll;
	private String isSteelEnt;
	private String registerKeyId;
	private String registerNo;
	@Temporal(TemporalType.DATE)
	private Date registerDate;
	private String regOrg;
	private String apprDocNo;
	private String registerAddr;
	private String registerCapital;
	private String registerType;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Temporal(TemporalType.DATE)
	private Date auditEndDate;
	private String apprOrg;
	private String registerArea;
	private String registerEnAddr;
	private String registerCapitalCurr;
	private String identRegNo;
	private String identRegOrg;
	@Temporal(TemporalType.DATE)
	private Date idenRegDate;
	private String identValidPeriod;
	private String mainProduct;
	private String workFieldArea;
	private String manageStat;
	private String workFieldOwnership;
	private String legalCustId;
	private String legalResume;
	@Temporal(TemporalType.DATE)
	private Date signStartDate;
	@Temporal(TemporalType.DATE)
	private Date signEndDate;
	private String poName;
	private String indivCusId;
	private String controlIdentType;
	private String controlIdentNo;
	private String controlCustId;
	private String controlName;
	@Temporal(TemporalType.DATE)
	private Date controlSignStartDate;
	@Temporal(TemporalType.DATE)
	private Date controlSignEndDate;
	@Temporal(TemporalType.DATE)
	private Date authStartDate;
	@Temporal(TemporalType.DATE)
	private Date authEndDate;
	private String controlIndivCusId;
	private String controlPoIdentNo;
	private String controlPoName;
	private String controlResume;
	private String cwfzPerson;
	private String cwlxPerson;
	private String cwlxMobile;
	
	private String taxRegId;
	private String taxRegistrationNo;
	@Temporal(TemporalType.DATE)
	private Date taxIdentExpiredDate;
	@Temporal(TemporalType.DATE)
	private Date legalExpiredDate;
	private String identModifiedTime;
	private String saleCcy;
	private String saleAmt;
	private String entScaleCk;
	private String remark;
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTaxRegId() {
		return taxRegId;
	}
	public void setTaxRegId(String taxRegId) {
		this.taxRegId = taxRegId;
	}
	public String getTaxRegistrationNo() {
		return taxRegistrationNo;
	}
	public void setTaxRegistrationNo(String taxRegistrationNo) {
		this.taxRegistrationNo = taxRegistrationNo;
	}
	public Date getTaxIdentExpiredDate() {
		return taxIdentExpiredDate;
	}
	public void setTaxIdentExpiredDate(Date taxIdentExpiredDate) {
		this.taxIdentExpiredDate = taxIdentExpiredDate;
	}
	public Date getLegalExpiredDate() {
		return legalExpiredDate;
	}
	public void setLegalExpiredDate(Date legalExpiredDate) {
		this.legalExpiredDate = legalExpiredDate;
	}
	public String getIdentModifiedTime() {
		return identModifiedTime;
	}
	public void setIdentModifiedTime(String identModifiedTime) {
		this.identModifiedTime = identModifiedTime;
	}
	public String getSaleCcy() {
		return saleCcy;
	}
	public void setSaleCcy(String saleCcy) {
		this.saleCcy = saleCcy;
	}
	public String getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(String saleAmt) {
		this.saleAmt = saleAmt;
	}
	public String getEntScaleCk() {
		return entScaleCk;
	}
	public void setEntScaleCk(String entScaleCk) {
		this.entScaleCk = entScaleCk;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getIdentId() {
		return identId;
	}
	public void setIdentId(String identId) {
		this.identId = identId;
	}
	public String getIdentType() {
		return identType;
	}
	public void setIdentType(String identType) {
		this.identType = identType;
	}
	public String getIdentNo() {
		return identNo;
	}
	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}
	public Date getIdentExpiredDate() {
		return identExpiredDate;
	}
	public void setIdentExpiredDate(Date identExpiredDate) {
		this.identExpiredDate = identExpiredDate;
	}
	public String getIdentId1() {
		return identId1;
	}
	public void setIdentId1(String identId1) {
		this.identId1 = identId1;
	}
	public String getIdentType1() {
		return identType1;
	}
	public void setIdentType1(String identType1) {
		this.identType1 = identType1;
	}
	public String getIdentNo1() {
		return identNo1;
	}
	public void setIdentNo1(String identNo1) {
		this.identNo1 = identNo1;
	}
	public Date getIdentExpiredDate1() {
		return identExpiredDate1;
	}
	public void setIdentExpiredDate1(Date identExpiredDate1) {
		this.identExpiredDate1 = identExpiredDate1;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getCitizenship() {
		return citizenship;
	}
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	public String getVipCustGrade() {
		return vipCustGrade;
	}
	public void setVipCustGrade(String vipCustGrade) {
		this.vipCustGrade = vipCustGrade;
	}
	public String getRiskCustGrade() {
		return riskCustGrade;
	}
	public void setRiskCustGrade(String riskCustGrade) {
		this.riskCustGrade = riskCustGrade;
	}
	public String getUsaTaxFlag() {
		return usaTaxFlag;
	}
	public void setUsaTaxFlag(String usaTaxFlag) {
		this.usaTaxFlag = usaTaxFlag;
	}
	public String getUsaTaxIdenNo() {
		return usaTaxIdenNo;
	}
	public void setUsaTaxIdenNo(String usaTaxIdenNo) {
		this.usaTaxIdenNo = usaTaxIdenNo;
	}
	public String getBirthlocale() {
		return birthlocale;
	}
	public void setBirthlocale(String birthlocale) {
		this.birthlocale = birthlocale;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInoutFlag() {
		return inoutFlag;
	}
	public void setInoutFlag(String inoutFlag) {
		this.inoutFlag = inoutFlag;
	}
	public String getArCustFlag() {
		return arCustFlag;
	}
	public void setArCustFlag(String arCustFlag) {
		this.arCustFlag = arCustFlag;
	}
	public String getRiskNationCode() {
		return riskNationCode;
	}
	public void setRiskNationCode(String riskNationCode) {
		this.riskNationCode = riskNationCode;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getMgrKeyId() {
		return mgrKeyId;
	}
	public void setMgrKeyId(String mgrKeyId) {
		this.mgrKeyId = mgrKeyId;
	}
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	public String getMgrName() {
		return mgrName;
	}
	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}
	public Date getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	public String getBasicAcctBankNo() {
		return basicAcctBankNo;
	}
	public void setBasicAcctBankNo(String basicAcctBankNo) {
		this.basicAcctBankNo = basicAcctBankNo;
	}
	public String getBasicAcctBankName() {
		return basicAcctBankName;
	}
	public void setBasicAcctBankName(String basicAcctBankName) {
		this.basicAcctBankName = basicAcctBankName;
	}
	public Date getBasicAcctOpenDate() {
		return basicAcctOpenDate;
	}
	public void setBasicAcctOpenDate(Date basicAcctOpenDate) {
		this.basicAcctOpenDate = basicAcctOpenDate;
	}
	public String getIsSendEcomstatFlag() {
		return isSendEcomstatFlag;
	}
	public void setIsSendEcomstatFlag(String isSendEcomstatFlag) {
		this.isSendEcomstatFlag = isSendEcomstatFlag;
	}
	public String getUnitFex() {
		return unitFex;
	}
	public void setUnitFex(String unitFex) {
		this.unitFex = unitFex;
	}
	public String getIsFaxTransCust() {
		return isFaxTransCust;
	}
	public void setIsFaxTransCust(String isFaxTransCust) {
		this.isFaxTransCust = isFaxTransCust;
	}
	public String getVipFlag() {
		return vipFlag;
	}
	public void setVipFlag(String vipFlag) {
		this.vipFlag = vipFlag;
	}
	public String getCusBankRel() {
		return cusBankRel;
	}
	public void setCusBankRel(String cusBankRel) {
		this.cusBankRel = cusBankRel;
	}
	public String getPerCustType() {
		return perCustType;
	}
	public void setPerCustType(String perCustType) {
		this.perCustType = perCustType;
	}
	public String getPersonalName() {
		return personalName;
	}
	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}
	public String getLoanCardNo() {
		return loanCardNo;
	}
	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}
	public String getBankDuty() {
		return bankDuty;
	}
	public void setBankDuty(String bankDuty) {
		this.bankDuty = bankDuty;
	}
	public String getHoldStockAmt() {
		return holdStockAmt;
	}
	public void setHoldStockAmt(String holdStockAmt) {
		this.holdStockAmt = holdStockAmt;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getNativeplace() {
		return nativeplace;
	}
	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}
	
	public String getHukouPlace() {
		return hukouPlace;
	}
	public void setHukouPlace(String hukouPlace) {
		this.hukouPlace = hukouPlace;
	}
	public String getPoliticalFace() {
		return politicalFace;
	}
	public void setPoliticalFace(String politicalFace) {
		this.politicalFace = politicalFace;
	}
	public String getHighestSchooling() {
		return highestSchooling;
	}
	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}
	public String getHighestDegree() {
		return highestDegree;
	}
	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getPoMarriage() {
		return poMarriage;
	}
	public void setPoMarriage(String poMarriage) {
		this.poMarriage = poMarriage;
	}
	public String getPoIdentType() {
		return poIdentType;
	}
	public void setPoIdent_type(String poIdentType) {
		this.poIdentType = poIdentType;
	}
	public String getPoCustIdMate() {
		return poCustIdMate;
	}
	public void setPoIdentType(String poIdentType) {
		this.poIdentType = poIdentType;
	}
	public void setPoCustIdMate(String poCustIdMate) {
		this.poCustIdMate = poCustIdMate;
	}
	public String getPoWorkUnit() {
		return poWorkUnit;
	}
	public void setPoWorkUnit(String poWorkUnit) {
		this.poWorkUnit = poWorkUnit;
	}
	public String getPoJobTitle() {
		return poJobTitle;
	}
	public void setPoJobTitle(String poJobTitle) {
		this.poJobTitle = poJobTitle;
	}
	public String getPoOfficeTel() {
		return poOfficeTel;
	}
	public void setPoOfficeTel(String poOfficeTel) {
		this.poOfficeTel = poOfficeTel;
	}
	public Date getPoWorkStartDate() {
		return poWorkStartDate;
	}
	public void setPoWorkStartDate(Date poWorkStartDate) {
		this.poWorkStartDate = poWorkStartDate;
	}
	public String getPoMarrCertNo() {
		return poMarrCertNo;
	}
	public void setPoMarrCertNo(String poMarrCertNo) {
		this.poMarrCertNo = poMarrCertNo;
	}
	public String getPoIdentNo() {
		return poIdentNo;
	}
	public void setPoIdentNo(String poIdentNo) {
		this.poIdentNo = poIdentNo;
	}
	public String getPoCareer() {
		return poCareer;
	}
	public void setPoCareer(String poCareer) {
		this.poCareer = poCareer;
	}
	public String getPoDuty() {
		return poDuty;
	}
	public void setPoDuty(String poDuty) {
		this.poDuty = poDuty;
	}
	public String getPoMobile() {
		return poMobile;
	}
	public void setPoMobile(String poMobile) {
		this.poMobile = poMobile;
	}
	public String getPoAnnualIncome() {
		return poAnnualIncome;
	}
	public void setPoAnnualIncome(String poAnnualIncome) {
		this.poAnnualIncome = poAnnualIncome;
	}
	public String getCusCorpRel() {
		return cusCorpRel;
	}
	public void setCusCorpRel(String cusCorpRel) {
		this.cusCorpRel = cusCorpRel;
	}
	public Date getFirstLoanDate() {
		return firstLoanDate;
	}
	public void setFirstLoanDate(Date firstLoanDate) {
		this.firstLoanDate = firstLoanDate;
	}
	public String getHoldAcct() {
		return holdAcct;
	}
	public void setHoldAcct(String holdAcct) {
		this.holdAcct = holdAcct;
	}
	public String getForeignPassportFlag() {
		return foreignPassportFlag;
	}
	public void setForeignPassportFlag(String foreignPassportFlag) {
		this.foreignPassportFlag = foreignPassportFlag;
	}
	public String getCustGrade() {
		return custGrade;
	}
	public void setCustGrade(String custGrade) {
		this.custGrade = custGrade;
	}
	public Date getEvaluateDate() {
		return evaluateDate;
	}
	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}
	public String getPostAddr() {
		return postAddr;
	}
	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}
	public String getPostZipcode() {
		return postZipcode;
	}
	public void setPostZipcode(String postZipcode) {
		this.postZipcode = postZipcode;
	}
	public String getHomeAddr() {
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getHomeZipcode() {
		return homeZipcode;
	}
	public void setHomeZipcode(String homeZipcode) {
		this.homeZipcode = homeZipcode;
	}
	public String getHomeTel() {
		return homeTel;
	}
	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getCareerType() {
		return careerType;
	}
	public void setCareerType(String careerType) {
		this.careerType = careerType;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitChar() {
		return unitChar;
	}
	public void setUnitChar(String unitChar) {
		this.unitChar = unitChar;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getUnitAddr() {
		return unitAddr;
	}
	public void setUnitAddr(String unitAddr) {
		this.unitAddr = unitAddr;
	}
	public String getUnitZipcode() {
		return unitZipcode;
	}
	public void setUnitZipcode(String unitZipcode) {
		this.unitZipcode = unitZipcode;
	}
	public String getUnitTel() {
		return unitTel;
	}
	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
	}
	public String getCntName() {
		return cntName;
	}
	public void setCntName(String cntName) {
		this.cntName = cntName;
	}
	public Date getCareerStartDate() {
		return careerStartDate;
	}
	public void setCareerStartDate(Date careerStartDate) {
		this.careerStartDate = careerStartDate;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getCareerTitle() {
		return careerTitle;
	}
	public void setCareerTitle(String careerTitle) {
		this.careerTitle = careerTitle;
	}
	public String getSalaryAcctBank() {
		return salaryAcctBank;
	}
	public void setSalaryAcctBank(String salaryAcctBank) {
		this.salaryAcctBank = salaryAcctBank;
	}
	public String getSalaryAcctNo() {
		return salaryAcctNo;
	}
	public void setSalaryAcctNo(String salaryAcctNo) {
		this.salaryAcctNo = salaryAcctNo;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getBelongOrg() {
		return belongOrg;
	}
	public void setBelongOrg(String belongOrg) {
		this.belongOrg = belongOrg;
	}
	public String getBelongOrgName() {
		return belongOrgName;
	}
	public void setBelongOrgName(String belongOrgName) {
		this.belongOrgName = belongOrgName;
	}
	public String getOrgKeyId() {
		return orgKeyId;
	}
	public void setOrgKeyId(String orgKeyId) {
		this.orgKeyId = orgKeyId;
	}
	public String getMateinfoKeyId() {
		return mateinfoKeyId;
	}
	public void setMateinfoKeyId(String mateinfoKeyId) {
		this.mateinfoKeyId = mateinfoKeyId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getArCustType() {
		return arCustType;
	}
	public void setArCustType(String arCustType) {
		this.arCustType = arCustType;
	}
	public String getLoanCustRank() {
		return loanCustRank;
	}
	public void setLoanCustRank(String loanCustRank) {
		this.loanCustRank = loanCustRank;
	}
	public String getOrgCustType() {
		return orgCustType;
	}
	public void setOrgCustType(String orgCustType) {
		this.orgCustType = orgCustType;
	}
	public String getNationCode() {
		return nationCode;
	}
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
	public String getEntProperty() {
		return entProperty;
	}
	public void setEntProperty(String entProperty) {
		this.entProperty = entProperty;
	}
	public String getInvestType() {
		return investType;
	}
	public void setInvestType(String investType) {
		this.investType = investType;
	}
	public String getComHoldType() {
		return comHoldType;
	}
	public void setComHoldType(String comHoldType) {
		this.comHoldType = comHoldType;
	}
	public String getMainIndustry() {
		return mainIndustry;
	}
	public void setMainIndustry(String mainIndustry) {
		this.mainIndustry = mainIndustry;
	}
	public String getIndustryCategory() {
		return industryCategory;
	}
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}
	public String getEmployeeScale() {
		return employeeScale;
	}
	public void setEmployeeScale(String employeeScale) {
		this.employeeScale = employeeScale;
	}
	public String getEntBelong() {
		return entBelong;
	}
	public void setEntBelong(String entBelong) {
		this.entBelong = entBelong;
	}
	public String getEntScale() {
		return entScale;
	}
	public void setEntScale(String entScale) {
		this.entScale = entScale;
	}
	public String getEntScaleRh() {
		return entScaleRh;
	}
	public void setEntScaleRh(String entScaleRh) {
		this.entScaleRh = entScaleRh;
	}
	public Date getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Date getOrgExpDate() {
		return orgExpDate;
	}
	public void setOrgExpDate(Date orgExpDate) {
		this.orgExpDate = orgExpDate;
	}
	public Date getOrgCodeAnnDate() {
		return orgCodeAnnDate;
	}
	public void setOrgCodeAnnDate(Date orgCodeAnnDate) {
		this.orgCodeAnnDate = orgCodeAnnDate;
	}
	public Date getOrgRegDate() {
		return orgRegDate;
	}
	public void setOrgRegDate(Date orgRegDate) {
		this.orgRegDate = orgRegDate;
	}
	public String getOrgCodeUnit() {
		return orgCodeUnit;
	}
	public void setOrgCodeUnit(String orgCodeUnit) {
		this.orgCodeUnit = orgCodeUnit;
	}
	public String getSuperDept() {
		return superDept;
	}
	public void setSuperDept(String superDept) {
		this.superDept = superDept;
	}
	public String getMinorBusiness() {
		return minorBusiness;
	}
	public void setMinorBusiness(String minorBusiness) {
		this.minorBusiness = minorBusiness;
	}
	public String getMainBusiness() {
		return mainBusiness;
	}
	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}
	public String getComSpBusiness() {
		return comSpBusiness;
	}
	public void setComSpBusiness(String comSpBusiness) {
		this.comSpBusiness = comSpBusiness;
	}
	public String getLoanCardFlag() {
		return loanCardFlag;
	}
	public void setLoanCardFlag(String loanCardFlag) {
		this.loanCardFlag = loanCardFlag;
	}
	public String getLoanCardStat() {
		return loanCardStat;
	}
	public void setLoanCardStat(String loanCardStat) {
		this.loanCardStat = loanCardStat;
	}
	public String getLoadCardPwd() {
		return loadCardPwd;
	}
	public void setLoadCardPwd(String loadCardPwd) {
		this.loadCardPwd = loadCardPwd;
	}
	public Date getLoadCardAuditDt() {
		return loadCardAuditDt;
	}
	public void setLoadCardAuditDt(Date loadCardAuditDt) {
		this.loadCardAuditDt = loadCardAuditDt;
	}
	public String getLegalReprIdentType() {
		return legalReprIdentType;
	}
	public void setLegalReprIdentType(String legalReprIdentType) {
		this.legalReprIdentType = legalReprIdentType;
	}
	public String getLegalReprIdentNo() {
		return legalReprIdentNo;
	}
	public void setLegalReprIdentNo(String legalReprIdentNo) {
		this.legalReprIdentNo = legalReprIdentNo;
	}
	public String getLegalReprName() {
		return legalReprName;
	}
	public void setLegalReprName(String legalReprName) {
		this.legalReprName = legalReprName;
	}
	public String getOrgAddr() {
		return orgAddr;
	}
	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}
	public String getOrgZipcode() {
		return orgZipcode;
	}
	public void setOrgZipcode(String orgZipcode) {
		this.orgZipcode = orgZipcode;
	}
	public String getOrgFex() {
		return orgFex;
	}
	public void setOrgFex(String orgFex) {
		this.orgFex = orgFex;
	}
	public String getOrgHomepage() {
		return orgHomepage;
	}
	public void setOrgHomepage(String orgHomepage) {
		this.orgHomepage = orgHomepage;
	}
	public String getOrgTel() {
		return orgTel;
	}
	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}
	public String getOrgEmail() {
		return orgEmail;
	}
	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}
	public String getIsBuildNew() {
		return isBuildNew;
	}
	public void setIsBuildNew(String isBuildNew) {
		this.isBuildNew = isBuildNew;
	}
	public String getFacilityMain() {
		return facilityMain;
	}
	public void setFacilityMain(String facilityMain) {
		this.facilityMain = facilityMain;
	}
	public String getProdCapacity() {
		return prodCapacity;
	}
	public void setProdCapacity(String prodCapacity) {
		this.prodCapacity = prodCapacity;
	}
	public String getTopCorpLevel() {
		return topCorpLevel;
	}
	public void setTopCorpLevel(String topCorpLevel) {
		this.topCorpLevel = topCorpLevel;
	}
	public String getCompOrg() {
		return compOrg;
	}
	public void setCompOrg(String compOrg) {
		this.compOrg = compOrg;
	}
	public String getFinRepType() {
		return finRepType;
	}
	public void setFinRepType(String finRepType) {
		this.finRepType = finRepType;
	}
	public String getIsNotLocalEnt() {
		return isNotLocalEnt;
	}
	public void setIsNotLocalEnt(String isNotLocalEnt) {
		this.isNotLocalEnt = isNotLocalEnt;
	}
	public String getIsRuralCorp() {
		return isRuralCorp;
	}
	public void setIsRuralCorp(String isRuralCorp) {
		this.isRuralCorp = isRuralCorp;
	}
	public String getIsListedCorp() {
		return isListedCorp;
	}
	public void setIsListedCorp(String isListedCorp) {
		this.isListedCorp = isListedCorp;
	}
	public String getHasIeRight() {
		return hasIeRight;
	}
	public void setHasIeRight(String hasIeRight) {
		this.hasIeRight = hasIeRight;
	}
	public String getIsPrepEnt() {
		return isPrepEnt;
	}
	public void setIsPrepEnt(String isPrepEnt) {
		this.isPrepEnt = isPrepEnt;
	}
	public String getIsAreaImpEnt() {
		return isAreaImpEnt;
	}
	public void setIsAreaImpEnt(String isAreaImpEnt) {
		this.isAreaImpEnt = isAreaImpEnt;
	}
	public String getIsNtnalMacroCtrl() {
		return isNtnalMacroCtrl;
	}
	public void setIsNtnalMacroCtrl(String isNtnalMacroCtrl) {
		this.isNtnalMacroCtrl = isNtnalMacroCtrl;
	}
	public String getIsHighRiskPoll() {
		return isHighRiskPoll;
	}
	public void setIsHighRiskPoll(String isHighRiskPoll) {
		this.isHighRiskPoll = isHighRiskPoll;
	}
	public String getIsSteelEnt() {
		return isSteelEnt;
	}
	public void setIsSteelEnt(String isSteelEnt) {
		this.isSteelEnt = isSteelEnt;
	}
	public String getRegisterKeyId() {
		return registerKeyId;
	}
	public void setRegisterKeyId(String registerKeyId) {
		this.registerKeyId = registerKeyId;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getRegOrg() {
		return regOrg;
	}
	public void setRegOrg(String regOrg) {
		this.regOrg = regOrg;
	}
	public String getApprDocNo() {
		return apprDocNo;
	}
	public void setApprDocNo(String apprDocNo) {
		this.apprDocNo = apprDocNo;
	}
	public String getRegisterAddr() {
		return registerAddr;
	}
	public void setRegisterAddr(String registerAddr) {
		this.registerAddr = registerAddr;
	}
	public String getRegisterCapital() {
		return registerCapital;
	}
	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getAuditEndDate() {
		return auditEndDate;
	}
	public void setAuditEndDate(Date auditEndDate) {
		this.auditEndDate = auditEndDate;
	}
	public String getApprOrg() {
		return apprOrg;
	}
	public void setApprOrg(String apprOrg) {
		this.apprOrg = apprOrg;
	}
	public String getRegisterArea() {
		return registerArea;
	}
	public void setRegisterArea(String registerArea) {
		this.registerArea = registerArea;
	}
	public String getRegisterEnAddr() {
		return registerEnAddr;
	}
	public void setRegisterEnAddr(String registerEnAddr) {
		this.registerEnAddr = registerEnAddr;
	}
	public String getRegisterCapitalCurr() {
		return registerCapitalCurr;
	}
	public void setRegisterCapitalCurr(String registerCapitalCurr) {
		this.registerCapitalCurr = registerCapitalCurr;
	}
	public String getIdentRegNo() {
		return identRegNo;
	}
	public void setIdentRegNo(String identRegNo) {
		this.identRegNo = identRegNo;
	}
	public String getIdentRegOrg() {
		return identRegOrg;
	}
	public void setIdentRegOrg(String identRegOrg) {
		this.identRegOrg = identRegOrg;
	}
	public Date getIdenRegDate() {
		return idenRegDate;
	}
	public void setIdenRegDate(Date idenRegDate) {
		this.idenRegDate = idenRegDate;
	}
	public String getIdentValidPeriod() {
		return identValidPeriod;
	}
	public void setIdentValidPeriod(String identValidPeriod) {
		this.identValidPeriod = identValidPeriod;
	}
	public String getMainProduct() {
		return mainProduct;
	}
	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}
	public String getWorkFieldArea() {
		return workFieldArea;
	}
	public void setWorkFieldArea(String workFieldArea) {
		this.workFieldArea = workFieldArea;
	}
	public String getManageStat() {
		return manageStat;
	}
	public void setManageStat(String manageStat) {
		this.manageStat = manageStat;
	}
	public String getWorkFieldOwnership() {
		return workFieldOwnership;
	}
	public void setWorkFieldOwnership(String workFieldOwnership) {
		this.workFieldOwnership = workFieldOwnership;
	}
	public String getLegalCustId() {
		return legalCustId;
	}
	public void setLegalCustId(String legalCustId) {
		this.legalCustId = legalCustId;
	}
	public String getLegalResume() {
		return legalResume;
	}
	public void setLegalResume(String legalResume) {
		this.legalResume = legalResume;
	}
	public Date getSignStartDate() {
		return signStartDate;
	}
	public void setSignStartDate(Date signStartDate) {
		this.signStartDate = signStartDate;
	}
	public Date getSignEndDate() {
		return signEndDate;
	}
	public void setSignEndDate(Date signEndDate) {
		this.signEndDate = signEndDate;
	}
	public String getPoName() {
		return poName;
	}
	public void setPoName(String poName) {
		this.poName = poName;
	}
	public String getIndivCusId() {
		return indivCusId;
	}
	public void setIndivCusId(String indivCusId) {
		this.indivCusId = indivCusId;
	}
	public String getControlIdentType() {
		return controlIdentType;
	}
	public void setControlIdentType(String controlIdentType) {
		this.controlIdentType = controlIdentType;
	}
	public String getControlIdentNo() {
		return controlIdentNo;
	}
	public void setControlIdentNo(String controlIdentNo) {
		this.controlIdentNo = controlIdentNo;
	}
	public String getControlCustId() {
		return controlCustId;
	}
	public void setControlCustId(String controlCustId) {
		this.controlCustId = controlCustId;
	}
	public String getControlName() {
		return controlName;
	}
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	public Date getControlSignStartDate() {
		return controlSignStartDate;
	}
	public void setControlSignStartDate(Date controlSignStartDate) {
		this.controlSignStartDate = controlSignStartDate;
	}
	public Date getControlSignEndDate() {
		return controlSignEndDate;
	}
	public void setControlSignEndDate(Date controlSignEndDate) {
		this.controlSignEndDate = controlSignEndDate;
	}
	public Date getAuthStartDate() {
		return authStartDate;
	}
	public void setAuthStartDate(Date authStartDate) {
		this.authStartDate = authStartDate;
	}
	public Date getAuthEndDate() {
		return authEndDate;
	}
	public void setAuthEndDate(Date authEndDate) {
		this.authEndDate = authEndDate;
	}
	public String getControlIndivCusId() {
		return controlIndivCusId;
	}
	public void setControlIndivCusId(String controlIndivCusId) {
		this.controlIndivCusId = controlIndivCusId;
	}
	public String getControlPoIdentNo() {
		return controlPoIdentNo;
	}
	public void setControlPoIdentNo(String controlPoIdentNo) {
		this.controlPoIdentNo = controlPoIdentNo;
	}
	public String getControlPoName() {
		return controlPoName;
	}
	public void setControlPoName(String controlPoName) {
		this.controlPoName = controlPoName;
	}
	public String getControlResume() {
		return controlResume;
	}
	public void setControlResume(String controlResume) {
		this.controlResume = controlResume;
	}
	public String getCwfzPerson() {
		return cwfzPerson;
	}
	public void setCwfzPerson(String cwfzPerson) {
		this.cwfzPerson = cwfzPerson;
	}
	public String getCwlxPerson() {
		return cwlxPerson;
	}
	public void setCwlxPerson(String cwlxPerson) {
		this.cwlxPerson = cwlxPerson;
	}
	public String getCwlxMobile() {
		return cwlxMobile;
	}
	public void setCwlxMobile(String cwlxMobile) {
		this.cwlxMobile = cwlxMobile;
	}
	public String getOrgSubType() {
		return orgSubType;
	}
	public void setOrgSubType(String orgSubType) {
		this.orgSubType = orgSubType;
	}
	public String getInCllType() {
		return inCllType;
	}
	public void setInCllType(String inCllType) {
		this.inCllType = inCllType;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getRegisterNationCode() {
		return registerNationCode;
	}
	public void setRegisterNationCode(String registerNationCode) {
		this.registerNationCode = registerNationCode;
	}
	
}