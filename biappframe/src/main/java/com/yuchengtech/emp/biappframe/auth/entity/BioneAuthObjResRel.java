package com.yuchengtech.emp.biappframe.auth.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_AUTH_OBJ_RES_REL database table.
 * 
 */
@Entity
@Table(name="BIONE_AUTH_OBJ_RES_REL")
public class BioneAuthObjResRel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BioneAuthObjResRelPK id;

    public BioneAuthObjResRel() {
    }

	public BioneAuthObjResRelPK getId() {
		return this.id;
	}

	public void setId(BioneAuthObjResRelPK id) {
		this.id = id;
	}
	
}