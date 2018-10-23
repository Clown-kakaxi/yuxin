package com.yuchengtech.bcrm.sales.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_MM_MKT_BUSI_OPPOR_HIS_S database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MM_MKT_BUSI_OPPOR_HIS_S")
public class OcrmFMmMktBusiOpporHisS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MM_MKT_BUSI_OPPOR_HIS_S_STEPID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MM_MKT_BUSI_OPPOR_HIS_S_STEPID_GENERATOR")
	@Column(name="STEP_ID")
	private String stepId;

	@Column(name="OPPOR_ID")
	private String opporId;

	@Column(name="OPR_CONTENT")
	private String oprContent;

	@Column(name="OPR_DATE_TIME")
	private Timestamp oprDateTime;

	@Column(name="OPR_ORG_ID")
	private String oprOrgId;

	@Column(name="OPR_ORG_NAME")
	private String oprOrgName;

	@Column(name="OPR_USER_ID")
	private String oprUserId;

	@Column(name="OPR_USER_NAME")
	private String oprUserName;

	public OcrmFMmMktBusiOpporHisS() {
	}

	public String getStepId() {
		return this.stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getOpporId() {
		return this.opporId;
	}

	public void setOpporId(String opporId) {
		this.opporId = opporId;
	}

	public String getOprContent() {
		return this.oprContent;
	}

	public void setOprContent(String oprContent) {
		this.oprContent = oprContent;
	}

	public Timestamp getOprDateTime() {
		return this.oprDateTime;
	}

	public void setOprDateTime(Timestamp oprDateTime) {
		this.oprDateTime = oprDateTime;
	}

	public String getOprOrgId() {
		return this.oprOrgId;
	}

	public void setOprOrgId(String oprOrgId) {
		this.oprOrgId = oprOrgId;
	}

	public String getOprOrgName() {
		return this.oprOrgName;
	}

	public void setOprOrgName(String oprOrgName) {
		this.oprOrgName = oprOrgName;
	}

	public String getOprUserId() {
		return this.oprUserId;
	}

	public void setOprUserId(String oprUserId) {
		this.oprUserId = oprUserId;
	}

	public String getOprUserName() {
		return this.oprUserName;
	}

	public void setOprUserName(String oprUserName) {
		this.oprUserName = oprUserName;
	}

}