package com.yuchengtech.emp.ecif.customer.entity.orgevaluate;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgevaluate.Orgauth")
@Table(name="M_CI_ORG_AUTH")
public class Orgauth implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ORG_AUTH_ID", unique=true, nullable=false)
	private Long orgAuthId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="AUTH_TYPE")
	private String authType;
	@Column(name="AUTH_ORG")
	private String authOrg;
	@Column(name="AUTH_RESULT")
	private String authResult;
	@Column(name="CERT_NAME")
	private String certName;
	@Column(name="CERT_NO")
	private String certNo;
	@Column(name="AUTH_DATE")
	private String authDate;
	@Column(name="VALID_DATE")
	private String validDate;
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
 public Long getOrgAuthId() {
 return this.orgAuthId;
 }
 public void setOrgAuthId(Long orgAuthId) {
  this.orgAuthId=orgAuthId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getAuthType() {
 return this.authType;
 }
 public void setAuthType(String authType) {
  this.authType=authType;
 }
 public String getAuthOrg() {
 return this.authOrg;
 }
 public void setAuthOrg(String authOrg) {
  this.authOrg=authOrg;
 }
 public String getAuthResult() {
 return this.authResult;
 }
 public void setAuthResult(String authResult) {
  this.authResult=authResult;
 }
 public String getCertName() {
 return this.certName;
 }
 public void setCertName(String certName) {
  this.certName=certName;
 }
 public String getCertNo() {
 return this.certNo;
 }
 public void setCertNo(String certNo) {
  this.certNo=certNo;
 }
 public String getAuthDate() {
 return this.authDate;
 }
 public void setAuthDate(String authDate) {
  this.authDate=authDate;
 }
 public String getValidDate() {
 return this.validDate;
 }
 public void setValidDate(String validDate) {
  this.validDate=validDate;
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

