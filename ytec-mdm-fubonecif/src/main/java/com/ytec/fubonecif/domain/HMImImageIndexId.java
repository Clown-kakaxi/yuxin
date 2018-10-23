package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMImImageIndexId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMImImageIndexId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMImImageIndexId() {
	}

	/** minimal constructor */
	public HMImImageIndexId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMImImageIndexId(String imageIndexId, String custId,
			String imageType, String imageSerialNo, String fileFormat,
			String imageIndex, String localIndex, String imageDesc,
			String validFlag, Date effectiveDate, Date expiredDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "IMAGE_INDEX_ID", length = 20)
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

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMImImageIndexId))
			return false;
		HMImImageIndexId castOther = (HMImImageIndexId) other;

		return ((this.getImageIndexId() == castOther.getImageIndexId()) || (this
				.getImageIndexId() != null
				&& castOther.getImageIndexId() != null && this
				.getImageIndexId().equals(castOther.getImageIndexId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getImageType() == castOther.getImageType()) || (this
						.getImageType() != null
						&& castOther.getImageType() != null && this
						.getImageType().equals(castOther.getImageType())))
				&& ((this.getImageSerialNo() == castOther.getImageSerialNo()) || (this
						.getImageSerialNo() != null
						&& castOther.getImageSerialNo() != null && this
						.getImageSerialNo()
						.equals(castOther.getImageSerialNo())))
				&& ((this.getFileFormat() == castOther.getFileFormat()) || (this
						.getFileFormat() != null
						&& castOther.getFileFormat() != null && this
						.getFileFormat().equals(castOther.getFileFormat())))
				&& ((this.getImageIndex() == castOther.getImageIndex()) || (this
						.getImageIndex() != null
						&& castOther.getImageIndex() != null && this
						.getImageIndex().equals(castOther.getImageIndex())))
				&& ((this.getLocalIndex() == castOther.getLocalIndex()) || (this
						.getLocalIndex() != null
						&& castOther.getLocalIndex() != null && this
						.getLocalIndex().equals(castOther.getLocalIndex())))
				&& ((this.getImageDesc() == castOther.getImageDesc()) || (this
						.getImageDesc() != null
						&& castOther.getImageDesc() != null && this
						.getImageDesc().equals(castOther.getImageDesc())))
				&& ((this.getValidFlag() == castOther.getValidFlag()) || (this
						.getValidFlag() != null
						&& castOther.getValidFlag() != null && this
						.getValidFlag().equals(castOther.getValidFlag())))
				&& ((this.getEffectiveDate() == castOther.getEffectiveDate()) || (this
						.getEffectiveDate() != null
						&& castOther.getEffectiveDate() != null && this
						.getEffectiveDate()
						.equals(castOther.getEffectiveDate())))
				&& ((this.getExpiredDate() == castOther.getExpiredDate()) || (this
						.getExpiredDate() != null
						&& castOther.getExpiredDate() != null && this
						.getExpiredDate().equals(castOther.getExpiredDate())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getImageIndexId() == null ? 0 : this.getImageIndexId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37 * result
				+ (getImageType() == null ? 0 : this.getImageType().hashCode());
		result = 37
				* result
				+ (getImageSerialNo() == null ? 0 : this.getImageSerialNo()
						.hashCode());
		result = 37
				* result
				+ (getFileFormat() == null ? 0 : this.getFileFormat()
						.hashCode());
		result = 37
				* result
				+ (getImageIndex() == null ? 0 : this.getImageIndex()
						.hashCode());
		result = 37
				* result
				+ (getLocalIndex() == null ? 0 : this.getLocalIndex()
						.hashCode());
		result = 37 * result
				+ (getImageDesc() == null ? 0 : this.getImageDesc().hashCode());
		result = 37 * result
				+ (getValidFlag() == null ? 0 : this.getValidFlag().hashCode());
		result = 37
				* result
				+ (getEffectiveDate() == null ? 0 : this.getEffectiveDate()
						.hashCode());
		result = 37
				* result
				+ (getExpiredDate() == null ? 0 : this.getExpiredDate()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}