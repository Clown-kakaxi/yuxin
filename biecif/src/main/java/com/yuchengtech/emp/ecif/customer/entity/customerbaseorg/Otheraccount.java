package com.yuchengtech.emp.ecif.customer.entity.customerbaseorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the OTHERACCOUNT database table.
 * 
 */
@Entity
@Table(name="OTHERACCOUNT")
public class Otheraccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="OTHER_ACCOUNT_ID", unique=true, nullable=false)
	private Long otherAccountId;

	@Column(name="ACCT_NAME", length=60)
	private String acctName;

	@Column(name="ACCT_NO", length=32)
	private String acctNo;

	@Column(name="ACCT_OPEN_BANK", length=100)
	private String acctOpenBank;

	@Column(name="ACCT_OPEN_DATE",length=20)
	private String acctOpenDate;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="OTHER_ACCT_NAME", length=60)
	private String otherAcctName;

	@Column(name="OTHER_ACCT_NO", length=32)
	private String otherAcctNo;

	@Column(name="OTHER_BANK", length=100)
	private String otherBank;

    public Otheraccount() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOtherAccountId() {
		return this.otherAccountId;
	}

	public void setOtherAccountId(Long otherAccountId) {
		this.otherAccountId = otherAccountId;
	}

	public String getAcctName() {
		return this.acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctNo() {
		return this.acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctOpenBank() {
		return this.acctOpenBank;
	}

	public void setAcctOpenBank(String acctOpenBank) {
		this.acctOpenBank = acctOpenBank;
	}

	public String getAcctOpenDate() {
		return this.acctOpenDate;
	}

	public void setAcctOpenDate(String acctOpenDate) {
		this.acctOpenDate = acctOpenDate;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getOtherAcctName() {
		return this.otherAcctName;
	}

	public void setOtherAcctName(String otherAcctName) {
		this.otherAcctName = otherAcctName;
	}

	public String getOtherAcctNo() {
		return this.otherAcctNo;
	}

	public void setOtherAcctNo(String otherAcctNo) {
		this.otherAcctNo = otherAcctNo;
	}

	public String getOtherBank() {
		return this.otherBank;
	}

	public void setOtherBank(String otherBank) {
		this.otherBank = otherBank;
	}

}