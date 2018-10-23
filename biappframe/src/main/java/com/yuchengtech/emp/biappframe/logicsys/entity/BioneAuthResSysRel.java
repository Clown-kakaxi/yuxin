package com.yuchengtech.emp.biappframe.logicsys.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_AUTH_RES_SYS_REL database table.
 * 
 */
@Entity
@Table(name="BIONE_AUTH_RES_SYS_REL")
public class BioneAuthResSysRel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BioneAuthResSysRelPK id;

    public BioneAuthResSysRel() {
    }

	public BioneAuthResSysRelPK getId() {
		return this.id;
	}

	public void setId(BioneAuthResSysRelPK id) {
		this.id = id;
	}
	
}