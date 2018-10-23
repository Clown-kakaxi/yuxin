package com.ytec.mdm.domain.biz;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AcrmACiCustRecognise entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ACRM_A_CI_CUST_RECOGNISE")
public class AcrmACiCustRecognise implements java.io.Serializable {

	// Fields

	private String custId;
	private String custName;
	private Date birthday;
	private String citizenship;
	private String identNo;
	private String identType;
	private String postPhone;
	private BigDecimal age;
	private String homeAddr;
	private String nickName;
	private Double currentAum;
	private String isPrivBankCust;
	private String isHuge;
	private String busiSource;
	private String riskLevel;
	private String needsLast;
	private String cardLvl;
	private String avoid;
	private String favor;
	private String drink;
	private String barcode;
	private String cardCode;
	private String cardIc;
	private String cardNfc;
	private String bankbook;
	private String cusFingerprint;
	private String cusFaceIdent;
	private String cusVein;
	private Double currentBalance;
	private Double fixPeriodBalance;
	private Double finanDepositBalance;
	private Double insurBalance;
	private Double currFormerly;
	private Double awardYnLine;
	private String postAddr;
	private String countryOrRegion;
	private String custRel;
	private String csat;
	private String note;
	private String backMrk;
	private String family;
	private String custSource;
	private String gifts;
	private String cusCons;
	private Date endDate;
	private Date comeDate;

	// Constructors

	/** default constructor */
	public AcrmACiCustRecognise() {
	}

