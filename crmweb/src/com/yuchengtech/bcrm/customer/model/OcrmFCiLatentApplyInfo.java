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
 * The persistent class for the OCRM_F_CI_LATENT_APPLY_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_LATENT_APPLY_INFO")
public class OcrmFCiLatentApplyInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_LATENT_APPLY_INFO_CLAIMTAGNO_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_LATENT_APPLY_INFO_CLAIMTAGNO_GENERATOR")
	@Column(name = "CLAIMTAG_NO",unique=true, nullable=false)
	private Long claimtagNo;

	@Column(name="APPLY_INIT")
	private String applyInit;

	@Column(name="APPLY_INITNAME")
	private String applyInitname;

	@Column(name="APPLY_PERIOD")
	private Long applyPeriod;

	@Column(name="APPLY_REASON")
	private String applyReason;

	@Column(name="APPLY_USER")
	private String applyUser;

	@Column(name="APPLY_USERNAME")
	private String applyUsername;

	@Column(name="APPROVEL_STATUS")
	private String approvelStatus;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CUST_ID")
	private String custId;

	public Long getClaimtagNo() {
		return claimtagNo;
	}

	public void setClaimtagNo(Long claimtagNo) {
		this.claimtagNo = claimtagNo;
	}

	public String getApplyInit() {
		return applyInit;
	}

	public void setApplyInit(String applyInit) {
		this.applyInit = applyInit;
	}

	public String getApplyInitname() {
		return applyInitname;
	}

	public void setApplyInitname(String applyInitname) {
		this.applyInitname = applyInitname;
	}

	public Long getApplyPeriod() {
		return applyPeriod;
	}

	public void setApplyPeriod(Long applyPeriod) {
		this.applyPeriod = applyPeriod;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyUsername() {
		return applyUsername;
	}

	public void setApplyUsername(String applyUsername) {
		this.applyUsername = applyUsername;
	}

	public String getApprovelStatus() {
		return approvelStatus;
	}

	public void setApprovelStatus(String approvelStatus) {
		this.approvelStatus = approvelStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
}