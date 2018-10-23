package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgIssuestock entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_ISSUESTOCK")
public class HMCiOrgIssuestock implements java.io.Serializable {

	// Fields

	private HMCiOrgIssuestockId id;

	// Constructors

	/** default constructor */
	public HMCiOrgIssuestock() {
	}

	/** full constructor */
	public HMCiOrgIssuestock(HMCiOrgIssuestockId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "issueStockId", column = @Column(name = "ISSUE_STOCK_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "exchangeCountryCode", column = @Column(name = "EXCHANGE_COUNTRY_CODE", length = 20)),
			@AttributeOverride(name = "exchangeCode", column = @Column(name = "EXCHANGE_CODE", length = 20)),
			@AttributeOverride(name = "exchangeName", column = @Column(name = "EXCHANGE_NAME", length = 80)),
			@AttributeOverride(name = "marketPlace", column = @Column(name = "MARKET_PLACE", length = 20)),
			@AttributeOverride(name = "ipoDate", column = @Column(name = "IPO_DATE", length = 7)),
			@AttributeOverride(name = "stockType", column = @Column(name = "STOCK_TYPE", length = 20)),
			@AttributeOverride(name = "stocktKind", column = @Column(name = "STOCKT_KIND", length = 20)),
			@AttributeOverride(name = "stockCode", column = @Column(name = "STOCK_CODE", length = 32)),
			@AttributeOverride(name = "stockName", column = @Column(name = "STOCK_NAME", length = 80)),
			@AttributeOverride(name = "stockState", column = @Column(name = "STOCK_STATE", length = 20)),
			@AttributeOverride(name = "shareholderNum", column = @Column(name = "SHAREHOLDER_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "flowStockNum", column = @Column(name = "FLOW_STOCK_NUM", scale = 0)),
			@AttributeOverride(name = "currStockNum", column = @Column(name = "CURR_STOCK_NUM", scale = 0)),
			@AttributeOverride(name = "issueStockNum", column = @Column(name = "ISSUE_STOCK_NUM", scale = 0)),
			@AttributeOverride(name = "issueStockPrice", column = @Column(name = "ISSUE_STOCK_PRICE", precision = 12)),
			@AttributeOverride(name = "netassetPerShare", column = @Column(name = "NETASSET_PER_SHARE", precision = 17)),
			@AttributeOverride(name = "oncf", column = @Column(name = "ONCF", precision = 17)),
			@AttributeOverride(name = "allotmentShareAmt", column = @Column(name = "ALLOTMENT_SHARE_AMT", precision = 17)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgIssuestockId getId() {
		return this.id;
	}

	public void setId(HMCiOrgIssuestockId id) {
		this.id = id;
	}

}