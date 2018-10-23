package com.yuchengtech.emp.biappframe.label.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_LABEL_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_LABEL_INFO")
public class BioneLabelInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LABEL_ID")
	private String labelId;

	@Column(name="LABEL_NAME")
	private String labelName;

	private String remark;

	@Column(name="TYPE_ID")
	private String typeId;

    public BioneLabelInfo() {
    }

	public String getLabelId() {
		return this.labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return this.labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}