package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiSpeciallist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_SPECIALLIST")
public class MCiSpeciallist implements java.io.Serializable {

	// Fields

	private String specialListId;
	private String custId;
	private String specialListType;
	private String specialListKind;
	private String specialListFlag;
	private String identType;
	private String identNo;
	private String custName;
	private String origin;
	private String enterReason;
	private String statFlag;
	private Date startDate;
	private Date endDate;
	private String approvalFlag;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiSpeciallist() {
	}

	/** minimal constructor */
	public MCiSpeciallist(String specialListId) {
		this.specialListId = specialListId;
	}

	/** full constructor */
	public MCiSpeciallist(String specialListId, String custId,
			String specialListType, String specialListKind,
			String specialListFlag, String identType, String identNo,
			String custName, String origin, String enterReason,
			String statFlag, Date startDate, Date endDate, String approvalFlag,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.specialListId = specialListId;
		this.custId = custId;
		this.specialListType = specialListType;
		this.specialListKind = specialListKind;
		this.specialListFlag = specialListFlag;
		this.identType = identType;
		this.identNo = identNo;
		this.custName = custName;
		this.origin = origin;
		this.enterReason = enterReason;
		this.statFlag = statFlag;
		this.startDate = startDate;
		this.endDate = endDate;
		this.approvalFlag = approvalFlag;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "SPECIAL_LIST_ID", unique = true, nullable = false, length = 20)
	public String getSpecialListId() {
		return this.specialListId;
	}

	public void setSpecialListId(String specialListId) {
		this.specialListId = specialListId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "SPECIAL_LIST_TYPE", length = 20)
	public String getSpecialListType() {
		return this.specialListType;
	}

	public void setSpecialListType(String specialListType) {
		this.specialListType = specialListType;
	}

	@Column(name = "SPECIAL_LIST_KIND", length = 20)
	public String getSpecialListKind() {
		return this.specialListKind;
	}

	public void setSpecialListKind(String specialListKind) {
		this.specialListKind = specialListKind;
	}

	@Column(name = "SPECIAL_LIST_FLAG", length = 20)
	public String getSpecialListFlag() {
		return this.specialListFlag;
	}

	public void setSpecialListFlag(String specialListFlag) {
		this.specialListFlag = specialListFlag;
	}

	@Column(name = "IDENT_TYPE", length = 20)
	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	@Column(name = "IDENT_NO", length = 40)
	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	@Column(name = "CUST_NAME", length = 80)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name = "ORIGIN", length = 20)
	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(name = "ENTER_REASON", length = 100)
	public String getEnterReason() {
		return this.enterReason;
	}

	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

	@Column(name = "STAT_FLAG", length = 20)
	public String getStatFlag() {
		return this.statFlag;
	}

	public void setStatFlag(String statFlag) {
		this.statFlag = statFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "APPROVAL_FLAG", length = 20)
	public String getApprovalFlag() {
		return this.approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}