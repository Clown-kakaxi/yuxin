package com.ytec.mdm.domain.txp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStatByRtncode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STAT_BY_RTNCODE")
public class TxStatByRtncode implements java.io.Serializable {

	// Fields

	private TxStatByRtncodeId id;
	private Long txCount;

	// Constructors

	/** default constructor */
	public TxStatByRtncode() {
	}

	/** minimal constructor */
	public TxStatByRtncode(TxStatByRtncodeId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStatByRtncode(TxStatByRtncodeId id, Long txCount) {
		this.id = id;
		this.txCount = txCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "txDate", column = @Column(name = "TX_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "txRtnCode", column = @Column(name = "TX_RTN_CODE", nullable = false, length = 10)) })
	public TxStatByRtncodeId getId() {
		return this.id;
	}

	public void setId(TxStatByRtncodeId id) {
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