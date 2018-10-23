package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiCustVertex entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CUST_VERTEX")
public class MCiCustVertex implements java.io.Serializable {

	// Fields

	private String vertexId;
	private String graphId;
	private String custId;
	private String custName;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiCustVertex() {
	}

	/** minimal constructor */
	public MCiCustVertex(String vertexId) {
		this.vertexId = vertexId;
	}

	/** full constructor */
	public MCiCustVertex(String vertexId, String graphId, String custId,
			String custName, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.vertexId = vertexId;
		this.graphId = graphId;
		this.custId = custId;
		this.custName = custName;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "VERTEX_ID", unique = true, nullable = false, length = 20)
	public String getVertexId() {
		return this.vertexId;
	}

	public void setVertexId(String vertexId) {
		this.vertexId = vertexId;
	}

	@Column(name = "GRAPH_ID", length = 20)
	public String getGraphId() {
		return this.graphId;
	}

	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "CUST_NAME", length = 80)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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