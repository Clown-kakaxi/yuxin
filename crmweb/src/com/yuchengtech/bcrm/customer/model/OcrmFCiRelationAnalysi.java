package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;

import javax.persistence.*;




/**
 * The persistent class for the OCRM_F_CI_RELATION_ANALYSIS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_RELATION_ANALYSIS")
public class OcrmFCiRelationAnalysi implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@SequenceGenerator(name="OCRM_F_CI_RELATION_ANALYSIS_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_RELATION_ANALYSIS_ID_GENERATOR")
	private String id;
	
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="DEPOSIT_OTHER_AVERAGE")
	private String depositOtherAverage;

	@Column(name="DEPOSIT_OTHER_MARGIN")
	private String depositOtherMargin;

	@Column(name="DEPOSIT_OTHER_PROPORTION")
	private String depositOtherProportion;

	@Column(name="DEPOSIT_RMB_AVERAGE")
	private String depositRmbAverage;

	@Column(name="DEPOSIT_RMB_MARGIN")
	private String depositRmbMargin;

	@Column(name="DEPOSIT_RMB_PROPORTION")
	private String depositRmbProportion;

	@Column(name="DEPOSIT_TRADE_AVERAGE")
	private String depositTradeAverage;

	@Column(name="DEPOSIT_TRADE_MARGIN")
	private String depositTradeMargin;

	@Column(name="DEPOSIT_TRADE_PROPORTION")
	private String depositTradeProportion;

	@Column(name="EXCHANGE_FORWARD_AVERAGE")
	private String exchangeForwardAverage;

	@Column(name="EXCHANGE_FORWARD_MARGIN")
	private String exchangeForwardMargin;

	@Column(name="EXCHANGE_FORWARD_PROPORTION")
	private String exchangeForwardProportion;

	@Column(name="EXCHANGE_IMMEDIATE_AVERAGE")
	private String exchangeImmediateAverage;

	@Column(name="EXCHANGE_IMMEDIATE_MARGIN")
	private String exchangeImmediateMargin;

	@Column(name="EXCHANGE_IMMEDIATE_PROPORTION")
	private String exchangeImmediateProportion;

	@Column(name="EXCHANGE_INTEREST_AVERAGE")
	private String exchangeInterestAverage;

	@Column(name="EXCHANGE_INTEREST_MARGIN")
	private String exchangeInterestMargin;

	@Column(name="EXCHANGE_INTEREST_PROPORTION")
	private String exchangeInterestProportion;

	

	@Column(name="LOAN_AVERAGE")
	private String loanAverage;

	@Column(name="LOAN_MARGIN")
	private String loanMargin;

	@Column(name="LOAN_PROPORTION")
	private String loanProportion;

	@Column(name="OPTIONS_TRADING_AVERAGE")
	private String optionsTradingAverage;

	@Column(name="OPTIONS_TRADING_MARGIN")
	private String optionsTradingMargin;

	@Column(name="OPTIONS_TRADING_PROPORTION")
	private String optionsTradingProportion;

	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="PROVIDE_PRODUCTS")
	private String provideProducts;
	
	@Column(name="SUIT_PRODUCTS")
	private String suitProducts;

	@Column(name="TRADE_ACCEPTANCE_AVERAGE")
	private String tradeAcceptanceAverage;

	@Column(name="TRADE_ACCEPTANCE_MARGIN")
	private String tradeAcceptanceMargin;

	@Column(name="TRADE_ACCEPTANCE_PROPORTION")
	private String tradeAcceptanceProportion;

	@Column(name="TRADE_CREDIT_AVERAGE")
	private String tradeCreditAverage;

	@Column(name="TRADE_CREDIT_MARGIN")
	private String tradeCreditMargin;

	@Column(name="TRADE_CREDIT_PROPORTION")
	private String tradeCreditProportion;

	@Column(name="TRADE_DISCOUNT_AVERAGE")
	private String tradeDiscountAverage;

	@Column(name="TRADE_DISCOUNT_MARGIN")
	private String tradeDiscountMargin;

	@Column(name="TRADE_DISCOUNT_PROPORTION")
	private String tradeDiscountProportion;

	@Column(name="TRADE_FACTORING_AVERAGE")
	private String tradeFactoringAverage;

	@Column(name="TRADE_FACTORING_MARGIN")
	private String tradeFactoringMargin;

	@Column(name="TRADE_FACTORING_PROPORTION")
	private String tradeFactoringProportion;

	@Column(name="TRADE_FINANCING_AVERAGE")
	private String tradeFinancingAverage;

	@Column(name="TRADE_FINANCING_MARGIN")
	private String tradeFinancingMargin;

	@Column(name="TRADE_FINANCING_PROPORTION")
	private String tradeFinancingProportion;

	@Column(name="TRADE_GUARANTEE_AVERAGE")
	private String tradeGuaranteeAverage;

	@Column(name="TRADE_GUARANTEE_MARGIN")
	private String tradeGuaranteeMargin;

	@Column(name="TRADE_GUARANTEE_PROPORTION")
	private String tradeGuaranteeProportion;

	@Column(name="WALLETSIZE_PRODUCTS")
	private String walletsizeProducts;

	public OcrmFCiRelationAnalysi() {
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getDepositOtherAverage() {
		return this.depositOtherAverage;
	}

	public void setDepositOtherAverage(String depositOtherAverage) {
		this.depositOtherAverage = depositOtherAverage;
	}

	public String getDepositOtherMargin() {
		return this.depositOtherMargin;
	}

	public void setDepositOtherMargin(String depositOtherMargin) {
		this.depositOtherMargin = depositOtherMargin;
	}

	public String getDepositOtherProportion() {
		return this.depositOtherProportion;
	}

	public void setDepositOtherProportion(String depositOtherProportion) {
		this.depositOtherProportion = depositOtherProportion;
	}

	public String getDepositRmbAverage() {
		return this.depositRmbAverage;
	}

	public void setDepositRmbAverage(String depositRmbAverage) {
		this.depositRmbAverage = depositRmbAverage;
	}

	public String getDepositRmbMargin() {
		return this.depositRmbMargin;
	}

	public void setDepositRmbMargin(String depositRmbMargin) {
		this.depositRmbMargin = depositRmbMargin;
	}

	public String getDepositRmbProportion() {
		return this.depositRmbProportion;
	}

	public void setDepositRmbProportion(String depositRmbProportion) {
		this.depositRmbProportion = depositRmbProportion;
	}

	public String getDepositTradeAverage() {
		return this.depositTradeAverage;
	}

	public void setDepositTradeAverage(String depositTradeAverage) {
		this.depositTradeAverage = depositTradeAverage;
	}

	public String getDepositTradeMargin() {
		return this.depositTradeMargin;
	}

	public void setDepositTradeMargin(String depositTradeMargin) {
		this.depositTradeMargin = depositTradeMargin;
	}

	public String getDepositTradeProportion() {
		return this.depositTradeProportion;
	}

	public void setDepositTradeProportion(String depositTradeProportion) {
		this.depositTradeProportion = depositTradeProportion;
	}

	public String getExchangeForwardAverage() {
		return this.exchangeForwardAverage;
	}

	public void setExchangeForwardAverage(String exchangeForwardAverage) {
		this.exchangeForwardAverage = exchangeForwardAverage;
	}

	public String getExchangeForwardMargin() {
		return this.exchangeForwardMargin;
	}

	public void setExchangeForwardMargin(String exchangeForwardMargin) {
		this.exchangeForwardMargin = exchangeForwardMargin;
	}

	public String getExchangeForwardProportion() {
		return this.exchangeForwardProportion;
	}

	public void setExchangeForwardProportion(String exchangeForwardProportion) {
		this.exchangeForwardProportion = exchangeForwardProportion;
	}

	public String getExchangeImmediateAverage() {
		return this.exchangeImmediateAverage;
	}

	public void setExchangeImmediateAverage(String exchangeImmediateAverage) {
		this.exchangeImmediateAverage = exchangeImmediateAverage;
	}

	public String getExchangeImmediateMargin() {
		return this.exchangeImmediateMargin;
	}

	public void setExchangeImmediateMargin(String exchangeImmediateMargin) {
		this.exchangeImmediateMargin = exchangeImmediateMargin;
	}

	public String getExchangeImmediateProportion() {
		return this.exchangeImmediateProportion;
	}

	public void setExchangeImmediateProportion(String exchangeImmediateProportion) {
		this.exchangeImmediateProportion = exchangeImmediateProportion;
	}

	public String getExchangeInterestAverage() {
		return this.exchangeInterestAverage;
	}

	public void setExchangeInterestAverage(String exchangeInterestAverage) {
		this.exchangeInterestAverage = exchangeInterestAverage;
	}

	public String getExchangeInterestMargin() {
		return this.exchangeInterestMargin;
	}

	public void setExchangeInterestMargin(String exchangeInterestMargin) {
		this.exchangeInterestMargin = exchangeInterestMargin;
	}

	public String getExchangeInterestProportion() {
		return this.exchangeInterestProportion;
	}

	public void setExchangeInterestProportion(String exchangeInterestProportion) {
		this.exchangeInterestProportion = exchangeInterestProportion;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanAverage() {
		return this.loanAverage;
	}

	public void setLoanAverage(String loanAverage) {
		this.loanAverage = loanAverage;
	}

	public String getLoanMargin() {
		return this.loanMargin;
	}

	public void setLoanMargin(String loanMargin) {
		this.loanMargin = loanMargin;
	}

	public String getLoanProportion() {
		return this.loanProportion;
	}

	public void setLoanProportion(String loanProportion) {
		this.loanProportion = loanProportion;
	}

	public String getOptionsTradingAverage() {
		return this.optionsTradingAverage;
	}

	public void setOptionsTradingAverage(String optionsTradingAverage) {
		this.optionsTradingAverage = optionsTradingAverage;
	}

	public String getOptionsTradingMargin() {
		return this.optionsTradingMargin;
	}

	public void setOptionsTradingMargin(String optionsTradingMargin) {
		this.optionsTradingMargin = optionsTradingMargin;
	}

	public String getOptionsTradingProportion() {
		return this.optionsTradingProportion;
	}

	public void setOptionsTradingProportion(String optionsTradingProportion) {
		this.optionsTradingProportion = optionsTradingProportion;
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getProvideProducts() {
		return this.provideProducts;
	}

	public void setProvideProducts(String provideProducts) {
		this.provideProducts = provideProducts;
	}
	
	public String getSuitProducts() {
		return this.suitProducts;
	}

	public void setSuitProducts(String suitProducts) {
		this.suitProducts = suitProducts;
	}

	public String getTradeAcceptanceAverage() {
		return this.tradeAcceptanceAverage;
	}

	public void setTradeAcceptanceAverage(String tradeAcceptanceAverage) {
		this.tradeAcceptanceAverage = tradeAcceptanceAverage;
	}

	public String getTradeAcceptanceMargin() {
		return this.tradeAcceptanceMargin;
	}

	public void setTradeAcceptanceMargin(String tradeAcceptanceMargin) {
		this.tradeAcceptanceMargin = tradeAcceptanceMargin;
	}

	public String getTradeAcceptanceProportion() {
		return this.tradeAcceptanceProportion;
	}

	public void setTradeAcceptanceProportion(String tradeAcceptanceProportion) {
		this.tradeAcceptanceProportion = tradeAcceptanceProportion;
	}

	public String getTradeCreditAverage() {
		return this.tradeCreditAverage;
	}

	public void setTradeCreditAverage(String tradeCreditAverage) {
		this.tradeCreditAverage = tradeCreditAverage;
	}

	public String getTradeCreditMargin() {
		return this.tradeCreditMargin;
	}

	public void setTradeCreditMargin(String tradeCreditMargin) {
		this.tradeCreditMargin = tradeCreditMargin;
	}

	public String getTradeCreditProportion() {
		return this.tradeCreditProportion;
	}

	public void setTradeCreditProportion(String tradeCreditProportion) {
		this.tradeCreditProportion = tradeCreditProportion;
	}

	public String getTradeDiscountAverage() {
		return this.tradeDiscountAverage;
	}

	public void setTradeDiscountAverage(String tradeDiscountAverage) {
		this.tradeDiscountAverage = tradeDiscountAverage;
	}

	public String getTradeDiscountMargin() {
		return this.tradeDiscountMargin;
	}

	public void setTradeDiscountMargin(String tradeDiscountMargin) {
		this.tradeDiscountMargin = tradeDiscountMargin;
	}

	public String getTradeDiscountProportion() {
		return this.tradeDiscountProportion;
	}

	public void setTradeDiscountProportion(String tradeDiscountProportion) {
		this.tradeDiscountProportion = tradeDiscountProportion;
	}

	public String getTradeFactoringAverage() {
		return this.tradeFactoringAverage;
	}

	public void setTradeFactoringAverage(String tradeFactoringAverage) {
		this.tradeFactoringAverage = tradeFactoringAverage;
	}

	public String getTradeFactoringMargin() {
		return this.tradeFactoringMargin;
	}

	public void setTradeFactoringMargin(String tradeFactoringMargin) {
		this.tradeFactoringMargin = tradeFactoringMargin;
	}

	public String getTradeFactoringProportion() {
		return this.tradeFactoringProportion;
	}

	public void setTradeFactoringProportion(String tradeFactoringProportion) {
		this.tradeFactoringProportion = tradeFactoringProportion;
	}

	public String getTradeFinancingAverage() {
		return this.tradeFinancingAverage;
	}

	public void setTradeFinancingAverage(String tradeFinancingAverage) {
		this.tradeFinancingAverage = tradeFinancingAverage;
	}

	public String getTradeFinancingMargin() {
		return this.tradeFinancingMargin;
	}

	public void setTradeFinancingMargin(String tradeFinancingMargin) {
		this.tradeFinancingMargin = tradeFinancingMargin;
	}

	public String getTradeFinancingProportion() {
		return this.tradeFinancingProportion;
	}

	public void setTradeFinancingProportion(String tradeFinancingProportion) {
		this.tradeFinancingProportion = tradeFinancingProportion;
	}

	public String getTradeGuaranteeAverage() {
		return this.tradeGuaranteeAverage;
	}

	public void setTradeGuaranteeAverage(String tradeGuaranteeAverage) {
		this.tradeGuaranteeAverage = tradeGuaranteeAverage;
	}

	public String getTradeGuaranteeMargin() {
		return this.tradeGuaranteeMargin;
	}

	public void setTradeGuaranteeMargin(String tradeGuaranteeMargin) {
		this.tradeGuaranteeMargin = tradeGuaranteeMargin;
	}

	public String getTradeGuaranteeProportion() {
		return this.tradeGuaranteeProportion;
	}

	public void setTradeGuaranteeProportion(String tradeGuaranteeProportion) {
		this.tradeGuaranteeProportion = tradeGuaranteeProportion;
	}

	public String getWalletsizeProducts() {
		return this.walletsizeProducts;
	}

	public void setWalletsizeProducts(String walletsizeProducts) {
		this.walletsizeProducts = walletsizeProducts;
	}

}