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
 * The persistent class for the OCRM_F_CI_MKT_APPROVED_C database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_APPROVED_C")
public class OcrmFCiMktApprovedC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_APPROVED_C_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_APPROVED_C_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="ACCOUNT_DATE")
	private Date accountDate;

	@Column(name="ADD_AMT")
	private BigDecimal addAmt;

    @Temporal( TemporalType.DATE)
	@Column(name="AMT_USE_DATE")
	private Date amtUseDate;

	@Column(name="APPLY_AMT")
	private BigDecimal applyAmt;

	@Column(name="AREA_ID")
	private String areaId;

	@Column(name="AREA_NAME")
	private String areaName;

	@Column(name="CALL_ID")
	private String callId;

	@Column(name="CASE_TYPE")
	private String caseType;

	@Column(name="CHECK_STAT")
	private String checkStat;

	@Column(name="COMP_TYPE")
	private String compType;

    @Temporal( TemporalType.DATE)
	@Column(name="CTR_C_DATE")
	private Date ctrCDate;

	@Column(name="CTR_PROBLEM")
	private String ctrProblem;

    @Temporal( TemporalType.DATE)
	@Column(name="CTR_S_DATE")
	private Date ctrSDate;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="DEPT_NAME")
	private String deptName;

    @Temporal( TemporalType.DATE)
	@Column(name="FILE_UP_DATE")
	private Date fileUpDate;

	@Column(name="GRADE_LEVEL")
	private String gradeLevel;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name="HP_ID")
	private String hpId;

	@Column(name="IF_ACCEPT")
	private String ifAccept;

	@Column(name="IF_ADD")
	private String ifAdd;
	
	@Column(name="INSURE_AMT")
	private String insureAmt;
	
	@Column(name="INSURE_CURRENCY")
	private String insureCurrency;
	
	@Column(name="INSURE_MONEY")
	private BigDecimal insureMoney;

    @Temporal( TemporalType.DATE)
	@Column(name="MORTGAGE_DATE")
	private Date mortgageDate;

	@Column(name="NOACCEPT_REASON")
	private String noacceptReason;

    @Temporal( TemporalType.DATE)
	@Column(name="PAY_DATE")
	private Date payDate;

    @Temporal( TemporalType.DATE)
	@Column(name="PROBLEM_DATE")
	private Date problemDate;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

    @Column(name="RM")
	private String rm;

    @Temporal( TemporalType.DATE)
	@Column(name="SX_CTR_DATE")
	private Date sxCtrDate;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

    @Temporal( TemporalType.DATE)
	@Column(name="USE_DATE_P")
	private Date useDateP;

	@Column(name="USER_ID")
	private String userId;

    @Temporal( TemporalType.DATE)
	@Column(name="XD_HZ_DATE")
	private Date xdHzDate;

    @Temporal( TemporalType.DATE)
	@Column(name="XD_CHECK_DATE")
	private Date xdCheckDate;
    
    /**
	 * PIPELINE_ID
	 */
	@Column(name="PIPELINE_ID")
	private Long pipelineId;
	    
/**
 * 客户经理ID
 */
    @Column(name="RM_ID")
	private String rmId;
    
    
/**
 * 客户不接受核准额度原因
 */
    @Column(name="UNRECEPT_REASON")
	private String unreceptReason;
        
/**
 * 客户不接受核准额度原因说明
 */
    @Column(name="REASON_REMARK1")
	private String reasonRemark1;
    
/**
 * 未动拨原因
 */
    @Column(name="UNISSUE_REASON")
	private String unissueReason;    
                    
