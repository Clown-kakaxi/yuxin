package com.yuchengtech.bcrm.customer.customerView.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_PERSON_TEMP database table.
 * 个人客户临时表
 */
@Entity
@Table(name="ACRM_F_CI_PERSON_TEMP")
public class AcrmFCiPersonTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="ADMIN_LEVEL")
	private String adminLevel;

	@Column(name="ANNUAL_INCOME")
	private BigDecimal annualIncome;

	@Column(name="ANNUAL_INCOME_SCOPE")
	private String annualIncomeScope;

	@Column(name="APPROVAL_STATUS")
	private String approvalStatus;

	@Column(name="BANK_DUTY")
	private String bankDuty;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	private String birthlocale;

    @Temporal( TemporalType.DATE)
	@Column(name="CAREER_START_DATE")
	private Date careerStartDate;

	@Column(name="CAREER_STAT")
	private String careerStat;

	@Column(name="CAREER_TITLE")
	private String careerTitle;

	@Column(name="CAREER_TYPE")
	private String careerType;

	private String citizenship;

	@Column(name="CNT_NAME")
	private String cntName;

    @Temporal( TemporalType.DATE)
	@Column(name="CURR_CAREER_START_DATE")
	private Date currCareerStartDate;

	@Column(name="CUS_BANK_REL")
	private String cusBankRel;

	@Column(name="CUS_CONS")
	private String cusCons;

	@Column(name="CUS_FACE_IDENT")
	private String cusFaceIdent;

	@Column(name="CUS_FINGERPRINT")
	private String cusFingerprint;

	@Column(name="CUS_VEIN")
	private String cusVein;

	@Column(name="CUST_FROM")
	private String custFrom;

	@Column(name="CUST_LEVEL")
	private String custLevel;

	private String duty;

	private String email;

	@Column(name="EN_NAME")
	private String enName;

	private String gender;

	@Column(name="GRADUATE_SCHOOL")
	private String graduateSchool;

	@Column(name="GRADUATION_TIME")
	private String graduationTime;

	@Column(name="HAS_QUALIFICATION")
	private String hasQualification;

	private String health;

	@Column(name="HIGHEST_DEGREE")
	private String highestDegree;

	@Column(name="HIGHEST_SCHOOLING")
	private String highestSchooling;

	@Column(name="HOLD_ACCT")
	private String holdAcct;

	@Column(name="HOLD_CARD")
	private String holdCard;

	@Column(name="HOLD_STOCK_AMT")
	private BigDecimal holdStockAmt;

	@Column(name="HOME_ADDR")
	private String homeAddr;

	@Column(name="HOME_TEL")
	private String homeTel;

	@Column(name="HOME_ZIPCODE")
	private String homeZipcode;

	private String homepage;

	private String household;

	@Column(name="HUKOU_PLACE")
	private String hukouPlace;

	@Column(name="IS_APPROVAL")
	private String isApproval;

	@Column(name="JOINT_CUST_TYPE")
	private String jointCustType;

	@Column(name="LAST_DEALINGS_DESC")
	private String lastDealingsDesc;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LOAN_CARD_NO")
	private String loanCardNo;

	private String major;

	private String marriage;

	@Column(name="MOBILE_PHONE")
	private String mobilePhone;

	private String nationality;

	private String nativeplace;

	@Column(name="NICK_NAME")
	private String nickName;

	@Column(name="PER_CUST_TYPE")
	private String perCustType;

	@Column(name="PERSON_TITLE")
	private String personTitle;

	@Column(name="PERSONAL_NAME")
	private String personalName;

	@Column(name="PINYIN_ABBR")
	private String pinyinAbbr;

	@Column(name="PINYIN_NAME")
	private String pinyinName;

	@Column(name="POLITICAL_FACE")
	private String politicalFace;

	@Column(name="POST_ADDR")
	private String postAddr;

	@Column(name="POST_PHONE")
	private String postPhone;

	@Column(name="POST_ZIPCODE")
	private String postZipcode;

	private String profession;

	private String qq;

	private String qualification;

	@Column(name="RELIGIOUS_BELIEF")
	private String religiousBelief;

	private String remarks;

	private String residence;

	private String resume;

	@Column(name="RISK_LEVEL")
	private String riskLevel;

    @Temporal( TemporalType.DATE)
	@Column(name="RISK_LEVEL_END_DATE")
	private Date riskLevelEndDate;

	@Column(name="SALARY_ACCT_BANK")
	private String salaryAcctBank;

	@Column(name="SALARY_ACCT_NO")
	private String salaryAcctNo;

	@Column(name="SUR_NAME")
	private String surName;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="UNIT_ADDR")
	private String unitAddr;

	@Column(name="UNIT_CHAR")
	private String unitChar;

	@Column(name="UNIT_FEX")
	private String unitFex;

	@Column(name="UNIT_NAME")
	private String unitName;

	@Column(name="UNIT_TEL")
	private String unitTel;

	@Column(name="UNIT_ZIPCODE")
	private String unitZipcode;

	@Column(name="USA_TAX_IDEN_NO")
	private String usaTaxIdenNo;

	@Column(name="USED_NAME")
	private String usedName;

	private String weibo;

	private String weixin;

	@Column(name="WORK_RESULT")
	private String workResult;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public AcrmFCiPersonTemp() {
    }

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAdminLevel() {
		return this.adminLevel;
	}

	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}

	public BigDecimal getAnnualIncome() {
		return this.annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getAnnualIncomeScope() {
		return this.annualIncomeScope;
	}

	public void setAnnualIncomeScope(String annualIncomeScope) {
		this.annualIncomeScope = annualIncomeScope;
	}

	public String getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getBankDuty() {
		return this.bankDuty;
	}

	public void setBankDuty(String bankDuty) {
		this.bankDuty = bankDuty;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBirthlocale() {
		return this.birthlocale;
	}

	public void setBirthlocale(String birthlocale) {
		this.birthlocale = birthlocale;
	}

	public Date getCareerStartDate() {
		return this.careerStartDate;
	}

	public void setCareerStartDate(Date careerStartDate) {
		this.careerStartDate = careerStartDate;
	}

	public String getCareerStat() {
		return this.careerStat;
	}

	public void setCareerStat(String careerStat) {
		this.careerStat = careerStat;
	}

	public String getCareerTitle() {
		return this.careerTitle;
	}

	public void setCareerTitle(String careerTitle) {
		this.careerTitle = careerTitle;
	}

	public String getCareerType() {
		return this.careerType;
	}

	public void setCareerType(String careerType) {
		this.careerType = careerType;
	}

	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getCntName() {
		return this.cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public Date getCurrCareerStartDate() {
		return this.currCareerStartDate;
	}

	public void setCurrCareerStartDate(Date currCareerStartDate) {
		this.currCareerStartDate = currCareerStartDate;
	}

	public String getCusBankRel() {
		return this.cusBankRel;
	}

	public void setCusBankRel(String cusBankRel) {
		this.cusBankRel = cusBankRel;
	}

	public String getCusCons() {
		return this.cusCons;
	}

	public void setCusCons(String cusCons) {
		this.cusCons = cusCons;
	}

	public String getCusFaceIdent() {
		return this.cusFaceIdent;
	}

	public void setCusFaceIdent(String cusFaceIdent) {
		this.cusFaceIdent = cusFaceIdent;
	}

	public String getCusFingerprint() {
		return this.cusFingerprint;
	}

	public void setCusFingerprint(String cusFingerprint) {
		this.cusFingerprint = cusFingerprint;
	}

	public String getCusVein() {
		return this.cusVein;
	}

	public void setCusVein(String cusVein) {
		this.cusVein = cusVein;
	}

	public String getCustFrom() {
		return this.custFrom;
	}

	public void setCustFrom(String custFrom) {
		this.custFrom = custFrom;
	}

	public String getCustLevel() {
		return this.custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGraduateSchool() {
		return this.graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getGraduationTime() {
		return this.graduationTime;
	}

	public void setGraduationTime(String graduationTime) {
		this.graduationTime = graduationTime;
	}

	public String getHasQualification() {
		return this.hasQualification;
	}

	public void setHasQualification(String hasQualification) {
		this.hasQualification = hasQualification;
	}

	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getHighestDegree() {
		return this.highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	public String getHighestSchooling() {
		return this.highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}

	public String getHoldAcct() {
		return this.holdAcct;
	}

	public void setHoldAcct(String holdAcct) {
		this.holdAcct = holdAcct;
	}

	public String getHoldCard() {
		return this.holdCard;
	}

	public void setHoldCard(String holdCard) {
		this.holdCard = holdCard;
	}

	public BigDecimal getHoldStockAmt() {
		return this.holdStockAmt;
	}

	public void setHoldStockAmt(BigDecimal holdStockAmt) {
		this.holdStockAmt = holdStockAmt;
	}

	public String getHomeAddr() {
		return this.homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getHomeZipcode() {
		return this.homeZipcode;
	}

	public void setHomeZipcode(String homeZipcode) {
		this.homeZipcode = homeZipcode;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getHousehold() {
		return this.household;
	}

	public void setHousehold(String household) {
		this.household = household;
	}

	public String getHukouPlace() {
		return this.hukouPlace;
	}

	public void setHukouPlace(String hukouPlace) {
		this.hukouPlace = hukouPlace;
	}

	public String getIsApproval() {
		return this.isApproval;
	}

	public void setIsApproval(String isApproval) {
		this.isApproval = isApproval;
	}

	public String getJointCustType() {
		return this.jointCustType;
	}

	public void setJointCustType(String jointCustType) {
		this.jointCustType = jointCustType;
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

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLoanCardNo() {
		return this.loanCardNo;
	}

	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNativeplace() {
		return this.nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPerCustType() {
		return this.perCustType;
	}

	public void setPerCustType(String perCustType) {
		this.perCustType = perCustType;
	}

	public String getPersonTitle() {
		return this.personTitle;
	}

	public void setPersonTitle(String personTitle) {
		this.personTitle = personTitle;
	}

	public String getPersonalName() {
		return this.personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

	public String getPinyinAbbr() {
		return this.pinyinAbbr;
	}

	public void setPinyinAbbr(String pinyinAbbr) {
		this.pinyinAbbr = pinyinAbbr;
	}

	public String getPinyinName() {
		return this.pinyinName;
	}

	public void setPinyinName(String pinyinName) {
		this.pinyinName = pinyinName;
	}

	public String getPoliticalFace() {
		return this.politicalFace;
	}

	public void setPoliticalFace(String politicalFace) {
		this.politicalFace = politicalFace;
	}

	public String getPostAddr() {
		return this.postAddr;
	}

	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	public String getPostPhone() {
		return this.postPhone;
	}

	public void setPostPhone(String postPhone) {
		this.postPhone = postPhone;
	}

	public String getPostZipcode() {
		return this.postZipcode;
	}

	public void setPostZipcode(String postZipcode) {
		this.postZipcode = postZipcode;
	}

	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getQualification() {
		return this.qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getReligiousBelief() {
		return this.religiousBelief;
	}

	public void setReligiousBelief(String religiousBelief) {
		this.religiousBelief = religiousBelief;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getResidence() {
		return this.residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getResume() {
		return this.resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getRiskLevel() {
		return this.riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Date getRiskLevelEndDate() {
		return this.riskLevelEndDate;
	}

	public void setRiskLevelEndDate(Date riskLevelEndDate) {
		this.riskLevelEndDate = riskLevelEndDate;
	}

	public String getSalaryAcctBank() {
		return this.salaryAcctBank;
	}

	public void setSalaryAcctBank(String salaryAcctBank) {
		this.salaryAcctBank = salaryAcctBank;
	}

	public String getSalaryAcctNo() {
		return this.salaryAcctNo;
	}

	public void setSalaryAcctNo(String salaryAcctNo) {
		this.salaryAcctNo = salaryAcctNo;
	}

	public String getSurName() {
		return this.surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getUnitAddr() {
		return this.unitAddr;
	}

	public void setUnitAddr(String unitAddr) {
		this.unitAddr = unitAddr;
	}

	public String getUnitChar() {
		return this.unitChar;
	}

	public void setUnitChar(String unitChar) {
		this.unitChar = unitChar;
	}

	public String getUnitFex() {
		return this.unitFex;
	}

	public void setUnitFex(String unitFex) {
		this.unitFex = unitFex;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitTel() {
		return this.unitTel;
	}

	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
	}

	public String getUnitZipcode() {
		return this.unitZipcode;
	}

	public void setUnitZipcode(String unitZipcode) {
		this.unitZipcode = unitZipcode;
	}

	public String getUsaTaxIdenNo() {
		return this.usaTaxIdenNo;
	}

	public void setUsaTaxIdenNo(String usaTaxIdenNo) {
		this.usaTaxIdenNo = usaTaxIdenNo;
	}

	public String getUsedName() {
		return this.usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}

	public String getWeibo() {
		return this.weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getWeixin() {
		return this.weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getWorkResult() {
		return this.workResult;
	}

	public void setWorkResult(String workResult) {
		this.workResult = workResult;
	}

}