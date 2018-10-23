package com.yuchengtech.bcrm.customer.level.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_CI_ANTI_MONEY_INDEX_VAR database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_ANTI_MONEY_INDEX_VAR")
public class OcrmFCiAntiMoneyIndexVar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_ANTI_MONEY_INDEX_VAR_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_ANTI_MONEY_INDEX_VAR_ID_GENERATOR")
	private Long id;

	@Column(name="INDEX_CODE")
	private String indexCode;

	@Column(name="INDEX_ID")
	private String indexId;

	@Column(name="INDEX_NAME")
	private String indexName;

	@Column(name="INDEX_RIGHT")
	private BigDecimal indexRight;

	@Column(name="INDEX_SCORE")
	private BigDecimal indexScore;

	@Column(name="INDEX_VALUE")
	private String indexValue;

	@Column(name="HIGH_FLAG")
	private String highFlag;
	
    public OcrmFCiAntiMoneyIndexVar() {
    }

	public String getHighFlag() {
		return highFlag;
	}

	public void setHighFlag(String highFlag) {
		this.highFlag = highFlag;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIndexCode() {
		return this.indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getIndexId() {
		return this.indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public BigDecimal getIndexRight() {
		return this.indexRight;
	}

	public void setIndexRight(BigDecimal indexRight) {
		this.indexRight = indexRight;
	}

	public BigDecimal getIndexScore() {
		return this.indexScore;
	}

	public void setIndexScore(BigDecimal indexScore) {
		this.indexScore = indexScore;
	}

	public String getIndexValue() {
		return this.indexValue;
	}

	public void setIndexValue(String indexValue) {
		this.indexValue = indexValue;
	}

}