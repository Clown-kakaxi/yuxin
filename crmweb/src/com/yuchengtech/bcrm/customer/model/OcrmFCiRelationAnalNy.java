package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;

import javax.persistence.*;




/**
 * The persistent class for the OCRM_F_CI_RELATION_ANAL_NY database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_RELATION_ANAL_NY")
public class OcrmFCiRelationAnalNy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_RELATION_ANAL_NY_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_RELATION_ANAL_NY_ID_GENERATOR")
	private String id;
	
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="DEPOSIT_OTHER_AVERAGE_NY")
	private String depositOtherAverageNy;

	@Column(name="DEPOSIT_OTHER_MARGIN_NY")
	private String depositOtherMarginNy;

	@Column(name="DEPOSIT_OTHER_PROPORT_NY")
	private String depositOtherProportNy;

	@Column(name="DEPOSIT_RMB_AVERAGE_NY")
	private String depositRmbAverageNy;

	@Column(name="DEPOSIT_RMB_MARGIN_NY")
	private String depositRmbMarginNy;

	@Column(name="DEPOSIT_RMB_PROPORT_NY")
	private String depositRmbProportNy;

	@Column(name="DEPOSIT_TRADE_AVERAGE_NY")
	private String depositTradeAverageNy;

	@Column(name="DEPOSIT_TRADE_MARGIN_NY")
	private String depositTradeMarginNy;

	@Column(name="DEPOSIT_TRADE_PROPORT_NY")
	private String depositTradeProportNy;

	@Column(name="EXCHANGE_FORWARD_AVERAGE_NY")
	private String exchangeForwardAverageNy;

	@Column(name="EXCHANGE_FORWARD_MARGIN_NY")
	private String exchangeForwardMarginNy;

	@Column(name="EXCHANGE_FORWARD_PROPORT_NY")
	private String exchangeForwardProportNy;

	@Column(name="EXCHANGE_IMMEDIATE_AVERAGE_NY")
	private String exchangeImmediateAverageNy;

	@Column(name="EXCHANGE_IMMEDIATE_MARGIN_NY")
	private String exchangeImmediateMarginNy;

	@Column(name="EXCHANGE_IMMEDIATE_PROPORT_NY")
	private String exchangeImmediateProportNy;

	@Column(name="EXCHANGE_INTEREST_AVERAGE_NY")
	private String exchangeInterestAverageNy;

	@Column(name="EXCHANGE_INTEREST_MARGIN_NY")
	private String exchangeInterestMarginNy;

	@Column(name="EXCHANGE_INTEREST_PROPORT_NY")
	private String exchangeInterestProportNy;


	@Column(name="LOAN_AVERAGE_NY")
	private String loanAverageNy;

	@Column(name="LOAN_MARGIN_NY")
	private String loanMarginNy;

	@Column(name="LOAN_PROPORT_NY")
	private String loanProportNy;

	@Column(name="OPTIONS_TRADING_AVERAGE_NY")
	private String optionsTradingAverageNy;

	@Column(name="OPTIONS_TRADING_MARGIN_NY")
	private String optionsTradingMarginNy;

	@Column(name="OPTIONS_TRADING_PROPORT_NY")
	private String optionsTradingProportNy;

	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="PROVIDE_PRODUCTS_NY")
	private String provideProductsNy;
	
	@Column(name="SUIT_PRODUCTS_NY")
	private String suitProductsNy;

	@Column(name="TRADE_ACCEPTANCE_AVERAGE_NY")
	private String tradeAcceptanceAverageNy;

	@Column(name="TRADE_ACCEPTANCE_MARGIN_NY")
	private String tradeAcceptanceMarginNy;

	@Column(name="TRADE_ACCEPTANCE_PROPORT_NY")
	private String tradeAcceptanceProportNy;

	@Column(name="TRADE_CREDIT_AVERAGE_NY")
	private String tradeCreditAverageNy;

	@Column(name="TRADE_CREDIT_MARGIN_NY")
	private String tradeCreditMarginNy;

	@Column(name="TRADE_CREDIT_PROPORT_NY")
	private String tradeCreditProportNy;

	@Column(name="TRADE_DISCOUNT_AVERAGE_NY")
	private String tradeDiscountAverageNy;

	@Column(name="TRADE_DISCOUNT_MARGIN_NY")
	private String tradeDiscountMarginNy;

	@Column(name="TRADE_DISCOUNT_PROPORT_NY")
	private String tradeDiscountProportNy;

	@Column(name="TRADE_FACTORING_AVERAGE_NY")
	private String tradeFactoringAverageNy;

	@Column(name="TRADE_FACTORING_MARGIN_NY")
	private String tradeFactoringMarginNy;

	@Column(name="TRADE_FACTORING_PROPORT_NY")
	private String tradeFactoringProportNy;

	@Column(name="TRADE_FINANCING_AVERAGE_NY")
	private String tradeFinancingAverageNy;

	@Column(name="TRADE_FINANCING_MARGIN_NY")
	private String tradeFinancingMarginNy;

	@Column(name="TRADE_FINANCING_PROPORT_NY")
	private String tradeFinancingProportNy;

	@Column(name="TRADE_GUARANTEE_AVERAGE_NY")
	private String tradeGuaranteeAverageNy;

	@Column(name="TRADE_GUARANTEE_MARGIN_NY")
	private String tradeGuaranteeMarginNy;

	@Column(name="TRADE_GUARANTEE_PROPORT_NY")
	private String tradeGuaranteeProportNy;

	@Column(name="WALLETSIZE_PRODUCTS_NY")
	private String walletsizeProductsNy;

	public OcrmFCiRelationAnalNy() {
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getDepositOtherAverageNy() {
		return this.depositOtherAverageNy;
	}

	public void setDepositOtherAverageNy(String depositOtherAverageNy) {
		this.depositOtherAverageNy = depositOtherAverageNy;
	}

	public String getDepositOtherMarginNy() {
		return this.depositOtherMarginNy;
	}

	public void setDepositOtherMarginNy(String depositOtherMarginNy) {
		this.depositOtherMarginNy = depositOtherMarginNy;
	}

	public String getDepositOtherProportNy() {
		return this.depositOtherProportNy;
	}

	public void setDepositOtherProportNy(String depositOtherProportNy) {
		this.depositOtherProportNy = depositOtherProportNy;
	}

	public String getDepositRmbAverageNy() {
		return this.depositRmbAverageNy;
	}

	public void setDepositRmbAverageNy(String depositRmbAverageNy) {
		this.depositRmbAverageNy = depositRmbAverageNy;
	}

	public String getDepositRmbMarginNy() {
		return this.depositRmbMarginNy;
	}

	public void setDepositRmbMarginNy(String depositRmbMarginNy) {
		this.depositRmbMarginNy = depositRmbMarginNy;
	}

	public String getDepositRmbProportNy() {
		return this.depositRmbProportNy;
	}

	public void setDepositRmbProportNy(String depositRmbProportNy) {
		this.depositRmbProportNy = depositRmbProportNy;
	}

	public String getDepositTradeAverageNy() {
		return this.depositTradeAverageNy;
	}

	public void setDepositTradeAverageNy(String depositTradeAverageNy) {
		this.depositTradeAverageNy = depositTradeAverageNy;
	}

	public String getDepositTradeMarginNy() {
		return this.depositTradeMarginNy;
	}

	public void setDepositTradeMarginNy(String depositTradeMarginNy) {
		this.depositTradeMarginNy = depositTradeMarginNy;
	}

	public String getDepositTradeProportNy() {
		return this.depositTradeProportNy;
	}

	public void setDepositTradeProportNy(String depositTradeProportNy) {
		this.depositTradeProportNy = depositTradeProportNy;
	}

	public String getExchangeForwardAverageNy() {
		return this.exchangeForwardAverageNy;
	}

	public void setExchangeForwardAverageNy(String exchangeForwardAverageNy) {
		this.exchangeForwardAverageNy = exchangeForwardAverageNy;
	}

	public String getExchangeForwardMarginNy() {
		return this.exchangeForwardMarginNy;
	}

	public void setExchangeForwardMarginNy(String exchangeForwardMarginNy) {
		this.exchangeForwardMarginNy = exchangeForwardMarginNy;
	}

	public String getExchangeForwardProportNy() {
		return this.exchangeForwardProportNy;
	}

	public void setExchangeForwardProportNy(String exchangeForwardProportNy) {
		this.exchangeForwardProportNy = exchangeForwardProportNy;
	}

	public String getExchangeImmediateAverageNy() {
		return this.exchangeImmediateAverageNy;
	}

	public void setExchangeImmediateAverageNy(String exchangeImmediateAverageNy) {
		this.exchangeImmediateAverageNy = exchangeImmediateAverageNy;
	}

	public String getExchangeImmediateMarginNy() {
		return this.exchangeImmediateMarginNy;
	}

	public void setExchangeImmediateMarginNy(String exchangeImmediateMarginNy) {
		this.exchangeImmediateMarginNy = exchangeImmediateMarginNy;
	}

	public String getExchangeImmediateProportNy() {
		return this.exchangeImmediateProportNy;
	}

	public void setExchangeImmediateProportNy(String exchangeImmediateProportNy) {
		this.exchangeImmediateProportNy = exchangeImmediateProportNy;
	}

	public String getExchangeInterestAverageNy() {
		return this.exchangeInterestAverageNy;
	}

	public void setExchangeInterestAverageNy(String exchangeInterestAverageNy) {
		this.exchangeInterestAverageNy = exchangeInterestAverageNy;
	}

	public String getExchangeInterestMarginNy() {
		return this.exchangeInterestMarginNy;
	}

	public void setExchangeInterestMarginNy(String exchangeInterestMarginNy) {
		this.exchangeInterestMarginNy = exchangeInterestMarginNy;
	}

	public String getExchangeInterestProportNy() {
		return this.exchangeInterestProportNy;
	}

	public void setExchangeInterestProportNy(String exchangeInterestProportNy) {
		this.exchangeInterestProportNy = exchangeInterestProportNy;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanAverageNy() {
		return this.loanAverageNy;
	}

	public void setLoanAverageNy(String loanAverageNy) {
		this.loanAverageNy = loanAverageNy;
	}

	public String getLoanMarginNy() {
		return this.loanMarginNy;
	}

	public void setLoanMarginNy(String loanMarginNy) {
		this.loanMarginNy = loanMarginNy;
	}

	public String getLoanProportNy() {
		return this.loanProportNy;
	}

	public void setLoanProportNy(String loanProportNy) {
		this.loanProportNy = loanProportNy;
	}

	public String getOptionsTradingAverageNy() {
		return this.optionsTradingAverageNy;
	}

	public void setOptionsTradingAverageNy(String optionsTradingAverageNy) {
		this.optionsTradingAverageNy = optionsTradingAverageNy;
	}

	public String getOptionsTradingMarginNy() {
		return this.optionsTradingMarginNy;
	}

	public void setOptionsTradingMarginNy(String optionsTradingMarginNy) {
		this.optionsTradingMarginNy = optionsTradingMarginNy;
	}

	public String getOptionsTradingProportNy() {
		return this.optionsTradingProportNy;
	}

	public void setOptionsTradingProportNy(String optionsTradingProportNy) {
		this.optionsTradingProportNy = optionsTradingProportNy;
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	public String getProvideProductsNy() {
		return this.provideProductsNy;
	}

	public void setProvideProductsNy(String provideProductsNy) {
		this.provideProductsNy = provideProductsNy;
	}

	public String getSuitProductsNy() {
		return this.suitProductsNy;
	}

	public void setSuitProductsNy(String suitProductsNy) {
		this.suitProductsNy = suitProductsNy;
	}

	public String getTradeAcceptanceAverageNy() {
		return this.tradeAcceptanceAverageNy;
	}

	public void setTradeAcceptanceAverageNy(String tradeAcceptanceAverageNy) {
		this.tradeAcceptanceAverageNy = tradeAcceptanceAverageNy;
	}

	public String getTradeAcceptanceMarginNy() {
		return this.tradeAcceptanceMarginNy;
	}

	public void setTradeAcceptanceMarginNy(String tradeAcceptanceMarginNy) {
		this.tradeAcceptanceMarginNy = tradeAcceptanceMarginNy;
	}

	public String getTradeAcceptanceProportNy() {
		return this.tradeAcceptanceProportNy;
	}

	public void setTradeAcceptanceProportNy(String tradeAcceptanceProportNy) {
		this.tradeAcceptanceProportNy = tradeAcceptanceProportNy;
	}

	public String getTradeCreditAverageNy() {
		return this.tradeCreditAverageNy;
	}

	public void setTradeCreditAverageNy(String tradeCreditAverageNy) {
		this.tradeCreditAverageNy = tradeCreditAverageNy;
	}

	public String getTradeCreditMarginNy() {
		return this.tradeCreditMarginNy;
	}

	public void setTradeCreditMarginNy(String tradeCreditMarginNy) {
		this.tradeCreditMarginNy = tradeCreditMarginNy;
	}

	public String getTradeCreditProportNy() {
		return this.tradeCreditProportNy;
	}

	public void setTradeCreditProportNy(String tradeCreditProportNy) {
		this.tradeCreditProportNy = tradeCreditProportNy;
	}

	public String getTradeDiscountAverageNy() {
		return this.tradeDiscountAverageNy;
	}

	public void setTradeDiscountAverageNy(String tradeDiscountAverageNy) {
		this.tradeDiscountAverageNy = tradeDiscountAverageNy;
	}

	public String getTradeDiscountMarginNy() {
		return this.tradeDiscountMarginNy;
	}

	public void setTradeDiscountMarginNy(String tradeDiscountMarginNy) {
		this.tradeDiscountMarginNy = tradeDiscountMarginNy;
	}

	public String getTradeDiscountProportNy() {
		return this.tradeDiscountProportNy;
	}

	public void setTradeDiscountProportNy(String tradeDiscountProportNy) {
		this.tradeDiscountProportNy = tradeDiscountProportNy;
	}

	public String getTradeFactoringAverageNy() {
		return this.tradeFactoringAverageNy;
	}

	public void setTradeFactoringAverageNy(String tradeFactoringAverageNy) {
		this.tradeFactoringAverageNy = tradeFactoringAverageNy;
	}

	public String getTradeFactoringMarginNy() {
		return this.tradeFactoringMarginNy;
	}

	public void setTradeFactoringMarginNy(String tradeFactoringMarginNy) {
		this.tradeFactoringMarginNy = tradeFactoringMarginNy;
	}

	public String getTradeFactoringProportNy() {
		return this.tradeFactoringProportNy;
	}

	public void setTradeFactoringProportNy(String tradeFactoringProportNy) {
		this.tradeFactoringProportNy = tradeFactoringProportNy;
	}

	public String getTradeFinancingAverageNy() {
		return this.tradeFinancingAverageNy;
	}

	public void setTradeFinancingAverageNy(String tradeFinancingAverageNy) {
		this.tradeFinancingAverageNy = tradeFinancingAverageNy;
	}

	public String getTradeFinancingMarginNy() {
		return this.tradeFinancingMarginNy;
	}

	public void setTradeFinancingMarginNy(String tradeFinancingMarginNy) {
		this.tradeFinancingMarginNy = tradeFinancingMarginNy;
	}

	public String getTradeFinancingProportNy() {
		return this.tradeFinancingProportNy;
	}

	public void setTradeFinancingProportNy(String tradeFinancingProportNy) {
		this.tradeFinancingProportNy = tradeFinancingProportNy;
	}

	public String getTradeGuaranteeAverageNy() {
		return this.tradeGuaranteeAverageNy;
	}

	public void setTradeGuaranteeAverageNy(String tradeGuaranteeAverageNy) {
		this.tradeGuaranteeAverageNy = tradeGuaranteeAverageNy;
	}

	public String getTradeGuaranteeMarginNy() {
		return this.tradeGuaranteeMarginNy;
	}

	public void setTradeGuaranteeMarginNy(String tradeGuaranteeMarginNy) {
		this.tradeGuaranteeMarginNy = tradeGuaranteeMarginNy;
	}

	public String getTradeGuaranteeProportNy() {
		return this.tradeGuaranteeProportNy;
	}

	public void setTradeGuaranteeProportNy(String tradeGuaranteeProportNy) {
		this.tradeGuaranteeProportNy = tradeGuaranteeProportNy;
	}

	public String getWalletsizeProductsNy() {
		return this.walletsizeProductsNy;
	}

	public void setWalletsizeProductsNy(String walletsizeProductsNy) {
		this.walletsizeProductsNy = walletsizeProductsNy;
	}

}