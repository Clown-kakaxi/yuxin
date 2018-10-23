package com.yuchengtech.bcrm.customer.belong.model;

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
 * The persistent class for the OCRM_F_CI_TRANS_BUSINESS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_TRANS_BUSINESS")
public class OcrmFCiTransBusiness  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="OCRM_F_CI_TRANS_BUSINESS_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_TRANS_BUSINESS_GENERATOR")
	private Long id;

	@Column(name="APPLY_NO")
	private Long applyNo;
	
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;
	
	@Column(name="MGR_ID")
	private String mgrId;

	@Column(name="MGR_NAME")
	private String mgrName;

	@Column(name="BUS_DATA_ID")
	private String busDataId;
	
	@Temporal( TemporalType.DATE)
	@Column(name="EFFECT_DATE")
	private Date effectDate;
	
	@Column(name="STATE")
	private String state;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="PIPELINE_STEP")
	private String pipelineStep;
	
	@Column(name="PIPELINE_STATE")
	private String pipelineState;
	
	@Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	@Column(name="CREATOR")
	private String creator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(Long applyNo) {
		this.applyNo = applyNo;
	}

	public String getBusDataId() {
		return busDataId;
	}

	public void setBusDataId(String busDataId) {
		this.busDataId = busDataId;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPipelineStep() {
		return pipelineStep;
	}

	public void setPipelineStep(String pipelineStep) {
		this.pipelineStep = pipelineStep;
	}

	public String getPipelineState() {
		return pipelineState;
	}

	public void setPipelineState(String pipelineState) {
		this.pipelineState = pipelineState;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
