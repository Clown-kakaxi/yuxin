package com.yuchengtech.emp.ecif.customer.entity.orgevaluate;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgevaluate.Orggrade")
@Table(name="M_CI_ORG_GRADE")
public class Orggrade implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ORG_GRADE_ID", unique=true, nullable=false)
	private Long orgGradeId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="ORG_GRADE_TYPE")
	private String orgGradeType;
	@Column(name="ORG_GRADE")
	private String orgGrade;
	@Column(name="EVALUTE_DATE")
	private String evaluteDate;
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
 public Long getOrgGradeId() {
 return this.orgGradeId;
 }
 public void setOrgGradeId(Long orgGradeId) {
  this.orgGradeId=orgGradeId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getOrgGradeType() {
 return this.orgGradeType;
 }
 public void setOrgGradeType(String orgGradeType) {
  this.orgGradeType=orgGradeType;
 }
 public String getOrgGrade() {
 return this.orgGrade;
 }
 public void setOrgGrade(String orgGrade) {
  this.orgGrade=orgGrade;
 }
 public String getEvaluteDate() {
 return this.evaluteDate;
 }
 public void setEvaluteDate(String evaluteDate) {
  this.evaluteDate=evaluteDate;
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

