package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiCrossindex entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CROSSINDEX")
public class MCiCrossindex implements java.io.Serializable {

	// Fields

	private String crossindexId;
	private String srcSysNo;
	private String srcSysCustNo;
	private String custId;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiCrossindex() {
	}

	/** minimal constructor */
	public MCiCrossindex(String crossindexId) {
		this.crossindexId = crossindexId;
	}

	/** full constructor */
	public MCiCrossindex(String crossindexId, String srcSysNo,
			String srcSysCustNo, String custId, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
		this.crossindexId = crossindexId;
		this.srcSysNo = srcSysNo;
		this.srcSysCustNo = srcSysCustNo;
		this.custId = custId;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CROSSINDEX_ID", unique = true, nullable = false, length = 20)
	public String getCrossindexId() {
		return this.crossindexId;
	}

	public void setCrossindexId(String crossindexId) {
		this.crossindexId = crossindexId;
	}

	@Column(name = "SRC_SYS_NO", length = 20)
	public String getSrcSysNo() {
		return this.srcSysNo;
	}

	public void setSrcSysNo(String srcSysNo) {
		this.srcSysNo = srcSysNo;
	}

	@Column(name = "SRC_SYS_CUST_NO", length = 32)
	public String getSrcSysCustNo() {
		return this.srcSysCustNo;
	}

	public void setSrcSysCustNo(String srcSysCustNo) {
		this.srcSysCustNo = srcSysCustNo;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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