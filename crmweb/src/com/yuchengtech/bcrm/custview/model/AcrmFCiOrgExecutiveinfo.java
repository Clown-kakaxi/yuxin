package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_ORG_EXECUTIVEINFO database table.
 * 机构干系人
 */
@Entity
@Table(name="ACRM_F_CI_ORG_EXECUTIVEINFO")
public class AcrmFCiOrgExecutiveinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_ORG_EXECUTIVEINFO_LINKMAN_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_ORG_EXECUTIVEINFO_LINKMAN_ID_GENERATOR")
	@Column(name="LINKMAN_ID")
	private String linkmanId;

	private String address;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	private String citizenship;

	private String email;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	private String fex;

	private String gender;

	@Column(name="HIGHEST_SCHOOLING")
	private String highestSchooling;

	@Column(name="HOME_TEL")
	private String homeTel;

	@Column(name="HOME_TEL2")
	private String homeTel2;

    @Temporal( TemporalType.DATE)
	@Column(name="IDENT_EXPIRED_DATE")
	private Date identExpiredDate;

	@Column(name="IDENT_IS_VERIFY")
	private String identIsVerify;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_REG_ADDR")
	private String identRegAddr;

	@Column(name="IDENT_REG_ADDR_POST")
	private String identRegAddrPost;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="INDIV_CUS_ID")
	private String indivCusId;

	@Column(name="IS_THIS_BANK_CUST")
	private String isThisBankCust;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LINKMAN_EN_NAME")
	private String linkmanEnName;

	@Column(name="LINKMAN_NAME")
	private String linkmanName;

	@Column(name="LINKMAN_TITLE")
	private String linkmanTitle;

	@Column(name="LINKMAN_TYPE")
	private String linkmanType;

	private String marriage;

	private String mobile;

	private String mobile2;

	private String nationality;

	private String nativeplace;

	@Column(name="OFFICE_TEL")
	private String officeTel;

	@Column(name="OFFICE_TEL2")
	private String officeTel2;

	@Column(name="ORG_CUST_ID")
	private String orgCustId;

	@Column(name="POLITICAL_FACE")
	private String politicalFace;

	private String remark;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="WORK_DEPT")
	private String workDept;

	@Column(name="WORK_POSITION")
	private String workPosition;

	@Column(name="ZIP_CODE")
	private String zipCode;

    public AcrmFCiOrgExecutiveinfo() {
    }

	public String getLinkmanId() {
		return this.linkmanId;
	}

	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFex() {
		return this.fex;
	}

	public void setFex(String fex) {
		this.fex = fex;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHighestSchooling() {
		return this.highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}

	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getHomeTel2() {
		return this.homeTel2;
	}

	public void setHomeTel2(String homeTel2) {
		this.homeTel2 = homeTel2;
	}

	public Date getIdentExpiredDate() {
		return this.identExpiredDate;
	}

	public void setIdentExpiredDate(Date identExpiredDate) {
		this.identExpiredDate = identExpiredDate;
	}

	public String getIdentIsVerify() {
		return this.identIsVerify;
	}

	public void setIdentIsVerify(String identIsVerify) {
		this.identIsVerify = identIsVerify;
	}

	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentRegAddr() {
		return this.identRegAddr;
	}

	public void setIdentRegAddr(String identRegAddr) {
		this.identRegAddr = identRegAddr;
	}

	public String getIdentRegAddrPost() {
		return this.identRegAddrPost;
	}

	public void setIdentRegAddrPost(String identRegAddrPost) {
		this.identRegAddrPost = identRegAddrPost;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIndivCusId() {
		return this.indivCusId;
	}

	public void setIndivCusId(String indivCusId) {
		this.indivCusId = indivCusId;
	}

	public String getIsThisBankCust() {
		return this.isThisBankCust;
	}

	public void setIsThisBankCust(String isThisBankCust) {
		this.isThisBankCust = isThisBankCust;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLinkmanEnName() {
		return this.linkmanEnName;
	}

	public void setLinkmanEnName(String linkmanEnName) {
		this.linkmanEnName = linkmanEnName;
	}

	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanTitle() {
		return this.linkmanTitle;
	}

	public void setLinkmanTitle(String linkmanTitle) {
		this.linkmanTitle = linkmanTitle;
	}

	public String getLinkmanType() {
		return this.linkmanType;
	}

	public void setLinkmanType(String linkmanType) {
		this.linkmanType = linkmanType;
	}

	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile2() {
		return this.mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNativeplace() {
		return this.nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public String getOfficeTel() {
		return this.officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getOfficeTel2() {
		return this.officeTel2;
	}

	public void setOfficeTel2(String officeTel2) {
		this.officeTel2 = officeTel2;
	}

	public String getOrgCustId() {
		return this.orgCustId;
	}

	public void setOrgCustId(String orgCustId) {
		this.orgCustId = orgCustId;
	}

	public String getPoliticalFace() {
		return this.politicalFace;
	}

	public void setPoliticalFace(String politicalFace) {
		this.politicalFace = politicalFace;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getWorkDept() {
		return this.workDept;
	}

	public void setWorkDept(String workDept) {
		this.workDept = workDept;
	}

	public String getWorkPosition() {
		return this.workPosition;
	}

	public void setWorkPosition(String workPosition) {
		this.workPosition = workPosition;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}