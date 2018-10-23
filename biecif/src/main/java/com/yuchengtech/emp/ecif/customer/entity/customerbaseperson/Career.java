package com.yuchengtech.emp.ecif.customer.entity.customerbaseperson;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * <pre>
 * Title:Career的实体类
 * Description: 
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Entity
@Table(name="M_CI_PER_CAREER")
public class Career implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="CAREER_TYPE", length=20)
	private String career;
	
//	@Column(name="CAREER_CASE", length=20)
//	private String careerCase;
	
	@Column(name="CAREER_STAT", length=20)
	private String careerStat;
	
	@Column(name="CAREER_TITLE", length=20)
	private String careerTitle;
	
	@Column(name="CAREER_START_DATE", length=10)
	private String careerStartDate;
	
	@Column(name="PROFESSION", length=40)
	private String profession;

//	@Column(name="JOBCUSTOMID", length=40)
//	private String jobcustomid;
	
	@Column(name="UNIT_NAME", length=200)
	private String workUnitName;
	
	@Column(name="UNIT_CHAR", length=20)
	private String workUnitChar;
	
//	@Column(name="WORK_UNIT_INDUSTRY", length=20)
//	private String workUnitIndustry;
	
	@Column(name="ZIPCODE", length=32)
	private String zipCode;
	
	@Column(name="UNIT_ADDR", length=200)
	private String unitAddr;
	
	@Column(name="DUTY", length=20)
	private String duty;
	
//	@Column(name="DUTY_DETAIL", length=80)
//	private String dutyDetail;
	
//	@Column(name="CURR_CAREER_START_DATE", length=10)
//	private String currCareerStartDate;
//	
//	@Column(name="CURR_WORK_TIME", length=10)
//	private String currWorkTime;
	
	@Column(name="ADMIN_LEVEL", length=20)
	private String adminLevel;
	
	@Column(name="QUALIFICATION", length=40)
	private String qualification;
	
	@Column(name="WORK_RESULT", length=80)
	private String workResult;
	
	@Column(name="HAS_QUALIFICATION", length=1)
	private String hasQualification;
	
	@Column(name="ANNUAL_SALARY_SCOPE", length=20)
	private String annualSalaryScope;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}



	public String getCareerStat() {
		return careerStat;
	}

	public void setCareerStat(String careerStat) {
		this.careerStat = careerStat;
	}

	public String getCareerTitle() {
		return careerTitle;
	}

	public void setCareerTitle(String careerTitle) {
		this.careerTitle = careerTitle;
	}

	public String getCareerStartDate() {
		return careerStartDate;
	}

	public void setCareerStartDate(String careerStartDate) {
		this.careerStartDate = careerStartDate;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}



	public String getWorkUnitName() {
		return workUnitName;
	}

	public void setWorkUnitName(String workUnitName) {
		this.workUnitName = workUnitName;
	}

	public String getWorkUnitChar() {
		return workUnitChar;
	}

	public void setWorkUnitChar(String workUnitChar) {
		this.workUnitChar = workUnitChar;
	}



	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getUnitAddr() {
		return unitAddr;
	}

	public void setUnitAddr(String unitAddr) {
		this.unitAddr = unitAddr;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}



	public String getAdminLevel() {
		return adminLevel;
	}

	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getWorkResult() {
		return workResult;
	}

	public void setWorkResult(String workResult) {
		this.workResult = workResult;
	}

	public String getHasQualification() {
		return hasQualification;
	}

	public void setHasQualification(String hasQualification) {
		this.hasQualification = hasQualification;
	}

	public String getAnnualSalaryScope() {
		return annualSalaryScope;
	}

	public void setAnnualSalaryScope(String annualSalaryScope) {
		this.annualSalaryScope = annualSalaryScope;
	}
	
}
