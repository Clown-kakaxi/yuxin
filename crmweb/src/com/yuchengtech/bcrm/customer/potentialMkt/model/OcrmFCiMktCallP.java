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
 * The persistent class for the OCRM_F_CI_MKT_CALL_P database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_CALL_P")
public class OcrmFCiMktCallP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_CALL_P_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_CALL_P_ID_GENERATOR")
	private Long id;

	@Column(name="CALL_RESULT")
	private String callResult;

	@Column(name="CHECK_STAT")
	private String checkStat;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_SOURCE")
	private String custSource;

	@Column(name="IF_NEW")
	private String ifNew;

	@Column(name="JOB_TYPE")
	private String jobType;

	@Column(name="LINK_PHONE")
	private String linkPhone;

    @Temporal( TemporalType.DATE)
	@Column(name="PHONE_DATE")
	private Date phoneDate;

    @Temporal( TemporalType.DATE)
	@Column(name="RECALL_DATE")
	private Date recallDate;

	@Column(name="REFUSE_REASON")
	private String refuseReason;

	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="CALL_INFO")
	private String callInfo;

    @Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;

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


    public OcrmFCiMktCallP() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCallInfo() {
		return callInfo;
	}

	public void setCallInfo(String callInfo) {
		this.callInfo = callInfo;
	}

	public String getCallResult() {
		return this.callResult;
	}

	public void setCallResult(String callResult) {
		this.callResult = callResult;
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

	public String getCustSource() {
		return this.custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
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

	public String getLinkPhone() {
		return this.linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public Date getPhoneDate() {
		return this.phoneDate;
	}

	public void setPhoneDate(Date phoneDate) {
		this.phoneDate = phoneDate;
	}

	public Date getRecallDate() {
		return this.recallDate;
	}

	public void setRecallDate(Date recallDate) {
		this.recallDate = recallDate;
	}

	public String getRefuseReason() {
		return this.refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
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

}