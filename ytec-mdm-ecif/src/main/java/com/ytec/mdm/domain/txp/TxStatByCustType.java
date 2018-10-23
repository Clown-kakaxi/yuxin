package com.ytec.mdm.domain.txp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStatByCustType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STAT_BY_CUST_TYPE")
public class TxStatByCustType implements java.io.Serializable {

	// Fields

	private TxStatByCustTypeId id;
	private Long txCount;

	// Constructors

	/** default constructor */
	public TxStatByCustType() {
	}

	/** minimal constructor */
	public TxStatByCustType(TxStatByCustTypeId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStatByCustType(TxStatByCustTypeId id, Long txCount) {
		this.id = id;
		this.txCount = txCount;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "txDate", column = @Column(name = "TX_DATE", nullable = false, length = 7)),
			@AttributeOverride(name = "txCustType", column = @Column(name = "TX_CUST_TYPE", nullable = false, length = 20)) })
	public TxStatByCustTypeId getId() {
		return this.id;
	}

	public void setId(TxStatByCustTypeId id) {
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