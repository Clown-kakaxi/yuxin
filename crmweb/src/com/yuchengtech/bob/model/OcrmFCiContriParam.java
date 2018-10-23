package com.yuchengtech.bob.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_CI_CONTRI_PARAM database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CONTRI_PARAM")
public class OcrmFCiContriParam implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "PARAM_ID", nullable = false)
	private Long paramId;

	@Column(name="PARAM_DESC")
	private String paramDesc;

	@Column(name="PARAM_KIND")
	private Long paramKind;

	@Column(name="PARAM_NAME")
	private String paramName;

    public OcrmFCiContriParam() {
    }

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public Long getParamKind() {
		return paramKind;
	}

	public void setParamKind(Long paramKind) {
		this.paramKind = paramKind;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	

}