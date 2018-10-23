package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerBusiinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_BUSIINFO")
public class HMCiPerBusiinfo implements java.io.Serializable {

	// Fields

	private HMCiPerBusiinfoId id;

	// Constructors

	/** default constructor */
	public HMCiPerBusiinfo() {
	}

	/** full constructor */
	public HMCiPerBusiinfo(HMCiPerBusiinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "personBusiInfoId", column = @Column(name = "PERSON_BUSI_INFO_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "busiItemName", column = @Column(name = "BUSI_ITEM_NAME", length = 80)),
			@AttributeOverride(name = "busiItemDesc", column = @Column(name = "BUSI_ITEM_DESC")),
			@AttributeOverride(name = "busiItemType", column = @Column(name = "BUSI_ITEM_TYPE", length = 20)),
			@AttributeOverride(name = "busiItemAddr", column = @Column(name = "BUSI_ITEM_ADDR", length = 200)),
			@AttributeOverride(name = "isCorpOwnership", column = @Column(name = "IS_CORP_OWNERSHIP", length = 1)),
			@AttributeOverride(name = "partnerNum", column = @Column(name = "PARTNER_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "stockPercent", column = @Column(name = "STOCK_PERCENT", precision = 10, scale = 4)),
			@AttributeOverride(name = "assetsAmt", column = @Column(name = "ASSETS_AMT", precision = 17)),
			@AttributeOverride(name = "debtAmt", column = @Column(name = "DEBT_AMT", precision = 17)),
			@AttributeOverride(name = "inAmt", column = @Column(name = "IN_AMT", precision = 17)),
			@AttributeOverride(name = "outAmt", column = @Column(name = "OUT_AMT", precision = 17)),
			@AttributeOverride(name = "yearSaleAmount", column = @Column(name = "YEAR_SALE_AMOUNT", precision = 17)),
			@AttributeOverride(name = "yearSaleCcy", column = @Column(name = "YEAR_SALE_CCY", length = 20)),
			@AttributeOverride(name = "yearSaleIncome", column = @Column(name = "YEAR_SALE_INCOME", precision = 17)),
			@AttributeOverride(name = "yearPay", column = @Column(name = "YEAR_PAY", precision = 17)),
			@AttributeOverride(name = "yearProfit", column = @Column(name = "YEAR_PROFIT", precision = 17)),
			@AttributeOverride(name = "monthSaleIncome", column = @Column(name = "MONTH_SALE_INCOME", precision = 17)),
			@AttributeOverride(name = "monthPay", column = @Column(name = "MONTH_PAY", precision = 17)),
			@AttributeOverride(name = "monthProfit", column = @Column(name = "MONTH_PROFIT", precision = 17)),
			@AttributeOverride(name = "lastYearSaleAmt", column = @Column(name = "LAST_YEAR_SALE_AMT", precision = 17)),
			@AttributeOverride(name = "lastMonthSaleAmt", column = @Column(name = "LAST_MONTH_SALE_AMT", precision = 17)),
			@AttributeOverride(name = "dayMaxSaleAmt", column = @Column(name = "DAY_MAX_SALE_AMT", precision = 17)),
			@AttributeOverride(name = "dayMinSaleAmt", column = @Column(name = "DAY_MIN_SALE_AMT", precision = 17)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerBusiinfoId getId() {
		return this.id;
	}

	public void setId(HMCiPerBusiinfoId id) {
		this.id = id;
	}

}