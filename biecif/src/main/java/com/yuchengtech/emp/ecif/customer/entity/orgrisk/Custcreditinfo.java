package com.yuchengtech.emp.ecif.customer.entity.orgrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgrisk.Custcreditinfo")
@Table(name="M_CI_ORG_CREDITLINE")
public class Custcreditinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="CREDIT_ID", unique=true, nullable=false)
	private Long creditId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="CUST_NAME")
	private String custName;
	@Column(name="CREDIT_NO")
	private String creditNo;
	@Column(name="CREDIT_TYPE")
	private String creditType;
	@Column(name="MEASURE_CREDIT_LIMIT")
	private BigDecimal measureCreditLimit;
	@Column(name="SEND_CREDIT_LIMIT")
	private BigDecimal sendCreditLimit;
	@Column(name="LAST_CREDIT_LIMIT")
	private BigDecimal lastCreditLimit;
	@Column(name="CREDIT_CURRENCY")
	private String creditCurrency;
	@Column(name="CREDIT_START_DATE")
	private String creditStartDate;
	@Column(name="CREDIT_END_DATE")
	private String creditEndDate;
	@Column(name="IS_LOOP")
	private String isLoop;
	@Column(name="IS_VALID")
	private String isValid;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCreditId() {
 return this.creditId;
 }
 public void setCreditId(Long creditId) {
  this.creditId=creditId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getCustName() {
 return this.custName;
 }
 public void setCustName(String custName) {
  this.custName=custName;
 }
 public String getCreditNo() {
 return this.creditNo;
 }
 public void setCreditNo(String creditNo) {
  this.creditNo=creditNo;
 }
 public String getCreditType() {
 return this.creditType;
 }
 public void setCreditType(String creditType) {
  this.creditType=creditType;
 }
 public BigDecimal getMeasureCreditLimit() {
 return this.measureCreditLimit;
 }
 public void setMeasureCreditLimit(BigDecimal measureCreditLimit) {
  this.measureCreditLimit=measureCreditLimit;
 }
 public BigDecimal getSendCreditLimit() {
 return this.sendCreditLimit;
 }
 public void setSendCreditLimit(BigDecimal sendCreditLimit) {
  this.sendCreditLimit=sendCreditLimit;
 }
 public BigDecimal getLastCreditLimit() {
 return this.lastCreditLimit;
 }
 public void setLastCreditLimit(BigDecimal lastCreditLimit) {
  this.lastCreditLimit=lastCreditLimit;
 }
 public String getCreditCurrency() {
 return this.creditCurrency;
 }
 public void setCreditCurrency(String creditCurrency) {
  this.creditCurrency=creditCurrency;
 }
 public String getCreditStartDate() {
 return this.creditStartDate;
 }
 public void setCreditStartDate(String creditStartDate) {
  this.creditStartDate=creditStartDate;
 }
 public String getCreditEndDate() {
 return this.creditEndDate;
 }
 public void setCreditEndDate(String creditEndDate) {
  this.creditEndDate=creditEndDate;
 }
 public String getIsLoop() {
 return this.isLoop;
 }
 public void setIsLoop(String isLoop) {
  this.isLoop=isLoop;
 }
 public String getIsValid() {
 return this.isValid;
 }
 public void setIsValid(String isValid) {
  this.isValid=isValid;
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

