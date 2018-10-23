package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TxColMapId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxColMapId implements java.io.Serializable {

	// Fields

	private String srcSysCd;
	private String srcTab;
	private String srcCol;

	// Constructors

	/** default constructor */
	public TxColMapId() {
	}

	/** full constructor */
	public TxColMapId(String srcSysCd, String srcTab, String srcCol) {
		this.srcSysCd = srcSysCd;
		this.srcTab = srcTab;
		this.srcCol = srcCol;
	}

	// Property accessors

	@Column(name = "SRC_SYS_CD", nullable = false, length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	@Column(name = "SRC_TAB", nullable = false, length = 60)
	public String getSrcTab() {
		return this.srcTab;
	}

	public void setSrcTab(String srcTab) {
		this.srcTab = srcTab;
	}

	@Column(name = "SRC_COL", nullable = false, length = 60)
	public String getSrcCol() {
		return this.srcCol;
	}

	public void setSrcCol(String srcCol) {
		this.srcCol = srcCol;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxColMapId))
			return false;
		TxColMapId castOther = (TxColMapId) other;

		return ((this.getSrcSysCd() == castOther.getSrcSysCd()) || (this
				.getSrcSysCd() != null
				&& castOther.getSrcSysCd() != null && this.getSrcSysCd()
				.equals(castOther.getSrcSysCd())))
				&& ((this.getSrcTab() == castOther.getSrcTab()) || (this
						.getSrcTab() != null
						&& castOther.getSrcTab() != null && this.getSrcTab()
						.equals(castOther.getSrcTab())))
				&& ((this.getSrcCol() == castOther.getSrcCol()) || (this
						.getSrcCol() != null
						&& castOther.getSrcCol() != null && this.getSrcCol()
						.equals(castOther.getSrcCol())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSrcSysCd() == null ? 0 : this.getSrcSysCd().hashCode());
		result = 37 * result
				+ (getSrcTab() == null ? 0 : this.getSrcTab().hashCode());
		result = 37 * result
				+ (getSrcCol() == null ? 0 : this.getSrcCol().hashCode());
		return result;
	}

}