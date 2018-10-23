package com.yuchengtech.bcrm.product.model;

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
 * The persistent class for the OCRM_F_PD_PROD_SHOW_PLAN database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_SHOW_PLAN")
public class OcrmFPdProdShowPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_PD_PROD_SHOW_PLAN_PLANID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_PD_PROD_SHOW_PLAN_PLANID_GENERATOR")
	@Column(name="PLAN_ID", unique=true, nullable=false, precision=19)
	private Long planId;

	@Column(name="PLAN_NAME", length=100)
	private String planName;

	@Column(name="CREATE_USER", length=50)
	private String createUser;
	
	@Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	@Column(name="PLAN_TYPE", length=10)
	private String planType;

	@Column(length=300)
	private String remark;

    public OcrmFPdProdShowPlan() {
    }

	public Long getPlanId() {
		return this.planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return this.planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanType() {
		return this.planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}