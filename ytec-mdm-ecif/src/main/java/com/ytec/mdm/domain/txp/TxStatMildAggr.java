package com.ytec.mdm.domain.txp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStatMildAggr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STAT_MILD_AGGR")
public class TxStatMildAggr implements java.io.Serializable {

	// Fields

	private TxStatMildAggrId id;
	private Long txCount;

	// Constructors

	/** default constructor */
	public TxStatMildAggr() {
	}

	/** minimal constructor */
	public TxStatMildAggr(TxStatMildAggrId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStatMildAggr(TxStatMildAggrId id, Long txCount) {
		this.id = id;
		this.txCount = txCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "txDate", column = @Column(name = "TX_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "txSys", column = @Column(name = "TX_SYS", nullable = false, length = 20)),
			@AttributeOverride(name = "txChannel", column = @Column(name = "TX_CHANNEL", nullable = false, length = 20)),
			@AttributeOverride(name = "custType", column = @Column(name = "CUST_TYPE", nullable = false, length = 20)),
			@AttributeOverride(name = "txKind", column = @Column(name = "TX_KIND", nullable = false, length = 20)),
			@AttributeOverride(name = "txCode", column = @Column(name = "TX_CODE", nullable = false, length = 20)),
			@AttributeOverride(name = "txTimeInterval", column = @Column(name = "TX_TIME_INTERVAL", nullable = false, length = 20)),
			@AttributeOverride(name = "txRtnCode", column = @Column(name = "TX_RTN_CODE", nullable = false, length = 10)),
			@AttributeOverride(name = "txDealTime", column = @Column(name = "TX_DEAL_TIME", nullable = false, precision = 22)) })
	public TxStatMildAggrId getId() {
		return this.id;
	}

	public void setId(TxStatMildAggrId id) {
		this.id = id;
	}

	@Column(name = "TX_COUNT")
	public Long getTxCount() {
		return this.txCount;
	}

	public void setTxCount(Long txCount) {
		this.txCount = txCount;
	}

}