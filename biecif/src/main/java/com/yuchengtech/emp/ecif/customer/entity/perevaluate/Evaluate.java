package com.yuchengtech.emp.ecif.customer.entity.perevaluate;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perevaluate.Evaluate")
@Table(name="M_CI_EVALUATE")
public class Evaluate implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="EVALUATE_ID", unique=true, nullable=false)
	private Long evaluateId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="EVALUATE_TYPE")
	private String evaluateType;
	@Column(name="EVALUATE_INFO")
	private String evaluateInfo;
	@Column(name="EVALUATE_DATE")
	private String evaluateDate;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getEvaluateId() {
 return this.evaluateId;
 }
 public void setEvaluateId(Long evaluateId) {
  this.evaluateId=evaluateId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getEvaluateType() {
 return this.evaluateType;
 }
 public void setEvaluateType(String evaluateType) {
  this.evaluateType=evaluateType;
 }
 public String getEvaluateInfo() {
 return this.evaluateInfo;
 }
 public void setEvaluateInfo(String evaluateInfo) {
  this.evaluateInfo=evaluateInfo;
 }
 public String getEvaluateDate() {
 return this.evaluateDate;
 }
 public void setEvaluateDate(String evaluateDate) {
  this.evaluateDate=evaluateDate;
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

