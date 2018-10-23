package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiTaxinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_TAXINFO")
public class HMCiTaxinfo implements java.io.Serializable {

	// Fields

	private HMCiTaxinfoId id;

	// Constructors

	/** default constructor */
	public HMCiTaxinfo() {
	}

	/** full constructor */
	public HMCiTaxinfo(HMCiTaxinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "taxInfoId", column = @Column(name = "TAX_INFO_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "taxType", column = @Column(name = "TAX_TYPE", length = 20)),
			@AttributeOverride(name = "taxDate", column = @Column(name = "TAX_DATE", length = 7)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "endDate", column = @Column(name = "END_DATE", length = 7)),
			@AttributeOverride(name = "taxCurrency", column = @Column(name = "TAX_CURRENCY", length = 20)),
			@AttributeOverride(name = "taxAmt", column = @Column(name = "TAX_AMT", precision = 17)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiTaxinfoId getId() {
		return this.id;
	}

	public void setId(HMCiTaxinfoId id) {
		this.id = id;
	}

}