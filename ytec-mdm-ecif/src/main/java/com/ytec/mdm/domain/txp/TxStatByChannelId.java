package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxStatByChannelId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStatByChannelId implements java.io.Serializable {

	// Fields

	private Date txDate;
	private String txChannel;

	// Constructors

	/** default constructor */
	public TxStatByChannelId() {
	}

	/** full constructor */
	public TxStatByChannelId(Date txDate, String txChannel) {
		this.txDate = txDate;
		this.txChannel = txChannel;
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

	@Column(name = "TX_CHANNEL", nullable = false, length = 20)
	public String getTxChannel() {
		return this.txChannel;
	}

	public void setTxChannel(String txChannel) {
		this.txChannel = txChannel;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStatByChannelId))
			return false;
		TxStatByChannelId castOther = (TxStatByChannelId) other;

		return ((this.getTxDate() == castOther.getTxDate()) || (this
				.getTxDate() != null
				&& castOther.getTxDate() != null && this.getTxDate().equals(
				castOther.getTxDate())))
				&& ((this.getTxChannel() == castOther.getTxChannel()) || (this
						.getTxChannel() != null
						&& castOther.getTxChannel() != null && this
						.getTxChannel().equals(castOther.getTxChannel())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTxDate() == null ? 0 : this.getTxDate().hashCode());
		result = 37 * result
				+ (getTxChannel() == null ? 0 : this.getTxChannel().hashCode());
		return result;
	}

}