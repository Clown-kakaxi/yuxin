package com.ytec.mdm.domain.txp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStatBySys entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STAT_BY_SYS")
public class TxStatBySys implements java.io.Serializable {

	// Fields

	private TxStatBySysId id;
	private Long txCount;

	// Constructors

	/** default constructor */
	public TxStatBySys() {
	}

	/** minimal constructor */
	public TxStatBySys(TxStatBySysId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStatBySys(TxStatBySysId id, Long txCount) {
		this.id = id;
		this.txCount = txCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "txDate", column = @Column(name = "TX_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "txSys", column = @Column(name = "TX_SYS", nullable = false, length = 20)) })
	public TxStatBySysId getId() {
		return this.id;
	}

	public void setId(TxStatBySysId id) {
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