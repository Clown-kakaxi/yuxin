package com.yuchengtech.emp.ecif.customer.entity.perfinance;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perfinance.Personinvestment")
@Table(name="M_CI_PER_INVESTMENT")
public class Personinvestment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="INVESTMENT_ID", unique=true, nullable=false)
	private Long investmentId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="INVEST_AIM")
	private String investAim;
	@Column(name="INVEST_EXPECT")
	private String investExpect;
	@Column(name="INVEST_TYPE")
	private String investType;
	@Column(name="INVEST_AMT")
	private BigDecimal investAmt;
	@Column(name="INVEST_CURR")
	private String investCurr;
	@Column(name="INVEST_YIELD")
	private BigDecimal investYield;
	@Column(name="INVEST_INCOME")
	private BigDecimal investIncome;
	@Column(name="START_DATE")
	private String startDate;
	@Column(name="END_DATE")
	private String endDate;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getInvestmentId() {
 return this.investmentId;
 }
 public void setInvestmentId(Long investmentId) {
  this.investmentId=investmentId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getInvestAim() {
 return this.investAim;
 }
 public void setInvestAim(String investAim) {
  this.investAim=investAim;
 }
 public String getInvestExpect() {
 return this.investExpect;
 }
 public void setInvestExpect(String investExpect) {
  this.investExpect=investExpect;
 }
 public String getInvestType() {
 return this.investType;
 }
 public void setInvestType(String investType) {
  this.investType=investType;
 }
 public BigDecimal getInvestAmt() {
 return this.investAmt;
 }
 public void setInvestAmt(BigDecimal investAmt) {
  this.investAmt=investAmt;
 }
 public String getInvestCurr() {
 return this.investCurr;
 }
 public void setInvestCurr(String investCurr) {
  this.investCurr=investCurr;
 }
 public BigDecimal getInvestYield() {
 return this.investYield;
 }
 public void setInvestYield(BigDecimal investYield) {
  this.investYield=investYield;
 }
 public BigDecimal getInvestIncome() {
 return this.investIncome;
 }
 public void setInvestIncome(BigDecimal investIncome) {
  this.investIncome=investIncome;
 }
 public String getStartDate() {
 return this.startDate;
 }
 public void setStartDate(String startDate) {
  this.startDate=startDate;
 }
 public String getEndDate() {
 return this.endDate;
 }
 public void setEndDate(String endDate) {
  this.endDate=endDate;
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

