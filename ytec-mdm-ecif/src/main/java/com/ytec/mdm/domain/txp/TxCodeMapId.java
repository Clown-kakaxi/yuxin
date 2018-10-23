package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TxCodeMapId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxCodeMapId implements java.io.Serializable {

	// Fields

	private String srcSysCd;
	private String srcCode;
	private String stdCate;

	// Constructors

	/** default constructor */
	public TxCodeMapId() {
	}

	/** full constructor */
	public TxCodeMapId(String srcSysCd, String srcCode, String stdCate) {
		this.srcSysCd = srcSysCd;
		this.srcCode = srcCode;
		this.stdCate = stdCate;
	}

	// Property accessors

	@Column(name = "SRC_SYS_CD", nullable = false, length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	@Column(name = "SRC_CODE", nullable = false, length = 30)
	public String getSrcCode() {
		return this.srcCode;
	}

	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
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
		if (!(other instanceof TxCodeMapId))
			return false;
		TxCodeMapId castOther = (TxCodeMapId) other;

		return ((this.getSrcSysCd() == castOther.getSrcSysCd()) || (this
				.getSrcSysCd() != null
				&& castOther.getSrcSysCd() != null && this.getSrcSysCd()
				.equals(castOther.getSrcSysCd())))
				&& ((this.getSrcCode() == castOther.getSrcCode()) || (this
						.getSrcCode() != null
						&& castOther.getSrcCode() != null && this.getSrcCode()
						.equals(castOther.getSrcCode())))
				&& ((this.getStdCate() == castOther.getStdCate()) || (this
						.getStdCate() != null
						&& castOther.getStdCate() != null && this.getStdCate()
						.equals(castOther.getStdCate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSrcSysCd() == null ? 0 : this.getSrcSysCd().hashCode());
		result = 37 * result
				+ (getSrcCode() == null ? 0 : this.getSrcCode().hashCode());
		result = 37 * result
				+ (getStdCate() == null ? 0 : this.getStdCate().hashCode());
		return result;
	}

}