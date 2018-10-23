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
 * The persistent class for the OCRM_F_CI_MKT_VISIT_C database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_VISIT_C")
public class OcrmFCiMktVisitC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_VISIT_C_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_VISIT_C_ID_GENERATOR")
	private Long id;

	@Column(name="CALL_ID")
	private String callId;

	@Column(name="CHECK_STAT")
	private String checkStat;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_SOURCE")
	private String custSource;

	@Column(name="CUST_SOURCE_MAN")
	private String custSourceMan;

	@Column(name="CUST_SOURCE_TEL")
	private String custSourceTel;

	@Column(name="INDUST_TYPE")
	private String industType;

	@Column(name="OWN_VISIT_MAN")
	private String ownVisitMan;

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

	@Column(name="VISIT_INFO")
	private String visitInfo;

	@Column(name="VISIT_MAN")
	private String visitMan;

	@Column(name="VISIT_RESULT")
	private String visitResult;

    public OcrmFCiMktVisitC() {
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

	public String getCustSource() {
		return this.custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}

	public String getCustSourceMan() {
		return this.custSourceMan;
	}

	public void setCustSourceMan(String custSourceMan) {
		this.custSourceMan = custSourceMan;
	}

	public String getCustSourceTel() {
		return this.custSourceTel;
	}

	public void setCustSourceTel(String custSourceTel) {
		this.custSourceTel = custSourceTel;
	}

	public String getIndustType() {
		return this.industType;
	}

	public void setIndustType(String industType) {
		this.industType = industType;
	}

	public String getOwnVisitMan() {
		return this.ownVisitMan;
	}

	public void setOwnVisitMan(String ownVisitMan) {
		this.ownVisitMan = ownVisitMan;
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

	public String getVisitInfo() {
		return this.visitInfo;
	}

	public void setVisitInfo(String visitInfo) {
		this.visitInfo = visitInfo;
	}

	public String getVisitMan() {
		return this.visitMan;
	}

	public void setVisitMan(String visitMan) {
		this.visitMan = visitMan;
	}

	public String getVisitResult() {
		return this.visitResult;
	}

	public void setVisitResult(String visitResult) {
		this.visitResult = visitResult;
	}

}