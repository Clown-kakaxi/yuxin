package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxStatByTimeConsumeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStatByTimeConsumeId implements java.io.Serializable {

	// Fields

	private Date txDate;
	private Long txDealTime;

	// Constructors

	/** default constructor */
	public TxStatByTimeConsumeId() {
	}

	/** full constructor */
	public TxStatByTimeConsumeId(Date txDate, Long txDealTime) {
		this.txDate = txDate;
		this.txDealTime = txDealTime;
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

	@Column(name = "TX_DEAL_TIME", nullable = false, precision = 22)
	public Long getTxDealTime() {
		return this.txDealTime;
	}

	public void setTxDealTime(Long txDealTime) {
		this.txDealTime = txDealTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStatByTimeConsumeId))
			return false;
		TxStatByTimeConsumeId castOther = (TxStatByTimeConsumeId) other;

		return ((this.getTxDate() == castOther.getTxDate()) || (this
				.getTxDate() != null
				&& castOther.getTxDate() != null && this.getTxDate().equals(
				castOther.getTxDate())))
				&& ((this.getTxDealTime() == castOther.getTxDealTime()) || (this
						.getTxDealTime() != null
						&& castOther.getTxDealTime() != null && this
						.getTxDealTime().equals(castOther.getTxDealTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTxDate() == null ? 0 : this.getTxDate().hashCode());
		result = 37
				* result
				+ (getTxDealTime() == null ? 0 : this.getTxDealTime()
						.hashCode());
		return result;
	}

}