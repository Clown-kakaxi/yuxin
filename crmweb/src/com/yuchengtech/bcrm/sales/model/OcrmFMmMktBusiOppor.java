package com.yuchengtech.bcrm.sales.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
 * The persistent class for the OCRM_F_MM_MKT_BUSI_OPPOR database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MM_MKT_BUSI_OPPOR")
public class OcrmFMmMktBusiOppor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MM_MKT_BUSI_OPPOR_OPPOR_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MM_MKT_BUSI_OPPOR_OPPOR_ID_GENERATOR")
	@Column(name="OPPOR_ID")
	private String opporId;

	@Column(name="ASSIGN_OGR_ID")
	private String assignOgrId;

	@Column(name="ASSIGN_ORG_NAME")
	private String assignOrgName;

	@Column(name="CLAIM_ORG_ID")
	private String claimOrgId;

	@Column(name="CLAIM_ORG_NAME")
	private String claimOrgName;

	@Column(name="CLAIM_USER_ID")
	private String claimUserId;

	@Column(name="CLAIM_USER_NAME")
	private String claimUserName;

	@Column(name="CREATE_DATE_TIME")
	private Timestamp createDateTime;

	@Column(name="CREATE_ORG_ID")
	private String createOrgId;

	@Column(name="CREATE_ORG_NAME")
	private String createOrgName;

	@Column(name="CREATER_ID")
	private String createrId;

	@Column(name="CREATER_NAME")
	private String createrName;

	@Column(name="CUST_CATEGORY")
	private String custCategory;

	@Column(name="CUST_CONTACT_NAME")
	private String custContactName;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_TYPE")
	private String custType;

	@Column(name="EXECUTE_ORG_ID")
	private String executeOrgId;

	@Column(name="EXECUTE_ORG_NAME")
	private String executeOrgName;

	@Column(name="EXECUTE_USER_ID")
	private String executeUserId;

	@Column(name="EXECUTE_USER_NAME")
	private String executeUserName;

	private String memo;

	@Column(name="MKT_ACTIV_ID")
	private String mktActivId;

	@Column(name="MKT_ACTIV_NAME")
	private String mktActivName;

	@Column(name="MKT_TARGET_ID")
	private String mktTargetId;

	@Column(name="MKT_TARGET_NAME")
	private String mktTargetName;

	@Column(name="OPPOR_CONTENT")
	private String opporContent;

	@Temporal(TemporalType.DATE)
	@Column(name="OPPOR_DUE_DATE")
	private Date opporDueDate;

	@Temporal(TemporalType.DATE)
	@Column(name="OPPOR_END_DATE")
	private Date opporEndDate;

	@Column(name="OPPOR_NAME")
	private String opporName;

	@Column(name="OPPOR_SOURCE")
	private String opporSource;

	@Column(name="OPPOR_STAGE")
	private String opporStage;

	@Temporal(TemporalType.DATE)
	@Column(name="OPPOR_START_DATE")
	private Date opporStartDate;

	@Column(name="OPPOR_STAT")
	private String opporStat;

	@Column(name="OPPOR_TYPE")
	private String opporType;

	@Column(name="PLAN_AMOUNT")
	private BigDecimal planAmount;

	@Column(name="PLAN_COST")
	private BigDecimal planCost;

	@Column(name="PROD_ID")
	private String prodId;

	@Column(name="PROD_NAME")
	private String prodName;

	@Column(name="REACH_AMOUNT")
	private BigDecimal reachAmount;

	@Column(name="REACH_PROB")
	private String reachProb;

	@Column(name="REL_TRAD")
	private String relTrad;

	@Column(name="UPDATE_DATE_TIME")
	private Timestamp updateDateTime;

	@Column(name="UPDATE_ORG_ID")
	private String updateOrgId;

	@Column(name="UPDATE_ORG_NAME")
	private String updateOrgName;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	@Column(name="UPDATE_USER_NAME")
	private String updateUserName;

	public OcrmFMmMktBusiOppor() {
	}

	public String getOpporId() {
		return this.opporId;
	}

	public void setOpporId(String opporId) {
		this.opporId = opporId;
	}

	public String getAssignOgrId() {
		return this.assignOgrId;
	}

	public void setAssignOgrId(String assignOgrId) {
		this.assignOgrId = assignOgrId;
	}

	public String getAssignOrgName() {
		return this.assignOrgName;
	}

	public void setAssignOrgName(String assignOrgName) {
		this.assignOrgName = assignOrgName;
	}

	public String getClaimOrgId() {
		return this.claimOrgId;
	}

	public void setClaimOrgId(String claimOrgId) {
		this.claimOrgId = claimOrgId;
	}

	public String getClaimOrgName() {
		return this.claimOrgName;
	}

	public void setClaimOrgName(String claimOrgName) {
		this.claimOrgName = claimOrgName;
	}

	public String getClaimUserId() {
		return this.claimUserId;
	}

	public void setClaimUserId(String claimUserId) {
		this.claimUserId = claimUserId;
	}

	public String getClaimUserName() {
		return this.claimUserName;
	}

	public void setClaimUserName(String claimUserName) {
		this.claimUserName = claimUserName;
	}

	public Timestamp getCreateDateTime() {
		return this.createDateTime;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getCreateOrgId() {
		return this.createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	public String getCreateOrgName() {
		return this.createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreaterId() {
		return this.createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getCreaterName() {
		return this.createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getCustCategory() {
		return this.custCategory;
	}

	public void setCustCategory(String custCategory) {
		this.custCategory = custCategory;
	}

	public String getCustContactName() {
		return this.custContactName;
	}

	public void setCustContactName(String custContactName) {
		this.custContactName = custContactName;
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

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getExecuteOrgId() {
		return this.executeOrgId;
	}

	public void setExecuteOrgId(String executeOrgId) {
		this.executeOrgId = executeOrgId;
	}

	public String getExecuteOrgName() {
		return this.executeOrgName;
	}

	public void setExecuteOrgName(String executeOrgName) {
		this.executeOrgName = executeOrgName;
	}

	public String getExecuteUserId() {
		return this.executeUserId;
	}

	public void setExecuteUserId(String executeUserId) {
		this.executeUserId = executeUserId;
	}

	public String getExecuteUserName() {
		return this.executeUserName;
	}

	public void setExecuteUserName(String executeUserName) {
		this.executeUserName = executeUserName;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMktActivId() {
		return this.mktActivId;
	}

	public void setMktActivId(String mktActivId) {
		this.mktActivId = mktActivId;
	}

	public String getMktActivName() {
		return this.mktActivName;
	}

	public void setMktActivName(String mktActivName) {
		this.mktActivName = mktActivName;
	}

	public String getMktTargetId() {
		return this.mktTargetId;
	}

	public void setMktTargetId(String mktTargetId) {
		this.mktTargetId = mktTargetId;
	}

	public String getMktTargetName() {
		return this.mktTargetName;
	}

	public void setMktTargetName(String mktTargetName) {
		this.mktTargetName = mktTargetName;
	}

	public String getOpporContent() {
		return this.opporContent;
	}

	public void setOpporContent(String opporContent) {
		this.opporContent = opporContent;
	}

	public Date getOpporDueDate() {
		return this.opporDueDate;
	}

	public void setOpporDueDate(Date opporDueDate) {
		this.opporDueDate = opporDueDate;
	}

	public Date getOpporEndDate() {
		return this.opporEndDate;
	}

	public void setOpporEndDate(Date opporEndDate) {
		this.opporEndDate = opporEndDate;
	}

	public String getOpporName() {
		return this.opporName;
	}

	public void setOpporName(String opporName) {
		this.opporName = opporName;
	}

	public String getOpporSource() {
		return this.opporSource;
	}

	public void setOpporSource(String opporSource) {
		this.opporSource = opporSource;
	}

	public String getOpporStage() {
		return this.opporStage;
	}

	public void setOpporStage(String opporStage) {
		this.opporStage = opporStage;
	}

	public Date getOpporStartDate() {
		return this.opporStartDate;
	}

	public void setOpporStartDate(Date opporStartDate) {
		this.opporStartDate = opporStartDate;
	}

	public String getOpporStat() {
		return this.opporStat;
	}

	public void setOpporStat(String opporStat) {
		this.opporStat = opporStat;
	}

	public String getOpporType() {
		return this.opporType;
	}

	public void setOpporType(String opporType) {
		this.opporType = opporType;
	}

	public BigDecimal getPlanAmount() {
		return this.planAmount;
	}

	public void setPlanAmount(BigDecimal planAmount) {
		this.planAmount = planAmount;
	}

	public BigDecimal getPlanCost() {
		return this.planCost;
	}

	public void setPlanCost(BigDecimal planCost) {
		this.planCost = planCost;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public BigDecimal getReachAmount() {
		return this.reachAmount;
	}

	public void setReachAmount(BigDecimal reachAmount) {
		this.reachAmount = reachAmount;
	}

	public String getReachProb() {
		return this.reachProb;
	}

	public void setReachProb(String reachProb) {
		this.reachProb = reachProb;
	}

	public String getRelTrad() {
		return this.relTrad;
	}

	public void setRelTrad(String relTrad) {
		this.relTrad = relTrad;
	}

	public Timestamp getUpdateDateTime() {
		return this.updateDateTime;
	}

	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getUpdateOrgId() {
		return this.updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}

	public String getUpdateOrgName() {
		return this.updateOrgName;
	}

	public void setUpdateOrgName(String updateOrgName) {
		this.updateOrgName = updateOrgName;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

}