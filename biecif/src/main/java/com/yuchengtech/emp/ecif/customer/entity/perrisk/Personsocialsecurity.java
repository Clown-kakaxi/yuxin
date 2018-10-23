package com.yuchengtech.emp.ecif.customer.entity.perrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perrisk.Personsocialsecurity")
@Table(name="M_CI_PER_SOCIALSECURITY")
public class Personsocialsecurity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;
	@Column(name="HAS_ALLO_FLAG")
	private String hasAlloFlag;
	@Column(name="HAS_COMM_FLAG")
	private String hasCommFlag;
	@Column(name="HAS_ENDO_FLAG")
	private String hasEndoFlag;
	@Column(name="HAS_IDLE_FLAG")
	private String hasIdleFlag;
	@Column(name="HAS_MEDI_FLAG")
	private String hasMediFlag;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getHasAlloFlag() {
 return this.hasAlloFlag;
 }
 public void setHasAlloFlag(String hasAlloFlag) {
  this.hasAlloFlag=hasAlloFlag;
 }
 public String getHasCommFlag() {
 return this.hasCommFlag;
 }
 public void setHasCommFlag(String hasCommFlag) {
  this.hasCommFlag=hasCommFlag;
 }
 public String getHasEndoFlag() {
 return this.hasEndoFlag;
 }
 public void setHasEndoFlag(String hasEndoFlag) {
  this.hasEndoFlag=hasEndoFlag;
 }
 public String getHasIdleFlag() {
 return this.hasIdleFlag;
 }
 public void setHasIdleFlag(String hasIdleFlag) {
  this.hasIdleFlag=hasIdleFlag;
 }
 public String getHasMediFlag() {
 return this.hasMediFlag;
 }
 public void setHasMediFlag(String hasMediFlag) {
  this.hasMediFlag=hasMediFlag;
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

