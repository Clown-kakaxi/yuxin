package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiOrgBasicaccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_BASICACCOUNT")
public class MCiOrgBasicaccount implements java.io.Serializable {

	// Fields

	private String custId;
	private String basicAcctBankNo;
	private String basicAcctBankName;
	private String basicAcctNo;
	private String basicAcctName;
	private String basicAcctStat;
	private String basicAcctLicNo;
	private Date basicAcctOpenDate;
	private Date basicAcctVerifyDate;
	private String isOwnBank;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiOrgBasicaccount() {
	}

	/** minimal constructor */
	public MCiOrgBasicaccount(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiOrgBasicaccount(String custId, String basicAcctBankNo,
			String basicAcctBankName, String basicAcctNo, String basicAcctName,
			String basicAcctStat, String basicAcctLicNo,
			Date basicAcctOpenDate, Date basicAcctVerifyDate, String isOwnBank,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custId = custId;
		this.basicAcctBankNo = basicAcctBankNo;
		this.basicAcctBankName = basicAcctBankName;
		this.basicAcctNo = basicAcctNo;
		this.basicAcctName = basicAcctName;
		this.basicAcctStat = basicAcctStat;
		this.basicAcctLicNo = basicAcctLicNo;
		this.basicAcctOpenDate = basicAcctOpenDate;
		this.basicAcctVerifyDate = basicAcctVerifyDate;
		this.isOwnBank = isOwnBank;
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

	@Column(name = "BASIC_ACCT_BANK_NO", length = 20)
	public String getBasicAcctBankNo() {
		return this.basicAcctBankNo;
	}

	public void setBasicAcctBankNo(String basicAcctBankNo) {
		this.basicAcctBankNo = basicAcctBankNo;
	}

	@Column(name = "BASIC_ACCT_BANK_NAME", length = 100)
	public String getBasicAcctBankName() {
		return this.basicAcctBankName;
	}

	public void setBasicAcctBankName(String basicAcctBankName) {
		this.basicAcctBankName = basicAcctBankName;
	}

	@Column(name = "BASIC_ACCT_NO", length = 32)
	public String getBasicAcctNo() {
		return this.basicAcctNo;
	}

	public void setBasicAcctNo(String basicAcctNo) {
		this.basicAcctNo = basicAcctNo;
	}

	@Column(name = "BASIC_ACCT_NAME", length = 60)
	public String getBasicAcctName() {
		return this.basicAcctName;
	}

	public void setBasicAcctName(String basicAcctName) {
		this.basicAcctName = basicAcctName;
	}

	@Column(name = "BASIC_ACCT_STAT", length = 20)
	public String getBasicAcctStat() {
		return this.basicAcctStat;
	}

	public void setBasicAcctStat(String basicAcctStat) {
		this.basicAcctStat = basicAcctStat;
	}

	@Column(name = "BASIC_ACCT_LIC_NO", length = 20)
	public String getBasicAcctLicNo() {
		return this.basicAcctLicNo;
	}

	public void setBasicAcctLicNo(String basicAcctLicNo) {
		this.basicAcctLicNo = basicAcctLicNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BASIC_ACCT_OPEN_DATE", length = 7)
	public Date getBasicAcctOpenDate() {
		return this.basicAcctOpenDate;
	}

	public void setBasicAcctOpenDate(Date basicAcctOpenDate) {
		this.basicAcctOpenDate = basicAcctOpenDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BASIC_ACCT_VERIFY_DATE", length = 7)
	public Date getBasicAcctVerifyDate() {
		return this.basicAcctVerifyDate;
	}

	public void setBasicAcctVerifyDate(Date basicAcctVerifyDate) {
		this.basicAcctVerifyDate = basicAcctVerifyDate;
	}

	@Column(name = "IS_OWN_BANK", length = 1)
	public String getIsOwnBank() {
		return this.isOwnBank;
	}

	public void setIsOwnBank(String isOwnBank) {
		this.isOwnBank = isOwnBank;
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