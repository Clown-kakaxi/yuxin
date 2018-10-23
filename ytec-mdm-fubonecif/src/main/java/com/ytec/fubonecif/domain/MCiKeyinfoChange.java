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
 * MCiKeyinfoChange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_KEYINFO_CHANGE")
public class MCiKeyinfoChange implements java.io.Serializable {

	// Fields

	private String changeId;
	private String custId;
	private String changeType;
	private String infoName;
	private String beforeInfo;
	private String afterInfo;
	private Date changeDate;
	private Timestamp changeTime;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiKeyinfoChange() {
	}

	/** minimal constructor */
	public MCiKeyinfoChange(String changeId) {
		this.changeId = changeId;
	}

	/** full constructor */
	public MCiKeyinfoChange(String changeId, String custId, String changeType,
			String infoName, String beforeInfo, String afterInfo,
			Date changeDate, Timestamp changeTime, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
		this.changeId = changeId;
		this.custId = custId;
		this.changeType = changeType;
		this.infoName = infoName;
		this.beforeInfo = beforeInfo;
		this.afterInfo = afterInfo;
		this.changeDate = changeDate;
		this.changeTime = changeTime;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CHANGE_ID", unique = true, nullable = false, length = 20)
	public String getChangeId() {
		return this.changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "CHANGE_TYPE", length = 20)
	public String getChangeType() {
		return this.changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	@Column(name = "INFO_NAME", length = 100)
	public String getInfoName() {
		return this.infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	@Column(name = "BEFORE_INFO")
	public String getBeforeInfo() {
		return this.beforeInfo;
	}

	public void setBeforeInfo(String beforeInfo) {
		this.beforeInfo = beforeInfo;
	}

	@Column(name = "AFTER_INFO")
	public String getAfterInfo() {
		return this.afterInfo;
	}

	public void setAfterInfo(String afterInfo) {
		this.afterInfo = afterInfo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CHANGE_DATE", length = 7)
	public Date getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	@Column(name = "CHANGE_TIME", length = 11)
	public Timestamp getChangeTime() {
		return this.changeTime;
	}

	public void setChangeTime(Timestamp changeTime) {
		this.changeTime = changeTime;
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