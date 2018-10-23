package com.yuchengtech.emp.biappframe.label.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_LABEL_TYPE_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_LABEL_TYPE_INFO")
public class BioneLabelTypeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TYPE_ID")
	private String typeId;

	@Column(name="LABEL_OBJ_ID")
	private String labelObjId;

	private String remark;

	@Column(name="TYPE_NAME")
	private String typeName;

    public BioneLabelTypeInfo() {
    }

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getLabelObjId() {
		return labelObjId;
	}

	public void setLabelObjId(String labelObjId) {
		this.labelObjId = labelObjId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}