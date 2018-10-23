package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_A_CI_CUST_RECOGNISE database table.
 * 
 */
@Entity
@Table(name="ACRM_A_CI_CUST_RECOGNISE")
public class AcrmACiCustRecognise implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID")
	private String custId;
	
	private BigDecimal age;

	private String avoid;

	@Column(name="AWARD_YN_LINE")
	private BigDecimal awardYnLine;

	@Column(name="BACK_MRK")
	private String backMrk;

	private String bankbook;

	private String barcode;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	@Column(name="BUSI_SOURCE")
	private String busiSource;

	@Column(name="CARD_CODE")
	private String cardCode;

	@Column(name="CARD_IC")
	private String cardIc;

	@Column(name="CARD_LVL")
	private String cardLvl;

	@Column(name="CARD_NFC")
	private String cardNfc;

	private String citizenship;

    @Temporal( TemporalType.DATE)
	@Column(name="COME_DATE")
	private Date comeDate;

	@Column(name="COUNTRY_OR_REGION")
	private String countryOrRegion;

	private String csat;

	@Column(name="CURR_FORMERLY")
	private BigDecimal currFormerly;

	@Column(name="CURRENT_AUM")
	private BigDecimal currentAum;

	@Column(name="CURRENT_BALANCE")
	private BigDecimal currentBalance;

	@Column(name="CUS_CONS")
	private String cusCons;

	@Column(name="CUS_FACE_IDENT")
	private String cusFaceIdent;

	@Column(name="CUS_FINGERPRINT")
	private String cusFingerprint;

	@Column(name="CUS_VEIN")
	private String cusVein;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_REL")
	private String custRel;

	@Column(name="CUST_SOURCE")
	private String custSource;

	private String drink;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	private String family;

	private String favor;

	@Column(name="FINAN_DEPOSIT_BALANCE")
	private BigDecimal finanDepositBalance;

	@Column(name="FIX_PERIOD_BALANCE")
	private BigDecimal fixPeriodBalance;

	private String gifts;

	@Column(name="HOME_ADDR")
	private String homeAddr;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="INSUR_BALANCE")
	private BigDecimal insurBalance;

	@Column(name="IS_HUGE")
	private String isHuge;

	@Column(name="IS_PRIV_BANK_CUST")
	private String isPrivBankCust;

	@Column(name="NEEDS_LAST")
	private String needsLast;

	@Column(name="NICK_NAME")
	private String nickName;

	private String note;

	@Column(name="POST_ADDR")
	private String postAddr;

	@Column(name="POST_PHONE")
	private String postPhone;

	@Column(name="RISK_LEVEL")
	private String riskLevel;

    public AcrmACiCustRecognise() {
    }

	public BigDecimal getAge() {
		return this.age;
	}

	public void setAge(BigDecimal age) {
		this.age = age;
	}

	public String getAvoid() {
		return this.avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}

	public BigDecimal getAwardYnLine() {
		return this.awardYnLine;
	}

	public void setAwardYnLine(BigDecimal awardYnLine) {
		this.awardYnLine = awardYnLine;
	}

	public String getBackMrk() {
		return this.backMrk;
	}

	public void setBackMrk(String backMrk) {
		this.backMrk = backMrk;
	}

	public String getBankbook() {
		return this.bankbook;
	}

	public void setBankbook(String bankbook) {
		this.bankbook = bankbook;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBusiSource() {
		return this.busiSource;
	}

	public void setBusiSource(String busiSource) {
		this.busiSource = busiSource;
	}

	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardIc() {
		return this.cardIc;
	}

	public void setCardIc(String cardIc) {
		this.cardIc = cardIc;
	}

	public String getCardLvl() {
		return this.cardLvl;
	}

	public void setCardLvl(String cardLvl) {
		this.cardLvl = cardLvl;
	}

	public String getCardNfc() {
		return this.cardNfc;
	}

	public void setCardNfc(String cardNfc) {
		this.cardNfc = cardNfc;
	}

	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public Date getComeDate() {
		return this.comeDate;
	}

	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}

	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	public String getCsat() {
		return this.csat;
	}

	public void setCsat(String csat) {
		this.csat = csat;
	}

	public BigDecimal getCurrFormerly() {
		return this.currFormerly;
	}

	public void setCurrFormerly(BigDecimal currFormerly) {
		this.currFormerly = currFormerly;
	}

	public BigDecimal getCurrentAum() {
		return this.currentAum;
	}

	public void setCurrentAum(BigDecimal currentAum) {
		this.currentAum = currentAum;
	}

	public BigDecimal getCurrentBalance() {
		return this.currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getCusCons() {
		return this.cusCons;
	}

	public void setCusCons(String cusCons) {
		this.cusCons = cusCons;
	}

	public String getCusFaceIdent() {
		return this.cusFaceIdent;
	}

	public void setCusFaceIdent(String cusFaceIdent) {
		this.cusFaceIdent = cusFaceIdent;
	}

	public String getCusFingerprint() {
		return this.cusFingerprint;
	}

	public void setCusFingerprint(String cusFingerprint) {
		this.cusFingerprint = cusFingerprint;
	}

	public String getCusVein() {
		return this.cusVein;
	}

	public void setCusVein(String cusVein) {
		this.cusVein = cusVein;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustRel() {
		return this.custRel;
	}

	public void setCustRel(String custRel) {
		this.custRel = custRel;
	}

	public String getCustSource() {
		return this.custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}

	public String getDrink() {
		return this.drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFamily() {
		return this.family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getFavor() {
		return this.favor;
	}

	public void setFavor(String favor) {
		this.favor = favor;
	}

	public BigDecimal getFinanDepositBalance() {
		return this.finanDepositBalance;
	}

	public void setFinanDepositBalance(BigDecimal finanDepositBalance) {
		this.finanDepositBalance = finanDepositBalance;
	}

	public BigDecimal getFixPeriodBalance() {
		return this.fixPeriodBalance;
	}

	public void setFixPeriodBalance(BigDecimal fixPeriodBalance) {
		this.fixPeriodBalance = fixPeriodBalance;
	}

	public String getGifts() {
		return this.gifts;
	}

	public void setGifts(String gifts) {
		this.gifts = gifts;
	}

	public String getHomeAddr() {
		return this.homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public BigDecimal getInsurBalance() {
		return this.insurBalance;
	}

	public void setInsurBalance(BigDecimal insurBalance) {
		this.insurBalance = insurBalance;
	}

	public String getIsHuge() {
		return this.isHuge;
	}

	public void setIsHuge(String isHuge) {
		this.isHuge = isHuge;
	}

	public String getIsPrivBankCust() {
		return this.isPrivBankCust;
	}

	public void setIsPrivBankCust(String isPrivBankCust) {
		this.isPrivBankCust = isPrivBankCust;
	}

	public String getNeedsLast() {
		return this.needsLast;
	}

	public void setNeedsLast(String needsLast) {
		this.needsLast = needsLast;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPostAddr() {
		return this.postAddr;
	}

	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	public String getPostPhone() {
		return this.postPhone;
	}

	public void setPostPhone(String postPhone) {
		this.postPhone = postPhone;
	}

	public String getRiskLevel() {
		return this.riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

}