package com.yuchengtech.emp.ecif.customer.entity.perrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perrisk.Personsocialinsurance")
@Table(name="M_CI_PER_SOCIALINSURANCE")
public class Personsocialinsurance implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PERSON_SI_ID", unique=true, nullable=false)
	private Long personSiId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="SITYPE")
	private String sitype;
	@Column(name="SIACCOUNT")
	private String siaccount;
	@Column(name="SIBALANCE")
	private BigDecimal sibalance;
	@Column(name="UPTODATE")
	private String uptodate;
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
 public Long getPersonSiId() {
 return this.personSiId;
 }
 public void setPersonSiId(Long personSiId) {
  this.personSiId=personSiId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getSitype() {
 return this.sitype;
 }
 public void setSitype(String sitype) {
  this.sitype=sitype;
 }
 public String getSiaccount() {
 return this.siaccount;
 }
 public void setSiaccount(String siaccount) {
  this.siaccount=siaccount;
 }
 public BigDecimal getSibalance() {
 return this.sibalance;
 }
 public void setSibalance(BigDecimal sibalance) {
  this.sibalance=sibalance;
 }
 public String getUptodate() {
 return this.uptodate;
 }
 public void setUptodate(String uptodate) {
  this.uptodate=uptodate;
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

