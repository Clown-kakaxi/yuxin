package com.yuchengtech.bob.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * km-11-09-18
 */
@Entity
@Table(name = "OCRM_F_CI_SOME_INFO")
public class CustomerSomeInfo implements Serializable {

    private static final long serialVersionUID = 6482590646972478929L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    private Long ID;

    /** 英文名称 */
    @Column(length = 100)
    private String CUST_EN_NAME;

    /** 英文简称 */
    @Column(length = 80)
    private String EN_ABBR;

    /** 所有者性质 */
    @Column(length = 50)
    private String SYZ_CHAR;
    
    /** 考核口径客户规模*/
    @Column(length = 20)
    private String CRM_SCOPE;


	/** 行业一级分类 */
    @Column(length = 20)
    private String HY_TYP1;

    /** 行业二级分类 */
    @Column(length = 20)
    private String HY_TYP2;

    /** 行业三级分类 */
    @Column(length = 20)
    private String HY_TYP3;

    /** 是否集团客户 */
    @Column(length = 20)
    private String GROUP_FLG;

    /** 是否上市公司 */
    @Column(length = 20)
    private String MARKET_FLG;

    /** 大客户级别 */
    @Column(length = 1)
    private String CUST_BIG_LEV;

    /** 中小客户级别 */
    @Column(length = 1)
    private String CUST_SMALL_LEV;

    /** 客户评级 */
    @Column(length = 4)
    private String CUST_LEV;

    /** 系统客户分类 */
    @Column(length = 20)
    private String CRM_CUST_CLASS;

    /** 主营业务 */
    @Column(length = 200)
    private String IMT_BUSINESS;

    /** 办公地址邮政编码 */
    @Column(length = 100)
    private String OFFICE_ZIP;

    /** 客户联系电话 */
    @Column(length = 30)
    private String TEL;

    /** 传真电话 */
    @Column(length = 30)
    private String FAX;

    /** 客户英文地址 */
    @Column(length = 200)
    private String EN_ADDR;

    /** 网址 */
    @Column(length = 200)
    private String INTER_SITE;
    public String getCRM_SCOPE() {
		return CRM_SCOPE;
	}

	public void setCRM_SCOPE(String cRM_SCOPE) {
		CRM_SCOPE = cRM_SCOPE;
	}
    @Column(length = 20)
    private String CUST_ID;

    public String getCUST_ID() {
        return CUST_ID;
    }

    public void setCUST_ID(String cUST_ID) {
        CUST_ID = cUST_ID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public String getCUST_EN_NAME() {
        return CUST_EN_NAME;
    }

    public void setCUST_EN_NAME(String cUST_EN_NAME) {
        CUST_EN_NAME = cUST_EN_NAME;
    }

    public String getEN_ABBR() {
        return EN_ABBR;
    }

    public void setEN_ABBR(String eN_ABBR) {
        EN_ABBR = eN_ABBR;
    }

    public String getSYZ_CHAR() {
        return SYZ_CHAR;
    }

    public void setSYZ_CHAR(String sYZ_CHAR) {
        SYZ_CHAR = sYZ_CHAR;
    }

    public String getHY_TYP1() {
        return HY_TYP1;
    }

    public void setHY_TYP1(String hY_TYP1) {
        HY_TYP1 = hY_TYP1;
    }

    public String getHY_TYP2() {
        return HY_TYP2;
    }

    public void setHY_TYP2(String hY_TYP2) {
        HY_TYP2 = hY_TYP2;
    }

    public String getHY_TYP3() {
        return HY_TYP3;
    }

    public void setHY_TYP3(String hY_TYP3) {
        HY_TYP3 = hY_TYP3;
    }

    public String getGROUP_FLG() {
        return GROUP_FLG;
    }

    public void setGROUP_FLG(String gROUP_FLG) {
        GROUP_FLG = gROUP_FLG;
    }

    public String getMARKET_FLG() {
        return MARKET_FLG;
    }

    public void setMARKET_FLG(String mARKET_FLG) {
        MARKET_FLG = mARKET_FLG;
    }

    public String getCUST_BIG_LEV() {
        return CUST_BIG_LEV;
    }

    public void setCUST_BIG_LEV(String cUST_BIG_LEV) {
        CUST_BIG_LEV = cUST_BIG_LEV;
    }

    public String getCUST_SMALL_LEV() {
        return CUST_SMALL_LEV;
    }

    public void setCUST_SMALL_LEV(String cUST_SMALL_LEV) {
        CUST_SMALL_LEV = cUST_SMALL_LEV;
    }

    public String getCUST_LEV() {
        return CUST_LEV;
    }

    public void setCUST_LEV(String cUST_LEV) {
        CUST_LEV = cUST_LEV;
    }

    public String getCRM_CUST_CLASS() {
        return CRM_CUST_CLASS;
    }

    public void setCRM_CUST_CLASS(String cRM_CUST_CLASS) {
        CRM_CUST_CLASS = cRM_CUST_CLASS;
    }

    public String getIMT_BUSINESS() {
        return IMT_BUSINESS;
    }

    public void setIMT_BUSINESS(String iMT_BUSINESS) {
        IMT_BUSINESS = iMT_BUSINESS;
    }

    public String getOFFICE_ZIP() {
        return OFFICE_ZIP;
    }

    public void setOFFICE_ZIP(String oFFICE_ZIP) {
        OFFICE_ZIP = oFFICE_ZIP;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String tEL) {
        TEL = tEL;
    }

    public String getFAX() {
        return FAX;
    }

    public void setFAX(String fAX) {
        FAX = fAX;
    }

    public String getEN_ADDR() {
        return EN_ADDR;
    }

    public void setEN_ADDR(String eN_ADDR) {
        EN_ADDR = eN_ADDR;
    }

    public String getINTER_SITE() {
        return INTER_SITE;
    }

    public void setINTER_SITE(String iNTER_SITE) {
        INTER_SITE = iNTER_SITE;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
