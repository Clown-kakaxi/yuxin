package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CI_MKT_END_P database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_END_P")
public class OcrmFCiMktEndP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_END_P_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_END_P_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="ACCOUNT_DATE")
	private Date accountDate;

	@Column(name="AREA_ID")
	private String areaId;

	@Column(name="AREA_NAME")
	private String areaName;

	@Column(name="BUY_AMT")
	private BigDecimal buyAmt;

	@Column(name="CALL_ID")
	private String callId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

    @Temporal( TemporalType.DATE)
	@Column(name="DEAL_DATE")
	private Date dealDate;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="DEPT_NAME")
	private String deptName;

	@Column(name="IF_DEAL")
	private String ifDeal;

	@Column(name="IF_HAS_PROD")
	private String ifHasProd;

	@Column(name="PLAN_CHANGE_ID")
	private String planChangeId;

	@Column(name="PRODUCT_ID")
	private String productId;

	@Column(name="PRODUCT_NAME")
	private String productName;

	@Column(name="REFUSE_REASON")
	private String refuseReason;

	private String rm;

	@Column(name="USER_ID")
	private String userId;
	
	 @Temporal( TemporalType.DATE)
		@Column(name="RECORD_DATE")
		private Date recordDate;
	 
	 @Temporal( TemporalType.DATE)
		@Column(name="UPDATE_DATE")
		private Date updateDate;
	 
	 @Column(name="CHECK_STAT")
		private String checkStat;
	 
	 

	 public Date getRecordDate() {
		return recordDate;
	}

	public String getCheckStat() {
		return checkStat;
	}

	public void setCheckStat(String checkStat) {
		this.checkStat = checkStat;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


    public OcrmFCiMktEndP() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAccountDate() {
		return this.accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public BigDecimal getBuyAmt() {
		return this.buyAmt;
	}

	public void setBuyAmt(BigDecimal buyAmt) {
		this.buyAmt = buyAmt;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getDealDate() {
		return this.dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getIfDeal() {
		return this.ifDeal;
	}

	public void setIfDeal(String ifDeal) {
		this.ifDeal = ifDeal;
	}

	public String getIfHasProd() {
		return this.ifHasProd;
	}

	public void setIfHasProd(String ifHasProd) {
		this.ifHasProd = ifHasProd;
	}

	public String getPlanChangeId() {
		return this.planChangeId;
	}

	public void setPlanChangeId(String planChangeId) {
		this.planChangeId = planChangeId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRefuseReason() {
		return this.refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getRm() {
		return this.rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}