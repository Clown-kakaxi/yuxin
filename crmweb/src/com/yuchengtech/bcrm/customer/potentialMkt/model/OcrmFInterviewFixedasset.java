package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_FIXEDASSETS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_FIXEDASSETS")
public class OcrmFInterviewFixedasset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_FIXEDASSETS_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_FIXEDASSETS_GENERATOR")
	private String id;
	
	@Column(name="F_AREA")
	private BigDecimal fArea;

	@Column(name="F_ASSESS")
	private BigDecimal fAssess;

	@Column(name="F_HTYPE")
	private BigDecimal fHtype;

	@Column(name="F_MEMO")
	private String fMemo;

	@Column(name="F_OTYPE")
	private BigDecimal fOtype;

	@Column(name="F_UTYPE")
	private BigDecimal fUtype;

	@Column(name="TASK_NUMBER")
	private String taskNumber;
	
	@Column(name="F_HOLDER")
	private String fHolder;
	
	@Column(name="F_REGION")
	private String fRegion;
	
	@Column(name="F_SECURED")
	private BigDecimal fSecured;
	

    public OcrmFInterviewFixedasset() {
    }

	public BigDecimal getFArea() {
		return this.fArea;
	}

	public void setFArea(BigDecimal fArea) {
		this.fArea = fArea;
	}

	public BigDecimal getFAssess() {
		return this.fAssess;
	}

	public void setFAssess(BigDecimal fAssess) {
		this.fAssess = fAssess;
	}

	public BigDecimal getFHtype() {
		return this.fHtype;
	}

	public void setFHtype(BigDecimal fHtype) {
		this.fHtype = fHtype;
	}

	public String getFMemo() {
		return this.fMemo;
	}

	public void setFMemo(String fMemo) {
		this.fMemo = fMemo;
	}

	public BigDecimal getFOtype() {
		return this.fOtype;
	}

	public void setFOtype(BigDecimal fOtype) {
		this.fOtype = fOtype;
	}

	public BigDecimal getFUtype() {
		return this.fUtype;
	}

	public void setFUtype(BigDecimal fUtype) {
		this.fUtype = fUtype;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getFHolder() {
		return this.fHolder;
	}

	public void setFHolder(String fHolder) {
		this.fHolder = fHolder;
	}

	public String getFRegion() {
		return this.fRegion;
	}

	public void setFRegion(String fRegion) {
		this.fRegion = fRegion;
	}

	public BigDecimal getFSecured() {
		return this.fSecured;
	}

	public void setFSecured(BigDecimal fSecured) {
		this.fSecured = fSecured;
	}

}