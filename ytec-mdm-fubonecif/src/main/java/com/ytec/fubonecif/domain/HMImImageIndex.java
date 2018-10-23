package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMImImageIndex entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_IM_IMAGE_INDEX")
public class HMImImageIndex implements java.io.Serializable {

	// Fields

	private HMImImageIndexId id;

	// Constructors

	/** default constructor */
	public HMImImageIndex() {
	}

	/** full constructor */
	public HMImImageIndex(HMImImageIndexId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "imageIndexId", column = @Column(name = "IMAGE_INDEX_ID", length = 20)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", length = 20)),
			@AttributeOverride(name = "imageType", column = @Column(name = "IMAGE_TYPE", length = 20)),
			@AttributeOverride(name = "imageSerialNo", column = @Column(name = "IMAGE_SERIAL_NO", length = 32)),
			@AttributeOverride(name = "fileFormat", column = @Column(name = "FILE_FORMAT", length = 20)),
			@AttributeOverride(name = "imageIndex", column = @Column(name = "IMAGE_INDEX")),
			@AttributeOverride(name = "localIndex", column = @Column(name = "LOCAL_INDEX")),
			@AttributeOverride(name = "imageDesc", column = @Column(name = "IMAGE_DESC")),
			@AttributeOverride(name = "validFlag", column = @Column(name = "VALID_FLAG", length = 1)),
			@AttributeOverride(name = "effectiveDate", column = @Column(name = "EFFECTIVE_DATE", length = 7)),
			@AttributeOverride(name = "expiredDate", column = @Column(name = "EXPIRED_DATE", length = 7)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMImImageIndexId getId() {
		return this.id;
	}

	public void setId(HMImImageIndexId id) {
		this.id = id;
	}

}