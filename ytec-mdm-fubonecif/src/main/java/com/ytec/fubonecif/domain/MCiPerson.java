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
 * MCiPerson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PERSON")
public class MCiPerson implements java.io.Serializable {

	// Fields

	private String custId;
	private String perCustType;
	private String jointCustType;
	private String surName;
	private String personalName;
	private String pinyinName;
	private String pinyinAbbr;
	private String personTitle;
	private String nickName;
	private String usedName;
	private String gender;
	private Date birthday;
	private String birthlocale;
	private String citizenship;
	private String nationality;
	private String nativeplace;
	private String household;
	private String hukouPlace;
	private String marriage;
	private String residence;
	private String health;
	private String religiousBelief;
	private String politicalFace;
	private String mobilePhone;
	private String email;
	private String homepage;
	private String weibo;
	private String weixin;
	private String qq;
	private String starSign;
	private String homeAddr;
	private String homeZipcode;
	private String homeTel;
	private String highestSchooling;
	private String highestDegree;
	private String graduateSchool;
	private String major;
	private Date graduationDate;
	private String careerStat;
	private String careerType;
	private String profession;
	private String unitName;
	private String unitChar;
	private String unitAddr;
	private String unitZipcode;
	private String unitTel;
	private String unitFex;
	private String postAddr;
	private String postZipcode;
	private String postPhone;
	private String adminLevel;
	private String cntName;
	private String duty;
	private String workResult;
	private Date careerStartDate;
	private String annualIncomeScope;
	private Double annualIncome;
	private Date currCareerStartDate;
	private String hasQualification;
	private String qualification;
	private String careerTitle;
	private Double holdStockAmt;
	private String bankDuty;
	private String salaryAcctBank;
	private String salaryAcctNo;
	private String loanCardNo;
	private String holdAcct;
	private String holdCard;
	private String resume;
	private String usaTaxIdenNo;
	private String lastDealingsDesc;
	private String remark;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;
	private String orgSubType;
	private String ifOrgSubType;
	private String areaCode;
	
	// Constructors

	/** default constructor */
	public MCiPerson() {
	}

