package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiIdentityVerifyId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiIdentityVerifyId implements java.io.Serializable {

	// Fields

	private String custId;
	private String verifyStat;
	private String verifyResult;
	private String reason;
	private String dealWay;
	private String verifyBranchNo;
	private String verifyTellerNo;
	private Date verifyDate;
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
	public HMCiIdentityVerifyId() {
	}

	/** minimal constructor */
	public HMCiIdentityVerifyId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiIdentityVerifyId(String custId, String verifyStat,
			String verifyResult, String reason, String dealWay,
			String verifyBranchNo, String verifyTellerNo, Date verifyDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.custId = custId;
		this.verifyStat = verifyStat;
		this.verifyResult = verifyResult;
		this.reason = reason;
		this.dealWay = dealWay;
		this.verifyBranchNo = verifyBranchNo;
		this.verifyTellerNo = verifyTellerNo;
		this.verifyDate = verifyDate;
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

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "VERIFY_STAT", length = 20)
	public String getVerifyStat() {
		return this.verifyStat;
	}

	public void setVerifyStat(String verifyStat) {
		this.verifyStat = verifyStat;
	}

	@Column(name = "VERIFY_RESULT", length = 20)
	public String getVerifyResult() {
		return this.verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	@Column(name = "REASON", length = 80)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "DEAL_WAY", length = 80)
	public String getDealWay() {
		return this.dealWay;
	}

	public void setDealWay(String dealWay) {
		this.dealWay = dealWay;
	}

	@Column(name = "VERIFY_BRANCH_NO", length = 20)
	public String getVerifyBranchNo() {
		return this.verifyBranchNo;
	}

	public void setVerifyBranchNo(String verifyBranchNo) {
		this.verifyBranchNo = verifyBranchNo;
	}

	@Column(name = "VERIFY_TELLER_NO", length = 20)
	public String getVerifyTellerNo() {
		return this.verifyTellerNo;
	}

	public void setVerifyTellerNo(String verifyTellerNo) {
		this.verifyTellerNo = verifyTellerNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VERIFY_DATE", length = 7)
	public Date getVerifyDate() {
		return this.verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
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
		if (!(other instanceof HMCiIdentityVerifyId))
			return false;
		HMCiIdentityVerifyId castOther = (HMCiIdentityVerifyId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getVerifyStat() == castOther.getVerifyStat()) || (this
						.getVerifyStat() != null
						&& castOther.getVerifyStat() != null && this
						.getVerifyStat().equals(castOther.getVerifyStat())))
				&& ((this.getVerifyResult() == castOther.getVerifyResult()) || (this
						.getVerifyResult() != null
						&& castOther.getVerifyResult() != null && this
						.getVerifyResult().equals(castOther.getVerifyResult())))
				&& ((this.getReason() == castOther.getReason()) || (this
						.getReason() != null
						&& castOther.getReason() != null && this.getReason()
						.equals(castOther.getReason())))
				&& ((this.getDealWay() == castOther.getDealWay()) || (this
						.getDealWay() != null
						&& castOther.getDealWay() != null && this.getDealWay()
						.equals(castOther.getDealWay())))
				&& ((this.getVerifyBranchNo() == castOther.getVerifyBranchNo()) || (this
						.getVerifyBranchNo() != null
						&& castOther.getVerifyBranchNo() != null && this
						.getVerifyBranchNo().equals(
								castOther.getVerifyBranchNo())))
				&& ((this.getVerifyTellerNo() == castOther.getVerifyTellerNo()) || (this
						.getVerifyTellerNo() != null
						&& castOther.getVerifyTellerNo() != null && this
						.getVerifyTellerNo().equals(
								castOther.getVerifyTellerNo())))
				&& ((this.getVerifyDate() == castOther.getVerifyDate()) || (this
						.getVerifyDate() != null
						&& castOther.getVerifyDate() != null && this
						.getVerifyDate().equals(castOther.getVerifyDate())))
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
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getVerifyStat() == null ? 0 : this.getVerifyStat()
						.hashCode());
		result = 37
				* result
				+ (getVerifyResult() == null ? 0 : this.getVerifyResult()
						.hashCode());
		result = 37 * result
				+ (getReason() == null ? 0 : this.getReason().hashCode());
		result = 37 * result
				+ (getDealWay() == null ? 0 : this.getDealWay().hashCode());
		result = 37
				* result
				+ (getVerifyBranchNo() == null ? 0 : this.getVerifyBranchNo()
						.hashCode());
		result = 37
				* result
				+ (getVerifyTellerNo() == null ? 0 : this.getVerifyTellerNo()
						.hashCode());
		result = 37
				* result
				+ (getVerifyDate() == null ? 0 : this.getVerifyDate()
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