package com.yuchengtech.bob.model;

import java.io.Serializable;
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
 * Entity implementation class for Entity: CustomerProfitScore
 * 综合收益打分卡
 */
@Entity
@Table(name = "ocrm_f_ci_profit_score")
public class CustomerProfitScore implements Serializable {

    private static final long serialVersionUID = -8105611587508234463L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long ID;

    /** 申请日期 */
    @Temporal(TemporalType.DATE)
    private Date APPLY_DATE;

    /** 机构名称 */
    @Column(length = 200)
    private String ORG_NAME;

    /** 所属分行管理部 */
    @Column(length = 200)
    private String BRANCH;

    /** 打分卡用途 */
    @Column(length = 100)
    private String USE;

    /** 申请简介 */
    @Column(length = 2000)
    private String APPLY_INTRO;

    /** 填表人 */
    @Column(length = 100)
    private String APPLYER;

    /** 申请备注 */
    @Column(length = 2000)
    private String RMAK;

    /** 分行管理部意见 */
    @Column(length = 2000)
    private String BRANCH_OPINION;

    /** 总行意见 */
    @Column(length = 2000)
    private String HEAD;

    /** 客户名称 */
    @Column(length = 100)
    private String CUST_NAME;

    /** 组织机构代码 */
    @Column(length = 20)
    private String CUST_ZZDM;

    /** 附件 */
    @Column(length = 250)
    private String ANNEX;

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public Date getAPPLY_DATE() {
        return APPLY_DATE;
    }

    public void setAPPLY_DATE(Date aPPLY_DATE) {
        APPLY_DATE = aPPLY_DATE;
    }

    public String getORG_NAME() {
        return ORG_NAME;
    }

    public void setORG_NAME(String oRG_NAME) {
        ORG_NAME = oRG_NAME;
    }

    public String getBRANCH() {
        return BRANCH;
    }

    public void setBRANCH(String bRANCH) {
        BRANCH = bRANCH;
    }

    public String getUSE() {
        return USE;
    }

    public void setUSE(String uSE) {
        USE = uSE;
    }

    public String getAPPLY_INTRO() {
        return APPLY_INTRO;
    }

    public void setAPPLY_INTRO(String aPPLY_INTRO) {
        APPLY_INTRO = aPPLY_INTRO;
    }

    public String getAPPLYER() {
        return APPLYER;
    }

    public void setAPPLYER(String aPPLYER) {
        APPLYER = aPPLYER;
    }

    public String getRMAK() {
        return RMAK;
    }

    public void setRMAK(String rMAK) {
        RMAK = rMAK;
    }

    public String getBRANCH_OPINION() {
        return BRANCH_OPINION;
    }

    public void setBRANCH_OPINION(String bRANCH_OPINION) {
        BRANCH_OPINION = bRANCH_OPINION;
    }

    public String getHEAD() {
        return HEAD;
    }

    public void setHEAD(String hEAD) {
        HEAD = hEAD;
    }

    public String getCUST_NAME() {
        return CUST_NAME;
    }

    public void setCUST_NAME(String cUST_NAME) {
        CUST_NAME = cUST_NAME;
    }

    public String getCUST_ZZDM() {
        return CUST_ZZDM;
    }

    public void setCUST_ZZDM(String cUST_ZZDM) {
        CUST_ZZDM = cUST_ZZDM;
    }

    public String getANNEX() {
        return ANNEX;
    }

    public void setANNEX(String aNNEX) {
        ANNEX = aNNEX;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
