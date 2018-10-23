package com.yuchengtech.emp.ecif.customer.entity.perevaluate;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.perevaluate.Personpreference")
@Table(name="M_CI_PER_PREFERENCE")
public class Personpreference implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;
	@Column(name="LANG_PREFER")
	private String langPrefer;
	@Column(name="TITLE_PREFER")
	private String titlePrefer;
	@Column(name="CONTACT_TYPE")
	private String contactType;
	@Column(name="CONTACT_FREQUENCY_PREFER")
	private String contactFrequencyPrefer;
	@Column(name="CONTACT_TIME_PREFER")
	private String contactTimePrefer;
	@Column(name="GIFT_PREFER")
	private String giftPrefer;
	@Column(name="VEHICLE_PREFER")
	private String vehiclePrefer;
	@Column(name="CONSUM_HABIT")
	private String consumHabit;
	@Column(name="INSURANCE_PREFER")
	private String insurancePrefer;
	@Column(name="INVEST_EXPR")
	private String investExpr;
	@Column(name="RISK_PREFER")
	private String riskPrefer;
	@Column(name="INTEREST_INVESTMENT")
	private String interestInvestment;
	@Column(name="INVEST_STYLE")
	private String investStyle;
	@Column(name="INVEST_TARGET")
	private String investTarget;
	@Column(name="INVEST_CHANNEL")
	private String investChannel;
	@Column(name="POST_DATA_FLAG")
	private String postDataFlag;
	@Column(name="JOIN_CAMP_FLAG")
	private String joinCampFlag;
	@Column(name="WELCOME_TEXT")
	private String welcomeText;
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
 public String getLangPrefer() {
 return this.langPrefer;
 }
 public void setLangPrefer(String langPrefer) {
  this.langPrefer=langPrefer;
 }
 public String getTitlePrefer() {
 return this.titlePrefer;
 }
 public void setTitlePrefer(String titlePrefer) {
  this.titlePrefer=titlePrefer;
 }
 public String getContactType() {
 return this.contactType;
 }
 public void setContactType(String contactType) {
  this.contactType=contactType;
 }
 public String getContactFrequencyPrefer() {
 return this.contactFrequencyPrefer;
 }
 public void setContactFrequencyPrefer(String contactFrequencyPrefer) {
  this.contactFrequencyPrefer=contactFrequencyPrefer;
 }
 public String getContactTimePrefer() {
 return this.contactTimePrefer;
 }
 public void setContactTimePrefer(String contactTimePrefer) {
  this.contactTimePrefer=contactTimePrefer;
 }
 public String getGiftPrefer() {
 return this.giftPrefer;
 }
 public void setGiftPrefer(String giftPrefer) {
  this.giftPrefer=giftPrefer;
 }
 public String getVehiclePrefer() {
 return this.vehiclePrefer;
 }
 public void setVehiclePrefer(String vehiclePrefer) {
  this.vehiclePrefer=vehiclePrefer;
 }
 public String getConsumHabit() {
 return this.consumHabit;
 }
 public void setConsumHabit(String consumHabit) {
  this.consumHabit=consumHabit;
 }
 public String getInsurancePrefer() {
 return this.insurancePrefer;
 }
 public void setInsurancePrefer(String insurancePrefer) {
  this.insurancePrefer=insurancePrefer;
 }
 public String getInvestExpr() {
 return this.investExpr;
 }
 public void setInvestExpr(String investExpr) {
  this.investExpr=investExpr;
 }
 public String getRiskPrefer() {
 return this.riskPrefer;
 }
 public void setRiskPrefer(String riskPrefer) {
  this.riskPrefer=riskPrefer;
 }
 public String getInterestInvestment() {
 return this.interestInvestment;
 }
 public void setInterestInvestment(String interestInvestment) {
  this.interestInvestment=interestInvestment;
 }
 public String getInvestStyle() {
 return this.investStyle;
 }
 public void setInvestStyle(String investStyle) {
  this.investStyle=investStyle;
 }
 public String getInvestTarget() {
 return this.investTarget;
 }
 public void setInvestTarget(String investTarget) {
  this.investTarget=investTarget;
 }
 public String getInvestChannel() {
 return this.investChannel;
 }
 public void setInvestChannel(String investChannel) {
  this.investChannel=investChannel;
 }
 public String getPostDataFlag() {
 return this.postDataFlag;
 }
 public void setPostDataFlag(String postDataFlag) {
  this.postDataFlag=postDataFlag;
 }
 public String getJoinCampFlag() {
 return this.joinCampFlag;
 }
 public void setJoinCampFlag(String joinCampFlag) {
  this.joinCampFlag=joinCampFlag;
 }
 public String getWelcomeText() {
 return this.welcomeText;
 }
 public void setWelcomeText(String welcomeText) {
  this.welcomeText=welcomeText;
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

