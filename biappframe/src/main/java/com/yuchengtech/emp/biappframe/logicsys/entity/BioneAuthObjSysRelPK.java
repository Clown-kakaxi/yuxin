package com.yuchengtech.emp.biappframe.logicsys.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BIONE_AUTH_OBJ_SYS_REL database table.
 * 
 */
@Embeddable
public class BioneAuthObjSysRelPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="OBJ_DEF_NO", unique=true, nullable=false, length=32)
	private String objDefNo;

	@Column(name="LOGIC_SYS_NO", unique=true, nullable=false, length=32)
	private String logicSysNo;

    public BioneAuthObjSysRelPK() {
    }
	public String getObjDefNo() {
		return this.objDefNo;
	}
	public void setObjDefNo(String objDefNo) {
		this.objDefNo = objDefNo;
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
		if (!(other instanceof BioneAuthObjSysRelPK)) {
			return false;
		}
		BioneAuthObjSysRelPK castOther = (BioneAuthObjSysRelPK)other;
		return 
			this.objDefNo.equals(castOther.objDefNo)
			&& this.logicSysNo.equals(castOther.logicSysNo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.objDefNo.hashCode();
		hash = hash * prime + this.logicSysNo.hashCode();
		
		return hash;
    }
}