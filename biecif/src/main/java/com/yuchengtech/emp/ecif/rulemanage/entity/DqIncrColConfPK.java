package com.yuchengtech.emp.ecif.rulemanage.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ETL_CVR_COL_CONF database table.
 * 
 */
@Embeddable
public class DqIncrColConfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TID")
	private String tid;

	@Column(name="DST_COL")
	private String dstCol;

    public DqIncrColConfPK() {
    }
	public String getTid() {
		return this.tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getDstCol() {
		return this.dstCol;
	}
	public void setDstCol(String dstCol) {
		this.dstCol = dstCol;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DqIncrColConfPK)) {
			return false;
		}
		DqIncrColConfPK castOther = (DqIncrColConfPK)other;
		return 
			this.tid.equals(castOther.tid)
			&& this.dstCol.equals(castOther.dstCol);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.tid.hashCode();
		hash = hash * prime + this.dstCol.hashCode();
		
		return hash;
    }
}