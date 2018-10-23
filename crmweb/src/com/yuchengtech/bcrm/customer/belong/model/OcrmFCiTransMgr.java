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
 * The persistent class for the OCRM_F_CI_TRANS_MGR database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_TRANS_MGR")
public class OcrmFCiTransMgr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_TRANS_MGR_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_TRANS_MGR_ID_GENERATOR")
	private Long id;

	@Column(name="APPLY_NO")
	private String applyNo;

	private String institution;

	@Column(name="INSTITUTION_NAME")
	private String institutionName;

	@Column(name="MGR_ID")
	private String mgrId;

	@Column(name="MGR_NAME")
	private String mgrName;

    public OcrmFCiTransMgr() {
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