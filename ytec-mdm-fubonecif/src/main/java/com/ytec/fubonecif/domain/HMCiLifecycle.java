package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiLifecycle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_LIFECYCLE")
public class HMCiLifecycle implements java.io.Serializable {

	// Fields

	private HMCiLifecycleId id;

	// Constructors

	/** default constructor */
	public HMCiLifecycle() {
	}

	/** full constructor */
	public HMCiLifecycle(HMCiLifecycleId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "lifecycleId", column = @Column(name = "LIFECYCLE_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "lifecycleStatType", column = @Column(name = "LIFECYCLE_STAT_TYPE", length = 20)),
			@AttributeOverride(name = "lifecycleStatDesc", column = @Column(name = "LIFECYCLE_STAT_DESC", length = 80)),
			@AttributeOverride(name = "lifecycleStatDate", column = @Column(name = "LIFECYCLE_STAT_DATE", length = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiLifecycleId getId() {
		return this.id;
	}

	public void setId(HMCiLifecycleId id) {
		this.id = id;
	}

}