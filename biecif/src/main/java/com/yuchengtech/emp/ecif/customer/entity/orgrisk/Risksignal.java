package com.yuchengtech.emp.ecif.customer.entity.orgrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgrisk.Risksignal")
@Table(name="M_CI_ORG_RISKSIGNAL")
public class Risksignal implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="RISK_SIGNAL_ID", unique=true, nullable=false)
	private Long riskSignalId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="OBJECT_TYPE")
	private String objectType;
	@Column(name="OBJECT_NO")
	private String objectNo;
	@Column(name="SIGNAL_TYPE")
	private String signalType;
	@Column(name="SIGNAL_STATUS")
	private String signalStatus;
	@Column(name="SIGNAL_NO")
	private String signalNo;
	@Column(name="SIGNAL_NAME")
	private String signalName;
	@Column(name="MESSAGE_ORIGIN")
	private String messageOrigin;
	@Column(name="MESSAGE_CONTENT")
	private String messageContent;
	@Column(name="ACTION_FLAG")
	private String actionFlag;
	@Column(name="ACTION_TYPE")
	private String actionType;
	@Column(name="FREE_REASON")
	private String freeReason;
	@Column(name="SIGNAL_CHANNEL")
	private String signalChannel;
	@Column(name="FINISH_DATE")
	private String finishDate;
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
 public Long getRiskSignalId() {
 return this.riskSignalId;
 }
 public void setRiskSignalId(Long riskSignalId) {
  this.riskSignalId=riskSignalId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getObjectType() {
 return this.objectType;
 }
 public void setObjectType(String objectType) {
  this.objectType=objectType;
 }
 public String getObjectNo() {
 return this.objectNo;
 }
 public void setObjectNo(String objectNo) {
  this.objectNo=objectNo;
 }
 public String getSignalType() {
 return this.signalType;
 }
 public void setSignalType(String signalType) {
  this.signalType=signalType;
 }
 public String getSignalStatus() {
 return this.signalStatus;
 }
 public void setSignalStatus(String signalStatus) {
  this.signalStatus=signalStatus;
 }
 public String getSignalNo() {
 return this.signalNo;
 }
 public void setSignalNo(String signalNo) {
  this.signalNo=signalNo;
 }
 public String getSignalName() {
 return this.signalName;
 }
 public void setSignalName(String signalName) {
  this.signalName=signalName;
 }
 public String getMessageOrigin() {
 return this.messageOrigin;
 }
 public void setMessageOrigin(String messageOrigin) {
  this.messageOrigin=messageOrigin;
 }
 public String getMessageContent() {
 return this.messageContent;
 }
 public void setMessageContent(String messageContent) {
  this.messageContent=messageContent;
 }
 public String getActionFlag() {
 return this.actionFlag;
 }
 public void setActionFlag(String actionFlag) {
  this.actionFlag=actionFlag;
 }
 public String getActionType() {
 return this.actionType;
 }
 public void setActionType(String actionType) {
  this.actionType=actionType;
 }
 public String getFreeReason() {
 return this.freeReason;
 }
 public void setFreeReason(String freeReason) {
  this.freeReason=freeReason;
 }
 public String getSignalChannel() {
 return this.signalChannel;
 }
 public void setSignalChannel(String signalChannel) {
  this.signalChannel=signalChannel;
 }
 public String getFinishDate() {
 return this.finishDate;
 }
 public void setFinishDate(String finishDate) {
  this.finishDate=finishDate;
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

