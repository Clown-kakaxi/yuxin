package com.yuchengtech.bcrm.model;

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
 * The persistent class for the ACRM_F_CI_GK_SAVE database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_GK_SAVE")
public class AcrmFCiGkSave implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="BNSY_AVG")
	private BigDecimal bnsyAvg;

	@Column(name="CUR_AC_BL")
	private BigDecimal curAcBl;

	@Column(name="CUR_SEASON_AVG")
	private BigDecimal curSeasonAvg;

	@Column(name="CUR_YEAR_AVG")
	private BigDecimal curYearAvg;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="DEPOSIT_TYPE")
	private String depositType;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Column(name="LAST_AC_BL")
	private BigDecimal lastAcBl;
	
	@Column(name="CUR_MONTH_AVG")
	private BigDecimal curMonthAvg;

	@Column(name="LAST_MONTH_AVG")
	private BigDecimal lastMonthAvg;

	@Column(name="LAST_SEASON_AVG")
	private BigDecimal lastSeasonAvg;

	@Column(name="LAST_YEAR_AVG")
	private BigDecimal lastYearAvg;

	@Column(name="SNSY_AVG")
	private BigDecimal snsyAvg;
	

    public AcrmFCiGkSave() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getBnsyAvg() {
		return this.bnsyAvg;
	}

	public void setBnsyAvg(BigDecimal bnsyAvg) {
		this.bnsyAvg = bnsyAvg;
	}

	public BigDecimal getCurAcBl() {
		return this.curAcBl;
	}

	public void setCurAcBl(BigDecimal curAcBl) {
		this.curAcBl = curAcBl;
	}

	public BigDecimal getCurSeasonAvg() {
		return this.curSeasonAvg;
	}

	public void setCurSeasonAvg(BigDecimal curSeasonAvg) {
		this.curSeasonAvg = curSeasonAvg;
	}

	public BigDecimal getCurYearAvg() {
		return this.curYearAvg;
	}

	public void setCurYearAvg(BigDecimal curYearAvg) {
		this.curYearAvg = curYearAvg;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getDepositType() {
		return this.depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public BigDecimal getLastAcBl() {
		return this.lastAcBl;
	}

	public void setLastAcBl(BigDecimal lastAcBl) {
		this.lastAcBl = lastAcBl;
	}

	public BigDecimal getLastMonthAvg() {
		return this.lastMonthAvg;
	}

	public void setLastMonthAvg(BigDecimal lastMonthAvg) {
		this.lastMonthAvg = lastMonthAvg;
	}

	public BigDecimal getLastSeasonAvg() {
		return this.lastSeasonAvg;
	}

	public void setLastSeasonAvg(BigDecimal lastSeasonAvg) {
		this.lastSeasonAvg = lastSeasonAvg;
	}

	public BigDecimal getLastYearAvg() {
		return this.lastYearAvg;
	}

	public void setLastYearAvg(BigDecimal lastYearAvg) {
		this.lastYearAvg = lastYearAvg;
	}

	public BigDecimal getSnsyAvg() {
		return this.snsyAvg;
	}

	public void setSnsyAvg(BigDecimal snsyAvg) {
		this.snsyAvg = snsyAvg;
	}

	public BigDecimal getCurMonthAvg() {
		return curMonthAvg;
	}

	public void setCurMonthAvg(BigDecimal curMonthAvg) {
		this.curMonthAvg = curMonthAvg;
	}
	

}