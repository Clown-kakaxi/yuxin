package com.yuchengtech.bcrm.custview.model;

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
 * 客户他行信息
 */
@Entity
@Table(name = "ocrm_f_ci_other_bank")
public class CustomerOtherBank implements Serializable {

    private static final long serialVersionUID = 4708388339748713926L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    private Long MXTID;

    @Column(name = "BAIL_VAL", precision = 24, scale = 6)
    private BigDecimal BAIL_VAL;// 保证金存款时点余额

    @Column(name = "CRED_AMT", precision = 24, scale = 6)
    private BigDecimal CRED_AMT;// 授信总额

    @Column(name = "CRED_LIMIT", length = 20)
    private String CRED_LIMIT;// 授信期限

    @Temporal(TemporalType.DATE)
    @Column(name = "CRM_DT")
    private Date CRM_DT;// 平台日期

    @Column(name = "CURRENT_VAL", precision = 24, scale = 6)
    private BigDecimal CURRENT_VAL;// 活期存款时点余额

    @Column(name = "CUST_ID", length = 20)
    private String CUST_ID;// 客户编号

    @Column(name = "CUST_NAME", length = 100)
    private String CUST_NAME;// 客户名称

    @Column(name = "HZYEARS", length = 20)
    private String HZYEARS;// 合作年限

    @Column(length = 20)
    private String ID;// 序号

    @Temporal(TemporalType.DATE)
    @Column(name = "INPUT_DT")
    private Date INPUT_DT;// 录入时间

    @Column(name = "INSTN_NAME", length = 100)
    private String INSTN_NAME;// 金融机构名称

    @Column(name = "LON_VAL", precision = 24, scale = 6)
    private BigDecimal LON_VAL;// 贷款时点余额

    @Column(name = "PERIODCIAL_VAL", precision = 24, scale = 6)
    private BigDecimal PERIODCIAL_VAL;// 定期存款时点余额

    @Column(name = "PRD_USE", length = 100)
    private String PRD_USE;// 产品使用情况

    @Column(name = "RMAK", length = 2000)
    private String RMAK;// 备注

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDT_DT")
    private Date UPDT_DT;// 维护日期

    @Column(length = 30)
    private String USERID;// 维护人员

    @Column(length = 20)
    private String USERNAME;// 维护人员姓名
    
    @Column(length = 10)
    private String IS_BASIC_BANK;

    public String getIS_BASIC_BANK() {
        return IS_BASIC_BANK;
    }

    public void setIS_BASIC_BANK(String iS_BASIC_BANK) {
        IS_BASIC_BANK = iS_BASIC_BANK;
    }

    public Long getMXTID() {
        return MXTID;
    }

    public void setMXTID(Long mXTID) {
        MXTID = mXTID;
    }

    public BigDecimal getBAIL_VAL() {
        return BAIL_VAL;
    }

    public void setBAIL_VAL(BigDecimal bAIL_VAL) {
        BAIL_VAL = bAIL_VAL;
    }

    public BigDecimal getCRED_AMT() {
        return CRED_AMT;
    }

    public void setCRED_AMT(BigDecimal cRED_AMT) {
        CRED_AMT = cRED_AMT;
    }

    public String getCRED_LIMIT() {
        return CRED_LIMIT;
    }

    public void setCRED_LIMIT(String cRED_LIMIT) {
        CRED_LIMIT = cRED_LIMIT;
    }

    public Date getCRM_DT() {
        return CRM_DT;
    }

    public void setCRM_DT(Date cRM_DT) {
        CRM_DT = cRM_DT;
    }

    public BigDecimal getCURRENT_VAL() {
        return CURRENT_VAL;
    }

    public void setCURRENT_VAL(BigDecimal cURRENT_VAL) {
        CURRENT_VAL = cURRENT_VAL;
    }

    public String getCUST_ID() {
        return CUST_ID;
    }

    public void setCUST_ID(String cUST_ID) {
        CUST_ID = cUST_ID;
    }

    public String getCUST_NAME() {
        return CUST_NAME;
    }

    public void setCUST_NAME(String cUST_NAME) {
        CUST_NAME = cUST_NAME;
    }

    public String getHZYEARS() {
        return HZYEARS;
    }

    public void setHZYEARS(String hZYEARS) {
        HZYEARS = hZYEARS;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public Date getINPUT_DT() {
        return INPUT_DT;
    }

    public void setINPUT_DT(Date iNPUT_DT) {
        INPUT_DT = iNPUT_DT;
    }

    public String getINSTN_NAME() {
        return INSTN_NAME;
    }

    public void setINSTN_NAME(String iNSTN_NAME) {
        INSTN_NAME = iNSTN_NAME;
    }

    public BigDecimal getLON_VAL() {
        return LON_VAL;
    }

    public void setLON_VAL(BigDecimal lON_VAL) {
        LON_VAL = lON_VAL;
    }

    public BigDecimal getPERIODCIAL_VAL() {
        return PERIODCIAL_VAL;
    }

    public void setPERIODCIAL_VAL(BigDecimal pERIODCIAL_VAL) {
        PERIODCIAL_VAL = pERIODCIAL_VAL;
    }

    public String getPRD_USE() {
        return PRD_USE;
    }

    public void setPRD_USE(String pRD_USE) {
        PRD_USE = pRD_USE;
    }

    public String getRMAK() {
        return RMAK;
    }

    public void setRMAK(String rMAK) {
        RMAK = rMAK;
    }

    public Date getUPDT_DT() {
        return UPDT_DT;
    }

    public void setUPDT_DT(Date uPDT_DT) {
        UPDT_DT = uPDT_DT;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String uSERID) {
        USERID = uSERID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        USERNAME = uSERNAME;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}