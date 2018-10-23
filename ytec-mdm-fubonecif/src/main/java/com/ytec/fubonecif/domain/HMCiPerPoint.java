package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerPoint entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_POINT")
public class HMCiPerPoint implements java.io.Serializable {

	// Fields

	private HMCiPerPointId id;

	// Constructors

	/** default constructor */
	public HMCiPerPoint() {
	}

	/** full constructor */
	public HMCiPerPoint(HMCiPerPointId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "personPointId", column = @Column(name = "PERSON_POINT_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "usablePoint", column = @Column(name = "USABLE_POINT", precision = 10)),
			@AttributeOverride(name = "sumPoint", column = @Column(name = "SUM_POINT", precision = 10)),
			@AttributeOverride(name = "totalPoint", column = @Column(name = "TOTAL_POINT", precision = 10)),
			@AttributeOverride(name = "pointPeriod", column = @Column(name = "POINT_PERIOD", precision = 22, scale = 0)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "profitSum", column = @Column(name = "PROFIT_SUM", precision = 10)),
			@AttributeOverride(name = "basePointSum", column = @Column(name = "BASE_POINT_SUM", precision = 10)),
			@AttributeOverride(name = "awardPointSum", column = @Column(name = "AWARD_POINT_SUM", precision = 10)),
			@AttributeOverride(name = "usedPointSum", column = @Column(name = "USED_POINT_SUM", precision = 10)),
			@AttributeOverride(name = "monthUsedPointSum", column = @Column(name = "MONTH_USED_POINT_SUM", precision = 10)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerPointId getId() {
		return this.id;
	}

	public void setId(HMCiPerPointId id) {
		this.id = id;
	}

}