package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_ACCOUNT_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_BANK_SERVICE")
public class AcrmFCiBankService implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @Column(name="CUST_ID")
	private String custId;
	
    @Column(name="ACCEPT")
	private String accept;

	@Column(name="ATM_HIGH")
	private String atmHigh;

	@Column(name="ATM_LIMIT")
	private String atmLimit;

	@Column(name="CHANGE_NOTICE")
	private String changeNotice;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="MAIL")
	private String mail;
	
	@Column(name="MAIL_ADDRESS")
	private String mailAddress;

	@Column(name="MESSAGE_CODE")
	private String messageCode;

	@Column(name="MICRO_BANKING")
	private String microBanking;

	@Column(name="MOBILE_BANKING")
	private String mobileBanking;

	@Column(name="PHONE_BANKING")
	private String phoneBanking;

	@Column(name="POS_HIGH")
	private String posHigh;

	@Column(name="POS_LIMIT")
	private String posLimit;

	@Column(name="STATE")
	private String state;

	@Column(name="STATEMENTS")
	private String statements;

	@Column(name="TRANSACTION_SERVICE")
	private String transactionService;
	
	@Column(name="IS_CARD_APPLY")
	private String isCardApply;
	
	@Column(name="IS_NT_BANK")
	private String isNtBank;
	
	@Column(name="IS_ELEBANK_SER")
	private String isElebankSer;
	
	@Column(name="IS_ATM_HIGH")
	private String isAtmHigh;
	
	@Column(name="IS_POS_HIGH")
	private String isPosHigh;
	
	@Column(name="UKEY")
	private String ukey;
	
	@Column(name="UKEY_LOST")
	private String ukeyLost;
	
	@Column(name="MOBILE_BANKING_QUERY")
	private String mobileBankingQuery;
	
	@Column(name="MOBILE_BANKING_TRADE")
	private String mobileBankingTrade;
	
	@Column(name="FAX_NUMBER")
	private String faxNumber;
	
    public AcrmFCiBankService() {
    }

	public String getIsCardApply() {
		return isCardApply;
	}

	public void setIsCardApply(String isCardApply) {
		this.isCardApply = isCardApply;
	}

	public String getIsNtBank() {
		return isNtBank;
	}

	public void setIsNtBank(String isNtBank) {
		this.isNtBank = isNtBank;
	}

	public String getIsElebankSer() {
		return isElebankSer;
	}

	public void setIsElebankSer(String isElebankSer) {
		this.isElebankSer = isElebankSer;
	}

	public String getIsAtmHigh() {
		return isAtmHigh;
	}

	public void setIsAtmHigh(String isAtmHigh) {
		this.isAtmHigh = isAtmHigh;
	}

	public String getIsPosHigh() {
		return isPosHigh;
	}

	public void setIsPosHigh(String isPosHigh) {
		this.isPosHigh = isPosHigh;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getAccept() {
		return this.accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAtmHigh() {
		return this.atmHigh;
	}

	public void setAtmHigh(String atmHigh) {
		this.atmHigh = atmHigh;
	}

	public String getAtmLimit() {
		return this.atmLimit;
	}

	public void setAtmLimit(String atmLimit) {
		this.atmLimit = atmLimit;
	}

	public String getChangeNotice() {
		return this.changeNotice;
	}

	public void setChangeNotice(String changeNotice) {
		this.changeNotice = changeNotice;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMessageCode() {
		return this.messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMicroBanking() {
		return this.microBanking;
	}

	public void setMicroBanking(String microBanking) {
		this.microBanking = microBanking;
	}

	public String getMobileBanking() {
		return this.mobileBanking;
	}

	public void setMobileBanking(String mobileBanking) {
		this.mobileBanking = mobileBanking;
	}

	public String getPhoneBanking() {
		return this.phoneBanking;
	}

	public void setPhoneBanking(String phoneBanking) {
		this.phoneBanking = phoneBanking;
	}

	public String getPosHigh() {
		return this.posHigh;
	}

	public void setPosHigh(String posHigh) {
		this.posHigh = posHigh;
	}

	public String getPosLimit() {
		return this.posLimit;
	}

	public void setPosLimit(String posLimit) {
		this.posLimit = posLimit;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatements() {
		return this.statements;
	}

	public void setStatements(String statements) {
		this.statements = statements;
	}

	public String getTransactionService() {
		return this.transactionService;
	}

	public void setTransactionService(String transactionService) {
		this.transactionService = transactionService;
	}

	public String getUkey() {
		return this.ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}

	public String getUkeyLost() {
		return ukeyLost;
	}

	public void setUkeyLost(String ukeyLost) {
		this.ukeyLost = ukeyLost;
	}

	public String getMobileBankingQuery() {
		return mobileBankingQuery;
	}

	public void setMobileBankingQuery(String mobileBankingQuery) {
		this.mobileBankingQuery = mobileBankingQuery;
	}

	public String getMobileBankingTrade() {
		return mobileBankingTrade;
	}

	public void setMobileBankingTrade(String mobileBankingTrade) {
		this.mobileBankingTrade = mobileBankingTrade;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

}