package com.ytec.fubonecif.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the O_WZ_SERVICE_INFO database table.
 * 
 */
@Entity
@Table(name="O_WZ_SERVICE_INFO")
public class OWzServiceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="O_WZ_SERVICE_INFO_SERIALNO_GENERATOR" )
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="O_WZ_SERVICE_INFO_SERIALNO_GENERATOR")
	@Column(unique=true, nullable=false, length=32)
	private String serialno;

	@Column(length=2)
	private String accchangenot;

	@Column(length=2)
	private String approvepass;

	@Column(length=18)
	private String approvepassloan;

	@Column(nullable=false, length=32)
	private String customerid;

	@Column(length=2)
	private String debitcardapply;

	@Column(length=2)
	private String debitcardapply1;

	@Column(length=2)
	private String debitcardapply2;

	@Column(length=18)
	private String debitcardapply22;

	@Column(length=2)
	private String debitcardapply3;

	@Column(length=2)
	private String debitcardapply4;

	@Column(length=18)
	private String debitcardapply42;

	@Column(length=2)
	private String elecbank;

	@Column(length=2)
	private String elecbill;

	@Column(length=64)
	private String elecbillmail;

	@Column(length=2)
	private String faxservice;

	@Column(length=64)
	private String faxservicemail;

	@Column(length=32)
	private String faxserviceno;

	@Column(length=3)
	private String mobilebankflag;

	@Column(length=2)
	private String mobilephonebank;

	@Column(length=2)
	private String networkbank;

	@Column(length=2)
	private String shortmsg;

	@Column(length=2)
	private String telephonebank;

	@Column(length=2)
	private String ukeyapply;

	@Column(length=2)
	private String wxbank;

    public OWzServiceInfo() {
    }

	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getAccchangenot() {
		return this.accchangenot;
	}

	public void setAccchangenot(String accchangenot) {
		this.accchangenot = accchangenot;
	}

	public String getApprovepass() {
		return this.approvepass;
	}

	public void setApprovepass(String approvepass) {
		this.approvepass = approvepass;
	}

	public String getApprovepassloan() {
		return this.approvepassloan;
	}

	public void setApprovepassloan(String approvepassloan) {
		this.approvepassloan = approvepassloan;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getDebitcardapply() {
		return this.debitcardapply;
	}

	public void setDebitcardapply(String debitcardapply) {
		this.debitcardapply = debitcardapply;
	}

	public String getDebitcardapply1() {
		return this.debitcardapply1;
	}

	public void setDebitcardapply1(String debitcardapply1) {
		this.debitcardapply1 = debitcardapply1;
	}

	public String getDebitcardapply2() {
		return this.debitcardapply2;
	}

	public void setDebitcardapply2(String debitcardapply2) {
		this.debitcardapply2 = debitcardapply2;
	}

	public String getDebitcardapply22() {
		return this.debitcardapply22;
	}

	public void setDebitcardapply22(String debitcardapply22) {
		this.debitcardapply22 = debitcardapply22;
	}

	public String getDebitcardapply3() {
		return this.debitcardapply3;
	}

	public void setDebitcardapply3(String debitcardapply3) {
		this.debitcardapply3 = debitcardapply3;
	}

	public String getDebitcardapply4() {
		return this.debitcardapply4;
	}

	public void setDebitcardapply4(String debitcardapply4) {
		this.debitcardapply4 = debitcardapply4;
	}

	public String getDebitcardapply42() {
		return this.debitcardapply42;
	}

	public void setDebitcardapply42(String debitcardapply42) {
		this.debitcardapply42 = debitcardapply42;
	}

	public String getElecbank() {
		return this.elecbank;
	}

	public void setElecbank(String elecbank) {
		this.elecbank = elecbank;
	}

	public String getElecbill() {
		return this.elecbill;
	}

	public void setElecbill(String elecbill) {
		this.elecbill = elecbill;
	}

	public String getElecbillmail() {
		return this.elecbillmail;
	}

	public void setElecbillmail(String elecbillmail) {
		this.elecbillmail = elecbillmail;
	}

	public String getFaxservice() {
		return this.faxservice;
	}

	public void setFaxservice(String faxservice) {
		this.faxservice = faxservice;
	}

	public String getFaxservicemail() {
		return this.faxservicemail;
	}

	public void setFaxservicemail(String faxservicemail) {
		this.faxservicemail = faxservicemail;
	}

	public String getFaxserviceno() {
		return this.faxserviceno;
	}

	public void setFaxserviceno(String faxserviceno) {
		this.faxserviceno = faxserviceno;
	}

	public String getMobilebankflag() {
		return this.mobilebankflag;
	}

	public void setMobilebankflag(String mobilebankflag) {
		this.mobilebankflag = mobilebankflag;
	}

	public String getMobilephonebank() {
		return this.mobilephonebank;
	}

	public void setMobilephonebank(String mobilephonebank) {
		this.mobilephonebank = mobilephonebank;
	}

	public String getNetworkbank() {
		return this.networkbank;
	}

	public void setNetworkbank(String networkbank) {
		this.networkbank = networkbank;
	}

	public String getShortmsg() {
		return this.shortmsg;
	}

	public void setShortmsg(String shortmsg) {
		this.shortmsg = shortmsg;
	}

	public String getTelephonebank() {
		return this.telephonebank;
	}

	public void setTelephonebank(String telephonebank) {
		this.telephonebank = telephonebank;
	}

	public String getUkeyapply() {
		return this.ukeyapply;
	}

	public void setUkeyapply(String ukeyapply) {
		this.ukeyapply = ukeyapply;
	}

	public String getWxbank() {
		return this.wxbank;
	}

	public void setWxbank(String wxbank) {
		this.wxbank = wxbank;
	}

}