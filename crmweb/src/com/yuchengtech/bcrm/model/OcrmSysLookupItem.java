package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the OCRM_SYS_LOOKUP_ITEM database table.
 * 
 */
@Entity
@Table(name="OCRM_SYS_LOOKUP_ITEM")
public class OcrmSysLookupItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="F_ID")
	private long fId;

	@Column(name="F_CODE")
	private String fCode;

	@Column(name="F_COMMENT")
	private String fComment;

	@Column(name="F_LOOKUP_ID")
	private String fLookupId;

	@Column(name="F_VALUE")
	private String fValue;

    public OcrmSysLookupItem() {
    }

	public long getFId() {
		return this.fId;
	}

	public void setFId(long fId) {
		this.fId = fId;
	}

	public String getFCode() {
		return this.fCode;
	}

	public void setFCode(String fCode) {
		this.fCode = fCode;
	}

	public String getFComment() {
		return this.fComment;
	}

	public void setFComment(String fComment) {
		this.fComment = fComment;
	}

	public String getFLookupId() {
		return this.fLookupId;
	}

	public void setFLookupId(String fLookupId) {
		this.fLookupId = fLookupId;
	}

	public String getFValue() {
		return this.fValue;
	}

	public void setFValue(String fValue) {
		this.fValue = fValue;
	}

}