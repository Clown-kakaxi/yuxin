package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the REST_APPLY database table. 请假申请表
 */
@Entity
@Table(name = "REST_APPLY")
public class RestApply implements Serializable {

	private static final long serialVersionUID = 6804884171635847347L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", nullable = false, precision = 22)
	private Long iD;

	@Column(name = "ACCOUNT_ID", precision = 22)
	private BigDecimal ACCOUNT_ID;

	@Column(name = "APPLY_CAUSE", length = 300)
	private String APPLY_CAUSE;

	@Temporal(TemporalType.DATE)
	@Column(name = "APPLY_DATE")
	private Date APPLY_DATE;

	@Column(name = "APPLY_NAME", length = 100)
	private String APPLY_NAME;

	@Column(name = "APPLY_NO", length = 20)
	private String APPLY_NO;

	@Column(name = "APPLY_RESULT", length = 20)
	private String APPLY_RESULT;

	@Column(name = "ORG_ID", precision = 22)
	private BigDecimal ORG_ID;

	@Temporal(TemporalType.DATE)
	@Column(name = "REST_END_DT")
	private Date REST_END_DT;

	@Temporal(TemporalType.DATE)
	@Column(name = "REST_START_DT")
	private Date REST_START_DT;

	@Column(name = "RESULT_REMARK", length = 300)
	private String RESULT_REMARK;

	@Column(length = 20)
	private String STATUS;

	public Long getID() {
		return iD;
	}

	public void setID(Long iD) {
		this.iD = iD;
	}

	public BigDecimal getACCOUNT_ID() {
		return ACCOUNT_ID;
	}

	public void setACCOUNT_ID(BigDecimal aCCOUNTID) {
		ACCOUNT_ID = aCCOUNTID;
	}

	public String getAPPLY_CAUSE() {
		return APPLY_CAUSE;
	}

	public void setAPPLY_CAUSE(String aPPLYCAUSE) {
		APPLY_CAUSE = aPPLYCAUSE;
	}

	public Date getAPPLY_DATE() {
		return APPLY_DATE;
	}

	public void setAPPLY_DATE(Date aPPLYDATE) {
		APPLY_DATE = aPPLYDATE;
	}

	public String getAPPLY_NAME() {
		return APPLY_NAME;
	}

	public void setAPPLY_NAME(String aPPLYNAME) {
		APPLY_NAME = aPPLYNAME;
	}

	public String getAPPLY_NO() {
		return APPLY_NO;
	}

	public void setAPPLY_NO(String aPPLYNO) {
		APPLY_NO = aPPLYNO;
	}

	public String getAPPLY_RESULT() {
		return APPLY_RESULT;
	}

	public void setAPPLY_RESULT(String aPPLYRESULT) {
		APPLY_RESULT = aPPLYRESULT;
	}

	public BigDecimal getORG_ID() {
		return ORG_ID;
	}

	public void setORG_ID(BigDecimal oRGID) {
		ORG_ID = oRGID;
	}

	public Date getREST_END_DT() {
		return REST_END_DT;
	}

	public void setREST_END_DT(Date rESTENDDT) {
		REST_END_DT = rESTENDDT;
	}

	public Date getREST_START_DT() {
		return REST_START_DT;
	}

	public void setREST_START_DT(Date rESTSTARTDT) {
		REST_START_DT = rESTSTARTDT;
	}

	public String getRESULT_REMARK() {
		return RESULT_REMARK;
	}

	public void setRESULT_REMARK(String rESULTREMARK) {
		RESULT_REMARK = rESULTREMARK;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}