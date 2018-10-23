package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerJobresume entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PER_JOBRESUME")
public class HMCiPerJobresume implements java.io.Serializable {

	// Fields

	private HMCiPerJobresumeId id;

	// Constructors

	/** default constructor */
	public HMCiPerJobresume() {
	}

	/** full constructor */
	public HMCiPerJobresume(HMCiPerJobresumeId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "jobresumeId", column = @Column(name = "JOBRESUME_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "endDate", column = @Column(name = "END_DATE", length = 7)),
			@AttributeOverride(name = "unitChar", column = @Column(name = "UNIT_CHAR", length = 20)),
			@AttributeOverride(name = "unitName", column = @Column(name = "UNIT_NAME", length = 200)),
			@AttributeOverride(name = "workDept", column = @Column(name = "WORK_DEPT", length = 80)),
			@AttributeOverride(name = "position", column = @Column(name = "POSITION", length = 80)),
			@AttributeOverride(name = "unitTel", column = @Column(name = "UNIT_TEL", length = 20)),
			@AttributeOverride(name = "unitAddress", column = @Column(name = "UNIT_ADDRESS", length = 200)),
			@AttributeOverride(name = "unitZipcode", column = @Column(name = "UNIT_ZIPCODE", length = 32)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPerJobresumeId getId() {
		return this.id;
	}

	public void setId(HMCiPerJobresumeId id) {
		this.id = id;
	}

}