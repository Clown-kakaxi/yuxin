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
 * The persistent class for the OCRM_F_CI_MKT_APPROVL_C database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_APPROVL_C")
public class OcrmFCiMktApprovlC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_APPROVL_C_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_APPROVL_C_ID_GENERATOR")
	private Long id;

	@Column(name="ADD_AMT")
	private BigDecimal addAmt;

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

    @Temporal( TemporalType.DATE)
	@Column(name="CC_DATE")
	private Date ccDate;

    @Temporal( TemporalType.DATE)
	@Column(name="CC_OPEN_DATE")
	private Date ccOpenDate;

    @Temporal( TemporalType.DATE)
	@Column(name="CHECK_DATE")
	private Date checkDate;

	@Column(name="CHECK_PROGRESS")
	private String checkProgress;

	@Column(name="CHECK_STAT")
	private String checkStat;

	@Column(name="COMP_TYPE")
	private String compType;
	
	@Column(name="CURRENCY")
	private String currency;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="DELINE_REASON")
	private String delineReason;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="DEPT_NAME")
	private String deptName;

	@Column(name="FOREIGN_MONEY")
	private Long foreignMoney;

	@Column(name="GRADE_LEVEL")
	private String gradeLevel;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name="IF_ADD")
	private String ifAdd;

	@Column(name="IF_FIFTH_STEP")
	private String ifFifthStep;

	@Column(name="IF_SURE")
	private String ifSure;

	@Column(name="INSURE_AMT")
	private BigDecimal insureAmt;

	@Column(name="INSURE_FORM")
	private String insureForm;
	
	@Column(name="INSURE_CURRENCY")
	private String insureCurrency;
	
	@Column(name="INSURE_MONEY")
	private BigDecimal insureMoney;

    @Temporal( TemporalType.DATE)
	@Column(name="LEVEL1_DATE")
	private Date level1Date;

    @Temporal( TemporalType.DATE)
	@Column(name="LEVEL2_DATE")
	private Date level2Date;

    @Temporal( TemporalType.DATE)
	@Column(name="LEVEL34_DATE")
	private Date level34Date;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

    @Column(name="RM")
	private String rm;

	@Column(name="SC_ID")
	private String scId;

    @Temporal( TemporalType.DATE)
	@Column(name="SC_DATE")
	private Date scDate;

	@Column(name="SP_LEVEL")
	private String spLevel;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

    @Temporal( TemporalType.DATE)
	@Column(name="USE_DATE_P")
	private Date useDateP;

	@Column(name="USER_ID")
	private String userId;

    @Temporal( TemporalType.DATE)
	@Column(name="XZ_CA_DATE")
	private Date xzCaDate;

	@Column(name="XZ_CA_FORM")
	private String xzCaForm;
	
	@Column(name="CO")
	private String co;
	
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
 * 是否退回案件
 */
    @Column(name="IF_BACK")
   	private String ifBack;
    
    
    /**
     * 未核准原因说明
     */
	@Column(name="RESON_REMARK")
	private String resonRemark; 
	
	/**
     * 客户类型
     */
	@Column(name="CUST_TYPE")
	private String custType;
	
	/**
     * 初审完成日期
     */
	@Temporal(TemporalType.DATE)
	@Column(name="TRAIL_COMPLETION_DATE")
	private Date trailCompletionDate;
	
	/**
     *退件或拒绝原因
     */
	@Column(name="REFUSE_REASON")
	private String refuseReason;
	
	/**
	 *退件或拒绝原因说明
	 */
	@Column(name = "REFUSE_REASON_REMARK")
	private String refuseReasonRemark;
	
	
	
    public OcrmFCiMktApprovlC() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAddAmt() {
		return this.addAmt;
	}

	public void setAddAmt(BigDecimal addAmt) {
		this.addAmt = addAmt;
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

	public Date getCcDate() {
		return ccDate;
	}

	public void setCcDate(Date ccDate) {
		this.ccDate = ccDate;
	}

	public Date getCcOpenDate() {
		return this.ccOpenDate;
	}

	public void setCcOpenDate(Date ccOpenDate) {
		this.ccOpenDate = ccOpenDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckProgress() {
		return checkProgress;
	}

	public void setCheckProgress(String checkProgress) {
		this.checkProgress = checkProgress;
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

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
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

	public String getDelineReason() {
		return this.delineReason;
	}

	public void setDelineReason(String delineReason) {
		this.delineReason = delineReason;
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

	public void setForeignMoney(Long foreignMoney) {
		this.foreignMoney = foreignMoney;
	}

	public Long getForeignMoney() {
		return foreignMoney;
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

	public String getIfAdd() {
		return this.ifAdd;
	}

	public void setIfAdd(String ifAdd) {
		this.ifAdd = ifAdd;
	}

	public String getIfFifthStep() {
		return this.ifFifthStep;
	}

	public void setIfFifthStep(String ifFifthStep) {
		this.ifFifthStep = ifFifthStep;
	}

	public String getIfSure() {
		return this.ifSure;
	}

	public void setIfSure(String ifSure) {
		this.ifSure = ifSure;
	}

	public BigDecimal getInsureAmt() {
		return this.insureAmt;
	}

	public void setInsureAmt(BigDecimal insuerAmt) {
		this.insureAmt = insuerAmt;
	}

	public String getInsureForm() {
		return this.insureForm;
	}

	public void setInsureForm(String insureForm) {
		this.insureForm = insureForm;
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

	public Date getLevel1Date() {
		return level1Date;
	}

	public void setLevel1Date(Date level1Date) {
		this.level1Date = level1Date;
	}

	public Date getLevel2Date() {
		return level2Date;
	}

	public void setLevel2Date(Date level2Date) {
		this.level2Date = level2Date;
	}

	public Date getLevel34Date() {
		return level34Date;
	}

	public void setLevel34Date(Date level34Date) {
		this.level34Date = level34Date;
	}

	public BigDecimal getInsureMoney() {
		return insureMoney;
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

	public String getScId() {
		return this.scId;
	}

	public void setScId(String scId) {
		this.scId = scId;
	}

	public Date getScDate() {
		return scDate;
	}

	public void setScDate(Date scDate) {
		this.scDate = scDate;
	}

	public String getSpLevel() {
		return this.spLevel;
	}

	public void setSpLevel(String spLevel) {
		this.spLevel = spLevel;
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

	public Date getXzCaDate() {
		return this.xzCaDate;
	}

	public void setXzCaDate(Date xzCaDate) {
		this.xzCaDate = xzCaDate;
	}

	public String getXzCaForm() {
		return this.xzCaForm;
	}

	public void setXzCaForm(String xzCaForm) {
		this.xzCaForm = xzCaForm;
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

	public String getIfBack() {
		return ifBack;
	}

	public void setIfBack(String ifBack) {
		this.ifBack = ifBack;
	}

	public String getResonRemark() {
		return resonRemark;
	}

	public void setResonRemark(String resonRemark) {
		this.resonRemark = resonRemark;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public Date getTrailCompletionDate() {
		return trailCompletionDate;
	}

	public void setTrailCompletionDate(Date trailCompletionDate) {
		this.trailCompletionDate = trailCompletionDate;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getRefuseReasonRemark() {
		return refuseReasonRemark;
	}

	public void setRefuseReasonRemark(String refuseReasonRemark) {
		this.refuseReasonRemark = refuseReasonRemark;
	}
	


}