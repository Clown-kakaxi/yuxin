package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiPerFamiliesId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerFamiliesId implements java.io.Serializable {

	// Fields

	private String mxtid;
	private String custId;
	private String membername;
	private String familyrela;
	private String membercretTyp;
	private String membercretNo;
	private String tel;
	private String mobile;
	private String email;
	private Date birthday;
	private String company;
	private String memberId;
	private String managerId;
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
	public HMCiPerFamiliesId() {
	}

	/** minimal constructor */
	public HMCiPerFamiliesId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerFamiliesId(String mxtid, String custId, String membername,
			String familyrela, String membercretTyp, String membercretNo,
			String tel, String mobile, String email, Date birthday,
			String company, String memberId, String managerId, String remark,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.mxtid = mxtid;
		this.custId = custId;
		this.membername = membername;
		this.familyrela = familyrela;
		this.membercretTyp = membercretTyp;
		this.membercretNo = membercretNo;
		this.tel = tel;
		this.mobile = mobile;
		this.email = email;
		this.birthday = birthday;
		this.company = company;
		this.memberId = memberId;
		this.managerId = managerId;
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

	@Column(name = "MXTID", length = 20)
	public String getMxtid() {
		return this.mxtid;
	}

	public void setMxtid(String mxtid) {
		this.mxtid = mxtid;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "MEMBERNAME", length = 40)
	public String getMembername() {
		return this.membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	@Column(name = "FAMILYRELA", length = 50)
	public String getFamilyrela() {
		return this.familyrela;
	}

	public void setFamilyrela(String familyrela) {
		this.familyrela = familyrela;
	}

	@Column(name = "MEMBERCRET_TYP", length = 20)
	public String getMembercretTyp() {
		return this.membercretTyp;
	}

	public void setMembercretTyp(String membercretTyp) {
		this.membercretTyp = membercretTyp;
	}

	@Column(name = "MEMBERCRET_NO", length = 100)
	public String getMembercretNo() {
		return this.membercretNo;
	}

	public void setMembercretNo(String membercretNo) {
		this.membercretNo = membercretNo;
	}

	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "MOBILE", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "COMPANY", length = 50)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "MEMBER_ID", length = 40)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "MANAGER_ID", length = 40)
	public String getManagerId() {
		return this.managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
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
		if (!(other instanceof HMCiPerFamiliesId))
			return false;
		HMCiPerFamiliesId castOther = (HMCiPerFamiliesId) other;

		return ((this.getMxtid() == castOther.getMxtid()) || (this.getMxtid() != null
				&& castOther.getMxtid() != null && this.getMxtid().equals(
				castOther.getMxtid())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getMembername() == castOther.getMembername()) || (this
						.getMembername() != null
						&& castOther.getMembername() != null && this
						.getMembername().equals(castOther.getMembername())))
				&& ((this.getFamilyrela() == castOther.getFamilyrela()) || (this
						.getFamilyrela() != null
						&& castOther.getFamilyrela() != null && this
						.getFamilyrela().equals(castOther.getFamilyrela())))
				&& ((this.getMembercretTyp() == castOther.getMembercretTyp()) || (this
						.getMembercretTyp() != null
						&& castOther.getMembercretTyp() != null && this
						.getMembercretTyp()
						.equals(castOther.getMembercretTyp())))
				&& ((this.getMembercretNo() == castOther.getMembercretNo()) || (this
						.getMembercretNo() != null
						&& castOther.getMembercretNo() != null && this
						.getMembercretNo().equals(castOther.getMembercretNo())))
				&& ((this.getTel() == castOther.getTel()) || (this.getTel() != null
						&& castOther.getTel() != null && this.getTel().equals(
						castOther.getTel())))
				&& ((this.getMobile() == castOther.getMobile()) || (this
						.getMobile() != null
						&& castOther.getMobile() != null && this.getMobile()
						.equals(castOther.getMobile())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
				&& ((this.getBirthday() == castOther.getBirthday()) || (this
						.getBirthday() != null
						&& castOther.getBirthday() != null && this
						.getBirthday().equals(castOther.getBirthday())))
				&& ((this.getCompany() == castOther.getCompany()) || (this
						.getCompany() != null
						&& castOther.getCompany() != null && this.getCompany()
						.equals(castOther.getCompany())))
				&& ((this.getMemberId() == castOther.getMemberId()) || (this
						.getMemberId() != null
						&& castOther.getMemberId() != null && this
						.getMemberId().equals(castOther.getMemberId())))
				&& ((this.getManagerId() == castOther.getManagerId()) || (this
						.getManagerId() != null
						&& castOther.getManagerId() != null && this
						.getManagerId().equals(castOther.getManagerId())))
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
				+ (getMxtid() == null ? 0 : this.getMxtid().hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getMembername() == null ? 0 : this.getMembername()
						.hashCode());
		result = 37
				* result
				+ (getFamilyrela() == null ? 0 : this.getFamilyrela()
						.hashCode());
		result = 37
				* result
				+ (getMembercretTyp() == null ? 0 : this.getMembercretTyp()
						.hashCode());
		result = 37
				* result
				+ (getMembercretNo() == null ? 0 : this.getMembercretNo()
						.hashCode());
		result = 37 * result
				+ (getTel() == null ? 0 : this.getTel().hashCode());
		result = 37 * result
				+ (getMobile() == null ? 0 : this.getMobile().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37 * result
				+ (getBirthday() == null ? 0 : this.getBirthday().hashCode());
		result = 37 * result
				+ (getCompany() == null ? 0 : this.getCompany().hashCode());
		result = 37 * result
				+ (getMemberId() == null ? 0 : this.getMemberId().hashCode());
		result = 37 * result
				+ (getManagerId() == null ? 0 : this.getManagerId().hashCode());
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