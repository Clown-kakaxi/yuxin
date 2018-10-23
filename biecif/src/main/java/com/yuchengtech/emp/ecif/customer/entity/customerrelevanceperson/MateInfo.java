package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceperson;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * <pre>
 * Title:MateInfo的实体类
 * Description: 配偶信息
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
@Table(name="M_CI_PER_MATEINFO")
public class MateInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;
//
//	@Column(name="MATE_ID")
//	private Long mateId;
	
	@Column(name="MATE_NO",length = 40)
	private String mateNo;

	public String getMateNo() {
		return mateNo;
	}

	public void setMateNo(String mateNo) {
		this.mateNo = mateNo;
	}

	@Column(name="MATE_NAME",length = 40)
	private String mateName;
	
	@Column(name="IDENT_TYPE",length = 20)
	private String mateIdentType;
	
	@Column(name="IDENT_NO",length = 40)
	private String mateIdentNo;
	
	@Column(name="WORK_UNIT",length = 100)
	private String mateWorkUnit;
	
	@Column(name="WORK_UNIT_CHAR",length = 20)
	private String mateWorkUnitChar;
	
	@Column(name="WORK_START_TIME",length = 10)
	private String mateWorkStartTime;
	
	@Column(name="INDUSTRY",length = 20)
	private String mateIndustry;
	
	@Column(name="CAREER",length = 20)
	private String mateCareer;
	
	@Column(name="DUTY",length = 20)
	private String mateDuty;
	
	@Column(name="JOB_TITLE",length = 20)
	private String mateJobTitle;
	
	@Column(name="HOME_TEL",length = 20)
	private String mateTel;
	
	@Column(name="MOBILE",length = 20)
	private String mateMobile;
	
	@Column(name="OFFICE_TEL",length = 20)
	private String mateLandlineTel;
	
//	@Column(name="MATE_ANNUAL_INCOME",length = 20)
//	private String mateAnnualIncome;
	
	@Column(name="MONTH_INCOME")
	private Double mateMonthIncome;
	
	@Column(name="HOUSEHOLD",length = 20)
	private String mateHousehold;
	
	@Column(name="HIGHEST_SCHOOLING",length = 20)
	private String highestSchooling;
	
	@Column(name="BIRTHDAY",length = 10)
	private String birthday;
	
	@Column(name="GENDER",length = 20)
	private String gender;
	
	@Column(name="NATIONALITY",length = 20)
	private String nationality;
	
//	@Column(name="MATE_HEALTH_STAT",length = 20)
//	private String mateHealthStat;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

//    @JsonSerialize(using=BioneLongSerializer.class)
//	public Long getMateId() {
//		return mateId;
//	}
//
//	public void setMateId(Long mateId) {
//		this.mateId = mateId;
//	}

	public String getMateName() {
		return mateName;
	}

	public void setMateName(String mateName) {
		this.mateName = mateName;
	}

	public String getMateIdentType() {
		return mateIdentType;
	}

	public void setMateIdentType(String mateIdentType) {
		this.mateIdentType = mateIdentType;
	}

	public String getMateIdentNo() {
		return mateIdentNo;
	}

	public void setMateIdentNo(String mateIdentNo) {
		this.mateIdentNo = mateIdentNo;
	}

	public String getMateWorkUnit() {
		return mateWorkUnit;
	}

	public void setMateWorkUnit(String mateWorkUnit) {
		this.mateWorkUnit = mateWorkUnit;
	}

	public String getMateWorkUnitChar() {
		return mateWorkUnitChar;
	}

	public void setMateWorkUnitChar(String mateWorkUnitChar) {
		this.mateWorkUnitChar = mateWorkUnitChar;
	}

	public String getMateWorkStartTime() {
		return mateWorkStartTime;
	}

	public void setMateWorkStartTime(String mateWorkStartTime) {
		this.mateWorkStartTime = mateWorkStartTime;
	}

	public String getMateIndustry() {
		return mateIndustry;
	}

	public void setMateIndustry(String mateIndustry) {
		this.mateIndustry = mateIndustry;
	}

	public String getMateCareer() {
		return mateCareer;
	}

	public void setMateCareer(String mateCareer) {
		this.mateCareer = mateCareer;
	}

	public String getMateDuty() {
		return mateDuty;
	}

	public void setMateDuty(String mateDuty) {
		this.mateDuty = mateDuty;
	}

	public String getMateJobTitle() {
		return mateJobTitle;
	}

	public void setMateJobTitle(String mateJobTitle) {
		this.mateJobTitle = mateJobTitle;
	}

	public String getMateTel() {
		return mateTel;
	}

	public void setMateTel(String mateTel) {
		this.mateTel = mateTel;
	}

	public String getMateMobile() {
		return mateMobile;
	}

	public void setMateMobile(String mateMobile) {
		this.mateMobile = mateMobile;
	}

	public String getMateLandlineTel() {
		return mateLandlineTel;
	}

	public void setMateLandlineTel(String mateLandlineTel) {
		this.mateLandlineTel = mateLandlineTel;
	}

//	public String getMateAnnualIncome() {
//		return mateAnnualIncome;
//	}
//
//	public void setMateAnnualIncome(String mateAnnualIncome) {
//		this.mateAnnualIncome = mateAnnualIncome;
//	}

	public Double getMateMonthIncome() {
		return mateMonthIncome;
	}

	public void setMateMonthIncome(Double mateMonthIncome) {
		this.mateMonthIncome = mateMonthIncome;
	}

	public String getMateHousehold() {
		return mateHousehold;
	}

	public void setMateHousehold(String mateHousehold) {
		this.mateHousehold = mateHousehold;
	}

	public String getHighestSchooling() {
		return highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

//	public String getMateHealthStat() {
//		return mateHealthStat;
//	}
//
//	public void setMateHealthStat(String mateHealthStat) {
//		this.mateHealthStat = mateHealthStat;
//	}
	
}