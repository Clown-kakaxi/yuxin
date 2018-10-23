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
 * Entity implementation class for Entity: CustomerCreditRisk
 * 
 */
@Entity
@Table(name = "ocrm_f_ci_credit_risk")
public class CustomerCreditRisk implements Serializable {

    private static final long serialVersionUID = 3222960927234138102L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long ID;

    /** 业务主键（外键）贷款账号 */
    private String PAYACCOUNT;

    /** 基本比例 */
    @Column(length = 10)
    private String BASERATIO;

    /** 信用风险敞口 */
    @Column(precision = 24, scale = 6)
    private BigDecimal RISKBUSINESSSUM;

    /** 行业系数 */
    @Column(length = 10)
    private String BUSINESSCOEFF;

    /** 客户评级系数 */
    @Column(length = 10)
    private String GRADECOEFF;

    /** 产品权重 */
    @Column(length = 10)
    private String PRODRATIO;

    /** 担保系数 */
    @Column(length = 10)
    private String GUARANTEECOEFF;

    /** 期限系数 */
    @Column(length = 10)
    private String TIMELIMITCOEFF;

    /** 调整系数 */
    @Column(precision = 10, scale = 4)
    private BigDecimal ADJUSTCOEFF;

    /** 调整值 */
    @Column(precision = 24, scale = 6)
    private BigDecimal AJUSTVALUE;

    /** 申请人 */
    @Column(length = 50)
    private String APPLICANT;

    /** 申请时间 */
    @Temporal(TemporalType.DATE)
    private Date APPLY_TIME;

    /** 审批人 */
    @Column(length = 50)
    private String APPROVER;

    /** 审批时间 */
    @Temporal(TemporalType.DATE)
    private Date APPROVE_TIME;

    /** 审批状态 */
    @Column(length = 2)
    private Integer APPROVE_STS;

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public String getPAYACCOUNT() {
        return PAYACCOUNT;
    }

    public void setPAYACCOUNT(String pAYACCOUNT) {
        PAYACCOUNT = pAYACCOUNT;
    }

    public String getBASERATIO() {
        return BASERATIO;
    }

    public void setBASERATIO(String bASERATIO) {
        BASERATIO = bASERATIO;
    }

    public BigDecimal getRISKBUSINESSSUM() {
        return RISKBUSINESSSUM;
    }

    public void setRISKBUSINESSSUM(BigDecimal rISKBUSINESSSUM) {
        RISKBUSINESSSUM = rISKBUSINESSSUM;
    }

    public String getBUSINESSCOEFF() {
        return BUSINESSCOEFF;
    }

    public void setBUSINESSCOEFF(String bUSINESSCOEFF) {
        BUSINESSCOEFF = bUSINESSCOEFF;
    }

    public String getGRADECOEFF() {
        return GRADECOEFF;
    }

    public void setGRADECOEFF(String gRADECOEFF) {
        GRADECOEFF = gRADECOEFF;
    }

    public String getPRODRATIO() {
        return PRODRATIO;
    }

    public void setPRODRATIO(String pRODRATIO) {
        PRODRATIO = pRODRATIO;
    }

    public String getGUARANTEECOEFF() {
        return GUARANTEECOEFF;
    }

    public void setGUARANTEECOEFF(String gUARANTEECOEFF) {
        GUARANTEECOEFF = gUARANTEECOEFF;
    }

    public String getTIMELIMITCOEFF() {
        return TIMELIMITCOEFF;
    }

    public void setTIMELIMITCOEFF(String tIMELIMITCOEFF) {
        TIMELIMITCOEFF = tIMELIMITCOEFF;
    }

    public BigDecimal getADJUSTCOEFF() {
        return ADJUSTCOEFF;
    }

    public void setADJUSTCOEFF(BigDecimal aDJUSTCOEFF) {
        ADJUSTCOEFF = aDJUSTCOEFF;
    }

    public BigDecimal getAJUSTVALUE() {
        return AJUSTVALUE;
    }

    public void setAJUSTVALUE(BigDecimal aJUSTVALUE) {
        AJUSTVALUE = aJUSTVALUE;
    }

    public String getAPPLICANT() {
        return APPLICANT;
    }

    public void setAPPLICANT(String aPPLICANT) {
        APPLICANT = aPPLICANT;
    }

    public Date getAPPLY_TIME() {
        return APPLY_TIME;
    }

    public void setAPPLY_TIME(Date aPPLY_TIME) {
        APPLY_TIME = aPPLY_TIME;
    }

    public String getAPPROVER() {
        return APPROVER;
    }

    public void setAPPROVER(String aPPROVER) {
        APPROVER = aPPROVER;
    }

    public Date getAPPROVE_TIME() {
        return APPROVE_TIME;
    }

    public void setAPPROVE_TIME(Date aPPROVE_TIME) {
        APPROVE_TIME = aPPROVE_TIME;
    }

    public Integer getAPPROVE_STS() {
        return APPROVE_STS;
    }

    public void setAPPROVE_STS(Integer aPPROVE_STS) {
        APPROVE_STS = aPPROVE_STS;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
