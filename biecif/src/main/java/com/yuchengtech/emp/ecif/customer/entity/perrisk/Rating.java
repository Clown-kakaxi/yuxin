package com.yuchengtech.emp.ecif.customer.entity.perrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perrisk.Rating")
@Table(name="M_CI_RATING")
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="RATING_ID", unique=true, nullable=false)
	private Long ratingId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="RATING_ORG_CODE")
	private String ratingOrgCode;
	@Column(name="RATING_ORG_NAME")
	private String ratingOrgName;
	@Column(name="RATING_TYPE")
	private String ratingType;
	@Column(name="RATING_RESULT")
	private String ratingResult;
	@Column(name="RATING_DATE")
	private String ratingDate;
	@Column(name="EFFECTIVE_DATE")
	private String effectiveDate;
	@Column(name="EXPIRED_DATE")
	private String expiredDate;
	@Column(name="VALID_FLAG")
	private String validFlag;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getRatingId() {
 return this.ratingId;
 }
 public void setRatingId(Long ratingId) {
  this.ratingId=ratingId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getRatingOrgCode() {
 return this.ratingOrgCode;
 }
 public void setRatingOrgCode(String ratingOrgCode) {
  this.ratingOrgCode=ratingOrgCode;
 }
 public String getRatingOrgName() {
 return this.ratingOrgName;
 }
 public void setRatingOrgName(String ratingOrgName) {
  this.ratingOrgName=ratingOrgName;
 }
 public String getRatingType() {
 return this.ratingType;
 }
 public void setRatingType(String ratingType) {
  this.ratingType=ratingType;
 }
 public String getRatingResult() {
 return this.ratingResult;
 }
 public void setRatingResult(String ratingResult) {
  this.ratingResult=ratingResult;
 }
 public String getRatingDate() {
 return this.ratingDate;
 }
 public void setRatingDate(String ratingDate) {
  this.ratingDate=ratingDate;
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
 public String getValidFlag() {
 return this.validFlag;
 }
 public void setValidFlag(String validFlag) {
  this.validFlag=validFlag;
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

