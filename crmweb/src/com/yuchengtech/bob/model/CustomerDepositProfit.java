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
 * Entity implementation class for Entity: CustomerDepositProfit
 * 产品收益参考表-存款结果列表
 */
@Entity
@Table(name = "ocrm_f_ci_deposit_profit")
public class CustomerDepositProfit implements Serializable {

    private static final long serialVersionUID = 6387830661544265028L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    private Long ID;

    /** 币种 */
    @Column(length = 20)
    private String CURRENCY;

    /** 存款金额 */
    @Column(precision = 24, scale = 6)
    private BigDecimal DEPOSIT_ACCOUNT;

    /** 存款期限 */
    @Column(length = 20)
    private String DEPOSIT_LIMIT_TIME;

    /** FTP */
    @Column(length = 10)
    private String FTP;

    /** 计算日期 */
    @Temporal(TemporalType.DATE)
    private Date CALCULATE_TIME;

    /** 用户ID */
    @Column(length = 20)
    private String CUST_ID;

    /** 机构号 */
    @Column(length = 20)
    private String ORG_ID;

    /** 测算收益值 */
    @Column(precision = 24, scale = 6)
    private BigDecimal CACULATE_PROFIT;

    /** 存款利率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal DEPOSIT_RATE;

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

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String cURRENCY) {
        CURRENCY = cURRENCY;
    }

    public BigDecimal getDEPOSIT_ACCOUNT() {
        return DEPOSIT_ACCOUNT;
    }

    public void setDEPOSIT_ACCOUNT(BigDecimal dEPOSIT_ACCOUNT) {
        DEPOSIT_ACCOUNT = dEPOSIT_ACCOUNT;
    }

    public String getDEPOSIT_LIMIT_TIME() {
        return DEPOSIT_LIMIT_TIME;
    }

    public void setDEPOSIT_LIMIT_TIME(String dEPOSIT_LIMIT_TIME) {
        DEPOSIT_LIMIT_TIME = dEPOSIT_LIMIT_TIME;
    }

    public String getFTP() {
        return FTP;
    }

    public void setFTP(String fTP) {
        FTP = fTP;
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

    public BigDecimal getCACULATE_PROFIT() {
        return CACULATE_PROFIT;
    }

    public void setCACULATE_PROFIT(BigDecimal cACULATE_PROFIT) {
        CACULATE_PROFIT = cACULATE_PROFIT;
    }

    public BigDecimal getDEPOSIT_RATE() {
        return DEPOSIT_RATE;
    }

    public void setDEPOSIT_RATE(BigDecimal dEPOSIT_RATE) {
        DEPOSIT_RATE = dEPOSIT_RATE;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
