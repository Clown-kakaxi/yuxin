package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCrRelatesParaInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CR_RELATES_PARA_INFO")
public class HMCrRelatesParaInfo implements java.io.Serializable {

	// Fields

	private HMCrRelatesParaInfoId id;

	// Constructors

	/** default constructor */
	public HMCrRelatesParaInfo() {
	}

	/** full constructor */
	public HMCrRelatesParaInfo(HMCrRelatesParaInfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "relateId", column = @Column(name = "RELATE_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "relatesType", column = @Column(name = "RELATES_TYPE", length = 20)),
			@AttributeOverride(name = "twRelatesType", column = @Column(name = "TW_RELATES_TYPE", length = 20)),
			@AttributeOverride(name = "isRelation", column = @Column(name = "IS_RELATION", length = 1)),
			@AttributeOverride(name = "isCreditparty", column = @Column(name = "IS_CREDITPARTY", length = 1)),
			@AttributeOverride(name = "isOutCreditparty", column = @Column(name = "IS_OUT_CREDITPARTY", length = 1)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCrRelatesParaInfoId getId() {
		return this.id;
	}

	public void setId(HMCrRelatesParaInfoId id) {
		this.id = id;
	}

}