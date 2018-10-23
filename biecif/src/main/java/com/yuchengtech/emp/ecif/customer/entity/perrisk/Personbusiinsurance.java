package com.yuchengtech.emp.ecif.customer.entity.perrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perrisk.Personbusiinsurance")
@Table(name="M_CI_PER_BUSIINSURANCE")
public class Personbusiinsurance implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PERSON_BI_ID", unique=true, nullable=false)
	private Long personBiId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="BINAME")
	private String biname;
	@Column(name="BITYPE")
	private String bitype;
	@Column(name="INSUREDSUM")
	private BigDecimal insuredsum;
	@Column(name="MATURITY")
	private String maturity;
	@Column(name="CUSTOMERID")
	private String customerid;
	@Column(name="UNDERWRITER")
	private String underwriter;
	@Column(name="INSUREDATE")
	private String insuredate;
	@Column(name="CASHVALUE")
	private BigDecimal cashvalue;
	@Column(name="CANCELDATE")
	private String canceldate;
	@Column(name="SRC_SERIAL_NO")
	private String srcSerialNo;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getPersonBiId() {
 return this.personBiId;
 }
 public void setPersonBiId(Long personBiId) {
  this.personBiId=personBiId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getBiname() {
 return this.biname;
 }
 public void setBiname(String biname) {
  this.biname=biname;
 }
 public String getBitype() {
 return this.bitype;
 }
 public void setBitype(String bitype) {
  this.bitype=bitype;
 }
 public BigDecimal getInsuredsum() {
 return this.insuredsum;
 }
 public void setInsuredsum(BigDecimal insuredsum) {
  this.insuredsum=insuredsum;
 }
 public String getMaturity() {
 return this.maturity;
 }
 public void setMaturity(String maturity) {
  this.maturity=maturity;
 }
 public String getCustomerid() {
 return this.customerid;
 }
 public void setCustomerid(String customerid) {
  this.customerid=customerid;
 }
 public String getUnderwriter() {
 return this.underwriter;
 }
 public void setUnderwriter(String underwriter) {
  this.underwriter=underwriter;
 }
 public String getInsuredate() {
 return this.insuredate;
 }
 public void setInsuredate(String insuredate) {
  this.insuredate=insuredate;
 }
 public BigDecimal getCashvalue() {
 return this.cashvalue;
 }
 public void setCashvalue(BigDecimal cashvalue) {
  this.cashvalue=cashvalue;
 }
 public String getCanceldate() {
 return this.canceldate;
 }
 public void setCanceldate(String canceldate) {
  this.canceldate=canceldate;
 }
 public String getSrcSerialNo() {
 return this.srcSerialNo;
 }
 public void setSrcSerialNo(String srcSerialNo) {
  this.srcSerialNo=srcSerialNo;
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

