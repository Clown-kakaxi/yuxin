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
 * Entity implementation class for Entity: CustomerProfit 
 * 产品收益参考表-贷款结果列表
 */
@Entity
@Table(name = "ocrm_f_ci_profit")
public class CustomerLoanProfit implements Serializable {

    private static final long serialVersionUID = -385103177739119889L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
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

    /** 基准利率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal REFERENCE_RATE;

    /** 贷款类型 */
    @Column(length = 20)
    private String LOAN_TYP;

    /** 融资平台现金流覆盖情况 */
    @Column(length = 20)
    private String COVER_STS;

    /** 考核利润 */
    @Column(precision = 24, scale = 6)
    private BigDecimal LOAN_ASSESS_PROFIT;

    /** 经济资本占用 */
    @Column(precision = 24, scale = 6)
    private BigDecimal ECONOMIC_CAPITAL_OCCUPIED;

    /** 融资平台经济资本占用 */
    @Column(precision = 24, scale = 6)
    private BigDecimal COVER_MOUNT;

    /** 测算收益值 */
    @Column(precision = 24, scale = 6)
    private BigDecimal CACULATE_PROFIT;

    /** 用户ID */
    @Column(length = 20)
    private String CUST_ID;

    /** 机构号 */
    @Column(length = 20)
    private String ORG_ID;

    /** 计算日期 */
    @Temporal(TemporalType.DATE)
    private Date CALCULATE_TIME;

    /** 执行利率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal RUN_RATE;

    /** FTP */
    @Column(length = 10)
    private String FTP;

    /** 利率浮动比率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal INTEREST_FLOAT_RATE;
    
    /** FTP_VALUE */
    @Column(precision = 10, scale = 4)
    private BigDecimal FTP_VALUE;

    public BigDecimal getFTP_VALUE() {
        return FTP_VALUE;
    }

    public void setFTP_VALUE(BigDecimal fTP_VALUE) {
        FTP_VALUE = fTP_VALUE;
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

    public BigDecimal getREFERENCE_RATE() {
        return REFERENCE_RATE;
    }

    public void setREFERENCE_RATE(BigDecimal rEFERENCE_RATE) {
        REFERENCE_RATE = rEFERENCE_RATE;
    }

    public String getLOAN_TYP() {
        return LOAN_TYP;
    }

    public void setLOAN_TYP(String lOAN_TYP) {
        LOAN_TYP = lOAN_TYP;
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

    public BigDecimal getCOVER_MOUNT() {
        return COVER_MOUNT;
    }

    public void setCOVER_MOUNT(BigDecimal cOVER_MOUNT) {
        COVER_MOUNT = cOVER_MOUNT;
    }

    public BigDecimal getCACULATE_PROFIT() {
        return CACULATE_PROFIT;
    }

    public void setCACULATE_PROFIT(BigDecimal cACULATE_PROFIT) {
        CACULATE_PROFIT = cACULATE_PROFIT;
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

    public Date getCALCULATE_TIME() {
        return CALCULATE_TIME;
    }

    public void setCALCULATE_TIME(Date cALCULATE_TIME) {
        CALCULATE_TIME = cALCULATE_TIME;
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

    public BigDecimal getINTEREST_FLOAT_RATE() {
        return INTEREST_FLOAT_RATE;
    }

    public void setINTEREST_FLOAT_RATE(BigDecimal iNTEREST_FLOAT_RATE) {
        INTEREST_FLOAT_RATE = iNTEREST_FLOAT_RATE;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


}
