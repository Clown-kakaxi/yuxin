package com.yuchengtech.bcrm.serviceManage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_SE_CUST_SATISFY_LIST database table.
 * 
 */
@Entity
@Table(name="OCRM_F_SE_CUST_SATISFY_LIST")
public class OcrmFSeCustSatisfyList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_SE_CUST_SATISFY_LIST_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_SE_CUST_SATISFY_LIST_ID_GENERATOR")
	private Long id;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

    @Temporal( TemporalType.DATE)
	@Column(name="EVALUATE_DATE")
	private Date evaluateDate;

	@Column(name="EVALUATE_NAME")
	private String evaluateName;

	@Column(name="INDAGETE_QA_SCORING")
	private BigDecimal indageteQaScoring;

	@Column(name="PAPERS_ID")
	private String papersId;

	@Column(name="SATISFY_TYPE")
	private String satisfyType;

    public OcrmFSeCustSatisfyList() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getEvaluateDate() {
		return this.evaluateDate;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	public String getEvaluateName() {
		return this.evaluateName;
	}

	public void setEvaluateName(String evaluateName) {
		this.evaluateName = evaluateName;
	}

	public BigDecimal getIndageteQaScoring() {
		return this.indageteQaScoring;
	}

	public void setIndageteQaScoring(BigDecimal indageteQaScoring) {
		this.indageteQaScoring = indageteQaScoring;
	}

	public String getPapersId() {
		return this.papersId;
	}

	public void setPapersId(String papersId) {
		this.papersId = papersId;
	}

	public String getSatisfyType() {
		return this.satisfyType;
	}

	public void setSatisfyType(String satisfyType) {
		this.satisfyType = satisfyType;
	}

}