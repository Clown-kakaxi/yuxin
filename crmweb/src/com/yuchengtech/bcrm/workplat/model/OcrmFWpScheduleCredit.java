package com.yuchengtech.bcrm.workplat.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_WP_SCHEDULE_CREDIT database table.
 * 
 */
@Entity
@Table(name="OCRM_F_WP_SCHEDULE_CREDIT")
public class OcrmFWpScheduleCredit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_WP_SCHEDULE_CREDIT_CID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_WP_SCHEDULE_CREDIT_CID_GENERATOR")
	@Column(name="C_ID")
	private Long cId;

	@Column(name="ARANGE_ID")
	private String arangeId;

	@Column(name="COMP_SIT")
	private String compSit;

	@Column(name="CRD_REQ")
	private String crdReq;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	private String remark;

	@Column(name="SCH_ID")
	private BigDecimal schId;

	private String stat;

    public OcrmFWpScheduleCredit() {
    }

	public Long getCId() {
		return this.cId;
	}

	public void setCId(Long cId) {
		this.cId = cId;
	}

	public String getArangeId() {
		return this.arangeId;
	}

	public void setArangeId(String arangeId) {
		this.arangeId = arangeId;
	}

	public String getCompSit() {
		return this.compSit;
	}

	public void setCompSit(String compSit) {
		this.compSit = compSit;
	}

	public String getCrdReq() {
		return this.crdReq;
	}

	public void setCrdReq(String crdReq) {
		this.crdReq = crdReq;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSchId() {
		return this.schId;
	}

	public void setSchId(BigDecimal schId) {
		this.schId = schId;
	}

	public String getStat() {
		return this.stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

}