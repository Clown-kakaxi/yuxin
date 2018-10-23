package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMPubBranchInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_PUB_BRANCH_INFO")
public class HMPubBranchInfo implements java.io.Serializable {

	// Fields

	private HMPubBranchInfoId id;

	// Constructors

	/** default constructor */
	public HMPubBranchInfo() {
	}

	/** full constructor */
	public HMPubBranchInfo(HMPubBranchInfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "branchNo", column = @Column(name = "BRANCH_NO", length = 20)),
			@AttributeOverride(name = "farenNo", column = @Column(name = "FAREN_NO", length = 20)),
			@AttributeOverride(name = "branchName", column = @Column(name = "BRANCH_NAME", length = 40)),
			@AttributeOverride(name = "branchShName", column = @Column(name = "BRANCH_SH_NAME", length = 20)),
			@AttributeOverride(name = "fenhangNo", column = @Column(name = "FENHANG_NO", length = 20)),
			@AttributeOverride(name = "branchType", column = @Column(name = "BRANCH_TYPE", length = 20)),
			@AttributeOverride(name = "branchKind", column = @Column(name = "BRANCH_KIND", length = 20)),
			@AttributeOverride(name = "branchLevel", column = @Column(name = "BRANCH_LEVEL", precision = 22, scale = 0)),
			@AttributeOverride(name = "validFlag", column = @Column(name = "VALID_FLAG", length = 1)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "endDate", column = @Column(name = "END_DATE", length = 7)),
			@AttributeOverride(name = "countryOrRegion", column = @Column(name = "COUNTRY_OR_REGION", length = 20)),
			@AttributeOverride(name = "areaCode", column = @Column(name = "AREA_CODE", length = 20)),
			@AttributeOverride(name = "provinceCode", column = @Column(name = "PROVINCE_CODE", length = 20)),
			@AttributeOverride(name = "cityCode", column = @Column(name = "CITY_CODE", length = 20)),
			@AttributeOverride(name = "countyCode", column = @Column(name = "COUNTY_CODE", length = 20)),
			@AttributeOverride(name = "branchAddress", column = @Column(name = "BRANCH_ADDRESS", length = 200)),
			@AttributeOverride(name = "branchZipcode", column = @Column(name = "BRANCH_ZIPCODE", length = 20)),
			@AttributeOverride(name = "branchTel", column = @Column(name = "BRANCH_TEL", length = 20)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMPubBranchInfoId getId() {
		return this.id;
	}

	public void setId(HMPubBranchInfoId id) {
		this.id = id;
	}

}