package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerFamily entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_FAMILY")
public class HMCiPerFamily implements java.io.Serializable {

	// Fields

	private HMCiPerFamilyId id;

	// Constructors

	/** default constructor */
	public HMCiPerFamily() {
	}

	/** full constructor */
	public HMCiPerFamily(HMCiPerFamilyId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "familyAddr", column = @Column(name = "FAMILY_ADDR", length = 200)),
			@AttributeOverride(name = "homeTel", column = @Column(name = "HOME_TEL", length = 20)),
			@AttributeOverride(name = "population", column = @Column(name = "POPULATION", precision = 22, scale = 0)),
			@AttributeOverride(name = "childrenNum", column = @Column(name = "CHILDREN_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "providePopNum", column = @Column(name = "PROVIDE_POP_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "supplyPopNum", column = @Column(name = "SUPPLY_POP_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "laborPopNum", column = @Column(name = "LABOR_POP_NUM", precision = 22, scale = 0)),
			@AttributeOverride(name = "isHouseHolder", column = @Column(name = "IS_HOUSE_HOLDER", length = 1)),
			@AttributeOverride(name = "houseHolderName", column = @Column(name = "HOUSE_HOLDER_NAME", length = 80)),
			@AttributeOverride(name = "residenceStat", column = @Column(name = "RESIDENCE_STAT", length = 20)),
			@AttributeOverride(name = "houseStat", column = @Column(name = "HOUSE_STAT", length = 20)),
			@AttributeOverride(name = "hasHomeCar", column = @Column(name = "HAS_HOME_CAR", length = 1)),
			@AttributeOverride(name = "isCreditFamily", column = @Column(name = "IS_CREDIT_FAMILY", length = 1)),
			@AttributeOverride(name = "isHarmony", column = @Column(name = "IS_HARMONY", length = 1)),
			@AttributeOverride(name = "creditAmount", column = @Column(name = "CREDIT_AMOUNT", precision = 17)),
			@AttributeOverride(name = "creditInfo", column = @Column(name = "CREDIT_INFO", length = 20)),
			@AttributeOverride(name = "busiAndScale", column = @Column(name = "BUSI_AND_SCALE", length = 200)),
			@AttributeOverride(name = "mainIncomeSource", column = @Column(name = "MAIN_INCOME_SOURCE", length = 20)),
			@AttributeOverride(name = "familyAnnIncScope", column = @Column(name = "FAMILY_ANN_INC_SCOPE", length = 20)),
			@AttributeOverride(name = "familyAnnualPayScope", column = @Column(name = "FAMILY_ANNUAL_PAY_SCOPE", length = 20)),
			@AttributeOverride(name = "familyAssetsInfo", column = @Column(name = "FAMILY_ASSETS_INFO", length = 80)),
			@AttributeOverride(name = "familyDebtScope", column = @Column(name = "FAMILY_DEBT_SCOPE", length = 20)),
			@AttributeOverride(name = "familyAdverseRecords", column = @Column(name = "FAMILY_ADVERSE_RECORDS", length = 200)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerFamilyId getId() {
		return this.id;
	}

	public void setId(HMCiPerFamilyId id) {
		this.id = id;
	}

}