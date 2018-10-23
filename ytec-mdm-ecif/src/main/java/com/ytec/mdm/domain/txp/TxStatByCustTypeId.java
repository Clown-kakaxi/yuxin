package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxStatByCustTypeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStatByCustTypeId implements java.io.Serializable {

	// Fields

	private Date txDate;
	private String txCustType;

	// Constructors

	/** default constructor */
	public TxStatByCustTypeId() {
	}

	/** full constructor */
	public TxStatByCustTypeId(Date txDate, String txCustType) {
		this.txDate = txDate;
		this.txCustType = txCustType;
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

	@Column(name = "TX_CUST_TYPE", nullable = false, length = 20)
	public String getTxCustType() {
		return this.txCustType;
	}

	public void setTxCustType(String txCustType) {
		this.txCustType = txCustType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStatByCustTypeId))
			return false;
		TxStatByCustTypeId castOther = (TxStatByCustTypeId) other;

		return ((this.getTxDate() == castOther.getTxDate()) || (this
				.getTxDate() != null
				&& castOther.getTxDate() != null && this.getTxDate().equals(
				castOther.getTxDate())))
				&& ((this.getTxCustType() == castOther.getTxCustType()) || (this
						.getTxCustType() != null
						&& castOther.getTxCustType() != null && this
						.getTxCustType().equals(castOther.getTxCustType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTxDate() == null ? 0 : this.getTxDate().hashCode());
		result = 37
				* result
				+ (getTxCustType() == null ? 0 : this.getTxCustType()
						.hashCode());
		return result;
	}

}