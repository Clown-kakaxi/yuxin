package com.yuchengtech.emp.biappframe.logicsys.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BIONE_AUTH_RES_SYS_REL database table.
 * 
 */
@Embeddable
public class BioneAuthResSysRelPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="RES_DEF_NO", unique=true, nullable=false, length=32)
	private String resDefNo;

	@Column(name="LOGIC_SYS_NO", unique=true, nullable=false, length=32)
	private String logicSysNo;

    public BioneAuthResSysRelPK() {
    }
	public String getResDefNo() {
		return this.resDefNo;
	}
	public void setResDefNo(String resDefNo) {
		this.resDefNo = resDefNo;
	}
	public String getLogicSysNo() {
		return this.logicSysNo;
	}
	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BioneAuthResSysRelPK)) {
			return false;
		}
		BioneAuthResSysRelPK castOther = (BioneAuthResSysRelPK)other;
		return 
			this.resDefNo.equals(castOther.resDefNo)
			&& this.logicSysNo.equals(castOther.logicSysNo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.resDefNo.hashCode();
		hash = hash * prime + this.logicSysNo.hashCode();
		
		return hash;
    }
}