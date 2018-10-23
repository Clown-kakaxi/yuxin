package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiPerBusiinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_BUSIINFO")
public class MCiPerBusiinfo implements java.io.Serializable {

	// Fields

	private String personBusiInfoId;
	private String custId;
	private String busiItemName;
	private String busiItemDesc;
	private String busiItemType;
	private String busiItemAddr;
	private String isCorpOwnership;
	private BigDecimal partnerNum;
	private Double stockPercent;
	private Double assetsAmt;
	private Double debtAmt;
	private Double inAmt;
	private Double outAmt;
	private Double yearSaleAmount;
	private String yearSaleCcy;
	private Double yearSaleIncome;
	private Double yearPay;
	private Double yearProfit;
	private Double monthSaleIncome;
	private Double monthPay;
	private Double monthProfit;
	private Double lastYearSaleAmt;
	private Double lastMonthSaleAmt;
	private Double dayMaxSaleAmt;
	private Double dayMinSaleAmt;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerBusiinfo() {
	}

	/** minimal constructor */
	public MCiPerBusiinfo(String personBusiInfoId) {
		this.personBusiInfoId = personBusiInfoId;
	}

	/** full constructor */
	public MCiPerBusiinfo(String personBusiInfoId, String custId,
			String busiItemName, String busiItemDesc, String busiItemType,
			String busiItemAddr, String isCorpOwnership, BigDecimal partnerNum,
			Double stockPercent, Double assetsAmt, Double debtAmt,
			Double inAmt, Double outAmt, Double yearSaleAmount,
			String yearSaleCcy, Double yearSaleIncome, Double yearPay,
			Double yearProfit, Double monthSaleIncome, Double monthPay,
			Double monthProfit, Double lastYearSaleAmt,
			Double lastMonthSaleAmt, Double dayMaxSaleAmt,
			Double dayMinSaleAmt, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.personBusiInfoId = personBusiInfoId;
		this.custId = custId;
		this.busiItemName = busiItemName;
		this.busiItemDesc = busiItemDesc;
		this.busiItemType = busiItemType;
		this.busiItemAddr = busiItemAddr;
		this.isCorpOwnership = isCorpOwnership;
		this.partnerNum = partnerNum;
		this.stockPercent = stockPercent;
		this.assetsAmt = assetsAmt;
		this.debtAmt = debtAmt;
		this.inAmt = inAmt;
		this.outAmt = outAmt;
		this.yearSaleAmount = yearSaleAmount;
		this.yearSaleCcy = yearSaleCcy;
		this.yearSaleIncome = yearSaleIncome;
		this.yearPay = yearPay;
		this.yearProfit = yearProfit;
		this.monthSaleIncome = monthSaleIncome;
		this.monthPay = monthPay;
		this.monthProfit = monthProfit;
		this.lastYearSaleAmt = lastYearSaleAmt;
		this.lastMonthSaleAmt = lastMonthSaleAmt;
		this.dayMaxSaleAmt = dayMaxSaleAmt;
		this.dayMinSaleAmt = dayMinSaleAmt;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "PERSON_BUSI_INFO_ID", unique = true, nullable = false, length = 20)
	public String getPersonBusiInfoId() {
		return this.personBusiInfoId;
	}

	public void setPersonBusiInfoId(String personBusiInfoId) {
		this.personBusiInfoId = personBusiInfoId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "BUSI_ITEM_NAME", length = 80)
	public String getBusiItemName() {
		return this.busiItemName;
	}

	public void setBusiItemName(String busiItemName) {
		this.busiItemName = busiItemName;
	}

	@Column(name = "BUSI_ITEM_DESC")
	public String getBusiItemDesc() {
		return this.busiItemDesc;
	}

	public void setBusiItemDesc(String busiItemDesc) {
		this.busiItemDesc = busiItemDesc;
	}

	@Column(name = "BUSI_ITEM_TYPE", length = 20)
	public String getBusiItemType() {
		return this.busiItemType;
	}

	public void setBusiItemType(String busiItemType) {
		this.busiItemType = busiItemType;
	}

	@Column(name = "BUSI_ITEM_ADDR", length = 200)
	public String getBusiItemAddr() {
		return this.busiItemAddr;
	}

	public void setBusiItemAddr(String busiItemAddr) {
		this.busiItemAddr = busiItemAddr;
	}

	@Column(name = "IS_CORP_OWNERSHIP", length = 1)
	public String getIsCorpOwnership() {
		return this.isCorpOwnership;
	}

	public void setIsCorpOwnership(String isCorpOwnership) {
		this.isCorpOwnership = isCorpOwnership;
	}

