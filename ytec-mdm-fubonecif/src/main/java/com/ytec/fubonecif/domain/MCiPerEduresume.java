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
 * MCiPerEduresume entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_EDURESUME")
public class MCiPerEduresume implements java.io.Serializable {

	// Fields

	private String eduresumeId;
	private String custId;
	private Date startDate;
	private Date endDate;
	private String university;
	private String college;
	private String major;
	private String eduSys;
	private String certificateNo;
	private String diplomaNo;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerEduresume() {
	}

	/** minimal constructor */
	public MCiPerEduresume(String eduresumeId) {
		this.eduresumeId = eduresumeId;
	}

	/** full constructor */
	public MCiPerEduresume(String eduresumeId, String custId, Date startDate,
			Date endDate, String university, String college, String major,
			String eduSys, String certificateNo, String diplomaNo,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.eduresumeId = eduresumeId;
		this.custId = custId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.university = university;
		this.college = college;
		this.major = major;
		this.eduSys = eduSys;
		this.certificateNo = certificateNo;
		this.diplomaNo = diplomaNo;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "EDURESUME_ID", unique = true, nullable = false, length = 20)
	public String getEduresumeId() {
		return this.eduresumeId;
	}

	public void setEduresumeId(String eduresumeId) {
		this.eduresumeId = eduresumeId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	@Column(name = "UNIVERSITY", length = 80)
	public String getUniversity() {
		return this.university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	@Column(name = "COLLEGE", length = 80)
	public String getCollege() {
		return this.college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	@Column(name = "MAJOR", length = 80)
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "EDU_SYS", length = 80)
	public String getEduSys() {
		return this.eduSys;
	}

	public void setEduSys(String eduSys) {
		this.eduSys = eduSys;
	}

	@Column(name = "CERTIFICATE_NO", length = 32)
	public String getCertificateNo() {
		return this.certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	@Column(name = "DIPLOMA_NO", length = 32)
	public String getDiplomaNo() {
		return this.diplomaNo;
	}

	public void setDiplomaNo(String diplomaNo) {
		this.diplomaNo = diplomaNo;
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