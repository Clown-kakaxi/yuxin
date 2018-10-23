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
 * The persistent class for the OCRM_F_CI_MKT_CALL_C database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_CALL_C")
public class OcrmFCiMktCallC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_CALL_C_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_CALL_C_ID_GENERATOR")
	private Long id;

	@Column(name="CALL_INFO")
	private String callInfo;

	@Column(name="CALL_RESULT")
	private String callResult;

	@Column(name="CHECK_STAT")
	private String checkStat;

	@Column(name="CUST_CLASS")
	private String custClass;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="IF_TARGET_INDUST")
	private String ifTargetIndust;

	@Column(name="INDUST_TYPE")
	private String industType;

	@Column(name="LINK_MAN")
	private String linkMan;

	@Column(name="LINK_PHONE")
	private String linkPhone;

    @Temporal( TemporalType.DATE)
	@Column(name="PHONE_DATE")
	private Date phoneDate;

    @Temporal( TemporalType.DATE)
	@Column(name="RECALL_DATE")
	private Date recallDate;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

	@Column(name="REFUSE_REASON")
	private String refuseReason;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="USER_ID")
	private String userId;

    @Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;

    public OcrmFCiMktCallC() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCallInfo() {
		return this.callInfo;
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

	public String getCustClass() {
		return this.custClass;
	}

	public void setCustClass(String custClass) {
		this.custClass = custClass;
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

	public String getIfTargetIndust() {
		return this.ifTargetIndust;
	}

	public void setIfTargetIndust(String ifTargetIndust) {
		this.ifTargetIndust = ifTargetIndust;
	}

	public String getIndustType() {
		return this.industType;
	}

	public void setIndustType(String industType) {
		this.industType = industType;
	}

	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
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

	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getRefuseReason() {
		return this.refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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