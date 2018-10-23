package com.yuchengtech.emp.biappframe.auth.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_AUTH_RES_DEF database table.
 * 
 */
@Entity
@Table(name="BIONE_AUTH_RES_DEF")
public class BioneAuthResDef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RES_DEF_NO", unique=true, nullable=false, length=32)
	private String resDefNo;

	@Column(name="BEAN_NAME", length=100)
	private String beanName;

	@Column(length=500)
	private String remark;

	@Column(name="RES_NAME", length=100)
	private String resName;

    public BioneAuthResDef() {
    }

	public String getResDefNo() {
		return this.resDefNo;
	}

	public void setResDefNo(String resDefNo) {
		this.resDefNo = resDefNo;
	}

	public String getBeanName() {
		return this.beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

}