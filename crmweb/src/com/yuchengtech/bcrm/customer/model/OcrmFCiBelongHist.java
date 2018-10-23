package com.yuchengtech.bcrm.customer.model;

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
 * The persistent class for the OCRM_F_CI_BELONG_HIST database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_BELONG_HIST")
public class OcrmFCiBelongHist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_BELONG_HIST_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_BELONG_HIST_ID_GENERATOR")
	@Column(name="ID",unique=true, nullable=false)
	private Long id;

	@Column(name="AFTER_INST_CODE")
	private String afterInstCode;

	@Column(name="AFTER_INST_NAME")
	private String afterInstName;

	@Column(name="AFTER_MAIN_TYPE")
	private String afterMainType;

	@Column(name="AFTER_MGR_ID")
	private String afterMgrId;

	@Column(name="AFTER_MGR_NAME")
	private String afterMgrName;

    @Temporal( TemporalType.DATE)
	@Column(name="ASSIGN_DATE")
	private Date assignDate;

	@Column(name="ASSIGN_USER")
	private String assignUser;

	@Column(name="ASSIGN_USERNAME")
	private String assignUsername;

	@Column(name="BEFORE_INST_CODE")
	private String beforeInstCode;

	@Column(name="BEFORE_INST_NAME")
	private String beforeInstName;

	@Column(name="BEFORE_MAIN_TYPE")
	private String beforeMainType;

	@Column(name="BEFORE_MGR_ID")
	private String beforeMgrId;

	@Column(name="BEFORE_MGR_NAME")
	private String beforeMgrName;

	@Column(name="CUST_ID")
	private String custId;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

    @Temporal( TemporalType.DATE)
	@Column(name="WORK_TRAN_DATE")
	private Date workTranDate;

	@Column(name="WORK_TRAN_LEVEL")
	private String workTranLevel;

	@Column(name="WORK_TRAN_REASON")
	private String workTranReason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAfterInstCode() {
		return afterInstCode;
	}

	public void setAfterInstCode(String afterInstCode) {
		this.afterInstCode = afterInstCode;
	}

	public String getAfterInstName() {
		return afterInstName;
	}

	public void setAfterInstName(String afterInstName) {
		this.afterInstName = afterInstName;
	}

	public String getAfterMainType() {
		return afterMainType;
	}

	public void setAfterMainType(String afterMainType) {
		this.afterMainType = afterMainType;
	}

	public String getAfterMgrId() {
		return afterMgrId;
	}

	public void setAfterMgrId(String afterMgrId) {
		this.afterMgrId = afterMgrId;
	}

	public String getAfterMgrName() {
		return afterMgrName;
	}

	public void setAfterMgrName(String afterMgrName) {
		this.afterMgrName = afterMgrName;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public String getAssignUser() {
		return assignUser;
	}

	public void setAssignUser(String assignUser) {
		this.assignUser = assignUser;
	}

	public String getAssignUsername() {
		return assignUsername;
	}

	public void setAssignUsername(String assignUsername) {
		this.assignUsername = assignUsername;
	}

	public String getBeforeInstCode() {
		return beforeInstCode;
	}

	public void setBeforeInstCode(String beforeInstCode) {
		this.beforeInstCode = beforeInstCode;
	}

	public String getBeforeInstName() {
		return beforeInstName;
	}

	public void setBeforeInstName(String beforeInstName) {
		this.beforeInstName = beforeInstName;
	}

	public String getBeforeMainType() {
		return beforeMainType;
	}

	public void setBeforeMainType(String beforeMainType) {
		this.beforeMainType = beforeMainType;
	}

	public String getBeforeMgrId() {
		return beforeMgrId;
	}

	public void setBeforeMgrId(String beforeMgrId) {
		this.beforeMgrId = beforeMgrId;
	}

	public String getBeforeMgrName() {
		return beforeMgrName;
	}

	public void setBeforeMgrName(String beforeMgrName) {
		this.beforeMgrName = beforeMgrName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getEtlDate() {
		return etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public Date getWorkTranDate() {
		return workTranDate;
	}

	public void setWorkTranDate(Date workTranDate) {
		this.workTranDate = workTranDate;
	}

	public String getWorkTranLevel() {
		return workTranLevel;
	}

	public void setWorkTranLevel(String workTranLevel) {
		this.workTranLevel = workTranLevel;
	}

	public String getWorkTranReason() {
		return workTranReason;
	}

	public void setWorkTranReason(String workTranReason) {
		this.workTranReason = workTranReason;
	}
}