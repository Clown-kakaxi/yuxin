package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiPersonId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPersonId implements java.io.Serializable {

	// Fields

	private String custId;
	private String perCustType;
	private String jointCustType;
	private String orgSubType;
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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiPersonId() {
	}

	/** minimal constructor */
	public HMCiPersonId(String custId, Timestamp hisOperTime) {
		this.custId = custId;
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPersonId(String custId, String perCustType,
			String jointCustType, String orgSubType, String surName,
			String personalName, String pinyinName, String pinyinAbbr,
			String personTitle, String nickName, String usedName,
			String gender, Date birthday, String birthlocale,
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
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.custId = custId;
		this.perCustType = perCustType;
		this.jointCustType = jointCustType;
		this.orgSubType = orgSubType;
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

	@Column(name = "ORG_SUB_TYPE", length = 20)
	public String getOrgSubType() {
		return this.orgSubType;
	}

	public void setOrgSubType(String orgSubType) {
		this.orgSubType = orgSubType;
	}

	@Column(name = "SUR_NAME", length = 20)
	public String getSurName() {
		return this.surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	@Column(name = "PERSONAL_NAME", length = 50)
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

	@Column(name = "UNIT_TEL", length = 30)
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
		if (!(other instanceof HMCiPersonId))
			return false;
		HMCiPersonId castOther = (HMCiPersonId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getPerCustType() == castOther.getPerCustType()) || (this
						.getPerCustType() != null
						&& castOther.getPerCustType() != null && this
						.getPerCustType().equals(castOther.getPerCustType())))
				&& ((this.getJointCustType() == castOther.getJointCustType()) || (this
						.getJointCustType() != null
						&& castOther.getJointCustType() != null && this
						.getJointCustType()
						.equals(castOther.getJointCustType())))
				&& ((this.getOrgSubType() == castOther.getOrgSubType()) || (this
						.getOrgSubType() != null
						&& castOther.getOrgSubType() != null && this
						.getOrgSubType().equals(castOther.getOrgSubType())))
				&& ((this.getSurName() == castOther.getSurName()) || (this
						.getSurName() != null
						&& castOther.getSurName() != null && this.getSurName()
						.equals(castOther.getSurName())))
				&& ((this.getPersonalName() == castOther.getPersonalName()) || (this
						.getPersonalName() != null
						&& castOther.getPersonalName() != null && this
						.getPersonalName().equals(castOther.getPersonalName())))
				&& ((this.getPinyinName() == castOther.getPinyinName()) || (this
						.getPinyinName() != null
						&& castOther.getPinyinName() != null && this
						.getPinyinName().equals(castOther.getPinyinName())))
				&& ((this.getPinyinAbbr() == castOther.getPinyinAbbr()) || (this
						.getPinyinAbbr() != null
						&& castOther.getPinyinAbbr() != null && this
						.getPinyinAbbr().equals(castOther.getPinyinAbbr())))
				&& ((this.getPersonTitle() == castOther.getPersonTitle()) || (this
						.getPersonTitle() != null
						&& castOther.getPersonTitle() != null && this
						.getPersonTitle().equals(castOther.getPersonTitle())))
				&& ((this.getNickName() == castOther.getNickName()) || (this
						.getNickName() != null
						&& castOther.getNickName() != null && this
						.getNickName().equals(castOther.getNickName())))
				&& ((this.getUsedName() == castOther.getUsedName()) || (this
						.getUsedName() != null
						&& castOther.getUsedName() != null && this
						.getUsedName().equals(castOther.getUsedName())))
				&& ((this.getGender() == castOther.getGender()) || (this
						.getGender() != null
						&& castOther.getGender() != null && this.getGender()
						.equals(castOther.getGender())))
				&& ((this.getBirthday() == castOther.getBirthday()) || (this
						.getBirthday() != null
						&& castOther.getBirthday() != null && this
						.getBirthday().equals(castOther.getBirthday())))
				&& ((this.getBirthlocale() == castOther.getBirthlocale()) || (this
						.getBirthlocale() != null
						&& castOther.getBirthlocale() != null && this
						.getBirthlocale().equals(castOther.getBirthlocale())))
				&& ((this.getCitizenship() == castOther.getCitizenship()) || (this
						.getCitizenship() != null
						&& castOther.getCitizenship() != null && this
						.getCitizenship().equals(castOther.getCitizenship())))
				&& ((this.getNationality() == castOther.getNationality()) || (this
						.getNationality() != null
						&& castOther.getNationality() != null && this
						.getNationality().equals(castOther.getNationality())))
				&& ((this.getNativeplace() == castOther.getNativeplace()) || (this
						.getNativeplace() != null
						&& castOther.getNativeplace() != null && this
						.getNativeplace().equals(castOther.getNativeplace())))
				&& ((this.getHousehold() == castOther.getHousehold()) || (this
						.getHousehold() != null
						&& castOther.getHousehold() != null && this
						.getHousehold().equals(castOther.getHousehold())))
				&& ((this.getHukouPlace() == castOther.getHukouPlace()) || (this
						.getHukouPlace() != null
						&& castOther.getHukouPlace() != null && this
						.getHukouPlace().equals(castOther.getHukouPlace())))
				&& ((this.getMarriage() == castOther.getMarriage()) || (this
						.getMarriage() != null
						&& castOther.getMarriage() != null && this
						.getMarriage().equals(castOther.getMarriage())))
				&& ((this.getResidence() == castOther.getResidence()) || (this
						.getResidence() != null
						&& castOther.getResidence() != null && this
						.getResidence().equals(castOther.getResidence())))
				&& ((this.getHealth() == castOther.getHealth()) || (this
						.getHealth() != null
						&& castOther.getHealth() != null && this.getHealth()
						.equals(castOther.getHealth())))
				&& ((this.getReligiousBelief() == castOther
						.getReligiousBelief()) || (this.getReligiousBelief() != null
						&& castOther.getReligiousBelief() != null && this
						.getReligiousBelief().equals(
								castOther.getReligiousBelief())))
				&& ((this.getPoliticalFace() == castOther.getPoliticalFace()) || (this
						.getPoliticalFace() != null
						&& castOther.getPoliticalFace() != null && this
						.getPoliticalFace()
						.equals(castOther.getPoliticalFace())))
				&& ((this.getMobilePhone() == castOther.getMobilePhone()) || (this
						.getMobilePhone() != null
						&& castOther.getMobilePhone() != null && this
						.getMobilePhone().equals(castOther.getMobilePhone())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
				&& ((this.getHomepage() == castOther.getHomepage()) || (this
						.getHomepage() != null
						&& castOther.getHomepage() != null && this
						.getHomepage().equals(castOther.getHomepage())))
				&& ((this.getWeibo() == castOther.getWeibo()) || (this
						.getWeibo() != null
						&& castOther.getWeibo() != null && this.getWeibo()
						.equals(castOther.getWeibo())))
				&& ((this.getWeixin() == castOther.getWeixin()) || (this
						.getWeixin() != null
						&& castOther.getWeixin() != null && this.getWeixin()
						.equals(castOther.getWeixin())))
				&& ((this.getQq() == castOther.getQq()) || (this.getQq() != null
						&& castOther.getQq() != null && this.getQq().equals(
						castOther.getQq())))
				&& ((this.getStarSign() == castOther.getStarSign()) || (this
						.getStarSign() != null
						&& castOther.getStarSign() != null && this
						.getStarSign().equals(castOther.getStarSign())))
				&& ((this.getHomeAddr() == castOther.getHomeAddr()) || (this
						.getHomeAddr() != null
						&& castOther.getHomeAddr() != null && this
						.getHomeAddr().equals(castOther.getHomeAddr())))
				&& ((this.getHomeZipcode() == castOther.getHomeZipcode()) || (this
						.getHomeZipcode() != null
						&& castOther.getHomeZipcode() != null && this
						.getHomeZipcode().equals(castOther.getHomeZipcode())))
				&& ((this.getHomeTel() == castOther.getHomeTel()) || (this
						.getHomeTel() != null
						&& castOther.getHomeTel() != null && this.getHomeTel()
						.equals(castOther.getHomeTel())))
				&& ((this.getHighestSchooling() == castOther
						.getHighestSchooling()) || (this.getHighestSchooling() != null
						&& castOther.getHighestSchooling() != null && this
						.getHighestSchooling().equals(
								castOther.getHighestSchooling())))
				&& ((this.getHighestDegree() == castOther.getHighestDegree()) || (this
						.getHighestDegree() != null
						&& castOther.getHighestDegree() != null && this
						.getHighestDegree()
						.equals(castOther.getHighestDegree())))
				&& ((this.getGraduateSchool() == castOther.getGraduateSchool()) || (this
						.getGraduateSchool() != null
						&& castOther.getGraduateSchool() != null && this
						.getGraduateSchool().equals(
								castOther.getGraduateSchool())))
				&& ((this.getMajor() == castOther.getMajor()) || (this
						.getMajor() != null
						&& castOther.getMajor() != null && this.getMajor()
						.equals(castOther.getMajor())))
				&& ((this.getGraduationDate() == castOther.getGraduationDate()) || (this
						.getGraduationDate() != null
						&& castOther.getGraduationDate() != null && this
						.getGraduationDate().equals(
								castOther.getGraduationDate())))
				&& ((this.getCareerStat() == castOther.getCareerStat()) || (this
						.getCareerStat() != null
						&& castOther.getCareerStat() != null && this
						.getCareerStat().equals(castOther.getCareerStat())))
				&& ((this.getCareerType() == castOther.getCareerType()) || (this
						.getCareerType() != null
						&& castOther.getCareerType() != null && this
						.getCareerType().equals(castOther.getCareerType())))
				&& ((this.getProfession() == castOther.getProfession()) || (this
						.getProfession() != null
						&& castOther.getProfession() != null && this
						.getProfession().equals(castOther.getProfession())))
				&& ((this.getUnitName() == castOther.getUnitName()) || (this
						.getUnitName() != null
						&& castOther.getUnitName() != null && this
						.getUnitName().equals(castOther.getUnitName())))
				&& ((this.getUnitChar() == castOther.getUnitChar()) || (this
						.getUnitChar() != null
						&& castOther.getUnitChar() != null && this
						.getUnitChar().equals(castOther.getUnitChar())))
				&& ((this.getUnitAddr() == castOther.getUnitAddr()) || (this
						.getUnitAddr() != null
						&& castOther.getUnitAddr() != null && this
						.getUnitAddr().equals(castOther.getUnitAddr())))
				&& ((this.getUnitZipcode() == castOther.getUnitZipcode()) || (this
						.getUnitZipcode() != null
						&& castOther.getUnitZipcode() != null && this
						.getUnitZipcode().equals(castOther.getUnitZipcode())))
				&& ((this.getUnitTel() == castOther.getUnitTel()) || (this
						.getUnitTel() != null
						&& castOther.getUnitTel() != null && this.getUnitTel()
						.equals(castOther.getUnitTel())))
				&& ((this.getUnitFex() == castOther.getUnitFex()) || (this
						.getUnitFex() != null
						&& castOther.getUnitFex() != null && this.getUnitFex()
						.equals(castOther.getUnitFex())))
				&& ((this.getPostAddr() == castOther.getPostAddr()) || (this
						.getPostAddr() != null
						&& castOther.getPostAddr() != null && this
						.getPostAddr().equals(castOther.getPostAddr())))
				&& ((this.getPostZipcode() == castOther.getPostZipcode()) || (this
						.getPostZipcode() != null
						&& castOther.getPostZipcode() != null && this
						.getPostZipcode().equals(castOther.getPostZipcode())))
				&& ((this.getPostPhone() == castOther.getPostPhone()) || (this
						.getPostPhone() != null
						&& castOther.getPostPhone() != null && this
						.getPostPhone().equals(castOther.getPostPhone())))
				&& ((this.getAdminLevel() == castOther.getAdminLevel()) || (this
						.getAdminLevel() != null
						&& castOther.getAdminLevel() != null && this
						.getAdminLevel().equals(castOther.getAdminLevel())))
				&& ((this.getCntName() == castOther.getCntName()) || (this
						.getCntName() != null
						&& castOther.getCntName() != null && this.getCntName()
						.equals(castOther.getCntName())))
				&& ((this.getDuty() == castOther.getDuty()) || (this.getDuty() != null
						&& castOther.getDuty() != null && this.getDuty()
						.equals(castOther.getDuty())))
				&& ((this.getWorkResult() == castOther.getWorkResult()) || (this
						.getWorkResult() != null
						&& castOther.getWorkResult() != null && this
						.getWorkResult().equals(castOther.getWorkResult())))
				&& ((this.getCareerStartDate() == castOther
						.getCareerStartDate()) || (this.getCareerStartDate() != null
						&& castOther.getCareerStartDate() != null && this
						.getCareerStartDate().equals(
								castOther.getCareerStartDate())))
				&& ((this.getAnnualIncomeScope() == castOther
						.getAnnualIncomeScope()) || (this
						.getAnnualIncomeScope() != null
						&& castOther.getAnnualIncomeScope() != null && this
						.getAnnualIncomeScope().equals(
								castOther.getAnnualIncomeScope())))
				&& ((this.getAnnualIncome() == castOther.getAnnualIncome()) || (this
						.getAnnualIncome() != null
						&& castOther.getAnnualIncome() != null && this
						.getAnnualIncome().equals(castOther.getAnnualIncome())))
				&& ((this.getCurrCareerStartDate() == castOther
						.getCurrCareerStartDate()) || (this
						.getCurrCareerStartDate() != null
						&& castOther.getCurrCareerStartDate() != null && this
						.getCurrCareerStartDate().equals(
								castOther.getCurrCareerStartDate())))
				&& ((this.getHasQualification() == castOther
						.getHasQualification()) || (this.getHasQualification() != null
						&& castOther.getHasQualification() != null && this
						.getHasQualification().equals(
								castOther.getHasQualification())))
				&& ((this.getQualification() == castOther.getQualification()) || (this
						.getQualification() != null
						&& castOther.getQualification() != null && this
						.getQualification()
						.equals(castOther.getQualification())))
				&& ((this.getCareerTitle() == castOther.getCareerTitle()) || (this
						.getCareerTitle() != null
						&& castOther.getCareerTitle() != null && this
						.getCareerTitle().equals(castOther.getCareerTitle())))
				&& ((this.getHoldStockAmt() == castOther.getHoldStockAmt()) || (this
						.getHoldStockAmt() != null
						&& castOther.getHoldStockAmt() != null && this
						.getHoldStockAmt().equals(castOther.getHoldStockAmt())))
				&& ((this.getBankDuty() == castOther.getBankDuty()) || (this
						.getBankDuty() != null
						&& castOther.getBankDuty() != null && this
						.getBankDuty().equals(castOther.getBankDuty())))
				&& ((this.getSalaryAcctBank() == castOther.getSalaryAcctBank()) || (this
						.getSalaryAcctBank() != null
						&& castOther.getSalaryAcctBank() != null && this
						.getSalaryAcctBank().equals(
								castOther.getSalaryAcctBank())))
				&& ((this.getSalaryAcctNo() == castOther.getSalaryAcctNo()) || (this
						.getSalaryAcctNo() != null
						&& castOther.getSalaryAcctNo() != null && this
						.getSalaryAcctNo().equals(castOther.getSalaryAcctNo())))
				&& ((this.getLoanCardNo() == castOther.getLoanCardNo()) || (this
						.getLoanCardNo() != null
						&& castOther.getLoanCardNo() != null && this
						.getLoanCardNo().equals(castOther.getLoanCardNo())))
				&& ((this.getHoldAcct() == castOther.getHoldAcct()) || (this
						.getHoldAcct() != null
						&& castOther.getHoldAcct() != null && this
						.getHoldAcct().equals(castOther.getHoldAcct())))
				&& ((this.getHoldCard() == castOther.getHoldCard()) || (this
						.getHoldCard() != null
						&& castOther.getHoldCard() != null && this
						.getHoldCard().equals(castOther.getHoldCard())))
				&& ((this.getResume() == castOther.getResume()) || (this
						.getResume() != null
						&& castOther.getResume() != null && this.getResume()
						.equals(castOther.getResume())))
				&& ((this.getUsaTaxIdenNo() == castOther.getUsaTaxIdenNo()) || (this
						.getUsaTaxIdenNo() != null
						&& castOther.getUsaTaxIdenNo() != null && this
						.getUsaTaxIdenNo().equals(castOther.getUsaTaxIdenNo())))
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
		result = 37
				* result
				+ (getPerCustType() == null ? 0 : this.getPerCustType()
						.hashCode());
		result = 37
				* result
				+ (getJointCustType() == null ? 0 : this.getJointCustType()
						.hashCode());
		result = 37
				* result
				+ (getOrgSubType() == null ? 0 : this.getOrgSubType()
						.hashCode());
		result = 37 * result
				+ (getSurName() == null ? 0 : this.getSurName().hashCode());
		result = 37
				* result
				+ (getPersonalName() == null ? 0 : this.getPersonalName()
						.hashCode());
		result = 37
				* result
				+ (getPinyinName() == null ? 0 : this.getPinyinName()
						.hashCode());
		result = 37
				* result
				+ (getPinyinAbbr() == null ? 0 : this.getPinyinAbbr()
						.hashCode());
		result = 37
				* result
				+ (getPersonTitle() == null ? 0 : this.getPersonTitle()
						.hashCode());
		result = 37 * result
				+ (getNickName() == null ? 0 : this.getNickName().hashCode());
		result = 37 * result
				+ (getUsedName() == null ? 0 : this.getUsedName().hashCode());
		result = 37 * result
				+ (getGender() == null ? 0 : this.getGender().hashCode());
		result = 37 * result
				+ (getBirthday() == null ? 0 : this.getBirthday().hashCode());
		result = 37
				* result
				+ (getBirthlocale() == null ? 0 : this.getBirthlocale()
						.hashCode());
		result = 37
				* result
				+ (getCitizenship() == null ? 0 : this.getCitizenship()
						.hashCode());
		result = 37
				* result
				+ (getNationality() == null ? 0 : this.getNationality()
						.hashCode());
		result = 37
				* result
				+ (getNativeplace() == null ? 0 : this.getNativeplace()
						.hashCode());
		result = 37 * result
				+ (getHousehold() == null ? 0 : this.getHousehold().hashCode());
		result = 37
				* result
				+ (getHukouPlace() == null ? 0 : this.getHukouPlace()
						.hashCode());
		result = 37 * result
				+ (getMarriage() == null ? 0 : this.getMarriage().hashCode());
		result = 37 * result
				+ (getResidence() == null ? 0 : this.getResidence().hashCode());
		result = 37 * result
				+ (getHealth() == null ? 0 : this.getHealth().hashCode());
		result = 37
				* result
				+ (getReligiousBelief() == null ? 0 : this.getReligiousBelief()
						.hashCode());
		result = 37
				* result
				+ (getPoliticalFace() == null ? 0 : this.getPoliticalFace()
						.hashCode());
		result = 37
				* result
				+ (getMobilePhone() == null ? 0 : this.getMobilePhone()
						.hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37 * result
				+ (getHomepage() == null ? 0 : this.getHomepage().hashCode());
		result = 37 * result
				+ (getWeibo() == null ? 0 : this.getWeibo().hashCode());
		result = 37 * result
				+ (getWeixin() == null ? 0 : this.getWeixin().hashCode());
		result = 37 * result + (getQq() == null ? 0 : this.getQq().hashCode());
		result = 37 * result
				+ (getStarSign() == null ? 0 : this.getStarSign().hashCode());
		result = 37 * result
				+ (getHomeAddr() == null ? 0 : this.getHomeAddr().hashCode());
		result = 37
				* result
				+ (getHomeZipcode() == null ? 0 : this.getHomeZipcode()
						.hashCode());
		result = 37 * result
				+ (getHomeTel() == null ? 0 : this.getHomeTel().hashCode());
		result = 37
				* result
				+ (getHighestSchooling() == null ? 0 : this
						.getHighestSchooling().hashCode());
		result = 37
				* result
				+ (getHighestDegree() == null ? 0 : this.getHighestDegree()
						.hashCode());
		result = 37
				* result
				+ (getGraduateSchool() == null ? 0 : this.getGraduateSchool()
						.hashCode());
		result = 37 * result
				+ (getMajor() == null ? 0 : this.getMajor().hashCode());
		result = 37
				* result
				+ (getGraduationDate() == null ? 0 : this.getGraduationDate()
						.hashCode());
		result = 37
				* result
				+ (getCareerStat() == null ? 0 : this.getCareerStat()
						.hashCode());
		result = 37
				* result
				+ (getCareerType() == null ? 0 : this.getCareerType()
						.hashCode());
		result = 37
				* result
				+ (getProfession() == null ? 0 : this.getProfession()
						.hashCode());
		result = 37 * result
				+ (getUnitName() == null ? 0 : this.getUnitName().hashCode());
		result = 37 * result
				+ (getUnitChar() == null ? 0 : this.getUnitChar().hashCode());
		result = 37 * result
				+ (getUnitAddr() == null ? 0 : this.getUnitAddr().hashCode());
		result = 37
				* result
				+ (getUnitZipcode() == null ? 0 : this.getUnitZipcode()
						.hashCode());
		result = 37 * result
				+ (getUnitTel() == null ? 0 : this.getUnitTel().hashCode());
		result = 37 * result
				+ (getUnitFex() == null ? 0 : this.getUnitFex().hashCode());
		result = 37 * result
				+ (getPostAddr() == null ? 0 : this.getPostAddr().hashCode());
		result = 37
				* result
				+ (getPostZipcode() == null ? 0 : this.getPostZipcode()
						.hashCode());
		result = 37 * result
				+ (getPostPhone() == null ? 0 : this.getPostPhone().hashCode());
		result = 37
				* result
				+ (getAdminLevel() == null ? 0 : this.getAdminLevel()
						.hashCode());
		result = 37 * result
				+ (getCntName() == null ? 0 : this.getCntName().hashCode());
		result = 37 * result
				+ (getDuty() == null ? 0 : this.getDuty().hashCode());
		result = 37
				* result
				+ (getWorkResult() == null ? 0 : this.getWorkResult()
						.hashCode());
		result = 37
				* result
				+ (getCareerStartDate() == null ? 0 : this.getCareerStartDate()
						.hashCode());
		result = 37
				* result
				+ (getAnnualIncomeScope() == null ? 0 : this
						.getAnnualIncomeScope().hashCode());
		result = 37
				* result
				+ (getAnnualIncome() == null ? 0 : this.getAnnualIncome()
						.hashCode());
		result = 37
				* result
				+ (getCurrCareerStartDate() == null ? 0 : this
						.getCurrCareerStartDate().hashCode());
		result = 37
				* result
				+ (getHasQualification() == null ? 0 : this
						.getHasQualification().hashCode());
		result = 37
				* result
				+ (getQualification() == null ? 0 : this.getQualification()
						.hashCode());
		result = 37
				* result
				+ (getCareerTitle() == null ? 0 : this.getCareerTitle()
						.hashCode());
		result = 37
				* result
				+ (getHoldStockAmt() == null ? 0 : this.getHoldStockAmt()
						.hashCode());
		result = 37 * result
				+ (getBankDuty() == null ? 0 : this.getBankDuty().hashCode());
		result = 37
				* result
				+ (getSalaryAcctBank() == null ? 0 : this.getSalaryAcctBank()
						.hashCode());
		result = 37
				* result
				+ (getSalaryAcctNo() == null ? 0 : this.getSalaryAcctNo()
						.hashCode());
		result = 37
				* result
				+ (getLoanCardNo() == null ? 0 : this.getLoanCardNo()
						.hashCode());
		result = 37 * result
				+ (getHoldAcct() == null ? 0 : this.getHoldAcct().hashCode());
		result = 37 * result
				+ (getHoldCard() == null ? 0 : this.getHoldCard().hashCode());
		result = 37 * result
				+ (getResume() == null ? 0 : this.getResume().hashCode());
		result = 37
				* result
				+ (getUsaTaxIdenNo() == null ? 0 : this.getUsaTaxIdenNo()
						.hashCode());
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