package com.yuchengtech.emp.ecif.transstatistic.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TX_STAT_BY_TXCODE")
public class TxStatByTxcode implements Serializable {
	public TxStatByTxcode(){
		
	}
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private TxStatByTxcodePK txStatByTxcodePK;

	@Column(name="TX_COUNT")
	private int txCount;

	public int getTxCount() {
		return txCount;
	}

	public void setTxCount(int txCount) {
		this.txCount = txCount;
	}

	public TxStatByTxcodePK getTxStatByTxcodePK() {
		return txStatByTxcodePK;
	}

	public void setTxStatByTxcodePK(TxStatByTxcodePK txStatByTxcodePK) {
		this.txStatByTxcodePK = txStatByTxcodePK;
	}
	
	
}
