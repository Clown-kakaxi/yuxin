package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiRatingId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiRatingId implements java.io.Serializable {

	// Fields

	private String ratingId;
	private String custId;
	private String ratingOrgCode;
	private String ratingOrgName;
	private String ratingType;
	private String ratingResult;
	private Date ratingDate;
	private String validFlag;
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
	public HMCiRatingId() {
	}

	/** minimal constructor */
	public HMCiRatingId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiRatingId(String ratingId, String custId, String ratingOrgCode,
			String ratingOrgName, String ratingType, String ratingResult,
			Date ratingDate, String validFlag, Date effectiveDate,
			Date expiredDate, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.ratingId = ratingId;
		this.custId = custId;
		this.ratingOrgCode = ratingOrgCode;
		this.ratingOrgName = ratingOrgName;
		this.ratingType = ratingType;
		this.ratingResult = ratingResult;
		this.ratingDate = ratingDate;
		this.validFlag = validFlag;
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

	@Column(name = "RATING_ID", length = 20)
	public String getRatingId() {
		return this.ratingId;
	}

	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "RATING_ORG_CODE", length = 20)
	public String getRatingOrgCode() {
		return this.ratingOrgCode;
	}

	public void setRatingOrgCode(String ratingOrgCode) {
		this.ratingOrgCode = ratingOrgCode;
	}

	@Column(name = "RATING_ORG_NAME", length = 40)
	public String getRatingOrgName() {
		return this.ratingOrgName;
	}

	public void setRatingOrgName(String ratingOrgName) {
		this.ratingOrgName = ratingOrgName;
	}

	@Column(name = "RATING_TYPE", length = 20)
	public String getRatingType() {
		return this.ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}

	@Column(name = "RATING_RESULT", length = 20)
	public String getRatingResult() {
		return this.ratingResult;
	}

	public void setRatingResult(String ratingResult) {
		this.ratingResult = ratingResult;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RATING_DATE", length = 7)
	public Date getRatingDate() {
		return this.ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}

	@Column(name = "VALID_FLAG", length = 1)
	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
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
		if (!(other instanceof HMCiRatingId))
			return false;
		HMCiRatingId castOther = (HMCiRatingId) other;

		return ((this.getRatingId() == castOther.getRatingId()) || (this
				.getRatingId() != null
				&& castOther.getRatingId() != null && this.getRatingId()
				.equals(castOther.getRatingId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getRatingOrgCode() == castOther.getRatingOrgCode()) || (this
						.getRatingOrgCode() != null
						&& castOther.getRatingOrgCode() != null && this
						.getRatingOrgCode()
						.equals(castOther.getRatingOrgCode())))
				&& ((this.getRatingOrgName() == castOther.getRatingOrgName()) || (this
						.getRatingOrgName() != null
						&& castOther.getRatingOrgName() != null && this
						.getRatingOrgName()
						.equals(castOther.getRatingOrgName())))
				&& ((this.getRatingType() == castOther.getRatingType()) || (this
						.getRatingType() != null
						&& castOther.getRatingType() != null && this
						.getRatingType().equals(castOther.getRatingType())))
				&& ((this.getRatingResult() == castOther.getRatingResult()) || (this
						.getRatingResult() != null
						&& castOther.getRatingResult() != null && this
						.getRatingResult().equals(castOther.getRatingResult())))
				&& ((this.getRatingDate() == castOther.getRatingDate()) || (this
						.getRatingDate() != null
						&& castOther.getRatingDate() != null && this
						.getRatingDate().equals(castOther.getRatingDate())))
				&& ((this.getValidFlag() == castOther.getValidFlag()) || (this
						.getValidFlag() != null
						&& castOther.getValidFlag() != null && this
						.getValidFlag().equals(castOther.getValidFlag())))
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

		result = 37 * result
				+ (getRatingId() == null ? 0 : this.getRatingId().hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getRatingOrgCode() == null ? 0 : this.getRatingOrgCode()
						.hashCode());
		result = 37
				* result
				+ (getRatingOrgName() == null ? 0 : this.getRatingOrgName()
						.hashCode());
		result = 37
				* result
				+ (getRatingType() == null ? 0 : this.getRatingType()
						.hashCode());
		result = 37
				* result
				+ (getRatingResult() == null ? 0 : this.getRatingResult()
						.hashCode());
		result = 37
				* result
				+ (getRatingDate() == null ? 0 : this.getRatingDate()
						.hashCode());
		result = 37 * result
				+ (getValidFlag() == null ? 0 : this.getValidFlag().hashCode());
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