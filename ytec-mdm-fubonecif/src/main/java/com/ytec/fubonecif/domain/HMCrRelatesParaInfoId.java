package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCrRelatesParaInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCrRelatesParaInfoId implements java.io.Serializable {

	// Fields

	private BigDecimal relateId;
	private String relatesType;
	private String twRelatesType;
	private String isRelation;
	private String isCreditparty;
	private String isOutCreditparty;
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
	public HMCrRelatesParaInfoId() {
	}

	/** minimal constructor */
	public HMCrRelatesParaInfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCrRelatesParaInfoId(BigDecimal relateId, String relatesType,
			String twRelatesType, String isRelation, String isCreditparty,
			String isOutCreditparty, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.relateId = relateId;
		this.relatesType = relatesType;
		this.twRelatesType = twRelatesType;
		this.isRelation = isRelation;
		this.isCreditparty = isCreditparty;
		this.isOutCreditparty = isOutCreditparty;
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

	@Column(name = "RELATE_ID", precision = 22, scale = 0)
	public BigDecimal getRelateId() {
		return this.relateId;
	}

	public void setRelateId(BigDecimal relateId) {
		this.relateId = relateId;
	}

	@Column(name = "RELATES_TYPE", length = 20)
	public String getRelatesType() {
		return this.relatesType;
	}

	public void setRelatesType(String relatesType) {
		this.relatesType = relatesType;
	}

	@Column(name = "TW_RELATES_TYPE", length = 20)
	public String getTwRelatesType() {
		return this.twRelatesType;
	}

	public void setTwRelatesType(String twRelatesType) {
		this.twRelatesType = twRelatesType;
	}

	@Column(name = "IS_RELATION", length = 1)
	public String getIsRelation() {
		return this.isRelation;
	}

	public void setIsRelation(String isRelation) {
		this.isRelation = isRelation;
	}

	@Column(name = "IS_CREDITPARTY", length = 1)
	public String getIsCreditparty() {
		return this.isCreditparty;
	}

	public void setIsCreditparty(String isCreditparty) {
		this.isCreditparty = isCreditparty;
	}

	@Column(name = "IS_OUT_CREDITPARTY", length = 1)
	public String getIsOutCreditparty() {
		return this.isOutCreditparty;
	}

	public void setIsOutCreditparty(String isOutCreditparty) {
		this.isOutCreditparty = isOutCreditparty;
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
		if (!(other instanceof HMCrRelatesParaInfoId))
			return false;
		HMCrRelatesParaInfoId castOther = (HMCrRelatesParaInfoId) other;

		return ((this.getRelateId() == castOther.getRelateId()) || (this
				.getRelateId() != null
				&& castOther.getRelateId() != null && this.getRelateId()
				.equals(castOther.getRelateId())))
				&& ((this.getRelatesType() == castOther.getRelatesType()) || (this
						.getRelatesType() != null
						&& castOther.getRelatesType() != null && this
						.getRelatesType().equals(castOther.getRelatesType())))
				&& ((this.getTwRelatesType() == castOther.getTwRelatesType()) || (this
						.getTwRelatesType() != null
						&& castOther.getTwRelatesType() != null && this
						.getTwRelatesType()
						.equals(castOther.getTwRelatesType())))
				&& ((this.getIsRelation() == castOther.getIsRelation()) || (this
						.getIsRelation() != null
						&& castOther.getIsRelation() != null && this
						.getIsRelation().equals(castOther.getIsRelation())))
				&& ((this.getIsCreditparty() == castOther.getIsCreditparty()) || (this
						.getIsCreditparty() != null
						&& castOther.getIsCreditparty() != null && this
						.getIsCreditparty()
						.equals(castOther.getIsCreditparty())))
				&& ((this.getIsOutCreditparty() == castOther
						.getIsOutCreditparty()) || (this.getIsOutCreditparty() != null
						&& castOther.getIsOutCreditparty() != null && this
						.getIsOutCreditparty().equals(
								castOther.getIsOutCreditparty())))
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

		result = 37 * result
				+ (getRelateId() == null ? 0 : this.getRelateId().hashCode());
		result = 37
				* result
				+ (getRelatesType() == null ? 0 : this.getRelatesType()
						.hashCode());
		result = 37
				* result
				+ (getTwRelatesType() == null ? 0 : this.getTwRelatesType()
						.hashCode());
		result = 37
				* result
				+ (getIsRelation() == null ? 0 : this.getIsRelation()
						.hashCode());
		result = 37
				* result
				+ (getIsCreditparty() == null ? 0 : this.getIsCreditparty()
						.hashCode());
		result = 37
				* result
				+ (getIsOutCreditparty() == null ? 0 : this
						.getIsOutCreditparty().hashCode());
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