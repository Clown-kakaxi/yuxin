package com.ytec.mdm.domain.txp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStatByTxcate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STAT_BY_TXCATE")
public class TxStatByTxcate implements java.io.Serializable {

	// Fields

	private TxStatByTxcateId id;
	private Long txCount;

	// Constructors

	/** default constructor */
	public TxStatByTxcate() {
	}

	/** minimal constructor */
	public TxStatByTxcate(TxStatByTxcateId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStatByTxcate(TxStatByTxcateId id, Long txCount) {
		this.id = id;
		this.txCount = txCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "txDate", column = @Column(name = "TX_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "txKind", column = @Column(name = "TX_KIND", nullable = false, length = 20)) })
	public TxStatByTxcateId getId() {
		return this.id;
	}

	public void setId(TxStatByTxcateId id) {
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