/**
 * 未动拨原因说明
 */
    @Column(name="REASON_REMARK2")
	private String reasonRemark2;
    
    @Column(name="LAST_SEND_STEP")
    private String lastSendStep;
    
    
    /**
     * 客户类型
     */
    @Column(name="CUST_TYPE")
    private String custType;
    
    /**
     * 申请币别(原币別)
     */
    @Column(name="CURRENCY")
    private String currency;
    
    
    public OcrmFCiMktApprovedC() {
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

	public BigDecimal getAddAmt() {
		return this.addAmt;
	}

	public void setAddAmt(BigDecimal addAmt) {
		this.addAmt = addAmt;
	}

	public Date getAmtUseDate() {
		return this.amtUseDate;
	}

	public void setAmtUseDate(Date amtUseDate) {
		this.amtUseDate = amtUseDate;
	}

	public BigDecimal getApplyAmt() {
		return this.applyAmt;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
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

	public String getCaseType() {
		return this.caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCheckStat() {
		return this.checkStat;
	}

	public void setCheckStat(String checkStat) {
		this.checkStat = checkStat;
	}

	public String getCompType() {
		return this.compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	public Date getCtrCDate() {
		return this.ctrCDate;
	}

	public void setCtrCDate(Date ctrCDate) {
		this.ctrCDate = ctrCDate;
	}

	public String getCtrProblem() {
		return this.ctrProblem;
	}

	public void setCtrProblem(String ctrProblem) {
		this.ctrProblem = ctrProblem;
	}

	public Date getCtrSDate() {
		return this.ctrSDate;
	}

	public void setCtrSDate(Date ctrSDate) {
		this.ctrSDate = ctrSDate;
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

	public Date getFileUpDate() {
		return this.fileUpDate;
	}

	public void setFileUpDate(Date fileUpDate) {
		this.fileUpDate = fileUpDate;
	}

	public String getGradeLevel() {
		return this.gradeLevel;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getHpId() {
		return this.hpId;
	}

	public void setHpId(String hpId) {
		this.hpId = hpId;
	}

	public String getIfAccept() {
		return this.ifAccept;
	}

	public void setIfAccept(String ifAccept) {
		this.ifAccept = ifAccept;
	}

	public String getIfAdd() {
		return this.ifAdd;
	}

	public void setIfAdd(String ifAdd) {
		this.ifAdd = ifAdd;
	}

	public String getInsureAmt() {
		return insureAmt;
	}

	public void setInsureAmt(String insureAmt) {
		this.insureAmt = insureAmt;
	}
	
	public void setInsureCurrency(String insureCurrency) {
		this.insureCurrency = insureCurrency;
	}

	public String getInsureCurrency() {
		return insureCurrency;
	}

	public void setInsureMoney(BigDecimal insureMoney) {
		this.insureMoney = insureMoney;
	}

	public BigDecimal getInsureMoney() {
		return insureMoney;
	}

	public Date getMortgageDate() {
		return this.mortgageDate;
	}

	public void setMortgageDate(Date mortgageDate) {
		this.mortgageDate = mortgageDate;
	}

	public String getNoacceptReason() {
		return this.noacceptReason;
	}

	public void setNoacceptReason(String noacceptReason) {
		this.noacceptReason = noacceptReason;
	}

	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getProblemDate() {
		return this.problemDate;
	}

	public void setProblemDate(Date problemDate) {
		this.problemDate = problemDate;
	}

	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getRm() {
		return this.rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public Date getSxCtrDate() {
		return this.sxCtrDate;
	}

	public void setSxCtrDate(Date sxCtrDate) {
		this.sxCtrDate = sxCtrDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUseDateP() {
		return this.useDateP;
	}

	public void setUseDateP(Date useDateP) {
		this.useDateP = useDateP;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getXdHzDate() {
		return this.xdHzDate;
	}

	public void setXdHzDate(Date xdHzDate) {
		this.xdHzDate = xdHzDate;
	}

	public Date getXdCheckDate() {
		return xdCheckDate;
	}

	public void setXdCheckDate(Date xdCheckDate) {
		this.xdCheckDate = xdCheckDate;
	}

	public Long getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(Long pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getRmId() {
		return rmId;
	}

	public void setRmId(String rmId) {
		this.rmId = rmId;
	}

	public String getUnreceptReason() {
		return unreceptReason;
	}

	public void setUnreceptReason(String unreceptReason) {
		this.unreceptReason = unreceptReason;
	}

	public String getReasonRemark1() {
		return reasonRemark1;
	}

	public void setReasonRemark1(String reasonRemark1) {
		this.reasonRemark1 = reasonRemark1;
	}

	public String getUnissueReason() {
		return unissueReason;
	}

	public void setUnissueReason(String unissueReason) {
		this.unissueReason = unissueReason;
	}

	public String getReasonRemark2() {
		return reasonRemark2;
	}

	public void setReasonRemark2(String reasonRemark2) {
		this.reasonRemark2 = reasonRemark2;
	}

	public String getLastSendStep() {
		return lastSendStep;
	}

	public void setLastSendStep(String lastSendStep) {
		this.lastSendStep = lastSendStep;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	


}