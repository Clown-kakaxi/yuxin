package com.yuchengtech.emp.ecif.customer.entity.perrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perrisk.SpecialList")
@Table(name="M_CI_SPECIALLIST")
public class SpecialList implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="SPECIAL_LIST_ID", unique=true, nullable=false)
	private Long specialListId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="SPECIAL_LIST_TYPE")
	private String specialListType;
	@Column(name="SPECIAL_LIST_KIND")
	private String specialListKind;
	@Column(name="SPECIAL_LIST_FLAG")
	private String specialListFlag;
	@Column(name="IDENT_TYPE")
	private String identType;
	@Column(name="IDENT_NO")
	private String identNo;
	@Column(name="IDENT_CUST_NAME")
	private String identCustName;
	@Column(name="ENTER_REASON")
	private String enterReason;
	@Column(name="STAT_FLAG")
	private String statFlag;
	@Column(name="START_DATE")
	private String startDate;
	@Column(name="END_DATE")
	private String endDate;
	@Column(name="APPROVAL_FLAG")
	private String approvalFlag;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getSpecialListId() {
 return this.specialListId;
 }
 public void setSpecialListId(Long specialListId) {
  this.specialListId=specialListId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getSpecialListType() {
 return this.specialListType;
 }
 public void setSpecialListType(String specialListType) {
  this.specialListType=specialListType;
 }
 public String getSpecialListKind() {
 return this.specialListKind;
 }
 public void setSpecialListKind(String specialListKind) {
  this.specialListKind=specialListKind;
 }
 public String getSpecialListFlag() {
 return this.specialListFlag;
 }
 public void setSpecialListFlag(String specialListFlag) {
  this.specialListFlag=specialListFlag;
 }
 public String getIdentType() {
 return this.identType;
 }
 public void setIdentType(String identType) {
  this.identType=identType;
 }
 public String getIdentNo() {
 return this.identNo;
 }
 public void setIdentNo(String identNo) {
  this.identNo=identNo;
 }
 public String getIdentCustName() {
 return this.identCustName;
 }
 public void setIdentCustName(String identCustName) {
  this.identCustName=identCustName;
 }
 public String getEnterReason() {
 return this.enterReason;
 }
 public void setEnterReason(String enterReason) {
  this.enterReason=enterReason;
 }
 public String getStatFlag() {
 return this.statFlag;
 }
 public void setStatFlag(String statFlag) {
  this.statFlag=statFlag;
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
 public String getApprovalFlag() {
 return this.approvalFlag;
 }
 public void setApprovalFlag(String approvalFlag) {
  this.approvalFlag=approvalFlag;
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

