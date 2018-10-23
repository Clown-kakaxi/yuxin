package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxStatByRtncodeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStatByRtncodeId implements java.io.Serializable {

	// Fields

	private Date txDate;
	private String txRtnCode;

	// Constructors

	/** default constructor */
	public TxStatByRtncodeId() {
	}

	/** full constructor */
	public TxStatByRtncodeId(Date txDate, String txRtnCode) {
		this.txDate = txDate;
		this.txRtnCode = txRtnCode;
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

	@Column(name = "TX_RTN_CODE", nullable = false, length = 10)
	public String getTxRtnCode() {
		return this.txRtnCode;
	}

	public void setTxRtnCode(String txRtnCode) {
		this.txRtnCode = txRtnCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStatByRtncodeId))
			return false;
		TxStatByRtncodeId castOther = (TxStatByRtncodeId) other;

		return ((this.getTxDate() == castOther.getTxDate()) || (this
				.getTxDate() != null
				&& castOther.getTxDate() != null && this.getTxDate().equals(
				castOther.getTxDate())))
				&& ((this.getTxRtnCode() == castOther.getTxRtnCode()) || (this
						.getTxRtnCode() != null
						&& castOther.getTxRtnCode() != null && this
						.getTxRtnCode().equals(castOther.getTxRtnCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTxDate() == null ? 0 : this.getTxDate().hashCode());
		result = 37 * result
				+ (getTxRtnCode() == null ? 0 : this.getTxRtnCode().hashCode());
		return result;
	}

}