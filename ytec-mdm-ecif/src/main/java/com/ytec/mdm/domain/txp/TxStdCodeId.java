package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TxStdCodeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStdCodeId implements java.io.Serializable {

	// Fields

	private String stdCode;
	private String stdCate;

	// Constructors

	/** default constructor */
	public TxStdCodeId() {
	}

	/** full constructor */
	public TxStdCodeId(String stdCode, String stdCate) {
		this.stdCode = stdCode;
		this.stdCate = stdCate;
	}

	// Property accessors

	@Column(name = "STD_CODE", nullable = false, length = 20)
	public String getStdCode() {
		return this.stdCode;
	}

	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
	}

	@Column(name = "STD_CATE", nullable = false, length = 10)
	public String getStdCate() {
		return this.stdCate;
	}

	public void setStdCate(String stdCate) {
		this.stdCate = stdCate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStdCodeId))
			return false;
		TxStdCodeId castOther = (TxStdCodeId) other;

		return ((this.getStdCode() == castOther.getStdCode()) || (this
				.getStdCode() != null
				&& castOther.getStdCode() != null && this.getStdCode().equals(
				castOther.getStdCode())))
				&& ((this.getStdCate() == castOther.getStdCate()) || (this
						.getStdCate() != null
						&& castOther.getStdCate() != null && this.getStdCate()
						.equals(castOther.getStdCate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStdCode() == null ? 0 : this.getStdCode().hashCode());
		result = 37 * result
				+ (getStdCate() == null ? 0 : this.getStdCate().hashCode());
		return result;
	}

}