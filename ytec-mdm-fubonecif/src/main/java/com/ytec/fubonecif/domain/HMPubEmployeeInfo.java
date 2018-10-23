package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMPubEmployeeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_PUB_EMPLOYEE_INFO")
public class HMPubEmployeeInfo implements java.io.Serializable {

	// Fields

	private HMPubEmployeeInfoId id;

	// Constructors

	/** default constructor */
	public HMPubEmployeeInfo() {
	}

	/** full constructor */
	public HMPubEmployeeInfo(HMPubEmployeeInfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "empNo", column = @Column(name = "EMP_NO", length = 20)),
			@AttributeOverride(name = "branchNo", column = @Column(name = "BRANCH_NO", length = 20)),
			@AttributeOverride(name = "identType", column = @Column(name = "IDENT_TYPE", length = 20)),
			@AttributeOverride(name = "identNo", column = @Column(name = "IDENT_NO", length = 40)),
			@AttributeOverride(name = "empName", column = @Column(name = "EMP_NAME", length = 80)),
			@AttributeOverride(name = "pinyinName", column = @Column(name = "PINYIN_NAME", length = 40)),
			@AttributeOverride(name = "enName", column = @Column(name = "EN_NAME", length = 40)),
			@AttributeOverride(name = "mobilePhone", column = @Column(name = "MOBILE_PHONE", length = 20)),
			@AttributeOverride(name = "tel", column = @Column(name = "TEL", length = 20)),
			@AttributeOverride(name = "email", column = @Column(name = "EMAIL", length = 40)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMPubEmployeeInfoId getId() {
		return this.id;
	}

	public void setId(HMPubEmployeeInfoId id) {
		this.id = id;
	}

}