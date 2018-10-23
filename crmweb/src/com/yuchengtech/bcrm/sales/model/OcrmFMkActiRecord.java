package com.yuchengtech.bcrm.sales.model;


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
 * The persistent class for the OCRM_F_MK_ACTI_RECORD database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MK_ACTI_RECORD")
public class OcrmFMkActiRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MK_ACTI_RECORD_RECORDID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MK_ACTI_RECORD_RECORDID_GENERATOR")
	@Column(name="RECORD_ID")
	private Long recordId;

	@Column(name="ACTI_CONT")
	private String actiCont;

    @Temporal( TemporalType.DATE)
	@Column(name="ACTI_DATE")
	private Date actiDate;

	@Column(name="ACTI_RESULT")
	private String actiResult;

	@Column(name="COMM_WAY")
	private String commWay;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="EXECUTOR_ID")
	private String executorId;

	@Column(name="EXECUTOR_NAME")
	private String executorName;

	@Column(name="FOLLOW_EVENT")
	private String followEvent;

	@Column(name="MKT_ACTI_ID")
	private BigDecimal mktActiId;

	@Column(name="PROGRESS_STAGE")
	private String progressStage;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getActiCont() {
		return actiCont;
	}

	public void setActiCont(String actiCont) {
		this.actiCont = actiCont;
	}

	public Date getActiDate() {
		return actiDate;
	}

	public void setActiDate(Date actiDate) {
		this.actiDate = actiDate;
	}

	public String getActiResult() {
		return actiResult;
	}

	public void setActiResult(String actiResult) {
		this.actiResult = actiResult;
	}

	public String getCommWay() {
		return commWay;
	}

	public void setCommWay(String commWay) {
		this.commWay = commWay;
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

	public String getExecutorId() {
		return executorId;
	}

	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}

	public String getExecutorName() {
		return executorName;
	}

	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}

	public String getFollowEvent() {
		return followEvent;
	}

	public void setFollowEvent(String followEvent) {
		this.followEvent = followEvent;
	}

	public BigDecimal getMktActiId() {
		return mktActiId;
	}

	public void setMktActiId(BigDecimal mktActiId) {
		this.mktActiId = mktActiId;
	}

	public String getProgressStage() {
		return progressStage;
	}

	public void setProgressStage(String progressStage) {
		this.progressStage = progressStage;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}