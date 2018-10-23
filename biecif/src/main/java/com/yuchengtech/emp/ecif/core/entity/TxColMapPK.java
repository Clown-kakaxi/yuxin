package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TX_COL_MAP database table.
 * 
 */
@Embeddable
public class TxColMapPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SRC_SYS_CD")
	private String srcSysCd;

	@Column(name="SRC_TAB")
	private String srcTab;

	@Column(name="SRC_COL")
	private String srcCol;

    public TxColMapPK() {
    }
	public String getSrcSysCd() {
		return this.srcSysCd;
	}
	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}
	public String getSrcTab() {
		return this.srcTab;
	}
	public void setSrcTab(String srcTab) {
		this.srcTab = srcTab;
	}
	public String getSrcCol() {
		return this.srcCol;
	}
	public void setSrcCol(String srcCol) {
		this.srcCol = srcCol;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TxColMapPK)) {
			return false;
		}
		TxColMapPK castOther = (TxColMapPK)other;
		return 
			this.srcSysCd.equals(castOther.srcSysCd)
			&& this.srcTab.equals(castOther.srcTab)
			&& this.srcCol.equals(castOther.srcCol);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.srcSysCd.hashCode();
		hash = hash * prime + this.srcTab.hashCode();
		hash = hash * prime + this.srcCol.hashCode();
		
		return hash;
    }
}