package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxInfoGrantObject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_INFO_GRANT_OBJECT")
public class TxInfoGrantObject implements java.io.Serializable {

	// Fields

	private Long grantObjectId;
	private String objectType;
	private String objectCode;

	// Constructors

	/** default constructor */
	public TxInfoGrantObject() {
	}

	/** full constructor */
	public TxInfoGrantObject(String objectType, String objectCode) {
		this.objectType = objectType;
		this.objectCode = objectCode;
	}

	// Property accessors
	@Id
		@Column(name = "GRANT_OBJECT_ID", unique = true, nullable = false)
	public Long getGrantObjectId() {
		return this.grantObjectId;
	}

	public void setGrantObjectId(Long grantObjectId) {
		this.grantObjectId = grantObjectId;
	}

	@Column(name = "OBJECT_TYPE", length = 20)
	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@Column(name = "OBJECT_CODE", length = 20)
	public String getObjectCode() {
		return this.objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

}