package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiContmeth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_CONTMETH")
public class HMCiContmeth implements java.io.Serializable {

	// Fields

	private HMCiContmethId id;

	// Constructors

	/** default constructor */
	public HMCiContmeth() {
	}

	/** full constructor */
	public HMCiContmeth(HMCiContmethId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "contmethId", column = @Column(name = "CONTMETH_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "isPriori", column = @Column(name = "IS_PRIORI", length = 1)),
			@AttributeOverride(name = "contmethType", column = @Column(name = "CONTMETH_TYPE", length = 20)),
			@AttributeOverride(name = "contmethInfo", column = @Column(name = "CONTMETH_INFO", length = 100)),
			@AttributeOverride(name = "contmethSeq", column = @Column(name = "CONTMETH_SEQ", precision = 22, scale = 0)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "stat", column = @Column(name = "STAT", length = 20)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM")),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiContmethId getId() {
		return this.id;
	}

	public void setId(HMCiContmethId id) {
		this.id = id;
	}

}