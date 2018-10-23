package com.ytec.fubonecif.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * OWzCustomerInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "O_WZ_CUSTOMER_INFO")
public class OWzCustomerInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	//@SequenceGenerator(name="O_WZ_CUSTOMER_INFO_CUSTOMERID_GENERATOR" )
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="O_WZ_CUSTOMER_INFO_CUSTOMERID_GENERATOR")
	@Column(unique=true, nullable=false, length=32)
	private String customerid;
	
	@Column
	private String accountno;

	@Column
	private String addrcoun;

	@Column
	private String addrdtl;

	@Column
	private String addrhouse;

	@Column
	private String aocode;

	@Column
	private String aocodename;

	@Column(name="APP_STATUS")
	private String appStatus;

	@Column
	private String attribute1;

	@Column
	private String attribute10;

	@Column
	private String attribute11;

	@Column
	private String attribute12;

	@Column
	private String attribute13;

	@Column
	private String attribute14;

	@Column
	private String attribute15;

	@Column
	private String attribute2;

	@Column
	private String attribute3;

	@Column
	private String attribute4;

	@Column
	private String attribute5;

	@Column
	private String attribute6;

	@Column
	private String attribute7;

	@Column
	private String attribute8;

	@Column
	private String attribute9;

	@Column
	private String batchid;

	@Column
	private String birthday;

	@Column
	private String birthplace;

	@Column
	private String cardno;

	@Column
	private String cardtype;

	@Column
	private String certdate;

	@Column
	private String certdate2;

	@Column
	private String certdatelong;

	@Column
	private String certid;

	@Column
	private String certid2;

	@Column
	private String certphotoname;

	@Column
	private String certtype;

	@Column
	private String certtype2;

	@Column
	private String channel;

	@Column
	private String companyname;

	@Column
	private String country;

	@Column
	private String customername;

	@Column
	private String custtype;

	@Column
	private String elecbill;

	@Column
	private String elecemail;

	@Column
	private String email;

	@Column
	private String englishname;

	@Column
	private String faxservice;

	@Column
	private String faxserviceno;

	@Column
	private String ftzcity;

	@Column
	private String ftzflag;

	@Column
	private String homecouncode;

	@Column
	private String homephonecity;

	@Column
	private String homephonecoun;

	@Column
	private String homephoneno;

	@Column
	private String homephonetype;

	@Column
	private String inputdate;

	@Column
	private String inputorg;

	@Column
	private String islineno;

	@Column
	private String iswechat;

	@Column
	private String jointaccount;

	@Column
	private String jointaccountid;

	@Column
	private String khstatus;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="\"LINENO\"")
	private String lineno;

	@Column
	private String managerorg;

	@Column
	private String manageruser;

	@Column
	private String mobilecouncode;

	@Column
	private String mobilephonecity;

	@Column
	private String mobilephonecoun;

	@Column
	private String mobilephoneno;

	@Column
	private String officecouncode;

	@Column
	private String officephonecity;

	@Column
	private String officephonecoun;

	@Column
	private String officephoneno;

	@Column
	private String officephonetype;

	@Column
	private String openorg;

	@Column
	private String openorgname;

	@Column
	private String orderdate;

	@Column
	private String orderphone;

	@Column(name="\"POSITION\"")
	private String position;

	@Column
	private String postaddrcoun;

	@Column
	private String postaddrdtl;

	@Column
	private String postaddrdtllike;

	@Column
	private String postzipcode;

	@Column
	private String pretime;

	@Column
	private String relflag;

	@Column
	private String relname;

	@Column
	private String relship;

	@Column
	private String saveflag;

	@Column
	private String seq;

	@Column
	private String sex;

	@Column
	private String sorderserialno;

	@Column
	private String twcertdate;

	@Column
	private String twcertdatelong;

	@Column
	private String twcertid;

	@Column
	private String twcertid2;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column
	private String updatedate;

	@Column
	private String usaflag;

	@Column
	private String usdtl;

	@Column
	private String visitaddress;

	@Column
	private String wechat;

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getAddrcoun() {
		return addrcoun;
	}

	public void setAddrcoun(String addrcoun) {
		this.addrcoun = addrcoun;
	}

	public String getAddrdtl() {
		return addrdtl;
	}

	public void setAddrdtl(String addrdtl) {
		this.addrdtl = addrdtl;
	}

	public String getAddrhouse() {
		return addrhouse;
	}

	public void setAddrhouse(String addrhouse) {
		this.addrhouse = addrhouse;
	}

	public String getAocode() {
		return aocode;
	}

	public void setAocode(String aocode) {
		this.aocode = aocode;
	}

	public String getAocodename() {
		return aocodename;
	}

	public void setAocodename(String aocodename) {
		this.aocodename = aocodename;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	public String getAttribute15() {
		return attribute15;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCertdate() {
		return certdate;
	}

	public void setCertdate(String certdate) {
		this.certdate = certdate;
	}

	public String getCertdate2() {
		return certdate2;
	}

	public void setCertdate2(String certdate2) {
		this.certdate2 = certdate2;
	}

	public String getCertdatelong() {
		return certdatelong;
	}

	public void setCertdatelong(String certdatelong) {
		this.certdatelong = certdatelong;
	}

	public String getCertid() {
		return certid;
	}

	public void setCertid(String certid) {
		this.certid = certid;
	}

	public String getCertid2() {
		return certid2;
	}

	public void setCertid2(String certid2) {
		this.certid2 = certid2;
	}

	public String getCertphotoname() {
		return certphotoname;
	}

	public void setCertphotoname(String certphotoname) {
		this.certphotoname = certphotoname;
	}

	public String getCerttype() {
		return certtype;
	}

	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}

	public String getCerttype2() {
		return certtype2;
	}

	public void setCerttype2(String certtype2) {
		this.certtype2 = certtype2;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getCusttype() {
		return custtype;
	}

	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}

	public String getElecbill() {
		return elecbill;
	}

	public void setElecbill(String elecbill) {
		this.elecbill = elecbill;
	}

	public String getElecemail() {
		return elecemail;
	}

	public void setElecemail(String elecemail) {
		this.elecemail = elecemail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnglishname() {
		return englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public String getFaxservice() {
		return faxservice;
	}

	public void setFaxservice(String faxservice) {
		this.faxservice = faxservice;
	}

	public String getFaxserviceno() {
		return faxserviceno;
	}

	public void setFaxserviceno(String faxserviceno) {
		this.faxserviceno = faxserviceno;
	}

	public String getFtzcity() {
		return ftzcity;
	}

	public void setFtzcity(String ftzcity) {
		this.ftzcity = ftzcity;
	}

	public String getFtzflag() {
		return ftzflag;
	}

	public void setFtzflag(String ftzflag) {
		this.ftzflag = ftzflag;
	}

	public String getHomecouncode() {
		return homecouncode;
	}

	public void setHomecouncode(String homecouncode) {
		this.homecouncode = homecouncode;
	}

	public String getHomephonecity() {
		return homephonecity;
	}

	public void setHomephonecity(String homephonecity) {
		this.homephonecity = homephonecity;
	}

	public String getHomephonecoun() {
		return homephonecoun;
	}

	public void setHomephonecoun(String homephonecoun) {
		this.homephonecoun = homephonecoun;
	}

	public String getHomephoneno() {
		return homephoneno;
	}

	public void setHomephoneno(String homephoneno) {
		this.homephoneno = homephoneno;
	}

	public String getHomephonetype() {
		return homephonetype;
	}

	public void setHomephonetype(String homephonetype) {
		this.homephonetype = homephonetype;
	}

	public String getInputdate() {
		return inputdate;
	}

	public void setInputdate(String inputdate) {
		this.inputdate = inputdate;
	}

	public String getInputorg() {
		return inputorg;
	}

	public void setInputorg(String inputorg) {
		this.inputorg = inputorg;
	}

	public String getIslineno() {
		return islineno;
	}

	public void setIslineno(String islineno) {
		this.islineno = islineno;
	}

	public String getIswechat() {
		return iswechat;
	}

	public void setIswechat(String iswechat) {
		this.iswechat = iswechat;
	}

	public String getJointaccount() {
		return jointaccount;
	}

	public void setJointaccount(String jointaccount) {
		this.jointaccount = jointaccount;
	}

	public String getJointaccountid() {
		return jointaccountid;
	}

	public void setJointaccountid(String jointaccountid) {
		this.jointaccountid = jointaccountid;
	}

	public String getKhstatus() {
		return khstatus;
	}

	public void setKhstatus(String khstatus) {
		this.khstatus = khstatus;
	}

	public String getLastUpdateSys() {
		return lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLineno() {
		return lineno;
	}

	public void setLineno(String lineno) {
		this.lineno = lineno;
	}

	public String getManagerorg() {
		return managerorg;
	}

	public void setManagerorg(String managerorg) {
		this.managerorg = managerorg;
	}

	public String getManageruser() {
		return manageruser;
	}

	public void setManageruser(String manageruser) {
		this.manageruser = manageruser;
	}

	public String getMobilecouncode() {
		return mobilecouncode;
	}

	public void setMobilecouncode(String mobilecouncode) {
		this.mobilecouncode = mobilecouncode;
	}

	public String getMobilephonecity() {
		return mobilephonecity;
	}

	public void setMobilephonecity(String mobilephonecity) {
		this.mobilephonecity = mobilephonecity;
	}

	public String getMobilephonecoun() {
		return mobilephonecoun;
	}

	public void setMobilephonecoun(String mobilephonecoun) {
		this.mobilephonecoun = mobilephonecoun;
	}

	public String getMobilephoneno() {
		return mobilephoneno;
	}

	public void setMobilephoneno(String mobilephoneno) {
		this.mobilephoneno = mobilephoneno;
	}

	public String getOfficecouncode() {
		return officecouncode;
	}

	public void setOfficecouncode(String officecouncode) {
		this.officecouncode = officecouncode;
	}

	public String getOfficephonecity() {
		return officephonecity;
	}

	public void setOfficephonecity(String officephonecity) {
		this.officephonecity = officephonecity;
	}

	public String getOfficephonecoun() {
		return officephonecoun;
	}

	public void setOfficephonecoun(String officephonecoun) {
		this.officephonecoun = officephonecoun;
	}

	public String getOfficephoneno() {
		return officephoneno;
	}

	public void setOfficephoneno(String officephoneno) {
		this.officephoneno = officephoneno;
	}

	public String getOfficephonetype() {
		return officephonetype;
	}

	public void setOfficephonetype(String officephonetype) {
		this.officephonetype = officephonetype;
	}

	public String getOpenorg() {
		return openorg;
	}

	public void setOpenorg(String openorg) {
		this.openorg = openorg;
	}

	public String getOpenorgname() {
		return openorgname;
	}

	public void setOpenorgname(String openorgname) {
		this.openorgname = openorgname;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getOrderphone() {
		return orderphone;
	}

	public void setOrderphone(String orderphone) {
		this.orderphone = orderphone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPostaddrcoun() {
		return postaddrcoun;
	}

	public void setPostaddrcoun(String postaddrcoun) {
		this.postaddrcoun = postaddrcoun;
	}

	public String getPostaddrdtl() {
		return postaddrdtl;
	}

	public void setPostaddrdtl(String postaddrdtl) {
		this.postaddrdtl = postaddrdtl;
	}

	public String getPostaddrdtllike() {
		return postaddrdtllike;
	}

	public void setPostaddrdtllike(String postaddrdtllike) {
		this.postaddrdtllike = postaddrdtllike;
	}

	public String getPostzipcode() {
		return postzipcode;
	}

	public void setPostzipcode(String postzipcode) {
		this.postzipcode = postzipcode;
	}

	public String getPretime() {
		return pretime;
	}

	public void setPretime(String pretime) {
		this.pretime = pretime;
	}

	public String getRelflag() {
		return relflag;
	}

	public void setRelflag(String relflag) {
		this.relflag = relflag;
	}

	public String getRelname() {
		return relname;
	}

	public void setRelname(String relname) {
		this.relname = relname;
	}

	public String getRelship() {
		return relship;
	}

	public void setRelship(String relship) {
		this.relship = relship;
	}

	public String getSaveflag() {
		return saveflag;
	}

	public void setSaveflag(String saveflag) {
		this.saveflag = saveflag;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSorderserialno() {
		return sorderserialno;
	}

	public void setSorderserialno(String sorderserialno) {
		this.sorderserialno = sorderserialno;
	}

	public String getTwcertdate() {
		return twcertdate;
	}

	public void setTwcertdate(String twcertdate) {
		this.twcertdate = twcertdate;
	}

	public String getTwcertdatelong() {
		return twcertdatelong;
	}

	public void setTwcertdatelong(String twcertdatelong) {
		this.twcertdatelong = twcertdatelong;
	}

	public String getTwcertid() {
		return twcertid;
	}

	public void setTwcertid(String twcertid) {
		this.twcertid = twcertid;
	}

	public String getTwcertid2() {
		return twcertid2;
	}

	public void setTwcertid2(String twcertid2) {
		this.twcertid2 = twcertid2;
	}

	public String getTxSeqNo() {
		return txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public String getUsaflag() {
		return usaflag;
	}

	public void setUsaflag(String usaflag) {
		this.usaflag = usaflag;
	}

	public String getUsdtl() {
		return usdtl;
	}

	public void setUsdtl(String usdtl) {
		this.usdtl = usdtl;
	}

	public String getVisitaddress() {
		return visitaddress;
	}

	public void setVisitaddress(String visitaddress) {
		this.visitaddress = visitaddress;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getWorktype() {
		return worktype;
	}

	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column
	private String weixin;

	@Column
	private String worktype;

	@Column
	private String zipcode;}
