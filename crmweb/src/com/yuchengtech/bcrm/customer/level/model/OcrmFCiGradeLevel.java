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
 * The persistent class for the OCRM_F_CI_GRADE_LEVEL database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GRADE_LEVEL")
public class OcrmFCiGradeLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_GRADE_LEVEL_LEVELID_GENERATOR" , sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_GRADE_LEVEL_LEVELID_GENERATOR")
	@Column(name="LEVEL_ID")
	private long levelId;

	@Column(name="LEVEL_LOWER")
	private BigDecimal levelLower;

	@Column(name="LEVEL_NAME")
	private String levelName;
	
	@Column(name="CARD_LEVEL")
	private String cardLevel;
	

	public String getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}

	@Column(name="LEVEL_UPPER")
	private BigDecimal levelUpper;

	@Column(name="SCHEME_ID")
	private long schemeId;

    public OcrmFCiGradeLevel() {
    }

	public long getLevelId() {
		return this.levelId;
	}

	public void setLevelId(long levelId) {
		this.levelId = levelId;
	}

	public BigDecimal getLevelLower() {
		return this.levelLower;
	}

	public void setLevelLower(BigDecimal levelLower) {
		this.levelLower = levelLower;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public BigDecimal getLevelUpper() {
		return this.levelUpper;
	}

	public void setLevelUpper(BigDecimal levelUpper) {
		this.levelUpper = levelUpper;
	}

	public long getSchemeId() {
		return this.schemeId;
	}

	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}

}