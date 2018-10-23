package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the OCRM_F_CI_OPEN_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_OPEN_INFO")
public class OcrmFCiOpenInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="ACC_NO")
	private String accNo;

	@Column(name="CARD_CATLG")
	private String cardCatlg;

	@Column(name="CARD_FC")
	private String cardFc;

	@Column(name="CARD_NO")
	private String cardNo;

	@Column(name="CARD_TYPE")
	private String cardType;

	@Column(name="CREATE_ORG")
	private String createOrg;
	
	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUR_TYPE")
	private String curType;

	@Column(name="INOUT_FLAG")
	private String inoutFlag;

	@Column(name="IS_CHG_NOTICE")
	private String isChgNotice;

	@Column(name="IS_CHK")
	private String isChk;

	@Column(name="IS_DFTCNT_ATM")
	private String isDftcntAtm;

	@Column(name="IS_DFTCNT_EB")
	private String isDftcntEb;

	@Column(name="IS_DFTLMT_POS")
	private String isDftlmtPos;

	@Column(name="IS_DFTLMTD_ATM")
	private String isDftlmtdAtm;

	@Column(name="IS_DFTLMTD_EB")
	private String isDftlmtdEb;

	@Column(name="IS_DFTLMTY_ATM")
	private String isDftlmtyAtm;

	@Column(name="IS_DFTLMTY_EB")
	private String isDftlmtyEb;

	@Column(name="IS_EQU_EMAIL")
	private String isEquEmail;

	@Column(name="IS_MSG_NETBK")
	private String isMsgNetbk;

	@Column(name="IS_MSG_PHONE")
	private String isMsgPhone;

	@Column(name="IS_NETBK")
	private String isNetbk;

	@Column(name="IS_OPEN_CARD")
	private String isOpenCard;

	@Column(name="IS_OPEN_EBK")
	private String isOpenEbk;

	@Column(name="IS_PHONE")
	private String isPhone;

	@Column(name="IS_TELBK")
	private String isTelbk;

	@Column(name="IS_UKEY")
	private String isUkey;

	@Column(name="LMTAMT_D_ATM")
	private BigDecimal lmtamtDAtm;

	@Column(name="LMTAMT_D_EB")
	private BigDecimal lmtamtDEb;

	@Column(name="LMTAMT_POS")
	private BigDecimal lmtamtPos;

	@Column(name="LMTAMT_Y_ATM")
	private BigDecimal lmtamtYAtm;

	@Column(name="LMTAMT_Y_EB")
	private BigDecimal lmtamtYEb;

	@Column(name="LMTCNT_D_ATM")
	private BigDecimal lmtcntDAtm;

	@Column(name="LMTCNT_D_EB")
	private BigDecimal lmtcntDEb;

	@Column(name="UPDATE_ORG")
	private String updateOrg;

    @Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;
	
	@Column(name= "IS_EQU_ADD")
	private String isEquAdd;

    public OcrmFCiOpenInfo() {
    }

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAccNo() {
		return this.accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getCardCatlg() {
		return this.cardCatlg;
	}

	public void setCardCatlg(String cardCatlg) {
		this.cardCatlg = cardCatlg;
	}

	public String getCardFc() {
		return this.cardFc;
	}

	public void setCardFc(String cardFc) {
		this.cardFc = cardFc;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCreateOrg() {
		return this.createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}


	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCurType() {
		return this.curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getInoutFlag() {
		return this.inoutFlag;
	}

	public void setInoutFlag(String inoutFlag) {
		this.inoutFlag = inoutFlag;
	}

	public String getIsChgNotice() {
		return this.isChgNotice;
	}

	public void setIsChgNotice(String isChgNotice) {
		this.isChgNotice = isChgNotice;
	}

	public String getIsChk() {
		return this.isChk;
	}

	public void setIsChk(String isChk) {
		this.isChk = isChk;
	}

	public String getIsDftcntAtm() {
		return this.isDftcntAtm;
	}

	public void setIsDftcntAtm(String isDftcntAtm) {
		this.isDftcntAtm = isDftcntAtm;
	}

	public String getIsDftcntEb() {
		return this.isDftcntEb;
	}

	public void setIsDftcntEb(String isDftcntEb) {
		this.isDftcntEb = isDftcntEb;
	}

	public String getIsDftlmtPos() {
		return this.isDftlmtPos;
	}

	public void setIsDftlmtPos(String isDftlmtPos) {
		this.isDftlmtPos = isDftlmtPos;
	}

	public String getIsDftlmtdAtm() {
		return this.isDftlmtdAtm;
	}

	public void setIsDftlmtdAtm(String isDftlmtdAtm) {
		this.isDftlmtdAtm = isDftlmtdAtm;
	}

	public String getIsDftlmtdEb() {
		return this.isDftlmtdEb;
	}

	public void setIsDftlmtdEb(String isDftlmtdEb) {
		this.isDftlmtdEb = isDftlmtdEb;
	}

	public String getIsDftlmtyAtm() {
		return this.isDftlmtyAtm;
	}

	public void setIsDftlmtyAtm(String isDftlmtyAtm) {
		this.isDftlmtyAtm = isDftlmtyAtm;
	}

	public String getIsDftlmtyEb() {
		return this.isDftlmtyEb;
	}

	public void setIsDftlmtyEb(String isDftlmtyEb) {
		this.isDftlmtyEb = isDftlmtyEb;
	}

	public String getIsEquEmail() {
		return this.isEquEmail;
	}

	public void setIsEquEmail(String isEquEmail) {
		this.isEquEmail = isEquEmail;
	}

	public String getIsMsgNetbk() {
		return this.isMsgNetbk;
	}

	public void setIsMsgNetbk(String isMsgNetbk) {
		this.isMsgNetbk = isMsgNetbk;
	}

	public String getIsMsgPhone() {
		return this.isMsgPhone;
	}

	public void setIsMsgPhone(String isMsgPhone) {
		this.isMsgPhone = isMsgPhone;
	}

	public String getIsNetbk() {
		return this.isNetbk;
	}

	public void setIsNetbk(String isNetbk) {
		this.isNetbk = isNetbk;
	}

	public String getIsOpenCard() {
		return this.isOpenCard;
	}

	public void setIsOpenCard(String isOpenCard) {
		this.isOpenCard = isOpenCard;
	}

	public String getIsOpenEbk() {
		return this.isOpenEbk;
	}

	public void setIsOpenEbk(String isOpenEbk) {
		this.isOpenEbk = isOpenEbk;
	}

	public String getIsPhone() {
		return this.isPhone;
	}

	public void setIsPhone(String isPhone) {
		this.isPhone = isPhone;
	}

	public String getIsTelbk() {
		return this.isTelbk;
	}

	public void setIsTelbk(String isTelbk) {
		this.isTelbk = isTelbk;
	}

	public String getIsUkey() {
		return this.isUkey;
	}

	public void setIsUkey(String isUkey) {
		this.isUkey = isUkey;
	}

	public BigDecimal getLmtamtDAtm() {
		return this.lmtamtDAtm;
	}

	public void setLmtamtDAtm(BigDecimal lmtamtDAtm) {
		this.lmtamtDAtm = lmtamtDAtm;
	}

	public BigDecimal getLmtamtDEb() {
		return this.lmtamtDEb;
	}

	public void setLmtamtDEb(BigDecimal lmtamtDEb) {
		this.lmtamtDEb = lmtamtDEb;
	}

	public BigDecimal getLmtamtPos() {
		return this.lmtamtPos;
	}

	public void setLmtamtPos(BigDecimal lmtamtPos) {
		this.lmtamtPos = lmtamtPos;
	}

	public BigDecimal getLmtamtYAtm() {
		return this.lmtamtYAtm;
	}

	public void setLmtamtYAtm(BigDecimal lmtamtYAtm) {
		this.lmtamtYAtm = lmtamtYAtm;
	}

	public BigDecimal getLmtamtYEb() {
		return this.lmtamtYEb;
	}

	public void setLmtamtYEb(BigDecimal lmtamtYEb) {
		this.lmtamtYEb = lmtamtYEb;
	}

	public BigDecimal getLmtcntDAtm() {
		return this.lmtcntDAtm;
	}

	public void setLmtcntDAtm(BigDecimal lmtcntDAtm) {
		this.lmtcntDAtm = lmtcntDAtm;
	}

	public BigDecimal getLmtcntDEb() {
		return this.lmtcntDEb;
	}

	public void setLmtcntDEb(BigDecimal lmtcntDEb) {
		this.lmtcntDEb = lmtcntDEb;
	}

	public String getUpdateOrg() {
		return this.updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}


	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public Timestamp getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getIsEquAdd() {
		return isEquAdd;
	}

	public void setIsEquAdd(String isEquAdd) {
		this.isEquAdd = isEquAdd;
	}
	
	
}