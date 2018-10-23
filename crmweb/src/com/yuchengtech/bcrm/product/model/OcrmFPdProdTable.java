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
 * The persistent class for the OCRM_F_PD_PROD_TABLE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_TABLE")
public class OcrmFPdProdTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_PD_PROD_TABLE_TABLEID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_PD_PROD_TABLE_TABLEID_GENERATOR")
	@Column(name="TABLE_ID", unique=true, nullable=false, precision=19)
	private Long tableId;

	@Column(name="TABLE_CH_NAME", length=100)
	private String tableChName;

	@Column(name="TABLE_NAME", length=40)
	private String tableName;

	@Column(name="TABLE_OTH_NAME", length=10)
	private String tableOthName;

	@Column(name="TABLE_TYPE", length=10)
	private String tableType;

    public OcrmFPdProdTable() {
    }

	public Long getTableId() {
		return this.tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getTableChName() {
		return this.tableChName;
	}

	public void setTableChName(String tableChName) {
		this.tableChName = tableChName;
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

	public String getTableType() {
		return this.tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

}