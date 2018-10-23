package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TX_CODE_MAP database table.
 * 
 */
@Embeddable
public class TxCodeMapPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SRC_SYS_CD")
	private String srcSysCd;

	@Column(name="SRC_CODE")
	private String srcCode;

	@Column(name="STD_CATE")
	private String stdCate;

    public TxCodeMapPK() {
    }
	public String getSrcSysCd() {
		return this.srcSysCd;
	}
	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}
	public String getSrcCode() {
		return this.srcCode;
	}
	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
	}
	public String getStdCate() {
		return this.stdCate;
	}
	public void setStdCate(String stdCate) {
		this.stdCate = stdCate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TxCodeMapPK)) {
			return false;
		}
		TxCodeMapPK castOther = (TxCodeMapPK)other;
		return 
			this.srcSysCd.equals(castOther.srcSysCd)
			&& this.srcCode.equals(castOther.srcCode)
			&& this.stdCate.equals(castOther.stdCate);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.srcSysCd.hashCode();
		hash = hash * prime + this.srcCode.hashCode();
		hash = hash * prime + this.stdCate.hashCode();
		
		return hash;
    }
}