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
 * 客户群信息表
 */
@Entity
@Table(name = "ocrm_f_ci_base")
public class CustomerBase2 implements Serializable {

	private static final long serialVersionUID = -3071512732613148823L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;

	/** 客户群编号 */
	@Column(name = "cust_base_number", length = 20)
	private String customerBaseNumber;

	/** 客户群名称 */
	@Column(name = "cust_base_name", length = 50)
	private String customerBaseName;

	/** 客户群创建日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "cust_base_create_date")
	private Date customerBaseCreateDate;

	/** 客户群成员数 */
	@Column(name = "cust_base_member_num")
	private int customerBaseMemberNum;

	/** 客户群描述 */
	@Column(name = "cust_base_desc", length = 360)
	private String customerBaseDescribe;

	/** 客户群创建人 */
	@Column(name = "cust_base_create_name", length = 30)
	private String cust_base_create_name;
    
	/**共享范围*/
	@Column(name = "share_flag", length = 10)
	private String shareFlag;
	
	/**创建机构*/
	@Column(name = "cust_base_create_org", length = 20)
	private String custBaseCreateOrg;
	
	/**客户来源标识*/
	@Column(name = "CUST_FROM", length = 20)
	private String custFrom;
	
	/**客户来源展现*/
	@Column(name = "CUST_FROM_NAME", length = 20)
	private String custFromName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerBaseNumber() {
		return customerBaseNumber;
	}

	public void setCustomerBaseNumber(String customerBaseNumber) {
		this.customerBaseNumber = customerBaseNumber;
	}

	public String getCustomerBaseName() {
		return customerBaseName;
	}

	public void setCustomerBaseName(String customerBaseName) {
		this.customerBaseName = customerBaseName;
	}

	public Date getCustomerBaseCreateDate() {
		return customerBaseCreateDate;
	}

	public void setCustomerBaseCreateDate(Date customerBaseCreateDate) {
		this.customerBaseCreateDate = customerBaseCreateDate;
	}

	public int getCustomerBaseMemberNum() {
		return customerBaseMemberNum;
	}

	public void setCustomerBaseMemberNum(int customerBaseMemberNum) {
		this.customerBaseMemberNum = customerBaseMemberNum;
	}

	public String getCustomerBaseDescribe() {
		return customerBaseDescribe;
	}

	public void setCustomerBaseDescribe(String customerBaseDescribe) {
		this.customerBaseDescribe = customerBaseDescribe;
	}

	public String getCust_base_create_name() {
		return cust_base_create_name;
	}

	public void setCust_base_create_name(String cust_base_create_name) {
		this.cust_base_create_name = cust_base_create_name;
	}

	public String getShareFlag() {
		return shareFlag;
	}

	public void setShareFlag(String shareFlag) {
		this.shareFlag = shareFlag;
	}

	public String getCustBaseCreateOrg() {
		return custBaseCreateOrg;
	}

	public void setCustBaseCreateOrg(String custBaseCreateOrg) {
		this.custBaseCreateOrg = custBaseCreateOrg;
	}

	public String getCustFrom() {
		return custFrom;
	}

	public void setCustFrom(String custFrom) {
		this.custFrom = custFrom;
	}

	public String getCustFromName() {
		return custFromName;
	}

	public void setCustFromName(String custFromName) {
		this.custFromName = custFromName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
    

}
