package com.yuchengtech.bcrm.customer.model;

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
 * The persistent class for the OCRM_F_CI_GRADE_APPLY database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GRADE_APPLY")
public class OcrmFCiGradeApply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_GRADE_APPLY_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_GRADE_APPLY_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer id;

    @Temporal( TemporalType.DATE)
	@Column(name="APPLY_DATE")
	private Date applyDate;

	@Column(name="APPLY_ORG")
	private String applyOrg;

	@Column(name="APPLY_USER")
	private String applyUser;

	@Column(name="CERT_CODE")
	private String certCode;

	@Column(name="CERT_TYPE")
	private String certType;

	@Column(name="CURRENT_GRADE")
	private String currentGrade;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="POSITION")
	private String position;

	@Column(name="REASON")
	private String reason;

	@Column(name="SALARY")
	private BigDecimal salary;

	@Column(name="STATUS")
	private String status;

	@Column(name="TELPHONE")
	private String telphone;

	@Column(name="TO_GRADE")
	private String toGrade;

	@Column(name="WORK_UNIT")
	private String workUnit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyOrg() {
		return applyOrg;
	}

	public void setApplyOrg(String applyOrg) {
		this.applyOrg = applyOrg;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCurrentGrade() {
		return currentGrade;
	}

	public void setCurrentGrade(String currentGrade) {
		this.currentGrade = currentGrade;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getToGrade() {
		return toGrade;
	}

	public void setToGrade(String toGrade) {
		this.toGrade = toGrade;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
}