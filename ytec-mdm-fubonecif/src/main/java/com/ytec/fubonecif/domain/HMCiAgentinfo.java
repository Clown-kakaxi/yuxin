package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiAgentinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_AGENTINFO")
public class HMCiAgentinfo implements java.io.Serializable {

	// Fields

	private HMCiAgentinfoId id;

	// Constructors

	/** default constructor */
	public HMCiAgentinfo() {
	}

	/** full constructor */
	public HMCiAgentinfo(HMCiAgentinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "agentId", column = @Column(name = "AGENT_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "agentNationCode", column = @Column(name = "AGENT_NATION_CODE", length = 20)),
			@AttributeOverride(name = "agentType", column = @Column(name = "AGENT_TYPE", length = 20)),
			@AttributeOverride(name = "agentName", column = @Column(name = "AGENT_NAME", length = 80)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "identExpiredDate", column = @Column(name = "IDENT_EXPIRED_DATE", length = 7)),
			@AttributeOverride(name = "tel", column = @Column(name = "TEL", length = 20)),
			@AttributeOverride(name = "mobile", column = @Column(name = "MOBILE", length = 20)),
			@AttributeOverride(name = "email", column = @Column(name = "EMAIL", length = 40)),
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
	public HMCiAgentinfoId getId() {
		return this.id;
	}

	public void setId(HMCiAgentinfoId id) {
		this.id = id;
	}

}