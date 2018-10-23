package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_PER_PREFERENCE database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_PER_PREFERENCE")
public class AcrmFCiPerPreference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CONSUM_HABIT")
	private String consumHabit;

	@Column(name="CONTACT_FREQ_PREFER")
	private String contactFreqPrefer;

	@Column(name="CONTACT_TIME_PREFER")
	private String contactTimePrefer;

	@Column(name="CONTACT_TYPE")
	private String contactType;

	@Column(name="FINANCE_BUSINESS_PREFER")
	private String financeBusinessPrefer;

	@Column(name="GIFT_PREFER")
	private String giftPrefer;

	@Column(name="INSURANCE_PREFER")
	private String insurancePrefer;

	@Column(name="INTEREST_INVESTMENT")
	private String interestInvestment;

	@Column(name="INVEST_CHANNEL")
	private String investChannel;

	@Column(name="INVEST_CYCLE")
	private String investCycle;

	@Column(name="INVEST_EXPR")
	private String investExpr;

	@Column(name="INVEST_POSITION")
	private String investPosition;

	@Column(name="INVEST_STYLE")
	private String investStyle;

	@Column(name="INVEST_TARGET")
	private String investTarget;

	@Column(name="JOIN_CAMP_FLAG")
	private String joinCampFlag;

	@Column(name="LANG_PREFER")
	private String langPrefer;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="POST_DATA_FLAG")
	private String postDataFlag;

	@Column(name="RECEIVE_SMS_FLAG")
	private String receiveSmsFlag;

	@Column(name="RISK_PREFER")
	private String riskPrefer;

	@Column(name="TITLE_PREFER")
	private String titlePrefer;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="VEHICLE_PREFER")
	private String vehiclePrefer;

	@Column(name="WELCOME_TEXT")
	private String welcomeText;

    public AcrmFCiPerPreference() {
    }

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getConsumHabit() {
		return this.consumHabit;
	}

	public void setConsumHabit(String consumHabit) {
		this.consumHabit = consumHabit;
	}

	public String getContactFreqPrefer() {
		return this.contactFreqPrefer;
	}

	public void setContactFreqPrefer(String contactFreqPrefer) {
		this.contactFreqPrefer = contactFreqPrefer;
	}

	public String getContactTimePrefer() {
		return this.contactTimePrefer;
	}

	public void setContactTimePrefer(String contactTimePrefer) {
		this.contactTimePrefer = contactTimePrefer;
	}

	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getFinanceBusinessPrefer() {
		return this.financeBusinessPrefer;
	}

	public void setFinanceBusinessPrefer(String financeBusinessPrefer) {
		this.financeBusinessPrefer = financeBusinessPrefer;
	}

	public String getGiftPrefer() {
		return this.giftPrefer;
	}

	public void setGiftPrefer(String giftPrefer) {
		this.giftPrefer = giftPrefer;
	}

	public String getInsurancePrefer() {
		return this.insurancePrefer;
	}

	public void setInsurancePrefer(String insurancePrefer) {
		this.insurancePrefer = insurancePrefer;
	}

	public String getInterestInvestment() {
		return this.interestInvestment;
	}

	public void setInterestInvestment(String interestInvestment) {
		this.interestInvestment = interestInvestment;
	}

	public String getInvestChannel() {
		return this.investChannel;
	}

	public void setInvestChannel(String investChannel) {
		this.investChannel = investChannel;
	}

	public String getInvestCycle() {
		return this.investCycle;
	}

	public void setInvestCycle(String investCycle) {
		this.investCycle = investCycle;
	}

	public String getInvestExpr() {
		return this.investExpr;
	}

	public void setInvestExpr(String investExpr) {
		this.investExpr = investExpr;
	}

	public String getInvestPosition() {
		return this.investPosition;
	}

	public void setInvestPosition(String investPosition) {
		this.investPosition = investPosition;
	}

	public String getInvestStyle() {
		return this.investStyle;
	}

	public void setInvestStyle(String investStyle) {
		this.investStyle = investStyle;
	}

	public String getInvestTarget() {
		return this.investTarget;
	}

	public void setInvestTarget(String investTarget) {
		this.investTarget = investTarget;
	}

	public String getJoinCampFlag() {
		return this.joinCampFlag;
	}

	public void setJoinCampFlag(String joinCampFlag) {
		this.joinCampFlag = joinCampFlag;
	}

	public String getLangPrefer() {
		return this.langPrefer;
	}

	public void setLangPrefer(String langPrefer) {
		this.langPrefer = langPrefer;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getPostDataFlag() {
		return this.postDataFlag;
	}

	public void setPostDataFlag(String postDataFlag) {
		this.postDataFlag = postDataFlag;
	}

	public String getReceiveSmsFlag() {
		return this.receiveSmsFlag;
	}

	public void setReceiveSmsFlag(String receiveSmsFlag) {
		this.receiveSmsFlag = receiveSmsFlag;
	}

	public String getRiskPrefer() {
		return this.riskPrefer;
	}

	public void setRiskPrefer(String riskPrefer) {
		this.riskPrefer = riskPrefer;
	}

	public String getTitlePrefer() {
		return this.titlePrefer;
	}

	public void setTitlePrefer(String titlePrefer) {
		this.titlePrefer = titlePrefer;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getVehiclePrefer() {
		return this.vehiclePrefer;
	}

	public void setVehiclePrefer(String vehiclePrefer) {
		this.vehiclePrefer = vehiclePrefer;
	}

	public String getWelcomeText() {
		return this.welcomeText;
	}

	public void setWelcomeText(String welcomeText) {
		this.welcomeText = welcomeText;
	}

}