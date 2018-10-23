package com.yuchengtech.emp.ecif.customer.entity.orgfinance;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgfinance.Issuebond")
@Table(name="M_CI_ORG_ISSUEBOND")
public class Issuebond implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ISSUE_BOND_ID", unique=true, nullable=false)
	private Long issueBondId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="BOND_KIND")
	private String bondKind;
	@Column(name="EVAL_BANK")
	private String evalBank;
	@Column(name="ISSUE_AMT")
	private BigDecimal issueAmt;
	@Column(name="ISSUE_DATE")
	private String issueDate;
	@Column(name="BOND_TYPE")
	private String bondType;
	@Column(name="BOND_GRADE")
	private String bondGrade;
	@Column(name="BOND_NAME")
	private String bondName;
	@Column(name="BOND_CURR")
	private String bondCurr;
	@Column(name="BOND_AMT")
	private BigDecimal bondAmt;
	@Column(name="IRREGULATION")
	private String irregulation;
	@Column(name="BOND_SELLER")
	private String bondSeller;
	@Column(name="BOND_WARRANTOR")
	private String bondWarrantor;
	@Column(name="BOND_TERM")
	private BigDecimal bondTerm;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getIssueBondId() {
 return this.issueBondId;
 }
 public void setIssueBondId(Long issueBondId) {
  this.issueBondId=issueBondId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getBondKind() {
 return this.bondKind;
 }
 public void setBondKind(String bondKind) {
  this.bondKind=bondKind;
 }
 public String getEvalBank() {
 return this.evalBank;
 }
 public void setEvalBank(String evalBank) {
  this.evalBank=evalBank;
 }
 public BigDecimal getIssueAmt() {
 return this.issueAmt;
 }
 public void setIssueAmt(BigDecimal issueAmt) {
  this.issueAmt=issueAmt;
 }
 public String getIssueDate() {
 return this.issueDate;
 }
 public void setIssueDate(String issueDate) {
  this.issueDate=issueDate;
 }
 public String getBondType() {
 return this.bondType;
 }
 public void setBondType(String bondType) {
  this.bondType=bondType;
 }
 public String getBondGrade() {
 return this.bondGrade;
 }
 public void setBondGrade(String bondGrade) {
  this.bondGrade=bondGrade;
 }
 public String getBondName() {
 return this.bondName;
 }
 public void setBondName(String bondName) {
  this.bondName=bondName;
 }
 public String getBondCurr() {
 return this.bondCurr;
 }
 public void setBondCurr(String bondCurr) {
  this.bondCurr=bondCurr;
 }
 public BigDecimal getBondAmt() {
 return this.bondAmt;
 }
 public void setBondAmt(BigDecimal bondAmt) {
  this.bondAmt=bondAmt;
 }
 public String getIrregulation() {
 return this.irregulation;
 }
 public void setIrregulation(String irregulation) {
  this.irregulation=irregulation;
 }
 public String getBondSeller() {
 return this.bondSeller;
 }
 public void setBondSeller(String bondSeller) {
  this.bondSeller=bondSeller;
 }
 public String getBondWarrantor() {
 return this.bondWarrantor;
 }
 public void setBondWarrantor(String bondWarrantor) {
  this.bondWarrantor=bondWarrantor;
 }
 public BigDecimal getBondTerm() {
 return this.bondTerm;
 }
 public void setBondTerm(BigDecimal bondTerm) {
  this.bondTerm=bondTerm;
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

