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
 * MCiLifecycle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_LIFECYCLE")
public class MCiLifecycle implements java.io.Serializable {

	// Fields

	private String lifecycleId;
	private String custId;
	private String lifecycleStatType;
	private String lifecycleStatDesc;
	private Date lifecycleStatDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiLifecycle() {
	}

	/** minimal constructor */
	public MCiLifecycle(String lifecycleId) {
		this.lifecycleId = lifecycleId;
	}

	/** full constructor */
	public MCiLifecycle(String lifecycleId, String custId,
			String lifecycleStatType, String lifecycleStatDesc,
			Date lifecycleStatDate, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
		this.lifecycleId = lifecycleId;
		this.custId = custId;
		this.lifecycleStatType = lifecycleStatType;
		this.lifecycleStatDesc = lifecycleStatDesc;
		this.lifecycleStatDate = lifecycleStatDate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "LIFECYCLE_ID", unique = true, nullable = false, length = 20)
	public String getLifecycleId() {
		return this.lifecycleId;
	}

	public void setLifecycleId(String lifecycleId) {
		this.lifecycleId = lifecycleId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "LIFECYCLE_STAT_TYPE", length = 20)
	public String getLifecycleStatType() {
		return this.lifecycleStatType;
	}

	public void setLifecycleStatType(String lifecycleStatType) {
		this.lifecycleStatType = lifecycleStatType;
	}

	@Column(name = "LIFECYCLE_STAT_DESC", length = 80)
	public String getLifecycleStatDesc() {
		return this.lifecycleStatDesc;
	}

	public void setLifecycleStatDesc(String lifecycleStatDesc) {
		this.lifecycleStatDesc = lifecycleStatDesc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LIFECYCLE_STAT_DATE", length = 7)
	public Date getLifecycleStatDate() {
		return this.lifecycleStatDate;
	}

	public void setLifecycleStatDate(Date lifecycleStatDate) {
		this.lifecycleStatDate = lifecycleStatDate;
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