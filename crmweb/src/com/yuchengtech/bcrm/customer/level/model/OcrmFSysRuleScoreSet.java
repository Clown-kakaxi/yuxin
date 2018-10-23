package com.yuchengtech.bcrm.customer.level.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_SYS_RULE_SCORE_SET database table.
 * 
 */
@Entity
@Table(name="OCRM_F_SYS_RULE_SCORE_SET")
public class OcrmFSysRuleScoreSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name="COMPUTE_TYPE")
	private String computeType;

	@Column(name="CONVERT_RATE")
	private BigDecimal convertRate;

	@Column(name="CUST_TYPE")
	private String custType;

	private String frequence;

	@Column(name="INDEX_CODE")
	private String indexCode;

	@Column(name="INDEX_NAME")
	private String indexName;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="SCORE_NAME")
	private String scoreName;

    @Temporal( TemporalType.DATE)
	@Column(name="SET_DATE")
	private Date setDate;

	private String status;

	@Column(name="USE_WAY")
	private String useWay;

	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="FORMULA")
	private String formula;
	
	
	@Column(name="FORMULA_MEAN")
	private String formulaMean;

    public OcrmFSysRuleScoreSet() {
    }

    
	public String getFormula() {
		return formula;
	}


	public void setFormula(String formula) {
		this.formula = formula;
	}


	public String getFormulaMean() {
		return formulaMean;
	}


	public void setFormulaMean(String formulaMean) {
		this.formulaMean = formulaMean;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComputeType() {
		return this.computeType;
	}

	public void setComputeType(String computeType) {
		this.computeType = computeType;
	}

	public BigDecimal getConvertRate() {
		return this.convertRate;
	}

	public void setConvertRate(BigDecimal convertRate) {
		this.convertRate = convertRate;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getFrequence() {
		return this.frequence;
	}

	public void setFrequence(String frequence) {
		this.frequence = frequence;
	}

	public String getIndexCode() {
		return this.indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getScoreName() {
		return this.scoreName;
	}

	public void setScoreName(String scoreName) {
		this.scoreName = scoreName;
	}

	public Date getSetDate() {
		return this.setDate;
	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUseWay() {
		return this.useWay;
	}

	public void setUseWay(String useWay) {
		this.useWay = useWay;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}