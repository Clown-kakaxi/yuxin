package com.yuchengtech.bcrm.customer.level.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CI_CONTRI_PARAM database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CONTRI_PARAM")
public class OcrmFCiContriPara implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CONTRI_PARAM_PARAMID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CONTRI_PARAM_PARAMID_GENERATOR")
	@Column(name="PARAM_ID")
	private Long  paramId;

	@Column(name="PARAM_CODE")
	private String paramCode;

    @Temporal( TemporalType.DATE)
	@Column(name="PARAM_DATE")
	private Date paramDate;

	@Column(name="PARAM_VALUE")
	private BigDecimal paramValue;

	@Column(name="USER_ID")
	private String userId;

    public OcrmFCiContriPara() {
    }

	public Long getParamId() {
		return this.paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public String getParamCode() {
		return this.paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public Date getParamDate() {
		return this.paramDate;
	}

	public void setParamDate(Date paramDate) {
		this.paramDate = paramDate;
	}

	public BigDecimal getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(BigDecimal paramValue) {
		this.paramValue = paramValue;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}