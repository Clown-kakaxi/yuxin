package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiGroupMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_GROUP_MEMBER")
public class HMCiGroupMember implements java.io.Serializable {

	// Fields

	private HMCiGroupMemberId id;

	// Constructors

	/** default constructor */
	public HMCiGroupMember() {
	}

	/** full constructor */
	public HMCiGroupMember(HMCiGroupMemberId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "memberId", column = @Column(name = "MEMBER_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "groupId", column = @Column(name = "GROUP_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "stockRate", column = @Column(name = "STOCK_RATE", precision = 10, scale = 4)),
			@AttributeOverride(name = "memberType", column = @Column(name = "MEMBER_TYPE", length = 20)),
			@AttributeOverride(name = "custName", column = @Column(name = "CUST_NAME", length = 80)),
			@AttributeOverride(name = "cropCode", column = @Column(name = "CROP_CODE", length = 40)),
			@AttributeOverride(name = "corpNameUp", column = @Column(name = "CORP_NAME_UP", length = 80)),
			@AttributeOverride(name = "relationshipUp", column = @Column(name = "RELATIONSHIP_UP", length = 20)),
			@AttributeOverride(name = "custStat", column = @Column(name = "CUST_STAT", length = 20)),
			@AttributeOverride(name = "industry", column = @Column(name = "INDUSTRY", length = 20)),
			@AttributeOverride(name = "custScale", column = @Column(name = "CUST_SCALE", precision = 22, scale = 0)),
			@AttributeOverride(name = "custScaleCheck", column = @Column(name = "CUST_SCALE_CHECK", precision = 22, scale = 0)),
			@AttributeOverride(name = "taxCertNo", column = @Column(name = "TAX_CERT_NO", length = 40)),
			@AttributeOverride(name = "licenseNo", column = @Column(name = "LICENSE_NO", length = 40)),
			@AttributeOverride(name = "memberShip", column = @Column(name = "MEMBER_SHIP", length = 20)),
			@AttributeOverride(name = "mainBrId", column = @Column(name = "MAIN_BR_ID", length = 20)),
			@AttributeOverride(name = "cusManagerId", column = @Column(name = "CUS_MANAGER_ID", length = 20)),
			@AttributeOverride(name = "grpCorreType", column = @Column(name = "GRP_CORRE_TYPE", length = 20)),
			@AttributeOverride(name = "grpCorreDetail", column = @Column(name = "GRP_CORRE_DETAIL", length = 200)),
			@AttributeOverride(name = "inputUserId", column = @Column(name = "INPUT_USER_ID", length = 20)),
			@AttributeOverride(name = "inputDate", column = @Column(name = "INPUT_DATE", length = 7)),
			@AttributeOverride(name = "inputBrId", column = @Column(name = "INPUT_BR_ID", length = 20)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiGroupMemberId getId() {
		return this.id;
	}

	public void setId(HMCiGroupMemberId id) {
		this.id = id;
	}

}