package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiCustrelControl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_CUSTREL_CONTROL")
public class HMCiCustrelControl implements java.io.Serializable {

	// Fields

	private HMCiCustrelControlId id;

	// Constructors

	/** default constructor */
	public HMCiCustrelControl() {
	}

	/** full constructor */
	public HMCiCustrelControl(HMCiCustrelControlId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custRelId", column = @Column(name = "CUST_REL_ID", length = 20)),
			@AttributeOverride(name = "stockNum", column = @Column(name = "STOCK_NUM", scale = 0)),
			@AttributeOverride(name = "stockAmt", column = @Column(name = "STOCK_AMT", precision = 17)),
			@AttributeOverride(name = "stockCurr", column = @Column(name = "STOCK_CURR", length = 20)),
			@AttributeOverride(name = "stockPercent", column = @Column(name = "STOCK_PERCENT", precision = 10, scale = 4)),
			@AttributeOverride(name = "stockDate", column = @Column(name = "STOCK_DATE", length = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiCustrelControlId getId() {
		return this.id;
	}

	public void setId(HMCiCustrelControlId id) {
		this.id = id;
	}

}