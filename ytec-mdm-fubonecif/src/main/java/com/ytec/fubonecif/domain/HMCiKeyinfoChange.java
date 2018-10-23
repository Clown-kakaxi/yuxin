package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiKeyinfoChange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_KEYINFO_CHANGE")
public class HMCiKeyinfoChange implements java.io.Serializable {

	// Fields

	private HMCiKeyinfoChangeId id;

	// Constructors

	/** default constructor */
	public HMCiKeyinfoChange() {
	}

	/** full constructor */
	public HMCiKeyinfoChange(HMCiKeyinfoChangeId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "changeId", column = @Column(name = "CHANGE_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "changeType", column = @Column(name = "CHANGE_TYPE", length = 20)),
			@AttributeOverride(name = "infoName", column = @Column(name = "INFO_NAME", length = 100)),
			@AttributeOverride(name = "beforeInfo", column = @Column(name = "BEFORE_INFO")),
			@AttributeOverride(name = "afterInfo", column = @Column(name = "AFTER_INFO")),
			@AttributeOverride(name = "changeDate", column = @Column(name = "CHANGE_DATE", length = 7)),
			@AttributeOverride(name = "changeTime", column = @Column(name = "CHANGE_TIME", length = 11)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiKeyinfoChangeId getId() {
		return this.id;
	}

	public void setId(HMCiKeyinfoChangeId id) {
		this.id = id;
	}

}