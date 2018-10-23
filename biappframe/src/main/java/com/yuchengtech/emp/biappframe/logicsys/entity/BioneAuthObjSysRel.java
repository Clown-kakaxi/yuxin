package com.yuchengtech.emp.biappframe.logicsys.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_AUTH_OBJ_SYS_REL database table.
 * 
 */
@Entity
@Table(name="BIONE_AUTH_OBJ_SYS_REL")
public class BioneAuthObjSysRel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BioneAuthObjSysRelPK id;

    public BioneAuthObjSysRel() {
    }

	public BioneAuthObjSysRelPK getId() {
		return this.id;
	}

	public void setId(BioneAuthObjSysRelPK id) {
		this.id = id;
	}
	
}