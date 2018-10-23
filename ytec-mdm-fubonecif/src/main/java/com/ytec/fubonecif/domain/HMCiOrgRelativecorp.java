package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgRelativecorp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_RELATIVECORP")
public class HMCiOrgRelativecorp implements java.io.Serializable {

	// Fields

	private HMCiOrgRelativecorpId id;

	// Constructors

	/** default constructor */
	public HMCiOrgRelativecorp() {
	}

	/** full constructor */
	public HMCiOrgRelativecorp(HMCiOrgRelativecorpId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "relativeCorpId", column = @Column(name = "RELATIVE_CORP_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "relativeCorpName", column = @Column(name = "RELATIVE_CORP_NAME", length = 80)),
			@AttributeOverride(name = "orgCode", column = @Column(name = "ORG_CODE", length = 40)),
			@AttributeOverride(name = "relationType", column = @Column(name = "RELATION_TYPE", length = 20)),
			@AttributeOverride(name = "relationDesc", column = @Column(name = "RELATION_DESC", length = 100)),
			@AttributeOverride(name = "totalAssets", column = @Column(name = "TOTAL_ASSETS", precision = 17)),
			@AttributeOverride(name = "netAssets", column = @Column(name = "NET_ASSETS", precision = 17)),
			@AttributeOverride(name = "totalDebt", column = @Column(name = "TOTAL_DEBT", precision = 17)),
			@AttributeOverride(name = "netProfit", column = @Column(name = "NET_PROFIT", precision = 17)),
			@AttributeOverride(name = "mainBusinessType", column = @Column(name = "MAIN_BUSINESS_TYPE", length = 20)),
			@AttributeOverride(name = "mainBusinessIncome", column = @Column(name = "MAIN_BUSINESS_INCOME", precision = 17)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgRelativecorpId getId() {
		return this.id;
	}

	public void setId(HMCiOrgRelativecorpId id) {
		this.id = id;
	}

}