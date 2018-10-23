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
 * The persistent class for the OCRM_F_CI_MKT_CHANGE_P database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_CHANGE_P")
public class OcrmFCiMktChangeP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_CHANGE_P_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_CHANGE_P_ID_GENERATOR")
	private Long id;

	@Column(name="AREA_ID")
	private String areaId;

	@Column(name="AREA_NAME")
	private String areaName;

	@Column(name="CALL_ID")
	private String callId;

	@Column(name="CHANGE_INFO")
	private String changeInfo;

	@Column(name="CHECK_STAT")
	private String checkStat;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="DEPT_NAME")
	private String deptName;

	@Column(name="IF_FOURTH_STEP")
	private String ifFourthStep;

	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="PRODUCT_ID")
	private String productId;

	@Column(name="PRODUCT_NAME")
	private String productName;

	@Column(name="PRODUCT_CATL")
	private String productCatl;

	@Column(name="RISK_LEVEL")
	private String riskLevel;

	private String rm;

	@Column(name="SALE_AMT")
	private BigDecimal saleAmt;

	@Column(name="USER_ID")
	private String userId;

	 @Temporal( TemporalType.DATE)
		@Column(name="RECORD_DATE")
		private Date recordDate;
	 
	 @Temporal( TemporalType.DATE)
		@Column(name="UPDATE_DATE")
		private Date updateDate;
	 

	 
	 
 public Date getRecordDate() {
		return recordDate;
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

	
    public OcrmFCiMktChangeP() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getChangeInfo() {
		return this.changeInfo;
	}

	public void setChangeInfo(String changeInfo) {
		this.changeInfo = changeInfo;
	}

	public String getCheckStat() {
		return this.checkStat;
	}

	public void setCheckStat(String checkStat) {
		this.checkStat = checkStat;
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

	public String getIfFourthStep() {
		return this.ifFourthStep;
	}

	public void setIfFourthStep(String ifFourthStep) {
		this.ifFourthStep = ifFourthStep;
	}

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
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

	public String getProductCatl() {
		return this.productCatl;
	}

	public void setProductCatl(String productCatl) {
		this.productCatl = productCatl;
	}

	public String getRiskLevel() {
		return this.riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getRm() {
		return this.rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public BigDecimal getSaleAmt() {
		return this.saleAmt;
	}

	public void setSaleAmt(BigDecimal saleAmt) {
		this.saleAmt = saleAmt;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}