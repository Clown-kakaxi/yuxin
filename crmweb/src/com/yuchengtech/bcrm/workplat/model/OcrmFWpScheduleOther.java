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
 * The persistent class for the OCRM_F_WP_SCHEDULE_OTHER database table.
 * 
 */
@Entity
@Table(name="OCRM_F_WP_SCHEDULE_OTHER")
public class OcrmFWpScheduleOther implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_WP_SCHEDULE_OTHER_OID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_WP_SCHEDULE_OTHER_OID_GENERATOR")
	@Column(name="O_ID")
	private Long oId;

	@Column(name="ARANGE_ID")
	private String arangeId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="OTH_COMP_REMARK")
	private String othCompRemark;

	@Column(name="OTH_SIT_REMARK")
	private String othSitRemark;

	@Column(name="SCH_ID")
	private BigDecimal schId;

	private String stat;

    public OcrmFWpScheduleOther() {
    }

	public Long getOId() {
		return this.oId;
	}

	public void setOId(Long oId) {
		this.oId = oId;
	}

	public String getArangeId() {
		return this.arangeId;
	}

	public void setArangeId(String arangeId) {
		this.arangeId = arangeId;
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

	public String getOthCompRemark() {
		return this.othCompRemark;
	}

	public void setOthCompRemark(String othCompRemark) {
		this.othCompRemark = othCompRemark;
	}

	public String getOthSitRemark() {
		return this.othSitRemark;
	}

	public void setOthSitRemark(String othSitRemark) {
		this.othSitRemark = othSitRemark;
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