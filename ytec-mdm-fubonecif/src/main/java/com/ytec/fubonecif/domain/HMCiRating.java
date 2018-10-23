package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiRating entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_RATING")
public class HMCiRating implements java.io.Serializable {

	// Fields

	private HMCiRatingId id;

	// Constructors

	/** default constructor */
	public HMCiRating() {
	}

	/** full constructor */
	public HMCiRating(HMCiRatingId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "ratingId", column = @Column(name = "RATING_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "ratingOrgCode", column = @Column(name = "RATING_ORG_CODE", length = 20)),
			@AttributeOverride(name = "ratingOrgName", column = @Column(name = "RATING_ORG_NAME", length = 40)),
			@AttributeOverride(name = "ratingType", column = @Column(name = "RATING_TYPE", length = 20)),
			@AttributeOverride(name = "ratingResult", column = @Column(name = "RATING_RESULT", length = 20)),
			@AttributeOverride(name = "ratingDate", column = @Column(name = "RATING_DATE", length = 7)),
			@AttributeOverride(name = "validFlag", column = @Column(name = "VALID_FLAG", length = 1)),
			@AttributeOverride(name = "effectiveDate", column = @Column(name = "EFFECTIVE_DATE", length = 7)),
			@AttributeOverride(name = "expiredDate", column = @Column(name = "EXPIRED_DATE", length = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiRatingId getId() {
		return this.id;
	}

	public void setId(HMCiRatingId id) {
		this.id = id;
	}

}