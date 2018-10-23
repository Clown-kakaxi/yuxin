package com.yuchengtech.bcrm.product.model;

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
 * The persistent class for the OCRM_F_PD_PROD_SHOW_COLUMN database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_SHOW_COLUMN")
public class OcrmFPdProdShowColumn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_PD_PROD_SHOW_COLUMN_RCLOUMNID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_PD_PROD_SHOW_COLUMN_RCLOUMNID_GENERATOR")
	@Column(name="R_CLOUMN_ID", unique=true, nullable=false, precision=19)
	private Long rCloumnId;

	@Column(name="CLOUMN_SQUENCE", precision=3)
	private BigDecimal cloumnSquence;

	@Column(name="COLUMN_ID", length=40)
	private String columnId;

	@Column(name="PLAN_ID", length=40)
	private String planId;

	@Column(name="R_TABLE_ID", length=40)
	private String rTableId;

    public OcrmFPdProdShowColumn() {
    }

	public Long getRCloumnId() {
		return this.rCloumnId;
	}

	public void setRCloumnId(Long rCloumnId) {
		this.rCloumnId = rCloumnId;
	}

	public BigDecimal getCloumnSquence() {
		return this.cloumnSquence;
	}

	public void setCloumnSquence(BigDecimal cloumnSquence) {
		this.cloumnSquence = cloumnSquence;
	}

	public String getColumnId() {
		return this.columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getRTableId() {
		return this.rTableId;
	}

	public void setRTableId(String rTableId) {
		this.rTableId = rTableId;
	}

}