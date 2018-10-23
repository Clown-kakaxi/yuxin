package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiOrgAuthId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgAuthId implements java.io.Serializable {

	// Fields

	private String orgAuthId;
	private String custId;
	private String authType;
	private String authOrg;
	private String authResult;
	private String certName;
	private String certNo;
	private Date authDate;
	private Date validDate;
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
	public HMCiOrgAuthId() {
	}

	/** minimal constructor */
	public HMCiOrgAuthId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgAuthId(String orgAuthId, String custId, String authType,
			String authOrg, String authResult, String certName, String certNo,
			Date authDate, Date validDate, Date effectiveDate,
			Date expiredDate, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.orgAuthId = orgAuthId;
		this.custId = custId;
		this.authType = authType;
		this.authOrg = authOrg;
		this.authResult = authResult;
		this.certName = certName;
		this.certNo = certNo;
		this.authDate = authDate;
		this.validDate = validDate;
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

	@Column(name = "ORG_AUTH_ID", length = 20)
	public String getOrgAuthId() {
		return this.orgAuthId;
	}

	public void setOrgAuthId(String orgAuthId) {
		this.orgAuthId = orgAuthId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "AUTH_TYPE", length = 20)
	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	@Column(name = "AUTH_ORG", length = 80)
	public String getAuthOrg() {
		return this.authOrg;
	}

	public void setAuthOrg(String authOrg) {
		this.authOrg = authOrg;
	}

	@Column(name = "AUTH_RESULT", length = 200)
	public String getAuthResult() {
		return this.authResult;
	}

	public void setAuthResult(String authResult) {
		this.authResult = authResult;
	}

	@Column(name = "CERT_NAME", length = 80)
	public String getCertName() {
		return this.certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	@Column(name = "CERT_NO", length = 32)
	public String getCertNo() {
		return this.certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "AUTH_DATE", length = 7)
	public Date getAuthDate() {
		return this.authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VALID_DATE", length = 7)
	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
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
		if (!(other instanceof HMCiOrgAuthId))
			return false;
		HMCiOrgAuthId castOther = (HMCiOrgAuthId) other;

		return ((this.getOrgAuthId() == castOther.getOrgAuthId()) || (this
				.getOrgAuthId() != null
				&& castOther.getOrgAuthId() != null && this.getOrgAuthId()
				.equals(castOther.getOrgAuthId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getAuthType() == castOther.getAuthType()) || (this
						.getAuthType() != null
						&& castOther.getAuthType() != null && this
						.getAuthType().equals(castOther.getAuthType())))
				&& ((this.getAuthOrg() == castOther.getAuthOrg()) || (this
						.getAuthOrg() != null
						&& castOther.getAuthOrg() != null && this.getAuthOrg()
						.equals(castOther.getAuthOrg())))
				&& ((this.getAuthResult() == castOther.getAuthResult()) || (this
						.getAuthResult() != null
						&& castOther.getAuthResult() != null && this
						.getAuthResult().equals(castOther.getAuthResult())))
				&& ((this.getCertName() == castOther.getCertName()) || (this
						.getCertName() != null
						&& castOther.getCertName() != null && this
						.getCertName().equals(castOther.getCertName())))
				&& ((this.getCertNo() == castOther.getCertNo()) || (this
						.getCertNo() != null
						&& castOther.getCertNo() != null && this.getCertNo()
						.equals(castOther.getCertNo())))
				&& ((this.getAuthDate() == castOther.getAuthDate()) || (this
						.getAuthDate() != null
						&& castOther.getAuthDate() != null && this
						.getAuthDate().equals(castOther.getAuthDate())))
				&& ((this.getValidDate() == castOther.getValidDate()) || (this
						.getValidDate() != null
						&& castOther.getValidDate() != null && this
						.getValidDate().equals(castOther.getValidDate())))
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

		result = 37 * result
				+ (getOrgAuthId() == null ? 0 : this.getOrgAuthId().hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37 * result
				+ (getAuthType() == null ? 0 : this.getAuthType().hashCode());
		result = 37 * result
				+ (getAuthOrg() == null ? 0 : this.getAuthOrg().hashCode());
		result = 37
				* result
				+ (getAuthResult() == null ? 0 : this.getAuthResult()
						.hashCode());
		result = 37 * result
				+ (getCertName() == null ? 0 : this.getCertName().hashCode());
		result = 37 * result
				+ (getCertNo() == null ? 0 : this.getCertNo().hashCode());
		result = 37 * result
				+ (getAuthDate() == null ? 0 : this.getAuthDate().hashCode());
		result = 37 * result
				+ (getValidDate() == null ? 0 : this.getValidDate().hashCode());
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