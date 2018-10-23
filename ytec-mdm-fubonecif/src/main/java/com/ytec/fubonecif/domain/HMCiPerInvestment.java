package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerInvestment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_INVESTMENT")
public class HMCiPerInvestment implements java.io.Serializable {

	// Fields

	private HMCiPerInvestmentId id;

	// Constructors

	/** default constructor */
	public HMCiPerInvestment() {
	}

	/** full constructor */
	public HMCiPerInvestment(HMCiPerInvestmentId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "investmentId", column = @Column(name = "INVESTMENT_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "investAim", column = @Column(name = "INVEST_AIM", length = 20)),
			@AttributeOverride(name = "investExpect", column = @Column(name = "INVEST_EXPECT", length = 20)),
			@AttributeOverride(name = "investType", column = @Column(name = "INVEST_TYPE", length = 20)),
			@AttributeOverride(name = "investAmt", column = @Column(name = "INVEST_AMT", precision = 17)),
			@AttributeOverride(name = "investCurr", column = @Column(name = "INVEST_CURR", length = 20)),
			@AttributeOverride(name = "investYield", column = @Column(name = "INVEST_YIELD", precision = 10, scale = 6)),
			@AttributeOverride(name = "investIncome", column = @Column(name = "INVEST_INCOME", precision = 17)),
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
	public HMCiPerInvestmentId getId() {
		return this.id;
	}

	public void setId(HMCiPerInvestmentId id) {
		this.id = id;
	}

}