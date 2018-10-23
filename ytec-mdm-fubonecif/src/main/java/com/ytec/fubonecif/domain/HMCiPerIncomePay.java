package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerIncomePay entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_INCOME_PAY")
public class HMCiPerIncomePay implements java.io.Serializable {

	// Fields

	private HMCiPerIncomePayId id;

	// Constructors

	/** default constructor */
	public HMCiPerIncomePay() {
	}

	/** full constructor */
	public HMCiPerIncomePay(HMCiPerIncomePayId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "ioId", column = @Column(name = "IO_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "ioFreq", column = @Column(name = "IO_FREQ", length = 20)),
			@AttributeOverride(name = "ioType", column = @Column(name = "IO_TYPE", length = 20)),
			@AttributeOverride(name = "detailType", column = @Column(name = "DETAIL_TYPE", length = 20)),
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
	public HMCiPerIncomePayId getId() {
		return this.id;
	}

	public void setId(HMCiPerIncomePayId id) {
		this.id = id;
	}

}