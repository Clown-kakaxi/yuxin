package com.yuchengtech.bob.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the OCRM_F_CI_GROUP_INFO database table.
 * 
 * FDM层(基础数据层)，归属于客户集团的第二主题，数据为集团客户的产品信息
 */
@Entity
@Table(name = "OCRM_F_CI_MEMBER_PRODUCT")
public class ClientMemberProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5198799400488146876L;

	@Id
	/**主键*/
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", nullable = false)
	private Long ID;

//	/** 集团客户id */
//	@Column(name = "GROUP_ID", nullable = false, length = 50)
//	private String groupId;

	/** 统计日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "COUNT_DATE", nullable = false)
	private Date countDate;

	/** 客户名称 */
	@Column(name = "CUST_NAME", nullable = false, length = 100)
	private String custName;

	/** 组织机构代码 */
	@Column(name = "CUST_ZZDM", nullable = false, length = 30)
	private String customerOrgNo;

	/** 产品名称 */
	@Column(name = "PRODUCT_NAME", length = 500)
	private String productName;

	/** 产品一级分类 */
	@Column(name = "PRODCUT_CAT_ONE", nullable = false, length = 100)
	private String productCategoryOne;

	/** 时点余额 */
	@Column(name = "BAL_TIME_POINT", nullable = false, precision=24,scale=6)
	private BigDecimal balanceTimePoint;

	/** 日均余额 */
	@Column(name = "BAL_DAY_AVG", nullable = false,precision=24,scale=6)
	private BigDecimal balanceDayAverage;

	/** 发生额 */
	@Column(name = "TRADE_BAL", nullable = false,precision=24,scale=6)
	private BigDecimal tradeBalance;

	/** 利息收入 */
	@Column(name = "INTEREST_INCOME",nullable=false,precision=24,scale=6)
	private BigDecimal interestIncome;

	/** 利息支出 */
	@Column(name = "INTEREST_SPENT", nullable = false,precision=24,scale=6)
	private BigDecimal interestSpent;
	
	@ManyToOne
	@JoinColumn(name="GROUP_ID",referencedColumnName="GROUP_ID",insertable=false,updatable=false)
	private ClientGroupInfo clientGroupInfo;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Date getCountDate() {
		return countDate;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustomerOrgNo() {
		return customerOrgNo;
	}

	public void setCustomerOrgNo(String customerOrgNo) {
		this.customerOrgNo = customerOrgNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategoryOne() {
		return productCategoryOne;
	}

	public void setProductCategoryOne(String productCategoryOne) {
		this.productCategoryOne = productCategoryOne;
	}

	public BigDecimal getBalanceTimePoint() {
		return balanceTimePoint;
	}

	public void setBalanceTimePoint(BigDecimal balanceTimePoint) {
		this.balanceTimePoint = balanceTimePoint;
	}

	public BigDecimal getBalanceDayAverage() {
		return balanceDayAverage;
	}

	public void setBalanceDayAverage(BigDecimal balanceDayAverage) {
		this.balanceDayAverage = balanceDayAverage;
	}

	public BigDecimal getTradeBalance() {
		return tradeBalance;
	}

	public void setTradeBalance(BigDecimal tradeBalance) {
		this.tradeBalance = tradeBalance;
	}

	public BigDecimal getInterestIncome() {
		return interestIncome;
	}

	public void setInterestIncome(BigDecimal interestIncome) {
		this.interestIncome = interestIncome;
	}

	public BigDecimal getInterestSpent() {
		return interestSpent;
	}

	public void setInterestSpent(BigDecimal interestSpent) {
		this.interestSpent = interestSpent;
	}

	public ClientGroupInfo getClientGroupInfo() {
		return clientGroupInfo;
	}

	public void setClientGroupInfo(ClientGroupInfo clientGroupInfo) {
		this.clientGroupInfo = clientGroupInfo;
	}
}