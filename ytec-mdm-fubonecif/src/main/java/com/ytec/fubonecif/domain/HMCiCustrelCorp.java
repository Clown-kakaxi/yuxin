package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiCustrelCorp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_CUSTREL_CORP")
public class HMCiCustrelCorp implements java.io.Serializable {

	// Fields

	private HMCiCustrelCorpId id;

	// Constructors

	/** default constructor */
	public HMCiCustrelCorp() {
	}

	/** full constructor */
	public HMCiCustrelCorp(HMCiCustrelCorpId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custRelId", column = @Column(name = "CUST_REL_ID", length = 20)),
			@AttributeOverride(name = "corpItemName", column = @Column(name = "CORP_ITEM_NAME", length = 80)),
			@AttributeOverride(name = "corpItemDesc", column = @Column(name = "CORP_ITEM_DESC")),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiCustrelCorpId getId() {
		return this.id;
	}

	public void setId(HMCiCustrelCorpId id) {
		this.id = id;
	}

}