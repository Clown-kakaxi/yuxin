package com.yuchengtech.bob.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: CustomerMiddleProfit 产品收益参考表-中间业务结果列表
 */
@Entity
@Table(name = "OCRM_F_CI_MID_PROFIT")
public class CustomerMiddleProfit implements Serializable {

    private static final long serialVersionUID = -5550633384136537030L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    private Long ID;

    /** 金额 */
    @Column(precision = 24, scale = 6)
    private BigDecimal ACCOUNT;

    /** 减值准备计提比率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal IMPAIRMENT_PROVISION_RATE;

    /** 基本比例 */
    @Column(precision = 10, scale = 4)
    private BigDecimal BASE_RATIO;

    /** 是否房地产行业 */
    @Column(length = 10)
    private String IS_REAL_ESTATE;

    /** 非房地产行业信用贷款的客户评级 */
    @Column(length = 20)
    private String NO_RE_LOAN_CUST_LV;

    /** 担保方式 */
    @Column(length = 20)
    private String GUARANTEE_MODE;

    /** 经济资本占用 */
    @Column(precision = 24, scale = 6)
    private BigDecimal ECONOMIC_CAPITAL_OCCUPIED;

    /** 保证金比例 */
    @Column(precision = 10, scale = 4)
    private BigDecimal MARGIN_RATIO;

    /** 测算收益值 */
    @Column(precision = 24, scale = 6)
    private BigDecimal CACULATE_PROFIT;

    /** 保证金期限 */
    @Column(length = 20)
    private String MARGIN_LIMIT_TIME;

    /** 中间业务收入 */
    @Column(precision = 24, scale = 6)
    private BigDecimal MARGIN_INCOME;

    /** 中间业务净收入 */
    @Column(precision = 24, scale = 6)
    private BigDecimal MARGIN_NET_INCOME;

    /** 中间业务支出 */
    @Column(precision = 24, scale = 6)
    private BigDecimal MARGIN_PAY;

    /** 手续费比率 */
    @Column(precision = 10, scale = 4)
    private BigDecimal SXF_RATIO;

    /** 产品类型 */
    @Column(length = 40)
    private String PROD_TYP;

    public String getPROD_TYP() {
        return PROD_TYP;
    }

    public void setPROD_TYP(String pROD_TYP) {
        PROD_TYP = pROD_TYP;
    }

    public BigDecimal getSXF_RATIO() {
        return SXF_RATIO;
    }

    public void setSXF_RATIO(BigDecimal sXF_RATIO) {
        SXF_RATIO = sXF_RATIO;
    }

    public BigDecimal getMARGIN_PAY() {
        return MARGIN_PAY;
    }

    public void setMARGIN_PAY(BigDecimal mARGIN_PAY) {
        MARGIN_PAY = mARGIN_PAY;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public BigDecimal getACCOUNT() {
        return ACCOUNT;
    }

    public void setACCOUNT(BigDecimal aCCOUNT) {
        ACCOUNT = aCCOUNT;
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

    public BigDecimal getECONOMIC_CAPITAL_OCCUPIED() {
        return ECONOMIC_CAPITAL_OCCUPIED;
    }

    public void setECONOMIC_CAPITAL_OCCUPIED(
            BigDecimal eCONOMIC_CAPITAL_OCCUPIED) {
        ECONOMIC_CAPITAL_OCCUPIED = eCONOMIC_CAPITAL_OCCUPIED;
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

    public BigDecimal getMARGIN_RATIO() {
        return MARGIN_RATIO;
    }

    public void setMARGIN_RATIO(BigDecimal mARGIN_RATIO) {
        MARGIN_RATIO = mARGIN_RATIO;
    }

    public BigDecimal getCACULATE_PROFIT() {
        return CACULATE_PROFIT;
    }

    public void setCACULATE_PROFIT(BigDecimal cACULATE_PROFIT) {
        CACULATE_PROFIT = cACULATE_PROFIT;
    }

    public String getMARGIN_LIMIT_TIME() {
        return MARGIN_LIMIT_TIME;
    }

    public void setMARGIN_LIMIT_TIME(String mARGIN_LIMIT_TIME) {
        MARGIN_LIMIT_TIME = mARGIN_LIMIT_TIME;
    }

    public BigDecimal getMARGIN_INCOME() {
        return MARGIN_INCOME;
    }

    public void setMARGIN_INCOME(BigDecimal mARGIN_INCOME) {
        MARGIN_INCOME = mARGIN_INCOME;
    }

    public BigDecimal getMARGIN_NET_INCOME() {
        return MARGIN_NET_INCOME;
    }

    public void setMARGIN_NET_INCOME(BigDecimal mARGIN_NET_INCOME) {
        MARGIN_NET_INCOME = mARGIN_NET_INCOME;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
