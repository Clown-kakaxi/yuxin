package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxDeptInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_DEPT_INFO")
public class TxDeptInfo implements java.io.Serializable {

	// Fields

	private String deptCd;
	private String deptType;
	private String deptName;

	// Constructors

	/** default constructor */
	public TxDeptInfo() {
	}

	/** full constructor */
	public TxDeptInfo(String deptType, String deptName) {
		this.deptType = deptType;
		this.deptName = deptName;
	}

	// Property accessors
	@Id
	@Column(name = "DEPT_CD", unique = true, nullable = false, length = 20)
	public String getDeptCd() {
		return this.deptCd;
	}

	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	@Column(name = "DEPT_TYPE", length = 20)
	public String getDeptType() {
		return this.deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	@Column(name = "DEPT_NAME", length = 100)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}