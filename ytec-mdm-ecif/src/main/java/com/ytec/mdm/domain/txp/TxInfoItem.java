package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxInfoItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_INFO_ITEM")
public class TxInfoItem implements java.io.Serializable {

	// Fields

	private Long infoItemId;
	private String subject1;
	private String subject2;
	private String subject3;
	private String subject4;
	private String schemaname;
	private Long tabId;
	private Long colId;
	private String isKeyInfo;
	private String isNotNull;

	// Constructors

	/** default constructor */
	public TxInfoItem() {
	}

	/** full constructor */
	public TxInfoItem(String subject1, String subject2, String subject3,
			String subject4, String schemaname, Long tabId,
			Long colId, String isKeyInfo, String isNotNull) {
		this.subject1 = subject1;
		this.subject2 = subject2;
		this.subject3 = subject3;
		this.subject4 = subject4;
		this.schemaname = schemaname;
		this.tabId = tabId;
		this.colId = colId;
		this.isKeyInfo = isKeyInfo;
		this.isNotNull = isNotNull;
	}

	// Property accessors
	@Id
		@Column(name = "INFO_ITEM_ID", unique = true, nullable = false)
	public Long getInfoItemId() {
		return this.infoItemId;
	}

	public void setInfoItemId(Long infoItemId) {
		this.infoItemId = infoItemId;
	}

	@Column(name = "SUBJECT1", length = 20)
	public String getSubject1() {
		return this.subject1;
	}

	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}

	@Column(name = "SUBJECT2", length = 20)
	public String getSubject2() {
		return this.subject2;
	}

	public void setSubject2(String subject2) {
		this.subject2 = subject2;
	}

	@Column(name = "SUBJECT3", length = 20)
	public String getSubject3() {
		return this.subject3;
	}

	public void setSubject3(String subject3) {
		this.subject3 = subject3;
	}

	@Column(name = "SUBJECT4", length = 20)
	public String getSubject4() {
		return this.subject4;
	}

	public void setSubject4(String subject4) {
		this.subject4 = subject4;
	}

	@Column(name = "SCHEMANAME")
	public String getSchemaname() {
		return this.schemaname;
	}

	public void setSchemaname(String schemaname) {
		this.schemaname = schemaname;
	}

	@Column(name = "TAB_ID", precision = 22)
	public Long getTabId() {
		return this.tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	@Column(name = "COL_ID", precision = 22)
	public Long getColId() {
		return this.colId;
	}

	public void setColId(Long colId) {
		this.colId = colId;
	}

	@Column(name = "IS_KEY_INFO", length = 1)
	public String getIsKeyInfo() {
		return this.isKeyInfo;
	}

	public void setIsKeyInfo(String isKeyInfo) {
		this.isKeyInfo = isKeyInfo;
	}

	@Column(name = "IS_NOT_NULL", length = 1)
	public String getIsNotNull() {
		return this.isNotNull;
	}

	public void setIsNotNull(String isNotNull) {
		this.isNotNull = isNotNull;
	}

}