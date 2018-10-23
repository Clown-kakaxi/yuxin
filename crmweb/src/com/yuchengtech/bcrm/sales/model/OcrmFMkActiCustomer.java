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
 * The persistent class for the OCRM_F_MK_ACTI_CUSTOMER database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MK_ACTI_CUSTOMER")
public class OcrmFMkActiCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MK_ACTI_CUSTOMER_AIMCUSTID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MK_ACTI_CUSTOMER_AIMCUSTID_GENERATOR")
	@Column(name="AIM_CUST_ID")
	private Long aimCustId;

	@Column(name="AIM_CUST_SOURCE")
	private String aimCustSource;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="HOST_MGR_ID")
	private String hostMgrId;

	@Column(name="HOST_ORG_ID")
	private String hostOrgId;

	@Column(name="MKT_ACTI_ID")
	private BigDecimal mktActiId;

	@Column(name="PROGRESS_STEP")
	private String progressStep;

	public Long getAimCustId() {
		return aimCustId;
	}

	public void setAimCustId(Long aimCustId) {
		this.aimCustId = aimCustId;
	}

	public String getAimCustSource() {
		return aimCustSource;
	}

	public void setAimCustSource(String aimCustSource) {
		this.aimCustSource = aimCustSource;
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

	public String getHostMgrId() {
		return hostMgrId;
	}

	public void setHostMgrId(String hostMgrId) {
		this.hostMgrId = hostMgrId;
	}

	public String getHostOrgId() {
		return hostOrgId;
	}

	public void setHostOrgId(String hostOrgId) {
		this.hostOrgId = hostOrgId;
	}

	public BigDecimal getMktActiId() {
		return mktActiId;
	}

	public void setMktActiId(BigDecimal mktActiId) {
		this.mktActiId = mktActiId;
	}

	public String getProgressStep() {
		return progressStep;
	}

	public void setProgressStep(String progressStep) {
		this.progressStep = progressStep;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}