package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ACRM_F_CI_PER_MATEINFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_PER_MATEINFO")
public class AcrmFCiPerMateinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	@Column(name="ANNUAL_INCOME")
	private BigDecimal annualIncome;

	@Column(name="ANNUAL_INCOME_TYPE")
	private String annualIncomeType;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	private String career;

	private String citizenship;
	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_ID_MATE")
	private String custIdMate;

	private String duty;

	private String email;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	private String gender;

	private String health;

	@Column(name="HIGHEST_SCHOOLING")
	private String highestSchooling;

	@Column(name="HOME_TEL")
	private String homeTel;

	private String household;

	@Column(name="HUKOU_PLACE")
	private String hukouPlace;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_TYPE")
	private String identType;

	private String industry;

	@Column(name="JOB_TITLE")
	private String jobTitle;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="MARR_CERT_NO")
	private String marrCertNo;

	@Column(name="MATE_NAME")
	private String mateName;

	private String mobile;

	@Column(name="MONTH_INCOME")
	private BigDecimal monthIncome;

	@Column(name="MONTH_INCOME_TYPE")
	private String monthIncomeType;

	private String nationality;

	private String nativeplace;

	@Column(name="OFFICE_TEL")
	private String officeTel;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

    @Temporal( TemporalType.DATE)
	@Column(name="WORK_START_DATE")
	private Date workStartDate;

	@Column(name="WORK_UNIT")
	private String workUnit;

	@Column(name="WORK_UNIT_CHAR")
	private String workUnitChar;

    public AcrmFCiPerMateinfo() {
    }

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAnnualIncome() {
		return this.annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getAnnualIncomeType() {
		return this.annualIncomeType;
	}

	public void setAnnualIncomeType(String annualIncomeType) {
		this.annualIncomeType = annualIncomeType;
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

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustIdMate() {
		return this.custIdMate;
	}

	public void setCustIdMate(String custIdMate) {
		this.custIdMate = custIdMate;
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

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getHighestSchooling() {
		return this.highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}

	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
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

	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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

	public String getMarrCertNo() {
		return this.marrCertNo;
	}

	public void setMarrCertNo(String marrCertNo) {
		this.marrCertNo = marrCertNo;
	}

	public String getMateName() {
		return this.mateName;
	}

	public void setMateName(String mateName) {
		this.mateName = mateName;
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

	public String getMonthIncomeType() {
		return this.monthIncomeType;
	}

	public void setMonthIncomeType(String monthIncomeType) {
		this.monthIncomeType = monthIncomeType;
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

	public String getOfficeTel() {
		return this.officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public Date getWorkStartDate() {
		return this.workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

	public String getWorkUnit() {
		return this.workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getWorkUnitChar() {
		return this.workUnitChar;
	}

	public void setWorkUnitChar(String workUnitChar) {
		this.workUnitChar = workUnitChar;
	}

}