package com.ytec.mdm.domain.txp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStatByTimeInterval entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STAT_BY_TIME_INTERVAL")
public class TxStatByTimeInterval implements java.io.Serializable {

	// Fields

	private TxStatByTimeIntervalId id;
	private Long txCount;

	// Constructors

	/** default constructor */
	public TxStatByTimeInterval() {
	}

	/** minimal constructor */
	public TxStatByTimeInterval(TxStatByTimeIntervalId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStatByTimeInterval(TxStatByTimeIntervalId id, Long txCount) {
		this.id = id;
		this.txCount = txCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "txDate", column = @Column(name = "TX_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "txTimeInterval", column = @Column(name = "TX_TIME_INTERVAL", nullable = false, length = 20)) })
	public TxStatByTimeIntervalId getId() {
		return this.id;
	}

	public void setId(TxStatByTimeIntervalId id) {
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