package com.yuchengtech.bcrm.customer.model;

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
 * The persistent class for the ACRM_F_CI_FINA_BUSI database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_FINA_BUSI")
public class AcrmFCiFinaBusi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_FINA_BUSI_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_FINA_BUSI_ID_GENERATOR")
	private long id;

    @Temporal( TemporalType.DATE)
	@Column(name="ABROAD_PLAN")
	private Date abroadPlan;

	@Column(name="ACC_RUN_STRATEGY")
	private String accRunStrategy;

	@Column(name="ASSET_AMT")
	private BigDecimal assetAmt;

	@Column(name="ASSET_INFO_OUT")
	private String assetInfoOut;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="FINA_INFO_IN")
	private String finaInfoIn;

	@Column(name="HOUSE_INFO")
	private String houseInfo;

    @Temporal( TemporalType.DATE)
	@Column(name="HOUSE_PLAN")
	private Date housePlan;

	@Column(name="IS_EDU_PLAN")
	private String isEduPlan;

	@Column(name="IS_HOUSE_PLAN")
	private String isHousePlan;

	@Column(name="IS_MORT")
	private String isMort;

	@Column(name="IS_RETIRE_PLAN")
	private String isRetirePlan;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="PROD_ADVICE")
	private String prodAdvice;

	@Column(name="RETIRE_PLAN")
	private String retirePlan;

	@Column(name="SERVICE_ATTENTION")
	private String serviceAttention;
	
	@Column(name="CUST_EN_ADDR")
	private String custEnAddr;
	
	

    public String getCustEnAddr() {
		return custEnAddr;
	}

	public void setCustEnAddr(String custEnAddr) {
		this.custEnAddr = custEnAddr;
	}

	public AcrmFCiFinaBusi() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getAbroadPlan() {
		return this.abroadPlan;
	}

	public void setAbroadPlan(Date abroadPlan) {
		this.abroadPlan = abroadPlan;
	}

	public String getAccRunStrategy() {
		return this.accRunStrategy;
	}

	public void setAccRunStrategy(String accRunStrategy) {
		this.accRunStrategy = accRunStrategy;
	}

	public BigDecimal getAssetAmt() {
		return this.assetAmt;
	}

	public void setAssetAmt(BigDecimal assetAmt) {
		this.assetAmt = assetAmt;
	}

	public String getAssetInfoOut() {
		return this.assetInfoOut;
	}

	public void setAssetInfoOut(String assetInfoOut) {
		this.assetInfoOut = assetInfoOut;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getFinaInfoIn() {
		return this.finaInfoIn;
	}

	public void setFinaInfoIn(String finaInfoIn) {
		this.finaInfoIn = finaInfoIn;
	}

	public String getHouseInfo() {
		return this.houseInfo;
	}

	public void setHouseInfo(String houseInfo) {
		this.houseInfo = houseInfo;
	}

	public Date getHousePlan() {
		return this.housePlan;
	}

	public void setHousePlan(Date housePlan) {
		this.housePlan = housePlan;
	}

	public String getIsEduPlan() {
		return this.isEduPlan;
	}

	public void setIsEduPlan(String isEduPlan) {
		this.isEduPlan = isEduPlan;
	}

	public String getIsHousePlan() {
		return this.isHousePlan;
	}

	public void setIsHousePlan(String isHousePlan) {
		this.isHousePlan = isHousePlan;
	}

	public String getIsMort() {
		return this.isMort;
	}

	public void setIsMort(String isMort) {
		this.isMort = isMort;
	}

	public String getIsRetirePlan() {
		return this.isRetirePlan;
	}

	public void setIsRetirePlan(String isRetirePlan) {
		this.isRetirePlan = isRetirePlan;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getProdAdvice() {
		return this.prodAdvice;
	}

	public void setProdAdvice(String prodAdvice) {
		this.prodAdvice = prodAdvice;
	}

	public String getRetirePlan() {
		return this.retirePlan;
	}

	public void setRetirePlan(String retirePlan) {
		this.retirePlan = retirePlan;
	}

	public String getServiceAttention() {
		return this.serviceAttention;
	}

	public void setServiceAttention(String serviceAttention) {
		this.serviceAttention = serviceAttention;
	}

}