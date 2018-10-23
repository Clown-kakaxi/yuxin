package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiCrossindex entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_CROSSINDEX")
public class HMCiCrossindex implements java.io.Serializable {

	// Fields

	private HMCiCrossindexId id;

	// Constructors

	/** default constructor */
	public HMCiCrossindex() {
	}

	/** full constructor */
	public HMCiCrossindex(HMCiCrossindexId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "crossindexId", column = @Column(name = "CROSSINDEX_ID", length = 20)),
			@AttributeOverride(name = "srcSysNo", column = @Column(name = "SRC_SYS_NO", length = 20)),
			@AttributeOverride(name = "srcSysCustNo", column = @Column(name = "SRC_SYS_CUST_NO", length = 32)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiCrossindexId getId() {
		return this.id;
	}

	public void setId(HMCiCrossindexId id) {
		this.id = id;
	}

}