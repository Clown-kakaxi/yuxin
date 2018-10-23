package com.yuchengtech.emp.biappframe.mtool.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIONE_DATASET_COL_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_DATASET_COL_INFO")
public class BioneDatasetColInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FIELD_ID")
	private String fieldId;

	@Column(name="CN_NAME")
	private String cnName;

	@Column(name="DATASET_ID")
	private String datasetId;

	@Column(name="EN_NAME")
	private String enName;

	@Column(name="FIELD_TYPE")
	private String fieldType;

	@Column(name="IS_NULLABLE")
	private String isNullable;

	@Column(name="IS_PK")
	private String isPk;

	@Column(name="IS_USE")
	private String isUse;

	@Column(name="LENGTH")
	private BigDecimal length;

	@Column(name="ORDER_NO")
	private BigDecimal orderNo;

	@Column(name="PRECISION")
	private String precision;
	
	@Column(name="IS_QUERY_FIELD")
	private String isQueryField;
	
	@Column(name="QUERY_LOGIC")
	private String queryLogic;

    public BioneDatasetColInfo() {
    }

	public String getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getCnName() {
		return this.cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getDatasetId() {
		return this.datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getIsNullable() {
		return this.isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getIsPk() {
		return this.isPk;
	}

	public void setIsPk(String isPk) {
		this.isPk = isPk;
	}

	public String getIsUse() {
		return this.isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

	public String getPrecision() {
		return this.precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getIsQueryField() {
		return isQueryField;
	}

	public void setIsQueryField(String isQueryField) {
		this.isQueryField = isQueryField;
	}

	public String getQueryLogic() {
		return queryLogic;
	}

	public void setQueryLogic(String queryLogic) {
		this.queryLogic = queryLogic;
	}

}