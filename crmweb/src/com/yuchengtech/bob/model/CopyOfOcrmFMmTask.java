package com.yuchengtech.bob.model;

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
 * The persistent class for the OCRM_F_MM_TASK database table.
 * 
 */
@Entity
@Table(name="COPY_OF_OCRM_F_MM_TASK")
public class CopyOfOcrmFMmTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COPY_OF_OCRM_F_MM_TASK_TASKID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COPY_OF_OCRM_F_MM_TASK_TASKID_GENERATOR")
	@Column(name="TASK_ID")
	private String taskId;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="DIST_ORG")
	private String distOrg;

	@Column(name="DIST_TASK_TYPE")
	private String distTaskType;

	@Column(name="DIST_USER")
	private String distUser;

	@Column(name="FEE_AMT")
	private BigDecimal feeAmt;

	@Column(name="MEMO")
	private String memo;

	@Column(name="OPER_ORG")
	private String operOrg;

	@Column(name="OPER_USER")
	private String operUser;

    @Temporal( TemporalType.DATE)
	@Column(name="TASK_BEGIN_DATE")
	private Date taskBeginDate;

	@Column(name="TASK_DETAIL")
	private String taskDetail;

    @Temporal( TemporalType.DATE)
	@Column(name="TASK_DIST_DATE")
	private Date taskDistDate;

    @Temporal( TemporalType.DATE)
	@Column(name="TASK_END_DATE")
	private Date taskEndDate;

	@Column(name="TASK_NAME")
	private String taskName;

	@Column(name="TASK_PARENT_ID")
	private Long taskParentId;

	@Column(name="TASK_STAT")
	private String taskStat;

	@Column(name="TASK_TYPE")
	private String taskType;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDistOrg() {
		return distOrg;
	}

	public void setDistOrg(String distOrg) {
		this.distOrg = distOrg;
	}

	public String getDistTaskType() {
		return distTaskType;
	}

	public void setDistTaskType(String distTaskType) {
		this.distTaskType = distTaskType;
	}

	public String getDistUser() {
		return distUser;
	}

	public void setDistUser(String distUser) {
		this.distUser = distUser;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOperOrg() {
		return operOrg;
	}

	public void setOperOrg(String operOrg) {
		this.operOrg = operOrg;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public Date getTaskBeginDate() {
		return taskBeginDate;
	}

	public void setTaskBeginDate(Date taskBeginDate) {
		this.taskBeginDate = taskBeginDate;
	}

	public String getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(String taskDetail) {
		this.taskDetail = taskDetail;
	}

	public Date getTaskDistDate() {
		return taskDistDate;
	}

	public void setTaskDistDate(Date taskDistDate) {
		this.taskDistDate = taskDistDate;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Long getTaskParentId() {
		return taskParentId;
	}

	public void setTaskParentId(Long taskParentId) {
		this.taskParentId = taskParentId;
	}

	public String getTaskStat() {
		return taskStat;
	}

	public void setTaskStat(String taskStat) {
		this.taskStat = taskStat;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
}