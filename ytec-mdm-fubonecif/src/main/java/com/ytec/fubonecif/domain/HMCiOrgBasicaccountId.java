package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiOrgBasicaccountId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgBasicaccountId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiOrgBasicaccountId() {
	}

	/** minimal constructor */
	public HMCiOrgBasicaccountId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgBasicaccountId(String custId, String basicAcctBankNo,
			String basicAcctBankName, String basicAcctNo, String basicAcctName,
			String basicAcctStat, String basicAcctLicNo,
			Date basicAcctOpenDate, Date basicAcctVerifyDate, String isOwnBank,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		if (!(other instanceof HMCiOrgBasicaccountId))
			return false;
		HMCiOrgBasicaccountId castOther = (HMCiOrgBasicaccountId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getBasicAcctBankNo() == castOther
						.getBasicAcctBankNo()) || (this.getBasicAcctBankNo() != null
						&& castOther.getBasicAcctBankNo() != null && this
						.getBasicAcctBankNo().equals(
								castOther.getBasicAcctBankNo())))
				&& ((this.getBasicAcctBankName() == castOther
						.getBasicAcctBankName()) || (this
						.getBasicAcctBankName() != null
						&& castOther.getBasicAcctBankName() != null && this
						.getBasicAcctBankName().equals(
								castOther.getBasicAcctBankName())))
				&& ((this.getBasicAcctNo() == castOther.getBasicAcctNo()) || (this
						.getBasicAcctNo() != null
						&& castOther.getBasicAcctNo() != null && this
						.getBasicAcctNo().equals(castOther.getBasicAcctNo())))
				&& ((this.getBasicAcctName() == castOther.getBasicAcctName()) || (this
						.getBasicAcctName() != null
						&& castOther.getBasicAcctName() != null && this
						.getBasicAcctName()
						.equals(castOther.getBasicAcctName())))
				&& ((this.getBasicAcctStat() == castOther.getBasicAcctStat()) || (this
						.getBasicAcctStat() != null
						&& castOther.getBasicAcctStat() != null && this
						.getBasicAcctStat()
						.equals(castOther.getBasicAcctStat())))
				&& ((this.getBasicAcctLicNo() == castOther.getBasicAcctLicNo()) || (this
						.getBasicAcctLicNo() != null
						&& castOther.getBasicAcctLicNo() != null && this
						.getBasicAcctLicNo().equals(
								castOther.getBasicAcctLicNo())))
				&& ((this.getBasicAcctOpenDate() == castOther
						.getBasicAcctOpenDate()) || (this
						.getBasicAcctOpenDate() != null
						&& castOther.getBasicAcctOpenDate() != null && this
						.getBasicAcctOpenDate().equals(
								castOther.getBasicAcctOpenDate())))
				&& ((this.getBasicAcctVerifyDate() == castOther
						.getBasicAcctVerifyDate()) || (this
						.getBasicAcctVerifyDate() != null
						&& castOther.getBasicAcctVerifyDate() != null && this
						.getBasicAcctVerifyDate().equals(
								castOther.getBasicAcctVerifyDate())))
				&& ((this.getIsOwnBank() == castOther.getIsOwnBank()) || (this
						.getIsOwnBank() != null
						&& castOther.getIsOwnBank() != null && this
						.getIsOwnBank().equals(castOther.getIsOwnBank())))
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
				+ (getBasicAcctBankNo() == null ? 0 : this.getBasicAcctBankNo()
						.hashCode());
		result = 37
				* result
				+ (getBasicAcctBankName() == null ? 0 : this
						.getBasicAcctBankName().hashCode());
		result = 37
				* result
				+ (getBasicAcctNo() == null ? 0 : this.getBasicAcctNo()
						.hashCode());
		result = 37
				* result
				+ (getBasicAcctName() == null ? 0 : this.getBasicAcctName()
						.hashCode());
		result = 37
				* result
				+ (getBasicAcctStat() == null ? 0 : this.getBasicAcctStat()
						.hashCode());
		result = 37
				* result
				+ (getBasicAcctLicNo() == null ? 0 : this.getBasicAcctLicNo()
						.hashCode());
		result = 37
				* result
				+ (getBasicAcctOpenDate() == null ? 0 : this
						.getBasicAcctOpenDate().hashCode());
		result = 37
				* result
				+ (getBasicAcctVerifyDate() == null ? 0 : this
						.getBasicAcctVerifyDate().hashCode());
		result = 37 * result
				+ (getIsOwnBank() == null ? 0 : this.getIsOwnBank().hashCode());
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