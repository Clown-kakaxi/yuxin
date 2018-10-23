package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CI_BELONG_ORG database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_BELONG_ORG")
public class OcrmFCiBelongOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="ASSIGN_DATE")
	private Date assignDate;

	@Column(name="ASSIGN_USER", length=200)
	private String assignUser;

	@Column(name="ASSIGN_USERNAME", length=100)
	private String assignUsername;

	@Column(name="CUST_ID", nullable=false, length=21)
	private String custId;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Column(name="INSTITUTION_CODE", length=200)
	private String institutionCode;

	@Column(name="INSTITUTION_NAME", length=200)
	private String institutionName;

	@Column(name="MAIN_TYPE", length=13)
	private String mainType;

    public OcrmFCiBelongOrg() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAssignDate() {
		return this.assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public String getAssignUser() {
		return this.assignUser;
	}

	public void setAssignUser(String assignUser) {
		this.assignUser = assignUser;
	}

	public String getAssignUsername() {
		return this.assignUsername;
	}

	public void setAssignUsername(String assignUsername) {
		this.assignUsername = assignUsername;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public String getInstitutionCode() {
		return this.institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getInstitutionName() {
		return this.institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getMainType() {
		return this.mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

}