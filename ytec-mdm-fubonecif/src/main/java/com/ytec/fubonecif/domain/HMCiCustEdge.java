package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiCustEdge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_CUST_EDGE")
public class HMCiCustEdge implements java.io.Serializable {

	// Fields

	private HMCiCustEdgeId id;

	// Constructors

	/** default constructor */
	public HMCiCustEdge() {
	}

	/** full constructor */
	public HMCiCustEdge(HMCiCustEdgeId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custRelId", column = @Column(name = "CUST_REL_ID", length = 20)),
			@AttributeOverride(name = "graphId", column = @Column(name = "GRAPH_ID", length = 20)),
			@AttributeOverride(name = "custRelType", column = @Column(name = "CUST_REL_TYPE", length = 20)),
			@AttributeOverride(name = "custRelName", column = @Column(name = "CUST_REL_NAME", length = 40)),
			@AttributeOverride(name = "custRelDesc", column = @Column(name = "CUST_REL_DESC", length = 200)),
			@AttributeOverride(name = "srcCustId", column = @Column(name = "SRC_CUST_ID", length = 20)),
			@AttributeOverride(name = "destCustId", column = @Column(name = "DEST_CUST_ID", length = 20)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiCustEdgeId getId() {
		return this.id;
	}

	public void setId(HMCiCustEdgeId id) {
		this.id = id;
	}

}