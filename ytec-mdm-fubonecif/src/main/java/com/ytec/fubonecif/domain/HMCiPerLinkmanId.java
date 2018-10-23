package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiPerLinkmanId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerLinkmanId implements java.io.Serializable {

	// Fields

	private String linkmanId;
	private String custId;
	private String linkmanType;
	private String linkmanName;
	private String identType;
	private String identNo;
	private String tel;
	private String tel2;
	private String mobile;
	private String mobile2;
	private String email;
	private String address;
	private String gender;
	private Date birthday;
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
	public HMCiPerLinkmanId() {
	}

	/** minimal constructor */
	public HMCiPerLinkmanId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerLinkmanId(String linkmanId, String custId,
			String linkmanType, String linkmanName, String identType,
			String identNo, String tel, String tel2, String mobile,
			String mobile2, String email, String address, String gender,
			Date birthday, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.linkmanId = linkmanId;
		this.custId = custId;
		this.linkmanType = linkmanType;
		this.linkmanName = linkmanName;
		this.identType = identType;
		this.identNo = identNo;
		this.tel = tel;
		this.tel2 = tel2;
		this.mobile = mobile;
		this.mobile2 = mobile2;
		this.email = email;
		this.address = address;
		this.gender = gender;
		this.birthday = birthday;
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

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "TEL2", length = 20)
	public String getTel2() {
		return this.tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
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
		if (!(other instanceof HMCiPerLinkmanId))
			return false;
		HMCiPerLinkmanId castOther = (HMCiPerLinkmanId) other;

		return ((this.getLinkmanId() == castOther.getLinkmanId()) || (this
				.getLinkmanId() != null
				&& castOther.getLinkmanId() != null && this.getLinkmanId()
				.equals(castOther.getLinkmanId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getLinkmanType() == castOther.getLinkmanType()) || (this
						.getLinkmanType() != null
						&& castOther.getLinkmanType() != null && this
						.getLinkmanType().equals(castOther.getLinkmanType())))
				&& ((this.getLinkmanName() == castOther.getLinkmanName()) || (this
						.getLinkmanName() != null
						&& castOther.getLinkmanName() != null && this
						.getLinkmanName().equals(castOther.getLinkmanName())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getTel() == castOther.getTel()) || (this.getTel() != null
						&& castOther.getTel() != null && this.getTel().equals(
						castOther.getTel())))
				&& ((this.getTel2() == castOther.getTel2()) || (this.getTel2() != null
						&& castOther.getTel2() != null && this.getTel2()
						.equals(castOther.getTel2())))
				&& ((this.getMobile() == castOther.getMobile()) || (this
						.getMobile() != null
						&& castOther.getMobile() != null && this.getMobile()
						.equals(castOther.getMobile())))
				&& ((this.getMobile2() == castOther.getMobile2()) || (this
						.getMobile2() != null
						&& castOther.getMobile2() != null && this.getMobile2()
						.equals(castOther.getMobile2())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
				&& ((this.getAddress() == castOther.getAddress()) || (this
						.getAddress() != null
						&& castOther.getAddress() != null && this.getAddress()
						.equals(castOther.getAddress())))
				&& ((this.getGender() == castOther.getGender()) || (this
						.getGender() != null
						&& castOther.getGender() != null && this.getGender()
						.equals(castOther.getGender())))
				&& ((this.getBirthday() == castOther.getBirthday()) || (this
						.getBirthday() != null
						&& castOther.getBirthday() != null && this
						.getBirthday().equals(castOther.getBirthday())))
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
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getLinkmanType() == null ? 0 : this.getLinkmanType()
						.hashCode());
		result = 37
				* result
				+ (getLinkmanName() == null ? 0 : this.getLinkmanName()
						.hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37 * result
				+ (getTel() == null ? 0 : this.getTel().hashCode());
		result = 37 * result
				+ (getTel2() == null ? 0 : this.getTel2().hashCode());
		result = 37 * result
				+ (getMobile() == null ? 0 : this.getMobile().hashCode());
		result = 37 * result
				+ (getMobile2() == null ? 0 : this.getMobile2().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37 * result
				+ (getAddress() == null ? 0 : this.getAddress().hashCode());
		result = 37 * result
				+ (getGender() == null ? 0 : this.getGender().hashCode());
		result = 37 * result
				+ (getBirthday() == null ? 0 : this.getBirthday().hashCode());
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