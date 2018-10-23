package com.yuchengtech.emp.ecif.customer.entity.perfinance;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perfinance.Personbusiinfo")
@Table(name="M_CI_PER_BUSIINFO")
public class Personbusiinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PERSON_BUSI_INFO_ID", unique=true, nullable=false)
	private Long personBusiInfoId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="BUSI_ITEM_NAME")
	private String busiItemName;
	@Column(name="BUSI_ITEM_DESC")
	private String busiItemDesc;
	@Column(name="BUSI_ITEM_TYPE")
	private String busiItemType;
	@Column(name="BUSI_ITEM_ADDRESS")
	private String busiItemAddress;
	@Column(name="IS_CORP_OWNERSHIP")
	private String isCorpOwnership;
	@Column(name="PARTNER_NUM")
	private Long partnerNum;
	@Column(name="STOCK_PERCENT")
	private BigDecimal stockPercent;
	@Column(name="ASSETS_AMT")
	private BigDecimal assetsAmt;
	@Column(name="DEBT_AMT")
	private BigDecimal debtAmt;
	@Column(name="IN_AMT")
	private BigDecimal inAmt;
	@Column(name="OUT_AMT")
	private BigDecimal outAmt;
	@Column(name="YEAR_SALE_INCOME")
	private BigDecimal yearSaleIncome;
	@Column(name="YEAR_PAY")
	private BigDecimal yearPay;
	@Column(name="YEAR_PROFIT")
	private BigDecimal yearProfit;
	@Column(name="MONTH_SALE_INCOME")
	private BigDecimal monthSaleIncome;
	@Column(name="MONTH_PAY")
	private BigDecimal monthPay;
	@Column(name="MONTH_PROFIT")
	private BigDecimal monthProfit;
	@Column(name="LAST_YEAR_SALE_AMT")
	private String lastYearSaleAmt;
	@Column(name="LAST_MONTH_SALE_AMT")
	private BigDecimal lastMonthSaleAmt;
	@Column(name="DAY_MAX_SALE_AMT")
	private BigDecimal dayMaxSaleAmt;
	@Column(name="DAY_MIN_SALE_AMT")
	private BigDecimal dayMinSaleAmt;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getPersonBusiInfoId() {
 return this.personBusiInfoId;
 }
 public void setPersonBusiInfoId(Long personBusiInfoId) {
  this.personBusiInfoId=personBusiInfoId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getBusiItemName() {
 return this.busiItemName;
 }
 public void setBusiItemName(String busiItemName) {
  this.busiItemName=busiItemName;
 }
 public String getBusiItemDesc() {
 return this.busiItemDesc;
 }
 public void setBusiItemDesc(String busiItemDesc) {
  this.busiItemDesc=busiItemDesc;
 }
 public String getBusiItemType() {
 return this.busiItemType;
 }
 public void setBusiItemType(String busiItemType) {
  this.busiItemType=busiItemType;
 }
 public String getBusiItemAddress() {
 return this.busiItemAddress;
 }
 public void setBusiItemAddress(String busiItemAddress) {
  this.busiItemAddress=busiItemAddress;
 }
 public String getIsCorpOwnership() {
 return this.isCorpOwnership;
 }
 public void setIsCorpOwnership(String isCorpOwnership) {
  this.isCorpOwnership=isCorpOwnership;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getPartnerNum() {
 return this.partnerNum;
 }
 public void setPartnerNum(Long partnerNum) {
  this.partnerNum=partnerNum;
 }
 public BigDecimal getStockPercent() {
 return this.stockPercent;
 }
 public void setStockPercent(BigDecimal stockPercent) {
  this.stockPercent=stockPercent;
 }
 public BigDecimal getAssetsAmt() {
 return this.assetsAmt;
 }
 public void setAssetsAmt(BigDecimal assetsAmt) {
  this.assetsAmt=assetsAmt;
 }
 public BigDecimal getDebtAmt() {
 return this.debtAmt;
 }
 public void setDebtAmt(BigDecimal debtAmt) {
  this.debtAmt=debtAmt;
 }
 public BigDecimal getInAmt() {
 return this.inAmt;
 }
 public void setInAmt(BigDecimal inAmt) {
  this.inAmt=inAmt;
 }
 public BigDecimal getOutAmt() {
 return this.outAmt;
 }
 public void setOutAmt(BigDecimal outAmt) {
  this.outAmt=outAmt;
 }
 public BigDecimal getYearSaleIncome() {
 return this.yearSaleIncome;
 }
 public void setYearSaleIncome(BigDecimal yearSaleIncome) {
  this.yearSaleIncome=yearSaleIncome;
 }
 public BigDecimal getYearPay() {
 return this.yearPay;
 }
 public void setYearPay(BigDecimal yearPay) {
  this.yearPay=yearPay;
 }
 public BigDecimal getYearProfit() {
 return this.yearProfit;
 }
 public void setYearProfit(BigDecimal yearProfit) {
  this.yearProfit=yearProfit;
 }
 public BigDecimal getMonthSaleIncome() {
 return this.monthSaleIncome;
 }
 public void setMonthSaleIncome(BigDecimal monthSaleIncome) {
  this.monthSaleIncome=monthSaleIncome;
 }
 public BigDecimal getMonthPay() {
 return this.monthPay;
 }
 public void setMonthPay(BigDecimal monthPay) {
  this.monthPay=monthPay;
 }
 public BigDecimal getMonthProfit() {
 return this.monthProfit;
 }
 public void setMonthProfit(BigDecimal monthProfit) {
  this.monthProfit=monthProfit;
 }
 public String getLastYearSaleAmt() {
 return this.lastYearSaleAmt;
 }
 public void setLastYearSaleAmt(String lastYearSaleAmt) {
  this.lastYearSaleAmt=lastYearSaleAmt;
 }
 public BigDecimal getLastMonthSaleAmt() {
 return this.lastMonthSaleAmt;
 }
 public void setLastMonthSaleAmt(BigDecimal lastMonthSaleAmt) {
  this.lastMonthSaleAmt=lastMonthSaleAmt;
 }
 public BigDecimal getDayMaxSaleAmt() {
 return this.dayMaxSaleAmt;
 }
 public void setDayMaxSaleAmt(BigDecimal dayMaxSaleAmt) {
  this.dayMaxSaleAmt=dayMaxSaleAmt;
 }
 public BigDecimal getDayMinSaleAmt() {
 return this.dayMinSaleAmt;
 }
 public void setDayMinSaleAmt(BigDecimal dayMinSaleAmt) {
  this.dayMinSaleAmt=dayMinSaleAmt;
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

