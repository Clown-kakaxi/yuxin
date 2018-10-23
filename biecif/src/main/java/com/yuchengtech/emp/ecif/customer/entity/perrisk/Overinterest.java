package com.yuchengtech.emp.ecif.customer.entity.perrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perrisk.Overinterest")
@Table(name="M_CI_ORG_OVERINTEREST")
public class Overinterest implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="OVER_INTEREST_ID", unique=true, nullable=false)
	private Long overInterestId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="OVER_AMT")
	private BigDecimal overAmt;
	@Column(name="OVER_CURR")
	private String overCurr;
	@Column(name="LOCATION")
	private String location;
	@Column(name="DEBT_ORG")
	private String debtOrg;
	@Column(name="BEGIN_DATE")
	private String beginDate;
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
 public Long getOverInterestId() {
 return this.overInterestId;
 }
 public void setOverInterestId(Long overInterestId) {
  this.overInterestId=overInterestId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public BigDecimal getOverAmt() {
 return this.overAmt;
 }
 public void setOverAmt(BigDecimal overAmt) {
  this.overAmt=overAmt;
 }
 public String getOverCurr() {
 return this.overCurr;
 }
 public void setOverCurr(String overCurr) {
  this.overCurr=overCurr;
 }
 public String getLocation() {
 return this.location;
 }
 public void setLocation(String location) {
  this.location=location;
 }
 public String getDebtOrg() {
 return this.debtOrg;
 }
 public void setDebtOrg(String debtOrg) {
  this.debtOrg=debtOrg;
 }
 public String getBeginDate() {
 return this.beginDate;
 }
 public void setBeginDate(String beginDate) {
  this.beginDate=beginDate;
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

