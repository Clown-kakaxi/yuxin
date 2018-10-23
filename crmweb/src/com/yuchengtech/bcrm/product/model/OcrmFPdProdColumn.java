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
 * The persistent class for the OCRM_F_PD_PROD_COLUMN database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_COLUMN")
public class OcrmFPdProdColumn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_PD_PROD_COLUMN_COLUMNID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_PD_PROD_COLUMN_COLUMNID_GENERATOR")
	@Column(name="COLUMN_ID", unique=true, nullable=false, precision=19)
	private Long columnId;

	@Column(name="ALIGN_TYPE", length=10)
	private String alignType;

	@Column(name="COLUMN_NAME", length=40)
	private String columnName;

	@Column(name="COLUMN_OTH_NAME", length=100)
	private String columnOthName;

	@Column(name="COLUMN_TYPE", length=10)
	private String columnType;

	@Column(name="DIC_NAME", length=40)
	private String dicName;

	@Column(name="SHOW_WIDTH", precision=3)
	private BigDecimal showWidth;

	@Column(name="TABLE_CH_NAME", length=100)
	private String tableChName;

	@Column(name="TABLE_ID", length=40)
	private String tableId;

	@Column(name="TABLE_NAME", length=40)
	private String tableName;

	@Column(name="TABLE_OTH_NAME", length=10)
	private String tableOthName;

    public OcrmFPdProdColumn() {
    }

	public Long getColumnId() {
		return this.columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public String getAlignType() {
		return this.alignType;
	}

	public void setAlignType(String alignType) {
		this.alignType = alignType;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnOthName() {
		return this.columnOthName;
	}

	public void setColumnOthName(String columnOthName) {
		this.columnOthName = columnOthName;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getDicName() {
		return this.dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public BigDecimal getShowWidth() {
		return this.showWidth;
	}

	public void setShowWidth(BigDecimal showWidth) {
		this.showWidth = showWidth;
	}

	public String getTableChName() {
		return this.tableChName;
	}

	public void setTableChName(String tableChName) {
		this.tableChName = tableChName;
	}

	public String getTableId() {
		return this.tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableOthName() {
		return this.tableOthName;
	}

	public void setTableOthName(String tableOthName) {
		this.tableOthName = tableOthName;
	}

}