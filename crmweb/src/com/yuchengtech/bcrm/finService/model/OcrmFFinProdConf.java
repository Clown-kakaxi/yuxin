package com.yuchengtech.bcrm.finService.model;

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
 * The persistent class for the OCRM_F_FIN_PROD_CONF database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FIN_PROD_CONF")
public class OcrmFFinProdConf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_FIN_PROD_CONF_CONFID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_FIN_PROD_CONF_CONFID_GENERATOR")
	@Column(name="CONF_ID")
	private Long confId;

	@Column(name="CONF_SCALE")
	private BigDecimal confScale;

	@Column(name="DEMAND_ID")
	private BigDecimal demandId;

	@Column(name="PROD_ID")
	private String prodId;

	@Column(name="PROD_RISK")
	private String prodRisk;

	@Column(name="TARGET_ID")
	private BigDecimal targetId;

    public OcrmFFinProdConf() {
    }

	public Long getConfId() {
		return this.confId;
	}

	public void setConfId(Long confId) {
		this.confId = confId;
	}

	public BigDecimal getConfScale() {
		return this.confScale;
	}

	public void setConfScale(BigDecimal confScale) {
		this.confScale = confScale;
	}

	public BigDecimal getDemandId() {
		return this.demandId;
	}

	public void setDemandId(BigDecimal demandId) {
		this.demandId = demandId;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdRisk() {
		return this.prodRisk;
	}

	public void setProdRisk(String prodRisk) {
		this.prodRisk = prodRisk;
	}

	public BigDecimal getTargetId() {
		return this.targetId;
	}

	public void setTargetId(BigDecimal targetId) {
		this.targetId = targetId;
	}

}