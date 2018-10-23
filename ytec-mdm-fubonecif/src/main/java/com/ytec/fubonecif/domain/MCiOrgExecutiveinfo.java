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
 * MCiOrgExecutiveinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_EXECUTIVEINFO")
public class MCiOrgExecutiveinfo implements java.io.Serializable {

	// Fields

	private String linkmanId;
	private String orgCustId;
	private String linkmanType;
	private String linkmanName;
	private String linkmanEnName;
	private String linkmanTitle;
	private String isThisBankCust;
	private String indivCusId;
	private String identType;
	private String identNo;
	private String identRegAddr;
	private String identRegAddrPost;
	private Date identExpiredDate;
	private String identIsVerify;
	private String citizenship;
	private String nationality;
	private String nativeplace;
	private String gender;
	private Date birthday;
	private String highestSchooling;
	private String marriage;
	private String politicalFace;
	private String officeTel;
	private String officeTel2;
	private String homeTel;
	private String homeTel2;
	private String mobile;
	private String mobile2;
	private String fex;
	private String email;
	private String address;
	private String zipCode;
	private String workDept;
	private String workPosition;
	private Date startDate;
	private Date endDate;
	private String remark;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;
	//add by liuming 20170629
	private Date artificialPerson;

	// Constructors

	/** default constructor */
	public MCiOrgExecutiveinfo() {
	}

	/** minimal constructor */
	public MCiOrgExecutiveinfo(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	/** full constructor */
	public MCiOrgExecutiveinfo(String linkmanId, String orgCustId,
			String linkmanType, String linkmanName, String linkmanEnName,
			String linkmanTitle, String isThisBankCust, String indivCusId,
			String identType, String identNo, String identRegAddr,
			String identRegAddrPost, Date identExpiredDate,
			String identIsVerify, String citizenship, String nationality,
			String nativeplace, String gender, Date birthday,
			String highestSchooling, String marriage, String politicalFace,
			String officeTel, String officeTel2, String homeTel,
			String homeTel2, String mobile, String mobile2, String fex,
			String email, String address, String zipCode, String workDept,
			String workPosition, Date startDate, Date endDate, String remark,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo
			//add by liuming 20170629
			,Date artificialPerson) {
		this.linkmanId = linkmanId;
		this.orgCustId = orgCustId;
		this.linkmanType = linkmanType;
		this.linkmanName = linkmanName;
		this.linkmanEnName = linkmanEnName;
		this.linkmanTitle = linkmanTitle;
		this.isThisBankCust = isThisBankCust;
		this.indivCusId = indivCusId;
		this.identType = identType;
		this.identNo = identNo;
		this.identRegAddr = identRegAddr;
		this.identRegAddrPost = identRegAddrPost;
		this.identExpiredDate = identExpiredDate;
		this.identIsVerify = identIsVerify;
		this.citizenship = citizenship;
		this.nationality = nationality;
		this.nativeplace = nativeplace;
		this.gender = gender;
		this.birthday = birthday;
		this.highestSchooling = highestSchooling;
		this.marriage = marriage;
		this.politicalFace = politicalFace;
		this.officeTel = officeTel;
		this.officeTel2 = officeTel2;
		this.homeTel = homeTel;
		this.homeTel2 = homeTel2;
		this.mobile = mobile;
		this.mobile2 = mobile2;
		this.fex = fex;
		this.email = email;
		this.address = address;
		this.zipCode = zipCode;
		this.workDept = workDept;
		this.workPosition = workPosition;
		this.startDate = startDate;
		this.endDate = endDate;
		this.remark = remark;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
		//add by liuming 20170629
		this.artificialPerson = artificialPerson;
	}

	// Property accessors
	@Id
	@Column(name = "LINKMAN_ID", unique = true, nullable = false, length = 20)
	public String getLinkmanId() {
		return this.linkmanId;
	}

	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	@Column(name = "ORG_CUST_ID", length = 20)
	public String getOrgCustId() {
		return this.orgCustId;
	}

	public void setOrgCustId(String orgCustId) {
		this.orgCustId = orgCustId;
	}

	@Column(name = "LINKMAN_TYPE", length = 20)
	public String getLinkmanType() {
		return this.linkmanType;
	}

	public void setLinkmanType(String linkmanType) {
		this.linkmanType = linkmanType;
	}

	@Column(name = "LINKMAN_NAME", length = 80)
	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	@Column(name = "LINKMAN_EN_NAME", length = 70)
	public String getLinkmanEnName() {
		return this.linkmanEnName;
	}

	public void setLinkmanEnName(String linkmanEnName) {
		this.linkmanEnName = linkmanEnName;
	}

	@Column(name = "LINKMAN_TITLE", length = 20)
	public String getLinkmanTitle() {
		return this.linkmanTitle;
	}

	public void setLinkmanTitle(String linkmanTitle) {
		this.linkmanTitle = linkmanTitle;
	}

	@Column(name = "IS_THIS_BANK_CUST", length = 1)
	public String getIsThisBankCust() {
		return this.isThisBankCust;
	}

	public void setIsThisBankCust(String isThisBankCust) {
		this.isThisBankCust = isThisBankCust;
	}

	@Column(name = "INDIV_CUS_ID", length = 20)
	public String getIndivCusId() {
		return this.indivCusId;
	}

	public void setIndivCusId(String indivCusId) {
		this.indivCusId = indivCusId;
	}

	@Column(name = "IDENT_TYPE", length = 20)
	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	@Column(name = "IDENT_NO", length = 40)
	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	@Column(name = "IDENT_REG_ADDR", length = 200)
	public String getIdentRegAddr() {
		return this.identRegAddr;
	}

	public void setIdentRegAddr(String identRegAddr) {
		this.identRegAddr = identRegAddr;
	}

	@Column(name = "IDENT_REG_ADDR_POST", length = 20)
	public String getIdentRegAddrPost() {
		return this.identRegAddrPost;
	}

	public void setIdentRegAddrPost(String identRegAddrPost) {
		this.identRegAddrPost = identRegAddrPost;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "IDENT_EXPIRED_DATE", length = 7)
	public Date getIdentExpiredDate() {
		return this.identExpiredDate;
	}

	public void setIdentExpiredDate(Date identExpiredDate) {
		this.identExpiredDate = identExpiredDate;
	}

	@Column(name = "IDENT_IS_VERIFY", length = 1)
	public String getIdentIsVerify() {
		return this.identIsVerify;
	}

	public void setIdentIsVerify(String identIsVerify) {
		this.identIsVerify = identIsVerify;
	}

	@Column(name = "CITIZENSHIP", length = 20)
	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	@Column(name = "NATIONALITY", length = 20)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "NATIVEPLACE", length = 20)
	public String getNativeplace() {
		return this.nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	@Column(name = "GENDER", length = 20)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "HIGHEST_SCHOOLING", length = 20)
	public String getHighestSchooling() {
		return this.highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}

