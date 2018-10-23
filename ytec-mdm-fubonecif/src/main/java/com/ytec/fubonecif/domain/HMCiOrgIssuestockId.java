package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiOrgIssuestockId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgIssuestockId implements java.io.Serializable {

	// Fields

	private String issueStockId;
	private String custId;
	private String exchangeCountryCode;
	private String exchangeCode;
	private String exchangeName;
	private String marketPlace;
	private Date ipoDate;
	private String stockType;
	private String stocktKind;
	private String stockCode;
	private String stockName;
	private String stockState;
	private BigDecimal shareholderNum;
	private BigDecimal flowStockNum;
	private BigDecimal currStockNum;
	private BigDecimal issueStockNum;
	private Double issueStockPrice;
	private Double netassetPerShare;
	private Double oncf;
	private Double allotmentShareAmt;
	private String remark;
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
	public HMCiOrgIssuestockId() {
	}

	/** minimal constructor */
	public HMCiOrgIssuestockId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgIssuestockId(String issueStockId, String custId,
			String exchangeCountryCode, String exchangeCode,
			String exchangeName, String marketPlace, Date ipoDate,
			String stockType, String stocktKind, String stockCode,
			String stockName, String stockState, BigDecimal shareholderNum,
			BigDecimal flowStockNum, BigDecimal currStockNum,
			BigDecimal issueStockNum, Double issueStockPrice,
			Double netassetPerShare, Double oncf, Double allotmentShareAmt,
			String remark, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.issueStockId = issueStockId;
		this.custId = custId;
		this.exchangeCountryCode = exchangeCountryCode;
		this.exchangeCode = exchangeCode;
		this.exchangeName = exchangeName;
		this.marketPlace = marketPlace;
		this.ipoDate = ipoDate;
		this.stockType = stockType;
		this.stocktKind = stocktKind;
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.stockState = stockState;
		this.shareholderNum = shareholderNum;
		this.flowStockNum = flowStockNum;
		this.currStockNum = currStockNum;
		this.issueStockNum = issueStockNum;
		this.issueStockPrice = issueStockPrice;
		this.netassetPerShare = netassetPerShare;
		this.oncf = oncf;
		this.allotmentShareAmt = allotmentShareAmt;
		this.remark = remark;
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

	@Column(name = "ISSUE_STOCK_ID", length = 20)
	public String getIssueStockId() {
		return this.issueStockId;
	}

	public void setIssueStockId(String issueStockId) {
		this.issueStockId = issueStockId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "EXCHANGE_COUNTRY_CODE", length = 20)
	public String getExchangeCountryCode() {
		return this.exchangeCountryCode;
	}

	public void setExchangeCountryCode(String exchangeCountryCode) {
		this.exchangeCountryCode = exchangeCountryCode;
	}

	@Column(name = "EXCHANGE_CODE", length = 20)
	public String getExchangeCode() {
		return this.exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	@Column(name = "EXCHANGE_NAME", length = 80)
	public String getExchangeName() {
		return this.exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	@Column(name = "MARKET_PLACE", length = 20)
	public String getMarketPlace() {
		return this.marketPlace;
	}

	public void setMarketPlace(String marketPlace) {
		this.marketPlace = marketPlace;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "IPO_DATE", length = 7)
	public Date getIpoDate() {
		return this.ipoDate;
	}

	public void setIpoDate(Date ipoDate) {
		this.ipoDate = ipoDate;
	}

	@Column(name = "STOCK_TYPE", length = 20)
	public String getStockType() {
		return this.stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	@Column(name = "STOCKT_KIND", length = 20)
	public String getStocktKind() {
		return this.stocktKind;
	}

	public void setStocktKind(String stocktKind) {
		this.stocktKind = stocktKind;
	}

	@Column(name = "STOCK_CODE", length = 32)
	public String getStockCode() {
		return this.stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	@Column(name = "STOCK_NAME", length = 80)
	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name = "STOCK_STATE", length = 20)
	public String getStockState() {
		return this.stockState;
	}

	public void setStockState(String stockState) {
		this.stockState = stockState;
	}

	@Column(name = "SHAREHOLDER_NUM", precision = 22, scale = 0)
	public BigDecimal getShareholderNum() {
		return this.shareholderNum;
	}

	public void setShareholderNum(BigDecimal shareholderNum) {
		this.shareholderNum = shareholderNum;
	}

	@Column(name = "FLOW_STOCK_NUM", scale = 0)
	public BigDecimal getFlowStockNum() {
		return this.flowStockNum;
	}

	public void setFlowStockNum(BigDecimal flowStockNum) {
		this.flowStockNum = flowStockNum;
	}

	@Column(name = "CURR_STOCK_NUM", scale = 0)
	public BigDecimal getCurrStockNum() {
		return this.currStockNum;
	}

	public void setCurrStockNum(BigDecimal currStockNum) {
		this.currStockNum = currStockNum;
	}

	@Column(name = "ISSUE_STOCK_NUM", scale = 0)
	public BigDecimal getIssueStockNum() {
		return this.issueStockNum;
	}

	public void setIssueStockNum(BigDecimal issueStockNum) {
		this.issueStockNum = issueStockNum;
	}

	@Column(name = "ISSUE_STOCK_PRICE", precision = 12)
	public Double getIssueStockPrice() {
		return this.issueStockPrice;
	}

	public void setIssueStockPrice(Double issueStockPrice) {
		this.issueStockPrice = issueStockPrice;
	}

	@Column(name = "NETASSET_PER_SHARE", precision = 17)
	public Double getNetassetPerShare() {
		return this.netassetPerShare;
	}

	public void setNetassetPerShare(Double netassetPerShare) {
		this.netassetPerShare = netassetPerShare;
	}

	@Column(name = "ONCF", precision = 17)
	public Double getOncf() {
		return this.oncf;
	}

	public void setOncf(Double oncf) {
		this.oncf = oncf;
	}

	@Column(name = "ALLOTMENT_SHARE_AMT", precision = 17)
	public Double getAllotmentShareAmt() {
		return this.allotmentShareAmt;
	}

	public void setAllotmentShareAmt(Double allotmentShareAmt) {
		this.allotmentShareAmt = allotmentShareAmt;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		if (!(other instanceof HMCiOrgIssuestockId))
			return false;
		HMCiOrgIssuestockId castOther = (HMCiOrgIssuestockId) other;

		return ((this.getIssueStockId() == castOther.getIssueStockId()) || (this
				.getIssueStockId() != null
				&& castOther.getIssueStockId() != null && this
				.getIssueStockId().equals(castOther.getIssueStockId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getExchangeCountryCode() == castOther
						.getExchangeCountryCode()) || (this
						.getExchangeCountryCode() != null
						&& castOther.getExchangeCountryCode() != null && this
						.getExchangeCountryCode().equals(
								castOther.getExchangeCountryCode())))
				&& ((this.getExchangeCode() == castOther.getExchangeCode()) || (this
						.getExchangeCode() != null
						&& castOther.getExchangeCode() != null && this
						.getExchangeCode().equals(castOther.getExchangeCode())))
				&& ((this.getExchangeName() == castOther.getExchangeName()) || (this
						.getExchangeName() != null
						&& castOther.getExchangeName() != null && this
						.getExchangeName().equals(castOther.getExchangeName())))
				&& ((this.getMarketPlace() == castOther.getMarketPlace()) || (this
						.getMarketPlace() != null
						&& castOther.getMarketPlace() != null && this
						.getMarketPlace().equals(castOther.getMarketPlace())))
				&& ((this.getIpoDate() == castOther.getIpoDate()) || (this
						.getIpoDate() != null
						&& castOther.getIpoDate() != null && this.getIpoDate()
						.equals(castOther.getIpoDate())))
				&& ((this.getStockType() == castOther.getStockType()) || (this
						.getStockType() != null
						&& castOther.getStockType() != null && this
						.getStockType().equals(castOther.getStockType())))
				&& ((this.getStocktKind() == castOther.getStocktKind()) || (this
						.getStocktKind() != null
						&& castOther.getStocktKind() != null && this
						.getStocktKind().equals(castOther.getStocktKind())))
				&& ((this.getStockCode() == castOther.getStockCode()) || (this
						.getStockCode() != null
						&& castOther.getStockCode() != null && this
						.getStockCode().equals(castOther.getStockCode())))
				&& ((this.getStockName() == castOther.getStockName()) || (this
						.getStockName() != null
						&& castOther.getStockName() != null && this
						.getStockName().equals(castOther.getStockName())))
				&& ((this.getStockState() == castOther.getStockState()) || (this
						.getStockState() != null
						&& castOther.getStockState() != null && this
						.getStockState().equals(castOther.getStockState())))
				&& ((this.getShareholderNum() == castOther.getShareholderNum()) || (this
						.getShareholderNum() != null
						&& castOther.getShareholderNum() != null && this
						.getShareholderNum().equals(
								castOther.getShareholderNum())))
				&& ((this.getFlowStockNum() == castOther.getFlowStockNum()) || (this
						.getFlowStockNum() != null
						&& castOther.getFlowStockNum() != null && this
						.getFlowStockNum().equals(castOther.getFlowStockNum())))
				&& ((this.getCurrStockNum() == castOther.getCurrStockNum()) || (this
						.getCurrStockNum() != null
						&& castOther.getCurrStockNum() != null && this
						.getCurrStockNum().equals(castOther.getCurrStockNum())))
				&& ((this.getIssueStockNum() == castOther.getIssueStockNum()) || (this
						.getIssueStockNum() != null
						&& castOther.getIssueStockNum() != null && this
						.getIssueStockNum()
						.equals(castOther.getIssueStockNum())))
				&& ((this.getIssueStockPrice() == castOther
						.getIssueStockPrice()) || (this.getIssueStockPrice() != null
						&& castOther.getIssueStockPrice() != null && this
						.getIssueStockPrice().equals(
								castOther.getIssueStockPrice())))
				&& ((this.getNetassetPerShare() == castOther
						.getNetassetPerShare()) || (this.getNetassetPerShare() != null
						&& castOther.getNetassetPerShare() != null && this
						.getNetassetPerShare().equals(
								castOther.getNetassetPerShare())))
				&& ((this.getOncf() == castOther.getOncf()) || (this.getOncf() != null
						&& castOther.getOncf() != null && this.getOncf()
						.equals(castOther.getOncf())))
				&& ((this.getAllotmentShareAmt() == castOther
						.getAllotmentShareAmt()) || (this
						.getAllotmentShareAmt() != null
						&& castOther.getAllotmentShareAmt() != null && this
						.getAllotmentShareAmt().equals(
								castOther.getAllotmentShareAmt())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())))
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
				+ (getIssueStockId() == null ? 0 : this.getIssueStockId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getExchangeCountryCode() == null ? 0 : this
						.getExchangeCountryCode().hashCode());
		result = 37
				* result
				+ (getExchangeCode() == null ? 0 : this.getExchangeCode()
						.hashCode());
		result = 37
				* result
				+ (getExchangeName() == null ? 0 : this.getExchangeName()
						.hashCode());
		result = 37
				* result
				+ (getMarketPlace() == null ? 0 : this.getMarketPlace()
						.hashCode());
		result = 37 * result
				+ (getIpoDate() == null ? 0 : this.getIpoDate().hashCode());
		result = 37 * result
				+ (getStockType() == null ? 0 : this.getStockType().hashCode());
		result = 37
				* result
				+ (getStocktKind() == null ? 0 : this.getStocktKind()
						.hashCode());
		result = 37 * result
				+ (getStockCode() == null ? 0 : this.getStockCode().hashCode());
		result = 37 * result
				+ (getStockName() == null ? 0 : this.getStockName().hashCode());
		result = 37
				* result
				+ (getStockState() == null ? 0 : this.getStockState()
						.hashCode());
		result = 37
				* result
				+ (getShareholderNum() == null ? 0 : this.getShareholderNum()
						.hashCode());
		result = 37
				* result
				+ (getFlowStockNum() == null ? 0 : this.getFlowStockNum()
						.hashCode());
		result = 37
				* result
				+ (getCurrStockNum() == null ? 0 : this.getCurrStockNum()
						.hashCode());
		result = 37
				* result
				+ (getIssueStockNum() == null ? 0 : this.getIssueStockNum()
						.hashCode());
		result = 37
				* result
				+ (getIssueStockPrice() == null ? 0 : this.getIssueStockPrice()
						.hashCode());
		result = 37
				* result
				+ (getNetassetPerShare() == null ? 0 : this
						.getNetassetPerShare().hashCode());
		result = 37 * result
				+ (getOncf() == null ? 0 : this.getOncf().hashCode());
		result = 37
				* result
				+ (getAllotmentShareAmt() == null ? 0 : this
						.getAllotmentShareAmt().hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
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