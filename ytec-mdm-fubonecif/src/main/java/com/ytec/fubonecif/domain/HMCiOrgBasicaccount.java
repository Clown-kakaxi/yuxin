package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgBasicaccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_BASICACCOUNT")
public class HMCiOrgBasicaccount implements java.io.Serializable {

	// Fields

	private HMCiOrgBasicaccountId id;

	// Constructors

	/** default constructor */
	public HMCiOrgBasicaccount() {
	}

	/** full constructor */
	public HMCiOrgBasicaccount(HMCiOrgBasicaccountId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "basicAcctBankNo", column = @Column(name = "BASIC_ACCT_BANK_NO", length = 20)),
			@AttributeOverride(name = "basicAcctBankName", column = @Column(name = "BASIC_ACCT_BANK_NAME", length = 100)),
			@AttributeOverride(name = "basicAcctNo", column = @Column(name = "BASIC_ACCT_NO", length = 32)),
			@AttributeOverride(name = "basicAcctName", column = @Column(name = "BASIC_ACCT_NAME", length = 60)),
			@AttributeOverride(name = "basicAcctStat", column = @Column(name = "BASIC_ACCT_STAT", length = 20)),
			@AttributeOverride(name = "basicAcctLicNo", column = @Column(name = "BASIC_ACCT_LIC_NO", length = 20)),
			@AttributeOverride(name = "basicAcctOpenDate", column = @Column(name = "BASIC_ACCT_OPEN_DATE", length = 7)),
			@AttributeOverride(name = "basicAcctVerifyDate", column = @Column(name = "BASIC_ACCT_VERIFY_DATE", length = 7)),
			@AttributeOverride(name = "isOwnBank", column = @Column(name = "IS_OWN_BANK", length = 1)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgBasicaccountId getId() {
		return this.id;
	}

	public void setId(HMCiOrgBasicaccountId id) {
		this.id = id;
	}

}