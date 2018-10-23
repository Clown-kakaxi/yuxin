package com.yuchengtech.bcrm.customer.model;

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
 * The persistent class for the ACRM_F_CI_ORG_ASSET_DEBT database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ORG_ASSET_DEBT")
public class AcrmFCiOrgAssetDebt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private BigDecimal amt;

	@Column(name="ASSET_DEBT_TYPE")
	private String assetDebtType;

	private String curr;

	@Column(name="CUST_ID")
	private String custId;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Column(name="ITEM_ID")
	private String itemId;

	@Column(name="REPORT_TYPE")
	private String reportType;

	@Column(name="STATIS_CYCLE")
	private String statisCycle;

    public AcrmFCiOrgAssetDebt() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getAssetDebtType() {
		return this.assetDebtType;
	}

	public void setAssetDebtType(String assetDebtType) {
		this.assetDebtType = assetDebtType;
	}

	public String getCurr() {
		return this.curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getReportType() {
		return this.reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getStatisCycle() {
		return this.statisCycle;
	}

	public void setStatisCycle(String statisCycle) {
		this.statisCycle = statisCycle;
	}

}