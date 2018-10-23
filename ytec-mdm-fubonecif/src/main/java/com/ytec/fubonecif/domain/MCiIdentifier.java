package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiIdentifier entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_IDENTIFIER")
public class MCiIdentifier implements java.io.Serializable {

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
	private String isOpenAccIdentLn;
	private String openAccIdentModifiedFlag;
	private Timestamp identModifiedTime;
	private Date verifyDate;
	private String verifyEmployee;
	private String verifyResult;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiIdentifier() {
	}

	/** minimal constructor */
	public MCiIdentifier(String identId) {
		this.identId = identId;
	}

	public MCiIdentifier(String identType, String identNo, String identCustName) {
		this.identType = identType;
		this.identNo = identNo;
		this.identCustName = identCustName;
	}

	/** full constructor */
	public MCiIdentifier(String identId, String custId, String identType, String identNo, String identCustName, String identDesc, String countryOrRegion, String identOrg, String identApproveUnit,
			String identCheckFlag, Date idenRegDate, Date identCheckingDate, Date identCheckedDate, BigDecimal identValidPeriod, Date identEffectiveDate, Date identExpiredDate, String identValidFlag,
			BigDecimal identPeriod, String isOpenAccIdent, String openAccIdentModifiedFlag, Timestamp identModifiedTime, Date verifyDate, String verifyEmployee, String verifyResult,
			String lastUpdateSys, String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo, String isOpenAccIdentLn) {
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
		this.isOpenAccIdentLn = isOpenAccIdentLn;
	}

	// Property accessors
	@Id
	@Column(name = "IDENT_ID", unique = true, nullable = false, length = 20)
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

	@Column(name = "IS_OPEN_ACC_IDENT_LN", length = 1)
	public String getIsOpenAccIdentLn() {
		return isOpenAccIdentLn;
	}

	public void setIsOpenAccIdentLn(String isOpenAccIdentLn) {
		this.isOpenAccIdentLn = isOpenAccIdentLn;
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

}