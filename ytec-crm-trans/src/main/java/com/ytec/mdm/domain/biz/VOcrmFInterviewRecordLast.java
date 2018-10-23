package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the OCRM_F_INTERVIEW_RECORD database table.
 */
@Entity
@Table(name = "V_OCRM_F_INTERVIEW_RECORD_LAST")
public class VOcrmFInterviewRecordLast implements Serializable {
	/**
	 * create view V_OCRM_F_INTERVIEW_RECORD_LAST as
	 * select * from ( select row_number() OVER (PARTITION BY cust_id, mgr_id ORDER BY visit_time desc) rn,t.* 
	 * from OCRM_F_INTERVIEW_RECORD t ) 
	 * where rn = 1
	 */
	@Id
	@SequenceGenerator(name = "OCRM_F_INTERVIEW_RECORD_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OCRM_F_INTERVIEW_RECORD_ID_GENERATOR")
	private BigDecimal id;

	@Column(name = "RN")
	private BigDecimal rn;// row_number

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "CUST_ID")
	private String custId;

	@Column(name = "CUST_NAME")
	private String custName;

	@Column(name = "MGR_ID")
	private String mgrId;

	@Column(name = "MGR_NAME")
	private String mgrName;

	private String remark;

	@Column(name = "REVIEW_STATE")
	private BigDecimal reviewState;

	@Column(name = "TASK_TYPE")
	private BigDecimal taskType;

	@Column(name = "VISIT_TIME")
	private Timestamp visitTime;

	@Column(name = "VISIT_TYPE")
	private String vistType;

	@Column(name = "TASK_NUMBER")
	private String taskNumber;

	public VOcrmFInterviewRecordLast() {
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
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

	public BigDecimal getReviewState() {
		return this.reviewState;
	}

	public void setReviewState(BigDecimal reviewState) {
		this.reviewState = reviewState;
	}

	public BigDecimal getTaskType() {
		return this.taskType;
	}

	public void setTaskType(BigDecimal taskType) {
		this.taskType = taskType;
	}

	public Timestamp getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Timestamp visitTime) {
		this.visitTime = visitTime;
	}

	public String getVistType() {
		return this.vistType;
	}

	public void setVistType(String vistType) {
		this.vistType = vistType;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public BigDecimal getRn() {
		return rn;
	}

	public void setRn(BigDecimal rn) {
		this.rn = rn;
	}
}