	/** minimal constructor */
	public MCiPerson(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiPerson(String custId, String perCustType, String jointCustType,
			String surName, String personalName, String pinyinName,
			String pinyinAbbr, String personTitle, String nickName,
			String usedName, String gender, Date birthday, String birthlocale,
			String citizenship, String nationality, String nativeplace,
			String household, String hukouPlace, String marriage,
			String residence, String health, String religiousBelief,
			String politicalFace, String mobilePhone, String email,
			String homepage, String weibo, String weixin, String qq,
			String starSign, String homeAddr, String homeZipcode,
			String homeTel, String highestSchooling, String highestDegree,
			String graduateSchool, String major, Date graduationDate,
			String careerStat, String careerType, String profession,
			String unitName, String unitChar, String unitAddr,
			String unitZipcode, String unitTel, String unitFex,
			String postAddr, String postZipcode, String postPhone,
			String adminLevel, String cntName, String duty, String workResult,
			Date careerStartDate, String annualIncomeScope,
			Double annualIncome, Date currCareerStartDate,
			String hasQualification, String qualification, String careerTitle,
			Double holdStockAmt, String bankDuty, String salaryAcctBank,
			String salaryAcctNo, String loanCardNo, String holdAcct,
			String holdCard, String resume, String usaTaxIdenNo,
			String lastDealingsDesc, String remark, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,String orgSubType,String ifOrgSubType,String areaCode) {
		this.custId = custId;
		this.perCustType = perCustType;
		this.jointCustType = jointCustType;
		this.surName = surName;
		this.personalName = personalName;
		this.pinyinName = pinyinName;
		this.pinyinAbbr = pinyinAbbr;
		this.personTitle = personTitle;
		this.nickName = nickName;
		this.usedName = usedName;
		this.gender = gender;
		this.birthday = birthday;
		this.birthlocale = birthlocale;
		this.citizenship = citizenship;
		this.nationality = nationality;
		this.nativeplace = nativeplace;
		this.household = household;
		this.hukouPlace = hukouPlace;
		this.marriage = marriage;
		this.residence = residence;
		this.health = health;
		this.religiousBelief = religiousBelief;
		this.politicalFace = politicalFace;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.homepage = homepage;
		this.weibo = weibo;
		this.weixin = weixin;
		this.qq = qq;
		this.starSign = starSign;
		this.homeAddr = homeAddr;
		this.homeZipcode = homeZipcode;
		this.homeTel = homeTel;
		this.highestSchooling = highestSchooling;
		this.highestDegree = highestDegree;
		this.graduateSchool = graduateSchool;
		this.major = major;
		this.graduationDate = graduationDate;
		this.careerStat = careerStat;
		this.careerType = careerType;
		this.profession = profession;
		this.unitName = unitName;
		this.unitChar = unitChar;
		this.unitAddr = unitAddr;
		this.unitZipcode = unitZipcode;
		this.unitTel = unitTel;
		this.unitFex = unitFex;
		this.postAddr = postAddr;
		this.postZipcode = postZipcode;
		this.postPhone = postPhone;
		this.adminLevel = adminLevel;
		this.cntName = cntName;
		this.duty = duty;
		this.workResult = workResult;
		this.careerStartDate = careerStartDate;
		this.annualIncomeScope = annualIncomeScope;
		this.annualIncome = annualIncome;
		this.currCareerStartDate = currCareerStartDate;
		this.hasQualification = hasQualification;
		this.qualification = qualification;
		this.careerTitle = careerTitle;
		this.holdStockAmt = holdStockAmt;
		this.bankDuty = bankDuty;
		this.salaryAcctBank = salaryAcctBank;
		this.salaryAcctNo = salaryAcctNo;
		this.loanCardNo = loanCardNo;
		this.holdAcct = holdAcct;
		this.holdCard = holdCard;
		this.resume = resume;
		this.usaTaxIdenNo = usaTaxIdenNo;
		this.lastDealingsDesc = lastDealingsDesc;
		this.remark = remark;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
		this.orgSubType = orgSubType;
		this.ifOrgSubType=ifOrgSubType;
		this.areaCode = areaCode;
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

	@Column(name = "PER_CUST_TYPE", length = 20)
	public String getPerCustType() {
		return this.perCustType;
	}

	public void setPerCustType(String perCustType) {
		this.perCustType = perCustType;
	}

	@Column(name = "JOINT_CUST_TYPE", length = 20)
	public String getJointCustType() {
		return this.jointCustType;
	}

	public void setJointCustType(String jointCustType) {
		this.jointCustType = jointCustType;
	}

	@Column(name = "SUR_NAME", length = 20)
	public String getSurName() {
		return this.surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	@Column(name = "PERSONAL_NAME", length = 20)
	public String getPersonalName() {
		return this.personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

	@Column(name = "PINYIN_NAME", length = 80)
	public String getPinyinName() {
		return this.pinyinName;
	}

	public void setPinyinName(String pinyinName) {
		this.pinyinName = pinyinName;
	}

	@Column(name = "PINYIN_ABBR", length = 20)
	public String getPinyinAbbr() {
		return this.pinyinAbbr;
	}

	public void setPinyinAbbr(String pinyinAbbr) {
		this.pinyinAbbr = pinyinAbbr;
	}

	@Column(name = "PERSON_TITLE", length = 20)
	public String getPersonTitle() {
		return this.personTitle;
	}

	public void setPersonTitle(String personTitle) {
		this.personTitle = personTitle;
	}

	@Column(name = "NICK_NAME", length = 20)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "USED_NAME", length = 70)
	public String getUsedName() {
		return this.usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}

	@Column(name = "GENDER", length = 20)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "BIRTHLOCALE", length = 50)
	public String getBirthlocale() {
		return this.birthlocale;
	}

	public void setBirthlocale(String birthlocale) {
		this.birthlocale = birthlocale;
	}

	@Column(name = "CITIZENSHIP", length = 20)
	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	@Column(name = "NATIONALITY", length = 20)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "NATIVEPLACE", length = 200)
	public String getNativeplace() {
		return this.nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	@Column(name = "HOUSEHOLD", length = 20)
	public String getHousehold() {
		return this.household;
	}

	public void setHousehold(String household) {
		this.household = household;
	}

	@Column(name = "HUKOU_PLACE", length = 60)
	public String getHukouPlace() {
		return this.hukouPlace;
	}

	public void setHukouPlace(String hukouPlace) {
		this.hukouPlace = hukouPlace;
	}

	@Column(name = "MARRIAGE", length = 20)
	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	@Column(name = "RESIDENCE", length = 20)
	public String getResidence() {
		return this.residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	@Column(name = "HEALTH", length = 20)
	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	@Column(name = "RELIGIOUS_BELIEF", length = 20)
	public String getReligiousBelief() {
		return this.religiousBelief;
	}

	public void setReligiousBelief(String religiousBelief) {
		this.religiousBelief = religiousBelief;
	}

	@Column(name = "POLITICAL_FACE", length = 20)
	public String getPoliticalFace() {
		return this.politicalFace;
	}

	public void setPoliticalFace(String politicalFace) {
		this.politicalFace = politicalFace;
	}

	@Column(name = "MOBILE_PHONE", length = 20)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "HOMEPAGE", length = 100)
	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	@Column(name = "WEIBO", length = 100)
	public String getWeibo() {
		return this.weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	@Column(name = "WEIXIN", length = 100)
	public String getWeixin() {
		return this.weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	@Column(name = "QQ", length = 20)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "STAR_SIGN", length = 20)
	public String getStarSign() {
		return this.starSign;
	}

	public void setStarSign(String starSign) {
		this.starSign = starSign;
	}

	@Column(name = "HOME_ADDR", length = 200)
	public String getHomeAddr() {
		return this.homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	@Column(name = "HOME_ZIPCODE", length = 20)
	public String getHomeZipcode() {
		return this.homeZipcode;
	}

	public void setHomeZipcode(String homeZipcode) {
		this.homeZipcode = homeZipcode;
	}

	@Column(name = "HOME_TEL", length = 20)
	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	@Column(name = "HIGHEST_SCHOOLING", length = 20)
	public String getHighestSchooling() {
		return this.highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}

	@Column(name = "HIGHEST_DEGREE", length = 20)
	public String getHighestDegree() {
		return this.highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	@Column(name = "GRADUATE_SCHOOL", length = 80)
	public String getGraduateSchool() {
		return this.graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	@Column(name = "MAJOR", length = 80)
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GRADUATION_DATE", length = 7)
	public Date getGraduationDate() {
		return this.graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	@Column(name = "CAREER_STAT", length = 20)
	public String getCareerStat() {
		return this.careerStat;
	}

	public void setCareerStat(String careerStat) {
		this.careerStat = careerStat;
	}

	@Column(name = "CAREER_TYPE", length = 20)
	public String getCareerType() {
		return this.careerType;
	}

	public void setCareerType(String careerType) {
		this.careerType = careerType;
	}

	@Column(name = "PROFESSION", length = 40)
	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Column(name = "UNIT_NAME", length = 200)
	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name = "UNIT_CHAR", length = 20)
	public String getUnitChar() {
		return this.unitChar;
	}

	public void setUnitChar(String unitChar) {
		this.unitChar = unitChar;
	}

	@Column(name = "UNIT_ADDR", length = 200)
	public String getUnitAddr() {
		return this.unitAddr;
	}

	public void setUnitAddr(String unitAddr) {
		this.unitAddr = unitAddr;
	}

	@Column(name = "UNIT_ZIPCODE", length = 32)
	public String getUnitZipcode() {
		return this.unitZipcode;
	}

	public void setUnitZipcode(String unitZipcode) {
		this.unitZipcode = unitZipcode;
	}

	@Column(name = "UNIT_TEL", length = 20)
	public String getUnitTel() {
		return this.unitTel;
	}

	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
	}

	@Column(name = "UNIT_FEX", length = 20)
	public String getUnitFex() {
		return this.unitFex;
	}

	public void setUnitFex(String unitFex) {
		this.unitFex = unitFex;
	}

	@Column(name = "POST_ADDR", length = 200)
	public String getPostAddr() {
		return this.postAddr;
	}

	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	@Column(name = "POST_ZIPCODE", length = 20)
	public String getPostZipcode() {
		return this.postZipcode;
	}

	public void setPostZipcode(String postZipcode) {
		this.postZipcode = postZipcode;
	}

	@Column(name = "POST_PHONE", length = 20)
	public String getPostPhone() {
		return this.postPhone;
	}

	public void setPostPhone(String postPhone) {
		this.postPhone = postPhone;
	}

	@Column(name = "ADMIN_LEVEL", length = 20)
	public String getAdminLevel() {
		return this.adminLevel;
	}

	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}

	@Column(name = "CNT_NAME", length = 30)
	public String getCntName() {
		return this.cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	@Column(name = "DUTY", length = 20)
	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name = "WORK_RESULT", length = 80)
	public String getWorkResult() {
		return this.workResult;
	}

	public void setWorkResult(String workResult) {
		this.workResult = workResult;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CAREER_START_DATE", length = 7)
	public Date getCareerStartDate() {
		return this.careerStartDate;
	}

	public void setCareerStartDate(Date careerStartDate) {
		this.careerStartDate = careerStartDate;
	}

	@Column(name = "ANNUAL_INCOME_SCOPE", length = 20)
	public String getAnnualIncomeScope() {
		return this.annualIncomeScope;
	}

	public void setAnnualIncomeScope(String annualIncomeScope) {
		this.annualIncomeScope = annualIncomeScope;
	}

	@Column(name = "ANNUAL_INCOME", precision = 17)
	public Double getAnnualIncome() {
		return this.annualIncome;
	}

	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CURR_CAREER_START_DATE", length = 7)
	public Date getCurrCareerStartDate() {
		return this.currCareerStartDate;
	}

	public void setCurrCareerStartDate(Date currCareerStartDate) {
		this.currCareerStartDate = currCareerStartDate;
	}

	@Column(name = "HAS_QUALIFICATION", length = 1)
	public String getHasQualification() {
		return this.hasQualification;
	}

	public void setHasQualification(String hasQualification) {
		this.hasQualification = hasQualification;
	}

	@Column(name = "QUALIFICATION", length = 40)
	public String getQualification() {
		return this.qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	@Column(name = "CAREER_TITLE", length = 20)
	public String getCareerTitle() {
		return this.careerTitle;
	}

	public void setCareerTitle(String careerTitle) {
		this.careerTitle = careerTitle;
	}

	@Column(name = "HOLD_STOCK_AMT", precision = 17)
	public Double getHoldStockAmt() {
		return this.holdStockAmt;
	}

	public void setHoldStockAmt(Double holdStockAmt) {
		this.holdStockAmt = holdStockAmt;
	}

	@Column(name = "BANK_DUTY", length = 20)
	public String getBankDuty() {
		return this.bankDuty;
	}

	public void setBankDuty(String bankDuty) {
		this.bankDuty = bankDuty;
	}

	@Column(name = "SALARY_ACCT_BANK", length = 80)
	public String getSalaryAcctBank() {
		return this.salaryAcctBank;
	}

	public void setSalaryAcctBank(String salaryAcctBank) {
		this.salaryAcctBank = salaryAcctBank;
	}

	@Column(name = "SALARY_ACCT_NO", length = 32)
	public String getSalaryAcctNo() {
		return this.salaryAcctNo;
	}

	public void setSalaryAcctNo(String salaryAcctNo) {
		this.salaryAcctNo = salaryAcctNo;
	}

	@Column(name = "LOAN_CARD_NO", length = 32)
	public String getLoanCardNo() {
		return this.loanCardNo;
	}

	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}

	@Column(name = "HOLD_ACCT", length = 20)
	public String getHoldAcct() {
		return this.holdAcct;
	}

	public void setHoldAcct(String holdAcct) {
		this.holdAcct = holdAcct;
	}

	@Column(name = "HOLD_CARD", length = 20)
	public String getHoldCard() {
		return this.holdCard;
	}

	public void setHoldCard(String holdCard) {
		this.holdCard = holdCard;
	}

	@Column(name = "RESUME", length = 500)
	public String getResume() {
		return this.resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	@Column(name = "USA_TAX_IDEN_NO", length = 32)
	public String getUsaTaxIdenNo() {
		return this.usaTaxIdenNo;
	}

	public void setUsaTaxIdenNo(String usaTaxIdenNo) {
		this.usaTaxIdenNo = usaTaxIdenNo;
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
	
	@Column(name = "ORG_SUB_TYPE", length = 20)
	public String getOrgSubType() {
		return orgSubType;
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
	
	@Column(name = "AREA_CODE", length = 20)
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
}