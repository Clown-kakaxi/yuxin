package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiGradeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiGradeId implements java.io.Serializable {

	// Fields

	private String custGradeId;
	private String custId;
	private String orgCode;
	private String orgName;
	private String custGradeType;
	private String custGrade;
	private Date evaluateDate;
	private Date effectiveDate;
	private Date expiredDate;
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
	public HMCiGradeId() {
	}

	/** minimal constructor */
	public HMCiGradeId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiGradeId(String custGradeId, String custId, String orgCode,
			String orgName, String custGradeType, String custGrade,
			Date evaluateDate, Date effectiveDate, Date expiredDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.custGradeId = custGradeId;
		this.custId = custId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.custGradeType = custGradeType;
		this.custGrade = custGrade;
		this.evaluateDate = evaluateDate;
		this.effectiveDate = effectiveDate;
		this.expiredDate = expiredDate;
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

	@Column(name = "CUST_GRADE_ID", length = 20)
	public String getCustGradeId() {
		return this.custGradeId;
	}

	public void setCustGradeId(String custGradeId) {
		this.custGradeId = custGradeId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "ORG_CODE", length = 20)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "ORG_NAME", length = 20)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "CUST_GRADE_TYPE", length = 20)
	public String getCustGradeType() {
		return this.custGradeType;
	}

	public void setCustGradeType(String custGradeType) {
		this.custGradeType = custGradeType;
	}

	@Column(name = "CUST_GRADE", length = 20)
	public String getCustGrade() {
		return this.custGrade;
	}

	public void setCustGrade(String custGrade) {
		this.custGrade = custGrade;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EVALUATE_DATE", length = 7)
	public Date getEvaluateDate() {
		return this.evaluateDate;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_DATE", length = 7)
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRED_DATE", length = 7)
	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
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
		if (!(other instanceof HMCiGradeId))
			return false;
		HMCiGradeId castOther = (HMCiGradeId) other;

		return ((this.getCustGradeId() == castOther.getCustGradeId()) || (this
				.getCustGradeId() != null
				&& castOther.getCustGradeId() != null && this.getCustGradeId()
				.equals(castOther.getCustGradeId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getOrgCode() == castOther.getOrgCode()) || (this
						.getOrgCode() != null
						&& castOther.getOrgCode() != null && this.getOrgCode()
						.equals(castOther.getOrgCode())))
				&& ((this.getOrgName() == castOther.getOrgName()) || (this
						.getOrgName() != null
						&& castOther.getOrgName() != null && this.getOrgName()
						.equals(castOther.getOrgName())))
				&& ((this.getCustGradeType() == castOther.getCustGradeType()) || (this
						.getCustGradeType() != null
						&& castOther.getCustGradeType() != null && this
						.getCustGradeType()
						.equals(castOther.getCustGradeType())))
				&& ((this.getCustGrade() == castOther.getCustGrade()) || (this
						.getCustGrade() != null
						&& castOther.getCustGrade() != null && this
						.getCustGrade().equals(castOther.getCustGrade())))
				&& ((this.getEvaluateDate() == castOther.getEvaluateDate()) || (this
						.getEvaluateDate() != null
						&& castOther.getEvaluateDate() != null && this
						.getEvaluateDate().equals(castOther.getEvaluateDate())))
				&& ((this.getEffectiveDate() == castOther.getEffectiveDate()) || (this
						.getEffectiveDate() != null
						&& castOther.getEffectiveDate() != null && this
						.getEffectiveDate()
						.equals(castOther.getEffectiveDate())))
				&& ((this.getExpiredDate() == castOther.getExpiredDate()) || (this
						.getExpiredDate() != null
						&& castOther.getExpiredDate() != null && this
						.getExpiredDate().equals(castOther.getExpiredDate())))
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
				+ (getCustGradeId() == null ? 0 : this.getCustGradeId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37 * result
				+ (getOrgCode() == null ? 0 : this.getOrgCode().hashCode());
		result = 37 * result
				+ (getOrgName() == null ? 0 : this.getOrgName().hashCode());
		result = 37
				* result
				+ (getCustGradeType() == null ? 0 : this.getCustGradeType()
						.hashCode());
		result = 37 * result
				+ (getCustGrade() == null ? 0 : this.getCustGrade().hashCode());
		result = 37
				* result
				+ (getEvaluateDate() == null ? 0 : this.getEvaluateDate()
						.hashCode());
		result = 37
				* result
				+ (getEffectiveDate() == null ? 0 : this.getEffectiveDate()
						.hashCode());
		result = 37
				* result
				+ (getExpiredDate() == null ? 0 : this.getExpiredDate()
						.hashCode());
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