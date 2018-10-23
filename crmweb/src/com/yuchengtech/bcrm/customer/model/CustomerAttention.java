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
 * 关注客户信息
 */
@Entity
@Table(name = "ocrm_f_ci_attention")
public class CustomerAttention implements Serializable {

	private static final long serialVersionUID = 2057403964492006610L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_dt")
	private Date createDate;// 建立日期

	@Temporal(TemporalType.DATE)
	@Column(name = "crm_dt")
	private Date crmDate;// 平台日期

	@Column(name = "cust_id", length = 20)
	private String customerId;// 客户编号

	@Column(name = "cust_lev", length = 4)
	private String customerLevel;// 客户级别

	@Column(name = "cust_name", length = 100)
	private String customerName;// 客户名称

	@Column(name = "cust_typ", length = 4)
	private String customerType;// 客户类型

	@Column(name = "cust_zzdm", length = 10)
	private String customerOrgNo;// 组织机构代码

	@Temporal(TemporalType.DATE)
	@Column(name = "end_dt")
	private Date endDate;// 取消日期

	@Column(name = "imtcust_flg", length = 4)
	private String importantCustomerFlag;// 是否关注客户

	@Column(name = "user_id", length = 30)
	private String userId;// 客户经理编号

	@Column(name = "user_name", length = 100)
	private String userName;// 客户经理名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCrmDate() {
		return crmDate;
	}

	public void setCrmDate(Date crmDate) {
		this.crmDate = crmDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerOrgNo() {
		return customerOrgNo;
	}

	public void setCustomerOrgNo(String customerOrgNo) {
		this.customerOrgNo = customerOrgNo;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getImportantCustomerFlag() {
		return importantCustomerFlag;
	}

	public void setImportantCustomerFlag(String importantCustomerFlag) {
		this.importantCustomerFlag = importantCustomerFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}