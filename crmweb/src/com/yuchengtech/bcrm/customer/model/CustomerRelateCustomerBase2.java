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
 * 客户归属客户群表
 */
@Entity
@Table(name = "ocrm_f_ci_relate_cust_base")
public class CustomerRelateCustomerBase2 implements Serializable {

	private static final long serialVersionUID = 7146577229726849069L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;

	/** 客户群ID */
	@Column(name = "cust_base_id", length = 38)
	private Long customerBaseId;

	/** 创建人 */
	@Column(name = "rela_create_name", length = 50)
	private String relationCreateName;

	/** 创建日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "rela_create_dt")
	private Date relationCreateDate;

	/** 客户编号 */
	@Column(name = "cust_id", length = 20)
	private String customerId;
	
	/** 客户名称 */
    @Column(name = "CUST_NAME", length = 100)
    private String CUST_NAME;
    
    /** 客户组织机构代码 */
    @Column(name = "CUST_ZZDM", length = 20)
    private String CUST_ZZDM;


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

    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getRelationCreateName() {
		return this.relationCreateName;
	}

	public void setRelationCreateName(String relationCreateName) {
		this.relationCreateName = relationCreateName;
	}

	public Long getCustomerBaseId() {
		return customerBaseId;
	}

	public void setCustomerBaseId(Long customerBaseId) {
		this.customerBaseId = customerBaseId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getRelationCreateDate() {
		return this.relationCreateDate;
	}

	public void setRelationCreateDate(Date relationCreateDate) {
		this.relationCreateDate = relationCreateDate;
	}


}
