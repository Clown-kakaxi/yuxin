package com.yuchengtech.emp.biappframe.user.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_USER_ATTR_VAL database table.
 * 
 */
@Entity
@Table(name="BIONE_USER_ATTR_VAL")
public class BioneUserAttrVal implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BioneUserAttrValPK id;

	@Column(name="ATTR_VALUE", length=500)
	private String attrValue;

    public BioneUserAttrVal() {
    }

	public BioneUserAttrValPK getId() {
		return this.id;
	}

	public void setId(BioneUserAttrValPK id) {
		this.id = id;
	}
	
	public String getAttrValue() {
		return this.attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

}