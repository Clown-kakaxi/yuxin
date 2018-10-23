package com.yuchengtech.bcrm.finService.model;

import java.io.Serializable;
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
 * The persistent class for the OCRM_F_FIN_CUST_DEMAND database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FIN_CUST_DEMAND")
public class OcrmFFinCustDemand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_FIN_CUST_DEMAND_DEMANDID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_FIN_CUST_DEMAND_DEMANDID_GENERATOR")
	@Column(name="DEMAND_ID")
	private Long demandId;

	private String available;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DT")
	private Date createDt;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="EXTRA_PERFORMACE")
	private String extraPerformace;

	private String investment;

	private String liquidity;

	private String pension;

	@Column(name="PLAN_RISK_LEV")
	private String planRiskLev;

	private String protection;

	private String reserve;

    public OcrmFFinCustDemand() {
    }

	public Long getDemandId() {
		return this.demandId;
	}

	public void setDemandId(Long demandId) {
		this.demandId = demandId;
	}

	public String getAvailable() {
		return this.available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getExtraPerformace() {
		return this.extraPerformace;
	}

	public void setExtraPerformace(String extraPerformace) {
		this.extraPerformace = extraPerformace;
	}

	public String getInvestment() {
		return this.investment;
	}

	public void setInvestment(String investment) {
		this.investment = investment;
	}

	public String getLiquidity() {
		return this.liquidity;
	}

	public void setLiquidity(String liquidity) {
		this.liquidity = liquidity;
	}

	public String getPension() {
		return this.pension;
	}

	public void setPension(String pension) {
		this.pension = pension;
	}

	public String getPlanRiskLev() {
		return this.planRiskLev;
	}

	public void setPlanRiskLev(String planRiskLev) {
		this.planRiskLev = planRiskLev;
	}

	public String getProtection() {
		return this.protection;
	}

	public void setProtection(String protection) {
		this.protection = protection;
	}

	public String getReserve() {
		return this.reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

}