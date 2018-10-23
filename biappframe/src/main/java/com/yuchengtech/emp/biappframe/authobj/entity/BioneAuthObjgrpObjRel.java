package com.yuchengtech.emp.biappframe.authobj.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the BIONE_AUTH_OBJGRP_OBJ_REL database table.
 * 
 */
@Entity
@Table(name="BIONE_AUTH_OBJGRP_OBJ_REL")
public class BioneAuthObjgrpObjRel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BioneAuthObjgrpObjRelPK id;

    public BioneAuthObjgrpObjRel() {
    }

	public BioneAuthObjgrpObjRelPK getId() {
		return this.id;
	}

	public void setId(BioneAuthObjgrpObjRelPK id) {
		this.id = id;
	}
	
}