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
 * The persistent class for the OCRM_F_MM_RCUST_LIST database table.
 * 名单客户关联表
 */
@Entity
@Table(name="OCRM_F_MM_RCUST_LIST")
public class RollCustomerList implements Serializable {

    private static final long serialVersionUID = 5847631133674776094L;

    /**主键*/
    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    private Long id;

    /**客户ID*/
    @Column(name="CUST_ID",length=100)
    private String customerId;

    /**客户名称*/
    @Column(name="CUST_NAME",length=400)
    private String customerName;
    
    /**组织机构代码*/
    @Column(name="ZZDM",length=10)
    private String zzdm;
    
    /** 创建日期 */
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    private Date createDate;
    
    /** 最近更新日期 */
    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    /** 创建人 */
    @Column(name = "CREATE_USER",length=100)
    private String createUser;
    
    /** 最近更新人 */
    @Column(name = "UPDATE_USER",length=100)
    private String updateUser;

    /**名单ID*/
    @Column(name="ROLL_ID",length=100)
    private String rollId;
    
//  //bi-directional many-to-one association to OcrmFMmRollCust
//    @ManyToOne
//  @JoinColumn(name="ROLL_ID")
//  private RollCustomer rollCustomer;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

//  public RollCustomer getRollCustomer() {
//      return rollCustomer;
//  }
//
//  public void setRollCustomer(RollCustomer rollCustomer) {
//      this.rollCustomer = rollCustomer;
//  }

    public String getRollId() {
        return rollId;
    }

    public void setRollId(String rollId) {
        this.rollId = rollId;
    }
    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    public String getZzdm() {
		return zzdm;
	}

	public void setZzdm(String zzdm) {
		this.zzdm = zzdm;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public static long getSerialversionuid() {
        return serialVersionUID;
    }
}