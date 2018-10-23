package com.yuchengtech.emp.ecif.customer.entity.customerevaluate;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the SCOREINFO database table.
 * 
 */
@Entity
@Table(name="SCOREINFO")
public class Scoreinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SCORE_INFO_ID", unique=true, nullable=false)
	private Long scoreInfoId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="EFFECTIVE_DATE", length=20)
	private String effectiveDate;

	@Column(name="EXPIRED_DATE", length=20)
	private String expiredDate;

	@Column(name="SCORE_BRANCH_CODE", length=20)
	private String scoreBranchCode;

	@Column(name="SCORE_BRANCH_NAME", length=80)
	private String scoreBranchName;

	@Column(name="SCORE_DATE", length=20)
	private String scoreDate;

	@Column(name="SCORE_PERIOD", length=20)
	private String scorePeriod;

	@Column(name="SCORE_RESULT", length=20)
	private String scoreResult;

	@Column(name="SCORE_TYPE", length=20)
	private String scoreType;

    public Scoreinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getScoreInfoId() {
		return this.scoreInfoId;
	}

	public void setScoreInfoId(Long scoreInfoId) {
		this.scoreInfoId = scoreInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getScoreBranchCode() {
		return this.scoreBranchCode;
	}

	public void setScoreBranchCode(String scoreBranchCode) {
		this.scoreBranchCode = scoreBranchCode;
	}

	public String getScoreBranchName() {
		return this.scoreBranchName;
	}

	public void setScoreBranchName(String scoreBranchName) {
		this.scoreBranchName = scoreBranchName;
	}

	public String getScoreDate() {
		return this.scoreDate;
	}

	public void setScoreDate(String scoreDate) {
		this.scoreDate = scoreDate;
	}

	public String getScorePeriod() {
		return this.scorePeriod;
	}

	public void setScorePeriod(String scorePeriod) {
		this.scorePeriod = scorePeriod;
	}

	public String getScoreResult() {
		return this.scoreResult;
	}

	public void setScoreResult(String scoreResult) {
		this.scoreResult = scoreResult;
	}

	public String getScoreType() {
		return this.scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

}