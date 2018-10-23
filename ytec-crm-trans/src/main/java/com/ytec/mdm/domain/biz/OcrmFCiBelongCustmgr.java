package com.ytec.mdm.domain.biz;

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
 * The persistent class for the OCRM_F_CI_BELONG_CUSTMGR database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_BELONG_CUSTMGR")
public class OcrmFCiBelongCustmgr implements Serializable {
	@Id
	@SequenceGenerator(name="CommonSequnce", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CommonSequnce")
	@Column(name="id")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="ASSIGN_DATE")
	private Date assignDate;

	@Column(name="ASSIGN_USER", length=35)
	private String assignUser;

	@Column(name="ASSIGN_USERNAME", length=80)
	private String assignUsername;

	@Column(name="CHECK_RIGHT", length=13)
	private String checkRight;

	@Column(name="CUST_ID", nullable=false, length=21)
	private String custId;

	@Column(length=200)
	private String institution;

	@Column(name="INSTITUTION_NAME", length=200)
	private String institutionName;

	@Column(name="MAIN_TYPE", length=13)
	private String mainType;

	@Column(name="MAINTAIN_RIGHT", length=13)
	private String maintainRight;

	@Column(name="MGR_ID", length=200)
	private String mgrId;
	
	@Column(name="MGR_NAME", length=30)
	private String mgrName;


	

	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	 @Temporal( TemporalType.DATE)
		@Column(name="EFFECT_DATE")
		private Date effectDate;
	 
	public OcrmFCiBelongCustmgr() {
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

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
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

	public String getCheckRight() {
		return this.checkRight;
	}

	public void setCheckRight(String checkRight) {
		this.checkRight = checkRight;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public String getMaintainRight() {
		return this.maintainRight;
	}

	public void setMaintainRight(String maintainRight) {
		this.maintainRight = maintainRight;
	}

	public String getMgrId() {
		return this.mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

}