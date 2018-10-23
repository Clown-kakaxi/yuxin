package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiIdentityVerify entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_IDENTITY_VERIFY")
public class HMCiIdentityVerify implements java.io.Serializable {

	// Fields

	private HMCiIdentityVerifyId id;

	// Constructors

	/** default constructor */
	public HMCiIdentityVerify() {
	}

	/** full constructor */
	public HMCiIdentityVerify(HMCiIdentityVerifyId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "verifyStat", column = @Column(name = "VERIFY_STAT", length = 20)),
			@AttributeOverride(name = "verifyResult", column = @Column(name = "VERIFY_RESULT", length = 20)),
			@AttributeOverride(name = "reason", column = @Column(name = "REASON", length = 80)),
			@AttributeOverride(name = "dealWay", column = @Column(name = "DEAL_WAY", length = 80)),
			@AttributeOverride(name = "verifyBranchNo", column = @Column(name = "VERIFY_BRANCH_NO", length = 20)),
			@AttributeOverride(name = "verifyTellerNo", column = @Column(name = "VERIFY_TELLER_NO", length = 20)),
			@AttributeOverride(name = "verifyDate", column = @Column(name = "VERIFY_DATE", length = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiIdentityVerifyId getId() {
		return this.id;
	}

	public void setId(HMCiIdentityVerifyId id) {
		this.id = id;
	}

}