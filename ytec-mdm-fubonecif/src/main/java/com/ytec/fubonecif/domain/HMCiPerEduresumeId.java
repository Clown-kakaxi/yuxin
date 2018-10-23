package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiPerEduresumeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerEduresumeId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiPerEduresumeId() {
	}

	/** minimal constructor */
	public HMCiPerEduresumeId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerEduresumeId(String eduresumeId, String custId,
			Date startDate, Date endDate, String university, String college,
			String major, String eduSys, String certificateNo,
			String diplomaNo, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "EDURESUME_ID", length = 20)
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
		if (!(other instanceof HMCiPerEduresumeId))
			return false;
		HMCiPerEduresumeId castOther = (HMCiPerEduresumeId) other;

		return ((this.getEduresumeId() == castOther.getEduresumeId()) || (this
				.getEduresumeId() != null
				&& castOther.getEduresumeId() != null && this.getEduresumeId()
				.equals(castOther.getEduresumeId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this
						.getStartDate() != null
						&& castOther.getStartDate() != null && this
						.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this
						.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate()
						.equals(castOther.getEndDate())))
				&& ((this.getUniversity() == castOther.getUniversity()) || (this
						.getUniversity() != null
						&& castOther.getUniversity() != null && this
						.getUniversity().equals(castOther.getUniversity())))
				&& ((this.getCollege() == castOther.getCollege()) || (this
						.getCollege() != null
						&& castOther.getCollege() != null && this.getCollege()
						.equals(castOther.getCollege())))
				&& ((this.getMajor() == castOther.getMajor()) || (this
						.getMajor() != null
						&& castOther.getMajor() != null && this.getMajor()
						.equals(castOther.getMajor())))
				&& ((this.getEduSys() == castOther.getEduSys()) || (this
						.getEduSys() != null
						&& castOther.getEduSys() != null && this.getEduSys()
						.equals(castOther.getEduSys())))
				&& ((this.getCertificateNo() == castOther.getCertificateNo()) || (this
						.getCertificateNo() != null
						&& castOther.getCertificateNo() != null && this
						.getCertificateNo()
						.equals(castOther.getCertificateNo())))
				&& ((this.getDiplomaNo() == castOther.getDiplomaNo()) || (this
						.getDiplomaNo() != null
						&& castOther.getDiplomaNo() != null && this
						.getDiplomaNo().equals(castOther.getDiplomaNo())))
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
				+ (getEduresumeId() == null ? 0 : this.getEduresumeId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37 * result
				+ (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37 * result
				+ (getEndDate() == null ? 0 : this.getEndDate().hashCode());
		result = 37
				* result
				+ (getUniversity() == null ? 0 : this.getUniversity()
						.hashCode());
		result = 37 * result
				+ (getCollege() == null ? 0 : this.getCollege().hashCode());
		result = 37 * result
				+ (getMajor() == null ? 0 : this.getMajor().hashCode());
		result = 37 * result
				+ (getEduSys() == null ? 0 : this.getEduSys().hashCode());
		result = 37
				* result
				+ (getCertificateNo() == null ? 0 : this.getCertificateNo()
						.hashCode());
		result = 37 * result
				+ (getDiplomaNo() == null ? 0 : this.getDiplomaNo().hashCode());
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