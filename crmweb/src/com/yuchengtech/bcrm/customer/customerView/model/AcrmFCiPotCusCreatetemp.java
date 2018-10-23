package com.yuchengtech.bcrm.customer.customerView.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the ACRM_F_CI_POT_CUS_CREATETEMP database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_POT_CUS_CREATETEMP")
public class AcrmFCiPotCusCreatetemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUS_ID")
	private String cusId;
	
	@Column(name="ATTEN_NAME")
	private String attenName;

	@Column(name="CALL_NO")
	private String callNo;

	@Column(name="CERT_CODE")
	private String certCode;

	@Column(name="CERT_TYPE")
	private String certType;

	private String conclusion;

	@Column(name="CONTMETH_INFO")
	private String contmethInfo;

	@Column(name="CUS_ADDR")
	private String cusAddr;

	@Column(name="CUS_NAME")
	private String cusName;

	@Column(name="CUS_NATIONALITY")
	private String cusNationality;

	@Column(name="CUST_MGR")
	private String custMgr;

	@Column(name="CUST_TYPE")
	private String custType;

	@Column(name="INDUST_TYPE")
	private String industType;

	@Column(name="INPUT_BR_ID")
	private String inputBrId;

	@Column(name="INPUT_DATE")
	private String inputDate;

	@Column(name="INPUT_ID")
	private String inputId;

	@Column(name="JOB_TYPE")
	private String jobType;

	@Column(name="MAIN_BR_ID")
	private String mainBrId;

	@Column(name="MKT_ACTIVITIE")
	private String mktActivitie;

	@Column(name="REFEREES_ID")
	private String refereesId;

	@Column(name="SOURCE_CHANNEL")
	private String sourceChannel;

	private String zipcode;

	public AcrmFCiPotCusCreatetemp() {
	}

	public String getAttenName() {
		return this.attenName;
	}

	public void setAttenName(String attenName) {
		this.attenName = attenName;
	}

	public String getCallNo() {
		return this.callNo;
	}

	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}

	public String getCertCode() {
		return this.certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getCertType() {
		return this.certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getConclusion() {
		return this.conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	public String getCusAddr() {
		return this.cusAddr;
	}

	public void setCusAddr(String cusAddr) {
		this.cusAddr = cusAddr;
	}

	public String getCusId() {
		return this.cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusNationality() {
		return this.cusNationality;
	}

	public void setCusNationality(String cusNationality) {
		this.cusNationality = cusNationality;
	}

	public String getCustMgr() {
		return this.custMgr;
	}

	public void setCustMgr(String custMgr) {
		this.custMgr = custMgr;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getIndustType() {
		return this.industType;
	}

	public void setIndustType(String industType) {
		this.industType = industType;
	}

	public String getInputBrId() {
		return this.inputBrId;
	}

	public void setInputBrId(String inputBrId) {
		this.inputBrId = inputBrId;
	}

	public String getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputId() {
		return this.inputId;
	}

	public void setInputId(String inputId) {
		this.inputId = inputId;
	}

	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getMainBrId() {
		return this.mainBrId;
	}

	public void setMainBrId(String mainBrId) {
		this.mainBrId = mainBrId;
	}

	public String getMktActivitie() {
		return this.mktActivitie;
	}

	public void setMktActivitie(String mktActivitie) {
		this.mktActivitie = mktActivitie;
	}

	public String getRefereesId() {
		return this.refereesId;
	}

	public void setRefereesId(String refereesId) {
		this.refereesId = refereesId;
	}

	public String getSourceChannel() {
		return this.sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}