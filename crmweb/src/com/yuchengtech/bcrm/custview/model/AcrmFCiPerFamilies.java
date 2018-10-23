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
 * The persistent class for the ACRM_F_CI_PER_FAMILIES database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_PER_FAMILIES")
public class AcrmFCiPerFamilies implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_PER_FAMILIES_MXTID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_PER_FAMILIES_MXTID_GENERATOR")
	private String mxtid;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	private String company;

	@Column(name="CUST_ID")
	private String custId;

	private String email;

	private String familyrela;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="MANAGER_ID")
	private String managerId;

	@Column(name="MEMBER_ID")
	private String memberId;

	@Column(name="MEMBERCRET_NO")
	private String membercretNo;

	@Column(name="MEMBERCRET_TYP")
	private String membercretTyp;

	private String membername;

	private String mobile;

	private String remark;

	private String tel;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

    public AcrmFCiPerFamilies() {
    }

	public String getMxtid() {
		return this.mxtid;
	}

	public void setMxtid(String mxtid) {
		this.mxtid = mxtid;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getFamilyrela() {
		return this.familyrela;
	}

	public void setFamilyrela(String familyrela) {
		this.familyrela = familyrela;
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

	public String getManagerId() {
		return this.managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMembercretNo() {
		return this.membercretNo;
	}

	public void setMembercretNo(String membercretNo) {
		this.membercretNo = membercretNo;
	}

	public String getMembercretTyp() {
		return this.membercretTyp;
	}

	public void setMembercretTyp(String membercretTyp) {
		this.membercretTyp = membercretTyp;
	}

	public String getMembername() {
		return this.membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}