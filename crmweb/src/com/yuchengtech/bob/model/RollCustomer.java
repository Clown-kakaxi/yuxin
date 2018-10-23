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
 * The persistent class for the OCRM_F_MM_ROLL_CUST database table. 
 * 名单制客户管理表
 */
@Entity
@Table(name = "OCRM_F_MM_ROLL_CUST")
public class RollCustomer implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8106233109593149465L;

	/** 名单ID */
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ROLL_ID")
	private Long rollId;

	/** 创建日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE")
	private Date createDate;

	/** 创建人 */
	@Column(name = "CREATE_USER",length=100)
	private String createUser;
	
	/** 更新日期 */
    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    /** 更新人 */
    @Column(name = "UPDATE_USER",length=100)
    private String updateUser;
    
    /** 名单分类 */
    @Column(name = "ROLL_KIND",length=100)
    private String rollKind;
    
    /** 名单类型 */
    @Column(name = "ROLL_TYPE",length=100)
    private String rollType;

	/** 名单说明 */
	@Column(name = "ROLL_DESC",length=600)
	private String rollDescribe;

	/** 名单有效期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ROLL_EXP_DATE")
	private Date rollExpectDate;

	/** 名单名称 */
	@Column(name = "ROLL_NAME",length=200)
	private String rollName;
	
//	// bi-directional many-to-one association to OcrmFMmRcustList
//	@OneToMany(mappedBy = "rollCustomer")
//	private Set<RollCustomerList> relationCustList;


	public Long getRollId() {
		return rollId;
	}

	public void setRollId(Long rollId) {
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

	public String getRollKind() {
		return rollKind;
	}

	public void setRollKind(String rollKind) {
		this.rollKind = rollKind;
	}

	public String getRollType() {
		return rollType;
	}

	public void setRollType(String rollType) {
		this.rollType = rollType;
	}

	public String getRollDescribe() {
		return rollDescribe;
	}

	public void setRollDescribe(String rollDescribe) {
		this.rollDescribe = rollDescribe;
	}

	public Date getRollExpectDate() {
		return rollExpectDate;
	}

	public void setRollExpectDate(Date rollExpectDate) {
		this.rollExpectDate = rollExpectDate;
	}

	public String getRollName() {
		return rollName;
	}

	public void setRollName(String rollName) {
		this.rollName = rollName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}