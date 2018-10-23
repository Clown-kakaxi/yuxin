package com.ytec.fubonecif.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the M_CI_BUSI_TYPE database table.
 * 
 */
@Entity
@Table(name="M_CI_BUSI_TYPE")
public class MCiBusiType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COL")
	private long idCol;

	@Column(name="F_CODE")
	private String fCode;

	@Column(name="F_VALUE")
	private String fValue;

	@Column(name="PARENT_CODE")
	private String parentCode;

    public MCiBusiType() {
    }

	public long getIdCol() {
		return this.idCol;
	}

	public void setIdCol(long idCol) {
		this.idCol = idCol;
	}

	public String getFCode() {
		return this.fCode;
	}

	public void setFCode(String fCode) {
		this.fCode = fCode;
	}

	public String getFValue() {
		return this.fValue;
	}

	public void setFValue(String fValue) {
		this.fValue = fValue;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}