package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppScvColDef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_SCV_COL_DEF")
public class AppScvColDef implements java.io.Serializable {

	// Fields

	private Long cumSerialNo;
	private Long tbSerialNo;
	private String cumName;
	private String cumCnnm;
	private Long cumShowSeq;
	private String isCondition;
	private String isResult;
	private String isMust;
	private String cumDescribe;

	// Constructors

	/** default constructor */
	public AppScvColDef() {
	}

	/** minimal constructor */
	public AppScvColDef(Long cumSerialNo) {
		this.cumSerialNo = cumSerialNo;
	}

	/** full constructor */
	public AppScvColDef(Long cumSerialNo, Long tbSerialNo,
			String cumName, String cumCnnm, Long cumShowSeq,
			String isCondition, String isResult, String isMust,
			String cumDescribe) {
		this.cumSerialNo = cumSerialNo;
		this.tbSerialNo = tbSerialNo;
		this.cumName = cumName;
		this.cumCnnm = cumCnnm;
		this.cumShowSeq = cumShowSeq;
		this.isCondition = isCondition;
		this.isResult = isResult;
		this.isMust = isMust;
		this.cumDescribe = cumDescribe;
	}

	// Property accessors
	@Id
	@Column(name = "CUM_SERIAL_NO", unique = true, nullable = false, precision = 22)
	public Long getCumSerialNo() {
		return this.cumSerialNo;
	}

	public void setCumSerialNo(Long cumSerialNo) {
		this.cumSerialNo = cumSerialNo;
	}

	@Column(name = "TB_SERIAL_NO", precision = 22)
	public Long getTbSerialNo() {
		return this.tbSerialNo;
	}

	public void setTbSerialNo(Long tbSerialNo) {
		this.tbSerialNo = tbSerialNo;
	}

	@Column(name = "CUM_NAME", length = 128)
	public String getCumName() {
		return this.cumName;
	}

	public void setCumName(String cumName) {
		this.cumName = cumName;
	}

	@Column(name = "CUM_CNNM")
	public String getCumCnnm() {
		return this.cumCnnm;
	}

	public void setCumCnnm(String cumCnnm) {
		this.cumCnnm = cumCnnm;
	}

	@Column(name = "CUM_SHOW_SEQ", precision = 22)
	public Long getCumShowSeq() {
		return this.cumShowSeq;
	}

	public void setCumShowSeq(Long cumShowSeq) {
		this.cumShowSeq = cumShowSeq;
	}

	@Column(name = "IS_CONDITION", length = 1)
	public String getIsCondition() {
		return this.isCondition;
	}

	public void setIsCondition(String isCondition) {
		this.isCondition = isCondition;
	}

	@Column(name = "IS_RESULT", length = 1)
	public String getIsResult() {
		return this.isResult;
	}

	public void setIsResult(String isResult) {
		this.isResult = isResult;
	}

	@Column(name = "IS_MUST", length = 1)
	public String getIsMust() {
		return this.isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	@Column(name = "CUM_DESCRIBE")
	public String getCumDescribe() {
		return this.cumDescribe;
	}

	public void setCumDescribe(String cumDescribe) {
		this.cumDescribe = cumDescribe;
	}

}