package com.yuchengtech.emp.ecif.customer.entity.perrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perrisk.Mendinfo")
@Table(name="M_CI_ORG_MENDINFO")
public class Mendinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="MEND_INFO_ID", unique=true, nullable=false)
	private Long mendInfoId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="MEND_DATE")
	private String mendDate;
	@Column(name="MEND_DESC")
	private String mendDesc;
	@Column(name="LOCATION")
	private String location;
	@Column(name="DEAL_INFO")
	private String dealInfo;
	@Column(name="DATA_SOURCE")
	private String dataSource;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getMendInfoId() {
 return this.mendInfoId;
 }
 public void setMendInfoId(Long mendInfoId) {
  this.mendInfoId=mendInfoId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getMendDate() {
 return this.mendDate;
 }
 public void setMendDate(String mendDate) {
  this.mendDate=mendDate;
 }
 public String getMendDesc() {
 return this.mendDesc;
 }
 public void setMendDesc(String mendDesc) {
  this.mendDesc=mendDesc;
 }
 public String getLocation() {
 return this.location;
 }
 public void setLocation(String location) {
  this.location=location;
 }
 public String getDealInfo() {
 return this.dealInfo;
 }
 public void setDealInfo(String dealInfo) {
  this.dealInfo=dealInfo;
 }
 public String getDataSource() {
 return this.dataSource;
 }
 public void setDataSource(String dataSource) {
  this.dataSource=dataSource;
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

