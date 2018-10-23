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
 * The persistent class for the OCRM_F_CI_MKT_CHECK_C database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_CHECK_C")
public class OcrmFCiMktCheckC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_CHECK_C_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_CHECK_C_ID_GENERATOR")
	private Long id;

	@Column(name="ADD_AMT")
	private BigDecimal addAmt;

	@Column(name="ADD_CASE_CONTENT")
	private String addCaseContent;

    @Temporal( TemporalType.DATE)
	@Column(name="ADD_CASE_DATE")
	private Date addCaseDate;

	@Column(name="APPLY_AMT")
	private BigDecimal applyAmt;

	@Column(name="AREA_ID")
	private String areaId;

	@Column(name="AREA_NAME")
	private String areaName;

	@Column(name="CA_FORM")
	private String caForm;

	@Column(name="CA_ID")
	private String caId;

	@Column(name="CALL_ID")
	private String callId;

	@Column(name="CASE_TYPE")
	private String caseType;

    @Temporal( TemporalType.DATE)
	@Column(name="CC_DATE")
	private Date ccDate;

	@Column(name="CHECK_PROGRESS")
	private String checkProgress;

	@Column(name="CHECK_STAT")
	private String checkStat;

	@Column(name="CO")
	private String co;

	@Column(name="COMP_TYPE")
	private String compType;
	
	@Column(name="CURRENCY")
	private String currency;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

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

	@Column(name="IF_FOURTH_STEP")
	private String ifFourthStep;

	@Column(name="MEMO")
	private String memo;

    @Temporal( TemporalType.DATE)
	@Column(name="QA_DATE")
	private Date qaDate;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

    @Column(name="RM")
	private String rm;

    @Temporal( TemporalType.DATE)
	@Column(name="RM_C_DATE")
	private Date rmCDate;

    @Temporal( TemporalType.DATE)
	@Column(name="RM_DATE")
	private Date rmDate;

	@Column(name="SP_LEVEL")
	private String spLevel;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="USER_ID")
	private String userId;

    @Temporal( TemporalType.DATE)
	@Column(name="XD_CA_DATE")
	private Date xdCaDate;

    @Temporal( TemporalType.DATE)
	@Column(name="XS_CC_DATE")
	private Date xsCcDate;
    
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
   	 * 未核准原因
   	 */
    @Column(name="REFUSE_REASON")
   	private String refuseReason;
    
    /**
   	 * 未核准原因说明
   	 */
    @Column(name="REASON_REMARK")
   	private String reasonRemark;
    
    /**
   	 * 客户类型
   	 */
    @Column(name="CUST_TYPE")
   	private String custType;
    
    /**
   	 * CA准备完成时间
   	 */
    @Temporal(TemporalType.DATE)
   	@Column(name="CA_FINISH_DATE")
   	private Date caFinishDate;
    
    /**
   	 * 系统分配至CO日期
   	 */
    @Temporal(TemporalType.DATE)
   	@Column(name="TO_CO_DATE")
   	private Date toCoDate;
    
    /**
   	 * 文件审查
   	 */
    @Temporal(TemporalType.DATE)
   	@Column(name="DOCU_CHECK")
   	private Date docuCheck;
   	
    /**
   	 * 访厂完成日期
   	 */
    @Temporal(TemporalType.DATE)
   	@Column(name="VISIT_FACTORY_DATE")
   	private Date visitFactoryDate;
    
    /**
   	 * 信审要求补件日期
   	 */
    @Temporal(TemporalType.DATE)
   	@Column(name="REQUIRE_CASE_DATE")
   	private Date requireCaseDate;
    
    /**
   	 * 退件或拒绝日期(若有)
   	 */
    @Temporal(TemporalType.DATE)
   	@Column(name="REFUSE_DATE")
   	private Date refuseDate;
    
    public OcrmFCiMktCheckC() {
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

	public String getAddCaseContent() {
		return addCaseContent;
	}

	public void setAddCaseContent(String addCaseContent) {
		this.addCaseContent = addCaseContent;
	}

	public Date getAddCaseDate() {
		return addCaseDate;
	}

	public void setAddCaseDate(Date addCaseDate) {
		this.addCaseDate = addCaseDate;
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

	public String getCaForm() {
		return this.caForm;
	}

	public void setCaForm(String caForm) {
		this.caForm = caForm;
	}

	public String getCaId() {
		return this.caId;
	}

	public void setCaId(String caId) {
		this.caId = caId;
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
		return this.ccDate;
	}

	public void setCcDate(Date ccDate) {
		this.ccDate = ccDate;
	}

	public String getCheckStat() {
		return this.checkStat;
	}

	public void setCheckStat(String checkStat) {
		this.checkStat = checkStat;
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

	public String getIfFourthStep() {
		return this.ifFourthStep;
	}

	public void setIfFourthStep(String ifFourthStep) {
		this.ifFourthStep = ifFourthStep;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String momo) {
		this.memo = momo;
	}

	public Date getQaDate() {
		return this.qaDate;
	}

	public void setQaDate(Date qaDate) {
		this.qaDate = qaDate;
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

	public Date getRmCDate() {
		return this.rmCDate;
	}

	public void setRmCDate(Date rmCDate) {
		this.rmCDate = rmCDate;
	}

	public Date getRmDate() {
		return this.rmDate;
	}

	public void setRmDate(Date rmDate) {
		this.rmDate = rmDate;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getXdCaDate() {
		return this.xdCaDate;
	}

	public void setXdCaDate(Date xdCaDate) {
		this.xdCaDate = xdCaDate;
	}

	public Date getXsCcDate() {
		return this.xsCcDate;
	}

	public void setXsCcDate(Date xsCcDate) {
		this.xsCcDate = xsCcDate;
	}

	public void setCheckProgress(String checkProgress) {
		this.checkProgress = checkProgress;
	}

	public String getCheckProgress() {
		return checkProgress;
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

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getReasonRemark() {
		return reasonRemark;
	}

	public void setReasonRemark(String reasonRemark) {
		this.reasonRemark = reasonRemark;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public Date getCaFinishDate() {
		return caFinishDate;
	}

	public void setCaFinishDate(Date caFinishDate) {
		this.caFinishDate = caFinishDate;
	}

	public Date getToCoDate() {
		return toCoDate;
	}

	public void setToCoDate(Date toCoDate) {
		this.toCoDate = toCoDate;
	}

	public Date getVisitFactoryDate() {
		return visitFactoryDate;
	}

	public void setVisitFactoryDate(Date visitFactoryDate) {
		this.visitFactoryDate = visitFactoryDate;
	}

	public Date getRequireCaseDate() {
		return requireCaseDate;
	}

	public void setRequireCaseDate(Date requireCaseDate) {
		this.requireCaseDate = requireCaseDate;
	}

	public Date getRefuseDate() {
		return refuseDate;
	}

	public void setRefuseDate(Date refuseDate) {
		this.refuseDate = refuseDate;
	}

	public Date getDocuCheck() {
		return docuCheck;
	}

	public void setDocuCheck(Date docuCheck) {
		this.docuCheck = docuCheck;
	}


}