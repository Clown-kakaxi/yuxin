package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiCustrelSupplyChain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_CUSTREL_SUPPLY_CHAIN")
public class HMCiCustrelSupplyChain implements java.io.Serializable {

	// Fields

	private HMCiCustrelSupplyChainId id;

	// Constructors

	/** default constructor */
	public HMCiCustrelSupplyChain() {
	}

	/** full constructor */
	public HMCiCustrelSupplyChain(HMCiCustrelSupplyChainId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custRelId", column = @Column(name = "CUST_REL_ID", length = 20)),
			@AttributeOverride(name = "isMainProvider", column = @Column(name = "IS_MAIN_PROVIDER", length = 1)),
			@AttributeOverride(name = "isMainSaler", column = @Column(name = "IS_MAIN_SALER", length = 1)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiCustrelSupplyChainId getId() {
		return this.id;
	}

	public void setId(HMCiCustrelSupplyChainId id) {
		this.id = id;
	}

}