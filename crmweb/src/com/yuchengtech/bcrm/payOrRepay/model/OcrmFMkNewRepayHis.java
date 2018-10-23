package com.yuchengtech.bcrm.payOrRepay.model;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the OCRM_F_MK_NEW_REPAY database table.
 * 新拨及还款历史信息
 */
@Entity
@Table(name="OCRM_F_MK_NEW_REPAY_HIS")
public class OcrmFMkNewRepayHis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="ID")
	private Long id;
  
	/**
	 * 新波及还款历史字段
	 */

	/*
	 * 区域及分支行
	 */
	@Column(name="REGION")
	private String region;
	
	@Column(name="ORG_ID")
	private String orgId;
	/**
	 * 客户信息
	 */
	@Column(name="CUST_TYPE")
	private String custType;
	@Column(name="IF_RE")
	private String ifRe;
	@Column(name="CUST_ID")
	private String custId;
	@Column(name="CUST_NAME")
	private String custName;
	@Column(name="PAY_OR_REPAY")
	private String payOrRepay;
	
	@Column(name="INDUST_TYPE")
	private String industType;
	@Column(name="IF_PASS")
	private String ifPass;
	@Column(name="LOAN_TYPE")
	private String loanType;
	@Column(name="CURRENCY")
	private String currency;
	@Column(name="AMOUNT")
	private BigDecimal amount;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="ESTIMATE_DATE")
	private Date estimateDate;
	@Column(name="REPAY_REASON")
	private String repayReason;
	@Column(name="PROGRESS")
	private String progress;
	@Column(name="IF_REAL")
	private String ifReal;
	@Column(name="PROBABILITY")
	private String probability;
	@Column(name="FLOAT_OR_FIXED_IR")
	private String floatOrFixedIr;
	@Column(name="INTEREST_RATE")
	private String interestRate;
	@Column(name="DISCOUNT_OCCUR_AMT")
	private BigDecimal discountOccurAmt;
	@Column(name="IF_TAIWANBUSINESS")
	private String ifTaiwanbusiness;
	@Column(name="CUS_NATURE")
	private String cusNature;
	/**
	 * 审批状态
	 */
	@Column(name="APPROVE_STATE")
	private String approveState;
	/**
	 * 创建信息与更新信息
	 */
	@Column(name="CREATE_USER")
	private String createUser;
	@Column(name="CREATE_ORG")
	private String createOrg;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Date createDate;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_ORG")
	private String lastUpdateOrg;
	

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;
	/**
	 *同步日期
	 */

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="ETL_DATE")
	private Date etlDate;
	
	public OcrmFMkNewRepayHis() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/*
	 * 区域
	 */
    public String getREGION() {
		return region;
	}
	public void setREGION(String region) {
		this.region = region;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getIfRe() {
		return this.ifRe;
	}
	public void setIfRe(String ifRe) {
		this.ifRe = ifRe;
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

	public String getPayOrRepay() {
		return this.payOrRepay;
	}
	public void setPayOrRepay(String payOrRepay) {
		this.payOrRepay = payOrRepay;
	}

	public String getOrgId() {
		return this.orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getIndustType() {
		return this.industType;
	}
	public void setIndustType(String industType) {
		this.industType = industType;
	}

	public String getIfPass() {
		return this.ifPass;
	}
	public void setIfPass(String ifPass) {
		this.ifPass = ifPass;
	}
	
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getEstimateDate() {
		return this.estimateDate;
	}
	public void setVisitDate(Date estimateDate) {
		this.estimateDate = estimateDate;
	}

	public String getRepayReason() {
		return this.repayReason;
	}
	public void setRepayReason(String repayReason) {
		this.repayReason = repayReason;
	}
	
	public String getProgress() {
		return this.progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getIfReal() {
		return this.ifReal;
	}
	public void setIfReal(String ifReal) {
		this.ifReal = ifReal;
	}
	
	public String getProbability() {
		return this.probability;
	}
	public void setProbability(String probability) {
		this.probability = probability;
	}
	
	public String getFloatOrFixedIr() {
		return this.floatOrFixedIr;
	}
	public void setFloatOrFixedIr(String floatOrFixedIr) {
		this.floatOrFixedIr = floatOrFixedIr;
	}
	
	public String getInterestRate() {
		return this.interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	
	public BigDecimal getDiscountOccurAmt() {
		return this.discountOccurAmt;
	}
	public void setDiscountOccurAmt(BigDecimal discountOccurAmt) {
		this.discountOccurAmt = discountOccurAmt;
	}
	
	public String getIfTaiwanbusiness() {
		return this.ifTaiwanbusiness;
	}
	public void setIfTaiwanbusiness(String ifTaiwanbusiness) {
		this.ifTaiwanbusiness = ifTaiwanbusiness;
	}
	
	public String getCusNature() {
		return this.cusNature;
	}
	public void setCusNature(String cusNature) {
		this.cusNature = cusNature;
	}
	
	public String getApproveState() {
		return this.approveState;
	}
	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}
	
	public String getCreateUser() {
		return this.createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getCreateOrg() {
		return this.createOrg;
	}
	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	public String getLastUpdateOrg() {
		return this.lastUpdateOrg;
	}
	public void setLastUpdateOrg(String lastUpdateOrg) {
		this.lastUpdateOrg = lastUpdateOrg;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}
	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}
	
	public Date getEtlDate() {
		return this.etlDate;
	}
	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}
}