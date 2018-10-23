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
 * MCiRating entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_RATING")
public class MCiRating implements java.io.Serializable {

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

	// Constructors

	/** default constructor */
	public MCiRating() {
	}

	/** minimal constructor */
	public MCiRating(String ratingId) {
		this.ratingId = ratingId;
	}

	/** full constructor */
	public MCiRating(String ratingId, String custId, String ratingOrgCode,
			String ratingOrgName, String ratingType, String ratingResult,
			Date ratingDate, String validFlag, Date effectiveDate,
			Date expiredDate, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
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
	}

	// Property accessors
	@Id
	@Column(name = "RATING_ID", unique = true, nullable = false, length = 20)
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

}