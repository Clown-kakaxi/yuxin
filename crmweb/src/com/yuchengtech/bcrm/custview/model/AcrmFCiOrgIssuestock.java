package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_ORG_ISSUESTOCK database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ORG_ISSUESTOCK")
public class AcrmFCiOrgIssuestock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_ORG_ISSUESTOCK_ISSUESTOCKID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_ORG_ISSUESTOCK_ISSUESTOCKID_GENERATOR")
	@Column(name="ISSUE_STOCK_ID")
	private String issueStockId;

	@Column(name="ALLOTMENT_SHARE_AMT")
	private BigDecimal allotmentShareAmt;

	@Column(name="CURR_STOCK_NUM")
	private BigDecimal currStockNum;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="EXCHANGE_CODE")
	private String exchangeCode;

	@Column(name="EXCHANGE_COUNTRY_CODE")
	private String exchangeCountryCode;

	@Column(name="EXCHANGE_NAME")
	private String exchangeName;

	@Column(name="FLOW_STOCK_NUM")
	private BigDecimal flowStockNum;

    @Temporal( TemporalType.DATE)
	@Column(name="IPO_DATE")
	private Date ipoDate;

	@Column(name="ISSUE_STOCK_NUM")
	private BigDecimal issueStockNum;

	@Column(name="ISSUE_STOCK_PRICE")
	private BigDecimal issueStockPrice;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="MARKET_PLACE")
	private String marketPlace;

	@Column(name="NETASSET_PER_SHARE")
	private BigDecimal netassetPerShare;

	private BigDecimal oncf;

	private String remark;

	@Column(name="SHAREHOLDER_NUM")
	private BigDecimal shareholderNum;

	@Column(name="STOCK_CODE")
	private String stockCode;

	@Column(name="STOCK_NAME")
	private String stockName;

	@Column(name="STOCK_STATE")
	private String stockState;

	@Column(name="STOCK_TYPE")
	private String stockType;

	@Column(name="STOCKT_KIND")
	private String stocktKind;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

    public AcrmFCiOrgIssuestock() {
    }

	public String getIssueStockId() {
		return this.issueStockId;
	}

	public void setIssueStockId(String issueStockId) {
		this.issueStockId = issueStockId;
	}

	public BigDecimal getAllotmentShareAmt() {
		return this.allotmentShareAmt;
	}

	public void setAllotmentShareAmt(BigDecimal allotmentShareAmt) {
		this.allotmentShareAmt = allotmentShareAmt;
	}

	public BigDecimal getCurrStockNum() {
		return this.currStockNum;
	}

	public void setCurrStockNum(BigDecimal currStockNum) {
		this.currStockNum = currStockNum;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getExchangeCode() {
		return this.exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getExchangeCountryCode() {
		return this.exchangeCountryCode;
	}

	public void setExchangeCountryCode(String exchangeCountryCode) {
		this.exchangeCountryCode = exchangeCountryCode;
	}

	public String getExchangeName() {
		return this.exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public BigDecimal getFlowStockNum() {
		return this.flowStockNum;
	}

	public void setFlowStockNum(BigDecimal flowStockNum) {
		this.flowStockNum = flowStockNum;
	}

	public Date getIpoDate() {
		return this.ipoDate;
	}

	public void setIpoDate(Date ipoDate) {
		this.ipoDate = ipoDate;
	}

	public BigDecimal getIssueStockNum() {
		return this.issueStockNum;
	}

	public void setIssueStockNum(BigDecimal issueStockNum) {
		this.issueStockNum = issueStockNum;
	}

	public BigDecimal getIssueStockPrice() {
		return this.issueStockPrice;
	}

	public void setIssueStockPrice(BigDecimal issueStockPrice) {
		this.issueStockPrice = issueStockPrice;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getMarketPlace() {
		return this.marketPlace;
	}

	public void setMarketPlace(String marketPlace) {
		this.marketPlace = marketPlace;
	}

	public BigDecimal getNetassetPerShare() {
		return this.netassetPerShare;
	}

	public void setNetassetPerShare(BigDecimal netassetPerShare) {
		this.netassetPerShare = netassetPerShare;
	}

	public BigDecimal getOncf() {
		return this.oncf;
	}

	public void setOncf(BigDecimal oncf) {
		this.oncf = oncf;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getShareholderNum() {
		return this.shareholderNum;
	}

	public void setShareholderNum(BigDecimal shareholderNum) {
		this.shareholderNum = shareholderNum;
	}

	public String getStockCode() {
		return this.stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockState() {
		return this.stockState;
	}

	public void setStockState(String stockState) {
		this.stockState = stockState;
	}

	public String getStockType() {
		return this.stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getStocktKind() {
		return this.stocktKind;
	}

	public void setStocktKind(String stocktKind) {
		this.stocktKind = stocktKind;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}