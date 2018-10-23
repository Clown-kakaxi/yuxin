package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the BUGOLDCONT database table.
 * 
 */
@Entity
@Table(name="BUGOLDCONT")
public class Bugoldcont implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="ACCTNO",length=32)
	private String acctno;

	@Column(name="ADDR",length=128)
	private String addr;

	@Column(name="BASPDATE",length=20)
	private String baspdate;

	@Column(name="BASPSTANNO",length=10)
	private String baspstanno;

	@Column(name="BASPTIME",length=6)
	private String basptime;

	@Column(name="BNKINSTID",length=12)
	private String bnkinstid;

	@Column(name="BNKUSERID",length=12)
	private String bnkuserid;

	@Column(name="CCBASPDATE",length=20)
	private String ccbaspdate;

	@Column(name="CCBASPSTANNO",length=10)
	private String ccbaspstanno;

	@Column(name="CCBASPTIME",length=6)
	private String ccbasptime;

	@Column(name="CCBNKINSTID",length=12)
	private String ccbnkinstid;

	@Column(name="CCBNKUSERID",length=12)
	private String ccbnkuserid;

	@Column(name="CCY",length=20)
	private String ccy;

	@Column(name="CENTERCODE",length=8)
	private String centercode;

	@Column(name="CODE1",length=10)
	private String code1;

	@Column(name="CODE2",length=10)
	private String code2;

	@Column(name="CODE3",length=10)
	private String code3;

	@Column(name="CODE4",length=10)
	private String code4;

	@Column(name="CONTSTAT",length=8)
	private String contstat;

	@Column(name="CUSTNAME",length=80)
	private String custname;

	@Column(name="CUSTTYPE",length=1)
	private String custtype;

	@Column(name="EMAIL",length=40)
	private String email;

	@Column(name="FAXNO",length=16)
	private String faxno;

	@Column(name="GOLDACCTNO",length=22)
	private String goldacctno;

	@Column(name="GOLDACCTPWD",length=16)
	private String goldacctpwd;

	@Column(name="IDNO",length=18)
	private String idno;

	@Column(name="IDTYPE",length=2)
	private String idtype;

	@Column(name="KINDNAME",length=70)
	private String kindname;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="MEMO",length=40)
	private String memo;

	@Column(name="MOBPHONE",length=16)
	private String mobphone;

	@Column(name="OPNACCTBRC",length=12)
	private String opnacctbrc;

	@Column(name="RES1",length=32)
	private String res1;

	@Column(name="RES2",length=80)
	private String res2;

	@Column(name="RES3",length=80)
	private String res3;

	@Column(name="SRVTEL",length=16)
	private String srvtel;

	@Column(name="TEL",length=16)
	private String tel;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="WEBSITE",length=40)
	private String website;

	@Column(name="ZIPCODE",length=6)
	private String zipcode;

    public Bugoldcont() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getAcctno() {
		return this.acctno;
	}

	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getBaspdate() {
		return this.baspdate;
	}

	public void setBaspdate(String baspdate) {
		this.baspdate = baspdate;
	}

	public String getBaspstanno() {
		return this.baspstanno;
	}

	public void setBaspstanno(String baspstanno) {
		this.baspstanno = baspstanno;
	}

	public String getBasptime() {
		return this.basptime;
	}

	public void setBasptime(String basptime) {
		this.basptime = basptime;
	}

	public String getBnkinstid() {
		return this.bnkinstid;
	}

	public void setBnkinstid(String bnkinstid) {
		this.bnkinstid = bnkinstid;
	}

	public String getBnkuserid() {
		return this.bnkuserid;
	}

	public void setBnkuserid(String bnkuserid) {
		this.bnkuserid = bnkuserid;
	}

	public String getCcbaspdate() {
		return this.ccbaspdate;
	}

	public void setCcbaspdate(String ccbaspdate) {
		this.ccbaspdate = ccbaspdate;
	}

	public String getCcbaspstanno() {
		return this.ccbaspstanno;
	}

	public void setCcbaspstanno(String ccbaspstanno) {
		this.ccbaspstanno = ccbaspstanno;
	}

	public String getCcbasptime() {
		return this.ccbasptime;
	}

	public void setCcbasptime(String ccbasptime) {
		this.ccbasptime = ccbasptime;
	}

	public String getCcbnkinstid() {
		return this.ccbnkinstid;
	}

	public void setCcbnkinstid(String ccbnkinstid) {
		this.ccbnkinstid = ccbnkinstid;
	}

	public String getCcbnkuserid() {
		return this.ccbnkuserid;
	}

	public void setCcbnkuserid(String ccbnkuserid) {
		this.ccbnkuserid = ccbnkuserid;
	}

	public String getCcy() {
		return this.ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getCentercode() {
		return this.centercode;
	}

	public void setCentercode(String centercode) {
		this.centercode = centercode;
	}

	public String getCode1() {
		return this.code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	public String getCode2() {
		return this.code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public String getCode3() {
		return this.code3;
	}

	public void setCode3(String code3) {
		this.code3 = code3;
	}

	public String getCode4() {
		return this.code4;
	}

	public void setCode4(String code4) {
		this.code4 = code4;
	}

	public String getContstat() {
		return this.contstat;
	}

	public void setContstat(String contstat) {
		this.contstat = contstat;
	}

	public String getCustname() {
		return this.custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getCusttype() {
		return this.custtype;
	}

	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFaxno() {
		return this.faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getGoldacctno() {
		return this.goldacctno;
	}

	public void setGoldacctno(String goldacctno) {
		this.goldacctno = goldacctno;
	}

	public String getGoldacctpwd() {
		return this.goldacctpwd;
	}

	public void setGoldacctpwd(String goldacctpwd) {
		this.goldacctpwd = goldacctpwd;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getIdtype() {
		return this.idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getKindname() {
		return this.kindname;
	}

	public void setKindname(String kindname) {
		this.kindname = kindname;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMobphone() {
		return this.mobphone;
	}

	public void setMobphone(String mobphone) {
		this.mobphone = mobphone;
	}

	public String getOpnacctbrc() {
		return this.opnacctbrc;
	}

	public void setOpnacctbrc(String opnacctbrc) {
		this.opnacctbrc = opnacctbrc;
	}

	public String getRes1() {
		return this.res1;
	}

	public void setRes1(String res1) {
		this.res1 = res1;
	}

	public String getRes2() {
		return this.res2;
	}

	public void setRes2(String res2) {
		this.res2 = res2;
	}

	public String getRes3() {
		return this.res3;
	}

	public void setRes3(String res3) {
		this.res3 = res3;
	}

	public String getSrvtel() {
		return this.srvtel;
	}

	public void setSrvtel(String srvtel) {
		this.srvtel = srvtel;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}