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
 * The persistent class for the OCRM_F_CI_MKT_INTENT_C database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_INTENT_C")
public class OcrmFCiMktIntentC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_INTENT_C_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_INTENT_C_ID_GENERATOR")
	private Long id;

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

	@Column(name="COMBY_AMT")
	private BigDecimal combyAmt;

	@Column(name="COMP_TYPE")
	private String compType;

	@Column(name="CP_HARD_INFO")
	private String cpHardInfo;
	
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
	private BigDecimal foreignMoney;

	@Column(name="GRADE_PERSECT")
	private String gradePersect;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name="HARD_INFO")
	private String hardInfo;

	@Column(name="IF_SECOND_STEP")
	private String ifSecondStep;

	@Column(name="MAIN_AMT")
	private BigDecimal mainAmt;

	@Column(name="MAIN_INSURE")
	private String mainInsure;

	@Column(name="PROSPECT_ID")
	private String prospectId;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

    @Column(name="RM")
	private String rm;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="IF_ADD")
	private String ifAdd ;
	
	@Column(name="ADD_AMT")
	private BigDecimal addAmt ;
	
	@Column(name="USER_ID")
	private String userId;

    @Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;
    
    /**
     * 客户类型
     */
    @Column(name="CUST_TYPE")
    private String custType;
    
    /**
     * 拒绝原因
     */
    @Column(name="REFUSE_REASON")
    private String refuseReason;
    
    /**
     * 备注
     */
    @Column(name="REMARK")
    private String remark;
    /**
	 * 客户经理ID
	 */
    @Column(name="RM_ID")
	private String rmId;
    
    public String getIfAdd() {
		return ifAdd;
	}

	public void setIfAdd(String ifAdd) {
		this.ifAdd = ifAdd;
	}

	public BigDecimal getAddAmt() {
		return addAmt;
	}

	public void setAddAmt(BigDecimal addAmt) {
		this.addAmt = addAmt;
	}

	/**
	 * PIPELINE_ID
	 */
    @Column(name="PIPELINE_ID")
	private Long pipelineId;
    
    /**
  	 * 送案概率
  	 */
      @Column(name="SUC_PROBABILITY")
  	private String sucProbability;
      
     
      
    public OcrmFCiMktIntentC() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getCombyAmt() {
		return this.combyAmt;
	}

	public void setCombyAmt(BigDecimal combyAmt) {
		this.combyAmt = combyAmt;
	}

	public String getCompType() {
		return this.compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	public String getCpHardInfo() {
		return this.cpHardInfo;
	}

	public void setCpHardInfo(String cpHardInfo) {
		this.cpHardInfo = cpHardInfo;
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

	public void setForeignMoney(BigDecimal foreignMoney) {
		this.foreignMoney = foreignMoney;
	}

	public BigDecimal getForeignMoney() {
		return foreignMoney;
	}

	public String getGradePersect() {
		return this.gradePersect;
	}

	public void setGradePersect(String gradePersect) {
		this.gradePersect = gradePersect;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getHardInfo() {
		return this.hardInfo;
	}

	public void setHardInfo(String hardInfo) {
		this.hardInfo = hardInfo;
	}

	public String getIfSecondStep() {
		return this.ifSecondStep;
	}

	public void setIfSecondStep(String ifSecondStep) {
		this.ifSecondStep = ifSecondStep;
	}

	public BigDecimal getMainAmt() {
		return this.mainAmt;
	}

	public void setMainAmt(BigDecimal mainAmt) {
		this.mainAmt = mainAmt;
	}

	public String getMainInsure() {
		return this.mainInsure;
	}

	public void setMainInsure(String mainInsure) {
		this.mainInsure = mainInsure;
	}

	public String getProspectId() {
		return this.prospectId;
	}

	public void setProspectId(String prospectId) {
		this.prospectId = prospectId;
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

	public Date getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getRmId() {
		return rmId;
	}

	public void setRmId(String rmId) {
		this.rmId = rmId;
	}

	public Long getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(Long pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getSucProbability() {
		return sucProbability;
	}

	public void setSucProbability(String sucProbability) {
		this.sucProbability = sucProbability;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



}