package com.yuchengtech.emp.biappframe.mtool.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the BIONE_DATASET_INFO database table.
 * 
 */
@Entity
@Table(name = "BIONE_DATASET_INFO")
public class BioneDatasetInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DATASET_ID")
	private String datasetId;

	@Column(name = "CATALOG_ID")
	private String catalogId;

	@Column(name = "DATASET_NAME")
	private String datasetName;

	@Column(name = "DS_ID")
	private String dsId;

	@Column(name = "DS_TYPE")
	private String dsType;

	@Column(name = "QUERY_SQL")
	private String querySql;

	private String remark;

	@Column(name = "T_ENAME")
	private String tableEname;

	@Column(name = "LOGIC_SYS_NO", nullable = false, length = 32)
	private String logicSysNo;

	public BioneDatasetInfo() {
	}

	public String getDatasetId() {
		return this.datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getDatasetName() {
		return this.datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getDsId() {
		return this.dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getDsType() {
		return this.dsType;
	}

	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	public String getQuerySql() {
		return this.querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTableEname() {
		return tableEname;
	}

	public void setTableEname(String tableEname) {
		this.tableEname = tableEname;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

}