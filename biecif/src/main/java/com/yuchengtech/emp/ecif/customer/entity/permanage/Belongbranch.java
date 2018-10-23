package com.yuchengtech.emp.ecif.customer.entity.permanage;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.permanage.Belongbranch")
@Table(name="M_CI_BELONG_BRANCH")
public class Belongbranch implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="BELONG_BRANCH_ID", unique=true, nullable=false)
	private Long belongBranchId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="BELONG_BRANCH_TYPE")
	private String belongBranchType;
	@Column(name="BELONG_BRANCH_NO")
	private String belongBranchNo;
	@Column(name="MAIN_TYPE")
	private String mainType;
	@Column(name="VALID_FLAG")
	private String validFlag;
	@Column(name="START_DATE")
	private String startDate;
	@Column(name="END_DATE")
	private String endDate;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getBelongBranchId() {
 return this.belongBranchId;
 }
 public void setBelongBranchId(Long belongBranchId) {
  this.belongBranchId=belongBranchId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getBelongBranchType() {
 return this.belongBranchType;
 }
 public void setBelongBranchType(String belongBranchType) {
  this.belongBranchType=belongBranchType;
 }
 public String getBelongBranchNo() {
 return this.belongBranchNo;
 }
 public void setBelongBranchNo(String belongBranchNo) {
  this.belongBranchNo=belongBranchNo;
 }
 public String getMainType() {
 return this.mainType;
 }
 public void setMainType(String mainType) {
  this.mainType=mainType;
 }
 public String getValidFlag() {
 return this.validFlag;
 }
 public void setValidFlag(String validFlag) {
  this.validFlag=validFlag;
 }
 public String getStartDate() {
 return this.startDate;
 }
 public void setStartDate(String startDate) {
  this.startDate=startDate;
 }
 public String getEndDate() {
 return this.endDate;
 }
 public void setEndDate(String endDate) {
  this.endDate=endDate;
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

