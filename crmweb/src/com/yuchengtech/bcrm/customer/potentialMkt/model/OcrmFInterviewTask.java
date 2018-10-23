package com.yuchengtech.bcrm.customer.potentialMkt.model;

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
 * The persistent class for the OCRM_F_INTERVIEW_TASK database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_RECORD")
public class OcrmFInterviewTask implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_TASK_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_TASK_GENERATOR")
	private String id;
	
	@Column(name="TASK_NUMBER")
	private String taskNumber;
	
	@Temporal( TemporalType.DATE)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;
	
	@Column(name="MGR_ID")
	private String mgrId;

	@Column(name="MGR_NAME")
	private String mgrName;

	private String remark;

	@Column(name="REVIEW_STATE")
	private String reviewState;

	@Column(name="TASK_TYPE")
	private String taskType;

    @Temporal( TemporalType.DATE)
	@Column(name="VISIT_TIME")
	private Date visitTime;

	@Column(name="VISIT_TYPE")
	private String visitType;
	
	@Column(name="MODEL_TYPE")
	private String modelType;
	
	@Column(name="NEW_RECORD_ID")
	private String newRecordId;
	
	@Column(name="FLAG")
	private String flag;
	
	@Column(name="VISIT_START_TIME")
	private String visitStartTime;//预约拜访开始时间
	
	@Column(name="VISIT_END_TIME")
	private String visitEndTime;//预约拜访结束时间
	
    public OcrmFInterviewTask() {
    }

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getNewRecordId() {
		return newRecordId;
	}

	public void setNewRecordId(String newRecordId) {
		this.newRecordId = newRecordId;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMgrId() {
		return this.mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public String getMgrName() {
		return this.mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReviewState() {
		return this.reviewState;
	}

	public void setReviewState(String reviewState) {
		this.reviewState = reviewState;
	}

	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Date getVisitTime() {
		return this.visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getVisitStartTime() {
		return visitStartTime;
	}

	public void setVisitStartTime(String visitStartTime) {
		this.visitStartTime = visitStartTime;
	}

	public String getVisitEndTime() {
		return visitEndTime;
	}

	public void setVisitEndTime(String visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

}