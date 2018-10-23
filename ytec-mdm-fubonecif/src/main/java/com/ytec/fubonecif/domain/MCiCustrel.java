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
 * MCiCustrel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CUSTREL")
public class MCiCustrel implements java.io.Serializable {

	// Fields

	private String custRelId;
	private String custRelType;
	private String custRelDesc;
	private String custRelStat;
	private String custRelDirect;
	private String srcCustId;
	private String srcCustName;
	private String destCustId;
	private String destCustName;
	private Date relStartDate;
	private Date relEndDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiCustrel() {
	}

	/** minimal constructor */
	public MCiCustrel(String custRelId) {
		this.custRelId = custRelId;
	}

	/** full constructor */
	public MCiCustrel(String custRelId, String custRelType, String custRelDesc,
			String custRelStat, String custRelDirect, String srcCustId,
			String srcCustName, String destCustId, String destCustName,
			Date relStartDate, Date relEndDate, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
		this.custRelId = custRelId;
		this.custRelType = custRelType;
		this.custRelDesc = custRelDesc;
		this.custRelStat = custRelStat;
		this.custRelDirect = custRelDirect;
		this.srcCustId = srcCustId;
		this.srcCustName = srcCustName;
		this.destCustId = destCustId;
		this.destCustName = destCustName;
		this.relStartDate = relStartDate;
		this.relEndDate = relEndDate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_REL_ID", unique = true, nullable = false, length = 20)
	public String getCustRelId() {
		return this.custRelId;
	}

	public void setCustRelId(String custRelId) {
		this.custRelId = custRelId;
	}

	@Column(name = "CUST_REL_TYPE", length = 20)
	public String getCustRelType() {
		return this.custRelType;
	}

	public void setCustRelType(String custRelType) {
		this.custRelType = custRelType;
	}

	@Column(name = "CUST_REL_DESC", length = 200)
	public String getCustRelDesc() {
		return this.custRelDesc;
	}

	public void setCustRelDesc(String custRelDesc) {
		this.custRelDesc = custRelDesc;
	}

	@Column(name = "CUST_REL_STAT", length = 20)
	public String getCustRelStat() {
		return this.custRelStat;
	}

	public void setCustRelStat(String custRelStat) {
		this.custRelStat = custRelStat;
	}

	@Column(name = "CUST_REL_DIRECT", length = 20)
	public String getCustRelDirect() {
		return this.custRelDirect;
	}

	public void setCustRelDirect(String custRelDirect) {
		this.custRelDirect = custRelDirect;
	}

	@Column(name = "SRC_CUST_ID", length = 20)
	public String getSrcCustId() {
		return this.srcCustId;
	}

	public void setSrcCustId(String srcCustId) {
		this.srcCustId = srcCustId;
	}

	@Column(name = "SRC_CUST_NAME", length = 80)
	public String getSrcCustName() {
		return this.srcCustName;
	}

	public void setSrcCustName(String srcCustName) {
		this.srcCustName = srcCustName;
	}

	@Column(name = "DEST_CUST_ID", length = 20)
	public String getDestCustId() {
		return this.destCustId;
	}

	public void setDestCustId(String destCustId) {
		this.destCustId = destCustId;
	}

	@Column(name = "DEST_CUST_NAME", length = 80)
	public String getDestCustName() {
		return this.destCustName;
	}

	public void setDestCustName(String destCustName) {
		this.destCustName = destCustName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REL_START_DATE", length = 7)
	public Date getRelStartDate() {
		return this.relStartDate;
	}

	public void setRelStartDate(Date relStartDate) {
		this.relStartDate = relStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REL_END_DATE", length = 7)
	public Date getRelEndDate() {
		return this.relEndDate;
	}

	public void setRelEndDate(Date relEndDate) {
		this.relEndDate = relEndDate;
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