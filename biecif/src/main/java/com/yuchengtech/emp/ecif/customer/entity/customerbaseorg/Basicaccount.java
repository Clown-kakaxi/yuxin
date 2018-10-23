package com.yuchengtech.emp.ecif.customer.entity.customerbaseorg;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the BASICACCOUNT database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_BASICACCOUNT")
public class Basicaccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="BASIC_ACCT_BANK_NAME", length=100)
	private String basicAcctBankName;

	@Column(name="BASIC_ACCT_BANK_NO", length=2)
	private String basicAcctBankNo;


	@Column(name="BASIC_ACCT_LIC_NO", length=20)
	private String basicAcctLicNo;

	@Column(name="BASIC_ACCT_NAME", length=60)
	private String basicAcctName;

	@Column(name="BASIC_ACCT_NO", length=32)
	private String basicAcctNo;

	@Column(name="BASIC_ACCT_OPEN_DATE",length=10)
	private String basicAcctOpenDate;

	@Column(name="BASIC_ACCT_STAT", length=1)
	private String basicAcctStat;

	@Column(name="BASIC_ACCT_VERIFY_DATE",length=10)
	private String basicAcctVerifyDate;

	@Column(name="IS_OWN_BANK", length=1)
	private String isOwnBank;

    public Basicaccount() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getBasicAcctBankName() {
		return basicAcctBankName;
	}

	public void setBasicAcctBankName(String basicAcctBankName) {
		this.basicAcctBankName = basicAcctBankName;
	}

	public String getBasicAcctBankNo() {
		return basicAcctBankNo;
	}

	public void setBasicAcctBankNo(String basicAcctBankNo) {
		this.basicAcctBankNo = basicAcctBankNo;
	}

	public String getBasicAcctLicNo() {
		return basicAcctLicNo;
	}

	public void setBasicAcctLicNo(String basicAcctLicNo) {
		this.basicAcctLicNo = basicAcctLicNo;
	}

	public String getBasicAcctName() {
		return basicAcctName;
	}

	public void setBasicAcctName(String basicAcctName) {
		this.basicAcctName = basicAcctName;
	}

	public String getBasicAcctNo() {
		return basicAcctNo;
	}

	public void setBasicAcctNo(String basicAcctNo) {
		this.basicAcctNo = basicAcctNo;
	}

	public String getBasicAcctOpenDate() {
		return basicAcctOpenDate;
	}

	public void setBasicAcctOpenDate(String basicAcctOpenDate) {
		this.basicAcctOpenDate = basicAcctOpenDate;
	}

	public String getBasicAcctStat() {
		return basicAcctStat;
	}

	public void setBasicAcctStat(String basicAcctStat) {
		this.basicAcctStat = basicAcctStat;
	}

	public String getBasicAcctVerifyDate() {
		return basicAcctVerifyDate;
	}

	public void setBasicAcctVerifyDate(String basicAcctVerifyDate) {
		this.basicAcctVerifyDate = basicAcctVerifyDate;
	}

	public String getIsOwnBank() {
		return isOwnBank;
	}

	public void setIsOwnBank(String isOwnBank) {
		this.isOwnBank = isOwnBank;
	}
}
