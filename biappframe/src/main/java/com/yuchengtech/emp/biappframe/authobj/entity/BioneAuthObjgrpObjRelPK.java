package com.yuchengtech.emp.biappframe.authobj.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BIONE_AUTH_OBJGRP_OBJ_REL database table.
 * 
 */
@Embeddable
public class BioneAuthObjgrpObjRelPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="LOGIC_SYS_NO", unique=true, nullable=false, length=32)
	private String logicSysNo;

	@Column(name="OBJ_DEF_NO", unique=true, nullable=false, length=32)
	private String objDefNo;

	@Column(name="OBJGRP_ID", unique=true, nullable=false, length=32)
	private String objgrpId;

	@Column(name="OBJ_ID", unique=true, nullable=false, length=32)
	private String objId;

    public BioneAuthObjgrpObjRelPK() {
    }
	public String getLogicSysNo() {
		return this.logicSysNo;
	}
	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}
	public String getObjDefNo() {
		return this.objDefNo;
	}
	public void setObjDefNo(String objDefNo) {
		this.objDefNo = objDefNo;
	}
	public String getObjgrpId() {
		return this.objgrpId;
	}
	public void setObjgrpId(String objgrpId) {
		this.objgrpId = objgrpId;
	}
	public String getObjId() {
		return this.objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BioneAuthObjgrpObjRelPK)) {
			return false;
		}
		BioneAuthObjgrpObjRelPK castOther = (BioneAuthObjgrpObjRelPK)other;
		return 
			this.logicSysNo.equals(castOther.logicSysNo)
			&& this.objDefNo.equals(castOther.objDefNo)
			&& this.objgrpId.equals(castOther.objgrpId)
			&& this.objId.equals(castOther.objId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.logicSysNo.hashCode();
		hash = hash * prime + this.objDefNo.hashCode();
		hash = hash * prime + this.objgrpId.hashCode();
		hash = hash * prime + this.objId.hashCode();
		
		return hash;
    }
}