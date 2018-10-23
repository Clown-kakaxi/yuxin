package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgExecutiveinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_EXECUTIVEINFO")
public class HMCiOrgExecutiveinfo implements java.io.Serializable {

	// Fields

	private HMCiOrgExecutiveinfoId id;

	// Constructors

	/** default constructor */
	public HMCiOrgExecutiveinfo() {
	}

	/** full constructor */
	public HMCiOrgExecutiveinfo(HMCiOrgExecutiveinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "linkmanId", column = @Column(name = "LINKMAN_ID", length = 20)),
			@AttributeOverride(name = "orgCustId", column = @Column(name = "ORG_CUST_ID", length = 20)),
			@AttributeOverride(name = "linkmanType", column = @Column(name = "LINKMAN_TYPE", length = 20)),
			@AttributeOverride(name = "linkmanName", column = @Column(name = "LINKMAN_NAME", length = 80)),
			@AttributeOverride(name = "linkmanEnName", column = @Column(name = "LINKMAN_EN_NAME", length = 70)),
			@AttributeOverride(name = "linkmanTitle", column = @Column(name = "LINKMAN_TITLE", length = 20)),
			@AttributeOverride(name = "isThisBankCust", column = @Column(name = "IS_THIS_BANK_CUST", length = 1)),
			@AttributeOverride(name = "indivCusId", column = @Column(name = "INDIV_CUS_ID", length = 20)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "identRegAddr", column = @Column(name = "IDENT_REG_ADDR", length = 200)),
			@AttributeOverride(name = "identRegAddrPost", column = @Column(name = "IDENT_REG_ADDR_POST", length = 20)),
			@AttributeOverride(name = "identExpiredDate", column = @Column(name = "IDENT_EXPIRED_DATE", length = 7)),
			@AttributeOverride(name = "identIsVerify", column = @Column(name = "IDENT_IS_VERIFY", length = 1)),
			@AttributeOverride(name = "citizenship", column = @Column(name = "CITIZENSHIP", length = 20)),
			@AttributeOverride(name = "nationality", column = @Column(name = "NATIONALITY", length = 20)),
			@AttributeOverride(name = "nativeplace", column = @Column(name = "NATIVEPLACE", length = 20)),
			@AttributeOverride(name = "gender", column = @Column(name = "GENDER", length = 20)),
			@AttributeOverride(name = "birthday", column = @Column(name = "BIRTHDAY", length = 7)),
			@AttributeOverride(name = "highestSchooling", column = @Column(name = "HIGHEST_SCHOOLING", length = 20)),
			@AttributeOverride(name = "marriage", column = @Column(name = "MARRIAGE", length = 20)),
			@AttributeOverride(name = "politicalFace", column = @Column(name = "POLITICAL_FACE", length = 20)),
			@AttributeOverride(name = "officeTel", column = @Column(name = "OFFICE_TEL", length = 20)),
			@AttributeOverride(name = "officeTel2", column = @Column(name = "OFFICE_TEL2", length = 20)),
			@AttributeOverride(name = "homeTel", column = @Column(name = "HOME_TEL", length = 20)),
			@AttributeOverride(name = "homeTel2", column = @Column(name = "HOME_TEL2", length = 20)),
			@AttributeOverride(name = "mobile", column = @Column(name = "MOBILE", length = 20)),
			@AttributeOverride(name = "mobile2", column = @Column(name = "MOBILE2", length = 20)),
			@AttributeOverride(name = "fex", column = @Column(name = "FEX", length = 20)),
			@AttributeOverride(name = "email", column = @Column(name = "EMAIL", length = 40)),
			@AttributeOverride(name = "address", column = @Column(name = "ADDRESS", length = 200)),
			@AttributeOverride(name = "zipCode", column = @Column(name = "ZIP_CODE", length = 20)),
			@AttributeOverride(name = "workDept", column = @Column(name = "WORK_DEPT", length = 60)),
			@AttributeOverride(name = "workPosition", column = @Column(name = "WORK_POSITION", length = 20)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "endDate", column = @Column(name = "END_DATE", length = 7)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgExecutiveinfoId getId() {
		return this.id;
	}

	public void setId(HMCiOrgExecutiveinfoId id) {
		this.id = id;
	}

}