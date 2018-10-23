package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiSpeciallist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_SPECIALLIST")
public class HMCiSpeciallist implements java.io.Serializable {

	// Fields

	private HMCiSpeciallistId id;

	// Constructors

	/** default constructor */
	public HMCiSpeciallist() {
	}

	/** full constructor */
	public HMCiSpeciallist(HMCiSpeciallistId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "specialListId", column = @Column(name = "SPECIAL_LIST_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "specialListType", column = @Column(name = "SPECIAL_LIST_TYPE", length = 20)),
			@AttributeOverride(name = "specialListKind", column = @Column(name = "SPECIAL_LIST_KIND", length = 20)),
			@AttributeOverride(name = "specialListFlag", column = @Column(name = "SPECIAL_LIST_FLAG", length = 20)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "custName", column = @Column(name = "CUST_NAME", length = 80)),
			@AttributeOverride(name = "origin", column = @Column(name = "ORIGIN", length = 20)),
			@AttributeOverride(name = "enterReason", column = @Column(name = "ENTER_REASON", length = 20)),
			@AttributeOverride(name = "statFlag", column = @Column(name = "STAT_FLAG", length = 20)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "endDate", column = @Column(name = "END_DATE", length = 7)),
			@AttributeOverride(name = "approvalFlag", column = @Column(name = "APPROVAL_FLAG", length = 20)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiSpeciallistId getId() {
		return this.id;
	}

	public void setId(HMCiSpeciallistId id) {
		this.id = id;
	}

}