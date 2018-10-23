package com.yuchengtech.bcrm.customer.model;

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
 * 潜在客户表
 */
@Entity
@Table(name = "ACRM_F_CI_LATENT_CUST_INFO")

public class MyPotentialCustomer implements Serializable {
    private static final long serialVersionUID = -3802673719403650829L;

    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    /** 客户ID */
    @Column(name = "CUST_ID", length = 50)
    private String custId;

	/** 客户名称 */
    @Column(name = "CUST_ZH_NAME", length = 50)
    private String custZhName;
    
	/** 客户名称 */
    @Column(name = "CUST_EN_NAME", length = 50)
    private String custEnName;

    /** 组织机构代码 */
    @Column(name = "CUST_ZZDM", length = 50)
    private String custZzdm;

    /** 客户状态（默认潜在） */
    @Column(name = "IS_POTENTIAL", length = 2)
    private String isPotential;

    /** 客户来源渠道 */
    @Column(name = "CHANNEL", length = 50)
    private String channel;

    /** 客户规模 */
    @Column(name = "CUST_SCOPE", length = 50)
    private String custScope;

    /** 行业 */
    @Column(name = "HY_CLASS", length = 50)
    private String hyClass;

    /** 客户联系人 */
    @Column(name = "CIF5LXR1", length = 50)
    private String cif5lxr1;

    /** 联系电话 */
    @Column(name = "CIF5TEL1", length = 20)
    private String cif5tel1;

    /** 客户分配状态 */
    @Column(name = "ASSIGN_STS", length = 2)
    private String assignSts ;
    /** 是否中小企业客户 */
    @Column(name = "CRE_MS_FLG", length = 10)
    private String creMsFlg ;
    
    /** 数据是否为导入 */
    @Column(name = "IS_IMPORT", length = 2)
    private String isImport ;
    /**创建人 */
    @Column(name = "CREATE_NAME", length = 20)
    private String createName;
    /** 创建日期*/
	@Temporal(TemporalType.DATE)
    @Column(name = "CRM_DT")
    private Date crmDt ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustZhName() {
		return custZhName;
	}
	public void setCustZhName(String custZhName) {
		this.custZhName = custZhName;
	}
	public String getCustEnName() {
		return custEnName;
	}
	public void setCustEnName(String custEnName) {
		this.custEnName = custEnName;
	}
	public String getCustZzdm() {
		return custZzdm;
	}
	public void setCustZzdm(String custZzdm) {
		this.custZzdm = custZzdm;
	}
	public String getIsPotential() {
		return isPotential;
	}
	public void setIsPotential(String isPotential) {
		this.isPotential = isPotential;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCustScope() {
		return custScope;
	}
	public void setCustScope(String custScope) {
		this.custScope = custScope;
	}
	public String getHyClass() {
		return hyClass;
	}
	public void setHyClass(String hyClass) {
		this.hyClass = hyClass;
	}
	public String getCif5lxr1() {
		return cif5lxr1;
	}
	public void setCif5lxr1(String cif5lxr1) {
		this.cif5lxr1 = cif5lxr1;
	}
	public String getCif5tel1() {
		return cif5tel1;
	}
	public void setCif5tel1(String cif5tel1) {
		this.cif5tel1 = cif5tel1;
	}
	public String getAssignSts() {
		return assignSts;
	}
	public void setAssignSts(String assignSts) {
		this.assignSts = assignSts;
	}
	public String getCreMsFlg() {
		return creMsFlg;
	}
	public void setCreMsFlg(String creMsFlg) {
		this.creMsFlg = creMsFlg;
	}
	public String getIsImport() {
		return isImport;
	}
	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getCrmDt() {
		return crmDt;
	}
	public void setCrmDt(Date crmDt) {
		this.crmDt = crmDt;
	}
}
