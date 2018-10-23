package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxStatBySysId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStatBySysId implements java.io.Serializable {

	// Fields

	private Date txDate;
	private String txSys;

	// Constructors

	/** default constructor */
	public TxStatBySysId() {
	}

	/** full constructor */
	public TxStatBySysId(Date txDate, String txSys) {
		this.txDate = txDate;
		this.txSys = txSys;
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

	@Column(name = "TX_SYS", nullable = false, length = 20)
	public String getTxSys() {
		return this.txSys;
	}

	public void setTxSys(String txSys) {
		this.txSys = txSys;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStatBySysId))
			return false;
		TxStatBySysId castOther = (TxStatBySysId) other;

		return ((this.getTxDate() == castOther.getTxDate()) || (this
				.getTxDate() != null
				&& castOther.getTxDate() != null && this.getTxDate().equals(
				castOther.getTxDate())))
				&& ((this.getTxSys() == castOther.getTxSys()) || (this
						.getTxSys() != null
						&& castOther.getTxSys() != null && this.getTxSys()
						.equals(castOther.getTxSys())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTxDate() == null ? 0 : this.getTxDate().hashCode());
		result = 37 * result
				+ (getTxSys() == null ? 0 : this.getTxSys().hashCode());
		return result;
	}

}