package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgIssuebond entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_ISSUEBOND")
public class HMCiOrgIssuebond implements java.io.Serializable {

	// Fields

	private HMCiOrgIssuebondId id;

	// Constructors

	/** default constructor */
	public HMCiOrgIssuebond() {
	}

	/** full constructor */
	public HMCiOrgIssuebond(HMCiOrgIssuebondId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "issueBondId", column = @Column(name = "ISSUE_BOND_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "exchangeCountryCode", column = @Column(name = "EXCHANGE_COUNTRY_CODE", length = 20)),
			@AttributeOverride(name = "exchangeCode", column = @Column(name = "EXCHANGE_CODE", length = 20)),
			@AttributeOverride(name = "exchangeName", column = @Column(name = "EXCHANGE_NAME", length = 80)),
			@AttributeOverride(name = "bondType", column = @Column(name = "BOND_TYPE", length = 20)),
			@AttributeOverride(name = "bondKind", column = @Column(name = "BOND_KIND", length = 20)),
			@AttributeOverride(name = "bondState", column = @Column(name = "BOND_STATE", length = 20)),
			@AttributeOverride(name = "bondTerm", column = @Column(name = "BOND_TERM", precision = 22, scale = 0)),
			@AttributeOverride(name = "bondName", column = @Column(name = "BOND_NAME", length = 80)),
			@AttributeOverride(name = "bondCode", column = @Column(name = "BOND_CODE", length = 32)),
			@AttributeOverride(name = "bondGrade", column = @Column(name = "BOND_GRADE", length = 20)),
			@AttributeOverride(name = "bondSeller", column = @Column(name = "BOND_SELLER", length = 80)),
			@AttributeOverride(name = "bondWarrantor", column = @Column(name = "BOND_WARRANTOR", length = 80)),
			@AttributeOverride(name = "isMarket", column = @Column(name = "IS_MARKET", length = 1)),
			@AttributeOverride(name = "issueDate", column = @Column(name = "ISSUE_DATE", length = 7)),
			@AttributeOverride(name = "issueAmt", column = @Column(name = "ISSUE_AMT", precision = 17)),
			@AttributeOverride(name = "bondCurr", column = @Column(name = "BOND_CURR", length = 20)),
			@AttributeOverride(name = "bondIntr", column = @Column(name = "BOND_INTR", length = 200)),
			@AttributeOverride(name = "evalOrg", column = @Column(name = "EVAL_ORG", length = 40)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgIssuebondId getId() {
		return this.id;
	}

	public void setId(HMCiOrgIssuebondId id) {
		this.id = id;
	}

}