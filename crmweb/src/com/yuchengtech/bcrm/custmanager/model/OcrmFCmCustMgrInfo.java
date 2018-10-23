package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;
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
 * The persistent class for the OCRM_F_CM_CUST_MGR_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CM_CUST_MGR_INFO")
public class OcrmFCmCustMgrInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CM_CUST_MGR_INFO_USERID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CM_CUST_MGR_INFO_USERID_GENERATOR")
	@Column(name="USER_ID")
	private Long userId;

	@Column(name="AFFI_INST_ID")
	private String affiInstId;

	@Column(name="BUSINESS_TYPE")
	private String businessType;

	@Column(name="CUST_MANAGER_ID")
	private String custManagerId;

	@Column(name="CUST_MANAGER_LEVEL")
	private String custManagerLevel;

	@Column(name="CUST_MANAGER_NAME")
	private String custManagerName;

	@Column(name="CUST_MANAGER_STATION_YEAR")
	private Integer custManagerStationYear;


	@Column(name="DUTY")
	private String duty;

	@Column(name="ECONOMY_WORK_YEAR")
	private Integer economyWorkYear;
    
	@Column(name="EDUCATION")
	private String education;

    @Temporal( TemporalType.DATE)
	@Column(name="ENTRANTS_DATE")
	private Date entrantsDate;

	@Column(name="IF_CREDIT")
	private String ifCredit;

	@Column(name="STATE")
	private String state;
	
	@Column(name="STATION")
	private String station;
	
	@Column(name="WORK_UNIT")
	private String workUnit;

	@Column(name="FINANCIAL_JOB_TIME")
	private String financialJobTime;
	
	@Column(name="POSITION_DEGREE")
	private String positionDegree;
	
	@Column(name="BELONG_BUSI_LINE")
	private String belongBusiLine;
	
	@Column(name="BELONG_TEAM_HEAD")
	private String belongTeamHead;
	
	@Column(name="CERTIFICATE")
	private String certificate;
	
	@Column(name="BIRTHDAY")
	private String birthday;
	
	@Column(name="EVA_RESULT")
	private String evaResult;
	
	@Column(name="WORK_PERFORMANCE")
	private String workPerformance;
	
	@Column(name="AWARD")
	private String award;
	
	@Column(name="POSITION_CHANGE")
	private String positionChange;
	
	@Column(name="DPT_ID")
	private String dptId;
	
	@Column(name="POSITION_TIME")
	private String positionTime;
	
	@Column(name="SUB_BRANCH_ID")
	private String subBranchId;
	
	@Column(name="BRANCH_ID")
	private String branchId;
	
	public String getSubBranchId() {
		return subBranchId;
	}

	public void setSubBranchId(String subBranchId) {
		this.subBranchId = subBranchId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getPositionTime() {
		return positionTime;
	}

	public void setPositionTime(String positionTime) {
		this.positionTime = positionTime;
	}

	public String getDptId() {
		return dptId;
	}

	public void setDptId(String dptId) {
		this.dptId = dptId;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getFinancialJobTime() {
		return financialJobTime;
	}

	public void setFinancialJobTime(String financialJobTime) {
		this.financialJobTime = financialJobTime;
	}

	public String getPositionDegree() {
		return positionDegree;
	}

	public void setPositionDegree(String positionDegree) {
		this.positionDegree = positionDegree;
	}

	public String getBelongBusiLine() {
		return belongBusiLine;
	}

	public void setBelongBusiLine(String belongBusiLine) {
		this.belongBusiLine = belongBusiLine;
	}

	public String getBelongTeamHead() {
		return belongTeamHead;
	}

	public void setBelongTeamHead(String belongTeamHead) {
		this.belongTeamHead = belongTeamHead;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAffiInstId() {
		return affiInstId;
	}

	public void setAffiInstId(String affiInstId) {
		this.affiInstId = affiInstId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public String getCustManagerId() {
		return custManagerId;
	}

	public void setCustManagerId(String custManagerId) {
		this.custManagerId = custManagerId;
	}

	public String getCustManagerLevel() {
		return custManagerLevel;
	}

	public void setCustManagerLevel(String custManagerLevel) {
		this.custManagerLevel = custManagerLevel;
	}

	public String getCustManagerName() {
		return custManagerName;
	}

	public void setCustManagerName(String custManagerName) {
		this.custManagerName = custManagerName;
	}

	public Integer getCustManagerStationYear() {
		return custManagerStationYear;
	}

	public void setCustManagerStationYear(Integer custManagerStationYear) {
		this.custManagerStationYear = custManagerStationYear;
	}

	public Integer getEconomyWorkYear() {
		return economyWorkYear;
	}

	public void setEconomyWorkYear(Integer economyWorkYear) {
		this.economyWorkYear = economyWorkYear;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Date getEntrantsDate() {
		return entrantsDate;
	}

	public void setEntrantsDate(Date entrantsDate) {
		this.entrantsDate = entrantsDate;
	}


	public String getIfCredit() {
		return ifCredit;
	}

	public void setIfCredit(String ifCredit) {
		this.ifCredit = ifCredit;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getEvaResult() {
		return evaResult;
	}

	public void setEvaResult(String evaResult) {
		this.evaResult = evaResult;
	}

	public String getWorkPerformance() {
		return workPerformance;
	}

	public void setWorkPerformance(String workPerformance) {
		this.workPerformance = workPerformance;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getPositionChange() {
		return positionChange;
	}

	public void setPositionChange(String positionChange) {
		this.positionChange = positionChange;
	}
	
	
	
	
}