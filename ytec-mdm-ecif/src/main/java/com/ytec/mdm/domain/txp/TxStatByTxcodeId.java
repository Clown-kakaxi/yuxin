package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxStatByTxcodeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStatByTxcodeId implements java.io.Serializable {

	// Fields

	private Date txDate;
	private String txCode;

	// Constructors

	/** default constructor */
	public TxStatByTxcodeId() {
	}

	/** full constructor */
	public TxStatByTxcodeId(Date txDate, String txCode) {
		this.txDate = txDate;
		this.txCode = txCode;
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

	@Column(name = "TX_CODE", nullable = false, length = 32)
	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStatByTxcodeId))
			return false;
		TxStatByTxcodeId castOther = (TxStatByTxcodeId) other;

		return ((this.getTxDate() == castOther.getTxDate()) || (this
				.getTxDate() != null
				&& castOther.getTxDate() != null && this.getTxDate().equals(
				castOther.getTxDate())))
				&& ((this.getTxCode() == castOther.getTxCode()) || (this
						.getTxCode() != null
						&& castOther.getTxCode() != null && this.getTxCode()
						.equals(castOther.getTxCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTxDate() == null ? 0 : this.getTxDate().hashCode());
		result = 37 * result
				+ (getTxCode() == null ? 0 : this.getTxCode().hashCode());
		return result;
	}

}