package com.ytec.fubonecif.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the M_CI_SERVICE_INFO database table.
 * 
 */
@Entity
@Table(name="M_CI_SERVICE_INFO")
public class MCiServiceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="M_CI_SERVICE_INFO_SERIALNO_GENERATOR" )
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="M_CI_SERVICE_INFO_SERIALNO_GENERATOR")
	@Column(unique=true, nullable=false, length=32)
	private String serialno;

	@Column(length=20)
	private String accept;

	@Column(name="ATM_HIGH", length=20)
	private String atmHigh;

	@Column(name="ATM_LIMIT", length=20)
	private String atmLimit;

	@Column(name="CHANGE_NOTICE", length=20)
	private String changeNotice;

	@Column(name="CUST_ID", length=20)
	private String custId;

	@Column(length=64)
	private String faxservicemail;

	@Column(name="IS_ATM_HIGH", length=2)
	private String isAtmHigh;

	@Column(name="IS_CARD_APPLY", length=2)
	private String isCardApply;

	@Column(name="IS_ELEBANK_SER", length=2)
	private String isElebankSer;

	@Column(name="IS_NT_BANK", length=2)
	private String isNtBank;

	@Column(name="IS_POS_HIGH", length=2)
	private String isPosHigh;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(length=2)
	private String likeelecbill;

	@Column(length=100)
	private String mail;

	@Column(name="MAIL_ADDRESS", length=20)
	private String mailAddress;

	@Column(name="MESSAGE_CODE", length=20)
	private String messageCode;

	@Column(name="MICRO_BANKING", length=20)
	private String microBanking;

	@Column(name="MOBILE_BANKING", length=20)
	private String mobileBanking;

	@Column(name="PHONE_BANKING", length=20)
	private String phoneBanking;

	@Column(name="POS_HIGH", length=20)
	private String posHigh;

	@Column(name="POS_LIMIT", length=20)
	private String posLimit;

	@Column(name="\"STATE\"", length=20)
	private String state;

	@Column(length=20)
	private String statements;

	@Column(name="TRANSACTION_SERVICE", length=20)
	private String transactionService;

	@Column(length=20)
	private String ukey;

    public MCiServiceInfo() {
    }

	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
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

	public String getFaxservicemail() {
		return this.faxservicemail;
	}

	public void setFaxservicemail(String faxservicemail) {
		this.faxservicemail = faxservicemail;
	}

	public String getIsAtmHigh() {
		return this.isAtmHigh;
	}

	public void setIsAtmHigh(String isAtmHigh) {
		this.isAtmHigh = isAtmHigh;
	}

	public String getIsCardApply() {
		return this.isCardApply;
	}

	public void setIsCardApply(String isCardApply) {
		this.isCardApply = isCardApply;
	}

	public String getIsElebankSer() {
		return this.isElebankSer;
	}

	public void setIsElebankSer(String isElebankSer) {
		this.isElebankSer = isElebankSer;
	}

	public String getIsNtBank() {
		return this.isNtBank;
	}

	public void setIsNtBank(String isNtBank) {
		this.isNtBank = isNtBank;
	}

	public String getIsPosHigh() {
		return this.isPosHigh;
	}

	public void setIsPosHigh(String isPosHigh) {
		this.isPosHigh = isPosHigh;
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

	public String getLikeelecbill() {
		return this.likeelecbill;
	}

	public void setLikeelecbill(String likeelecbill) {
		this.likeelecbill = likeelecbill;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMailAddress() {
		return this.mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
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

}