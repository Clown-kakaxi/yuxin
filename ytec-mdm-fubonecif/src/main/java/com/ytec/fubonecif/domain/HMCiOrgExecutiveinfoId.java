package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiOrgExecutiveinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgExecutiveinfoId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiOrgExecutiveinfoId() {
	}

	/** minimal constructor */
	public HMCiOrgExecutiveinfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgExecutiveinfoId(String linkmanId, String orgCustId,
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
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "LINKMAN_ID", length = 20)
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
		if (!(other instanceof HMCiOrgExecutiveinfoId))
			return false;
		HMCiOrgExecutiveinfoId castOther = (HMCiOrgExecutiveinfoId) other;

		return ((this.getLinkmanId() == castOther.getLinkmanId()) || (this
				.getLinkmanId() != null
				&& castOther.getLinkmanId() != null && this.getLinkmanId()
				.equals(castOther.getLinkmanId())))
				&& ((this.getOrgCustId() == castOther.getOrgCustId()) || (this
						.getOrgCustId() != null
						&& castOther.getOrgCustId() != null && this
						.getOrgCustId().equals(castOther.getOrgCustId())))
				&& ((this.getLinkmanType() == castOther.getLinkmanType()) || (this
						.getLinkmanType() != null
						&& castOther.getLinkmanType() != null && this
						.getLinkmanType().equals(castOther.getLinkmanType())))
				&& ((this.getLinkmanName() == castOther.getLinkmanName()) || (this
						.getLinkmanName() != null
						&& castOther.getLinkmanName() != null && this
						.getLinkmanName().equals(castOther.getLinkmanName())))
				&& ((this.getLinkmanEnName() == castOther.getLinkmanEnName()) || (this
						.getLinkmanEnName() != null
						&& castOther.getLinkmanEnName() != null && this
						.getLinkmanEnName()
						.equals(castOther.getLinkmanEnName())))
				&& ((this.getLinkmanTitle() == castOther.getLinkmanTitle()) || (this
						.getLinkmanTitle() != null
						&& castOther.getLinkmanTitle() != null && this
						.getLinkmanTitle().equals(castOther.getLinkmanTitle())))
				&& ((this.getIsThisBankCust() == castOther.getIsThisBankCust()) || (this
						.getIsThisBankCust() != null
						&& castOther.getIsThisBankCust() != null && this
						.getIsThisBankCust().equals(
								castOther.getIsThisBankCust())))
				&& ((this.getIndivCusId() == castOther.getIndivCusId()) || (this
						.getIndivCusId() != null
						&& castOther.getIndivCusId() != null && this
						.getIndivCusId().equals(castOther.getIndivCusId())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getIdentRegAddr() == castOther.getIdentRegAddr()) || (this
						.getIdentRegAddr() != null
						&& castOther.getIdentRegAddr() != null && this
						.getIdentRegAddr().equals(castOther.getIdentRegAddr())))
				&& ((this.getIdentRegAddrPost() == castOther
						.getIdentRegAddrPost()) || (this.getIdentRegAddrPost() != null
						&& castOther.getIdentRegAddrPost() != null && this
						.getIdentRegAddrPost().equals(
								castOther.getIdentRegAddrPost())))
				&& ((this.getIdentExpiredDate() == castOther
						.getIdentExpiredDate()) || (this.getIdentExpiredDate() != null
						&& castOther.getIdentExpiredDate() != null && this
						.getIdentExpiredDate().equals(
								castOther.getIdentExpiredDate())))
				&& ((this.getIdentIsVerify() == castOther.getIdentIsVerify()) || (this
						.getIdentIsVerify() != null
						&& castOther.getIdentIsVerify() != null && this
						.getIdentIsVerify()
						.equals(castOther.getIdentIsVerify())))
				&& ((this.getCitizenship() == castOther.getCitizenship()) || (this
						.getCitizenship() != null
						&& castOther.getCitizenship() != null && this
						.getCitizenship().equals(castOther.getCitizenship())))
				&& ((this.getNationality() == castOther.getNationality()) || (this
						.getNationality() != null
						&& castOther.getNationality() != null && this
						.getNationality().equals(castOther.getNationality())))
				&& ((this.getNativeplace() == castOther.getNativeplace()) || (this
						.getNativeplace() != null
						&& castOther.getNativeplace() != null && this
						.getNativeplace().equals(castOther.getNativeplace())))
				&& ((this.getGender() == castOther.getGender()) || (this
						.getGender() != null
						&& castOther.getGender() != null && this.getGender()
						.equals(castOther.getGender())))
				&& ((this.getBirthday() == castOther.getBirthday()) || (this
						.getBirthday() != null
						&& castOther.getBirthday() != null && this
						.getBirthday().equals(castOther.getBirthday())))
				&& ((this.getHighestSchooling() == castOther
						.getHighestSchooling()) || (this.getHighestSchooling() != null
						&& castOther.getHighestSchooling() != null && this
						.getHighestSchooling().equals(
								castOther.getHighestSchooling())))
				&& ((this.getMarriage() == castOther.getMarriage()) || (this
						.getMarriage() != null
						&& castOther.getMarriage() != null && this
						.getMarriage().equals(castOther.getMarriage())))
				&& ((this.getPoliticalFace() == castOther.getPoliticalFace()) || (this
						.getPoliticalFace() != null
						&& castOther.getPoliticalFace() != null && this
						.getPoliticalFace()
						.equals(castOther.getPoliticalFace())))
				&& ((this.getOfficeTel() == castOther.getOfficeTel()) || (this
						.getOfficeTel() != null
						&& castOther.getOfficeTel() != null && this
						.getOfficeTel().equals(castOther.getOfficeTel())))
				&& ((this.getOfficeTel2() == castOther.getOfficeTel2()) || (this
						.getOfficeTel2() != null
						&& castOther.getOfficeTel2() != null && this
						.getOfficeTel2().equals(castOther.getOfficeTel2())))
				&& ((this.getHomeTel() == castOther.getHomeTel()) || (this
						.getHomeTel() != null
						&& castOther.getHomeTel() != null && this.getHomeTel()
						.equals(castOther.getHomeTel())))
				&& ((this.getHomeTel2() == castOther.getHomeTel2()) || (this
						.getHomeTel2() != null
						&& castOther.getHomeTel2() != null && this
						.getHomeTel2().equals(castOther.getHomeTel2())))
				&& ((this.getMobile() == castOther.getMobile()) || (this
						.getMobile() != null
						&& castOther.getMobile() != null && this.getMobile()
						.equals(castOther.getMobile())))
				&& ((this.getMobile2() == castOther.getMobile2()) || (this
						.getMobile2() != null
						&& castOther.getMobile2() != null && this.getMobile2()
						.equals(castOther.getMobile2())))
				&& ((this.getFex() == castOther.getFex()) || (this.getFex() != null
						&& castOther.getFex() != null && this.getFex().equals(
						castOther.getFex())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
				&& ((this.getAddress() == castOther.getAddress()) || (this
						.getAddress() != null
						&& castOther.getAddress() != null && this.getAddress()
						.equals(castOther.getAddress())))
				&& ((this.getZipCode() == castOther.getZipCode()) || (this
						.getZipCode() != null
						&& castOther.getZipCode() != null && this.getZipCode()
						.equals(castOther.getZipCode())))
				&& ((this.getWorkDept() == castOther.getWorkDept()) || (this
						.getWorkDept() != null
						&& castOther.getWorkDept() != null && this
						.getWorkDept().equals(castOther.getWorkDept())))
				&& ((this.getWorkPosition() == castOther.getWorkPosition()) || (this
						.getWorkPosition() != null
						&& castOther.getWorkPosition() != null && this
						.getWorkPosition().equals(castOther.getWorkPosition())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this
						.getStartDate() != null
						&& castOther.getStartDate() != null && this
						.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this
						.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate()
						.equals(castOther.getEndDate())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())))
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
				+ (getLinkmanId() == null ? 0 : this.getLinkmanId().hashCode());
		result = 37 * result
				+ (getOrgCustId() == null ? 0 : this.getOrgCustId().hashCode());
		result = 37
				* result
				+ (getLinkmanType() == null ? 0 : this.getLinkmanType()
						.hashCode());
		result = 37
				* result
				+ (getLinkmanName() == null ? 0 : this.getLinkmanName()
						.hashCode());
		result = 37
				* result
				+ (getLinkmanEnName() == null ? 0 : this.getLinkmanEnName()
						.hashCode());
		result = 37
				* result
				+ (getLinkmanTitle() == null ? 0 : this.getLinkmanTitle()
						.hashCode());
		result = 37
				* result
				+ (getIsThisBankCust() == null ? 0 : this.getIsThisBankCust()
						.hashCode());
		result = 37
				* result
				+ (getIndivCusId() == null ? 0 : this.getIndivCusId()
						.hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37
				* result
				+ (getIdentRegAddr() == null ? 0 : this.getIdentRegAddr()
						.hashCode());
		result = 37
				* result
				+ (getIdentRegAddrPost() == null ? 0 : this
						.getIdentRegAddrPost().hashCode());
		result = 37
				* result
				+ (getIdentExpiredDate() == null ? 0 : this
						.getIdentExpiredDate().hashCode());
		result = 37
				* result
				+ (getIdentIsVerify() == null ? 0 : this.getIdentIsVerify()
						.hashCode());
		result = 37
				* result
				+ (getCitizenship() == null ? 0 : this.getCitizenship()
						.hashCode());
		result = 37
				* result
				+ (getNationality() == null ? 0 : this.getNationality()
						.hashCode());
		result = 37
				* result
				+ (getNativeplace() == null ? 0 : this.getNativeplace()
						.hashCode());
		result = 37 * result
				+ (getGender() == null ? 0 : this.getGender().hashCode());
		result = 37 * result
				+ (getBirthday() == null ? 0 : this.getBirthday().hashCode());
		result = 37
				* result
				+ (getHighestSchooling() == null ? 0 : this
						.getHighestSchooling().hashCode());
		result = 37 * result
				+ (getMarriage() == null ? 0 : this.getMarriage().hashCode());
		result = 37
				* result
				+ (getPoliticalFace() == null ? 0 : this.getPoliticalFace()
						.hashCode());
		result = 37 * result
				+ (getOfficeTel() == null ? 0 : this.getOfficeTel().hashCode());
		result = 37
				* result
				+ (getOfficeTel2() == null ? 0 : this.getOfficeTel2()
						.hashCode());
		result = 37 * result
				+ (getHomeTel() == null ? 0 : this.getHomeTel().hashCode());
		result = 37 * result
				+ (getHomeTel2() == null ? 0 : this.getHomeTel2().hashCode());
		result = 37 * result
				+ (getMobile() == null ? 0 : this.getMobile().hashCode());
		result = 37 * result
				+ (getMobile2() == null ? 0 : this.getMobile2().hashCode());
		result = 37 * result
				+ (getFex() == null ? 0 : this.getFex().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37 * result
				+ (getAddress() == null ? 0 : this.getAddress().hashCode());
		result = 37 * result
				+ (getZipCode() == null ? 0 : this.getZipCode().hashCode());
		result = 37 * result
				+ (getWorkDept() == null ? 0 : this.getWorkDept().hashCode());
		result = 37
				* result
				+ (getWorkPosition() == null ? 0 : this.getWorkPosition()
						.hashCode());
		result = 37 * result
				+ (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37 * result
				+ (getEndDate() == null ? 0 : this.getEndDate().hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
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