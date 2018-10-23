package com.yuchengtech.bcrm.callReport.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the OCRM_F_CI_CALLREPORT_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CALLREPORT_INFO")
@NamedQuery(name="OcrmFCiCallreportInfo.findAll", query="SELECT o FROM OcrmFCiCallreportInfo o")
public class OcrmFCiCallreportInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Lob
	@Column(name="CALLREPORT_INFO")
	private String callreportInfo;

	@Column(name="CREATE_ORG")
	private String createOrg;

	//@Temporal(TemporalType.DATE)
	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_ID")
	private String custId;

//	@Temporal(TemporalType.DATE)
//	@Column(name="REMIND_TM")
//	private Date remindTm;

//	@Column(name="REMIND_TYPE")
//	private String remindType;

	@Column(name="UPDATE_ORG")
	private String updateOrg;

	//@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;
	
	@Column(name="CUST_NAME")
	private String custName;
	
	@Column(name="CREATE_USERNAME")
	private String createUsername;
	
	@Column(name="UPDATE_USERNAME")
	private String updateUsername;
	
	@Column(name="MANAGER_OPINION")
	private String managerOpinion;
	
	@Column(name="MANAGER_USER")
	private String managerUser;
	
	@Column(name="MANAGER_USER_NAME")
	private String managerUserName;

	public OcrmFCiCallreportInfo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCallreportInfo() {
		return this.callreportInfo;
	}

	public void setCallreportInfo(String callreportInfo) {
		this.callreportInfo = callreportInfo;
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

//	public Date getRemindTm() {
//		return this.remindTm;
//	}
//
//	public void setRemindTm(Date remindTm) {
//		this.remindTm = remindTm;
//	}
//
//	public String getRemindType() {
//		return this.remindType;
//	}
//
//	public void setRemindType(String remindType) {
//		this.remindType = remindType;
//	}

	public String getUpdateOrg() {
		return this.updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public Date getUpdateTm() {
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getUpdateUsername() {
		return updateUsername;
	}

	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}

	public String getManagerOpinion() {
		return managerOpinion;
	}

	public void setManagerOpinion(String managerOpinion) {
		this.managerOpinion = managerOpinion;
	}

	public String getManagerUser() {
		return managerUser;
	}

	public void setManagerUser(String managerUser) {
		this.managerUser = managerUser;
	}

	public String getManagerUserName() {
		return managerUserName;
	}

	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}

}