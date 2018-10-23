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
 * The persistent class for the OCRM_F_PD_PROD_SHOW_TABLE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_SHOW_TABLE")
public class OcrmFPdProdShowTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_PD_PROD_SHOW_TABLE_RTABLEID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_PD_PROD_SHOW_TABLE_RTABLEID_GENERATOR")
	@Column(name="R_TABLE_ID", unique=true, nullable=false, precision=19)
	private Long rTableId;

	@Column(name="PLAN_ID", length=40)
	private String planId;

	@Column(name="PLAN_NAME", length=100)
	private String planName;

	@Column(name="TABLE_ID", length=40)
	private String tableId;

    public OcrmFPdProdShowTable() {
    }

	public Long getRTableId() {
		return this.rTableId;
	}

	public void setRTableId(Long rTableId) {
		this.rTableId = rTableId;
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

	public String getTableId() {
		return this.tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

}