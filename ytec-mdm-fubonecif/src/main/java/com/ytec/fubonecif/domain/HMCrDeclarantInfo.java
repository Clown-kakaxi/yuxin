package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCrDeclarantInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CR_DECLARANT_INFO")
public class HMCrDeclarantInfo implements java.io.Serializable {

	// Fields

	private HMCrDeclarantInfoId id;

	// Constructors

	/** default constructor */
	public HMCrDeclarantInfo() {
	}

	/** full constructor */
	public HMCrDeclarantInfo(HMCrDeclarantInfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "mainId", column = @Column(name = "MAIN_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "declarantName", column = @Column(name = "DECLARANT_NAME", length = 80)),
			@AttributeOverride(name = "declarantAttr", column = @Column(name = "DECLARANT_ATTR", length = 20)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "tel", column = @Column(name = "TEL", length = 32)),
			@AttributeOverride(name = "email", column = @Column(name = "EMAIL", length = 40)),
			@AttributeOverride(name = "contactAddr", column = @Column(name = "CONTACT_ADDR", length = 200)),
			@AttributeOverride(name = "declarantBankRel", column = @Column(name = "DECLARANT_BANK_REL", length = 20)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "stockRatio", column = @Column(name = "STOCK_RATIO", precision = 10, scale = 4)),
			@AttributeOverride(name = "isCommecialBank", column = @Column(name = "IS_COMMECIAL_BANK", length = 1)),
			@AttributeOverride(name = "cancelState", column = @Column(name = "CANCEL_STATE", length = 20)),
			@AttributeOverride(name = "effectDate", column = @Column(name = "EFFECT_DATE", length = 7)),
			@AttributeOverride(name = "cancelDate", column = @Column(name = "CANCEL_DATE", length = 7)),
			@AttributeOverride(name = "changeType", column = @Column(name = "CHANGE_TYPE", length = 20)),
			@AttributeOverride(name = "declareStatus", column = @Column(name = "DECLARE_STATUS", length = 1)),
			@AttributeOverride(name = "declareDate", column = @Column(name = "DECLARE_DATE", length = 7)),
			@AttributeOverride(name = "cancleCause", column = @Column(name = "CANCLE_CAUSE", length = 200)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "createDate", column = @Column(name = "CREATE_DATE", length = 7)),
			@AttributeOverride(name = "creator", column = @Column(name = "CREATOR", length = 20)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCrDeclarantInfoId getId() {
		return this.id;
	}

	public void setId(HMCrDeclarantInfoId id) {
		this.id = id;
	}

}