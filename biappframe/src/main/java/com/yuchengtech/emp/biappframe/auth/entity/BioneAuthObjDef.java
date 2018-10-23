package com.yuchengtech.emp.biappframe.auth.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_AUTH_OBJ_DEF database table.
 * 
 */
@Entity
@Table(name="BIONE_AUTH_OBJ_DEF")
public class BioneAuthObjDef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="OBJ_DEF_NO", unique=true, nullable=false, length=32)
	private String objDefNo;

	@Column(name="BEAN_NAME", length=100)
	private String beanName;

	@Column(name="OBJ_DEF_NAME", length=100)
	private String objDefName;

	@Column(length=500)
	private String remark;

    public BioneAuthObjDef() {
    }

	public String getObjDefNo() {
		return this.objDefNo;
	}

	public void setObjDefNo(String objDefNo) {
		this.objDefNo = objDefNo;
	}

	public String getBeanName() {
		return this.beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getObjDefName() {
		return this.objDefName;
	}

	public void setObjDefName(String objDefName) {
		this.objDefName = objDefName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}