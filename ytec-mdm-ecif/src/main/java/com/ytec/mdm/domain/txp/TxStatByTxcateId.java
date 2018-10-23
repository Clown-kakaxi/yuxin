package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxStatByTxcateId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStatByTxcateId implements java.io.Serializable {

	// Fields

	private Date txDate;
	private String txKind;

	// Constructors

	/** default constructor */
	public TxStatByTxcateId() {
	}

	/** full constructor */
	public TxStatByTxcateId(Date txDate, String txKind) {
		this.txDate = txDate;
		this.txKind = txKind;
	}

	// Property accessors
	@Temporal(TemporalType.DATE)
	@Column(name = "TX_DATE", nullable = false, length = 7)
	public Date getTxDate() {
		return this.txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	@Column(name = "TX_KIND", nullable = false, length = 20)
	public String getTxKind() {
		return this.txKind;
	}

	public void setTxKind(String txKind) {
		this.txKind = txKind;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStatByTxcateId))
			return false;
		TxStatByTxcateId castOther = (TxStatByTxcateId) other;

		return ((this.getTxDate() == castOther.getTxDate()) || (this
				.getTxDate() != null
				&& castOther.getTxDate() != null && this.getTxDate().equals(
				castOther.getTxDate())))
				&& ((this.getTxKind() == castOther.getTxKind()) || (this
						.getTxKind() != null
						&& castOther.getTxKind() != null && this.getTxKind()
						.equals(castOther.getTxKind())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTxDate() == null ? 0 : this.getTxDate().hashCode());
		result = 37 * result
				+ (getTxKind() == null ? 0 : this.getTxKind().hashCode());
		return result;
	}

}