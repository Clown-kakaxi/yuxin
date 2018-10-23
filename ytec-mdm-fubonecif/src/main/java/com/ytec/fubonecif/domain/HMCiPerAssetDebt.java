package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerAssetDebt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_ASSET_DEBT")
public class HMCiPerAssetDebt implements java.io.Serializable {

	// Fields

	private HMCiPerAssetDebtId id;

	// Constructors

	/** default constructor */
	public HMCiPerAssetDebt() {
	}

	/** full constructor */
	public HMCiPerAssetDebt(HMCiPerAssetDebtId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "assetDebtId", column = @Column(name = "ASSET_DEBT_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "assetDebtType", column = @Column(name = "ASSET_DEBT_TYPE", length = 20)),
			@AttributeOverride(name = "itemType", column = @Column(name = "ITEM_TYPE", length = 20)),
			@AttributeOverride(name = "curr", column = @Column(name = "CURR", length = 20)),
			@AttributeOverride(name = "amt", column = @Column(name = "AMT", precision = 17)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerAssetDebtId getId() {
		return this.id;
	}

	public void setId(HMCiPerAssetDebtId id) {
		this.id = id;
	}

}