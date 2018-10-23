package com.yuchengtech.emp.ecif.report.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RPT_CORP_INFO_DETAIL database table.
 * 
 */
public class RptCorpInfoDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private String aumType;

	private String custType;

	private BigDecimal daySumAum;

	private Long daySumCust;

	private BigDecimal firstDaySumAum;

	private Long firstDaySumCust;

	private BigDecimal lastDaySumAum;

	private Long lastDaySumCust;

	private Date rptDate;

    public RptCorpInfoDetail() {
    }

	public String getAumType() {
		return this.aumType;
	}

	public void setAumType(String aumType) {
		this.aumType = aumType;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public BigDecimal getDaySumAum() {
		return this.daySumAum;
	}

	public void setDaySumAum(BigDecimal daySumAum) {
		this.daySumAum = daySumAum;
	}

	public Long getDaySumCust() {
		return this.daySumCust;
	}

	public void setDaySumCust(Long daySumCust) {
		this.daySumCust = daySumCust;
	}

	public BigDecimal getFirstDaySumAum() {
		return this.firstDaySumAum;
	}

	public void setFirstDaySumAum(BigDecimal firstDaySumAum) {
		this.firstDaySumAum = firstDaySumAum;
	}

	public Long getFirstDaySumCust() {
		return this.firstDaySumCust;
	}

	public void setFirstDaySumCust(Long firstDaySumCust) {
		this.firstDaySumCust = firstDaySumCust;
	}

	public BigDecimal getLastDaySumAum() {
		return this.lastDaySumAum;
	}

	public void setLastDaySumAum(BigDecimal lastDaySumAum) {
		this.lastDaySumAum = lastDaySumAum;
	}

	public Long getLastDaySumCust() {
		return this.lastDaySumCust;
	}

	public void setLastDaySumCust(Long lastDaySumCust) {
		this.lastDaySumCust = lastDaySumCust;
	}

	public Date getRptDate() {
		return this.rptDate;
	}

	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}

}