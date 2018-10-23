package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the REVIEW_MAPPING database table.
 * 
 */
@Entity
@Table(name="REVIEW_MAPPING")
public class ReviewMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="MODULE_ITEM")
	private String moduleItem;

	@Column(name="ORIGIN_COLUMN")
	private String originColumn;

	@Column(name="PAGE_COLUMN")
	private String pageColumn;

	@Column(name="TABLE_NAME")
	private String tableName;

    public ReviewMapping() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getModuleItem() {
		return this.moduleItem;
	}

	public void setModuleItem(String moduleItem) {
		this.moduleItem = moduleItem;
	}

	public String getOriginColumn() {
		return this.originColumn;
	}

	public void setOriginColumn(String originColumn) {
		this.originColumn = originColumn;
	}

	public String getPageColumn() {
		return this.pageColumn;
	}

	public void setPageColumn(String pageColumn) {
		this.pageColumn = pageColumn;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}