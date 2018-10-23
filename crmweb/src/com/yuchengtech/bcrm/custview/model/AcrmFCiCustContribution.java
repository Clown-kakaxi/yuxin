package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_CUST_CONTRIBUTION database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CUST_CONTRIBUTION")
public class AcrmFCiCustContribution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="CONTRI_DEPOSIT")
	private BigDecimal contriDeposit;

	@Column(name="CONTRIBUTION_CUST")
	private BigDecimal contributionCust;

	@Column(name="CONTRIBUTION_LOAN")
	private BigDecimal contributionLoan;

	@Column(name="CONTRIBUTION_MID")
	private BigDecimal contributionMid;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Column(name="ORG_ID")
	private String orgId;

    public AcrmFCiCustContribution() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getContriDeposit() {
		return this.contriDeposit;
	}

	public void setContriDeposit(BigDecimal contriDeposit) {
		this.contriDeposit = contriDeposit;
	}

	public BigDecimal getContributionCust() {
		return this.contributionCust;
	}

	public void setContributionCust(BigDecimal contributionCust) {
		this.contributionCust = contributionCust;
	}

	public BigDecimal getContributionLoan() {
		return this.contributionLoan;
	}

	public void setContributionLoan(BigDecimal contributionLoan) {
		this.contributionLoan = contributionLoan;
	}

	public BigDecimal getContributionMid() {
		return this.contributionMid;
	}

	public void setContributionMid(BigDecimal contributionMid) {
		this.contributionMid = contributionMid;
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

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}