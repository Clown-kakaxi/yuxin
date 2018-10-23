package com.yuchengtech.emp.ecif.transstatistic.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the TX_STAT_BY_TXCODE database table.
 * 
 */
@Embeddable
public class TxStatByTxcodePK implements Serializable{
	
	/**
	 * default serial version id, required for serializable classes.
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name="TX_DATE")
	private Date txDate;
	
	@Column(name="TX_CODE")
	private String txCode;

	public TxStatByTxcodePK(){
		
	}
	
	public TxStatByTxcodePK(Date txDate,String txCode){
		this.txDate=txDate;
		this.txCode=txCode;
	}
	
	public Date getTxDate() {
		return txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.txDate.hashCode();
		hash = hash * prime + this.txCode.hashCode();
		
		return hash;
    }
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TxStatByTxcodePK)) {
			return false;
		}
		TxStatByTxcodePK castOther = (TxStatByTxcodePK)other;
		return 
			this.txDate.equals(castOther.txDate)
			&& this.txCode.equals(castOther.txCode);

    }
}
