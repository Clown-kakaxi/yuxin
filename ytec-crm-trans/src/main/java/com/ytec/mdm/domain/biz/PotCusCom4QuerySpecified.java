package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the ACRM_F_CI_POT_CUS_COM database table.
 */
@Entity
@Table(name = "ACRM_F_CI_POT_CUS_COM")
public class PotCusCom4QuerySpecified implements Serializable {
	@Id
	@SequenceGenerator(name = "ACRM_F_CI_POT_CUS_COM_CUSID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACRM_F_CI_POT_CUS_COM_CUSID_GENERATOR")
	@Column(name = "CUS_ID")
	private String cusId;

	@Column(name = "CUS_NAME")
	private String cusName;

	@Column(name = "CUS_ADDR")
	private String cusAddr;

	@Column(name = "ATTEN_BUSI")
	private String attenBusi;

	@Column(name = "ATTEN_NAME")
	private String attenName;

	@Column(name = "ATTEN_PHONE")
	private String attenPhone;

	@Column(name = "LEGAL_NAME")
	private String legalName;

	@Column(name = "REG_CAP_AMT")
	private BigDecimal regCapAmt;

	@Column(name = "CUS_RESOURCE")
	private String cusResource;

	@Column(name = "CUST_MGR")
	private String custMgr;

	public PotCusCom4QuerySpecified() {
		super();
	}

	public PotCusCom4QuerySpecified(String cusId, String cusName, String cusAddr, String attenBusi, String attenName, String attenPhone, String legalName, BigDecimal regCapAmt, String cusResource,
			String custMgr) {
		super();
		this.cusId = cusId;
		this.cusName = cusName;
		this.cusAddr = cusAddr;
		this.attenBusi = attenBusi;
		this.attenName = attenName;
		this.attenPhone = attenPhone;
		this.legalName = legalName;
		this.regCapAmt = regCapAmt;
		this.cusResource = cusResource;
		this.custMgr = custMgr;
	}

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusAddr() {
		return cusAddr;
	}

	public void setCusAddr(String cusAddr) {
		this.cusAddr = cusAddr;
	}

	public String getAttenBusi() {
		return attenBusi;
	}

	public void setAttenBusi(String attenBusi) {
		this.attenBusi = attenBusi;
	}

	public String getAttenName() {
		return attenName;
	}

	public void setAttenName(String attenName) {
		this.attenName = attenName;
	}

	public String getAttenPhone() {
		return attenPhone;
	}

	public void setAttenPhone(String attenPhone) {
		this.attenPhone = attenPhone;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public BigDecimal getRegCapAmt() {
		return regCapAmt;
	}

	public void setRegCapAmt(BigDecimal regCapAmt) {
		this.regCapAmt = regCapAmt;
	}

	public String getCusResource() {
		return cusResource;
	}

	public void setCusResource(String cusResource) {
		this.cusResource = cusResource;
	}

	public String getCustMgr() {
		return custMgr;
	}

	public void setCustMgr(String custMgr) {
		this.custMgr = custMgr;
	}
}