	@Column(name = "MARRIAGE", length = 20)
	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	@Column(name = "POLITICAL_FACE", length = 20)
	public String getPoliticalFace() {
		return this.politicalFace;
	}

	public void setPoliticalFace(String politicalFace) {
		this.politicalFace = politicalFace;
	}

	@Column(name = "OFFICE_TEL", length = 20)
	public String getOfficeTel() {
		return this.officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	@Column(name = "OFFICE_TEL2", length = 20)
	public String getOfficeTel2() {
		return this.officeTel2;
	}

	public void setOfficeTel2(String officeTel2) {
		this.officeTel2 = officeTel2;
	}

	@Column(name = "HOME_TEL", length = 20)
	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	@Column(name = "HOME_TEL2", length = 20)
	public String getHomeTel2() {
		return this.homeTel2;
	}

	public void setHomeTel2(String homeTel2) {
		this.homeTel2 = homeTel2;
	}

	@Column(name = "MOBILE", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "MOBILE2", length = 20)
	public String getMobile2() {
		return this.mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	@Column(name = "FEX", length = 20)
	public String getFex() {
		return this.fex;
	}

	public void setFex(String fex) {
		this.fex = fex;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ZIP_CODE", length = 20)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "WORK_DEPT", length = 60)
	public String getWorkDept() {
		return this.workDept;
	}

	public void setWorkDept(String workDept) {
		this.workDept = workDept;
	}

	@Column(name = "WORK_POSITION", length = 20)
	public String getWorkPosition() {
		return this.workPosition;
	}

	public void setWorkPosition(String workPosition) {
		this.workPosition = workPosition;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ARTIFICIAL_PERSON", length = 7)
	public Date getArtificialPerson() {
		return artificialPerson;
	}

	public void setArtificialPerson(Date artificialPerson) {
		this.artificialPerson = artificialPerson;
	}

}