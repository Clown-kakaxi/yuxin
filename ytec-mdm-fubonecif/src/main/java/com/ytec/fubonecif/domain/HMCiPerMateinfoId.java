package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiPerMateinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerMateinfoId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiPerMateinfoId() {
	}

	/** minimal constructor */
	public HMCiPerMateinfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerMateinfoId(String custId, String custIdMate, String mateName,
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
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "CUST_ID", length = 20)
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

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
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
		if (!(other instanceof HMCiPerMateinfoId))
			return false;
		HMCiPerMateinfoId castOther = (HMCiPerMateinfoId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getCustIdMate() == castOther.getCustIdMate()) || (this
						.getCustIdMate() != null
						&& castOther.getCustIdMate() != null && this
						.getCustIdMate().equals(castOther.getCustIdMate())))
				&& ((this.getMateName() == castOther.getMateName()) || (this
						.getMateName() != null
						&& castOther.getMateName() != null && this
						.getMateName().equals(castOther.getMateName())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getMarrCertNo() == castOther.getMarrCertNo()) || (this
						.getMarrCertNo() != null
						&& castOther.getMarrCertNo() != null && this
						.getMarrCertNo().equals(castOther.getMarrCertNo())))
				&& ((this.getGender() == castOther.getGender()) || (this
						.getGender() != null
						&& castOther.getGender() != null && this.getGender()
						.equals(castOther.getGender())))
				&& ((this.getBirthday() == castOther.getBirthday()) || (this
						.getBirthday() != null
						&& castOther.getBirthday() != null && this
						.getBirthday().equals(castOther.getBirthday())))
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
				&& ((this.getHealth() == castOther.getHealth()) || (this
						.getHealth() != null
						&& castOther.getHealth() != null && this.getHealth()
						.equals(castOther.getHealth())))
				&& ((this.getHighestSchooling() == castOther
						.getHighestSchooling()) || (this.getHighestSchooling() != null
						&& castOther.getHighestSchooling() != null && this
						.getHighestSchooling().equals(
								castOther.getHighestSchooling())))
				&& ((this.getWorkUnit() == castOther.getWorkUnit()) || (this
						.getWorkUnit() != null
						&& castOther.getWorkUnit() != null && this
						.getWorkUnit().equals(castOther.getWorkUnit())))
				&& ((this.getWorkUnitChar() == castOther.getWorkUnitChar()) || (this
						.getWorkUnitChar() != null
						&& castOther.getWorkUnitChar() != null && this
						.getWorkUnitChar().equals(castOther.getWorkUnitChar())))
				&& ((this.getWorkStartDate() == castOther.getWorkStartDate()) || (this
						.getWorkStartDate() != null
						&& castOther.getWorkStartDate() != null && this
						.getWorkStartDate()
						.equals(castOther.getWorkStartDate())))
				&& ((this.getIndustry() == castOther.getIndustry()) || (this
						.getIndustry() != null
						&& castOther.getIndustry() != null && this
						.getIndustry().equals(castOther.getIndustry())))
				&& ((this.getCareer() == castOther.getCareer()) || (this
						.getCareer() != null
						&& castOther.getCareer() != null && this.getCareer()
						.equals(castOther.getCareer())))
				&& ((this.getDuty() == castOther.getDuty()) || (this.getDuty() != null
						&& castOther.getDuty() != null && this.getDuty()
						.equals(castOther.getDuty())))
				&& ((this.getJobTitle() == castOther.getJobTitle()) || (this
						.getJobTitle() != null
						&& castOther.getJobTitle() != null && this
						.getJobTitle().equals(castOther.getJobTitle())))
				&& ((this.getMonthIncomeType() == castOther
						.getMonthIncomeType()) || (this.getMonthIncomeType() != null
						&& castOther.getMonthIncomeType() != null && this
						.getMonthIncomeType().equals(
								castOther.getMonthIncomeType())))
				&& ((this.getMonthIncome() == castOther.getMonthIncome()) || (this
						.getMonthIncome() != null
						&& castOther.getMonthIncome() != null && this
						.getMonthIncome().equals(castOther.getMonthIncome())))
				&& ((this.getAnnualIncomeType() == castOther
						.getAnnualIncomeType()) || (this.getAnnualIncomeType() != null
						&& castOther.getAnnualIncomeType() != null && this
						.getAnnualIncomeType().equals(
								castOther.getAnnualIncomeType())))
				&& ((this.getAnnualIncome() == castOther.getAnnualIncome()) || (this
						.getAnnualIncome() != null
						&& castOther.getAnnualIncome() != null && this
						.getAnnualIncome().equals(castOther.getAnnualIncome())))
				&& ((this.getOfficeTel() == castOther.getOfficeTel()) || (this
						.getOfficeTel() != null
						&& castOther.getOfficeTel() != null && this
						.getOfficeTel().equals(castOther.getOfficeTel())))
				&& ((this.getHomeTel() == castOther.getHomeTel()) || (this
						.getHomeTel() != null
						&& castOther.getHomeTel() != null && this.getHomeTel()
						.equals(castOther.getHomeTel())))
				&& ((this.getMobile() == castOther.getMobile()) || (this
						.getMobile() != null
						&& castOther.getMobile() != null && this.getMobile()
						.equals(castOther.getMobile())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
				&& ((this.getAddress() == castOther.getAddress()) || (this
						.getAddress() != null
						&& castOther.getAddress() != null && this.getAddress()
						.equals(castOther.getAddress())))
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
				+ (getCustIdMate() == null ? 0 : this.getCustIdMate()
						.hashCode());
		result = 37 * result
				+ (getMateName() == null ? 0 : this.getMateName().hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37
				* result
				+ (getMarrCertNo() == null ? 0 : this.getMarrCertNo()
						.hashCode());
		result = 37 * result
				+ (getGender() == null ? 0 : this.getGender().hashCode());
		result = 37 * result
				+ (getBirthday() == null ? 0 : this.getBirthday().hashCode());
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
				+ (getHealth() == null ? 0 : this.getHealth().hashCode());
		result = 37
				* result
				+ (getHighestSchooling() == null ? 0 : this
						.getHighestSchooling().hashCode());
		result = 37 * result
				+ (getWorkUnit() == null ? 0 : this.getWorkUnit().hashCode());
		result = 37
				* result
				+ (getWorkUnitChar() == null ? 0 : this.getWorkUnitChar()
						.hashCode());
		result = 37
				* result
				+ (getWorkStartDate() == null ? 0 : this.getWorkStartDate()
						.hashCode());
		result = 37 * result
				+ (getIndustry() == null ? 0 : this.getIndustry().hashCode());
		result = 37 * result
				+ (getCareer() == null ? 0 : this.getCareer().hashCode());
		result = 37 * result
				+ (getDuty() == null ? 0 : this.getDuty().hashCode());
		result = 37 * result
				+ (getJobTitle() == null ? 0 : this.getJobTitle().hashCode());
		result = 37
				* result
				+ (getMonthIncomeType() == null ? 0 : this.getMonthIncomeType()
						.hashCode());
		result = 37
				* result
				+ (getMonthIncome() == null ? 0 : this.getMonthIncome()
						.hashCode());
		result = 37
				* result
				+ (getAnnualIncomeType() == null ? 0 : this
						.getAnnualIncomeType().hashCode());
		result = 37
				* result
				+ (getAnnualIncome() == null ? 0 : this.getAnnualIncome()
						.hashCode());
		result = 37 * result
				+ (getOfficeTel() == null ? 0 : this.getOfficeTel().hashCode());
		result = 37 * result
				+ (getHomeTel() == null ? 0 : this.getHomeTel().hashCode());
		result = 37 * result
				+ (getMobile() == null ? 0 : this.getMobile().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37 * result
				+ (getAddress() == null ? 0 : this.getAddress().hashCode());
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