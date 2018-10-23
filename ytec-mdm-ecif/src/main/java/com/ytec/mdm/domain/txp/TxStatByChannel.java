package com.ytec.mdm.domain.txp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStatByChannel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STAT_BY_CHANNEL")
public class TxStatByChannel implements java.io.Serializable {

	// Fields

	private TxStatByChannelId id;
	private Long txCount;

	// Constructors

	/** default constructor */
	public TxStatByChannel() {
	}

	/** minimal constructor */
	public TxStatByChannel(TxStatByChannelId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStatByChannel(TxStatByChannelId id, Long txCount) {
		this.id = id;
		this.txCount = txCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "txDate", column = @Column(name = "TX_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "txChannel", column = @Column(name = "TX_CHANNEL", nullable = false, length = 20)) })
	public TxStatByChannelId getId() {
		return this.id;
	}

	public void setId(TxStatByChannelId id) {
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