package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_CI_GRADE_RESULT database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GRADE_RESULT")
public class OcrmFCiGradeResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RESULT_ID")
	private long resultId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_ZH_NAME")
	private String custZhName;

	@Column(name="GRADE_DATE")
	private Timestamp gradeDate;

	@Column(name="GRADE_ORG_ID")
	private String gradeOrgId;

	@Column(name="GRADE_ORG_NAME")
	private String gradeOrgName;

	@Column(name="GRADE_RESULT")
	private String gradeResult;

	public OcrmFCiGradeResult() {
	}

	public long getResultId() {
		return this.resultId;
	}

	public void setResultId(long resultId) {
		this.resultId = resultId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustZhName() {
		return this.custZhName;
	}

	public void setCustZhName(String custZhName) {
		this.custZhName = custZhName;
	}

	public Timestamp getGradeDate() {
		return this.gradeDate;
	}

	public void setGradeDate(Timestamp gradeDate) {
		this.gradeDate = gradeDate;
	}

	public String getGradeOrgId() {
		return this.gradeOrgId;
	}

	public void setGradeOrgId(String gradeOrgId) {
		this.gradeOrgId = gradeOrgId;
	}

	public String getGradeOrgName() {
		return this.gradeOrgName;
	}

	public void setGradeOrgName(String gradeOrgName) {
		this.gradeOrgName = gradeOrgName;
	}

	public String getGradeResult() {
		return this.gradeResult;
	}

	public void setGradeResult(String gradeResult) {
		this.gradeResult = gradeResult;
	}

}