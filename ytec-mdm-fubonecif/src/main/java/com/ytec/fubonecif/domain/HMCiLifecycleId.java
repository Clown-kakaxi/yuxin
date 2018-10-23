package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiLifecycleId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiLifecycleId implements java.io.Serializable {

	// Fields

	private String lifecycleId;
	private String custId;
	private String lifecycleStatType;
	private String lifecycleStatDesc;
	private Date lifecycleStatDate;
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
	public HMCiLifecycleId() {
	}

	/** minimal constructor */
	public HMCiLifecycleId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiLifecycleId(String lifecycleId, String custId,
			String lifecycleStatType, String lifecycleStatDesc,
			Date lifecycleStatDate, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.lifecycleId = lifecycleId;
		this.custId = custId;
		this.lifecycleStatType = lifecycleStatType;
		this.lifecycleStatDesc = lifecycleStatDesc;
		this.lifecycleStatDate = lifecycleStatDate;
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

	@Column(name = "LIFECYCLE_ID", length = 20)
	public String getLifecycleId() {
		return this.lifecycleId;
	}

	public void setLifecycleId(String lifecycleId) {
		this.lifecycleId = lifecycleId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "LIFECYCLE_STAT_TYPE", length = 20)
	public String getLifecycleStatType() {
		return this.lifecycleStatType;
	}

	public void setLifecycleStatType(String lifecycleStatType) {
		this.lifecycleStatType = lifecycleStatType;
	}

	@Column(name = "LIFECYCLE_STAT_DESC", length = 80)
	public String getLifecycleStatDesc() {
		return this.lifecycleStatDesc;
	}

	public void setLifecycleStatDesc(String lifecycleStatDesc) {
		this.lifecycleStatDesc = lifecycleStatDesc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LIFECYCLE_STAT_DATE", length = 7)
	public Date getLifecycleStatDate() {
		return this.lifecycleStatDate;
	}

	public void setLifecycleStatDate(Date lifecycleStatDate) {
		this.lifecycleStatDate = lifecycleStatDate;
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
		if (!(other instanceof HMCiLifecycleId))
			return false;
		HMCiLifecycleId castOther = (HMCiLifecycleId) other;

		return ((this.getLifecycleId() == castOther.getLifecycleId()) || (this
				.getLifecycleId() != null
				&& castOther.getLifecycleId() != null && this.getLifecycleId()
				.equals(castOther.getLifecycleId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getLifecycleStatType() == castOther
						.getLifecycleStatType()) || (this
						.getLifecycleStatType() != null
						&& castOther.getLifecycleStatType() != null && this
						.getLifecycleStatType().equals(
								castOther.getLifecycleStatType())))
				&& ((this.getLifecycleStatDesc() == castOther
						.getLifecycleStatDesc()) || (this
						.getLifecycleStatDesc() != null
						&& castOther.getLifecycleStatDesc() != null && this
						.getLifecycleStatDesc().equals(
								castOther.getLifecycleStatDesc())))
				&& ((this.getLifecycleStatDate() == castOther
						.getLifecycleStatDate()) || (this
						.getLifecycleStatDate() != null
						&& castOther.getLifecycleStatDate() != null && this
						.getLifecycleStatDate().equals(
								castOther.getLifecycleStatDate())))
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
				+ (getLifecycleId() == null ? 0 : this.getLifecycleId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getLifecycleStatType() == null ? 0 : this
						.getLifecycleStatType().hashCode());
		result = 37
				* result
				+ (getLifecycleStatDesc() == null ? 0 : this
						.getLifecycleStatDesc().hashCode());
		result = 37
				* result
				+ (getLifecycleStatDate() == null ? 0 : this
						.getLifecycleStatDate().hashCode());
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