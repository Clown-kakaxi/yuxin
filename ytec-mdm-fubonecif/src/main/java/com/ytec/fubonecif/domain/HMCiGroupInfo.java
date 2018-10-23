package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiGroupInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_GROUP_INFO")
public class HMCiGroupInfo implements java.io.Serializable {

	// Fields

	private HMCiGroupInfoId id;

	// Constructors

	/** default constructor */
	public HMCiGroupInfo() {
	}

	/** full constructor */
	public HMCiGroupInfo(HMCiGroupInfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "groupId", column = @Column(name = "GROUP_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "groupNo", column = @Column(name = "GROUP_NO", length = 20)),
			@AttributeOverride(name = "groupType", column = @Column(name = "GROUP_TYPE", length = 20)),
			@AttributeOverride(name = "groupName", column = @Column(name = "GROUP_NAME", length = 80)),
			@AttributeOverride(name = "groupStatus", column = @Column(name = "GROUP_STATUS", length = 20)),
			@AttributeOverride(name = "groupRootCustId", column = @Column(name = "GROUP_ROOT_CUST_ID", length = 20)),
			@AttributeOverride(name = "grpFinanceType", column = @Column(name = "GRP_FINANCE_TYPE", length = 20)),
			@AttributeOverride(name = "groupMemo", column = @Column(name = "GROUP_MEMO", length = 200)),
			@AttributeOverride(name = "groupHostOrgNo", column = @Column(name = "GROUP_HOST_ORG_NO", length = 20)),
			@AttributeOverride(name = "groupRootAddress", column = @Column(name = "GROUP_ROOT_ADDRESS", length = 200)),
			@AttributeOverride(name = "creataDate", column = @Column(name = "CREATA_DATE", length = 7)),
			@AttributeOverride(name = "createUserId", column = @Column(name = "CREATE_USER_ID", length = 20)),
			@AttributeOverride(name = "createUserName", column = @Column(name = "CREATE_USER_NAME", length = 80)),
			@AttributeOverride(name = "createUserOrgId", column = @Column(name = "CREATE_USER_ORG_ID", length = 20)),
			@AttributeOverride(name = "groupNameMain", column = @Column(name = "GROUP_NAME_MAIN", length = 80)),
			@AttributeOverride(name = "gao", column = @Column(name = "GAO", length = 20)),
			@AttributeOverride(name = "gaoOrg", column = @Column(name = "GAO_ORG", length = 20)),
			@AttributeOverride(name = "creditAmt", column = @Column(name = "CREDIT_AMT", precision = 20)),
			@AttributeOverride(name = "creditCur", column = @Column(name = "CREDIT_CUR", length = 20)),
			@AttributeOverride(name = "dueDate", column = @Column(name = "DUE_DATE", length = 7)),
			@AttributeOverride(name = "corpNameSub", column = @Column(name = "CORP_NAME_SUB", length = 200)),
			@AttributeOverride(name = "useSituation", column = @Column(name = "USE_SITUATION", length = 200)),
			@AttributeOverride(name = "addrRegist", column = @Column(name = "ADDR_REGIST", length = 200)),
			@AttributeOverride(name = "employeeNum", column = @Column(name = "EMPLOYEE_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "gurantCusNum", column = @Column(name = "GURANT_CUS_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "employeeNumFormal", column = @Column(name = "EMPLOYEE_NUM_FORMAL", precision = 22, scale = 0)),
			@AttributeOverride(name = "pendingMemberNum", column = @Column(name = "PENDING_MEMBER_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "externalSecurityNum", column = @Column(name = "EXTERNAL_SECURITY_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "groupForm", column = @Column(name = "GROUP_FORM", length = 20)),
			@AttributeOverride(name = "usedAmt", column = @Column(name = "USED_AMT", precision = 20)),
			@AttributeOverride(name = "loanBalance", column = @Column(name = "LOAN_BALANCE", precision = 20)),
			@AttributeOverride(name = "badLoanBalance", column = @Column(name = "BAD_LOAN_BALANCE", precision = 20)),
			@AttributeOverride(name = "guaranteeType", column = @Column(name = "GUARANTEE_TYPE", length = 20)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiGroupInfoId getId() {
		return this.id;
	}

	public void setId(HMCiGroupInfoId id) {
		this.id = id;
	}

}