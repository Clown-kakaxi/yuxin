package com.yuchengtech.emp.ecif.customer.entity.orgfinance;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgfinance.Issuestock")
@Table(name="M_CI_ORG_ISSUESTOCK")
public class Issuestock implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ISSUE_STOCK_ID", unique=true, nullable=false)
	private Long issueStockId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="EXCHANGE_COUNTRY_CODE")
	private String exchangeCountryCode;
	@Column(name="EXCHANGE_CODE")
	private String exchangeCode;
	@Column(name="EXCHANGE_NAME")
	private String exchangeName;
	@Column(name="MARKET_PLACE")
	private String marketPlace;
	@Column(name="IPO_DATE")
	private String ipoDate;
	@Column(name="STOCK_TYPE")
	private String stockType;
	@Column(name="STOCKT_KIND")
	private String stocktKind;
	@Column(name="STOCK_CODE")
	private String stockCode;
	@Column(name="STOCK_NAME")
	private String stockName;
	@Column(name="SHAREHOLDER_NUM")
	private Long shareholderNum;
	@Column(name="FLOW_STOCK_NUM")
	private Long flowStockNum;
	@Column(name="CURR_STOCK_NUM")
	private Long currStockNum;
	@Column(name="ISSUE_STOCK_NUM")
	private Long issueStockNum;
	@Column(name="ISSUE_STOCK_PRICE")
	private BigDecimal issueStockPrice;
	@Column(name="NETASSET_PER_SHARE")
	private BigDecimal netassetPerShare;
	@Column(name="ALLOTMENT_SHARE_AMT")
	private BigDecimal allotmentShareAmt;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getIssueStockId() {
 return this.issueStockId;
 }
 public void setIssueStockId(Long issueStockId) {
  this.issueStockId=issueStockId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getExchangeCountryCode() {
 return this.exchangeCountryCode;
 }
 public void setExchangeCountryCode(String exchangeCountryCode) {
  this.exchangeCountryCode=exchangeCountryCode;
 }
 public String getExchangeCode() {
 return this.exchangeCode;
 }
 public void setExchangeCode(String exchangeCode) {
  this.exchangeCode=exchangeCode;
 }
 public String getExchangeName() {
 return this.exchangeName;
 }
 public void setExchangeName(String exchangeName) {
  this.exchangeName=exchangeName;
 }
 public String getMarketPlace() {
 return this.marketPlace;
 }
 public void setMarketPlace(String marketPlace) {
  this.marketPlace=marketPlace;
 }
 public String getIpoDate() {
 return this.ipoDate;
 }
 public void setIpoDate(String ipoDate) {
  this.ipoDate=ipoDate;
 }
 public String getStockType() {
 return this.stockType;
 }
 public void setStockType(String stockType) {
  this.stockType=stockType;
 }
 public String getStocktKind() {
 return this.stocktKind;
 }
 public void setStocktKind(String stocktKind) {
  this.stocktKind=stocktKind;
 }
 public String getStockCode() {
 return this.stockCode;
 }
 public void setStockCode(String stockCode) {
  this.stockCode=stockCode;
 }
 public String getStockName() {
 return this.stockName;
 }
 public void setStockName(String stockName) {
  this.stockName=stockName;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getShareholderNum() {
 return this.shareholderNum;
 }
 public void setShareholderNum(Long shareholderNum) {
  this.shareholderNum=shareholderNum;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getFlowStockNum() {
 return this.flowStockNum;
 }
 public void setFlowStockNum(Long flowStockNum) {
  this.flowStockNum=flowStockNum;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCurrStockNum() {
 return this.currStockNum;
 }
 public void setCurrStockNum(Long currStockNum) {
  this.currStockNum=currStockNum;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getIssueStockNum() {
 return this.issueStockNum;
 }
 public void setIssueStockNum(Long issueStockNum) {
  this.issueStockNum=issueStockNum;
 }
 public BigDecimal getIssueStockPrice() {
 return this.issueStockPrice;
 }
 public void setIssueStockPrice(BigDecimal issueStockPrice) {
  this.issueStockPrice=issueStockPrice;
 }
 public BigDecimal getNetassetPerShare() {
 return this.netassetPerShare;
 }
 public void setNetassetPerShare(BigDecimal netassetPerShare) {
  this.netassetPerShare=netassetPerShare;
 }
 public BigDecimal getAllotmentShareAmt() {
 return this.allotmentShareAmt;
 }
 public void setAllotmentShareAmt(BigDecimal allotmentShareAmt) {
  this.allotmentShareAmt=allotmentShareAmt;
 }
 public String getLastUpdateSys() {
 return this.lastUpdateSys;
 }
 public void setLastUpdateSys(String lastUpdateSys) {
  this.lastUpdateSys=lastUpdateSys;
 }
 public String getLastUpdateUser() {
 return this.lastUpdateUser;
 }
 public void setLastUpdateUser(String lastUpdateUser) {
  this.lastUpdateUser=lastUpdateUser;
 }
 public String getLastUpdateTm() {
 return this.lastUpdateTm;
 }
 public void setLastUpdateTm(String lastUpdateTm) {
  this.lastUpdateTm=lastUpdateTm;
 }
 public String getTxSeqNo() {
 return this.txSeqNo;
 }
 public void setTxSeqNo(String txSeqNo) {
  this.txSeqNo=txSeqNo;
 }
 }

