package com.yuchengtech.emp.ecif.customer.entity.perevaluate;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perevaluate.Personpoint")
@Table(name="M_CI_PER_POINT")
public class Personpoint implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PERSON_POINT_ID", unique=true, nullable=false)
	private Long personPointId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="USABLE_POINT")
	private BigDecimal usablePoint;
	@Column(name="SUM_POINT")
	private BigDecimal sumPoint;
	@Column(name="TOTAL_POINT")
	private BigDecimal totalPoint;
	@Column(name="POINT_PERIOD")
	private String pointPeriod;
	@Column(name="START_DATE")
	private String startDate;
	@Column(name="PROFIT_SUM")
	private BigDecimal profitSum;
	@Column(name="BASE_POINT_SUM")
	private BigDecimal basePointSum;
	@Column(name="AWARD_POINT_SUM")
	private BigDecimal awardPointSum;
	@Column(name="USED_POINT_SUM")
	private BigDecimal usedPointSum;
	@Column(name="MONTH_USED_POINT_SUM")
	private BigDecimal monthUsedPointSum;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getPersonPointId() {
 return this.personPointId;
 }
 public void setPersonPointId(Long personPointId) {
  this.personPointId=personPointId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public BigDecimal getUsablePoint() {
 return this.usablePoint;
 }
 public void setUsablePoint(BigDecimal usablePoint) {
  this.usablePoint=usablePoint;
 }
 public BigDecimal getSumPoint() {
 return this.sumPoint;
 }
 public void setSumPoint(BigDecimal sumPoint) {
  this.sumPoint=sumPoint;
 }
 public BigDecimal getTotalPoint() {
 return this.totalPoint;
 }
 public void setTotalPoint(BigDecimal totalPoint) {
  this.totalPoint=totalPoint;
 }
 public String getPointPeriod() {
 return this.pointPeriod;
 }
 public void setPointPeriod(String pointPeriod) {
  this.pointPeriod=pointPeriod;
 }
 public String getStartDate() {
 return this.startDate;
 }
 public void setStartDate(String startDate) {
  this.startDate=startDate;
 }
 public BigDecimal getProfitSum() {
 return this.profitSum;
 }
 public void setProfitSum(BigDecimal profitSum) {
  this.profitSum=profitSum;
 }
 public BigDecimal getBasePointSum() {
 return this.basePointSum;
 }
 public void setBasePointSum(BigDecimal basePointSum) {
  this.basePointSum=basePointSum;
 }
 public BigDecimal getAwardPointSum() {
 return this.awardPointSum;
 }
 public void setAwardPointSum(BigDecimal awardPointSum) {
  this.awardPointSum=awardPointSum;
 }
 public BigDecimal getUsedPointSum() {
 return this.usedPointSum;
 }
 public void setUsedPointSum(BigDecimal usedPointSum) {
  this.usedPointSum=usedPointSum;
 }
 public BigDecimal getMonthUsedPointSum() {
 return this.monthUsedPointSum;
 }
 public void setMonthUsedPointSum(BigDecimal monthUsedPointSum) {
  this.monthUsedPointSum=monthUsedPointSum;
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

