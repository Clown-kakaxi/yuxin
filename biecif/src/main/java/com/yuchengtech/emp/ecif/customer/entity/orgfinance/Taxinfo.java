package com.yuchengtech.emp.ecif.customer.entity.orgfinance;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgfinance.Taxinfo")
@Table(name="M_CI_TAXINFO")
public class Taxinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="TAX_INFO_ID", unique=true, nullable=false)
	private Long taxInfoId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="TAX_TYPE")
	private String taxType;
	@Column(name="TAX_DATE")
	private String taxDate;
	@Column(name="START_DATE")
	private String startDate;
	@Column(name="END_DATE")
	private String endDate;
	@Column(name="TAX_CURRENCY")
	private String taxCurrency;
	@Column(name="TAX_AMT")
	private BigDecimal taxAmt;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getTaxInfoId() {
 return this.taxInfoId;
 }
 public void setTaxInfoId(Long taxInfoId) {
  this.taxInfoId=taxInfoId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getTaxType() {
 return this.taxType;
 }
 public void setTaxType(String taxType) {
  this.taxType=taxType;
 }
 public String getTaxDate() {
 return this.taxDate;
 }
 public void setTaxDate(String taxDate) {
  this.taxDate=taxDate;
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
 public String getTaxCurrency() {
 return this.taxCurrency;
 }
 public void setTaxCurrency(String taxCurrency) {
  this.taxCurrency=taxCurrency;
 }
 public BigDecimal getTaxAmt() {
 return this.taxAmt;
 }
 public void setTaxAmt(BigDecimal taxAmt) {
  this.taxAmt=taxAmt;
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

