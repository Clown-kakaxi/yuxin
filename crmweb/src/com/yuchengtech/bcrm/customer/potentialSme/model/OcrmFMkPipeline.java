package com.yuchengtech.bcrm.customer.potentialSme.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the OCRM_F_MK_PIPELINE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MK_PIPELINE")
public class OcrmFMkPipeline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private long id;
	
	@Column(name="PIPELINE_ID")
	private long pipelineId;

	@Column(name="APPLY_AMT")
	private BigDecimal applyAmt;

	@Column(name="APPLY_CURRENCY")
	private String applyCurrency;

    @Temporal( TemporalType.DATE)
	@Column(name="APPLY_DATE")
	private Date applyDate;

	@Column(name="AREA_ID")
	private String areaId;

	@Column(name="AREA_NAME")
	private String areaName;

	@Column(name="BUY_NAME")
	private String buyName;

    @Temporal( TemporalType.DATE)
	@Column(name="CA_DATE_R")
	private Date caDateR;

    @Temporal( TemporalType.DATE)
	@Column(name="CA_DUE")
	private Date caDue;

	@Column(name="CA_NUMBER")
	private String caNumber;

	@Column(name="CA_SP_STATE")
	private String caSpState;

    @Temporal( TemporalType.DATE)
	@Column(name="CA_VALIDITY")
	private Date caValidity;

	@Column(name="CASE_STATE")
	private String caseState;

	@Column(name="CASE_TYPE")
	private String caseType;

	private String co;

	@Column(name="COMP_TYPE")
	private String compType;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_PROPERTIES")
	private String custProperties;

	@Column(name="CUST_TYPE")
	private String custType;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="DEPT_NAME")
	private String deptName;

	@Column(name="EXPECT_M_AMT")
	private BigDecimal expectMAmt;

    @Temporal( TemporalType.DATE)
	@Column(name="EXPECT_M_DATE")
	private Date expectMDate;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FIRST_IN_DATE")
	private Date firstInDate;

	@Column(name="FIRST_M_AMT")
	private BigDecimal firstMAmt;

	@Column(name="FIRST_M_CURRENCY")
	private String firstMCurrency;

    @Temporal( TemporalType.DATE)
	@Column(name="FIRST_M_DATE")
	private Date firstMDate;

	@Column(name="GROUP_ID")
	private String groupId;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name="IF_MUNI")
	private String ifMuni;

	@Column(name="IF_NEXT")
	private String ifNext;

	@Column(name="IF_SAME")
	private String ifSame;

	@Column(name="IF_TAIWAN")
	private String ifTaiwan;

	@Column(name="INSURE_AMT")
	private BigDecimal insureAmt;
	
	@Column(name="INSURE_CURRENCY")
	private String insureCurrency;

    @Temporal( TemporalType.DATE)
	@Column(name="INSURE_DATE")
	private Date insureDate;

	@Column(name="INSURE_MONEY")
	private BigDecimal insureMoney;

	private String memo;

	@Column(name="NEW_HAS_CUST")
	private String newHasCust;

	@Column(name="NOW_PROGRESS")
	private String nowProgress;

	@Column(name="PRODUCT_FORM")
	private String productForm;

	@Column(name="PRODUCT_SUBJECT")
	private String productSubject;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Date createDate;

	private String rm;

	@Column(name="RM_ID")
	private String rmId;

    @Temporal( TemporalType.DATE)
	@Column(name="SUB_CASE_DATE")
	private Date subCaseDate;

	@Column(name="SURPLUS_M_AMT")
	private BigDecimal surplusMAmt;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

    @Column(name="UPDATER")
	private String updater;

	@Column(name="CREATER")
	private String creater;

    @Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;
    
    @Temporal( TemporalType.DATE)
	@Column(name="RECEIVE_DATE")
	private Date receiveDate;

    public OcrmFMkPipeline() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPipelineId() {
		return this.pipelineId;
	}

	public void setPipelineId(long pipelineId) {
		this.pipelineId = pipelineId;
	}

	public BigDecimal getApplyAmt() {
		return this.applyAmt;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}

	public String getApplyCurrency() {
		return this.applyCurrency;
	}

	public void setApplyCurrency(String applyCurrency) {
		this.applyCurrency = applyCurrency;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
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

	public String getBuyName() {
		return this.buyName;
	}

	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}

	public Date getCaDateR() {
		return this.caDateR;
	}

	public void setCaDateR(Date caDateR) {
		this.caDateR = caDateR;
	}

	public Date getCaDue() {
		return this.caDue;
	}

	public void setCaDue(Date caDue) {
		this.caDue = caDue;
	}

	public String getCaNumber() {
		return this.caNumber;
	}

	public void setCaNumber(String caNumber) {
		this.caNumber = caNumber;
	}

	public String getCaSpState() {
		return this.caSpState;
	}

	public void setCaSpState(String caSpState) {
		this.caSpState = caSpState;
	}

	public Date getCaValidity() {
		return this.caValidity;
	}

	public void setCaValidity(Date caValidity) {
		this.caValidity = caValidity;
	}

	public String getCaseState() {
		return this.caseState;
	}

	public void setCaseState(String caseState) {
		this.caseState = caseState;
	}

	public String getCaseType() {
		return this.caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCo() {
		return this.co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getCompType() {
		return this.compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
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

	public String getCustProperties() {
		return this.custProperties;
	}

	public void setCustProperties(String custProperties) {
		this.custProperties = custProperties;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
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

	public BigDecimal getExpectMAmt() {
		return this.expectMAmt;
	}

	public void setExpectMAmt(BigDecimal expectMAmt) {
		this.expectMAmt = expectMAmt;
	}

	public Date getExpectMDate() {
		return this.expectMDate;
	}

	public void setExpectMDate(Date expectMDate) {
		this.expectMDate = expectMDate;
	}

	public Date getFirstInDate() {
		return this.firstInDate;
	}

	public void setFirstInDate(Date firstInDate) {
		this.firstInDate = firstInDate;
	}

	public BigDecimal getFirstMAmt() {
		return this.firstMAmt;
	}

	public void setFirstMAmt(BigDecimal firstMAmt) {
		this.firstMAmt = firstMAmt;
	}

	public String getFirstMCurrency() {
		return this.firstMCurrency;
	}

	public void setFirstMCurrency(String firstMCurrency) {
		this.firstMCurrency = firstMCurrency;
	}

	public Date getFirstMDate() {
		return this.firstMDate;
	}

	public void setFirstMDate(Date firstMDate) {
		this.firstMDate = firstMDate;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIfMuni() {
		return this.ifMuni;
	}

	public void setIfMuni(String ifMuni) {
		this.ifMuni = ifMuni;
	}

	public String getIfNext() {
		return this.ifNext;
	}

	public void setIfNext(String ifNext) {
		this.ifNext = ifNext;
	}

	public String getIfSame() {
		return this.ifSame;
	}

	public void setIfSame(String ifSame) {
		this.ifSame = ifSame;
	}

	public String getIfTaiwan() {
		return this.ifTaiwan;
	}

	public void setIfTaiwan(String ifTaiwan) {
		this.ifTaiwan = ifTaiwan;
	}

	public BigDecimal getInsureAmt() {
		return this.insureAmt;
	}

	public void setInsureAmt(BigDecimal insureAmt) {
		this.insureAmt = insureAmt;
	}

	public String getInsureCurrency() {
		return this.insureCurrency;
	}

	public void setInsureCurrency(String insureCurrency) {
		this.insureCurrency = insureCurrency;
	}

	public Date getInsureDate() {
		return this.insureDate;
	}

	public void setInsureDate(Date insureDate) {
		this.insureDate = insureDate;
	}

	public BigDecimal getInsureMoney() {
		return this.insureMoney;
	}

	public void setInsureMoney(BigDecimal insureMoney) {
		this.insureMoney = insureMoney;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNewHasCust() {
		return this.newHasCust;
	}

	public void setNewHasCust(String newHasCust) {
		this.newHasCust = newHasCust;
	}

	public String getNowProgress() {
		return this.nowProgress;
	}

	public void setNowProgress(String nowProgress) {
		this.nowProgress = nowProgress;
	}

	public String getProductForm() {
		return this.productForm;
	}

	public void setProductForm(String productForm) {
		this.productForm = productForm;
	}

	public String getProductSubject() {
		return this.productSubject;
	}

	public void setProductSubject(String productSubject) {
		this.productSubject = productSubject;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRm() {
		return this.rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getRmId() {
		return this.rmId;
	}

	public void setRmId(String rmId) {
		this.rmId = rmId;
	}

	public Date getSubCaseDate() {
		return this.subCaseDate;
	}

	public void setSubCaseDate(Date subCaseDate) {
		this.subCaseDate = subCaseDate;
	}

	public BigDecimal getSurplusMAmt() {
		return this.surplusMAmt;
	}

	public void setSurplusMAmt(BigDecimal surplusMAmt) {
		this.surplusMAmt = surplusMAmt;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdater() {
		return this.updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
}