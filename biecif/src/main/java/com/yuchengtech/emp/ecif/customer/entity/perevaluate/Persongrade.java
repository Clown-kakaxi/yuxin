package com.yuchengtech.emp.ecif.customer.entity.perevaluate;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perevaluate.Persongrade")
@Table(name="M_CI_PER_GRADE")
public class Persongrade implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PERSON_GRADE_ID", unique=true, nullable=false)
	private Long personGradeId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="PERSON_GRADE_TYPE")
	private String personGradeType;
	@Column(name="PERSON_GRADE")
	private String personGrade;
	@Column(name="EVALUATE_DATE")
	private String evaluateDate;
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
 public Long getPersonGradeId() {
 return this.personGradeId;
 }
 public void setPersonGradeId(Long personGradeId) {
  this.personGradeId=personGradeId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getPersonGradeType() {
 return this.personGradeType;
 }
 public void setPersonGradeType(String personGradeType) {
  this.personGradeType=personGradeType;
 }
 public String getPersonGrade() {
 return this.personGrade;
 }
 public void setPersonGrade(String personGrade) {
  this.personGrade=personGrade;
 }
 public String getEvaluateDate() {
 return this.evaluateDate;
 }
 public void setEvaluateDate(String evaluateDate) {
  this.evaluateDate=evaluateDate;
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

