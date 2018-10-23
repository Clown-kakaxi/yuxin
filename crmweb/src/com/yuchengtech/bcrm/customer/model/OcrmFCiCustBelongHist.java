package com.yuchengtech.bcrm.customer.model;

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
 * The persistent class for the OCRM_F_CI_CUST_BELONG_HIST database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CUST_BELONG_HIST")
public class OcrmFCiCustBelongHist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ASSIGN_TIME")
	private Date assignTime;

	@Column(name="ASSIGN_USER")
	private String assignUser;

	@Column(name="ASSIGN_USER_NAME")
	private String assignUserName;

	@Column(name="CURRENT_INSTITUTION")
	private String currentInstitution;

	@Column(name="CURRENT_INSTITUTION_NAME")
	private String currentInstitutionName;

	@Column(name="CUST_ASSIGN_TYPE")
	private String custAssignType;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="INSTITUTION_CODE")
	private String institutionCode;

	@Column(name="INSTUTION_NAME")
	private String instutionName;

	@Column(name="MAIN_TYPE")
	private String mainType;

	@Column(name="MGR_ID")
	private String mgrId;

	@Column(name="MGR_NAME")
	private String mgrName;

    public OcrmFCiCustBelongHist() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAssignTime() {
		return this.assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public String getAssignUser() {
		return this.assignUser;
	}

	public void setAssignUser(String assignUser) {
		this.assignUser = assignUser;
	}

	public String getAssignUserName() {
		return this.assignUserName;
	}

	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}

	public String getCurrentInstitution() {
		return this.currentInstitution;
	}

	public void setCurrentInstitution(String currentInstitution) {
		this.currentInstitution = currentInstitution;
	}

	public String getCurrentInstitutionName() {
		return this.currentInstitutionName;
	}

	public void setCurrentInstitutionName(String currentInstitutionName) {
		this.currentInstitutionName = currentInstitutionName;
	}

	public String getCustAssignType() {
		return this.custAssignType;
	}

	public void setCustAssignType(String custAssignType) {
		this.custAssignType = custAssignType;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getInstitutionCode() {
		return this.institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getInstutionName() {
		return this.instutionName;
	}

	public void setInstutionName(String instutionName) {
		this.instutionName = instutionName;
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

}