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
 * �ͻ���������
 */
@Entity
@Table(name="OCRM_F_CI_CF_APPLY_INFO")
public class OcrmFCiCfApplyInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CF_APPLY_INFO_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CF_APPLY_INFO_ID_GENERATOR")
	@Column(name = "ID",unique=true, nullable=false)
	private Long id;

	@Column(name="APPLY_INIT")
	private String applyInit;

	@Column(name="APPLY_REASON")
	private String applyReason;

	@Column(name="APPLY_USER")
	private String applyUser;

	@Column(name="APPROVEL_STATUS")
	private String approvelStatus;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="HB_CUST_ID")
	private String hbCustId;

	@Column(name="HB_CUST_NAME")
	private String hbCustName;

	@Column(name="TAR_CUST_ID")
	private String tarCustId;

	@Column(name="TAR_CUST_NAME")
	private String tarCustName;

    public OcrmFCiCfApplyInfo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplyInit() {
		return this.applyInit;
	}

	public void setApplyInit(String applyInit) {
		this.applyInit = applyInit;
	}

	public String getApplyReason() {
		return this.applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getApplyUser() {
		return this.applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApprovelStatus() {
		return this.approvelStatus;
	}

	public void setApprovelStatus(String approvelStatus) {
		this.approvelStatus = approvelStatus;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getHbCustId() {
		return this.hbCustId;
	}

	public void setHbCustId(String hbCustId) {
		this.hbCustId = hbCustId;
	}

	public String getHbCustName() {
		return this.hbCustName;
	}

	public void setHbCustName(String hbCustName) {
		this.hbCustName = hbCustName;
	}

	public String getTarCustId() {
		return this.tarCustId;
	}

	public void setTarCustId(String tarCustId) {
		this.tarCustId = tarCustId;
	}

	public String getTarCustName() {
		return this.tarCustName;
	}

	public void setTarCustName(String tarCustName) {
		this.tarCustName = tarCustName;
	}
}