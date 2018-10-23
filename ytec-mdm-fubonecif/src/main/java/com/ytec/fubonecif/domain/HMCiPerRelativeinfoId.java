package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiPerRelativeinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerRelativeinfoId implements java.io.Serializable {

	// Fields

	private String relativeId;
	private String custId;
	private String relativeType;
	private String relativeName;
	private String identType;
	private String identNo;
	private String gender;
	private Date birthday;
	private String health;
	private String monthIncomeScope;
	private Double monthIncome;
	private String officeTel;
	private String homeTel;
	private String mobile;
	private String email;
	private String address;
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
	public HMCiPerRelativeinfoId() {
	}

	/** minimal constructor */
	public HMCiPerRelativeinfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerRelativeinfoId(String relativeId, String custId,
			String relativeType, String relativeName, String identType,
			String identNo, String gender, Date birthday, String health,
			String monthIncomeScope, Double monthIncome, String officeTel,
			String homeTel, String mobile, String email, String address,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.relativeId = relativeId;
		this.custId = custId;
		this.relativeType = relativeType;
		this.relativeName = relativeName;
		this.identType = identType;
		this.identNo = identNo;
		this.gender = gender;
		this.birthday = birthday;
		this.health = health;
		this.monthIncomeScope = monthIncomeScope;
		this.monthIncome = monthIncome;
		this.officeTel = officeTel;
		this.homeTel = homeTel;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
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

	@Column(name = "RELATIVE_ID", length = 20)
	public String getRelativeId() {
		return this.relativeId;
	}

	public void setRelativeId(String relativeId) {
		this.relativeId = relativeId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "RELATIVE_TYPE", length = 20)
	public String getRelativeType() {
		return this.relativeType;
	}

	public void setRelativeType(String relativeType) {
		this.relativeType = relativeType;
	}

	@Column(name = "RELATIVE_NAME", length = 80)
	public String getRelativeName() {
		return this.relativeName;
	}

	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
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

	@Column(name = "HEALTH", length = 80)
	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	@Column(name = "MONTH_INCOME_SCOPE", length = 20)
	public String getMonthIncomeScope() {
		return this.monthIncomeScope;
	}

	public void setMonthIncomeScope(String monthIncomeScope) {
		this.monthIncomeScope = monthIncomeScope;
	}

	@Column(name = "MONTH_INCOME", precision = 17)
	public Double getMonthIncome() {
		return this.monthIncome;
	}

	public void setMonthIncome(Double monthIncome) {
		this.monthIncome = monthIncome;
	}

	@Column(name = "OFFICE_TEL", length = 20)
	public String getOfficeTel() {
		return this.officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	@Column(name = "HOME_TEL", length = 20)
	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	@Column(name = "MOBILE", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
		if (!(other instanceof HMCiPerRelativeinfoId))
			return false;
		HMCiPerRelativeinfoId castOther = (HMCiPerRelativeinfoId) other;

		return ((this.getRelativeId() == castOther.getRelativeId()) || (this
				.getRelativeId() != null
				&& castOther.getRelativeId() != null && this.getRelativeId()
				.equals(castOther.getRelativeId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getRelativeType() == castOther.getRelativeType()) || (this
						.getRelativeType() != null
						&& castOther.getRelativeType() != null && this
						.getRelativeType().equals(castOther.getRelativeType())))
				&& ((this.getRelativeName() == castOther.getRelativeName()) || (this
						.getRelativeName() != null
						&& castOther.getRelativeName() != null && this
						.getRelativeName().equals(castOther.getRelativeName())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getGender() == castOther.getGender()) || (this
						.getGender() != null
						&& castOther.getGender() != null && this.getGender()
						.equals(castOther.getGender())))
				&& ((this.getBirthday() == castOther.getBirthday()) || (this
						.getBirthday() != null
						&& castOther.getBirthday() != null && this
						.getBirthday().equals(castOther.getBirthday())))
				&& ((this.getHealth() == castOther.getHealth()) || (this
						.getHealth() != null
						&& castOther.getHealth() != null && this.getHealth()
						.equals(castOther.getHealth())))
				&& ((this.getMonthIncomeScope() == castOther
						.getMonthIncomeScope()) || (this.getMonthIncomeScope() != null
						&& castOther.getMonthIncomeScope() != null && this
						.getMonthIncomeScope().equals(
								castOther.getMonthIncomeScope())))
				&& ((this.getMonthIncome() == castOther.getMonthIncome()) || (this
						.getMonthIncome() != null
						&& castOther.getMonthIncome() != null && this
						.getMonthIncome().equals(castOther.getMonthIncome())))
				&& ((this.getOfficeTel() == castOther.getOfficeTel()) || (this
						.getOfficeTel() != null
						&& castOther.getOfficeTel() != null && this
						.getOfficeTel().equals(castOther.getOfficeTel())))
				&& ((this.getHomeTel() == castOther.getHomeTel()) || (this
						.getHomeTel() != null
						&& castOther.getHomeTel() != null && this.getHomeTel()
						.equals(castOther.getHomeTel())))
				&& ((this.getMobile() == castOther.getMobile()) || (this
						.getMobile() != null
						&& castOther.getMobile() != null && this.getMobile()
						.equals(castOther.getMobile())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
				&& ((this.getAddress() == castOther.getAddress()) || (this
						.getAddress() != null
						&& castOther.getAddress() != null && this.getAddress()
						.equals(castOther.getAddress())))
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

		result = 37
				* result
				+ (getRelativeId() == null ? 0 : this.getRelativeId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getRelativeType() == null ? 0 : this.getRelativeType()
						.hashCode());
		result = 37
				* result
				+ (getRelativeName() == null ? 0 : this.getRelativeName()
						.hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37 * result
				+ (getGender() == null ? 0 : this.getGender().hashCode());
		result = 37 * result
				+ (getBirthday() == null ? 0 : this.getBirthday().hashCode());
		result = 37 * result
				+ (getHealth() == null ? 0 : this.getHealth().hashCode());
		result = 37
				* result
				+ (getMonthIncomeScope() == null ? 0 : this
						.getMonthIncomeScope().hashCode());
		result = 37
				* result
				+ (getMonthIncome() == null ? 0 : this.getMonthIncome()
						.hashCode());
		result = 37 * result
				+ (getOfficeTel() == null ? 0 : this.getOfficeTel().hashCode());
		result = 37 * result
				+ (getHomeTel() == null ? 0 : this.getHomeTel().hashCode());
		result = 37 * result
				+ (getMobile() == null ? 0 : this.getMobile().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37 * result
				+ (getAddress() == null ? 0 : this.getAddress().hashCode());
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