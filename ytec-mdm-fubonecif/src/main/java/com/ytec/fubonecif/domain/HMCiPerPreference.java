package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerPreference entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_PREFERENCE")
public class HMCiPerPreference implements java.io.Serializable {

	// Fields

	private HMCiPerPreferenceId id;

	// Constructors

	/** default constructor */
	public HMCiPerPreference() {
	}

	/** full constructor */
	public HMCiPerPreference(HMCiPerPreferenceId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "langPrefer", column = @Column(name = "LANG_PREFER", length = 20)),
			@AttributeOverride(name = "titlePrefer", column = @Column(name = "TITLE_PREFER", length = 20)),
			@AttributeOverride(name = "contactType", column = @Column(name = "CONTACT_TYPE", length = 20)),
			@AttributeOverride(name = "contactFreqPrefer", column = @Column(name = "CONTACT_FREQ_PREFER", length = 20)),
			@AttributeOverride(name = "contactTimePrefer", column = @Column(name = "CONTACT_TIME_PREFER", length = 20)),
			@AttributeOverride(name = "giftPrefer", column = @Column(name = "GIFT_PREFER", length = 20)),
			@AttributeOverride(name = "vehiclePrefer", column = @Column(name = "VEHICLE_PREFER", length = 20)),
			@AttributeOverride(name = "consumHabit", column = @Column(name = "CONSUM_HABIT", length = 20)),
			@AttributeOverride(name = "insurancePrefer", column = @Column(name = "INSURANCE_PREFER", length = 20)),
			@AttributeOverride(name = "investExpr", column = @Column(name = "INVEST_EXPR", length = 20)),
			@AttributeOverride(name = "riskPrefer", column = @Column(name = "RISK_PREFER", length = 20)),
			@AttributeOverride(name = "investPosition", column = @Column(name = "INVEST_POSITION", length = 20)),
			@AttributeOverride(name = "investCycle", column = @Column(name = "INVEST_CYCLE", length = 20)),
			@AttributeOverride(name = "financeBusinessPrefer", column = @Column(name = "FINANCE_BUSINESS_PREFER", length = 20)),
			@AttributeOverride(name = "interestInvestment", column = @Column(name = "INTEREST_INVESTMENT", length = 500)),
			@AttributeOverride(name = "investStyle", column = @Column(name = "INVEST_STYLE", length = 20)),
			@AttributeOverride(name = "investTarget", column = @Column(name = "INVEST_TARGET", length = 20)),
			@AttributeOverride(name = "investChannel", column = @Column(name = "INVEST_CHANNEL", length = 20)),
			@AttributeOverride(name = "postDataFlag", column = @Column(name = "POST_DATA_FLAG", length = 1)),
			@AttributeOverride(name = "joinCampFlag", column = @Column(name = "JOIN_CAMP_FLAG", length = 1)),
			@AttributeOverride(name = "receiveSmsFlag", column = @Column(name = "RECEIVE_SMS_FLAG", length = 1)),
			@AttributeOverride(name = "welcomeText", column = @Column(name = "WELCOME_TEXT", length = 100)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerPreferenceId getId() {
		return this.id;
	}

	public void setId(HMCiPerPreferenceId id) {
		this.id = id;
	}

}