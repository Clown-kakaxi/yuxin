package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_PD_PROD_SHOW_R database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_SHOW_R")
public class OcrmFPdProdShowR implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_PD_PROD_SHOW_R_RID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_PD_PROD_SHOW_R_RID_GENERATOR")
	@Column(name="R_ID", unique=true, nullable=false, precision=19)
	private Long rId;

	@Column(name="PLAN_ID", length=40)
	private String planId;

	@Column(name="PLAN_NAME", length=100)
	private String planName;

	@Column(name="R_FROM", length=300)
	private String rFrom;

	@Column(name="R_WHERE", length=300)
	private String rWhere;
	
	@Column(name="CUST_COLUMN", length=40)
	private String custColumn;

    public OcrmFPdProdShowR() {
    }

	public Long getRId() {
		return this.rId;
	}

	public String getCustColumn() {
		return custColumn;
	}

	public void setCustColumn(String custColumn) {
		this.custColumn = custColumn;
	}

	public void setRId(Long rId) {
		this.rId = rId;
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return this.planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getRFrom() {
		return this.rFrom;
	}

	public void setRFrom(String rFrom) {
		this.rFrom = rFrom;
	}

	public String getRWhere() {
		return this.rWhere;
	}

	public void setRWhere(String rWhere) {
		this.rWhere = rWhere;
	}

}