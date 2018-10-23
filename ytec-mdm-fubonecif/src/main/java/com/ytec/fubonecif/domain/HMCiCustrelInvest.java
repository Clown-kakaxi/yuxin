package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiCustrelInvest entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_CUSTREL_INVEST")
public class HMCiCustrelInvest implements java.io.Serializable {

	// Fields

	private HMCiCustrelInvestId id;

	// Constructors

	/** default constructor */
	public HMCiCustrelInvest() {
	}

	/** full constructor */
	public HMCiCustrelInvest(HMCiCustrelInvestId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custRelId", column = @Column(name = "CUST_REL_ID", length = 20)),
			@AttributeOverride(name = "investKind", column = @Column(name = "INVEST_KIND", length = 20)),
			@AttributeOverride(name = "investDate", column = @Column(name = "INVEST_DATE", length = 7)),
			@AttributeOverride(name = "investAmt", column = @Column(name = "INVEST_AMT", precision = 17)),
			@AttributeOverride(name = "investCurr", column = @Column(name = "INVEST_CURR", length = 20)),
			@AttributeOverride(name = "investPercent", column = @Column(name = "INVEST_PERCENT", precision = 10, scale = 4)),
			@AttributeOverride(name = "investYield", column = @Column(name = "INVEST_YIELD", precision = 17)),
			@AttributeOverride(name = "stockCertNo", column = @Column(name = "STOCK_CERT_NO", length = 32)),
			@AttributeOverride(name = "isLargestHolder", column = @Column(name = "IS_LARGEST_HOLDER", length = 1)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiCustrelInvestId getId() {
		return this.id;
	}

	public void setId(HMCiCustrelInvestId id) {
		this.id = id;
	}

}