package com.yuchengtech.emp.biappframe.label.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BIONE_LABEL_OBJ_REL database table.
 * 
 */
@Embeddable
public class BioneLabelObjRelPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="LABEL_OBJ_ID")
	private String labelObjId;

	@Column(name="LABEL_ID")
	private String labelId;

	@Column(name="OBJ_ID")
	private String objId;

    public BioneLabelObjRelPK() {
    }
	public String getLabelObjId() {
		return this.labelObjId;
	}
	public void setLabelObjId(String labelObjId) {
		this.labelObjId = labelObjId;
	}
	public String getLabelId() {
		return this.labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
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
		if (!(other instanceof BioneLabelObjRelPK)) {
			return false;
		}
		BioneLabelObjRelPK castOther = (BioneLabelObjRelPK)other;
		return 
			this.labelObjId.equals(castOther.labelObjId)
			&& this.labelId.equals(castOther.labelId)
			&& this.objId.equals(castOther.objId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.labelObjId.hashCode();
		hash = hash * prime + this.labelId.hashCode();
		hash = hash * prime + this.objId.hashCode();
		
		return hash;
    }
}