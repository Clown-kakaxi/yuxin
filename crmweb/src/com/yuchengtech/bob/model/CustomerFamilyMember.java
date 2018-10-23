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
 * Entity implementation class for Entity: CustomerFamilyMember
 * 
 */
@Entity
@Table(name = "ocrm_f_ci_family_member")
public class CustomerFamilyMember implements Serializable {

    private static final long serialVersionUID = -8541998719159401556L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    private Long MXTID;

    /** 客户编号 */
    @Column(length = 20)
    private String CUST_ID;

    /** 成员编号 */
    @Column(length = 20)
    private String MEMBER_ID;

    /** 高管编号 */
    @Column(length = 20)
    private String MANAGER_ID;

    /** 成员证件号码 */
    @Column(length = 100)
    private String MEMBERCRET_NO;

    /** 家庭成员名称 */
    @Column(length = 40)
    private String MEMBERNAME;

    /** 家庭关系 */
    @Column(length = 50)
    private String FAMILYRELA;

    /** 成员证件类型 */
    @Column(length = 20)
    private String MEMBERCRET_TYP;

    /** 固定电话 */
    @Column(length = 30)
    private String TEL;

    /** 手机号码 */
    @Column(length = 30)
    private String MOBILE;

    /** email */
    @Column(length = 50)
    private String EMAIL;

    /** 生日 */
    @Temporal(TemporalType.DATE)
    private Date BIRTHDAY;

    /** 家族成员所在企业名称 */
    @Column(length = 50)
    private String COMPANY;
    /** 平台日期 */
    @Temporal(TemporalType.DATE)
    private Date CRM_DT;

    @Column(length = 2000)
    private String RMAK;

    public String getRMAK() {
        return RMAK;
    }

    public void setRMAK(String rMAK) {
        RMAK = rMAK;
    }

    public Long getMXTID() {
        return MXTID;
    }

    public void setMXTID(Long iD) {
        MXTID = iD;
    }

    public String getCUST_ID() {
        return CUST_ID;
    }

    public void setCUST_ID(String cUST_ID) {
        CUST_ID = cUST_ID;
    }

    public String getMEMBER_ID() {
        return MEMBER_ID;
    }

    public void setMEMBER_ID(String mEMBER_ID) {
        MEMBER_ID = mEMBER_ID;
    }

    public String getMANAGER_ID() {
        return MANAGER_ID;
    }

    public void setMANAGER_ID(String mANAGER_ID) {
        MANAGER_ID = mANAGER_ID;
    }

    public String getMEMBERCRET_NO() {
        return MEMBERCRET_NO;
    }

    public void setMEMBERCRET_NO(String mEMBERCRET_NO) {
        MEMBERCRET_NO = mEMBERCRET_NO;
    }

    public String getMEMBERNAME() {
        return MEMBERNAME;
    }

    public void setMEMBERNAME(String mEMBERNAME) {
        MEMBERNAME = mEMBERNAME;
    }

    public String getFAMILYRELA() {
        return FAMILYRELA;
    }

    public void setFAMILYRELA(String fAMILYRELA) {
        FAMILYRELA = fAMILYRELA;
    }

    public String getMEMBERCRET_TYP() {
        return MEMBERCRET_TYP;
    }

    public void setMEMBERCRET_TYP(String mEMBERCRET_TYP) {
        MEMBERCRET_TYP = mEMBERCRET_TYP;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String tEL) {
        TEL = tEL;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String mOBILE) {
        MOBILE = mOBILE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String eMAIL) {
        EMAIL = eMAIL;
    }

    public Date getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(Date bIRTHDAY) {
        BIRTHDAY = bIRTHDAY;
    }

    public String getCOMPANY() {
        return COMPANY;
    }

    public void setCOMPANY(String cOMPANY) {
        COMPANY = cOMPANY;
    }

    public Date getCRM_DT() {
        return CRM_DT;
    }

    public void setCRM_DT(Date cRM_DT) {
        CRM_DT = cRM_DT;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
