package com.yuchengtech.emp.biappframe.authres.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the BIONE_RES_OPER_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_RES_OPER_INFO")
public class BioneResOperInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="OPER_ID", unique=true, nullable=false, length=32)
	private String operId;

	@Column(name="VISIT_URL", length=100)
	private String visitUrl;

	@Column(name="METHOD_NAME", length=100)
	private String methodName;

	@Column(name="OPER_NAME", length=100)
	private String operName;

	@Column(name="OPER_NO", nullable=false, length=100)
	private String operNo;

	@Column(length=500)
	private String remark;

	@Column(name="RES_NO", nullable=false, length=32)
	private String resNo;
	
	@Column(name="RES_DEF_NO", nullable=false, length=32)
	private String resDefNo;

	@Column(name="UP_NO", nullable=false, length=32)
	private String upNo;

    public BioneResOperInfo() {
    }

	public String getOperId() {
		return this.operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getOperName() {
		return this.operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperNo() {
		return this.operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResNo() {
		return this.resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getUpNo() {
		return this.upNo;
	}

	public void setUpNo(String upNo) {
		this.upNo = upNo;
	}

	public String getResDefNo() {
		return resDefNo;
	}

	public void setResDefNo(String resDefNo) {
		this.resDefNo = resDefNo;
	}

}