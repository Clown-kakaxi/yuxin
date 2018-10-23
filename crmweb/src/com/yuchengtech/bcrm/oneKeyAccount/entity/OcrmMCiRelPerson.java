package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * 在我行关联人
 * 
 */
@Entity
@Table(name="OCRM_M_CI_REL_PERSON")
public class OcrmMCiRelPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	//SEQ_REL_PERSON
	@Id
	@SequenceGenerator(name="OCRM_M_CI_REL_PERSON_ID_GENERATOR", sequenceName="SEQ_REL_PERSON")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_M_CI_REL_PERSON_ID_GENERATOR")
	@Column(name="ID")
	private long id;

	@Column(name="CREATE_ORG")
	private String createOrg;

    @Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="PERSONAL_NAME")
	private String personalName;
	
	@Column(name="RELATION")
	private String relation;

	@Column(name="UPDATE_ORG")
	private String updateOrg;

    @Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public OcrmMCiRelPerson() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreateOrg() {
		return this.createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPersonalName() {
		return this.personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getUpdateOrg() {
		return this.updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}