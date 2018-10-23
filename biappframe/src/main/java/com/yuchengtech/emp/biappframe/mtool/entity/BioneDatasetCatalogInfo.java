package com.yuchengtech.emp.biappframe.mtool.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_DATASET_CATALOG_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_DATASET_CATALOG_INFO")
public class BioneDatasetCatalogInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CATALOG_ID")
	private String catalogId;

	@Column(name="CATALOG_DESC")
	private String catalogDesc;

	@Column(name="CATALOG_NAME")
	private String catalogName;

	@Column(name="UP_ID")
	private String upId;
	
	@Column(name = "LOGIC_SYS_NO", nullable = false, length = 32)
	private String logicSysNo;

    public BioneDatasetCatalogInfo() {
    }

	public String getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogDesc() {
		return this.catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public String getCatalogName() {
		return this.catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getUpId() {
		return this.upId;
	}

	public void setUpId(String upId) {
		this.upId = upId;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}
	
	

}