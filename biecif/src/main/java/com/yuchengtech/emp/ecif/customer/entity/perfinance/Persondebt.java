package com.yuchengtech.emp.ecif.customer.entity.perfinance;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perfinance.Persondebt")
@Table(name="M_CI_PER_DEBT")
public class Persondebt implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PERSON_DEBT_ID", unique=true, nullable=false)
	private Long personDebtId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="DEBT_TYPE")
	private String debtType;
	@Column(name="DEBT_NAME")
	private String debtName;
	@Column(name="DEBT_BAL")
	private BigDecimal debtBal;
	@Column(name="DEBT_DESC")
	private String debtDesc;
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
 public Long getPersonDebtId() {
 return this.personDebtId;
 }
 public void setPersonDebtId(Long personDebtId) {
  this.personDebtId=personDebtId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getDebtType() {
 return this.debtType;
 }
 public void setDebtType(String debtType) {
  this.debtType=debtType;
 }
 public String getDebtName() {
 return this.debtName;
 }
 public void setDebtName(String debtName) {
  this.debtName=debtName;
 }
 public BigDecimal getDebtBal() {
 return this.debtBal;
 }
 public void setDebtBal(BigDecimal debtBal) {
  this.debtBal=debtBal;
 }
 public String getDebtDesc() {
 return this.debtDesc;
 }
 public void setDebtDesc(String debtDesc) {
  this.debtDesc=debtDesc;
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

