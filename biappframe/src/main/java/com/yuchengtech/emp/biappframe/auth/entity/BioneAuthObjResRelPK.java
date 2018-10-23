package com.yuchengtech.emp.biappframe.auth.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BIONE_AUTH_OBJ_RES_REL database table.
 * 
 */
@Embeddable
public class BioneAuthObjResRelPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="LOGIC_SYS_NO", unique=true, nullable=false, length=32)
	private String logicSysNo;

	@Column(name="OBJ_DEF_NO", unique=true, nullable=false, length=32)
	private String objDefNo;

	@Column(name="RES_DEF_NO", unique=true, nullable=false, length=32)
	private String resDefNo;

	@Column(name="PERMISSION_TYPE", unique=true, nullable=false, length=10)
	private String permissionType;

	@Column(name="OBJ_ID", unique=true, nullable=false, length=32)
	private String objId;

	@Column(name="RES_ID", unique=true, nullable=false, length=32)
	private String resId;

	@Column(name="PERMISSION_ID", unique=true, nullable=false, length=32)
	private String permissionId;

    public BioneAuthObjResRelPK() {
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
	public String getResDefNo() {
		return this.resDefNo;
	}
	public void setResDefNo(String resDefNo) {
		this.resDefNo = resDefNo;
	}
	public String getPermissionType() {
		return this.permissionType;
	}
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}
	public String getObjId() {
		return this.objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getResId() {
		return this.resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getPermissionId() {
		return this.permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BioneAuthObjResRelPK)) {
			return false;
		}
		BioneAuthObjResRelPK castOther = (BioneAuthObjResRelPK)other;
		return 
			this.logicSysNo.equals(castOther.logicSysNo)
			&& this.objDefNo.equals(castOther.objDefNo)
			&& this.resDefNo.equals(castOther.resDefNo)
			&& this.permissionType.equals(castOther.permissionType)
			&& this.objId.equals(castOther.objId)
			&& this.resId.equals(castOther.resId)
			&& this.permissionId.equals(castOther.permissionId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.logicSysNo.hashCode();
		hash = hash * prime + this.objDefNo.hashCode();
		hash = hash * prime + this.resDefNo.hashCode();
		hash = hash * prime + this.permissionType.hashCode();
		hash = hash * prime + this.objId.hashCode();
		hash = hash * prime + this.resId.hashCode();
		hash = hash * prime + this.permissionId.hashCode();
		
		return hash;
    }
}