package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the ACRM_F_CI_SPECIALLIST database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_SPECIALLIST")
public class AcrmFCiSpeciallist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_SPECIALLIST_SPECIALLISTID_GENERATOR", sequenceName="ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_SPECIALLIST_SPECIALLISTID_GENERATOR")
	@Column(name="SPECIAL_LIST_ID")
	private String specialListId;

	@Column(name="APPROVAL_FLAG")
	private String approvalFlag;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="ENTER_REASON")
	private String enterReason;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	private String origin;

	@Column(name="SPECIAL_LIST_FLAG")
	private String specialListFlag;

	@Column(name="SPECIAL_LIST_KIND")
	private String specialListKind;

	@Column(name="SPECIAL_LIST_TYPE")
	private String specialListType;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="STAT_FLAG")
	private String statFlag;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

    public AcrmFCiSpeciallist() {
    }

	public String getSpecialListId() {
		return this.specialListId;
	}

	public void setSpecialListId(String specialListId) {
		this.specialListId = specialListId;
	}

	public String getApprovalFlag() {
		return this.approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEnterReason() {
		return this.enterReason;
	}

	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getSpecialListFlag() {
		return this.specialListFlag;
	}

	public void setSpecialListFlag(String specialListFlag) {
		this.specialListFlag = specialListFlag;
	}

	public String getSpecialListKind() {
		return this.specialListKind;
	}

	public void setSpecialListKind(String specialListKind) {
		this.specialListKind = specialListKind;
	}

	public String getSpecialListType() {
		return this.specialListType;
	}

	public void setSpecialListType(String specialListType) {
		this.specialListType = specialListType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatFlag() {
		return this.statFlag;
	}

	public void setStatFlag(String statFlag) {
		this.statFlag = statFlag;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}