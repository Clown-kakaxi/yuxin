package com.yuchengtech.emp.biappframe.label.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_LABEL_OBJ_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_LABEL_OBJ_INFO")
public class BioneLabelObjInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LABEL_OBJ_ID")
	private String labelObjId;
	
	@Column(name="LABEL_OBJ_NO")
	private String labelObjNo;
	
	@Column(name="LOGIC_SYS_NO")
	private String logicSysNo;

	@Column(name="LABEL_OBJ_NAME")
	private String labelObjName;

	private String remark;

    public BioneLabelObjInfo() {
    }

	public String getLabelObjNo() {
		return this.labelObjNo;
	}

	public void setLabelObjNo(String labelObjNo) {
		this.labelObjNo = labelObjNo;
	}

	public String getLabelObjName() {
		return this.labelObjName;
	}

	public void setLabelObjName(String labelObjName) {
		this.labelObjName = labelObjName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLabelObjId() {
		return labelObjId;
	}

	public void setLabelObjId(String labelObjId) {
		this.labelObjId = labelObjId;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

}