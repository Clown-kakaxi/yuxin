package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgAuth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_AUTH")
public class HMCiOrgAuth implements java.io.Serializable {

	// Fields

	private HMCiOrgAuthId id;

	// Constructors

	/** default constructor */
	public HMCiOrgAuth() {
	}

	/** full constructor */
	public HMCiOrgAuth(HMCiOrgAuthId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "orgAuthId", column = @Column(name = "ORG_AUTH_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "authType", column = @Column(name = "AUTH_TYPE", length = 20)),
			@AttributeOverride(name = "authOrg", column = @Column(name = "AUTH_ORG", length = 80)),
			@AttributeOverride(name = "authResult", column = @Column(name = "AUTH_RESULT", length = 200)),
			@AttributeOverride(name = "certName", column = @Column(name = "CERT_NAME", length = 80)),
			@AttributeOverride(name = "certNo", column = @Column(name = "CERT_NO", length = 32)),
			@AttributeOverride(name = "authDate", column = @Column(name = "AUTH_DATE", length = 7)),
			@AttributeOverride(name = "validDate", column = @Column(name = "VALID_DATE", length = 7)),
			@AttributeOverride(name = "effectiveDate", column = @Column(name = "EFFECTIVE_DATE", length = 7)),
			@AttributeOverride(name = "expiredDate", column = @Column(name = "EXPIRED_DATE", length = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgAuthId getId() {
		return this.id;
	}

	public void setId(HMCiOrgAuthId id) {
		this.id = id;
	}

}