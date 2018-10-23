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
 * The persistent class for the OCRM_F_CI_MKT_CA_C database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_CA_C")
public class OcrmFCiMktCaC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_CA_C_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_CA_C_ID_GENERATOR")
	private Long id;

	@Column(name="ADD_AMT")
	private BigDecimal addAmt;

	@Column(name="APPLY_AMT")
	private BigDecimal applyAmt;

	@Column(name="AREA_ID")
	private String areaId;

	@Column(name="AREA_NAME")
	private String areaName;

    @Temporal( TemporalType.DATE)
	@Column(name="CA_DATE_P")
	private Date caDateP;

    @Temporal( TemporalType.DATE)
	@Column(name="CA_DATE_R")
	private Date caDateR;

	@Column(name="CA_HARD_INFO")
	private String caHardInfo;

	@Column(name="CALL_ID")
	private String callId;

	@Column(name="CASE_TYPE")
	private String caseType;

	@Column(name="CHECK_STAT")
	private String checkStat;

    @Temporal( TemporalType.DATE)
	@Column(name="COCO_DATE")
	private Date cocoDate;

	@Column(name="COCO_INFO")
	private String cocoInfo;
	
	@Column(name="CURRENCY")
	private String currency;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

    @Temporal( TemporalType.DATE)
	@Column(name="DD_DATE")
	private Date ddDate;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="DEPT_NAME")
	private String deptName;
	
	@Column(name="FOREIGN_MONEY")
	private BigDecimal foreignMoney;

	@Column(name="GRADE_LEVEL")
	private String gradeLevel;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name="HARD_REMARK")
	private String hardRemark;

	@Column(name="IF_ADD")
	private String ifAdd;

	@Column(name="IF_THIRD_STEP")
	private String ifThirdStep;

	@Column(name="INTENT_ID")
	private String intentId;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

    @Column(name="RM")
	private String rm;

    @Temporal( TemporalType.DATE)
	@Column(name="SX_DATE")
	private Date sxDate;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="USER_ID")
	private String userId;


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
	 * 企业类型
	 */
    @Column(name="COMP_TYPE")
	private String compType;
    
    
    /**
	 * 是否已开CO-CO MEETING
	 */
    @Column(name="IF_COCO")
	private String ifCoco;
    
    
    /**
	 * 预计核案概率
	 */
    @Column(name="SUC_PROBABILITY")
	private String sucProbability;
    
    /**
     * 首次文件收集日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name="FIRST_DOCU_DATE")
    private Date firstDocuDate;
    
    /**
     * 文件收齐日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name="GET_DOCU_DATE")
    private Date getDocuDate;
    
    /**
     * 文件送出日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name="SEND_DOCU_DATE")
    private Date sendDocuDate;
    
    
    /**
     * CA开始准备日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name="CA_DATE_S")
    private Date caDateS;
    
    /**
     * CA准备人员
     */
    @Column(name="CA_PP")
    private String caPp;
    
    /**
     * 客户初评级
     */
    @Column(name="GRADE_PERSECT")
    private String gradePersect;
    
    /**
     * RM回复COCO_MEETING提问日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name="RM_REPLY_COCO")
    private Date rmReplyCoco;
    
    /**
     * 客户类型
     */
    @Column(name="CUST_TYPE")
    private String custType;
    
    /**
     * 是否提交至CO
     */
    @Column(name="IF_SUMBIT_CO")
    private String ifSumbitCo;
    
    /**
     * RM信贷系统提交日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name="XD_CA_DATE")
    private Date xdCaDate;
	
    public OcrmFCiMktCaC() {
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

	public Date getCaDateP() {
		return this.caDateP;
	}

	public void setCaDateP(Date caDateP) {
		this.caDateP = caDateP;
	}

	public Date getCaDateR() {
		return this.caDateR;
	}

	public void setCaDateR(Date caDateR) {
		this.caDateR = caDateR;
	}

	public String getCaHardInfo() {
		return this.caHardInfo;
	}

	public void setCaHardInfo(String caHardInfo) {
		this.caHardInfo = caHardInfo;
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

	public Date getCocoDate() {
		return this.cocoDate;
	}

	public void setCocoDate(Date cocoDate) {
		this.cocoDate = cocoDate;
	}

	public String getCocoInfo() {
		return this.cocoInfo;
	}

	public void setCocoInfo(String cocoInfo) {
		this.cocoInfo = cocoInfo;
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

	public Date getDdDate() {
		return this.ddDate;
	}

	public void setDdDate(Date ddDate) {
		this.ddDate = ddDate;
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

	public String getGradeLevel() {
		return this.gradeLevel;
	}

	public void setGradeLevel(String dradeLevel) {
		this.gradeLevel = dradeLevel;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setHardRemark(String hardRemark) {
		this.hardRemark = hardRemark;
	}

	public String getHardRemark() {
		return hardRemark;
	}

	public String getIfAdd() {
		return this.ifAdd;
	}

	public void setIfAdd(String ifAdd) {
		this.ifAdd = ifAdd;
	}

	public String getIfThirdStep() {
		return this.ifThirdStep;
	}

	public void setIfThirdStep(String ifThirdStep) {
		this.ifThirdStep = ifThirdStep;
	}

	public String getIntentId() {
		return this.intentId;
	}

	public void setIntentId(String intentId) {
		this.intentId = intentId;
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

	public Date getSxDate() {
		return this.sxDate;
	}

	public void setSxDate(Date sxDate) {
		this.sxDate = sxDate;
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

	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	public String getIfCoco() {
		return ifCoco;
	}

	public void setIfCoco(String ifCoco) {
		this.ifCoco = ifCoco;
	}

	public String getSucProbability() {
		return sucProbability;
	}

	public void setSucProbability(String sucProbability) {
		this.sucProbability = sucProbability;
	}

	public Date getFirstDocuDate() {
		return firstDocuDate;
	}

	public void setFirstDocuDate(Date firstDocuDate) {
		this.firstDocuDate = firstDocuDate;
	}

	public Date getGetDocuDate() {
		return getDocuDate;
	}

	public void setGetDocuDate(Date getDocuDate) {
		this.getDocuDate = getDocuDate;
	}

	public Date getSendDocuDate() {
		return sendDocuDate;
	}

	public void setSendDocuDate(Date sendDocuDate) {
		this.sendDocuDate = sendDocuDate;
	}

	public Date getCaDateS() {
		return caDateS;
	}

	public void setCaDateS(Date caDateS) {
		this.caDateS = caDateS;
	}

	public String getCaPp() {
		return caPp;
	}

	public void setCaPp(String caPp) {
		this.caPp = caPp;
	}

	public String getGradePersect() {
		return gradePersect;
	}

	public void setGradePersect(String gradePersect) {
		this.gradePersect = gradePersect;
	}

	public Date getRmReplyCoco() {
		return rmReplyCoco;
	}

	public void setRmReplyCoco(Date rmReplyCoco) {
		this.rmReplyCoco = rmReplyCoco;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getIfSumbitCo() {
		return ifSumbitCo;
	}

	public void setIfSumbitCo(String ifSumbitCo) {
		this.ifSumbitCo = ifSumbitCo;
	}

	public Date getXdCaDate() {
		return xdCaDate;
	}

	public void setXdCaDate(Date xdCaDate) {
		this.xdCaDate = xdCaDate;
	}


}