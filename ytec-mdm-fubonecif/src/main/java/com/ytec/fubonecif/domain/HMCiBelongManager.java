package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiBelongManager entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_BELONG_MANAGER")
public class HMCiBelongManager implements java.io.Serializable {

	// Fields

	private HMCiBelongManagerId id;

	// Constructors

	/** default constructor */
	public HMCiBelongManager() {
	}

	/** full constructor */
	public HMCiBelongManager(HMCiBelongManagerId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "belongManagerId", column = @Column(name = "BELONG_MANAGER_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "custManagerType", column = @Column(name = "CUST_MANAGER_TYPE", length = 20)),
			@AttributeOverride(name = "custManagerNo", column = @Column(name = "CUST_MANAGER_NO", length = 20)),
			@AttributeOverride(name = "mainType", column = @Column(name = "MAIN_TYPE", length = 20)),
			@AttributeOverride(name = "validFlag", column = @Column(name = "VALID_FLAG", length = 1)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "endDate", column = @Column(name = "END_DATE", length = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiBelongManagerId getId() {
		return this.id;
	}

	public void setId(HMCiBelongManagerId id) {
		this.id = id;
	}

}