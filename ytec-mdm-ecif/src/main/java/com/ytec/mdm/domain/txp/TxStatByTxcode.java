package com.ytec.mdm.domain.txp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStatByTxcode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STAT_BY_TXCODE")
public class TxStatByTxcode implements java.io.Serializable {

	// Fields

	private TxStatByTxcodeId id;
	private Long txCount;

	// Constructors

	/** default constructor */
	public TxStatByTxcode() {
	}

	/** minimal constructor */
	public TxStatByTxcode(TxStatByTxcodeId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStatByTxcode(TxStatByTxcodeId id, Long txCount) {
		this.id = id;
		this.txCount = txCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "txDate", column = @Column(name = "TX_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "txCode", column = @Column(name = "TX_CODE", nullable = false, length = 32)) })
	public TxStatByTxcodeId getId() {
		return this.id;
	}

	public void setId(TxStatByTxcodeId id) {
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