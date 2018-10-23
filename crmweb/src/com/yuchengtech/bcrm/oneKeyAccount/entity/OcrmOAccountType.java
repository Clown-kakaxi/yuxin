package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the OCRM_O_ACCOUNT_TYPE database table.
 * 
 */
@Entity
@Table(name="OCRM_O_ACCOUNT_TYPE")
public class OcrmOAccountType implements Serializable {
	private static final long serialVersionUID = 1L;

	//key
	@Id
	@Column(name="SERIALNO")
	private String serialno;
	
	//账户类别
	@Column(name="ACCOUNT")
	private String account;

	//账户类别中文名
	@Column(name="ACCOUNTNAME")
	private String accountname;

	//第9-10位
	@Column(name="BITNO")
	private String bitno;

	//币种
	@Column(name="CURRENCY")
	private String currency;

	//币种中文名
	@Column(name="CURRENCYNAME")
	private String currencyname;

	//境内外类型
	@Column(name="SIDE")
	private String side;

	//境内外类型中文名
	@Column(name="SIDENAME")
	private String sidename;

	//状态
	@Column(name="STATUS")
	private String status;

	//科目
	@Column(name="SUBJECT")
	private String subject;

    public OcrmOAccountType() {
    }

	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountname() {
		return this.accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getBitno() {
		return this.bitno;
	}

	public void setBitno(String bitno) {
		this.bitno = bitno;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyname() {
		return this.currencyname;
	}

	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}

	public String getSide() {
		return this.side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getSidename() {
		return this.sidename;
	}

	public void setSidename(String sidename) {
		this.sidename = sidename;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}