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
 * MCiPerRelativeinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_RELATIVEINFO")
public class MCiPerRelativeinfo implements java.io.Serializable {

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

	// Constructors

	/** default constructor */
	public MCiPerRelativeinfo() {
	}

	/** minimal constructor */
	public MCiPerRelativeinfo(String relativeId) {
		this.relativeId = relativeId;
	}

	/** full constructor */
	public MCiPerRelativeinfo(String relativeId, String custId,
			String relativeType, String relativeName, String identType,
			String identNo, String gender, Date birthday, String health,
			String monthIncomeScope, Double monthIncome, String officeTel,
			String homeTel, String mobile, String email, String address,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
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
	}

	// Property accessors
	@Id
	@Column(name = "RELATIVE_ID", unique = true, nullable = false, length = 20)
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

}