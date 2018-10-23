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
 * The persistent class for the OCRM_F_CI_MKT_PROSPECT_P database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_PROSPECT_P")
public class OcrmFCiMktProspectP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_PROSPECT_P_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_PROSPECT_P_ID_GENERATOR")
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

	private String rm;

	@Column(name="RUFUSE_REASON")
	private String rufuseReason;

	@Column(name="USER_ID")
	private String userId;

    @Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;

	@Column(name="VISIT_WAY")
	private String visitWay;

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

	
    public OcrmFCiMktProspectP() {
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

}