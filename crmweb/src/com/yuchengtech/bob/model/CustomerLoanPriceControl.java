package com.yuchengtech.bob.model;

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
 * Entity implementation class for Entity: CustomerLoanPriceControl
 * 贷款定价器
 */
@Entity
@Table(name = "ocrm_f_ci_loan_price_ctrl")
public class CustomerLoanPriceControl implements Serializable {

    private static final long serialVersionUID = -4862998194001715871L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long ID;

    /** 贷款金额 */
    @Column(precision = 24, scale = 6)
    private BigDecimal LOAN_ACCOUNT;

    /** 币种 */
    @Column(length = 100)
    private String CURRENCY;

    /** 贷款期限 */
    @Column(length = 100)
    private String LOAN_LIMIT_TIME;

    /** FTP */
    @Column(length = 100)
    private String FTP;
    
    /** FTP值 */
    @Column(precision = 24, scale = 6)
    private  BigDecimal FTP_VALUE;
    
    /** 营销费用 */
    @Column(precision = 24, scale = 6)
    private BigDecimal MARKET_COST;

    /** 其他费用 */
    @Column(precision = 24, scale = 6)
    private BigDecimal OTHER_COST;

    /** 经济资本占用 */
    @Column(precision = 24, scale = 6)
    private BigDecimal ECONOMIC_CAPITAL_OCCUPIED;

    /** 目标利润 */
    @Column(precision = 24, scale = 6)
    private BigDecimal TARGET_PROFIT;

    /** 目标收益率 */
    @Column(precision = 24, scale = 6)
    private BigDecimal TARGET_PROFIT_RATE;

    /** 利率浮动比率 */
    @Column(precision = 24, scale = 6)
    private BigDecimal INTEREST_FLOAT_RATE;

    /** 基准利率 */
    @Column(precision = 24, scale = 6)
    private BigDecimal REFERENCE_RATE;

    /** 测算日期时间 */
    @Temporal(TemporalType.DATE)
    private Date CALCULATE_TIME;

    /** 用户ID */
    @Column(length = 100)
    private String CUST_ID;

    /** 机构号 */
    @Column(length = 100)
    private String ORG_ID;

    /** 估算价格 */
    @Column(precision = 24, scale = 6)
    private BigDecimal ESTIMATE_PRICE;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public BigDecimal getLOAN_ACCOUNT() {
		return LOAN_ACCOUNT;
	}

	public void setLOAN_ACCOUNT(BigDecimal lOAN_ACCOUNT) {
		LOAN_ACCOUNT = lOAN_ACCOUNT;
	}

	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}

	public String getLOAN_LIMIT_TIME() {
		return LOAN_LIMIT_TIME;
	}

	public void setLOAN_LIMIT_TIME(String lOAN_LIMIT_TIME) {
		LOAN_LIMIT_TIME = lOAN_LIMIT_TIME;
	}

	public String getFTP() {
		return FTP;
	}

	public void setFTP(String fTP) {
		FTP = fTP;
	}



	public BigDecimal getFTP_VALUE() {
		return FTP_VALUE;
	}

	public void setFTP_VALUE(BigDecimal fTP_VALUE) {
		FTP_VALUE = fTP_VALUE;
	}

	public BigDecimal getMARKET_COST() {
		return MARKET_COST;
	}

	public void setMARKET_COST(BigDecimal mARKET_COST) {
		MARKET_COST = mARKET_COST;
	}

	public BigDecimal getOTHER_COST() {
		return OTHER_COST;
	}

	public void setOTHER_COST(BigDecimal oTHER_COST) {
		OTHER_COST = oTHER_COST;
	}

	public BigDecimal getECONOMIC_CAPITAL_OCCUPIED() {
		return ECONOMIC_CAPITAL_OCCUPIED;
	}

	public void setECONOMIC_CAPITAL_OCCUPIED(BigDecimal eCONOMIC_CAPITAL_OCCUPIED) {
		ECONOMIC_CAPITAL_OCCUPIED = eCONOMIC_CAPITAL_OCCUPIED;
	}

	public BigDecimal getTARGET_PROFIT() {
		return TARGET_PROFIT;
	}

	public void setTARGET_PROFIT(BigDecimal tARGET_PROFIT) {
		TARGET_PROFIT = tARGET_PROFIT;
	}

	public BigDecimal getTARGET_PROFIT_RATE() {
		return TARGET_PROFIT_RATE;
	}

	public void setTARGET_PROFIT_RATE(BigDecimal tARGET_PROFIT_RATE) {
		TARGET_PROFIT_RATE = tARGET_PROFIT_RATE;
	}

	public BigDecimal getINTEREST_FLOAT_RATE() {
		return INTEREST_FLOAT_RATE;
	}

	public void setINTEREST_FLOAT_RATE(BigDecimal iNTEREST_FLOAT_RATE) {
		INTEREST_FLOAT_RATE = iNTEREST_FLOAT_RATE;
	}

	public BigDecimal getREFERENCE_RATE() {
		return REFERENCE_RATE;
	}

	public void setREFERENCE_RATE(BigDecimal rEFERENCE_RATE) {
		REFERENCE_RATE = rEFERENCE_RATE;
	}

	public Date getCALCULATE_TIME() {
		return CALCULATE_TIME;
	}

	public void setCALCULATE_TIME(Date cALCULATE_TIME) {
		CALCULATE_TIME = cALCULATE_TIME;
	}

	public String getCUST_ID() {
		return CUST_ID;
	}

	public void setCUST_ID(String cUST_ID) {
		CUST_ID = cUST_ID;
	}

	public String getORG_ID() {
		return ORG_ID;
	}

	public void setORG_ID(String oRG_ID) {
		ORG_ID = oRG_ID;
	}

	public BigDecimal getESTIMATE_PRICE() {
		return ESTIMATE_PRICE;
	}

	public void setESTIMATE_PRICE(BigDecimal eSTIMATE_PRICE) {
		ESTIMATE_PRICE = eSTIMATE_PRICE;
	}



}
