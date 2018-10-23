package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the OCRM_M_CI_TAX_SUB database table.
 * 
 */
@Entity
@Table(name="OCRM_M_CI_TAX_SUB")
@NamedQuery(name="OcrmMCiTaxSub.findAll", query="SELECT o FROM OcrmMCiTaxSub o")
public class OcrmMCiTaxSub implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="IF_NO_TIN_COUNTRY")
	private String ifNoTinCountry;

	@Column(name="IF_NO_TIN_PERSON")
	private String ifNoTinPerson;

	private String reason;

	@Column(name="TAX_COUNTRY")
	private String taxCountry;

	private String tin;

	@Column(name="UPDATE_ORG")
	private String updateOrg;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

	public OcrmMCiTaxSub() {
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

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTaxCountry() {
		return this.taxCountry;
	}

	public void setTaxCountry(String taxCountry) {
		this.taxCountry = taxCountry;
	}

	public String getTin() {
		return this.tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
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