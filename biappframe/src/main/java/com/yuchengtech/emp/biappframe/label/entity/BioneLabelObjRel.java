package com.yuchengtech.emp.biappframe.label.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_LABEL_OBJ_REL database table.
 * 
 */
@Entity
@Table(name="BIONE_LABEL_OBJ_REL")
public class BioneLabelObjRel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BioneLabelObjRelPK id;

    public BioneLabelObjRel() {
    }

	public BioneLabelObjRelPK getId() {
		return this.id;
	}

	public void setId(BioneLabelObjRelPK id) {
		this.id = id;
	}

}