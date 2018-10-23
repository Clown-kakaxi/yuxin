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
 * Entity implementation class for Entity: CustomerEconomicCapitalCalculation
 * 经济资本测算
 */
@Entity
@Table(name = "ocrm_f_ci_ecnm_cptl_clcltn")
public class CustomerEconomicCapitalCalculation implements Serializable {

    private static final long serialVersionUID = 3811106777138757365L;

    /** 测算结果编号 */
    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long ID;

    /** 贷款金额 */
    @Column(precision = 24, scale = 6)
    private BigDecimal LOAN_ACCOUNT;

    /** 币种 */
    @Column(length = 20)
    private String CURRENCY;

    /** 贷款期限 */
    @Column(length = 20)
    private String LOAN_LIMIT_TIME;

    /** 利率浮动比率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal INTEREST_FLOAT_RATE;

    /** 基准利率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal REFERENCE_RATE;

    /** 执行利率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal RUN_RATE;

    /** FTP */
    @Column(length = 10)
    private String FTP;

    /** 减值准备计提比率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal IMPAIRMENT_PROVISION_RATE;

    /** 基本比例 */
    @Column(precision = 10, scale = 4)
    private BigDecimal BASE_RATIO;

    /** 产品权重 */
    @Column(precision = 10, scale = 4)
    private BigDecimal PRODUCT_RATIO;

    /** 是否房地产行业 */
    @Column(length = 20)
    private String IS_REAL_ESTATE;

    /** 非房地产行业信用贷款的客户评级 */
    @Column(length = 20)
    private String NO_RE_LOAN_CUST_LV;

    /** 担保方式 */
    @Column(length = 20)
    private String GUARANTEE_MODE;

    /** 融资平台现金流覆盖情况 */
    @Column(length = 20)
    private String COVER_STS;

    /** 贷款考核利润 */
    @Column(precision = 24, scale = 6)
    private BigDecimal LOAN_ASSESS_PROFIT;

    /** 经济资本占用 */
    @Column(precision = 24, scale = 6)
    private BigDecimal ECONOMIC_CAPITAL_OCCUPIED;

    /** 经济增加值 */
    @Column(precision = 24, scale = 6)
    private BigDecimal ECONOMIC_INCREASE;

    /** 测算日期时间 */
    @Temporal(TemporalType.DATE)
    private Date CALCULATE_TIME;

    /** 用户ID */
    @Column(length = 20)
    private String CUST_ID;

    /** 机构号 */
    @Column(length = 20)
    private String ORG_ID;

    /** 方案名称 */
    @Column(length = 100)
    private String FA_NAME;
    
    /** FTPvalue */
    @Column(precision = 10, scale = 4)
    private BigDecimal FTP_VALUE;
    
    public BigDecimal getFTP_VALUE() {
        return FTP_VALUE;
    }

    public void setFTP_VALUE(BigDecimal fTP_VALUE) {
        FTP_VALUE = fTP_VALUE;
    }

    public String getFA_NAME() {
        return FA_NAME;
    }

    public void setFA_NAME(String fA_NAME) {
        FA_NAME = fA_NAME;
    }

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

    public BigDecimal getRUN_RATE() {
        return RUN_RATE;
    }

    public void setRUN_RATE(BigDecimal rUN_RATE) {
        RUN_RATE = rUN_RATE;
    }

    public String getFTP() {
        return FTP;
    }

    public void setFTP(String fTP) {
        FTP = fTP;
    }

    public BigDecimal getIMPAIRMENT_PROVISION_RATE() {
        return IMPAIRMENT_PROVISION_RATE;
    }

    public void setIMPAIRMENT_PROVISION_RATE(
            BigDecimal iMPAIRMENT_PROVISION_RATE) {
        IMPAIRMENT_PROVISION_RATE = iMPAIRMENT_PROVISION_RATE;
    }

    public BigDecimal getBASE_RATIO() {
        return BASE_RATIO;
    }

    public void setBASE_RATIO(BigDecimal bASE_RATIO) {
        BASE_RATIO = bASE_RATIO;
    }

    public BigDecimal getPRODUCT_RATIO() {
        return PRODUCT_RATIO;
    }

    public void setPRODUCT_RATIO(BigDecimal pRODUCT_RATIO) {
        PRODUCT_RATIO = pRODUCT_RATIO;
    }

    public String getIS_REAL_ESTATE() {
        return IS_REAL_ESTATE;
    }

    public void setIS_REAL_ESTATE(String iS_REAL_ESTATE) {
        IS_REAL_ESTATE = iS_REAL_ESTATE;
    }

    public String getNO_RE_LOAN_CUST_LV() {
        return NO_RE_LOAN_CUST_LV;
    }

    public void setNO_RE_LOAN_CUST_LV(String nO_RE_LOAN_CUST_LV) {
        NO_RE_LOAN_CUST_LV = nO_RE_LOAN_CUST_LV;
    }

    public String getGUARANTEE_MODE() {
        return GUARANTEE_MODE;
    }

    public void setGUARANTEE_MODE(String gUARANTEE_MODE) {
        GUARANTEE_MODE = gUARANTEE_MODE;
    }

    public String getCOVER_STS() {
        return COVER_STS;
    }

    public void setCOVER_STS(String cOVER_STS) {
        COVER_STS = cOVER_STS;
    }

    public BigDecimal getLOAN_ASSESS_PROFIT() {
        return LOAN_ASSESS_PROFIT;
    }

    public void setLOAN_ASSESS_PROFIT(BigDecimal lOAN_ASSESS_PROFIT) {
        LOAN_ASSESS_PROFIT = lOAN_ASSESS_PROFIT;
    }

    public BigDecimal getECONOMIC_CAPITAL_OCCUPIED() {
        return ECONOMIC_CAPITAL_OCCUPIED;
    }

    public void setECONOMIC_CAPITAL_OCCUPIED(
            BigDecimal eCONOMIC_CAPITAL_OCCUPIED) {
        ECONOMIC_CAPITAL_OCCUPIED = eCONOMIC_CAPITAL_OCCUPIED;
    }

    public BigDecimal getECONOMIC_INCREASE() {
        return ECONOMIC_INCREASE;
    }

    public void setECONOMIC_INCREASE(BigDecimal eCONOMIC_INCREASE) {
        ECONOMIC_INCREASE = eCONOMIC_INCREASE;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
