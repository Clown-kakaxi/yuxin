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
 * Entity implementation class for Entity: CustomerLeaderInfo
 * 
 */
@Entity
@Table(name = "ocrm_f_ci_manager")
public class CustomerLeaderInfo implements Serializable {

    private static final long serialVersionUID = 7011239297092502293L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    private Long ID;

    /** 是否法人 */
    @Column(length = 4)
    private String FR_FLG;

    /** 公司职务 */
    @Column(length = 40)
    private String POSITION;

    /** 客户编号 */
    @Column(length = 20)
    private String CUST_ID;

    /** 客户名称 */
    @Column(length = 100)
    private String CUST_NAME;

    /** 证件号码 */
    @Column(length = 100)
    private String CRET_NO;

    /** 高管中文名 */
    @Column(length = 20)
    private String GGNAME_ZH;

    /** 高管英文名 */
    @Column(length = 100)
    private String GGNAME_EN;

    /** 担任职务时间 */
    @Column(length = 20)
    private String POST_TM;

    /** 负责业务类型 */
    @Column(length = 40)
    private String FZ_TYP;

    /** 证件类型 */
    @Column(length = 20)
    private String CRET_TYP;

    /** 证件有效起始日 */
    @Temporal(TemporalType.DATE)
    private Date CRET_BGNDT;

    /** 证件有效到期日 */
    @Temporal(TemporalType.DATE)
    private Date CRET_ENDDT;

    /** 现单位工作时间 */
    @Column(length = 20)
    private String WORK_TM;

    /** 重要程度 */
    @Column(length = 20)
    private String IMT_LEV;

    /** 是否我行个人客户 */
    @Column(length = 20)
    private String PER_FLG;

    /** 营销主要关键要素 */
    @Column(length = 100)
    private String WK_IMT;

    /** 关系背景 */
    @Column(length = 50)
    private String RELA_BJ;

    /** 持股情况 */
    @Column(length = 20)
    private String STOCK;

    /** 个人爱好 */
    @Column(length = 100)
    private String PER_LIKE;

    /** 国籍 */
    @Column(length = 40)
    private String COUNTRY;

    /** 出生日期 */
    @Temporal(TemporalType.DATE)
    private Date BIRTHDAY;

    /** 性别 */
    @Column(length = 20)
    private String SEX;

    /** 民族 */
    @Column(length = 20)
    private String NATION;

    /** 宗教信仰 */
    @Column(length = 40)
    private String RELIGION;

    /** 婚姻状况 */
    @Column(length = 20)
    private String MARRY_FLG;

    /** 学历 */
    @Column(length = 20)
    private String SCHOOL_H;

    /** 毕业院校 */
    @Column(length = 40)
    private String SCHOOL;

    /** 职称 */
    @Column(length = 50)
    private String STAFF;

    /** 相关行业从业年限 */
    @Column(length = 20)
    private String TRADE_YEAR;

    /** 月收入（元） */
    @Column(precision = 10, scale = 2)
    private BigDecimal RCV_MON;

    /** 主要经济来源 */
    @Column(length = 40)
    private String RCV_FROM;

    /** 其他经济来源 */
    @Column(length = 40)
    private String RCV_FROM_OTHER;

    /** 社会职务 */
    @Column(length = 20)
    private String SH_POSITION;

    /** 邮箱地址 */
    @Column(length = 40)
    private String EMAIL;

    /** 工作地址 */
    @Column(length = 100)
    private String OFFICEADDR;

    /** 移动电话 */
    @Column(length = 30)
    private String MOBILE;

    /** 办公电话 */
    @Column(length = 30)
    private String OFFICETEL;

    /** 家庭住址 */
    @Column(length = 100)
    private String FAMILYADDR;

    /** 家庭供养人员 */
    @Column(precision = 10, scale = 2)
    private BigDecimal NURTUREPER;

    /** 工作简历 */
    @Column(length = 100)
    private String WORK_H;

    /** 备注 */
    @Column(length = 2000)
    private String RMAK;

    /** 平台日期 */
    @Temporal(TemporalType.DATE)
    private Date CRM_DT;

    /** 家庭电话 */
    @Column(length = 30)
    private String FAMILY_TEL;
    
    @Column(length = 40)
    private String MANAGER_ID;
    
    public String getMANAGER_ID() {
        return MANAGER_ID;
    }

