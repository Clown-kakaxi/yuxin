package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMImImageInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_IM_IMAGE_INFO")
public class HMImImageInfo implements java.io.Serializable {

	// Fields

	private HMImImageInfoId id;

	// Constructors

	/** default constructor */
	public HMImImageInfo() {
	}

	/** full constructor */
	public HMImImageInfo(HMImImageInfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "imageIndexId", column = @Column(name = "IMAGE_INDEX_ID", length = 20)),
			@AttributeOverride(name = "imageContent", column = @Column(name = "IMAGE_CONTENT")),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMImImageInfoId getId() {
		return this.id;
	}

	public void setId(HMImImageInfoId id) {
		this.id = id;
	}

}