package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MImImageInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_IM_IMAGE_INFO")
public class MImImageInfo implements java.io.Serializable {

	// Fields

	private String imageIndexId;
	private String imageContent;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MImImageInfo() {
	}

	/** minimal constructor */
	public MImImageInfo(String imageIndexId) {
		this.imageIndexId = imageIndexId;
	}

	/** full constructor */
	public MImImageInfo(String imageIndexId, String imageContent,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.imageIndexId = imageIndexId;
		this.imageContent = imageContent;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "IMAGE_INDEX_ID", unique = true, nullable = false, length = 20)
	public String getImageIndexId() {
		return this.imageIndexId;
	}

	public void setImageIndexId(String imageIndexId) {
		this.imageIndexId = imageIndexId;
	}

	@Column(name = "IMAGE_CONTENT")
	public String getImageContent() {
		return this.imageContent;
	}

	public void setImageContent(String imageContent) {
		this.imageContent = imageContent;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}