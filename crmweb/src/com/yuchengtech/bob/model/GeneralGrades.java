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
 * Entity implementation class for Entity: CustomerLoanPriceControl
 * 收益打分卡
 */
@Entity
@Table(name = "ocrm_f_ci_general_grades")
public class GeneralGrades implements Serializable {

    private static final long serialVersionUID = -4862998134567715871L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long ID;

    /** 申请日期 */
    @Temporal(TemporalType.DATE)
    private Date APPLY_DATE;

    /** 所属机构名称 */
    @Column(length = 100)
    private String ORG_NAME;
    
    /** 所属机构代码 */
    @Column(length = 100)
    private String BELONG_INSTN;

    /** 所属分行管理部 */
    @Column(length = 100)
    private String LOCAL_ORG_NAME;

    /** 打分卡用途 */
    @Column(length = 200)
    private String CARD_USE;
    
    /** 申请简介 */
    @Column(length = 200)
    private String APPLY_INTRODUCTION;
    
    /** 填表人 */
    @Column(length = 100)
    private String FILLER_NAME;

    /** 申请备注 */
    @Column(length = 200)
    private String APPLY_COMMENT;

    /** 分行管理部意见 */
    @Column(length = 200)
    private String LOCAL_ORG_OPINION;

    /** 总行意见 */
    @Column(length = 200)
    private String CENTRAL_ORG_OPINION;

    /** 客户名称 */
    @Column(length = 100)
    private String CUST_ZH_NAME;

    /** 组织机构代码 */
    @Column(length = 100)
    private String CUST_ZZDM;

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

	public String getBELONG_INSTN() {
		return BELONG_INSTN;
	}

	public void setBELONG_INSTN(String bELONG_INSTN) {
		BELONG_INSTN = bELONG_INSTN;
	}

	public String getLOCAL_ORG_NAME() {
		return LOCAL_ORG_NAME;
	}

	public void setLOCAL_ORG_NAME(String lOCAL_ORG_NAME) {
		LOCAL_ORG_NAME = lOCAL_ORG_NAME;
	}

	public String getCARD_USE() {
		return CARD_USE;
	}

	public void setCARD_USE(String cARD_USE) {
		CARD_USE = cARD_USE;
	}

	public String getAPPLY_INTRODUCTION() {
		return APPLY_INTRODUCTION;
	}

	public void setAPPLY_INTRODUCTION(String aPPLY_INTRODUCTION) {
		APPLY_INTRODUCTION = aPPLY_INTRODUCTION;
	}

	public String getFILLER_NAME() {
		return FILLER_NAME;
	}

	public void setFILLER_NAME(String fILLER_NAME) {
		FILLER_NAME = fILLER_NAME;
	}

	public String getAPPLY_COMMENT() {
		return APPLY_COMMENT;
	}

	public void setAPPLY_COMMENT(String aPPLY_COMMENT) {
		APPLY_COMMENT = aPPLY_COMMENT;
	}

	public String getLOCAL_ORG_OPINION() {
		return LOCAL_ORG_OPINION;
	}

	public void setLOCAL_ORG_OPINION(String lOCAL_ORG_OPINION) {
		LOCAL_ORG_OPINION = lOCAL_ORG_OPINION;
	}

	public String getCENTRAL_ORG_OPINION() {
		return CENTRAL_ORG_OPINION;
	}

	public void setCENTRAL_ORG_OPINION(String cENTRAL_ORG_OPINION) {
		CENTRAL_ORG_OPINION = cENTRAL_ORG_OPINION;
	}

	public String getCUST_ZH_NAME() {
		return CUST_ZH_NAME;
	}

	public void setCUST_ZH_NAME(String cUST_ZH_NAME) {
		CUST_ZH_NAME = cUST_ZH_NAME;
	}

	public String getCUST_ZZDM() {
		return CUST_ZZDM;
	}

	public void setCUST_ZZDM(String cUST_ZZDM) {
		CUST_ZZDM = cUST_ZZDM;
	}



}
