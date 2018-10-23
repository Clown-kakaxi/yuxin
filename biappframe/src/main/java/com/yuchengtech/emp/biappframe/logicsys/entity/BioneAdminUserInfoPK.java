package com.yuchengtech.emp.biappframe.logicsys.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BIONE_ADMIN_USER_INFO database table.
 * 
 */
@Embeddable
public class BioneAdminUserInfoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="LOGIC_SYS_ID", unique=true, nullable=false, length=32)
	private String logicSysId;

	@Column(name="USER_ID", unique=true, nullable=false, length=32)
	private String userId;

    public BioneAdminUserInfoPK() {
    }
	public String getLogicSysId() {
		return this.logicSysId;
	}
	public void setLogicSysId(String logicSysId) {
		this.logicSysId = logicSysId;
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
		if (!(other instanceof BioneAdminUserInfoPK)) {
			return false;
		}
		BioneAdminUserInfoPK castOther = (BioneAdminUserInfoPK)other;
		return 
			this.logicSysId.equals(castOther.logicSysId)
			&& this.userId.equals(castOther.userId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.logicSysId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
    }
}