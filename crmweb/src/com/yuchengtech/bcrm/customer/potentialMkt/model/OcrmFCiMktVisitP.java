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
 * The persistent class for the OCRM_F_CI_MKT_VISIT_P database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_VISIT_P")
public class OcrmFCiMktVisitP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_VISIT_P_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_VISIT_P_ID_GENERATOR")
	private Long id;

	@Column(name="CALL_ID")
	private String callId;

	@Column(name="CHECK_STAT")
	private String checkStat;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="IF_NEW")
	private String ifNew;

	@Column(name="JOB_TYPE")
	private String jobType;

	@Column(name="USER_ID")
	private String userId;

    @Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;

	@Column(name="VISIT_INFO")
	private String visitInfo;

	@Column(name="VISIT_RESULT")
	private String visitResult;
	
	 @Temporal( TemporalType.DATE)
		@Column(name="RECORD_DATE")
		private Date recordDate;
	 
	 @Temporal( TemporalType.DATE)
		@Column(name="UPDATE_DATE")
		private Date updateDate;
	 

	 
	 
 public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


    public OcrmFCiMktVisitP() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getCheckStat() {
		return this.checkStat;
	}

	public void setCheckStat(String checkStat) {
		this.checkStat = checkStat;
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

	public String getIfNew() {
		return this.ifNew;
	}

	public void setIfNew(String ifNew) {
		this.ifNew = ifNew;
	}

	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getVisitInfo() {
		return this.visitInfo;
	}

	public void setVisitInfo(String visitInfo) {
		this.visitInfo = visitInfo;
	}

	public String getVisitResult() {
		return this.visitResult;
	}

	public void setVisitResult(String visitResult) {
		this.visitResult = visitResult;
	}

}