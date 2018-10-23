package com.yuchengtech.emp.biappframe.user.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BIONE_USER_ATTR_VAL database table.
 * 
 */
@Embeddable
public class BioneUserAttrValPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="USER_ID", unique=true, nullable=false, length=32)
	private String userId;

	@Column(name="ATTR_ID", unique=true, nullable=false, length=32)
	private String attrId;

    public BioneUserAttrValPK() {
    }
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAttrId() {
		return this.attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BioneUserAttrValPK)) {
			return false;
		}
		BioneUserAttrValPK castOther = (BioneUserAttrValPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.attrId.equals(castOther.attrId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.attrId.hashCode();
		
		return hash;
    }
}