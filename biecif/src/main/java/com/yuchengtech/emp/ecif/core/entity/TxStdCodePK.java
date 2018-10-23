package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TX_STD_CODE database table.
 * 
 */
@Embeddable
public class TxStdCodePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="STD_CODE")
	private String stdCode;

	@Column(name="STD_CATE")
	private String stdCate;

    public TxStdCodePK() {
    }
	public String getStdCode() {
		return this.stdCode;
	}
	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
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
		if (!(other instanceof TxStdCodePK)) {
			return false;
		}
		TxStdCodePK castOther = (TxStdCodePK)other;
		return 
			this.stdCode.equals(castOther.stdCode)
			&& this.stdCate.equals(castOther.stdCate);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.stdCode.hashCode();
		hash = hash * prime + this.stdCate.hashCode();
		
		return hash;
    }
}