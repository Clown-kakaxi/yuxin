package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiIdentifierId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiIdentifierId implements java.io.Serializable {

	// Fields

	private String identId;
	private String custId;
	private String identType;
	private String identNo;
	private String identCustName;
	private String identDesc;
	private String countryOrRegion;
	private String identOrg;
	private String identApproveUnit;
	private String identCheckFlag;
	private Date idenRegDate;
	private Date identCheckingDate;
	private Date identCheckedDate;
	private BigDecimal identValidPeriod;
	private Date identEffectiveDate;
	private Date identExpiredDate;
	private String identValidFlag;
	private BigDecimal identPeriod;
	private String isOpenAccIdent;
	private String openAccIdentModifiedFlag;
	private Timestamp identModifiedTime;
	private Date verifyDate;
	private String verifyEmployee;
	private String verifyResult;
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
	public HMCiIdentifierId() {
	}

	/** minimal constructor */
	public HMCiIdentifierId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiIdentifierId(String identId, String custId, String identType,
			String identNo, String identCustName, String identDesc,
			String countryOrRegion, String identOrg, String identApproveUnit,
			String identCheckFlag, Date idenRegDate, Date identCheckingDate,
			Date identCheckedDate, BigDecimal identValidPeriod,
			Date identEffectiveDate, Date identExpiredDate,
			String identValidFlag, BigDecimal identPeriod,
			String isOpenAccIdent, String openAccIdentModifiedFlag,
			Timestamp identModifiedTime, Date verifyDate,
			String verifyEmployee, String verifyResult, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.identId = identId;
		this.custId = custId;
		this.identType = identType;
		this.identNo = identNo;
		this.identCustName = identCustName;
		this.identDesc = identDesc;
		this.countryOrRegion = countryOrRegion;
		this.identOrg = identOrg;
		this.identApproveUnit = identApproveUnit;
		this.identCheckFlag = identCheckFlag;
		this.idenRegDate = idenRegDate;
		this.identCheckingDate = identCheckingDate;
		this.identCheckedDate = identCheckedDate;
		this.identValidPeriod = identValidPeriod;
		this.identEffectiveDate = identEffectiveDate;
		this.identExpiredDate = identExpiredDate;
		this.identValidFlag = identValidFlag;
		this.identPeriod = identPeriod;
		this.isOpenAccIdent = isOpenAccIdent;
		this.openAccIdentModifiedFlag = openAccIdentModifiedFlag;
		this.identModifiedTime = identModifiedTime;
		this.verifyDate = verifyDate;
		this.verifyEmployee = verifyEmployee;
		this.verifyResult = verifyResult;
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

	@Column(name = "IDENT_ID", length = 20)
	public String getIdentId() {
		return this.identId;
	}

	public void setIdentId(String identId) {
		this.identId = identId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	@Column(name = "IDENT_CUST_NAME", length = 70)
	public String getIdentCustName() {
		return this.identCustName;
	}

	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}

	@Column(name = "IDENT_DESC", length = 80)
	public String getIdentDesc() {
		return this.identDesc;
	}

	public void setIdentDesc(String identDesc) {
		this.identDesc = identDesc;
	}

	@Column(name = "COUNTRY_OR_REGION", length = 20)
	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	@Column(name = "IDENT_ORG", length = 40)
	public String getIdentOrg() {
		return this.identOrg;
	}

	public void setIdentOrg(String identOrg) {
		this.identOrg = identOrg;
	}

	@Column(name = "IDENT_APPROVE_UNIT", length = 40)
	public String getIdentApproveUnit() {
		return this.identApproveUnit;
	}

	public void setIdentApproveUnit(String identApproveUnit) {
		this.identApproveUnit = identApproveUnit;
	}

	@Column(name = "IDENT_CHECK_FLAG", length = 20)
	public String getIdentCheckFlag() {
		return this.identCheckFlag;
	}

	public void setIdentCheckFlag(String identCheckFlag) {
		this.identCheckFlag = identCheckFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "IDEN_REG_DATE", length = 7)
	public Date getIdenRegDate() {
		return this.idenRegDate;
	}

	public void setIdenRegDate(Date idenRegDate) {
		this.idenRegDate = idenRegDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "IDENT_CHECKING_DATE", length = 7)
	public Date getIdentCheckingDate() {
		return this.identCheckingDate;
	}

	public void setIdentCheckingDate(Date identCheckingDate) {
		this.identCheckingDate = identCheckingDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "IDENT_CHECKED_DATE", length = 7)
	public Date getIdentCheckedDate() {
		return this.identCheckedDate;
	}

	public void setIdentCheckedDate(Date identCheckedDate) {
		this.identCheckedDate = identCheckedDate;
	}

	@Column(name = "IDENT_VALID_PERIOD", precision = 22, scale = 0)
	public BigDecimal getIdentValidPeriod() {
		return this.identValidPeriod;
	}

	public void setIdentValidPeriod(BigDecimal identValidPeriod) {
		this.identValidPeriod = identValidPeriod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "IDENT_EFFECTIVE_DATE", length = 7)
	public Date getIdentEffectiveDate() {
		return this.identEffectiveDate;
	}

	public void setIdentEffectiveDate(Date identEffectiveDate) {
		this.identEffectiveDate = identEffectiveDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "IDENT_EXPIRED_DATE", length = 7)
	public Date getIdentExpiredDate() {
		return this.identExpiredDate;
	}

	public void setIdentExpiredDate(Date identExpiredDate) {
		this.identExpiredDate = identExpiredDate;
	}

	@Column(name = "IDENT_VALID_FLAG", length = 1)
	public String getIdentValidFlag() {
		return this.identValidFlag;
	}

	public void setIdentValidFlag(String identValidFlag) {
		this.identValidFlag = identValidFlag;
	}

	@Column(name = "IDENT_PERIOD", precision = 22, scale = 0)
	public BigDecimal getIdentPeriod() {
		return this.identPeriod;
	}

	public void setIdentPeriod(BigDecimal identPeriod) {
		this.identPeriod = identPeriod;
	}

	@Column(name = "IS_OPEN_ACC_IDENT", length = 1)
	public String getIsOpenAccIdent() {
		return this.isOpenAccIdent;
	}

	public void setIsOpenAccIdent(String isOpenAccIdent) {
		this.isOpenAccIdent = isOpenAccIdent;
	}

	@Column(name = "OPEN_ACC_IDENT_MODIFIED_FLAG", length = 1)
	public String getOpenAccIdentModifiedFlag() {
		return this.openAccIdentModifiedFlag;
	}

	public void setOpenAccIdentModifiedFlag(String openAccIdentModifiedFlag) {
		this.openAccIdentModifiedFlag = openAccIdentModifiedFlag;
	}

	@Column(name = "IDENT_MODIFIED_TIME", length = 11)
	public Timestamp getIdentModifiedTime() {
		return this.identModifiedTime;
	}

	public void setIdentModifiedTime(Timestamp identModifiedTime) {
		this.identModifiedTime = identModifiedTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VERIFY_DATE", length = 7)
	public Date getVerifyDate() {
		return this.verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	@Column(name = "VERIFY_EMPLOYEE", length = 20)
	public String getVerifyEmployee() {
		return this.verifyEmployee;
	}

	public void setVerifyEmployee(String verifyEmployee) {
		this.verifyEmployee = verifyEmployee;
	}

	@Column(name = "VERIFY_RESULT", length = 20)
	public String getVerifyResult() {
		return this.verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
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
		if (!(other instanceof HMCiIdentifierId))
			return false;
		HMCiIdentifierId castOther = (HMCiIdentifierId) other;

		return ((this.getIdentId() == castOther.getIdentId()) || (this
				.getIdentId() != null
				&& castOther.getIdentId() != null && this.getIdentId().equals(
				castOther.getIdentId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getIdentCustName() == castOther.getIdentCustName()) || (this
						.getIdentCustName() != null
						&& castOther.getIdentCustName() != null && this
						.getIdentCustName()
						.equals(castOther.getIdentCustName())))
				&& ((this.getIdentDesc() == castOther.getIdentDesc()) || (this
						.getIdentDesc() != null
						&& castOther.getIdentDesc() != null && this
						.getIdentDesc().equals(castOther.getIdentDesc())))
				&& ((this.getCountryOrRegion() == castOther
						.getCountryOrRegion()) || (this.getCountryOrRegion() != null
						&& castOther.getCountryOrRegion() != null && this
						.getCountryOrRegion().equals(
								castOther.getCountryOrRegion())))
				&& ((this.getIdentOrg() == castOther.getIdentOrg()) || (this
						.getIdentOrg() != null
						&& castOther.getIdentOrg() != null && this
						.getIdentOrg().equals(castOther.getIdentOrg())))
				&& ((this.getIdentApproveUnit() == castOther
						.getIdentApproveUnit()) || (this.getIdentApproveUnit() != null
						&& castOther.getIdentApproveUnit() != null && this
						.getIdentApproveUnit().equals(
								castOther.getIdentApproveUnit())))
				&& ((this.getIdentCheckFlag() == castOther.getIdentCheckFlag()) || (this
						.getIdentCheckFlag() != null
						&& castOther.getIdentCheckFlag() != null && this
						.getIdentCheckFlag().equals(
								castOther.getIdentCheckFlag())))
				&& ((this.getIdenRegDate() == castOther.getIdenRegDate()) || (this
						.getIdenRegDate() != null
						&& castOther.getIdenRegDate() != null && this
						.getIdenRegDate().equals(castOther.getIdenRegDate())))
				&& ((this.getIdentCheckingDate() == castOther
						.getIdentCheckingDate()) || (this
						.getIdentCheckingDate() != null
						&& castOther.getIdentCheckingDate() != null && this
						.getIdentCheckingDate().equals(
								castOther.getIdentCheckingDate())))
				&& ((this.getIdentCheckedDate() == castOther
						.getIdentCheckedDate()) || (this.getIdentCheckedDate() != null
						&& castOther.getIdentCheckedDate() != null && this
						.getIdentCheckedDate().equals(
								castOther.getIdentCheckedDate())))
				&& ((this.getIdentValidPeriod() == castOther
						.getIdentValidPeriod()) || (this.getIdentValidPeriod() != null
						&& castOther.getIdentValidPeriod() != null && this
						.getIdentValidPeriod().equals(
								castOther.getIdentValidPeriod())))
				&& ((this.getIdentEffectiveDate() == castOther
						.getIdentEffectiveDate()) || (this
						.getIdentEffectiveDate() != null
						&& castOther.getIdentEffectiveDate() != null && this
						.getIdentEffectiveDate().equals(
								castOther.getIdentEffectiveDate())))
				&& ((this.getIdentExpiredDate() == castOther
						.getIdentExpiredDate()) || (this.getIdentExpiredDate() != null
						&& castOther.getIdentExpiredDate() != null && this
						.getIdentExpiredDate().equals(
								castOther.getIdentExpiredDate())))
				&& ((this.getIdentValidFlag() == castOther.getIdentValidFlag()) || (this
						.getIdentValidFlag() != null
						&& castOther.getIdentValidFlag() != null && this
						.getIdentValidFlag().equals(
								castOther.getIdentValidFlag())))
				&& ((this.getIdentPeriod() == castOther.getIdentPeriod()) || (this
						.getIdentPeriod() != null
						&& castOther.getIdentPeriod() != null && this
						.getIdentPeriod().equals(castOther.getIdentPeriod())))
				&& ((this.getIsOpenAccIdent() == castOther.getIsOpenAccIdent()) || (this
						.getIsOpenAccIdent() != null
						&& castOther.getIsOpenAccIdent() != null && this
						.getIsOpenAccIdent().equals(
								castOther.getIsOpenAccIdent())))
				&& ((this.getOpenAccIdentModifiedFlag() == castOther
						.getOpenAccIdentModifiedFlag()) || (this
						.getOpenAccIdentModifiedFlag() != null
						&& castOther.getOpenAccIdentModifiedFlag() != null && this
						.getOpenAccIdentModifiedFlag().equals(
								castOther.getOpenAccIdentModifiedFlag())))
				&& ((this.getIdentModifiedTime() == castOther
						.getIdentModifiedTime()) || (this
						.getIdentModifiedTime() != null
						&& castOther.getIdentModifiedTime() != null && this
						.getIdentModifiedTime().equals(
								castOther.getIdentModifiedTime())))
				&& ((this.getVerifyDate() == castOther.getVerifyDate()) || (this
						.getVerifyDate() != null
						&& castOther.getVerifyDate() != null && this
						.getVerifyDate().equals(castOther.getVerifyDate())))
				&& ((this.getVerifyEmployee() == castOther.getVerifyEmployee()) || (this
						.getVerifyEmployee() != null
						&& castOther.getVerifyEmployee() != null && this
						.getVerifyEmployee().equals(
								castOther.getVerifyEmployee())))
				&& ((this.getVerifyResult() == castOther.getVerifyResult()) || (this
						.getVerifyResult() != null
						&& castOther.getVerifyResult() != null && this
						.getVerifyResult().equals(castOther.getVerifyResult())))
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
				+ (getIdentId() == null ? 0 : this.getIdentId().hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37
				* result
				+ (getIdentCustName() == null ? 0 : this.getIdentCustName()
						.hashCode());
		result = 37 * result
				+ (getIdentDesc() == null ? 0 : this.getIdentDesc().hashCode());
		result = 37
				* result
				+ (getCountryOrRegion() == null ? 0 : this.getCountryOrRegion()
						.hashCode());
		result = 37 * result
				+ (getIdentOrg() == null ? 0 : this.getIdentOrg().hashCode());
		result = 37
				* result
				+ (getIdentApproveUnit() == null ? 0 : this
						.getIdentApproveUnit().hashCode());
		result = 37
				* result
				+ (getIdentCheckFlag() == null ? 0 : this.getIdentCheckFlag()
						.hashCode());
		result = 37
				* result
				+ (getIdenRegDate() == null ? 0 : this.getIdenRegDate()
						.hashCode());
		result = 37
				* result
				+ (getIdentCheckingDate() == null ? 0 : this
						.getIdentCheckingDate().hashCode());
		result = 37
				* result
				+ (getIdentCheckedDate() == null ? 0 : this
						.getIdentCheckedDate().hashCode());
		result = 37
				* result
				+ (getIdentValidPeriod() == null ? 0 : this
						.getIdentValidPeriod().hashCode());
		result = 37
				* result
				+ (getIdentEffectiveDate() == null ? 0 : this
						.getIdentEffectiveDate().hashCode());
		result = 37
				* result
				+ (getIdentExpiredDate() == null ? 0 : this
						.getIdentExpiredDate().hashCode());
		result = 37
				* result
				+ (getIdentValidFlag() == null ? 0 : this.getIdentValidFlag()
						.hashCode());
		result = 37
				* result
				+ (getIdentPeriod() == null ? 0 : this.getIdentPeriod()
						.hashCode());
		result = 37
				* result
				+ (getIsOpenAccIdent() == null ? 0 : this.getIsOpenAccIdent()
						.hashCode());
		result = 37
				* result
				+ (getOpenAccIdentModifiedFlag() == null ? 0 : this
						.getOpenAccIdentModifiedFlag().hashCode());
		result = 37
				* result
				+ (getIdentModifiedTime() == null ? 0 : this
						.getIdentModifiedTime().hashCode());
		result = 37
				* result
				+ (getVerifyDate() == null ? 0 : this.getVerifyDate()
						.hashCode());
		result = 37
				* result
				+ (getVerifyEmployee() == null ? 0 : this.getVerifyEmployee()
						.hashCode());
		result = 37
				* result
				+ (getVerifyResult() == null ? 0 : this.getVerifyResult()
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