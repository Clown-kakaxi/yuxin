package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppScvOperColDef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_SCV_OPER_COL_DEF")
public class AppScvOperColDef implements java.io.Serializable {

	// Fields

	private Long operSerialNo;
	private String oprNo;
	private Long cumSerialNo;
	private String cstType;

	// Constructors

	/** default constructor */
	public AppScvOperColDef() {
	}

	/** minimal constructor */
	public AppScvOperColDef(Long operSerialNo) {
		this.operSerialNo = operSerialNo;
	}

	/** full constructor */
	public AppScvOperColDef(Long operSerialNo, String oprNo,
			Long cumSerialNo, String cstType) {
		this.operSerialNo = operSerialNo;
		this.oprNo = oprNo;
		this.cumSerialNo = cumSerialNo;
		this.cstType = cstType;
	}

	// Property accessors
	@Id
	@Column(name = "OPER_SERIAL_NO", unique = true, nullable = false)
	public Long getOperSerialNo() {
		return this.operSerialNo;
	}

	public void setOperSerialNo(Long operSerialNo) {
		this.operSerialNo = operSerialNo;
	}

	@Column(name = "OPR_NO", length = 32)
	public String getOprNo() {
		return this.oprNo;
	}

	public void setOprNo(String oprNo) {
		this.oprNo = oprNo;
	}

	@Column(name = "CUM_SERIAL_NO", precision = 22)
	public Long getCumSerialNo() {
		return this.cumSerialNo;
	}

	public void setCumSerialNo(Long cumSerialNo) {
		this.cumSerialNo = cumSerialNo;
	}

	@Column(name = "CST_TYPE", length = 1)
	public String getCstType() {
		return this.cstType;
	}

	public void setCstType(String cstType) {
		this.cstType = cstType;
	}

}