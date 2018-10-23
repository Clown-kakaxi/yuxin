package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiSpeciallistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiSpeciallistId implements java.io.Serializable {

	// Fields

	private String specialListId;
	private String custId;
	private String specialListType;
	private String specialListKind;
	private String specialListFlag;
	private String identType;
	private String identNo;
	private String custName;
	private String origin;
	private String enterReason;
	private String statFlag;
	private Date startDate;
	private Date endDate;
	private String approvalFlag;
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
	public HMCiSpeciallistId() {
	}

	/** minimal constructor */
	public HMCiSpeciallistId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiSpeciallistId(String specialListId, String custId,
			String specialListType, String specialListKind,
			String specialListFlag, String identType, String identNo,
			String custName, String origin, String enterReason,
			String statFlag, Date startDate, Date endDate, String approvalFlag,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.specialListId = specialListId;
		this.custId = custId;
		this.specialListType = specialListType;
		this.specialListKind = specialListKind;
		this.specialListFlag = specialListFlag;
		this.identType = identType;
		this.identNo = identNo;
		this.custName = custName;
		this.origin = origin;
		this.enterReason = enterReason;
		this.statFlag = statFlag;
		this.startDate = startDate;
		this.endDate = endDate;
		this.approvalFlag = approvalFlag;
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

	@Column(name = "SPECIAL_LIST_ID", length = 20)
	public String getSpecialListId() {
		return this.specialListId;
	}

	public void setSpecialListId(String specialListId) {
		this.specialListId = specialListId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "SPECIAL_LIST_TYPE", length = 20)
	public String getSpecialListType() {
		return this.specialListType;
	}

	public void setSpecialListType(String specialListType) {
		this.specialListType = specialListType;
	}

	@Column(name = "SPECIAL_LIST_KIND", length = 20)
	public String getSpecialListKind() {
		return this.specialListKind;
	}

	public void setSpecialListKind(String specialListKind) {
		this.specialListKind = specialListKind;
	}

	@Column(name = "SPECIAL_LIST_FLAG", length = 20)
	public String getSpecialListFlag() {
		return this.specialListFlag;
	}

	public void setSpecialListFlag(String specialListFlag) {
		this.specialListFlag = specialListFlag;
	}

	@Column(name = "IDENT_TYPE", length = 20)
	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	@Column(name = "IDENT_NO", length = 40)
	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	@Column(name = "CUST_NAME", length = 80)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name = "ORIGIN", length = 20)
	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(name = "ENTER_REASON", length = 20)
	public String getEnterReason() {
		return this.enterReason;
	}

	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

	@Column(name = "STAT_FLAG", length = 20)
	public String getStatFlag() {
		return this.statFlag;
	}

	public void setStatFlag(String statFlag) {
		this.statFlag = statFlag;
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

	@Column(name = "APPROVAL_FLAG", length = 20)
	public String getApprovalFlag() {
		return this.approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
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
		if (!(other instanceof HMCiSpeciallistId))
			return false;
		HMCiSpeciallistId castOther = (HMCiSpeciallistId) other;

		return ((this.getSpecialListId() == castOther.getSpecialListId()) || (this
				.getSpecialListId() != null
				&& castOther.getSpecialListId() != null && this
				.getSpecialListId().equals(castOther.getSpecialListId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getSpecialListType() == castOther
						.getSpecialListType()) || (this.getSpecialListType() != null
						&& castOther.getSpecialListType() != null && this
						.getSpecialListType().equals(
								castOther.getSpecialListType())))
				&& ((this.getSpecialListKind() == castOther
						.getSpecialListKind()) || (this.getSpecialListKind() != null
						&& castOther.getSpecialListKind() != null && this
						.getSpecialListKind().equals(
								castOther.getSpecialListKind())))
				&& ((this.getSpecialListFlag() == castOther
						.getSpecialListFlag()) || (this.getSpecialListFlag() != null
						&& castOther.getSpecialListFlag() != null && this
						.getSpecialListFlag().equals(
								castOther.getSpecialListFlag())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getCustName() == castOther.getCustName()) || (this
						.getCustName() != null
						&& castOther.getCustName() != null && this
						.getCustName().equals(castOther.getCustName())))
				&& ((this.getOrigin() == castOther.getOrigin()) || (this
						.getOrigin() != null
						&& castOther.getOrigin() != null && this.getOrigin()
						.equals(castOther.getOrigin())))
				&& ((this.getEnterReason() == castOther.getEnterReason()) || (this
						.getEnterReason() != null
						&& castOther.getEnterReason() != null && this
						.getEnterReason().equals(castOther.getEnterReason())))
				&& ((this.getStatFlag() == castOther.getStatFlag()) || (this
						.getStatFlag() != null
						&& castOther.getStatFlag() != null && this
						.getStatFlag().equals(castOther.getStatFlag())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this
						.getStartDate() != null
						&& castOther.getStartDate() != null && this
						.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this
						.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate()
						.equals(castOther.getEndDate())))
				&& ((this.getApprovalFlag() == castOther.getApprovalFlag()) || (this
						.getApprovalFlag() != null
						&& castOther.getApprovalFlag() != null && this
						.getApprovalFlag().equals(castOther.getApprovalFlag())))
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
				+ (getSpecialListId() == null ? 0 : this.getSpecialListId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getSpecialListType() == null ? 0 : this.getSpecialListType()
						.hashCode());
		result = 37
				* result
				+ (getSpecialListKind() == null ? 0 : this.getSpecialListKind()
						.hashCode());
		result = 37
				* result
				+ (getSpecialListFlag() == null ? 0 : this.getSpecialListFlag()
						.hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37 * result
				+ (getCustName() == null ? 0 : this.getCustName().hashCode());
		result = 37 * result
				+ (getOrigin() == null ? 0 : this.getOrigin().hashCode());
		result = 37
				* result
				+ (getEnterReason() == null ? 0 : this.getEnterReason()
						.hashCode());
		result = 37 * result
				+ (getStatFlag() == null ? 0 : this.getStatFlag().hashCode());
		result = 37 * result
				+ (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37 * result
				+ (getEndDate() == null ? 0 : this.getEndDate().hashCode());
		result = 37
				* result
				+ (getApprovalFlag() == null ? 0 : this.getApprovalFlag()
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