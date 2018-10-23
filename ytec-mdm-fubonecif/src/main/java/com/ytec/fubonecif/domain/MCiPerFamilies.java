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
 * MCiPerFamilies entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_FAMILIES")
public class MCiPerFamilies implements java.io.Serializable {

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

	// Constructors

	/** default constructor */
	public MCiPerFamilies() {
	}

	/** minimal constructor */
	public MCiPerFamilies(String mxtid) {
		this.mxtid = mxtid;
	}

	/** full constructor */
	public MCiPerFamilies(String mxtid, String custId, String membername,
			String familyrela, String membercretTyp, String membercretNo,
			String tel, String mobile, String email, Date birthday,
			String company, String memberId, String managerId, String remark,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
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
	}

	// Property accessors
	@Id
	@Column(name = "MXTID", unique = true, nullable = false, length = 20)
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

}