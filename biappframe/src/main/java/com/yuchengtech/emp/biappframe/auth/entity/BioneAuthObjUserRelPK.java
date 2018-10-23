package com.yuchengtech.emp.biappframe.auth.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BIONE_AUTH_OBJ_USER_REL database table.
 * 
 */
@Embeddable
public class BioneAuthObjUserRelPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="LOGIC_SYS_NO", unique=true, nullable=false, length=32)
	private String logicSysNo;

	@Column(name="OBJ_DEF_NO", unique=true, nullable=false, length=32)
	private String objDefNo;

	@Column(name="OBJ_ID", unique=true, nullable=false, length=32)
	private String objId;

	@Column(name="USER_ID", unique=true, nullable=false, length=32)
	private String userId;

    public BioneAuthObjUserRelPK() {
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
	public String getObjId() {
		return this.objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BioneAuthObjUserRelPK)) {
			return false;
		}
		BioneAuthObjUserRelPK castOther = (BioneAuthObjUserRelPK)other;
		return 
			this.logicSysNo.equals(castOther.logicSysNo)
			&& this.objDefNo.equals(castOther.objDefNo)
			&& this.objId.equals(castOther.objId)
			&& this.userId.equals(castOther.userId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.logicSysNo.hashCode();
		hash = hash * prime + this.objDefNo.hashCode();
		hash = hash * prime + this.objId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
    }
}