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
 * The persistent class for the OCRM_F_CI_BELONG_TRUSTEESHIP database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_BELONG_TRUSTEESHIP")
public class OcrmFCiBelongTrusteeship implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_BELONG_TRUSTEESHIP_CUSTID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_BELONG_TRUSTEESHIP_CUSTID_GENERATOR")
	@Column(name="ID")
	private Long id;

	@Column(name="CUST_NAME")
	private String custName;

    @Temporal( TemporalType.DATE)
	@Column(name="DEAD_LINE")
	private Date deadLine;

    @Column(name="CUST_ID")
	private String custId;

	@Column(name="MGR_ID")
	private String mgrId;

	@Column(name="MGR_NAME")
	private String mgrName;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="ORG_NAME")
	private String orgName;

    @Temporal( TemporalType.DATE)
	@Column(name="SET_DATE")
	private Date setDate;

	@Column(name="SET_USER_ID")
	private String setUserId;

	@Column(name="SET_USER_NAME")
	private String setUserName;

	@Column(name="TRUST_MGR_ID")
	private String trustMgrId;

	@Column(name="TRUST_MGR_NAME")
	private String trustMgrName;

	@Column(name="TRUST_REASON")
	private String trustReason;

	@Column(name="TRUST_STAT")
	private String trustStat;

    public OcrmFCiBelongTrusteeship() {
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

	public Date getDeadLine() {
		return this.deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public Date getSetDate() {
		return this.setDate;
	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	public String getSetUserId() {
		return this.setUserId;
	}

	public void setSetUserId(String setUserId) {
		this.setUserId = setUserId;
	}

	public String getSetUserName() {
		return this.setUserName;
	}

	public void setSetUserName(String setUserName) {
		this.setUserName = setUserName;
	}

	public String getTrustMgrId() {
		return this.trustMgrId;
	}

	public void setTrustMgrId(String trustMgrId) {
		this.trustMgrId = trustMgrId;
	}

	public String getTrustMgrName() {
		return this.trustMgrName;
	}

	public void setTrustMgrName(String trustMgrName) {
		this.trustMgrName = trustMgrName;
	}

	public String getTrustReason() {
		return this.trustReason;
	}

	public void setTrustReason(String trustReason) {
		this.trustReason = trustReason;
	}

	public String getTrustStat() {
		return this.trustStat;
	}

	public void setTrustStat(String trustStat) {
		this.trustStat = trustStat;
	}

}