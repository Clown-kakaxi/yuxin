package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgHolderinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_HOLDERINFO")
public class HMCiOrgHolderinfo implements java.io.Serializable {

	// Fields

	private HMCiOrgHolderinfoId id;

	// Constructors

	/** default constructor */
	public HMCiOrgHolderinfo() {
	}

	/** full constructor */
	public HMCiOrgHolderinfo(HMCiOrgHolderinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "holderId", column = @Column(name = "HOLDER_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "holderType", column = @Column(name = "HOLDER_TYPE", length = 20)),
			@AttributeOverride(name = "holderName", column = @Column(name = "HOLDER_NAME", length = 80)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "identExpiredDate", column = @Column(name = "IDENT_EXPIRED_DATE", length = 7)),
			@AttributeOverride(name = "birthday", column = @Column(name = "BIRTHDAY", length = 7)),
			@AttributeOverride(name = "email", column = @Column(name = "EMAIL", length = 40)),
			@AttributeOverride(name = "isOffenceFlag", column = @Column(name = "IS_OFFENCE_FLAG", length = 1)),
			@AttributeOverride(name = "sponsorKind", column = @Column(name = "SPONSOR_KIND", length = 20)),
			@AttributeOverride(name = "sponsorAmt", column = @Column(name = "SPONSOR_AMT", precision = 17)),
			@AttributeOverride(name = "sponsorCurr", column = @Column(name = "SPONSOR_CURR", length = 20)),
			@AttributeOverride(name = "sponsorPercent", column = @Column(name = "SPONSOR_PERCENT", precision = 10, scale = 4)),
			@AttributeOverride(name = "sponsorDate", column = @Column(name = "SPONSOR_DATE", length = 7)),
			@AttributeOverride(name = "isCheckFlag", column = @Column(name = "IS_CHECK_FLAG", length = 1)),
			@AttributeOverride(name = "stockPercent", column = @Column(name = "STOCK_PERCENT", precision = 10, scale = 4)),
			@AttributeOverride(name = "countryCode", column = @Column(name = "COUNTRY_CODE", length = 20)),
			@AttributeOverride(name = "holderOrgAddr", column = @Column(name = "HOLDER_ORG_ADDR", length = 200)),
			@AttributeOverride(name = "holderOrgRegAddr", column = @Column(name = "HOLDER_ORG_REG_ADDR", length = 200)),
			@AttributeOverride(name = "holderOrgTel", column = @Column(name = "HOLDER_ORG_TEL", length = 20)),
			@AttributeOverride(name = "holderPerGender", column = @Column(name = "HOLDER_PER_GENDER", length = 20)),
			@AttributeOverride(name = "holderPerBirthLocale", column = @Column(name = "HOLDER_PER_BIRTH_LOCALE", length = 50)),
			@AttributeOverride(name = "holderPerCtryAddr", column = @Column(name = "HOLDER_PER_CTRY_ADDR", length = 200)),
			@AttributeOverride(name = "holderPerCtryTel", column = @Column(name = "HOLDER_PER_CTRY_TEL", length = 20)),
			@AttributeOverride(name = "remtRecverCtryCd", column = @Column(name = "REMT_RECVER_CTRY_CD", length = 20)),
			@AttributeOverride(name = "remtRecverCtryAddr", column = @Column(name = "REMT_RECVER_CTRY_ADDR", length = 200)),
			@AttributeOverride(name = "authedPerCtryCd", column = @Column(name = "AUTHED_PER_CTRY_CD", length = 20)),
			@AttributeOverride(name = "authedPerCtryAddr", column = @Column(name = "AUTHED_PER_CTRY_ADDR", length = 200)),
			@AttributeOverride(name = "holderPerPostAddr", column = @Column(name = "HOLDER_PER_POST_ADDR", length = 200)),
			@AttributeOverride(name = "holderPerOffcTel", column = @Column(name = "HOLDER_PER_OFFC_TEL", length = 20)),
			@AttributeOverride(name = "holderPerFamlyTel", column = @Column(name = "HOLDER_PER_FAMLY_TEL", length = 20)),
			@AttributeOverride(name = "holderPerMobile", column = @Column(name = "HOLDER_PER_MOBILE", length = 20)),
			@AttributeOverride(name = "holderPerIndPos", column = @Column(name = "HOLDER_PER_IND_POS", length = 20)),
			@AttributeOverride(name = "legalReprName", column = @Column(name = "LEGAL_REPR_NAME", length = 80)),
			@AttributeOverride(name = "needSponsorAmt", column = @Column(name = "NEED_SPONSOR_AMT", precision = 17)),
			@AttributeOverride(name = "actualStockPercent", column = @Column(name = "ACTUAL_STOCK_PERCENT", precision = 17)),
			@AttributeOverride(name = "isRptMerge", column = @Column(name = "IS_RPT_MERGE", length = 1)),
			@AttributeOverride(name = "isReported", column = @Column(name = "IS_REPORTED", length = 1)),
			@AttributeOverride(name = "isRegAtUsa", column = @Column(name = "IS_REG_AT_USA", length = 1)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgHolderinfoId getId() {
		return this.id;
	}

	public void setId(HMCiOrgHolderinfoId id) {
		this.id = id;
	}

}