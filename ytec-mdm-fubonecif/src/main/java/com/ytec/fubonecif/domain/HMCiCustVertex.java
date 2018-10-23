package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiCustVertex entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_CUST_VERTEX")
public class HMCiCustVertex implements java.io.Serializable {

	// Fields

	private HMCiCustVertexId id;

	// Constructors

	/** default constructor */
	public HMCiCustVertex() {
	}

	/** full constructor */
	public HMCiCustVertex(HMCiCustVertexId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "vertexId", column = @Column(name = "VERTEX_ID", length = 20)),
			@AttributeOverride(name = "graphId", column = @Column(name = "GRAPH_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "custName", column = @Column(name = "CUST_NAME", length = 80)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiCustVertexId getId() {
		return this.id;
	}

	public void setId(HMCiCustVertexId id) {
		this.id = id;
	}

}