	/** minimal constructor */
	public AcrmACiCustRecognise(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public AcrmACiCustRecognise(String custId, String custName, Date birthday,
			String citizenship, String identNo, String identType,
			String postPhone, BigDecimal age, String homeAddr, String nickName,
			Double currentAum, String isPrivBankCust, String isHuge,
			String busiSource, String riskLevel, String needsLast,
			String cardLvl, String avoid, String favor, String drink,
			String barcode, String cardCode, String cardIc, String cardNfc,
			String bankbook, String cusFingerprint, String cusFaceIdent,
			String cusVein, Double currentBalance, Double fixPeriodBalance,
			Double finanDepositBalance, Double insurBalance,
			Double currFormerly, Double awardYnLine, String postAddr,
			String countryOrRegion, String custRel, String csat, String note,
			String backMrk, String family, String custSource, String gifts,
			String cusCons, Date endDate, Date comeDate) {
		this.custId = custId;
		this.custName = custName;
		this.birthday = birthday;
		this.citizenship = citizenship;
		this.identNo = identNo;
		this.identType = identType;
		this.postPhone = postPhone;
		this.age = age;
		this.homeAddr = homeAddr;
		this.nickName = nickName;
		this.currentAum = currentAum;
		this.isPrivBankCust = isPrivBankCust;
		this.isHuge = isHuge;
		this.busiSource = busiSource;
		this.riskLevel = riskLevel;
		this.needsLast = needsLast;
		this.cardLvl = cardLvl;
		this.avoid = avoid;
		this.favor = favor;
		this.drink = drink;
		this.barcode = barcode;
		this.cardCode = cardCode;
		this.cardIc = cardIc;
		this.cardNfc = cardNfc;
		this.bankbook = bankbook;
		this.cusFingerprint = cusFingerprint;
		this.cusFaceIdent = cusFaceIdent;
		this.cusVein = cusVein;
		this.currentBalance = currentBalance;
		this.fixPeriodBalance = fixPeriodBalance;
		this.finanDepositBalance = finanDepositBalance;
		this.insurBalance = insurBalance;
		this.currFormerly = currFormerly;
		this.awardYnLine = awardYnLine;
		this.postAddr = postAddr;
		this.countryOrRegion = countryOrRegion;
		this.custRel = custRel;
		this.csat = csat;
		this.note = note;
		this.backMrk = backMrk;
		this.family = family;
		this.custSource = custSource;
		this.gifts = gifts;
		this.cusCons = cusCons;
		this.endDate = endDate;
		this.comeDate = comeDate;
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

	@Column(name = "CUST_NAME", length = 80)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "CITIZENSHIP", length = 20)
	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	@Column(name = "IDENT_NO", length = 40)
	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	@Column(name = "IDENT_TYPE", length = 20)
	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	@Column(name = "POST_PHONE", length = 35)
	public String getPostPhone() {
		return this.postPhone;
	}

	public void setPostPhone(String postPhone) {
		this.postPhone = postPhone;
	}

	@Column(name = "AGE", precision = 22, scale = 0)
	public BigDecimal getAge() {
		return this.age;
	}

	public void setAge(BigDecimal age) {
		this.age = age;
	}

	@Column(name = "HOME_ADDR", length = 200)
	public String getHomeAddr() {
		return this.homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	@Column(name = "NICK_NAME", length = 20)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "CURRENT_AUM", precision = 24, scale = 6)
	public Double getCurrentAum() {
		return this.currentAum;
	}

	public void setCurrentAum(Double currentAum) {
		this.currentAum = currentAum;
	}

	@Column(name = "IS_PRIV_BANK_CUST", length = 1)
	public String getIsPrivBankCust() {
		return this.isPrivBankCust;
	}

	public void setIsPrivBankCust(String isPrivBankCust) {
		this.isPrivBankCust = isPrivBankCust;
	}

	@Column(name = "IS_HUGE", length = 1)
	public String getIsHuge() {
		return this.isHuge;
	}

	public void setIsHuge(String isHuge) {
		this.isHuge = isHuge;
	}

	@Column(name = "BUSI_SOURCE", length = 20)
	public String getBusiSource() {
		return this.busiSource;
	}

	public void setBusiSource(String busiSource) {
		this.busiSource = busiSource;
	}

	@Column(name = "RISK_LEVEL", length = 20)
	public String getRiskLevel() {
		return this.riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	@Column(name = "NEEDS_LAST", length = 200)
	public String getNeedsLast() {
		return this.needsLast;
	}

	public void setNeedsLast(String needsLast) {
		this.needsLast = needsLast;
	}

	@Column(name = "CARD_LVL", length = 20)
	public String getCardLvl() {
		return this.cardLvl;
	}

	public void setCardLvl(String cardLvl) {
		this.cardLvl = cardLvl;
	}

	@Column(name = "AVOID", length = 100)
	public String getAvoid() {
		return this.avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}

	@Column(name = "FAVOR", length = 100)
	public String getFavor() {
		return this.favor;
	}

	public void setFavor(String favor) {
		this.favor = favor;
	}

	@Column(name = "DRINK", length = 100)
	public String getDrink() {
		return this.drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}

	@Column(name = "BARCODE", length = 100)
	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name = "CARD_CODE", length = 100)
	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	@Column(name = "CARD_IC", length = 100)
	public String getCardIc() {
		return this.cardIc;
	}

	public void setCardIc(String cardIc) {
		this.cardIc = cardIc;
	}

	@Column(name = "CARD_NFC", length = 100)
	public String getCardNfc() {
		return this.cardNfc;
	}

	public void setCardNfc(String cardNfc) {
		this.cardNfc = cardNfc;
	}

	@Column(name = "BANKBOOK", length = 100)
	public String getBankbook() {
		return this.bankbook;
	}

	public void setBankbook(String bankbook) {
		this.bankbook = bankbook;
	}

	@Column(name = "CUS_FINGERPRINT", length = 200)
	public String getCusFingerprint() {
		return this.cusFingerprint;
	}

	public void setCusFingerprint(String cusFingerprint) {
		this.cusFingerprint = cusFingerprint;
	}

	@Column(name = "CUS_FACE_IDENT", length = 200)
	public String getCusFaceIdent() {
		return this.cusFaceIdent;
	}

	public void setCusFaceIdent(String cusFaceIdent) {
		this.cusFaceIdent = cusFaceIdent;
	}

	@Column(name = "CUS_VEIN", length = 200)
	public String getCusVein() {
		return this.cusVein;
	}

	public void setCusVein(String cusVein) {
		this.cusVein = cusVein;
	}

	@Column(name = "CURRENT_BALANCE", precision = 24, scale = 6)
	public Double getCurrentBalance() {
		return this.currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	@Column(name = "FIX_PERIOD_BALANCE", precision = 24, scale = 6)
	public Double getFixPeriodBalance() {
		return this.fixPeriodBalance;
	}

	public void setFixPeriodBalance(Double fixPeriodBalance) {
		this.fixPeriodBalance = fixPeriodBalance;
	}

	@Column(name = "FINAN_DEPOSIT_BALANCE", precision = 24, scale = 6)
	public Double getFinanDepositBalance() {
		return this.finanDepositBalance;
	}

	public void setFinanDepositBalance(Double finanDepositBalance) {
		this.finanDepositBalance = finanDepositBalance;
	}

	@Column(name = "INSUR_BALANCE", precision = 24, scale = 6)
	public Double getInsurBalance() {
		return this.insurBalance;
	}

	public void setInsurBalance(Double insurBalance) {
		this.insurBalance = insurBalance;
	}

	@Column(name = "CURR_FORMERLY", precision = 24, scale = 6)
	public Double getCurrFormerly() {
		return this.currFormerly;
	}

	public void setCurrFormerly(Double currFormerly) {
		this.currFormerly = currFormerly;
	}

	@Column(name = "AWARD_YN_LINE", precision = 17, scale = 10)
	public Double getAwardYnLine() {
		return this.awardYnLine;
	}

	public void setAwardYnLine(Double awardYnLine) {
		this.awardYnLine = awardYnLine;
	}

	@Column(name = "POST_ADDR", length = 200)
	public String getPostAddr() {
		return this.postAddr;
	}

	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	@Column(name = "COUNTRY_OR_REGION", length = 20)
	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	@Column(name = "CUST_REL", length = 20)
	public String getCustRel() {
		return this.custRel;
	}

	public void setCustRel(String custRel) {
		this.custRel = custRel;
	}

	@Column(name = "CSAT", length = 20)
	public String getCsat() {
		return this.csat;
	}

	public void setCsat(String csat) {
		this.csat = csat;
	}

	@Column(name = "NOTE", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "BACK_MRK", length = 400)
	public String getBackMrk() {
		return this.backMrk;
	}

	public void setBackMrk(String backMrk) {
		this.backMrk = backMrk;
	}

	@Column(name = "FAMILY", length = 200)
	public String getFamily() {
		return this.family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	@Column(name = "CUST_SOURCE", length = 20)
	public String getCustSource() {
		return this.custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}

	@Column(name = "GIFTS", length = 200)
	public String getGifts() {
		return this.gifts;
	}

	public void setGifts(String gifts) {
		this.gifts = gifts;
	}

	@Column(name = "CUS_CONS", length = 20)
	public String getCusCons() {
		return this.cusCons;
	}

	public void setCusCons(String cusCons) {
		this.cusCons = cusCons;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COME_DATE", length = 7)
	public Date getComeDate() {
		return this.comeDate;
	}

	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}

}