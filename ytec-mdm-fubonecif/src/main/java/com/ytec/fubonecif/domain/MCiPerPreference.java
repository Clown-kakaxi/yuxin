package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiPerPreference entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_PREFERENCE")
public class MCiPerPreference implements java.io.Serializable {

	// Fields

	private String custId;
	private String langPrefer;
	private String titlePrefer;
	private String contactType;
	private String contactFreqPrefer;
	private String contactTimePrefer;
	private String giftPrefer;
	private String vehiclePrefer;
	private String consumHabit;
	private String insurancePrefer;
	private String investExpr;
	private String riskPrefer;
	private String investPosition;
	private String investCycle;
	private String financeBusinessPrefer;
	private String interestInvestment;
	private String investStyle;
	private String investTarget;
	private String investChannel;
	private String postDataFlag;
	private String joinCampFlag;
	private String receiveSmsFlag;
	private String welcomeText;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerPreference() {
	}

	/** minimal constructor */
	public MCiPerPreference(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiPerPreference(String custId, String langPrefer,
			String titlePrefer, String contactType, String contactFreqPrefer,
			String contactTimePrefer, String giftPrefer, String vehiclePrefer,
			String consumHabit, String insurancePrefer, String investExpr,
			String riskPrefer, String investPosition, String investCycle,
			String financeBusinessPrefer, String interestInvestment,
			String investStyle, String investTarget, String investChannel,
			String postDataFlag, String joinCampFlag, String receiveSmsFlag,
			String welcomeText, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custId = custId;
		this.langPrefer = langPrefer;
		this.titlePrefer = titlePrefer;
		this.contactType = contactType;
		this.contactFreqPrefer = contactFreqPrefer;
		this.contactTimePrefer = contactTimePrefer;
		this.giftPrefer = giftPrefer;
		this.vehiclePrefer = vehiclePrefer;
		this.consumHabit = consumHabit;
		this.insurancePrefer = insurancePrefer;
		this.investExpr = investExpr;
		this.riskPrefer = riskPrefer;
		this.investPosition = investPosition;
		this.investCycle = investCycle;
		this.financeBusinessPrefer = financeBusinessPrefer;
		this.interestInvestment = interestInvestment;
		this.investStyle = investStyle;
		this.investTarget = investTarget;
		this.investChannel = investChannel;
		this.postDataFlag = postDataFlag;
		this.joinCampFlag = joinCampFlag;
		this.receiveSmsFlag = receiveSmsFlag;
		this.welcomeText = welcomeText;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_ID", unique = true, nullable = false, length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "LANG_PREFER", length = 20)
	public String getLangPrefer() {
		return this.langPrefer;
	}

	public void setLangPrefer(String langPrefer) {
		this.langPrefer = langPrefer;
	}

	@Column(name = "TITLE_PREFER", length = 20)
	public String getTitlePrefer() {
		return this.titlePrefer;
	}

	public void setTitlePrefer(String titlePrefer) {
		this.titlePrefer = titlePrefer;
	}

	@Column(name = "CONTACT_TYPE", length = 20)
	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	@Column(name = "CONTACT_FREQ_PREFER", length = 20)
	public String getContactFreqPrefer() {
		return this.contactFreqPrefer;
	}

	public void setContactFreqPrefer(String contactFreqPrefer) {
		this.contactFreqPrefer = contactFreqPrefer;
	}

	@Column(name = "CONTACT_TIME_PREFER", length = 20)
	public String getContactTimePrefer() {
		return this.contactTimePrefer;
	}

	public void setContactTimePrefer(String contactTimePrefer) {
		this.contactTimePrefer = contactTimePrefer;
	}

	@Column(name = "GIFT_PREFER", length = 20)
	public String getGiftPrefer() {
		return this.giftPrefer;
	}

	public void setGiftPrefer(String giftPrefer) {
		this.giftPrefer = giftPrefer;
	}

	@Column(name = "VEHICLE_PREFER", length = 20)
	public String getVehiclePrefer() {
		return this.vehiclePrefer;
	}

	public void setVehiclePrefer(String vehiclePrefer) {
		this.vehiclePrefer = vehiclePrefer;
	}

	@Column(name = "CONSUM_HABIT", length = 20)
	public String getConsumHabit() {
		return this.consumHabit;
	}

	public void setConsumHabit(String consumHabit) {
		this.consumHabit = consumHabit;
	}

	@Column(name = "INSURANCE_PREFER", length = 20)
	public String getInsurancePrefer() {
		return this.insurancePrefer;
	}

	public void setInsurancePrefer(String insurancePrefer) {
		this.insurancePrefer = insurancePrefer;
	}

	@Column(name = "INVEST_EXPR", length = 20)
	public String getInvestExpr() {
		return this.investExpr;
	}

	public void setInvestExpr(String investExpr) {
		this.investExpr = investExpr;
	}

	@Column(name = "RISK_PREFER", length = 20)
	public String getRiskPrefer() {
		return this.riskPrefer;
	}

	public void setRiskPrefer(String riskPrefer) {
		this.riskPrefer = riskPrefer;
	}

	@Column(name = "INVEST_POSITION", length = 20)
	public String getInvestPosition() {
		return this.investPosition;
	}

	public void setInvestPosition(String investPosition) {
		this.investPosition = investPosition;
	}

	@Column(name = "INVEST_CYCLE", length = 20)
	public String getInvestCycle() {
		return this.investCycle;
	}

	public void setInvestCycle(String investCycle) {
		this.investCycle = investCycle;
	}

	@Column(name = "FINANCE_BUSINESS_PREFER", length = 20)
	public String getFinanceBusinessPrefer() {
		return this.financeBusinessPrefer;
	}

	public void setFinanceBusinessPrefer(String financeBusinessPrefer) {
		this.financeBusinessPrefer = financeBusinessPrefer;
	}

	@Column(name = "INTEREST_INVESTMENT", length = 500)
	public String getInterestInvestment() {
		return this.interestInvestment;
	}

	public void setInterestInvestment(String interestInvestment) {
		this.interestInvestment = interestInvestment;
	}

	@Column(name = "INVEST_STYLE", length = 20)
	public String getInvestStyle() {
		return this.investStyle;
	}

	public void setInvestStyle(String investStyle) {
		this.investStyle = investStyle;
	}

	@Column(name = "INVEST_TARGET", length = 20)
	public String getInvestTarget() {
		return this.investTarget;
	}

	public void setInvestTarget(String investTarget) {
		this.investTarget = investTarget;
	}

	@Column(name = "INVEST_CHANNEL", length = 20)
	public String getInvestChannel() {
		return this.investChannel;
	}

	public void setInvestChannel(String investChannel) {
		this.investChannel = investChannel;
	}

	@Column(name = "POST_DATA_FLAG", length = 1)
	public String getPostDataFlag() {
		return this.postDataFlag;
	}

	public void setPostDataFlag(String postDataFlag) {
		this.postDataFlag = postDataFlag;
	}

	@Column(name = "JOIN_CAMP_FLAG", length = 1)
	public String getJoinCampFlag() {
		return this.joinCampFlag;
	}

	public void setJoinCampFlag(String joinCampFlag) {
		this.joinCampFlag = joinCampFlag;
	}

	@Column(name = "RECEIVE_SMS_FLAG", length = 1)
	public String getReceiveSmsFlag() {
		return this.receiveSmsFlag;
	}

	public void setReceiveSmsFlag(String receiveSmsFlag) {
		this.receiveSmsFlag = receiveSmsFlag;
	}

	@Column(name = "WELCOME_TEXT", length = 100)
	public String getWelcomeText() {
		return this.welcomeText;
	}

	public void setWelcomeText(String welcomeText) {
		this.welcomeText = welcomeText;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}