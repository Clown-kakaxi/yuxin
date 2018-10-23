package com.yuchengtech.bcrm.customer.level.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_A_CI_CARD_APPLY database table.
 * 
 */
@Entity
@Table(name="ACRM_A_CI_CARD_APPLY")
public class AcrmACiCardApply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_A_CI_CARD_APPLY_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_A_CI_CARD_APPLY_ID_GENERATOR")
	private Long id;

	@Column(name="AMT_AVG_30DAYS")
	private BigDecimal amtAvg30days;

	@Column(name="CARD_APP_STATUS")
	private String cardAppStatus;
	
	@Temporal( TemporalType.DATE)
	@Column(name="CARD_APP_VALIDATE")
	private Date cardAppValidate;


	@Column(name="USER_ID")
	private String userId;
	
	@Temporal( TemporalType.DATE)
	@Column(name="APPLY_DATE")
	private Date applyDate;
	
	@Column(name="CARD_LEV_APP")
	private String cardLevApp;

	@Column(name="CARD_LVL")
	private String cardLvl;

	@Column(name="CARD_NUM")
	private BigDecimal cardNum;

	@Column(name="CUST_GRADE")
	private String custGrade;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="GET_WAY")
	private String getWay;

	@Column(name="H_CARD_LEV")
	private String hCardLev;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="SENT_ADDRESS")
	private String sentAddress;

	@Column(name="SENT_NAME")
	private String sentName;

	@Column(name="SENT_PHONE")
	private String sentPhone;

    public AcrmACiCardApply() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String gethCardLev() {
		return hCardLev;
	}

	public void sethCardLev(String hCardLev) {
		this.hCardLev = hCardLev;
	}

	public BigDecimal getAmtAvg30days() {
		return this.amtAvg30days;
	}

	public void setAmtAvg30days(BigDecimal amtAvg30days) {
		this.amtAvg30days = amtAvg30days;
	}

	public String getCardAppStatus() {
		return this.cardAppStatus;
	}

	public void setCardAppStatus(String cardAppStatus) {
		this.cardAppStatus = cardAppStatus;
	}

	public Date getCardAppValidate() {
		return this.cardAppValidate;
	}

	public void setCardAppValidate(Date cardAppValidate) {
		this.cardAppValidate = cardAppValidate;
	}

	public String getCardLevApp() {
		return this.cardLevApp;
	}

	public void setCardLevApp(String cardLevApp) {
		this.cardLevApp = cardLevApp;
	}

	public String getCardLvl() {
		return this.cardLvl;
	}

	public void setCardLvl(String cardLvl) {
		this.cardLvl = cardLvl;
	}

	public BigDecimal getCardNum() {
		return this.cardNum;
	}

	public void setCardNum(BigDecimal cardNum) {
		this.cardNum = cardNum;
	}

	public String getCustGrade() {
		return this.custGrade;
	}

	public void setCustGrade(String custGrade) {
		this.custGrade = custGrade;
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

	public String getGetWay() {
		return this.getWay;
	}

	public void setGetWay(String getWay) {
		this.getWay = getWay;
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

	public String getSentAddress() {
		return this.sentAddress;
	}

	public void setSentAddress(String sentAddress) {
		this.sentAddress = sentAddress;
	}

	public String getSentName() {
		return this.sentName;
	}

	public void setSentName(String sentName) {
		this.sentName = sentName;
	}

	public String getSentPhone() {
		return this.sentPhone;
	}

	public void setSentPhone(String sentPhone) {
		this.sentPhone = sentPhone;
	}

}