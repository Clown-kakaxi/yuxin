package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CM_CUST_MGR_INFO_REVIEW database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CM_CUST_MGR_INFO_REVIEW")
public class OcrmFCmCustMgrInfoReview implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CM_CUST_MGR_INFO_REVIEW_ID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CM_CUST_MGR_INFO_REVIEW_ID_GENERATOR")
	private Long id;

	@Column(name="AFFI_INST_ID")
	private String affiInstId;

	private String award;

	@Column(name="BELONG_BUSI_LINE")
	private String belongBusiLine;

	@Column(name="BELONG_TEAM_HEAD")
	private String belongTeamHead;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	@Column(name="BUSINESS_TYPE")
	private String businessType;

	private String certificate;

	@Column(name="CUST_MANAGER_CONTACT")
	private String custManagerContact;

	@Column(name="CUST_MANAGER_ID")
	private String custManagerId;

	@Column(name="CUST_MANAGER_LEVEL")
	private String custManagerLevel;

	@Column(name="CUST_MANAGER_NAME")
	private String custManagerName;

	@Column(name="CUST_MANAGER_STATION_YEAR")
	private BigDecimal custManagerStationYear;

	@Column(name="CUST_MANAGER_TYPE")
	private String custManagerType;

	@Column(name="DPT_ID")
	private String dptId;

	private String duty;

	@Column(name="ECONOMY_WORK_YEAR")
	private BigDecimal economyWorkYear;

	private String education;

    @Temporal( TemporalType.DATE)
	@Column(name="ENTRANTS_DATE")
	private Date entrantsDate;

	@Column(name="EVA_RESULT")
	private String evaResult;

	@Column(name="FINANCIAL_JOB_TIME")
	private String financialJobTime;

	@Column(name="IF_CREDIT")
	private String ifCredit;

	@Column(name="POSITION_CHANGE")
	private String positionChange;

	@Column(name="POSITION_DEGREE")
	private String positionDegree;

	private String state;

	private String station;

	@Column(name="WORK_PERFORMANCE")
	private String workPerformance;

	@Column(name="WORK_UNIT")
	private String workUnit;

    public OcrmFCmCustMgrInfoReview() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAffiInstId() {
		return this.affiInstId;
	}

	public void setAffiInstId(String affiInstId) {
		this.affiInstId = affiInstId;
	}

	public String getAward() {
		return this.award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getBelongBusiLine() {
		return this.belongBusiLine;
	}

	public void setBelongBusiLine(String belongBusiLine) {
		this.belongBusiLine = belongBusiLine;
	}

	public String getBelongTeamHead() {
		return this.belongTeamHead;
	}

	public void setBelongTeamHead(String belongTeamHead) {
		this.belongTeamHead = belongTeamHead;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getCustManagerContact() {
		return this.custManagerContact;
	}

	public void setCustManagerContact(String custManagerContact) {
		this.custManagerContact = custManagerContact;
	}

	public String getCustManagerId() {
		return this.custManagerId;
	}

	public void setCustManagerId(String custManagerId) {
		this.custManagerId = custManagerId;
	}

	public String getCustManagerLevel() {
		return this.custManagerLevel;
	}

	public void setCustManagerLevel(String custManagerLevel) {
		this.custManagerLevel = custManagerLevel;
	}

	public String getCustManagerName() {
		return this.custManagerName;
	}

	public void setCustManagerName(String custManagerName) {
		this.custManagerName = custManagerName;
	}

	public BigDecimal getCustManagerStationYear() {
		return this.custManagerStationYear;
	}

	public void setCustManagerStationYear(BigDecimal custManagerStationYear) {
		this.custManagerStationYear = custManagerStationYear;
	}

	public String getCustManagerType() {
		return this.custManagerType;
	}

	public void setCustManagerType(String custManagerType) {
		this.custManagerType = custManagerType;
	}

	public String getDptId() {
		return this.dptId;
	}

	public void setDptId(String dptId) {
		this.dptId = dptId;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public BigDecimal getEconomyWorkYear() {
		return this.economyWorkYear;
	}

	public void setEconomyWorkYear(BigDecimal economyWorkYear) {
		this.economyWorkYear = economyWorkYear;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Date getEntrantsDate() {
		return this.entrantsDate;
	}

	public void setEntrantsDate(Date entrantsDate) {
		this.entrantsDate = entrantsDate;
	}

	public String getEvaResult() {
		return this.evaResult;
	}

	public void setEvaResult(String evaResult) {
		this.evaResult = evaResult;
	}

	public String getFinancialJobTime() {
		return this.financialJobTime;
	}

	public void setFinancialJobTime(String financialJobTime) {
		this.financialJobTime = financialJobTime;
	}

	public String getIfCredit() {
		return this.ifCredit;
	}

	public void setIfCredit(String ifCredit) {
		this.ifCredit = ifCredit;
	}

	public String getPositionChange() {
		return this.positionChange;
	}

	public void setPositionChange(String positionChange) {
		this.positionChange = positionChange;
	}

	public String getPositionDegree() {
		return this.positionDegree;
	}

	public void setPositionDegree(String positionDegree) {
		this.positionDegree = positionDegree;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getWorkPerformance() {
		return this.workPerformance;
	}

	public void setWorkPerformance(String workPerformance) {
		this.workPerformance = workPerformance;
	}

	public String getWorkUnit() {
		return this.workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

}