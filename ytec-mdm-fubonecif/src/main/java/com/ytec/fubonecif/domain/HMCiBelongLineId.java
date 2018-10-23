package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiBelongLineId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiBelongLineId implements java.io.Serializable {

	// Fields

	private String belongLineId;
	private String custId;
	private String belongLineType;
	private String belongLineNo;
	private String mainType;
	private String validFlag;
	private Date startDate;
	private Date endDate;
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
	public HMCiBelongLineId() {
	}

	/** minimal constructor */
	public HMCiBelongLineId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiBelongLineId(String belongLineId, String custId,
			String belongLineType, String belongLineNo, String mainType,
			String validFlag, Date startDate, Date endDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.belongLineId = belongLineId;
		this.custId = custId;
		this.belongLineType = belongLineType;
		this.belongLineNo = belongLineNo;
		this.mainType = mainType;
		this.validFlag = validFlag;
		this.startDate = startDate;
		this.endDate = endDate;
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

	@Column(name = "BELONG_LINE_ID", length = 20)
	public String getBelongLineId() {
		return this.belongLineId;
	}

	public void setBelongLineId(String belongLineId) {
		this.belongLineId = belongLineId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "BELONG_LINE_TYPE", length = 20)
	public String getBelongLineType() {
		return this.belongLineType;
	}

	public void setBelongLineType(String belongLineType) {
		this.belongLineType = belongLineType;
	}

	@Column(name = "BELONG_LINE_NO", length = 20)
	public String getBelongLineNo() {
		return this.belongLineNo;
	}

	public void setBelongLineNo(String belongLineNo) {
		this.belongLineNo = belongLineNo;
	}

	@Column(name = "MAIN_TYPE", length = 20)
	public String getMainType() {
		return this.mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	@Column(name = "VALID_FLAG", length = 1)
	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
		if (!(other instanceof HMCiBelongLineId))
			return false;
		HMCiBelongLineId castOther = (HMCiBelongLineId) other;

		return ((this.getBelongLineId() == castOther.getBelongLineId()) || (this
				.getBelongLineId() != null
				&& castOther.getBelongLineId() != null && this
				.getBelongLineId().equals(castOther.getBelongLineId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getBelongLineType() == castOther.getBelongLineType()) || (this
						.getBelongLineType() != null
						&& castOther.getBelongLineType() != null && this
						.getBelongLineType().equals(
								castOther.getBelongLineType())))
				&& ((this.getBelongLineNo() == castOther.getBelongLineNo()) || (this
						.getBelongLineNo() != null
						&& castOther.getBelongLineNo() != null && this
						.getBelongLineNo().equals(castOther.getBelongLineNo())))
				&& ((this.getMainType() == castOther.getMainType()) || (this
						.getMainType() != null
						&& castOther.getMainType() != null && this
						.getMainType().equals(castOther.getMainType())))
				&& ((this.getValidFlag() == castOther.getValidFlag()) || (this
						.getValidFlag() != null
						&& castOther.getValidFlag() != null && this
						.getValidFlag().equals(castOther.getValidFlag())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this
						.getStartDate() != null
						&& castOther.getStartDate() != null && this
						.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this
						.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate()
						.equals(castOther.getEndDate())))
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
				+ (getBelongLineId() == null ? 0 : this.getBelongLineId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getBelongLineType() == null ? 0 : this.getBelongLineType()
						.hashCode());
		result = 37
				* result
				+ (getBelongLineNo() == null ? 0 : this.getBelongLineNo()
						.hashCode());
		result = 37 * result
				+ (getMainType() == null ? 0 : this.getMainType().hashCode());
		result = 37 * result
				+ (getValidFlag() == null ? 0 : this.getValidFlag().hashCode());
		result = 37 * result
				+ (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37 * result
				+ (getEndDate() == null ? 0 : this.getEndDate().hashCode());
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