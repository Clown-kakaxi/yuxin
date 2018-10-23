package com.yuchengtech.bcrm.customer.belong.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_CI_TRANS_CUST database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_TRANS_CUST")
public class OcrmFCiTransCust implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_TRANS_CUST_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_TRANS_CUST_ID_GENERATOR")
	private Long id;

	@Column(name="APPLY_NO")
	private String applyNo;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	private String institution;

	@Column(name="INSTITUTION_NAME")
	private String institutionName;

	@Column(name="MAIN_TYPE")
	private String mainType;

	@Column(name="MAIN_TYPE_NEW")
	private String mainTypeNew;

	@Column(name="MGR_ID")
	private String mgrId;

	@Column(name="MGR_NAME")
	private String mgrName;

	@Column(name="RECORD_ID")
	private String recordId;

	@Column(name="STATE")
	private String state;
	
    public OcrmFCiTransCust() {
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplyNo() {
		return this.applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
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

	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
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

	public String getMainTypeNew() {
		return this.mainTypeNew;
	}

	public void setMainTypeNew(String mainTypeNew) {
		this.mainTypeNew = mainTypeNew;
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

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

}