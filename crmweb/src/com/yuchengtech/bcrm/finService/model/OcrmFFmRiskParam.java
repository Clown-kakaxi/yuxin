package com.yuchengtech.bcrm.finService.model;

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
 * The persistent class for the OCRM_F_FM_RISK_PARAM database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FM_RISK_PARAM")
public class OcrmFFmRiskParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_FM_RISK_PARAM_PARAMID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_FM_RISK_PARAM_PARAMID_GENERATOR")
	@Column(name="PARAM_ID")
	private Long paramId;

	@Column(name="END_SCORE")
	private BigDecimal endScore;

	@Column(name="INIT_SCORE")
	private BigDecimal initScore;

	@Column(name="PARAM_NAME")
	private String paramName;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATER_ID")
	private String updaterId;

    public OcrmFFmRiskParam() {
    }

	public long getParamId() {
		return this.paramId;
	}

	public void setParamId(long paramId) {
		this.paramId = paramId;
	}

	public BigDecimal getEndScore() {
		return this.endScore;
	}

	public void setEndScore(BigDecimal endScore) {
		this.endScore = endScore;
	}

	public BigDecimal getInitScore() {
		return this.initScore;
	}

	public void setInitScore(BigDecimal initScore) {
		this.initScore = initScore;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdaterId() {
		return this.updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

}