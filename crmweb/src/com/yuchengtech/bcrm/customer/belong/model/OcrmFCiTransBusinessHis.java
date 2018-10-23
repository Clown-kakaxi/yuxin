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
 * The persistent class for the OCRM_F_CI_TRANS_BUSINESS_HIS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_TRANS_BUSINESS_HIS")
public class OcrmFCiTransBusinessHis implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="OCRM_F_CI_TRANS_BUSINESS_HIS_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_TRANS_BUSINESS_HIS_GENERATOR")
	private Long id;
	
	@Column(name="PIPELINE_ID")
	private String pipelineId;
	
	@Column(name="BEFORE_MGR_ID")
	private String beforeMgrId;
	
	@Column(name="BEFORE_MGR_NAME")
	private String beforeMgrName;
	
	@Column(name="AFTER_MGR_ID")
	private String afterMgrId;
	
	@Column(name="AFTER_MGR_NAME")
	private String afterMgrName;
	
	@Column(name="EFFECT_DATE")
	private String effectDate;
	
	@Column(name="BEFORE_STEP")
	private String beforeStep;
	
	@Column(name="BEFORE_STATE")
	private String beforeState;
	
	@Column(name="TYPE")
	private String type;
	
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

	public String getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(String pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getBeforeMgrId() {
		return beforeMgrId;
	}

	public void setBeforeMgrId(String beforeMgrId) {
		this.beforeMgrId = beforeMgrId;
	}

	public String getAfterMgrId() {
		return afterMgrId;
	}

	public void setAfterMgrId(String afterMgrId) {
		this.afterMgrId = afterMgrId;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getBeforeStep() {
		return beforeStep;
	}

	public void setBeforeStep(String beforeStep) {
		this.beforeStep = beforeStep;
	}

	public String getBeforeState() {
		return beforeState;
	}

	public void setBeforeState(String beforeState) {
		this.beforeState = beforeState;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBeforeMgrName() {
		return beforeMgrName;
	}

	public void setBeforeMgrName(String beforeMgrName) {
		this.beforeMgrName = beforeMgrName;
	}

	public String getAfterMgrName() {
		return afterMgrName;
	}

	public void setAfterMgrName(String afterMgrName) {
		this.afterMgrName = afterMgrName;
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
