package com.yuchengtech.emp.ecif.customer.entity.perrisk;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perrisk.Persongjj")
@Table(name="M_CI_PER_GJJ")
public class Persongjj implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PERSON_GJJ_INFO_ID", unique=true, nullable=false)
	private Long personGjjInfoId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="GJJ_ACCT_NO")
	private String gjjAcctNo;
	@Column(name="GJJ_AMT_PER_MONTH")
	private BigDecimal gjjAmtPerMonth;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getPersonGjjInfoId() {
 return this.personGjjInfoId;
 }
 public void setPersonGjjInfoId(Long personGjjInfoId) {
  this.personGjjInfoId=personGjjInfoId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getGjjAcctNo() {
 return this.gjjAcctNo;
 }
 public void setGjjAcctNo(String gjjAcctNo) {
  this.gjjAcctNo=gjjAcctNo;
 }
 public BigDecimal getGjjAmtPerMonth() {
 return this.gjjAmtPerMonth;
 }
 public void setGjjAmtPerMonth(BigDecimal gjjAmtPerMonth) {
  this.gjjAmtPerMonth=gjjAmtPerMonth;
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

