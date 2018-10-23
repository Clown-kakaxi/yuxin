package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiOrgIssuestock entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_ISSUESTOCK")
public class MCiOrgIssuestock implements java.io.Serializable {

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

	// Constructors

	/** default constructor */
	public MCiOrgIssuestock() {
	}

	/** minimal constructor */
	public MCiOrgIssuestock(String issueStockId) {
		this.issueStockId = issueStockId;
	}

	/** full constructor */
	public MCiOrgIssuestock(String issueStockId, String custId,
			String exchangeCountryCode, String exchangeCode,
			String exchangeName, String marketPlace, Date ipoDate,
			String stockType, String stocktKind, String stockCode,
			String stockName, String stockState, BigDecimal shareholderNum,
			BigDecimal flowStockNum, BigDecimal currStockNum,
			BigDecimal issueStockNum, Double issueStockPrice,
			Double netassetPerShare, Double oncf, Double allotmentShareAmt,
			String remark, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
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
	}

	// Property accessors
	@Id
	@Column(name = "ISSUE_STOCK_ID", unique = true, nullable = false, length = 20)
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

}