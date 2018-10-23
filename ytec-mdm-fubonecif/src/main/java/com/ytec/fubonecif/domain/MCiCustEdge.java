package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiCustEdge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CUST_EDGE")
public class MCiCustEdge implements java.io.Serializable {

	// Fields

	private String custRelId;
	private String graphId;
	private String custRelType;
	private String custRelName;
	private String custRelDesc;
	private String srcCustId;
	private String destCustId;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiCustEdge() {
	}

	/** minimal constructor */
	public MCiCustEdge(String custRelId) {
		this.custRelId = custRelId;
	}

	/** full constructor */
	public MCiCustEdge(String custRelId, String graphId, String custRelType,
			String custRelName, String custRelDesc, String srcCustId,
			String destCustId, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custRelId = custRelId;
		this.graphId = graphId;
		this.custRelType = custRelType;
		this.custRelName = custRelName;
		this.custRelDesc = custRelDesc;
		this.srcCustId = srcCustId;
		this.destCustId = destCustId;
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

	@Column(name = "GRAPH_ID", length = 20)
	public String getGraphId() {
		return this.graphId;
	}

	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}

	@Column(name = "CUST_REL_TYPE", length = 20)
	public String getCustRelType() {
		return this.custRelType;
	}

	public void setCustRelType(String custRelType) {
		this.custRelType = custRelType;
	}

	@Column(name = "CUST_REL_NAME", length = 40)
	public String getCustRelName() {
		return this.custRelName;
	}

	public void setCustRelName(String custRelName) {
		this.custRelName = custRelName;
	}

	@Column(name = "CUST_REL_DESC", length = 200)
	public String getCustRelDesc() {
		return this.custRelDesc;
	}

	public void setCustRelDesc(String custRelDesc) {
		this.custRelDesc = custRelDesc;
	}

	@Column(name = "SRC_CUST_ID", length = 20)
	public String getSrcCustId() {
		return this.srcCustId;
	}

	public void setSrcCustId(String srcCustId) {
		this.srcCustId = srcCustId;
	}

	@Column(name = "DEST_CUST_ID", length = 20)
	public String getDestCustId() {
		return this.destCustId;
	}

	public void setDestCustId(String destCustId) {
		this.destCustId = destCustId;
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