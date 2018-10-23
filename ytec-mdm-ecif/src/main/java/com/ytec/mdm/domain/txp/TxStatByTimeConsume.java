package com.ytec.mdm.domain.txp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStatByTimeConsume entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STAT_BY_TIME_CONSUME")
public class TxStatByTimeConsume implements java.io.Serializable {

	// Fields

	private TxStatByTimeConsumeId id;
	private Long txCount;

	// Constructors

	/** default constructor */
	public TxStatByTimeConsume() {
	}

	/** minimal constructor */
	public TxStatByTimeConsume(TxStatByTimeConsumeId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStatByTimeConsume(TxStatByTimeConsumeId id, Long txCount) {
		this.id = id;
		this.txCount = txCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "txDate", column = @Column(name = "TX_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "txDealTime", column = @Column(name = "TX_DEAL_TIME", nullable = false, precision = 22)) })
	public TxStatByTimeConsumeId getId() {
		return this.id;
	}

	public void setId(TxStatByTimeConsumeId id) {
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