	@Column(name = "PARTNER_NUM", precision = 22, scale = 0)
	public BigDecimal getPartnerNum() {
		return this.partnerNum;
	}

	public void setPartnerNum(BigDecimal partnerNum) {
		this.partnerNum = partnerNum;
	}

	@Column(name = "STOCK_PERCENT", precision = 10, scale = 4)
	public Double getStockPercent() {
		return this.stockPercent;
	}

	public void setStockPercent(Double stockPercent) {
		this.stockPercent = stockPercent;
	}

	@Column(name = "ASSETS_AMT", precision = 17)
	public Double getAssetsAmt() {
		return this.assetsAmt;
	}

	public void setAssetsAmt(Double assetsAmt) {
		this.assetsAmt = assetsAmt;
	}

	@Column(name = "DEBT_AMT", precision = 17)
	public Double getDebtAmt() {
		return this.debtAmt;
	}

	public void setDebtAmt(Double debtAmt) {
		this.debtAmt = debtAmt;
	}

	@Column(name = "IN_AMT", precision = 17)
	public Double getInAmt() {
		return this.inAmt;
	}

	public void setInAmt(Double inAmt) {
		this.inAmt = inAmt;
	}

	@Column(name = "OUT_AMT", precision = 17)
	public Double getOutAmt() {
		return this.outAmt;
	}

	public void setOutAmt(Double outAmt) {
		this.outAmt = outAmt;
	}

	@Column(name = "YEAR_SALE_AMOUNT", precision = 17)
	public Double getYearSaleAmount() {
		return this.yearSaleAmount;
	}

	public void setYearSaleAmount(Double yearSaleAmount) {
		this.yearSaleAmount = yearSaleAmount;
	}

	@Column(name = "YEAR_SALE_CCY", length = 20)
	public String getYearSaleCcy() {
		return this.yearSaleCcy;
	}

	public void setYearSaleCcy(String yearSaleCcy) {
		this.yearSaleCcy = yearSaleCcy;
	}

	@Column(name = "YEAR_SALE_INCOME", precision = 17)
	public Double getYearSaleIncome() {
		return this.yearSaleIncome;
	}

	public void setYearSaleIncome(Double yearSaleIncome) {
		this.yearSaleIncome = yearSaleIncome;
	}

	@Column(name = "YEAR_PAY", precision = 17)
	public Double getYearPay() {
		return this.yearPay;
	}

	public void setYearPay(Double yearPay) {
		this.yearPay = yearPay;
	}

	@Column(name = "YEAR_PROFIT", precision = 17)
	public Double getYearProfit() {
		return this.yearProfit;
	}

	public void setYearProfit(Double yearProfit) {
		this.yearProfit = yearProfit;
	}

	@Column(name = "MONTH_SALE_INCOME", precision = 17)
	public Double getMonthSaleIncome() {
		return this.monthSaleIncome;
	}

	public void setMonthSaleIncome(Double monthSaleIncome) {
		this.monthSaleIncome = monthSaleIncome;
	}

	@Column(name = "MONTH_PAY", precision = 17)
	public Double getMonthPay() {
		return this.monthPay;
	}

	public void setMonthPay(Double monthPay) {
		this.monthPay = monthPay;
	}

	@Column(name = "MONTH_PROFIT", precision = 17)
	public Double getMonthProfit() {
		return this.monthProfit;
	}

	public void setMonthProfit(Double monthProfit) {
		this.monthProfit = monthProfit;
	}

	@Column(name = "LAST_YEAR_SALE_AMT", precision = 17)
	public Double getLastYearSaleAmt() {
		return this.lastYearSaleAmt;
	}

	public void setLastYearSaleAmt(Double lastYearSaleAmt) {
		this.lastYearSaleAmt = lastYearSaleAmt;
	}

	@Column(name = "LAST_MONTH_SALE_AMT", precision = 17)
	public Double getLastMonthSaleAmt() {
		return this.lastMonthSaleAmt;
	}

	public void setLastMonthSaleAmt(Double lastMonthSaleAmt) {
		this.lastMonthSaleAmt = lastMonthSaleAmt;
	}

	@Column(name = "DAY_MAX_SALE_AMT", precision = 17)
	public Double getDayMaxSaleAmt() {
		return this.dayMaxSaleAmt;
	}

	public void setDayMaxSaleAmt(Double dayMaxSaleAmt) {
		this.dayMaxSaleAmt = dayMaxSaleAmt;
	}

	@Column(name = "DAY_MIN_SALE_AMT", precision = 17)
	public Double getDayMinSaleAmt() {
		return this.dayMinSaleAmt;
	}

	public void setDayMinSaleAmt(Double dayMinSaleAmt) {
		this.dayMinSaleAmt = dayMinSaleAmt;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}