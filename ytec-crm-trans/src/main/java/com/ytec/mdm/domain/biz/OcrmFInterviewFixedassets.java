package com.ytec.mdm.domain.biz;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Ocrm_F_Interview_Fixedassets")
public class OcrmFInterviewFixedassets {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "Ocrm_F_Interview_Fixedassets_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ocrm_F_Interview_Fixedassets_ID_GENERATOR")
	private BigDecimal id;

	@Column(name = "task_Number")
	private String taskNumber;

	@Column(name = "f_Htype")
	private BigDecimal fHtype;

	@Column(name = "f_Otype")
	private BigDecimal fOtype;

	@Column(name = "f_Area")
	private BigDecimal fArea;

	@Column(name = "f_Utype")
	private BigDecimal fUtype;

	@Column(name = "f_Assess")
	private BigDecimal fAssess;

	@Column(name = "f_Memo")
	private String fMemo;

	public OcrmFInterviewFixedassets() {
		super();
	}

	public OcrmFInterviewFixedassets(BigDecimal id, String taskNumber, BigDecimal fHtype, BigDecimal fOtype, BigDecimal fArea, BigDecimal fUtype, BigDecimal f_Assess, String fMemo) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.fHtype = fHtype;
		this.fOtype = fOtype;
		this.fArea = fArea;
		this.fUtype = fUtype;
		this.fAssess = f_Assess;
		this.fMemo = fMemo;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public BigDecimal getFHtype() {
		return fHtype;
	}

	public void setFHtype(BigDecimal fHtype) {
		this.fHtype = fHtype;
	}

	public BigDecimal getFOtype() {
		return fOtype;
	}

	public void setFOtype(BigDecimal fOtype) {
		this.fOtype = fOtype;
	}

	public BigDecimal getFArea() {
		return fArea;
	}

	public void setFArea(BigDecimal fArea) {
		this.fArea = fArea;
	}

	public BigDecimal getFUtype() {
		return fUtype;
	}

	public void setFUtype(BigDecimal fUtype) {
		this.fUtype = fUtype;
	}

	public BigDecimal getFAssess() {
		return fAssess;
	}

	public void setFAssess(BigDecimal f_Assess) {
		this.fAssess = f_Assess;
	}

	public String getFMemo() {
		return fMemo;
	}

	public void setFMemo(String fMemo) {
		this.fMemo = fMemo;
	}
}
