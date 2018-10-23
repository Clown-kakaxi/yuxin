package com.yuchengtech.emp.ecif.customer.entity.orgrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgrisk.Defaultinfo")
@Table(name="M_CI_BADRECORD")
public class Defaultinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="BAD_RECORD_ID", unique=true, nullable=false)
	private Long badRecordId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="BAD_REC_NO")
	private String badRecNo;
	@Column(name="BAD_REC_TYPE")
	private String badRecType;
	@Column(name="BAD_REC_DESC")
	private String badRecDesc;
	@Column(name="BAD_REC_LEVEL")
	private String badRecLevel;
	@Column(name="BAD_REC_DATE")
	private String badRecDate;
	@Column(name="BAD_ACCT_NO")
	private String badAcctNo;
	@Column(name="BAD_ACCT_NAME")
	private String badAcctName;
	@Column(name="BAD_ACCT_STAT")
	private String badAcctStat;
	@Column(name="BAD_ACCT_CURR")
	private String badAcctCurr;
	@Column(name="BAD_ACCT_AMT")
	private BigDecimal badAcctAmt;
	@Column(name="BAD_ACCT_BAL")
	private BigDecimal badAcctBal;
	@Column(name="OCCUR_AREA_CODE")
	private String occurAreaCode;
	@Column(name="OCCUR_BRANCH_NO")
	private String occurBranchNo;
	@Column(name="DEAL_INFO")
	private String dealInfo;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getBadRecordId() {
 return this.badRecordId;
 }
 public void setBadRecordId(Long badRecordId) {
  this.badRecordId=badRecordId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getBadRecNo() {
 return this.badRecNo;
 }
 public void setBadRecNo(String badRecNo) {
  this.badRecNo=badRecNo;
 }
 public String getBadRecType() {
 return this.badRecType;
 }
 public void setBadRecType(String badRecType) {
  this.badRecType=badRecType;
 }
 public String getBadRecDesc() {
 return this.badRecDesc;
 }
 public void setBadRecDesc(String badRecDesc) {
  this.badRecDesc=badRecDesc;
 }
 public String getBadRecLevel() {
 return this.badRecLevel;
 }
 public void setBadRecLevel(String badRecLevel) {
  this.badRecLevel=badRecLevel;
 }
 public String getBadRecDate() {
 return this.badRecDate;
 }
 public void setBadRecDate(String badRecDate) {
  this.badRecDate=badRecDate;
 }
 public String getBadAcctNo() {
 return this.badAcctNo;
 }
 public void setBadAcctNo(String badAcctNo) {
  this.badAcctNo=badAcctNo;
 }
 public String getBadAcctName() {
 return this.badAcctName;
 }
 public void setBadAcctName(String badAcctName) {
  this.badAcctName=badAcctName;
 }
 public String getBadAcctStat() {
 return this.badAcctStat;
 }
 public void setBadAcctStat(String badAcctStat) {
  this.badAcctStat=badAcctStat;
 }
 public String getBadAcctCurr() {
 return this.badAcctCurr;
 }
 public void setBadAcctCurr(String badAcctCurr) {
  this.badAcctCurr=badAcctCurr;
 }
 public BigDecimal getBadAcctAmt() {
 return this.badAcctAmt;
 }
 public void setBadAcctAmt(BigDecimal badAcctAmt) {
  this.badAcctAmt=badAcctAmt;
 }
 public BigDecimal getBadAcctBal() {
 return this.badAcctBal;
 }
 public void setBadAcctBal(BigDecimal badAcctBal) {
  this.badAcctBal=badAcctBal;
 }
 public String getOccurAreaCode() {
 return this.occurAreaCode;
 }
 public void setOccurAreaCode(String occurAreaCode) {
  this.occurAreaCode=occurAreaCode;
 }
 public String getOccurBranchNo() {
 return this.occurBranchNo;
 }
 public void setOccurBranchNo(String occurBranchNo) {
  this.occurBranchNo=occurBranchNo;
 }
 public String getDealInfo() {
 return this.dealInfo;
 }
 public void setDealInfo(String dealInfo) {
  this.dealInfo=dealInfo;
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

