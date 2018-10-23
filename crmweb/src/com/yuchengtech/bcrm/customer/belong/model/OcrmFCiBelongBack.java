package com.yuchengtech.bcrm.customer.belong.model;

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
 * The persistent class for the OCRM_F_CI_BELONG_BACK database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_BELONG_BACK")
public class OcrmFCiBelongBack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_BELONG_BACK_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_BELONG_BACK_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="BACK_DATE")
	private Date backDate;

	@Column(name="BACK_STAT")
	private String backStat;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_TYPE")
	private String custType;

	@Column(name="MAIN_TYPE")
	private String mainType;

	@Column(name="MGR_ID")
	private String mgrId;

	@Column(name="MGR_NAME")
	private String mgrName;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="ORG_NAME")
	private String orgName;
	
	@Column(name="RECORD_ID")
	private String recordId;
	
	@Column(name="BACK_REASON")
	private String backReason;

    public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public OcrmFCiBelongBack() {
    }

	public Long getId() {
		return this.id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBackDate() {
		return this.backDate;
	}

	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}

	public String getBackStat() {
		return this.backStat;
	}

	public void setBackStat(String backStat) {
		this.backStat = backStat;
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

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getMainType() {
		return this.mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
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

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}