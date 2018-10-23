package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the OCRM_M_CI_ACCO_TAX database table.
 * 
 */
@Entity
@Table(name="OCRM_M_CI_ACCO_TAX")
public class OcrmMCiAccoTax implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//SEQ_ACCO_TAX;
	@Id
	@SequenceGenerator(name="OCRM_M_CI_ACCO_TAX_ID_GENERATOR", sequenceName="SEQ_ACCO_TAX")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_M_CI_ACCO_TAX_ID_GENERATOR")
	@Column(name="ID")
	private long id;

	@Column(name="CREATE_ORG")
	private String createOrg;

    @Temporal( TemporalType.DATE)	
	@Column(name="CREATE_TM")
	private Date createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="IF_NO_TIN_COUNTRY")
	private String ifNoTinCountry;

	@Column(name="IF_NO_TIN_PERSON")
	private String ifNoTinPerson;

	@Column(name="PERSON_STATEMENT")
	private String personStatement;
	
	@Column(name="REASON")
	private String reason;

	@Column(name="TAX_COUNTRY")
	private String taxCountry;

	@Column(name="TIN")
	private String tin;

	@Column(name="UPDATE_ORG")
	private String updateOrg;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_TM")
	private Date updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

	@Column(name="USAFLAG")
	private String usaflag;

	@Column(name="USDTL")
	private String usdtl;

    public OcrmMCiAccoTax() {
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

	public Date getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Date createTm) {
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

	public Date getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Date updateTm) {
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

	public String getUsdtl() {
		return this.usdtl;
	}

	public void setUsdtl(String usdtl) {
		this.usdtl = usdtl;
	}

}