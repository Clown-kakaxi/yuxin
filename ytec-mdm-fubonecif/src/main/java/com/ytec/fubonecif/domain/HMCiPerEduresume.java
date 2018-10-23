package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerEduresume entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_EDURESUME")
public class HMCiPerEduresume implements java.io.Serializable {

	// Fields

	private HMCiPerEduresumeId id;

	// Constructors

	/** default constructor */
	public HMCiPerEduresume() {
	}

	/** full constructor */
	public HMCiPerEduresume(HMCiPerEduresumeId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "eduresumeId", column = @Column(name = "EDURESUME_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "endDate", column = @Column(name = "END_DATE", length = 7)),
			@AttributeOverride(name = "university", column = @Column(name = "UNIVERSITY", length = 80)),
			@AttributeOverride(name = "college", column = @Column(name = "COLLEGE", length = 80)),
			@AttributeOverride(name = "major", column = @Column(name = "MAJOR", length = 80)),
			@AttributeOverride(name = "eduSys", column = @Column(name = "EDU_SYS", length = 80)),
			@AttributeOverride(name = "certificateNo", column = @Column(name = "CERTIFICATE_NO", length = 32)),
			@AttributeOverride(name = "diplomaNo", column = @Column(name = "DIPLOMA_NO", length = 32)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerEduresumeId getId() {
		return this.id;
	}

	public void setId(HMCiPerEduresumeId id) {
		this.id = id;
	}

}