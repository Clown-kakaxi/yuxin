package com.yuchengtech.bcrm.customer.level.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_RATING database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_RATING")
public class AcrmFCiRating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RATING_ID")
	private String ratingId;

	@Column(name="CUST_ID")
	private String custId;

    @Temporal( TemporalType.DATE)
	@Column(name="EFFECTIVE_DATE")
	private Date effectiveDate;

    @Temporal( TemporalType.DATE)
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

    @Temporal( TemporalType.DATE)
	@Column(name="RATING_DATE")
	private Date ratingDate;

	@Column(name="RATING_ORG_CODE")
	private String ratingOrgCode;

	@Column(name="RATING_ORG_NAME")
	private String ratingOrgName;

	@Column(name="RATING_RESULT")
	private String ratingResult;

	@Column(name="RATING_SCORE")
	private BigDecimal ratingScore;

	@Column(name="RATING_TYPE")
	private String ratingType;

	@Column(name="SCHEME_ID")
	private String schemeId;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="VALID_FLAG")
	private String validFlag;

    public AcrmFCiRating() {
    }

	public String getRatingId() {
		return this.ratingId;
	}

	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
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

	public Date getRatingDate() {
		return this.ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}

	public String getRatingOrgCode() {
		return this.ratingOrgCode;
	}

	public void setRatingOrgCode(String ratingOrgCode) {
		this.ratingOrgCode = ratingOrgCode;
	}

	public String getRatingOrgName() {
		return this.ratingOrgName;
	}

	public void setRatingOrgName(String ratingOrgName) {
		this.ratingOrgName = ratingOrgName;
	}

	public String getRatingResult() {
		return this.ratingResult;
	}

	public void setRatingResult(String ratingResult) {
		this.ratingResult = ratingResult;
	}

	public BigDecimal getRatingScore() {
		return this.ratingScore;
	}

	public void setRatingScore(BigDecimal ratingScore) {
		this.ratingScore = ratingScore;
	}

	public String getRatingType() {
		return this.ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}

	public String getSchemeId() {
		return this.schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

}