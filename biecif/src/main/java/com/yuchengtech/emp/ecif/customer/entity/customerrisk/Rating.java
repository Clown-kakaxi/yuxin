package com.yuchengtech.emp.ecif.customer.entity.customerrisk;
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
 * The persistent class for the RATING database table.
 * 
 */
@Entity
@Table(name="RATING")
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RATING_ID", unique=true, nullable=false)
	private Long ratingId;

	@Column(name="CUST_ID")
	private Long custId;

    @Temporal( TemporalType.DATE)
	@Column(name="EFFECTIVE_DATE")
	private Date effectiveDate;

    @Temporal( TemporalType.DATE)
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

    @Temporal( TemporalType.DATE)
	@Column(name="RATING_DATE")
	private Date ratingDate;

	@Column(name="RATING_ORG_CODE", length=20)
	private String ratingOrgCode;

	@Column(name="RATING_ORG_NAME", length=40)
	private String ratingOrgName;

	@Column(name="RATING_RESULT", length=20)
	private String ratingResult;

	@Column(name="RATING_TYPE", length=20)
	private String ratingType;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="VALID_FLAG", length=1)
	private String validFlag;

    public Rating() {
    }

	public Long getRatingId() {
		return this.ratingId;
	}

	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
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

	public String getRatingType() {
		return this.ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
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