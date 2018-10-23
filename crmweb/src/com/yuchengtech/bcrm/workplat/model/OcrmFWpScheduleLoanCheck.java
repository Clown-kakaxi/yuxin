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
 * The persistent class for the OCRM_F_WP_SCHEDULE_LOAN_CHECK database table.
 * 
 */
@Entity
@Table(name="OCRM_F_WP_SCHEDULE_LOAN_CHECK")
public class OcrmFWpScheduleLoanCheck implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_WP_SCHEDULE_LOAN_CHECK_LID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_WP_SCHEDULE_LOAN_CHECK_LID_GENERATOR")
	@Column(name="L_ID")
	private Long lId;

	@Column(name="ARANGE_ID")
	private String arangeId;

	@Column(name="CHECK_SIT")
	private String checkSit;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="LOAN_BAL")
	private String loanBal;

	private String remark;

	@Column(name="SCH_ID")
	private BigDecimal schId;

	private String stat;

    public OcrmFWpScheduleLoanCheck() {
    }

	public Long getLId() {
		return this.lId;
	}

	public void setLId(Long lId) {
		this.lId = lId;
	}

	public String getArangeId() {
		return this.arangeId;
	}

	public void setArangeId(String arangeId) {
		this.arangeId = arangeId;
	}

	public String getCheckSit() {
		return this.checkSit;
	}

	public void setCheckSit(String checkSit) {
		this.checkSit = checkSit;
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

	public String getLoanBal() {
		return this.loanBal;
	}

	public void setLoanBal(String loanBal) {
		this.loanBal = loanBal;
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