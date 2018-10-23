package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiPerBusiinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerBusiinfoId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiPerBusiinfoId() {
	}

	/** minimal constructor */
	public HMCiPerBusiinfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerBusiinfoId(String personBusiInfoId, String custId,
			String busiItemName, String busiItemDesc, String busiItemType,
			String busiItemAddr, String isCorpOwnership, BigDecimal partnerNum,
			Double stockPercent, Double assetsAmt, Double debtAmt,
			Double inAmt, Double outAmt, Double yearSaleAmount,
			String yearSaleCcy, Double yearSaleIncome, Double yearPay,
			Double yearProfit, Double monthSaleIncome, Double monthPay,
			Double monthProfit, Double lastYearSaleAmt,
			Double lastMonthSaleAmt, Double dayMaxSaleAmt,
			Double dayMinSaleAmt, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "PERSON_BUSI_INFO_ID", length = 20)
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

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiPerBusiinfoId))
			return false;
		HMCiPerBusiinfoId castOther = (HMCiPerBusiinfoId) other;

		return ((this.getPersonBusiInfoId() == castOther.getPersonBusiInfoId()) || (this
				.getPersonBusiInfoId() != null
				&& castOther.getPersonBusiInfoId() != null && this
				.getPersonBusiInfoId().equals(castOther.getPersonBusiInfoId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getBusiItemName() == castOther.getBusiItemName()) || (this
						.getBusiItemName() != null
						&& castOther.getBusiItemName() != null && this
						.getBusiItemName().equals(castOther.getBusiItemName())))
				&& ((this.getBusiItemDesc() == castOther.getBusiItemDesc()) || (this
						.getBusiItemDesc() != null
						&& castOther.getBusiItemDesc() != null && this
						.getBusiItemDesc().equals(castOther.getBusiItemDesc())))
				&& ((this.getBusiItemType() == castOther.getBusiItemType()) || (this
						.getBusiItemType() != null
						&& castOther.getBusiItemType() != null && this
						.getBusiItemType().equals(castOther.getBusiItemType())))
				&& ((this.getBusiItemAddr() == castOther.getBusiItemAddr()) || (this
						.getBusiItemAddr() != null
						&& castOther.getBusiItemAddr() != null && this
						.getBusiItemAddr().equals(castOther.getBusiItemAddr())))
				&& ((this.getIsCorpOwnership() == castOther
						.getIsCorpOwnership()) || (this.getIsCorpOwnership() != null
						&& castOther.getIsCorpOwnership() != null && this
						.getIsCorpOwnership().equals(
								castOther.getIsCorpOwnership())))
				&& ((this.getPartnerNum() == castOther.getPartnerNum()) || (this
						.getPartnerNum() != null
						&& castOther.getPartnerNum() != null && this
						.getPartnerNum().equals(castOther.getPartnerNum())))
				&& ((this.getStockPercent() == castOther.getStockPercent()) || (this
						.getStockPercent() != null
						&& castOther.getStockPercent() != null && this
						.getStockPercent().equals(castOther.getStockPercent())))
				&& ((this.getAssetsAmt() == castOther.getAssetsAmt()) || (this
						.getAssetsAmt() != null
						&& castOther.getAssetsAmt() != null && this
						.getAssetsAmt().equals(castOther.getAssetsAmt())))
				&& ((this.getDebtAmt() == castOther.getDebtAmt()) || (this
						.getDebtAmt() != null
						&& castOther.getDebtAmt() != null && this.getDebtAmt()
						.equals(castOther.getDebtAmt())))
				&& ((this.getInAmt() == castOther.getInAmt()) || (this
						.getInAmt() != null
						&& castOther.getInAmt() != null && this.getInAmt()
						.equals(castOther.getInAmt())))
				&& ((this.getOutAmt() == castOther.getOutAmt()) || (this
						.getOutAmt() != null
						&& castOther.getOutAmt() != null && this.getOutAmt()
						.equals(castOther.getOutAmt())))
				&& ((this.getYearSaleAmount() == castOther.getYearSaleAmount()) || (this
						.getYearSaleAmount() != null
						&& castOther.getYearSaleAmount() != null && this
						.getYearSaleAmount().equals(
								castOther.getYearSaleAmount())))
				&& ((this.getYearSaleCcy() == castOther.getYearSaleCcy()) || (this
						.getYearSaleCcy() != null
						&& castOther.getYearSaleCcy() != null && this
						.getYearSaleCcy().equals(castOther.getYearSaleCcy())))
				&& ((this.getYearSaleIncome() == castOther.getYearSaleIncome()) || (this
						.getYearSaleIncome() != null
						&& castOther.getYearSaleIncome() != null && this
						.getYearSaleIncome().equals(
								castOther.getYearSaleIncome())))
				&& ((this.getYearPay() == castOther.getYearPay()) || (this
						.getYearPay() != null
						&& castOther.getYearPay() != null && this.getYearPay()
						.equals(castOther.getYearPay())))
				&& ((this.getYearProfit() == castOther.getYearProfit()) || (this
						.getYearProfit() != null
						&& castOther.getYearProfit() != null && this
						.getYearProfit().equals(castOther.getYearProfit())))
				&& ((this.getMonthSaleIncome() == castOther
						.getMonthSaleIncome()) || (this.getMonthSaleIncome() != null
						&& castOther.getMonthSaleIncome() != null && this
						.getMonthSaleIncome().equals(
								castOther.getMonthSaleIncome())))
				&& ((this.getMonthPay() == castOther.getMonthPay()) || (this
						.getMonthPay() != null
						&& castOther.getMonthPay() != null && this
						.getMonthPay().equals(castOther.getMonthPay())))
				&& ((this.getMonthProfit() == castOther.getMonthProfit()) || (this
						.getMonthProfit() != null
						&& castOther.getMonthProfit() != null && this
						.getMonthProfit().equals(castOther.getMonthProfit())))
				&& ((this.getLastYearSaleAmt() == castOther
						.getLastYearSaleAmt()) || (this.getLastYearSaleAmt() != null
						&& castOther.getLastYearSaleAmt() != null && this
						.getLastYearSaleAmt().equals(
								castOther.getLastYearSaleAmt())))
				&& ((this.getLastMonthSaleAmt() == castOther
						.getLastMonthSaleAmt()) || (this.getLastMonthSaleAmt() != null
						&& castOther.getLastMonthSaleAmt() != null && this
						.getLastMonthSaleAmt().equals(
								castOther.getLastMonthSaleAmt())))
				&& ((this.getDayMaxSaleAmt() == castOther.getDayMaxSaleAmt()) || (this
						.getDayMaxSaleAmt() != null
						&& castOther.getDayMaxSaleAmt() != null && this
						.getDayMaxSaleAmt()
						.equals(castOther.getDayMaxSaleAmt())))
				&& ((this.getDayMinSaleAmt() == castOther.getDayMinSaleAmt()) || (this
						.getDayMinSaleAmt() != null
						&& castOther.getDayMinSaleAmt() != null && this
						.getDayMinSaleAmt()
						.equals(castOther.getDayMinSaleAmt())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getPersonBusiInfoId() == null ? 0 : this
						.getPersonBusiInfoId().hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getBusiItemName() == null ? 0 : this.getBusiItemName()
						.hashCode());
		result = 37
				* result
				+ (getBusiItemDesc() == null ? 0 : this.getBusiItemDesc()
						.hashCode());
		result = 37
				* result
				+ (getBusiItemType() == null ? 0 : this.getBusiItemType()
						.hashCode());
		result = 37
				* result
				+ (getBusiItemAddr() == null ? 0 : this.getBusiItemAddr()
						.hashCode());
		result = 37
				* result
				+ (getIsCorpOwnership() == null ? 0 : this.getIsCorpOwnership()
						.hashCode());
		result = 37
				* result
				+ (getPartnerNum() == null ? 0 : this.getPartnerNum()
						.hashCode());
		result = 37
				* result
				+ (getStockPercent() == null ? 0 : this.getStockPercent()
						.hashCode());
		result = 37 * result
				+ (getAssetsAmt() == null ? 0 : this.getAssetsAmt().hashCode());
		result = 37 * result
				+ (getDebtAmt() == null ? 0 : this.getDebtAmt().hashCode());
		result = 37 * result
				+ (getInAmt() == null ? 0 : this.getInAmt().hashCode());
		result = 37 * result
				+ (getOutAmt() == null ? 0 : this.getOutAmt().hashCode());
		result = 37
				* result
				+ (getYearSaleAmount() == null ? 0 : this.getYearSaleAmount()
						.hashCode());
		result = 37
				* result
				+ (getYearSaleCcy() == null ? 0 : this.getYearSaleCcy()
						.hashCode());
		result = 37
				* result
				+ (getYearSaleIncome() == null ? 0 : this.getYearSaleIncome()
						.hashCode());
		result = 37 * result
				+ (getYearPay() == null ? 0 : this.getYearPay().hashCode());
		result = 37
				* result
				+ (getYearProfit() == null ? 0 : this.getYearProfit()
						.hashCode());
		result = 37
				* result
				+ (getMonthSaleIncome() == null ? 0 : this.getMonthSaleIncome()
						.hashCode());
		result = 37 * result
				+ (getMonthPay() == null ? 0 : this.getMonthPay().hashCode());
		result = 37
				* result
				+ (getMonthProfit() == null ? 0 : this.getMonthProfit()
						.hashCode());
		result = 37
				* result
				+ (getLastYearSaleAmt() == null ? 0 : this.getLastYearSaleAmt()
						.hashCode());
		result = 37
				* result
				+ (getLastMonthSaleAmt() == null ? 0 : this
						.getLastMonthSaleAmt().hashCode());
		result = 37
				* result
				+ (getDayMaxSaleAmt() == null ? 0 : this.getDayMaxSaleAmt()
						.hashCode());
		result = 37
				* result
				+ (getDayMinSaleAmt() == null ? 0 : this.getDayMinSaleAmt()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}