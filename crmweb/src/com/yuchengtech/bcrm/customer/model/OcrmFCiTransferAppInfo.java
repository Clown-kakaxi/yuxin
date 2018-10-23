package com.yuchengtech.bcrm.customer.model;

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
 * The persistent class for the OCRM_F_CI_TRANSFER_APP_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_TRANSFER_APP_INFO")
public class OcrmFCiTransferAppInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_TRANSFER_APP_INFO_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_TRANSFER_APP_INFO_ID_GENERATOR")
	private Long id;

	@Column(name="AFTER_ORG_ID")
	private String afterOrgId;

    @Temporal( TemporalType.DATE)
	@Column(name="APP_DATE1")
	private Date appDate1;

    @Temporal( TemporalType.DATE)
	@Column(name="APP_DATE2")
	private Date appDate2;

    @Temporal( TemporalType.DATE)
	@Column(name="APP_DATE3")
	private Date appDate3;

	@Column(name="APP_ORG_ID1")
	private String appOrgId1;

	@Column(name="APP_ORG_ID2")
	private String appOrgId2;

	@Column(name="APP_ORG_ID3")
	private String appOrgId3;

	@Column(name="APP_STATUS")
	private String appStatus;

	@Column(name="APP_USER_ID1")
	private String appUserId1;

	@Column(name="APP_USER_ID2")
	private String appUserId2;

	@Column(name="APP_USER_ID3")
	private String appUserId3;

	@Column(name="APP_USER_NAME1")
	private String appUserName1;

	@Column(name="APP_USER_NAME2")
	private String appUserName2;

	@Column(name="APP_USER_NAME3")
	private String appUserName3;

    @Temporal( TemporalType.DATE)
	@Column(name="APPLY_DATE")
	private Date applyDate;

	@Column(name="APPLY_USER_ID")
	private String applyUserId;

	@Column(name="APPLY_USER_NAME")
	private String applyUserName;

	@Column(name="BEFORE_ORG_ID")
	private String beforeOrgId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="NEW_MGR_ID")
	private String newMgrId;

	@Column(name="NEW_MGR_NAME")
	private String newMgrName;

	private String notes;

	@Column(name="OLD_MGR_ID")
	private String oldMgrId;

	@Column(name="OLD_MGR_NAME")
	private String oldMgrName;

	@Column(name="OPER_RESULT")
	private String operResult;

	@Column(name="OPER_TYPE")
	private String operType;
	
	@Column(name="SIGN_ID")
	private String signId;
	
	@Column(name="TEMP_ORG_ID")
	private String tempOrgId;

	private String remark;

	@Column(name="RIGHT_TYPE")
	private String rightType;

	@Column(name="BEFORE_ORG_NAME")
	private String beforeOrgName;
	
	@Column(name="AFTER_ORG_NAME")
	private String afterOrgName;
	
	@Column(name="REFUSE_REASON")
	private String refuseReason;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAfterOrgId() {
		return afterOrgId;
	}

	public void setAfterOrgId(String afterOrgId) {
		this.afterOrgId = afterOrgId;
	}

	public Date getAppDate1() {
		return appDate1;
	}

	public void setAppDate1(Date appDate1) {
		this.appDate1 = appDate1;
	}

	public Date getAppDate2() {
		return appDate2;
	}

	public void setAppDate2(Date appDate2) {
		this.appDate2 = appDate2;
	}

	public Date getAppDate3() {
		return appDate3;
	}

	public void setAppDate3(Date appDate3) {
		this.appDate3 = appDate3;
	}

	public String getAppOrgId1() {
		return appOrgId1;
	}

	public void setAppOrgId1(String appOrgId1) {
		this.appOrgId1 = appOrgId1;
	}

	public String getAppOrgId2() {
		return appOrgId2;
	}

	public void setAppOrgId2(String appOrgId2) {
		this.appOrgId2 = appOrgId2;
	}

	public String getAppOrgId3() {
		return appOrgId3;
	}

	public void setAppOrgId3(String appOrgId3) {
		this.appOrgId3 = appOrgId3;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getAppUserId1() {
		return appUserId1;
	}

	public void setAppUserId1(String appUserId1) {
		this.appUserId1 = appUserId1;
	}

	public String getAppUserId2() {
		return appUserId2;
	}

	public void setAppUserId2(String appUserId2) {
		this.appUserId2 = appUserId2;
	}

	public String getAppUserId3() {
		return appUserId3;
	}

	public void setAppUserId3(String appUserId3) {
		this.appUserId3 = appUserId3;
	}

	public String getAppUserName1() {
		return appUserName1;
	}

	public void setAppUserName1(String appUserName1) {
		this.appUserName1 = appUserName1;
	}

	public String getAppUserName2() {
		return appUserName2;
	}

	public void setAppUserName2(String appUserName2) {
		this.appUserName2 = appUserName2;
	}

	public String getAppUserName3() {
		return appUserName3;
	}

	public void setAppUserName3(String appUserName3) {
		this.appUserName3 = appUserName3;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getBeforeOrgId() {
		return beforeOrgId;
	}

	public void setBeforeOrgId(String beforeOrgId) {
		this.beforeOrgId = beforeOrgId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getNewMgrId() {
		return newMgrId;
	}

	public void setNewMgrId(String newMgrId) {
		this.newMgrId = newMgrId;
	}

	public String getNewMgrName() {
		return newMgrName;
	}

	public void setNewMgrName(String newMgrName) {
		this.newMgrName = newMgrName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOldMgrId() {
		return oldMgrId;
	}

	public void setOldMgrId(String oldMgrId) {
		this.oldMgrId = oldMgrId;
	}

	public String getOldMgrName() {
		return oldMgrName;
	}

	public void setOldMgrName(String oldMgrName) {
		this.oldMgrName = oldMgrName;
	}

	public String getOperResult() {
		return operResult;
	}

	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public String getTempOrgId() {
		return tempOrgId;
	}

	public String getBeforeOrgName() {
		return beforeOrgName;
	}

	public void setBeforeOrgName(String beforeOrgName) {
		this.beforeOrgName = beforeOrgName;
	}

	public String getAfterOrgName() {
		return afterOrgName;
	}

	public void setAfterOrgName(String afterOrgName) {
		this.afterOrgName = afterOrgName;
	}

	public void setTempOrgId(String tempOrgId) {
		this.tempOrgId = tempOrgId;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}	
}