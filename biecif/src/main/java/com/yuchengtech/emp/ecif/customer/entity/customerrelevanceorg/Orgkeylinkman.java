package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

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
 * The persistent class for the ORGKEYLINKMAN database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_KEYLINKMAN")
public class Orgkeylinkman implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LINKMAN_INFO_ID", unique=true, nullable=false)
	private Long linkmanInfoId;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	@Column(length=40)
	private String career;

	@Column(length=20)
	private String citizenship;

	@Column(name="CURR_UNIT_START_DATE", length=10)
	private String currUnitStartDate;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(length=20)
	private String duty;

	@Column(length=20)
	private String email;

	@Column(length=20)
	private String gender;

	@Column(name="GRADUATE_SCHOOL", length=80)
	private String graduateSchool;

	@Column(name="HIGHEST_SCHOOLING", length=20)
	private String highestSchooling;

	@Column(length=20)
	private String hobby;

	@Column(name="HOME_TEL", length=20)
	private String homeTel;

	@Column(name="IMPORTANT_LEVEL", length=20)
	private String importantLevel;

	@Column(name="IS_LEGAL_REPR", length=1)
	private String isLegalRepr;

	@Column(name="IS_PERSON_CUSTOMER", length=1)
	private String isPersonCustomer;

	@Column(length=20)
	private String kind;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="LINKMAN_BUSI_TYPE", length=20)
	private String linkmanBusiType;

	@Column(name="LINKMAN_EN_NAME", length=40)
	private String linkmanEnName;

	@Column(name="LINKMAN_ID")
	private Long linkmanId;

	@Column(name="LINKMAN_IDENT_LIMIT", length=20)
	private String linkmanIdentLimit;

	@Column(name="LINKMAN_IDENT_NO", length=40)
	private String linkmanIdentNo;

	@Column(name="LINKMAN_IDENT_TYPE", length=20)
	private String linkmanIdentType;

	@Column(name="LINKMAN_NAME", length=80)
	private String linkmanName;

	@Column(name="LINKMAN_TEL", length=32)
	private String linkmanTel;

	@Column(name="LINKMAN_TYPE", length=20)
	private String linkmanType;

	@Column(name="MAIN_ECONOMIC_SOURCE", length=20)
	private String mainEconomicSource;

	@Column(name="MAIN_MARKETING_ELEMENT", length=40)
	private String mainMarketingElement;

	@Column(length=20)
	private String marriage;

	@Column(length=20)
	private String mobile;

	@Column(name="MONTH_INCOME", precision=17, scale=2)
	private BigDecimal monthIncome;

	@Column(length=20)
	private String nationality;

	@Column(length=20)
	private String nativeplace;

	@Column(name="OFFICE_ADDR", length=200)
	private String officeAddr;

	@Column(name="OFFICE_ADDRESS", length=200)
	private String officeAddress;

	@Column(name="OFFICE_EMAIL", length=20)
	private String officeEmail;

	@Column(name="OFFICE_MOBILE", length=20)
	private String officeMobile;

	@Column(name="OTHER_ECONOMIC_SOURCE", length=20)
	private String otherEconomicSource;

	@Column(name="RELA_BACKGROUND", length=40)
	private String relaBackground;

	@Column(name="RELIGIOUS_BELIEF", length=20)
	private String religiousBelief;

	@Column(length=200)
	private String remark1;

	@Column(length=200)
	private String shareholding;

	@Column(name="SOCIETY_DUTY", length=20)
	private String societyDuty;

	@Column(name="SUPPLY_POP_NUM")
	private Long supplyPopNum;

	@Column(length=20)
	private String title;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="WORK_TIME", length=10)
	private String workTime;

	@Column(name="WORK_YEAR")
	private Long workYear;

    public Orgkeylinkman() {
    }

	public Long getLinkmanInfoId() {
		return this.linkmanInfoId;
	}

	public void setLinkmanInfoId(Long linkmanInfoId) {
		this.linkmanInfoId = linkmanInfoId;
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

	public String getCurrUnitStartDate() {
		return this.currUnitStartDate;
	}

	public void setCurrUnitStartDate(String currUnitStartDate) {
		this.currUnitStartDate = currUnitStartDate;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
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

	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getImportantLevel() {
		return this.importantLevel;
	}

	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}

	public String getIsLegalRepr() {
		return this.isLegalRepr;
	}

	public void setIsLegalRepr(String isLegalRepr) {
		this.isLegalRepr = isLegalRepr;
	}

	public String getIsPersonCustomer() {
		return this.isPersonCustomer;
	}

	public void setIsPersonCustomer(String isPersonCustomer) {
		this.isPersonCustomer = isPersonCustomer;
	}

	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
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

	public String getLinkmanBusiType() {
		return this.linkmanBusiType;
	}

	public void setLinkmanBusiType(String linkmanBusiType) {
		this.linkmanBusiType = linkmanBusiType;
	}

	public String getLinkmanEnName() {
		return this.linkmanEnName;
	}

	public void setLinkmanEnName(String linkmanEnName) {
		this.linkmanEnName = linkmanEnName;
	}

	public Long getLinkmanId() {
		return this.linkmanId;
	}

	public void setLinkmanId(Long linkmanId) {
		this.linkmanId = linkmanId;
	}

	public String getLinkmanIdentLimit() {
		return this.linkmanIdentLimit;
	}

	public void setLinkmanIdentLimit(String linkmanIdentLimit) {
		this.linkmanIdentLimit = linkmanIdentLimit;
	}

	public String getLinkmanIdentNo() {
		return this.linkmanIdentNo;
	}

	public void setLinkmanIdentNo(String linkmanIdentNo) {
		this.linkmanIdentNo = linkmanIdentNo;
	}

	public String getLinkmanIdentType() {
		return this.linkmanIdentType;
	}

	public void setLinkmanIdentType(String linkmanIdentType) {
		this.linkmanIdentType = linkmanIdentType;
	}

	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanTel() {
		return this.linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}

	public String getLinkmanType() {
		return this.linkmanType;
	}

	public void setLinkmanType(String linkmanType) {
		this.linkmanType = linkmanType;
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

	public BigDecimal getMonthIncome() {
		return this.monthIncome;
	}

	public void setMonthIncome(BigDecimal monthIncome) {
		this.monthIncome = monthIncome;
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

	public String getOfficeAddr() {
		return this.officeAddr;
	}

	public void setOfficeAddr(String officeAddr) {
		this.officeAddr = officeAddr;
	}

	public String getOfficeAddress() {
		return this.officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getOfficeEmail() {
		return this.officeEmail;
	}

	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}

	public String getOfficeMobile() {
		return this.officeMobile;
	}

	public void setOfficeMobile(String officeMobile) {
		this.officeMobile = officeMobile;
	}

	public String getOtherEconomicSource() {
		return this.otherEconomicSource;
	}

	public void setOtherEconomicSource(String otherEconomicSource) {
		this.otherEconomicSource = otherEconomicSource;
	}

	public String getRelaBackground() {
		return this.relaBackground;
	}

	public void setRelaBackground(String relaBackground) {
		this.relaBackground = relaBackground;
	}

	public String getReligiousBelief() {
		return this.religiousBelief;
	}

	public void setReligiousBelief(String religiousBelief) {
		this.religiousBelief = religiousBelief;
	}

	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getShareholding() {
		return this.shareholding;
	}

	public void setShareholding(String shareholding) {
		this.shareholding = shareholding;
	}

	public String getSocietyDuty() {
		return this.societyDuty;
	}

	public void setSocietyDuty(String societyDuty) {
		this.societyDuty = societyDuty;
	}

	public Long getSupplyPopNum() {
		return this.supplyPopNum;
	}

	public void setSupplyPopNum(Long supplyPopNum) {
		this.supplyPopNum = supplyPopNum;
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

	public String getWorkTime() {
		return this.workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public Long getWorkYear() {
		return this.workYear;
	}

	public void setWorkYear(Long workYear) {
		this.workYear = workYear;
	}

}