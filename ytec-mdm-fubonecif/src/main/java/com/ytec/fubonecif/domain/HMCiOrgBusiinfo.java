package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiOrgBusiinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ORG_BUSIINFO")
public class HMCiOrgBusiinfo implements java.io.Serializable {

	// Fields

	private HMCiOrgBusiinfoId id;

	// Constructors

	/** default constructor */
	public HMCiOrgBusiinfo() {
	}

	/** full constructor */
	public HMCiOrgBusiinfo(HMCiOrgBusiinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "manageForm", column = @Column(name = "MANAGE_FORM", length = 20)),
			@AttributeOverride(name = "manageAptitude", column = @Column(name = "MANAGE_APTITUDE", length = 20)),
			@AttributeOverride(name = "manageStat", column = @Column(name = "MANAGE_STAT", length = 20)),
			@AttributeOverride(name = "mainMaterial", column = @Column(name = "MAIN_MATERIAL", length = 20)),
			@AttributeOverride(name = "mainProduct", column = @Column(name = "MAIN_PRODUCT", length = 20)),
			@AttributeOverride(name = "mainService", column = @Column(name = "MAIN_SERVICE", length = 20)),
			@AttributeOverride(name = "mainCust", column = @Column(name = "MAIN_CUST", length = 20)),
			@AttributeOverride(name = "mainMaterialArea", column = @Column(name = "MAIN_MATERIAL_AREA")),
			@AttributeOverride(name = "mainSaleMarket", column = @Column(name = "MAIN_SALE_MARKET")),
			@AttributeOverride(name = "mainSupplier", column = @Column(name = "MAIN_SUPPLIER")),
			@AttributeOverride(name = "mainSale", column = @Column(name = "MAIN_SALE")),
			@AttributeOverride(name = "lastInco", column = @Column(name = "LAST_INCO", precision = 17)),
			@AttributeOverride(name = "yearInco", column = @Column(name = "YEAR_INCO", precision = 17)),
			@AttributeOverride(name = "saleNum", column = @Column(name = "SALE_NUM", scale = 0)),
			@AttributeOverride(name = "saleAmt", column = @Column(name = "SALE_AMT", precision = 17)),
			@AttributeOverride(name = "budgetAdminType", column = @Column(name = "BUDGET_ADMIN_TYPE", length = 20)),
			@AttributeOverride(name = "businessPlan", column = @Column(name = "BUSINESS_PLAN")),
			@AttributeOverride(name = "workFieldOwnership", column = @Column(name = "WORK_FIELD_OWNERSHIP", length = 20)),
			@AttributeOverride(name = "workFieldArea", column = @Column(name = "WORK_FIELD_AREA", precision = 10)),
			@AttributeOverride(name = "custBusiArea", column = @Column(name = "CUST_BUSI_AREA", precision = 10)),
			@AttributeOverride(name = "custOfficeArea", column = @Column(name = "CUST_OFFICE_AREA", precision = 10)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiOrgBusiinfoId getId() {
		return this.id;
	}

	public void setId(HMCiOrgBusiinfoId id) {
		this.id = id;
	}

}