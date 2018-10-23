package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiPerPreferenceId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerPreferenceId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiPerPreferenceId() {
	}

	/** minimal constructor */
	public HMCiPerPreferenceId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerPreferenceId(String custId, String langPrefer,
			String titlePrefer, String contactType, String contactFreqPrefer,
			String contactTimePrefer, String giftPrefer, String vehiclePrefer,
			String consumHabit, String insurancePrefer, String investExpr,
			String riskPrefer, String investPosition, String investCycle,
			String financeBusinessPrefer, String interestInvestment,
			String investStyle, String investTarget, String investChannel,
			String postDataFlag, String joinCampFlag, String receiveSmsFlag,
			String welcomeText, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "CUST_ID", length = 20)
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

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiPerPreferenceId))
			return false;
		HMCiPerPreferenceId castOther = (HMCiPerPreferenceId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getLangPrefer() == castOther.getLangPrefer()) || (this
						.getLangPrefer() != null
						&& castOther.getLangPrefer() != null && this
						.getLangPrefer().equals(castOther.getLangPrefer())))
				&& ((this.getTitlePrefer() == castOther.getTitlePrefer()) || (this
						.getTitlePrefer() != null
						&& castOther.getTitlePrefer() != null && this
						.getTitlePrefer().equals(castOther.getTitlePrefer())))
				&& ((this.getContactType() == castOther.getContactType()) || (this
						.getContactType() != null
						&& castOther.getContactType() != null && this
						.getContactType().equals(castOther.getContactType())))
				&& ((this.getContactFreqPrefer() == castOther
						.getContactFreqPrefer()) || (this
						.getContactFreqPrefer() != null
						&& castOther.getContactFreqPrefer() != null && this
						.getContactFreqPrefer().equals(
								castOther.getContactFreqPrefer())))
				&& ((this.getContactTimePrefer() == castOther
						.getContactTimePrefer()) || (this
						.getContactTimePrefer() != null
						&& castOther.getContactTimePrefer() != null && this
						.getContactTimePrefer().equals(
								castOther.getContactTimePrefer())))
				&& ((this.getGiftPrefer() == castOther.getGiftPrefer()) || (this
						.getGiftPrefer() != null
						&& castOther.getGiftPrefer() != null && this
						.getGiftPrefer().equals(castOther.getGiftPrefer())))
				&& ((this.getVehiclePrefer() == castOther.getVehiclePrefer()) || (this
						.getVehiclePrefer() != null
						&& castOther.getVehiclePrefer() != null && this
						.getVehiclePrefer()
						.equals(castOther.getVehiclePrefer())))
				&& ((this.getConsumHabit() == castOther.getConsumHabit()) || (this
						.getConsumHabit() != null
						&& castOther.getConsumHabit() != null && this
						.getConsumHabit().equals(castOther.getConsumHabit())))
				&& ((this.getInsurancePrefer() == castOther
						.getInsurancePrefer()) || (this.getInsurancePrefer() != null
						&& castOther.getInsurancePrefer() != null && this
						.getInsurancePrefer().equals(
								castOther.getInsurancePrefer())))
				&& ((this.getInvestExpr() == castOther.getInvestExpr()) || (this
						.getInvestExpr() != null
						&& castOther.getInvestExpr() != null && this
						.getInvestExpr().equals(castOther.getInvestExpr())))
				&& ((this.getRiskPrefer() == castOther.getRiskPrefer()) || (this
						.getRiskPrefer() != null
						&& castOther.getRiskPrefer() != null && this
						.getRiskPrefer().equals(castOther.getRiskPrefer())))
				&& ((this.getInvestPosition() == castOther.getInvestPosition()) || (this
						.getInvestPosition() != null
						&& castOther.getInvestPosition() != null && this
						.getInvestPosition().equals(
								castOther.getInvestPosition())))
				&& ((this.getInvestCycle() == castOther.getInvestCycle()) || (this
						.getInvestCycle() != null
						&& castOther.getInvestCycle() != null && this
						.getInvestCycle().equals(castOther.getInvestCycle())))
				&& ((this.getFinanceBusinessPrefer() == castOther
						.getFinanceBusinessPrefer()) || (this
						.getFinanceBusinessPrefer() != null
						&& castOther.getFinanceBusinessPrefer() != null && this
						.getFinanceBusinessPrefer().equals(
								castOther.getFinanceBusinessPrefer())))
				&& ((this.getInterestInvestment() == castOther
						.getInterestInvestment()) || (this
						.getInterestInvestment() != null
						&& castOther.getInterestInvestment() != null && this
						.getInterestInvestment().equals(
								castOther.getInterestInvestment())))
				&& ((this.getInvestStyle() == castOther.getInvestStyle()) || (this
						.getInvestStyle() != null
						&& castOther.getInvestStyle() != null && this
						.getInvestStyle().equals(castOther.getInvestStyle())))
				&& ((this.getInvestTarget() == castOther.getInvestTarget()) || (this
						.getInvestTarget() != null
						&& castOther.getInvestTarget() != null && this
						.getInvestTarget().equals(castOther.getInvestTarget())))
				&& ((this.getInvestChannel() == castOther.getInvestChannel()) || (this
						.getInvestChannel() != null
						&& castOther.getInvestChannel() != null && this
						.getInvestChannel()
						.equals(castOther.getInvestChannel())))
				&& ((this.getPostDataFlag() == castOther.getPostDataFlag()) || (this
						.getPostDataFlag() != null
						&& castOther.getPostDataFlag() != null && this
						.getPostDataFlag().equals(castOther.getPostDataFlag())))
				&& ((this.getJoinCampFlag() == castOther.getJoinCampFlag()) || (this
						.getJoinCampFlag() != null
						&& castOther.getJoinCampFlag() != null && this
						.getJoinCampFlag().equals(castOther.getJoinCampFlag())))
				&& ((this.getReceiveSmsFlag() == castOther.getReceiveSmsFlag()) || (this
						.getReceiveSmsFlag() != null
						&& castOther.getReceiveSmsFlag() != null && this
						.getReceiveSmsFlag().equals(
								castOther.getReceiveSmsFlag())))
				&& ((this.getWelcomeText() == castOther.getWelcomeText()) || (this
						.getWelcomeText() != null
						&& castOther.getWelcomeText() != null && this
						.getWelcomeText().equals(castOther.getWelcomeText())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getLangPrefer() == null ? 0 : this.getLangPrefer()
						.hashCode());
		result = 37
				* result
				+ (getTitlePrefer() == null ? 0 : this.getTitlePrefer()
						.hashCode());
		result = 37
				* result
				+ (getContactType() == null ? 0 : this.getContactType()
						.hashCode());
		result = 37
				* result
				+ (getContactFreqPrefer() == null ? 0 : this
						.getContactFreqPrefer().hashCode());
		result = 37
				* result
				+ (getContactTimePrefer() == null ? 0 : this
						.getContactTimePrefer().hashCode());
		result = 37
				* result
				+ (getGiftPrefer() == null ? 0 : this.getGiftPrefer()
						.hashCode());
		result = 37
				* result
				+ (getVehiclePrefer() == null ? 0 : this.getVehiclePrefer()
						.hashCode());
		result = 37
				* result
				+ (getConsumHabit() == null ? 0 : this.getConsumHabit()
						.hashCode());
		result = 37
				* result
				+ (getInsurancePrefer() == null ? 0 : this.getInsurancePrefer()
						.hashCode());
		result = 37
				* result
				+ (getInvestExpr() == null ? 0 : this.getInvestExpr()
						.hashCode());
		result = 37
				* result
				+ (getRiskPrefer() == null ? 0 : this.getRiskPrefer()
						.hashCode());
		result = 37
				* result
				+ (getInvestPosition() == null ? 0 : this.getInvestPosition()
						.hashCode());
		result = 37
				* result
				+ (getInvestCycle() == null ? 0 : this.getInvestCycle()
						.hashCode());
		result = 37
				* result
				+ (getFinanceBusinessPrefer() == null ? 0 : this
						.getFinanceBusinessPrefer().hashCode());
		result = 37
				* result
				+ (getInterestInvestment() == null ? 0 : this
						.getInterestInvestment().hashCode());
		result = 37
				* result
				+ (getInvestStyle() == null ? 0 : this.getInvestStyle()
						.hashCode());
		result = 37
				* result
				+ (getInvestTarget() == null ? 0 : this.getInvestTarget()
						.hashCode());
		result = 37
				* result
				+ (getInvestChannel() == null ? 0 : this.getInvestChannel()
						.hashCode());
		result = 37
				* result
				+ (getPostDataFlag() == null ? 0 : this.getPostDataFlag()
						.hashCode());
		result = 37
				* result
				+ (getJoinCampFlag() == null ? 0 : this.getJoinCampFlag()
						.hashCode());
		result = 37
				* result
				+ (getReceiveSmsFlag() == null ? 0 : this.getReceiveSmsFlag()
						.hashCode());
		result = 37
				* result
				+ (getWelcomeText() == null ? 0 : this.getWelcomeText()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}