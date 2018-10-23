package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxStatByTimeIntervalId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStatByTimeIntervalId implements java.io.Serializable {

	// Fields

	private Date txDate;
	private String txTimeInterval;

	// Constructors

	/** default constructor */
	public TxStatByTimeIntervalId() {
	}

	/** full constructor */
	public TxStatByTimeIntervalId(Date txDate, String txTimeInterval) {
		this.txDate = txDate;
		this.txTimeInterval = txTimeInterval;
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

	@Column(name = "TX_TIME_INTERVAL", nullable = false, length = 20)
	public String getTxTimeInterval() {
		return this.txTimeInterval;
	}

	public void setTxTimeInterval(String txTimeInterval) {
		this.txTimeInterval = txTimeInterval;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStatByTimeIntervalId))
			return false;
		TxStatByTimeIntervalId castOther = (TxStatByTimeIntervalId) other;

		return ((this.getTxDate() == castOther.getTxDate()) || (this
				.getTxDate() != null
				&& castOther.getTxDate() != null && this.getTxDate().equals(
				castOther.getTxDate())))
				&& ((this.getTxTimeInterval() == castOther.getTxTimeInterval()) || (this
						.getTxTimeInterval() != null
						&& castOther.getTxTimeInterval() != null && this
						.getTxTimeInterval().equals(
								castOther.getTxTimeInterval())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTxDate() == null ? 0 : this.getTxDate().hashCode());
		result = 37
				* result
				+ (getTxTimeInterval() == null ? 0 : this.getTxTimeInterval()
						.hashCode());
		return result;
	}

}