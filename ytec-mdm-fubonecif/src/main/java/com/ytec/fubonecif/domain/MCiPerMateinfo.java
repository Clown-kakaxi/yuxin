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
 * MCiPerMateinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_MATEINFO")
public class MCiPerMateinfo implements java.io.Serializable {

	// Fields

	private String custId;
	private String custIdMate;
	private String mateName;
	private String identType;
	private String identNo;
	private String marrCertNo;
	private String gender;
	private Date birthday;
	private String citizenship;
	private String nationality;
	private String nativeplace;
	private String household;
	private String hukouPlace;
	private String health;
	private String highestSchooling;
	private String workUnit;
	private String workUnitChar;
	private Date workStartDate;
	private String industry;
	private String career;
	private String duty;
	private String jobTitle;
	private String monthIncomeType;
	private Double monthIncome;
	private String annualIncomeType;
	private Double annualIncome;
	private String officeTel;
	private String homeTel;
	private String mobile;
	private String email;
	private String address;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerMateinfo() {
	}

	/** minimal constructor */
	public MCiPerMateinfo(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiPerMateinfo(String custId, String custIdMate, String mateName,
			String identType, String identNo, String marrCertNo, String gender,
			Date birthday, String citizenship, String nationality,
			String nativeplace, String household, String hukouPlace,
			String health, String highestSchooling, String workUnit,
			String workUnitChar, Date workStartDate, String industry,
			String career, String duty, String jobTitle,
			String monthIncomeType, Double monthIncome,
			String annualIncomeType, Double annualIncome, String officeTel,
			String homeTel, String mobile, String email, String address,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custId = custId;
		this.custIdMate = custIdMate;
		this.mateName = mateName;
		this.identType = identType;
		this.identNo = identNo;
		this.marrCertNo = marrCertNo;
		this.gender = gender;
		this.birthday = birthday;
		this.citizenship = citizenship;
		this.nationality = nationality;
		this.nativeplace = nativeplace;
		this.household = household;
		this.hukouPlace = hukouPlace;
		this.health = health;
		this.highestSchooling = highestSchooling;
		this.workUnit = workUnit;
		this.workUnitChar = workUnitChar;
		this.workStartDate = workStartDate;
		this.industry = industry;
		this.career = career;
		this.duty = duty;
		this.jobTitle = jobTitle;
		this.monthIncomeType = monthIncomeType;
		this.monthIncome = monthIncome;
		this.annualIncomeType = annualIncomeType;
		this.annualIncome = annualIncome;
		this.officeTel = officeTel;
		this.homeTel = homeTel;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
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

	@Column(name = "CUST_ID_MATE", length = 20)
	public String getCustIdMate() {
		return this.custIdMate;
	}

	public void setCustIdMate(String custIdMate) {
		this.custIdMate = custIdMate;
	}

	@Column(name = "MATE_NAME", length = 80)
	public String getMateName() {
		return this.mateName;
	}

	public void setMateName(String mateName) {
		this.mateName = mateName;
	}

	@Column(name = "IDENT_TYPE", length = 20)
	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	@Column(name = "IDENT_NO", length = 40)
	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	@Column(name = "MARR_CERT_NO", length = 40)
	public String getMarrCertNo() {
		return this.marrCertNo;
	}

	public void setMarrCertNo(String marrCertNo) {
		this.marrCertNo = marrCertNo;
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

	@Column(name = "NATIVEPLACE", length = 20)
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

	@Column(name = "HEALTH", length = 20)
	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	@Column(name = "HIGHEST_SCHOOLING", length = 20)
	public String getHighestSchooling() {
		return this.highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}

	@Column(name = "WORK_UNIT", length = 100)
	public String getWorkUnit() {
		return this.workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	@Column(name = "WORK_UNIT_CHAR", length = 20)
	public String getWorkUnitChar() {
		return this.workUnitChar;
	}

	public void setWorkUnitChar(String workUnitChar) {
		this.workUnitChar = workUnitChar;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "WORK_START_DATE", length = 7)
	public Date getWorkStartDate() {
		return this.workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

	@Column(name = "INDUSTRY", length = 20)
	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@Column(name = "CAREER", length = 20)
	public String getCareer() {
		return this.career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	@Column(name = "DUTY", length = 20)
	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name = "JOB_TITLE", length = 20)
	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name = "MONTH_INCOME_TYPE", length = 20)
	public String getMonthIncomeType() {
		return this.monthIncomeType;
	}

	public void setMonthIncomeType(String monthIncomeType) {
		this.monthIncomeType = monthIncomeType;
	}

	@Column(name = "MONTH_INCOME", precision = 17)
	public Double getMonthIncome() {
		return this.monthIncome;
	}

	public void setMonthIncome(Double monthIncome) {
		this.monthIncome = monthIncome;
	}

	@Column(name = "ANNUAL_INCOME_TYPE", length = 20)
	public String getAnnualIncomeType() {
		return this.annualIncomeType;
	}

	public void setAnnualIncomeType(String annualIncomeType) {
		this.annualIncomeType = annualIncomeType;
	}

	@Column(name = "ANNUAL_INCOME", precision = 17)
	public Double getAnnualIncome() {
		return this.annualIncome;
	}

	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}

	@Column(name = "OFFICE_TEL", length = 20)
	public String getOfficeTel() {
		return this.officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	@Column(name = "HOME_TEL", length = 20)
	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	@Column(name = "MOBILE", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

}