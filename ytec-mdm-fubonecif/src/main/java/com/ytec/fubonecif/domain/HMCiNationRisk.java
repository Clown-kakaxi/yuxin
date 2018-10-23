package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiNationRisk entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_NATION_RISK")
public class HMCiNationRisk implements java.io.Serializable {

	// Fields

	private HMCiNationRiskId id;

	// Constructors

	/** default constructor */
	public HMCiNationRisk() {
	}

	/** full constructor */
	public HMCiNationRisk(HMCiNationRiskId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskNationCode", column = @Column(name = "RISK_NATION_CODE", length = 20)),
			@AttributeOverride(name = "riskLevel", column = @Column(name = "RISK_LEVEL", length = 20)),
			@AttributeOverride(name = "rate", column = @Column(name = "RATE", precision = 10, scale = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiNationRiskId getId() {
		return this.id;
	}

	public void setId(HMCiNationRiskId id) {
		this.id = id;
	}

}