package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiCustomer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_CUSTOMER")
public class HMCiCustomer implements java.io.Serializable {

	// Fields

	private HMCiCustomerId id;

	// Constructors

	/** default constructor */
	public HMCiCustomer() {
	}

	/** full constructor */
	public HMCiCustomer(HMCiCustomerId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "coreNo", column = @Column(name = "CORE_NO", length = 20)),
			@AttributeOverride(name = "custType", column = @Column(name = "CUST_TYPE", length = 20)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "custName", column = @Column(name = "CUST_NAME", length = 80)),
			@AttributeOverride(name = "postName", column = @Column(name = "POST_NAME", length = 70)),
			@AttributeOverride(name = "shortName", column = @Column(name = "SHORT_NAME", length = 80)),
			@AttributeOverride(name = "enName", column = @Column(name = "EN_NAME", length = 100)),
			@AttributeOverride(name = "enShortName", column = @Column(name = "EN_SHORT_NAME", length = 80)),
			@AttributeOverride(name = "custStat", column = @Column(name = "CUST_STAT", length = 20)),
			@AttributeOverride(name = "jobType", column = @Column(name = "JOB_TYPE", length = 20)),
			@AttributeOverride(name = "industType", column = @Column(name = "INDUST_TYPE", length = 20)),
			@AttributeOverride(name = "riskNationCode", column = @Column(name = "RISK_NATION_CODE", length = 20)),
			@AttributeOverride(name = "potentialFlag", column = @Column(name = "POTENTIAL_FLAG", length = 1)),
			@AttributeOverride(name = "ebankFlag", column = @Column(name = "EBANK_FLAG", length = 1)),
			@AttributeOverride(name = "realFlag", column = @Column(name = "REAL_FLAG", length = 20)),
			@AttributeOverride(name = "inoutFlag", column = @Column(name = "INOUT_FLAG", length = 20)),
			@AttributeOverride(name = "blankFlag", column = @Column(name = "BLANK_FLAG", length = 20)),
			@AttributeOverride(name = "vipFlag", column = @Column(name = "VIP_FLAG", length = 1)),
			@AttributeOverride(name = "mergeFlag", column = @Column(name = "MERGE_FLAG", length = 20)),
			@AttributeOverride(name = "linkmanName", column = @Column(name = "LINKMAN_NAME", length = 80)),
			@AttributeOverride(name = "linkmanTel", column = @Column(name = "LINKMAN_TEL", length = 20)),
			@AttributeOverride(name = "firstLoanDate", column = @Column(name = "FIRST_LOAN_DATE", length = 7)),
			@AttributeOverride(name = "loanCustMgr", column = @Column(name = "LOAN_CUST_MGR", length = 20)),
			@AttributeOverride(name = "loanMainBrId", column = @Column(name = "LOAN_MAIN_BR_ID", length = 20)),
			@AttributeOverride(name = "arCustFlag", column = @Column(name = "AR_CUST_FLAG", length = 1)),
			@AttributeOverride(name = "arCustType", column = @Column(name = "AR_CUST_TYPE", length = 20)),
			@AttributeOverride(name = "sourceChannel", column = @Column(name = "SOURCE_CHANNEL", length = 20)),
			@AttributeOverride(name = "recommender", column = @Column(name = "RECOMMENDER", length = 20)),
			@AttributeOverride(name = "loanCustRank", column = @Column(name = "LOAN_CUST_RANK", length = 20)),
			@AttributeOverride(name = "loanCustStat", column = @Column(name = "LOAN_CUST_STAT", length = 20)),
			@AttributeOverride(name = "cusBankRel", column = @Column(name = "CUS_BANK_REL", length = 20)),
			@AttributeOverride(name = "cusCorpRel", column = @Column(name = "CUS_CORP_REL", length = 20)),
			@AttributeOverride(name = "infoPer", column = @Column(name = "INFO_PER", length = 20)),
			@AttributeOverride(name = "createDate", column = @Column(name = "CREATE_DATE", length = 7)),
			@AttributeOverride(name = "createTime", column = @Column(name = "CREATE_TIME")),
			@AttributeOverride(name = "createBranchNo", column = @Column(name = "CREATE_BRANCH_NO", length = 20)),
			@AttributeOverride(name = "createTellerNo", column = @Column(name = "CREATE_TELLER_NO", length = 20)),
			@AttributeOverride(name = "custLevel", column = @Column(name = "CUST_LEVEL", length = 20)),
			@AttributeOverride(name = "riskLevel", column = @Column(name = "RISK_LEVEL", length = 20)),
			@AttributeOverride(name = "riskValidDate", column = @Column(name = "RISK_VALID_DATE", length = 7)),
			@AttributeOverride(name = "creditLevel", column = @Column(name = "CREDIT_LEVEL", length = 20)),
			@AttributeOverride(name = "currentAum", column = @Column(name = "CURRENT_AUM", precision = 17)),
			@AttributeOverride(name = "totalDebt", column = @Column(name = "TOTAL_DEBT", precision = 17)),
			@AttributeOverride(name = "faxtradeNorecNum", column = @Column(name = "FAXTRADE_NOREC_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM")),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiCustomerId getId() {
		return this.id;
	}

	public void setId(HMCiCustomerId id) {
		this.id = id;
	}

}