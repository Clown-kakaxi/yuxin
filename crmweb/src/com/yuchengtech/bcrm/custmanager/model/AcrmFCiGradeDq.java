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
 * 
 * @description : 客户定期审核等级信息表
 *
 * @author : zhaolong
 * @date : 2016-1-5 上午11:45:37
 */
@Entity
@Table(name="ACRM_F_CI_GRADE_DQ")
public class AcrmFCiGradeDq implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name="CHECK_END_DATE")
	private Date checkEndDate;

	@Column(name="CHECK_RESULT")
	private String checkResult;

	@Column(name="CHECK_RQ")
	private String checkRq;

	@Temporal(TemporalType.DATE)
	@Column(name="CHECK_START_DATE")
	private Date checkStartDate;

	@Column(name="CHECK_USER")
	private String checkUser;

	@Column(name="CUST_GRADE_CHECK")
	private String custGradeCheck;

	@Column(name="CUST_GRADE_OLD")
	private String custGradeOld;

	@Column(name="CUST_GRADE_TYPE")
	private String custGradeType;
	@Column(name="CUST_ID")
	private String custId;

	private String instanceid;

	private String instruction;
	@Id 
	@SequenceGenerator(name="ACRM_F_CI_GRADE_DQ_ID_FXQ_SEQUENCE", sequenceName="ID_FXQ_SEQUENCE" ,allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_GRADE_DQ_ID_FXQ_SEQUENCE")
	@Column(name="GRADE_ID")
	private String gradeId;
	
	@Column(name="CHECK_STATUS")
	private String checkStatus;

	public AcrmFCiGradeDq() {
	}

	public Date getCheckEndDate() {
		return this.checkEndDate;
	}

	public void setCheckEndDate(Date checkEndDate) {
		this.checkEndDate = checkEndDate;
	}

	public String getCheckResult() {
		return this.checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckRq() {
		return this.checkRq;
	}

	public void setCheckRq(String checkRq) {
		this.checkRq = checkRq;
	}

	public Date getCheckStartDate() {
		return this.checkStartDate;
	}

	public void setCheckStartDate(Date checkStartDate) {
		this.checkStartDate = checkStartDate;
	}

	public String getCheckUser() {
		return this.checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public String getCustGradeCheck() {
		return this.custGradeCheck;
	}

	public void setCustGradeCheck(String custGradeCheck) {
		this.custGradeCheck = custGradeCheck;
	}

	public String getCustGradeOld() {
		return this.custGradeOld;
	}

	public void setCustGradeOld(String custGradeOld) {
		this.custGradeOld = custGradeOld;
	}

	public String getCustGradeType() {
		return this.custGradeType;
	}

	public void setCustGradeType(String custGradeType) {
		this.custGradeType = custGradeType;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getInstanceid() {
		return this.instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}

	public String getInstruction() {
		return this.instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	

}