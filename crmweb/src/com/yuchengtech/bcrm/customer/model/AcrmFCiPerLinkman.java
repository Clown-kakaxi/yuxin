package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_PER_LINKMAN database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_PER_LINKMAN")
public class AcrmFCiPerLinkman implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LINKMAN_ID")
	private String linkmanId;

	private String address;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	@Column(name="CUST_ID")
	private String custId;

	private String email;

	private String gender;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LINKMAN_NAME")
	private String linkmanName;

	@Column(name="LINKMAN_TYPE")
	private String linkmanType;

	private String mobile;

	private String mobile2;

	private String tel;

	private String tel2;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

    public AcrmFCiPerLinkman() {
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

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
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

	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanType() {
		return this.linkmanType;
	}

	public void setLinkmanType(String linkmanType) {
		this.linkmanType = linkmanType;
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

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTel2() {
		return this.tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}