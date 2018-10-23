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
 * 客户间关系表
 */
@Entity
@Table(name = "ocrm_f_ci_relation")
public class CustomerRelation implements Serializable {

    private static final long serialVersionUID = -4382774430367865504L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    private Long id;

    /** 目标客户ID */
    @Column(length = 20)
    private String DEST_CUST_ID;

    /** 目标客户NAME */
    @Column(length = 100)
    private String DEST_CUST_NAME;
    
    /** 目标客户组织机构代码 */
    @Column(length = 10)
    private String DEST_CUST_ZZDM;

    /** 关联客户ID */
    @Column(length = 20)
    private String RELA_CUST_ID;

    /** 关联客户名称 */
    @Column(length = 100)
    private String RELA_CUST_NAME;

    /** 关联客户组织机构代码 */
    @Column(length = 10)
    private String RELA_CUST_ZZDM;

    /** 关系名称 */
    @Column(length = 30)
    private String RELA_NAME;

    /** 关系描述 */
    @Column(length = 250)
    private String RELA_DESC;

    /** 持股比例 */
    @Column(precision = 10, scale = 4)
    private BigDecimal SH_PCT;

    /** 创建人ID */
    @Column(length = 20)
    private String CREATOR;

    /** 创建人姓名 */
    @Column(length = 20)
    private String CREATOR_NAME;

    /** 创建时间 */
    @Temporal(TemporalType.DATE)
    private Date CREAT_DATE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDEST_CUST_NAME() {
        return DEST_CUST_NAME;
    }

    public void setDEST_CUST_NAME(String dEST_CUST_NAME) {
        DEST_CUST_NAME = dEST_CUST_NAME;
    }

    public String getDEST_CUST_ID() {
        return DEST_CUST_ID;
    }

    public void setDEST_CUST_ID(String dEST_CUST_ID) {
        DEST_CUST_ID = dEST_CUST_ID;
    }

    public String getDEST_CUST_ZZDM() {
        return DEST_CUST_ZZDM;
    }

    public void setDEST_CUST_ZZDM(String dEST_CUST_ZZDM) {
        DEST_CUST_ZZDM = dEST_CUST_ZZDM;
    }

    public String getRELA_CUST_ID() {
        return RELA_CUST_ID;
    }

    public void setRELA_CUST_ID(String rELA_CUST_ID) {
        RELA_CUST_ID = rELA_CUST_ID;
    }

    public String getRELA_CUST_NAME() {
        return RELA_CUST_NAME;
    }

    public void setRELA_CUST_NAME(String rELA_CUST_NAME) {
        RELA_CUST_NAME = rELA_CUST_NAME;
    }

    public String getRELA_CUST_ZZDM() {
        return RELA_CUST_ZZDM;
    }

    public void setRELA_CUST_ZZDM(String rELA_CUST_ZZDM) {
        RELA_CUST_ZZDM = rELA_CUST_ZZDM;
    }

    public String getRELA_NAME() {
        return RELA_NAME;
    }

    public void setRELA_NAME(String rELA_NAME) {
        RELA_NAME = rELA_NAME;
    }

    public String getRELA_DESC() {
        return RELA_DESC;
    }

    public void setRELA_DESC(String rELA_DESC) {
        RELA_DESC = rELA_DESC;
    }

    public BigDecimal getSH_PCT() {
        return SH_PCT;
    }

    public void setSH_PCT(BigDecimal sH_PCT) {
        SH_PCT = sH_PCT;
    }

    public String getCREATOR() {
        return CREATOR;
    }

    public void setCREATOR(String cREATOR) {
        CREATOR = cREATOR;
    }

    public String getCREATOR_NAME() {
        return CREATOR_NAME;
    }

    public void setCREATOR_NAME(String cREATOR_NAME) {
        CREATOR_NAME = cREATOR_NAME;
    }

    public Date getCREAT_DATE() {
        return CREAT_DATE;
    }

    public void setCREAT_DATE(Date cREAT_DATE) {
        CREAT_DATE = cREAT_DATE;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