    public void setMANAGER_ID(String mANAGER_ID) {
        MANAGER_ID = mANAGER_ID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public String getFR_FLG() {
        return FR_FLG;
    }

    public void setFR_FLG(String fR_FLG) {
        FR_FLG = fR_FLG;
    }

    public String getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(String pOSITION) {
        POSITION = pOSITION;
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

    public String getCRET_NO() {
        return CRET_NO;
    }

    public void setCRET_NO(String cRET_NO) {
        CRET_NO = cRET_NO;
    }

    public String getGGNAME_ZH() {
        return GGNAME_ZH;
    }

    public void setGGNAME_ZH(String gGNAME_ZH) {
        GGNAME_ZH = gGNAME_ZH;
    }

    public String getGGNAME_EN() {
        return GGNAME_EN;
    }

    public void setGGNAME_EN(String gGNAME_EN) {
        GGNAME_EN = gGNAME_EN;
    }

    public String getPOST_TM() {
        return POST_TM;
    }

    public void setPOST_TM(String pOST_TM) {
        POST_TM = pOST_TM;
    }

    public String getFZ_TYP() {
        return FZ_TYP;
    }

    public void setFZ_TYP(String fZ_TYP) {
        FZ_TYP = fZ_TYP;
    }

    public String getCRET_TYP() {
        return CRET_TYP;
    }

    public void setCRET_TYP(String cRET_TYP) {
        CRET_TYP = cRET_TYP;
    }

    public Date getCRET_BGNDT() {
        return CRET_BGNDT;
    }

    public void setCRET_BGNDT(Date cRET_BGNDT) {
        CRET_BGNDT = cRET_BGNDT;
    }

    public Date getCRET_ENDDT() {
        return CRET_ENDDT;
    }

    public void setCRET_ENDDT(Date cRET_ENDDT) {
        CRET_ENDDT = cRET_ENDDT;
    }

    public String getWORK_TM() {
        return WORK_TM;
    }

    public void setWORK_TM(String wORK_TM) {
        WORK_TM = wORK_TM;
    }

    public String getIMT_LEV() {
        return IMT_LEV;
    }

    public void setIMT_LEV(String iMT_LEV) {
        IMT_LEV = iMT_LEV;
    }

    public String getPER_FLG() {
        return PER_FLG;
    }

    public void setPER_FLG(String pER_FLG) {
        PER_FLG = pER_FLG;
    }

    public String getWK_IMT() {
        return WK_IMT;
    }

    public void setWK_IMT(String wK_IMT) {
        WK_IMT = wK_IMT;
    }

    public String getRELA_BJ() {
        return RELA_BJ;
    }

    public void setRELA_BJ(String rELA_BJ) {
        RELA_BJ = rELA_BJ;
    }

    public String getSTOCK() {
        return STOCK;
    }

    public void setSTOCK(String sTOCK) {
        STOCK = sTOCK;
    }

    public String getPER_LIKE() {
        return PER_LIKE;
    }

    public void setPER_LIKE(String pER_LIKE) {
        PER_LIKE = pER_LIKE;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String cOUNTRY) {
        COUNTRY = cOUNTRY;
    }

    public Date getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(Date bIRTHDAY) {
        BIRTHDAY = bIRTHDAY;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String sEX) {
        SEX = sEX;
    }

    public String getNATION() {
        return NATION;
    }

    public void setNATION(String nATION) {
        NATION = nATION;
    }

    public String getRELIGION() {
        return RELIGION;
    }

    public void setRELIGION(String rELIGION) {
        RELIGION = rELIGION;
    }

    public String getMARRY_FLG() {
        return MARRY_FLG;
    }

    public void setMARRY_FLG(String mARRY_FLG) {
        MARRY_FLG = mARRY_FLG;
    }

    public String getSCHOOL_H() {
        return SCHOOL_H;
    }

    public void setSCHOOL_H(String sCHOOL_H) {
        SCHOOL_H = sCHOOL_H;
    }

    public String getSCHOOL() {
        return SCHOOL;
    }

    public void setSCHOOL(String sCHOOL) {
        SCHOOL = sCHOOL;
    }

    public String getSTAFF() {
        return STAFF;
    }

    public void setSTAFF(String sTAFF) {
        STAFF = sTAFF;
    }

    public String getTRADE_YEAR() {
        return TRADE_YEAR;
    }

    public void setTRADE_YEAR(String tRADE_YEAR) {
        TRADE_YEAR = tRADE_YEAR;
    }

    public BigDecimal getRCV_MON() {
        return RCV_MON;
    }

    public void setRCV_MON(BigDecimal rCV_MON) {
        RCV_MON = rCV_MON;
    }

    public String getRCV_FROM() {
        return RCV_FROM;
    }

    public void setRCV_FROM(String rCV_FROM) {
        RCV_FROM = rCV_FROM;
    }

    public String getRCV_FROM_OTHER() {
        return RCV_FROM_OTHER;
    }

    public void setRCV_FROM_OTHER(String rCV_FROM_OTHER) {
        RCV_FROM_OTHER = rCV_FROM_OTHER;
    }

    public String getSH_POSITION() {
        return SH_POSITION;
    }

    public void setSH_POSITION(String sH_POSITION) {
        SH_POSITION = sH_POSITION;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String eMAIL) {
        EMAIL = eMAIL;
    }

    public String getOFFICEADDR() {
        return OFFICEADDR;
    }

    public void setOFFICEADDR(String oFFICEADDR) {
        OFFICEADDR = oFFICEADDR;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String mOBILE) {
        MOBILE = mOBILE;
    }

    public String getOFFICETEL() {
        return OFFICETEL;
    }

    public void setOFFICETEL(String oFFICETEL) {
        OFFICETEL = oFFICETEL;
    }

    public String getFAMILYADDR() {
        return FAMILYADDR;
    }

    public void setFAMILYADDR(String fAMILYADDR) {
        FAMILYADDR = fAMILYADDR;
    }

    public BigDecimal getNURTUREPER() {
        return NURTUREPER;
    }

    public void setNURTUREPER(BigDecimal nURTUREPER) {
        NURTUREPER = nURTUREPER;
    }

    public String getWORK_H() {
        return WORK_H;
    }

    public void setWORK_H(String wORK_H) {
        WORK_H = wORK_H;
    }

    public String getRMAK() {
        return RMAK;
    }

    public void setRMAK(String rMAK) {
        RMAK = rMAK;
    }

    public Date getCRM_DT() {
        return CRM_DT;
    }

    public void setCRM_DT(Date cRM_DT) {
        CRM_DT = cRM_DT;
    }

    public String getFAMILY_TEL() {
        return FAMILY_TEL;
    }

    public void setFAMILY_TEL(String fAMILY_TEL) {
        FAMILY_TEL = fAMILY_TEL;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
