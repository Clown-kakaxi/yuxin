package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgInvestedcust entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_INVESTEDCUST")
public class HMCiOrgInvestedcust implements java.io.Serializable {

	// Fields

	private HMCiOrgInvestedcustId id;

	// Constructors

	/** default constructor */
	public HMCiOrgInvestedcust() {
	}

	/** full constructor */
	public HMCiOrgInvestedcust(HMCiOrgInvestedcustId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "investedCustId", column = @Column(name = "INVESTED_CUST_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "investedCustName", column = @Column(name = "INVESTED_CUST_NAME", length = 80)),
			@AttributeOverride(name = "investedCustOrgCode", column = @Column(name = "INVESTED_CUST_ORG_CODE", length = 40)),
			@AttributeOverride(name = "investedLoanCardNo", column = @Column(name = "INVESTED_LOAN_CARD_NO", length = 32)),
			@AttributeOverride(name = "investedLoanCardStat", column = @Column(name = "INVESTED_LOAN_CARD_STAT", length = 20)),
			@AttributeOverride(name = "investedCustRegNo", column = @Column(name = "INVESTED_CUST_REG_NO", length = 20)),
			@AttributeOverride(name = "investedCustRegAddr", column = @Column(name = "INVESTED_CUST_REG_ADDR", length = 200)),
			@AttributeOverride(name = "investedRegCapital", column = @Column(name = "INVESTED_REG_CAPITAL", precision = 17)),
			@AttributeOverride(name = "investedRegCurr", column = @Column(name = "INVESTED_REG_CURR", length = 20)),
			@AttributeOverride(name = "legalReprName", column = @Column(name = "LEGAL_REPR_NAME", length = 80)),
			@AttributeOverride(name = "legalReprIdentType", column = @Column(name = "LEGAL_REPR_IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "legalReprIdentNo", column = @Column(name = "LEGAL_REPR_IDENT_NO", length = 40)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgInvestedcustId getId() {
		return this.id;
	}

	public void setId(HMCiOrgInvestedcustId id) {
		this.id = id;
	}

}