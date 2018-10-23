package com.yuchengtech.emp.ecif.customer.entity.permanage;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.permanage.Lifecycleinfo")
@Table(name="M_CI_LIFECYCLE")
public class Lifecycleinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;
	@Column(name="LIFECYCLE_STAT_TYPE")
	private String lifecycleStatType;
	@Column(name="LIFECYCLE_STAT_DESC")
	private String lifecycleStatDesc;
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
 public String getLifecycleStatType() {
 return this.lifecycleStatType;
 }
 public void setLifecycleStatType(String lifecycleStatType) {
  this.lifecycleStatType=lifecycleStatType;
 }
 public String getLifecycleStatDesc() {
 return this.lifecycleStatDesc;
 }
 public void setLifecycleStatDesc(String lifecycleStatDesc) {
  this.lifecycleStatDesc=lifecycleStatDesc;
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

