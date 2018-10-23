package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerFamilies entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_FAMILIES")
public class HMCiPerFamilies implements java.io.Serializable {

	// Fields

	private HMCiPerFamiliesId id;

	// Constructors

	/** default constructor */
	public HMCiPerFamilies() {
	}

	/** full constructor */
	public HMCiPerFamilies(HMCiPerFamiliesId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "mxtid", column = @Column(name = "MXTID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "membername", column = @Column(name = "MEMBERNAME", length = 40)),
			@AttributeOverride(name = "familyrela", column = @Column(name = "FAMILYRELA", length = 50)),
			@AttributeOverride(name = "membercretTyp", column = @Column(name = "MEMBERCRET_TYP", length = 20)),
			@AttributeOverride(name = "membercretNo", column = @Column(name = "MEMBERCRET_NO", length = 100)),
			@AttributeOverride(name = "tel", column = @Column(name = "TEL", length = 20)),
			@AttributeOverride(name = "mobile", column = @Column(name = "MOBILE", length = 20)),
			@AttributeOverride(name = "email", column = @Column(name = "EMAIL", length = 50)),
			@AttributeOverride(name = "birthday", column = @Column(name = "BIRTHDAY", length = 7)),
			@AttributeOverride(name = "company", column = @Column(name = "COMPANY", length = 50)),
			@AttributeOverride(name = "memberId", column = @Column(name = "MEMBER_ID", length = 40)),
			@AttributeOverride(name = "managerId", column = @Column(name = "MANAGER_ID", length = 40)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerFamiliesId getId() {
		return this.id;
	}

	public void setId(HMCiPerFamiliesId id) {
		this.id = id;
	}

}