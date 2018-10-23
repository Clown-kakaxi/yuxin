package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TX_COL_DEF database table.
 * 
 */
@Embeddable
public class TxColDefPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TAB_ID")
	private long tabId;

	@Column(name="COL_ID")
	private long colId;

    public TxColDefPK() {
    }
	public long getTabId() {
		return this.tabId;
	}
	public void setTabId(long tabId) {
		this.tabId = tabId;
	}
	public long getColId() {
		return this.colId;
	}
	public void setColId(long colId) {
		this.colId = colId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TxColDefPK)) {
			return false;
		}
		TxColDefPK castOther = (TxColDefPK)other;
		return 
			(this.tabId == castOther.tabId)
			&& (this.colId == castOther.colId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.tabId ^ (this.tabId >>> 32)));
		hash = hash * prime + ((int) (this.colId ^ (this.colId >>> 32)));
		
		return hash;
    }
}