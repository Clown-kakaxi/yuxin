package com.yuchengtech.emp.ecif.customer.entity.customerevaluate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONPREFERENCE database table.
 * 
 */
@Entity
@Table(name="PERSONPREFERENCE")
public class Personpreference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PREFERENCE_ID", unique=true, nullable=false)
	private Long preferenceId;

	@Column(name="ABUSE",length=200)
	private String abuse;

	@Column(name="CONSUM_HABIT", length=20)
	private String consumHabit;

	@Column(name="CONTACT_FREQUENCY_PREFER", length=20)
	private String contactFrequencyPrefer;

	@Column(name="CONTACT_TIME", length=100)
	private String contactTime;

	@Column(name="CONTACT_TIME_PREFER", length=20)
	private String contactTimePrefer;

	@Column(name="CONTACT_TYPE", length=20)
	private String contactType;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="FILM_TV_TYPE", length=500)
	private String filmTvType;

	@Column(name="GIFT_PREFER", length=20)
	private String giftPrefer;

	@Column(name="HOBBY",length=200)
	private String hobby;

	@Column(name="INSURANCE_PREFER", length=20)
	private String insurancePrefer;

	@Column(name="INTEREST_INVESTMENT", length=500)
	private String interestInvestment;

	@Column(name="INVEST_CHANNEL", length=500)
	private String investChannel;

	@Column(name="INVEST_EXPR", length=20)
	private String investExpr;

	@Column(name="INVEST_STYLE", length=2)
	private String investStyle;

	@Column(name="INVEST_TARGET", length=500)
	private String investTarget;

	@Column(name="JOIN_CAMP_FLAG", length=1)
	private String joinCampFlag;

	@Column(name="LANG_PREFER", length=20)
	private String langPrefer;

	@Column(name="MAGAZINE",length=500)
	private String magazine;

	@Column(name="MEDIA",length=500)
	private String media;

	@Column(name="OTHER_BANK_PROD", length=200)
	private String otherBankProd;

	@Column(name="PET",length=500)
	private String pet;

	@Column(name="POST_DATA_FLAG", length=1)
	private String postDataFlag;

	@Column(name="REC_WAY", length=500)
	private String recWay;

	@Column(name="RISK_PREFER", length=20)
	private String riskPrefer;

	@Column(name="SELF_BANK_PROD", length=200)
	private String selfBankProd;

	@Column(name="SPORTS",length=500)
	private String sports;

	@Column(name="TABOO",length=200)
	private String taboo;

	@Column(name="TITLE_PREFER", length=20)
	private String titlePrefer;

	@Column(name="VEHICLE_PREFER", length=20)
	private String vehiclePrefer;

	@Column(name="WELCOME_TEXT", length=100)
	private String welcomeText;

    public Personpreference() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPreferenceId() {
		return this.preferenceId;
	}

	public void setPreferenceId(Long preferenceId) {
		this.preferenceId = preferenceId;
	}

	public String getAbuse() {
		return this.abuse;
	}

	public void setAbuse(String abuse) {
		this.abuse = abuse;
	}

	public String getConsumHabit() {
		return this.consumHabit;
	}

	public void setConsumHabit(String consumHabit) {
		this.consumHabit = consumHabit;
	}

	public String getContactFrequencyPrefer() {
		return this.contactFrequencyPrefer;
	}

	public void setContactFrequencyPrefer(String contactFrequencyPrefer) {
		this.contactFrequencyPrefer = contactFrequencyPrefer;
	}

	public String getContactTime() {
		return this.contactTime;
	}

	public void setContactTime(String contactTime) {
		this.contactTime = contactTime;
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

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getFilmTvType() {
		return this.filmTvType;
	}

	public void setFilmTvType(String filmTvType) {
		this.filmTvType = filmTvType;
	}

	public String getGiftPrefer() {
		return this.giftPrefer;
	}

	public void setGiftPrefer(String giftPrefer) {
		this.giftPrefer = giftPrefer;
	}

	public String getHobby() {
		return this.hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
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

	public String getInvestExpr() {
		return this.investExpr;
	}

	public void setInvestExpr(String investExpr) {
		this.investExpr = investExpr;
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

	public String getMagazine() {
		return this.magazine;
	}

	public void setMagazine(String magazine) {
		this.magazine = magazine;
	}

	public String getMedia() {
		return this.media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getOtherBankProd() {
		return this.otherBankProd;
	}

	public void setOtherBankProd(String otherBankProd) {
		this.otherBankProd = otherBankProd;
	}

	public String getPet() {
		return this.pet;
	}

	public void setPet(String pet) {
		this.pet = pet;
	}

	public String getPostDataFlag() {
		return this.postDataFlag;
	}

	public void setPostDataFlag(String postDataFlag) {
		this.postDataFlag = postDataFlag;
	}

	public String getRecWay() {
		return this.recWay;
	}

	public void setRecWay(String recWay) {
		this.recWay = recWay;
	}

	public String getRiskPrefer() {
		return this.riskPrefer;
	}

	public void setRiskPrefer(String riskPrefer) {
		this.riskPrefer = riskPrefer;
	}

	public String getSelfBankProd() {
		return this.selfBankProd;
	}

	public void setSelfBankProd(String selfBankProd) {
		this.selfBankProd = selfBankProd;
	}

	public String getSports() {
		return this.sports;
	}

	public void setSports(String sports) {
		this.sports = sports;
	}

	public String getTaboo() {
		return this.taboo;
	}

	public void setTaboo(String taboo) {
		this.taboo = taboo;
	}

	public String getTitlePrefer() {
		return this.titlePrefer;
	}

	public void setTitlePrefer(String titlePrefer) {
		this.titlePrefer = titlePrefer;
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