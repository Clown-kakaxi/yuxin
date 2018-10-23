package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MImImageIndex entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_IM_IMAGE_INDEX")
public class MImImageIndex implements java.io.Serializable {

	// Fields

	private String imageIndexId;
	private String custId;
	private String imageType;
	private String imageSerialNo;
	private String fileFormat;
	private String imageIndex;
	private String localIndex;
	private String imageDesc;
	private String validFlag;
	private Date effectiveDate;
	private Date expiredDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MImImageIndex() {
	}

	/** minimal constructor */
	public MImImageIndex(String imageIndexId) {
		this.imageIndexId = imageIndexId;
	}

	/** full constructor */
	public MImImageIndex(String imageIndexId, String custId, String imageType,
			String imageSerialNo, String fileFormat, String imageIndex,
			String localIndex, String imageDesc, String validFlag,
			Date effectiveDate, Date expiredDate, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
		this.imageIndexId = imageIndexId;
		this.custId = custId;
		this.imageType = imageType;
		this.imageSerialNo = imageSerialNo;
		this.fileFormat = fileFormat;
		this.imageIndex = imageIndex;
		this.localIndex = localIndex;
		this.imageDesc = imageDesc;
		this.validFlag = validFlag;
		this.effectiveDate = effectiveDate;
		this.expiredDate = expiredDate;
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

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "IMAGE_TYPE", length = 20)
	public String getImageType() {
		return this.imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	@Column(name = "IMAGE_SERIAL_NO", length = 32)
	public String getImageSerialNo() {
		return this.imageSerialNo;
	}

	public void setImageSerialNo(String imageSerialNo) {
		this.imageSerialNo = imageSerialNo;
	}

	@Column(name = "FILE_FORMAT", length = 20)
	public String getFileFormat() {
		return this.fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	@Column(name = "IMAGE_INDEX")
	public String getImageIndex() {
		return this.imageIndex;
	}

	public void setImageIndex(String imageIndex) {
		this.imageIndex = imageIndex;
	}

	@Column(name = "LOCAL_INDEX")
	public String getLocalIndex() {
		return this.localIndex;
	}

	public void setLocalIndex(String localIndex) {
		this.localIndex = localIndex;
	}

	@Column(name = "IMAGE_DESC")
	public String getImageDesc() {
		return this.imageDesc;
	}

	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

	@Column(name = "VALID_FLAG", length = 1)
	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_DATE", length = 7)
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRED_DATE", length = 7)
	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
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