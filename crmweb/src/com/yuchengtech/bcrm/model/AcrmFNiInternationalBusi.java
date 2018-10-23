package com.yuchengtech.bcrm.model;

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
 * The persistent class for the ACRM_F_NI_INTERNATIONAL_BUSI database table.
 * 
 */
@Entity
@Table(name="ACRM_F_NI_INTERNATIONAL_BUSI")
public class AcrmFNiInternationalBusi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_NI_INTERNATIONAL_BUSI_AGREEMENTID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_NI_INTERNATIONAL_BUSI_AGREEMENTID_GENERATOR")
	@Column(name="AGREEMENT_ID",unique=true, nullable=false)
	private Long agreementId;

	@Column(name="BUSI_NAME")
	private String busiName;

	@Column(name="BUSI_TYPE")
	private String busiType;

	@Column(name="CURR_RATE")
	private BigDecimal currRate;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUSTOM_ID")
	private String customId;

	@Column(name="FEE_AMT")
	private BigDecimal feeAmt;

	private BigDecimal money;

	@Column(name="MONEY_CNCY")
	private String moneyCncy;

	@Column(name="ODS_ST_DATE")
	private String odsStDate;

    @Temporal( TemporalType.DATE)
	@Column(name="TRADE_DATE")
	private Date tradeDate;

	@Column(name="TRADE_INST")
	private String tradeInst;

	@Column(name="TRADE_INST_NAME")
	private String tradeInstName;

	@Column(name="TRANS_AMT_USD")
	private BigDecimal transAmtUsd;

	public Long getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Long agreementId) {
		this.agreementId = agreementId;
	}

	public String getBusiName() {
		return busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public BigDecimal getCurrRate() {
		return currRate;
	}

	public void setCurrRate(BigDecimal currRate) {
		this.currRate = currRate;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getMoneyCncy() {
		return moneyCncy;
	}

	public void setMoneyCncy(String moneyCncy) {
		this.moneyCncy = moneyCncy;
	}

	public String getOdsStDate() {
		return odsStDate;
	}

	public void setOdsStDate(String odsStDate) {
		this.odsStDate = odsStDate;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeInst() {
		return tradeInst;
	}

	public void setTradeInst(String tradeInst) {
		this.tradeInst = tradeInst;
	}

	public String getTradeInstName() {
		return tradeInstName;
	}

	public void setTradeInstName(String tradeInstName) {
		this.tradeInstName = tradeInstName;
	}

	public BigDecimal getTransAmtUsd() {
		return transAmtUsd;
	}

	public void setTransAmtUsd(BigDecimal transAmtUsd) {
		this.transAmtUsd = transAmtUsd;
	}
}