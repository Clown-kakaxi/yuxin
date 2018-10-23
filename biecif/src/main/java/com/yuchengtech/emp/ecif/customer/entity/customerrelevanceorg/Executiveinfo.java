package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the EXECUTIVEINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_EXECUTIVEINFO")
public class Executiveinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EXECUTIVE_ID", unique=true, nullable=false)
	private Long executiveInfoId;

	@Column(name="BIRTHDAY",length=20)
	private String birthday;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="WORK_DEPT",length=60)
	private String dept;

//	@Column(name="ENTER_TIME", length=6)
//	private String enterTime;

//	@Column(name="EXECUTIVE_KIND", length=20)
//	private String executiveKind;

	@Column(name="GENDER",length=20)
	private String gender;

//	@Column(name="HAS_BAD_CREDIT_RECORD", length=1)
//	private String hasBadCreditRecord;

	@Column(name="HIGHEST_SCHOOLING", length=20)
	private String highestSchooling;

	@Column(name="IDENT_NO", length=40)
	private String identNo;

	@Column(name="IDENT_TYPE", length=20)
	private String identType;

	@Column(name="MARRIAGE",length=20)
	private String marriage;

	@Column(name="EXECUTIVE_NAME",length=80)
	private String name;

//	@Column(name="TITLE",length=100)
//	private String title;

//	@Column(name="WORK_PERIOD")
//	private Long workPeriod;

    public Executiveinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getExecutiveInfoId() {
		return this.executiveInfoId;
	}

	public void setExecutiveInfoId(Long executiveInfoId) {
		this.executiveInfoId = executiveInfoId;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

//	public String getEnterTime() {
//		return this.enterTime;
//	}
//
//	public void setEnterTime(String enterTime) {
//		this.enterTime = enterTime;
//	}

//	public String getExecutiveKind() {
//		return this.executiveKind;
//	}
//
//	public void setExecutiveKind(String executiveKind) {
//		this.executiveKind = executiveKind;
//	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

//	public String getHasBadCreditRecord() {
//		return this.hasBadCreditRecord;
//	}
//
//	public void setHasBadCreditRecord(String hasBadCreditRecord) {
//		this.hasBadCreditRecord = hasBadCreditRecord;
//	}

	public String getHighestSchooling() {
		return this.highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
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

	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getTitle() {
//		return this.title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}

//	public Long getWorkPeriod() {
//		return this.workPeriod;
//	}
//
//	public void setWorkPeriod(Long workPeriod) {
//		this.workPeriod = workPeriod;
//	}

}