package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMPubFarenInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_PUB_FAREN_INFO")
public class HMPubFarenInfo implements java.io.Serializable {

	// Fields

	private HMPubFarenInfoId id;

	// Constructors

	/** default constructor */
	public HMPubFarenInfo() {
	}

	/** full constructor */
	public HMPubFarenInfo(HMPubFarenInfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "farenNo", column = @Column(name = "FAREN_NO", length = 20)),
			@AttributeOverride(name = "farenName", column = @Column(name = "FAREN_NAME", length = 80)),
			@AttributeOverride(name = "nameAbbr", column = @Column(name = "NAME_ABBR", length = 20)),
			@AttributeOverride(name = "pinyinName", column = @Column(name = "PINYIN_NAME", length = 80)),
			@AttributeOverride(name = "pinyinAbbr", column = @Column(name = "PINYIN_ABBR", length = 20)),
			@AttributeOverride(name = "farenDesc", column = @Column(name = "FAREN_DESC")),
			@AttributeOverride(name = "validFlag", column = @Column(name = "VALID_FLAG", length = 1)),
			@AttributeOverride(name = "startDate", column = @Column(name = "START_DATE", length = 7)),
			@AttributeOverride(name = "endDate", column = @Column(name = "END_DATE", length = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMPubFarenInfoId getId() {
		return this.id;
	}

	public void setId(HMPubFarenInfoId id) {
		this.id = id;
	}

}