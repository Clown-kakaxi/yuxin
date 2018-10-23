package com.yuchengtech.emp.biappframe.authres.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the BIONE_MODULE_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_MODULE_INFO")
public class BioneModuleInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MODULE_ID", unique=true, nullable=false, length=32)
	private String moduleId;

	@Column(name="MODULE_NAME", length=100)
	private String moduleName;

	@Column(name="MODULE_NO", nullable=false, length=32)
	private String moduleNo;

	@Column(length=500)
	private String remark;

    public BioneModuleInfo() {
    }

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleNo() {
		return this.moduleNo;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}