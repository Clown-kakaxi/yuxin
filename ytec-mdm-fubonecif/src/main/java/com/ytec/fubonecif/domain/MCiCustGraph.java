package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiCustGraph entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CUST_GRAPH")
public class MCiCustGraph implements java.io.Serializable {

	// Fields

	private String graphId;
	private String graphName;
	private String graphDesc;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiCustGraph() {
	}

	/** minimal constructor */
	public MCiCustGraph(String graphId) {
		this.graphId = graphId;
	}

	/** full constructor */
	public MCiCustGraph(String graphId, String graphName, String graphDesc,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.graphId = graphId;
		this.graphName = graphName;
		this.graphDesc = graphDesc;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "GRAPH_ID", unique = true, nullable = false, length = 20)
	public String getGraphId() {
		return this.graphId;
	}

	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}

	@Column(name = "GRAPH_NAME", length = 100)
	public String getGraphName() {
		return this.graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	@Column(name = "GRAPH_DESC")
	public String getGraphDesc() {
		return this.graphDesc;
	}

	public void setGraphDesc(String graphDesc) {
		this.graphDesc = graphDesc;
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