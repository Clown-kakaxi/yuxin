package com.yuchengtech.bcrm.serviceManage.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_SE_CUST_SATISFY_QA database table.
 * 
 */
@Entity
@Table(name="OCRM_F_SE_CUST_SATISFY_QA")
public class OcrmFSeCustSatisfyQa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_SE_CUST_SATISFY_QA_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_SE_CUST_SATISFY_QA_ID_GENERATOR")
	private Long id;

	@Column(name="RESULT_ID")
	private String resultId;

	@Column(name="SATISFY_ID")
	private String satisfyId;

	private BigDecimal scoring;

	@Column(name="TITLE_ID")
	private String titleId;

	@Column(name="TITLE_REMARK")
	private String titleRemark;

    public OcrmFSeCustSatisfyQa() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResultId() {
		return this.resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getSatisfyId() {
		return this.satisfyId;
	}

	public void setSatisfyId(String satisfyId) {
		this.satisfyId = satisfyId;
	}

	public BigDecimal getScoring() {
		return this.scoring;
	}

	public void setScoring(BigDecimal scoring) {
		this.scoring = scoring;
	}

	public String getTitleId() {
		return this.titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getTitleRemark() {
		return this.titleRemark;
	}

	public void setTitleRemark(String titleRemark) {
		this.titleRemark = titleRemark;
	}

}