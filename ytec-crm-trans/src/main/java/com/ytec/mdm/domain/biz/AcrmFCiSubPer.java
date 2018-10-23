package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_SUB_PER database table.
 * 
 * 个人潜在客户信息 ACRM_F_CI_SUB_PER
 */
@Entity
@Table(name="ACRM_F_CI_SUB_PER")
public class AcrmFCiSubPer implements Serializable {
	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String career;

	private String citizenship;

	@Column(name="COMMU_ADDR")
	private String commuAddr;

	@Column(name="CONTACT_METHED_HOBBY")
	private String contactMethedHobby;

	@Column(name="CONTACT_TIME_HOPPY")
	private String contactTimeHoppy;

	@Column(name="CUST_NAME")
	private String custName;

	private String email;

	@Column(name="EN_NAME")
	private String enName;

	private String gender;

	@Column(name="GRADUATE_SCHOOL")
	private String graduateSchool;

	@Column(name="HIGHEST_SCHOOLING")
	private String highestSchooling;

	private String hobby;

	@Column(name="HOME_ADDRESS")
	private String homeAddress;

	@Column(name="HOME_TEL")
	private String homeTel;

	@Temporal(TemporalType.DATE)
	@Column(name="IDENT_EXPIRED_DATE")
	private Date identExpiredDate;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_TYPE")
	private String identType;

	private String industry;

	@Column(name="INVEST_HOBBY")
	private String investHobby;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="MAIN_ECONOMIC_SOURCE")
	private String mainEconomicSource;

	@Column(name="MAIN_MARKETING_ELEMENT")
	private String mainMarketingElement;

	private String marriage;

	private String mobile;

	@Column(name="MONTH_INCOME_SCOPE")
	private String monthIncomeScope;

	private String nationality;

	private String nativeplace;

	@Column(name="OFFICE_ADDRESS")
	private String officeAddress;

	@Column(name="OFFICE_TEL")
	private String officeTel;

	@Column(name="OTHER_ECONOMIC_SOURCE")
	private String otherEconomicSource;

	@Column(name="PER_CUST_TYPE")
	private String perCustType;

	@Column(name="PHOEN_AREA")
	private String phoenArea;

	@Column(name="PINYIN_NAME")
	private String pinyinName;

	@Column(name="RELIGIOUS_BELIEF")
	private String religiousBelief;

	@Column(name="SOCIETY_DUTY")
	private String societyDuty;

	private String title;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	private String zipcode;

	public AcrmFCiSubPer() {
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCareer() {
		return this.career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getCommuAddr() {
		return this.commuAddr;
	}

	public void setCommuAddr(String commuAddr) {
		this.commuAddr = commuAddr;
	}

	public String getContactMethedHobby() {
		return this.contactMethedHobby;
	}

	public void setContactMethedHobby(String contactMethedHobby) {
		this.contactMethedHobby = contactMethedHobby;
	}

	public String getContactTimeHoppy() {
		return this.contactTimeHoppy;
	}

	public void setContactTimeHoppy(String contactTimeHoppy) {
		this.contactTimeHoppy = contactTimeHoppy;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public String getHighestSchooling() {
		return this.highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}

	public String getHobby() {
		return this.hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getHomeAddress() {
		return this.homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public Date getIdentExpiredDate() {
		return this.identExpiredDate;
	}

	public void setIdentExpiredDate(Date identExpiredDate) {
		this.identExpiredDate = identExpiredDate;
	}

	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getInvestHobby() {
		return this.investHobby;
	}

	public void setInvestHobby(String investHobby) {
		this.investHobby = investHobby;
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

	public String getMainEconomicSource() {
		return this.mainEconomicSource;
	}

	public void setMainEconomicSource(String mainEconomicSource) {
		this.mainEconomicSource = mainEconomicSource;
	}

	public String getMainMarketingElement() {
		return this.mainMarketingElement;
	}

	public void setMainMarketingElement(String mainMarketingElement) {
		this.mainMarketingElement = mainMarketingElement;
	}

	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMonthIncomeScope() {
		return this.monthIncomeScope;
	}

	public void setMonthIncomeScope(String monthIncomeScope) {
		this.monthIncomeScope = monthIncomeScope;
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

	public String getOfficeAddress() {
		return this.officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getOfficeTel() {
		return this.officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getOtherEconomicSource() {
		return this.otherEconomicSource;
	}

	public void setOtherEconomicSource(String otherEconomicSource) {
		this.otherEconomicSource = otherEconomicSource;
	}

	public String getPerCustType() {
		return this.perCustType;
	}

	public void setPerCustType(String perCustType) {
		this.perCustType = perCustType;
	}

	public String getPhoenArea() {
		return this.phoenArea;
	}

	public void setPhoenArea(String phoenArea) {
		this.phoenArea = phoenArea;
	}

	public String getPinyinName() {
		return this.pinyinName;
	}

	public void setPinyinName(String pinyinName) {
		this.pinyinName = pinyinName;
	}

	public String getReligiousBelief() {
		return this.religiousBelief;
	}

	public void setReligiousBelief(String religiousBelief) {
		this.religiousBelief = religiousBelief;
	}

	public String getSocietyDuty() {
		return this.societyDuty;
	}

	public void setSocietyDuty(String societyDuty) {
		this.societyDuty = societyDuty;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}