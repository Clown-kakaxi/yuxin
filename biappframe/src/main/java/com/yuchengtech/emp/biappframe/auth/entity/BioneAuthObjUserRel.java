package com.yuchengtech.emp.biappframe.auth.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_AUTH_OBJ_USER_REL database table.
 * 
 */
@Entity
@Table(name="BIONE_AUTH_OBJ_USER_REL")
public class BioneAuthObjUserRel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BioneAuthObjUserRelPK id;

    public BioneAuthObjUserRel() {
    }

	public BioneAuthObjUserRelPK getId() {
		return this.id;
	}

	public void setId(BioneAuthObjUserRelPK id) {
		this.id = id;
	}
	
}