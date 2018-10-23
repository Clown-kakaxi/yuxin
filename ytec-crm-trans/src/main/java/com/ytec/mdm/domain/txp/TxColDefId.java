package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TxColDefId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxColDefId implements java.io.Serializable {

	// Fields

	private Long tabId;
	private Long colId;

	// Constructors

	/** default constructor */
	public TxColDefId() {
	}

	/** full constructor */
	public TxColDefId(Long tabId, Long colId) {
		this.tabId = tabId;
		this.colId = colId;
	}

	// Property accessors

	@Column(name = "TAB_ID", nullable = false, precision = 22)
	public Long getTabId() {
		return this.tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	@Column(name = "COL_ID", nullable = false, precision = 22)
	public Long getColId() {
		return this.colId;
	}

	public void setColId(Long colId) {
		this.colId = colId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxColDefId))
			return false;
		TxColDefId castOther = (TxColDefId) other;

		return ((this.getTabId() == castOther.getTabId()) || (this.getTabId() != null
				&& castOther.getTabId() != null && this.getTabId().equals(
				castOther.getTabId())))
				&& ((this.getColId() == castOther.getColId()) || (this
						.getColId() != null
						&& castOther.getColId() != null && this.getColId()
						.equals(castOther.getColId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTabId() == null ? 0 : this.getTabId().hashCode());
		result = 37 * result
				+ (getColId() == null ? 0 : this.getColId().hashCode());
		return result;
	}
	
	public String toString(){
		return "tabId"+tabId+"; colId"+colId;
	}

}