package com.yuchengtech.emp.ecif.customer.entity.orgevaluate;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgevaluate.Scoreinfo")
@Table(name="M_CI_SCOREINFO")
public class Scoreinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="SCORE_INFO_ID", unique=true, nullable=false)
	private Long scoreInfoId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="SCORE_BRANCH_CODE")
	private String scoreBranchCode;
	@Column(name="SCORE_BRANCH_NAME")
	private String scoreBranchName;
	@Column(name="SCORE_TYPE")
	private String scoreType;
	@Column(name="SCORE_RESULT")
	private String scoreResult;
	@Column(name="SCORE_DATE")
	private String scoreDate;
	@Column(name="SCORE_PERIOD")
	private String scorePeriod;
	@Column(name="EFFECTIVE_DATE")
	private String effectiveDate;
	@Column(name="EXPIRED_DATE")
	private String expiredDate;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getScoreInfoId() {
 return this.scoreInfoId;
 }
 public void setScoreInfoId(Long scoreInfoId) {
  this.scoreInfoId=scoreInfoId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getScoreBranchCode() {
 return this.scoreBranchCode;
 }
 public void setScoreBranchCode(String scoreBranchCode) {
  this.scoreBranchCode=scoreBranchCode;
 }
 public String getScoreBranchName() {
 return this.scoreBranchName;
 }
 public void setScoreBranchName(String scoreBranchName) {
  this.scoreBranchName=scoreBranchName;
 }
 public String getScoreType() {
 return this.scoreType;
 }
 public void setScoreType(String scoreType) {
  this.scoreType=scoreType;
 }
 public String getScoreResult() {
 return this.scoreResult;
 }
 public void setScoreResult(String scoreResult) {
  this.scoreResult=scoreResult;
 }
 public String getScoreDate() {
 return this.scoreDate;
 }
 public void setScoreDate(String scoreDate) {
  this.scoreDate=scoreDate;
 }
 public String getScorePeriod() {
 return this.scorePeriod;
 }
 public void setScorePeriod(String scorePeriod) {
  this.scorePeriod=scorePeriod;
 }
 public String getEffectiveDate() {
 return this.effectiveDate;
 }
 public void setEffectiveDate(String effectiveDate) {
  this.effectiveDate=effectiveDate;
 }
 public String getExpiredDate() {
 return this.expiredDate;
 }
 public void setExpiredDate(String expiredDate) {
  this.expiredDate=expiredDate;
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

