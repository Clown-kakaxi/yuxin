package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the OCRM_M_CI_TAX_MAIN database table.
 * 
 */
@Entity
@Table(name="OCRM_M_CI_TAX_MAIN")
@NamedQuery(name="OcrmMCiTaxMain.findAll", query="SELECT o FROM OcrmMCiTaxMain o")
public class OcrmMCiTaxMain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="IF_NO_TIN_COUNTRY")
	private String ifNoTinCountry;

	@Column(name="IF_NO_TIN_PERSON")
	private String ifNoTinPerson;

	@Column(name="PERSON_STATEMENT")
	private String personStatement;

	private String reason;

	@Column(name="UPDATE_ORG")
	private String updateOrg;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

	@Column(name="USAFLAG")
	private String usaflag;

	@Column(name="USTIN")
	private String ustin;

	public OcrmMCiTaxMain() {
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public String getIfNoTinCountry() {
		return this.ifNoTinCountry;
	}

	public void setIfNoTinCountry(String ifNoTinCountry) {
		this.ifNoTinCountry = ifNoTinCountry;
	}

	public String getIfNoTinPerson() {
		return this.ifNoTinPerson;
	}

	public void setIfNoTinPerson(String ifNoTinPerson) {
		this.ifNoTinPerson = ifNoTinPerson;
	}

	public String getPersonStatement() {
		return this.personStatement;
	}

	public void setPersonStatement(String personStatement) {
		this.personStatement = personStatement;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getUsaflag() {
		return this.usaflag;
	}

	public void setUsaflag(String usaflag) {
		this.usaflag = usaflag;
	}

	public String getUstin() {
		return this.ustin;
	}

	public void setUstin(String ustin) {
		this.ustin = ustin;
	}

}