package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
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
 * The persistent class for the OCRM_F_CI_MKT_PROSPECT_C database table.--->FOR SME 
 */

@Entity
@Table(name="OCRM_F_CI_MKT_PROSPECT_C")
public class OcrmFCiMktProspectC implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_PROSPECT_C_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_PROSPECT_C_ID_GENERATOR")
	private Long id;

	@Column(name="AREA_ID")
	private String areaId;

	@Column(name="AREA_NAME")
	private String areaName;

	@Column(name="CALL_ID")
	private String callId;

	@Column(name="CHECK_STAT")
	private String checkStat;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_SOURCE")
	private String custSource;

    @Temporal( TemporalType.DATE)
	@Column(name="CUST_SOURCE_DATE")
	private Date custSourceDate;

	@Column(name="CUST_SOURCE_INFO")
	private String custSourceInfo;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="DEPT_NAME")
	private String deptName;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name="IF_FILES")
	private String ifFiles;

	@Column(name="IF_PIPELINE")
	private String ifPipeline;

	@Column(name="IF_TRANS_CUST")
	private String ifTransCust;

	@Column(name="LINK_MAN")
	private String linkMan;

	@Column(name="LINK_PHONE")
	private String linkPhone;

	@Column(name="PRODUCT_NEED")
	private String productNeed;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

    @Column(name="RM")
	private String rm;

	@Column(name="RUFUSE_REASON")
	private String rufuseReason;
	
	

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="USER_ID")
	private String userId;

    @Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;

	@Column(name="VISIT_WAY")
	private String visitWay;

	@Column(name="RM_ID")
	private String rmId;
	
	@Column(name="PIPELINE_ID")
	private Long pipelineId;
	
	@Column(name="REASON_REMARK")
	private String reasonRemark;
	
	/**
	 * 转为存款户日期
	 */
	@Temporal( TemporalType.DATE)
	@Column(name="TRANS_DATE")
	private Date transDate;
	
	/**
	 * 转为PIPELINE日期
	 */
	@Temporal( TemporalType.DATE)
	@Column(name="PIPELINE_DATE")
	private Date pipelineDate;
	
    public OcrmFCiMktProspectC() {
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

	public String getCustSource() {
		return this.custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}

	public Date getCustSourceDate() {
		return this.custSourceDate;
	}

	public void setCustSourceDate(Date custSourceDate) {
		this.custSourceDate = custSourceDate;
	}

	public String getCustSourceInfo() {
		return this.custSourceInfo;
	}

	public void setCustSourceInfo(String custSourceInfo) {
		this.custSourceInfo = custSourceInfo;
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

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIfFiles() {
		return this.ifFiles;
	}

	public void setIfFiles(String ifFiles) {
		this.ifFiles = ifFiles;
	}

	public String getIfPipeline() {
		return this.ifPipeline;
	}

	public void setIfPipeline(String ifPipeline) {
		this.ifPipeline = ifPipeline;
	}

	public String getIfTransCust() {
		return this.ifTransCust;
	}

	public void setIfTransCust(String ifTransCust) {
		this.ifTransCust = ifTransCust;
	}

	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkPhone() {
		return this.linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getProductNeed() {
		return this.productNeed;
	}

	public void setProductNeed(String productNeed) {
		this.productNeed = productNeed;
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

	public String getRufuseReason() {
		return this.rufuseReason;
	}

	public void setRufuseReason(String rufuseReason) {
		this.rufuseReason = rufuseReason;
	}

	

	public String getReasonRemark() {
		return reasonRemark;
	}
	
	public void setReasonRemark(String reasonRemark) {
		this.reasonRemark = reasonRemark;
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

	public String getVisitWay() {
		return this.visitWay;
	}

	public void setVisitWay(String visitWay) {
		this.visitWay = visitWay;
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

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Date getPipelineDate() {
		return pipelineDate;
	}

	public void setPipelineDate(Date pipelineDate) {
		this.pipelineDate = pipelineDate;
	}



}
