package com.yuchengtech.emp.ecif.customer.entity.perfinance;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perfinance.PersonAsset")
@Table(name="M_CI_PER_ASSETS")
public class PersonAsset implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PERSON_ASSETS_ID", unique=true, nullable=false)
	private Long personAssetsId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="ASSETS_TYPE")
	private String assetsType;
	@Column(name="ASSETS_NAME")
	private String assetsName;
	@Column(name="ASSETS_VALUE")
	private BigDecimal assetsValue;
	@Column(name="ASSETS_DESC")
	private String assetsDesc;
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
 public Long getPersonAssetsId() {
 return this.personAssetsId;
 }
 public void setPersonAssetsId(Long personAssetsId) {
  this.personAssetsId=personAssetsId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getAssetsType() {
 return this.assetsType;
 }
 public void setAssetsType(String assetsType) {
  this.assetsType=assetsType;
 }
 public String getAssetsName() {
 return this.assetsName;
 }
 public void setAssetsName(String assetsName) {
  this.assetsName=assetsName;
 }
 public BigDecimal getAssetsValue() {
 return this.assetsValue;
 }
 public void setAssetsValue(BigDecimal assetsValue) {
  this.assetsValue=assetsValue;
 }
 public String getAssetsDesc() {
 return this.assetsDesc;
 }
 public void setAssetsDesc(String assetsDesc) {
  this.assetsDesc=assetsDesc;
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

