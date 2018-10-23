package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ACRM_F_CI_CUST_IDENTIFIER_LMH database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CUST_IDENTIFIER_LMH")
@NamedQuery(name="AcrmFCiCustIdentifierLmh.findAll", query="SELECT a FROM AcrmFCiCustIdentifierLmh a")
public class AcrmFCiCustIdentifierLmh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDENT_ID")
	private String identId;

	@Column(name="COUNTRY_OR_REGION")
	private String countryOrRegion;

	@Column(name="CUST_ID")
	private String custId;

	@Temporal(TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Temporal(TemporalType.DATE)
	@Column(name="IDEN_REG_DATE")
	private Date idenRegDate;

	@Column(name="IDENT_APPROVE_UNIT")
	private String identApproveUnit;

	@Column(name="IDENT_CHECK_FLAG")
	private String identCheckFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="IDENT_CHECKED_DATE")
	private Date identCheckedDate;

	@Temporal(TemporalType.DATE)
	@Column(name="IDENT_CHECKING_DATE")
	private Date identCheckingDate;

	@Column(name="IDENT_CUST_NAME")
	private String identCustName;

	@Column(name="IDENT_DESC")
	private String identDesc;

	@Temporal(TemporalType.DATE)
	@Column(name="IDENT_EFFECTIVE_DATE")
	private Date identEffectiveDate;

	@Temporal(TemporalType.DATE)
	@Column(name="IDENT_EXPIRED_DATE")
	private Date identExpiredDate;

	@Column(name="IDENT_MODIFIED_TIME")
	private Timestamp identModifiedTime;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_ORG")
	private String identOrg;

	@Column(name="IDENT_PERIOD")
	private BigDecimal identPeriod;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="IDENT_VALID_FLAG")
	private String identValidFlag;

	@Column(name="IDENT_VALID_PERIOD")
	private BigDecimal identValidPeriod;

	@Column(name="IS_OPEN_ACC_IDENT")
	private String isOpenAccIdent;

	@Column(name="IS_OPEN_ACC_IDENT_LN")
	private String isOpenAccIdentLn;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="OPEN_ACC_IDENT_MODIFIED_FLAG")
	private String openAccIdentModifiedFlag;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Temporal(TemporalType.DATE)
	@Column(name="VERIFY_DATE")
	private Date verifyDate;

	@Column(name="VERIFY_EMPLOYEE")
	private String verifyEmployee;

	@Column(name="VERIFY_RESULT")
	private String verifyResult;

	public AcrmFCiCustIdentifierLmh() {
	}

	public String getIdentId() {
		return this.identId;
	}

	public void setIdentId(String identId) {
		this.identId = identId;
	}

	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public Date getIdenRegDate() {
		return this.idenRegDate;
	}

	public void setIdenRegDate(Date idenRegDate) {
		this.idenRegDate = idenRegDate;
	}

	public String getIdentApproveUnit() {
		return this.identApproveUnit;
	}

	public void setIdentApproveUnit(String identApproveUnit) {
		this.identApproveUnit = identApproveUnit;
	}

	public String getIdentCheckFlag() {
		return this.identCheckFlag;
	}

	public void setIdentCheckFlag(String identCheckFlag) {
		this.identCheckFlag = identCheckFlag;
	}

	public Date getIdentCheckedDate() {
		return this.identCheckedDate;
	}

	public void setIdentCheckedDate(Date identCheckedDate) {
		this.identCheckedDate = identCheckedDate;
	}

	public Date getIdentCheckingDate() {
		return this.identCheckingDate;
	}

	public void setIdentCheckingDate(Date identCheckingDate) {
		this.identCheckingDate = identCheckingDate;
	}

	public String getIdentCustName() {
		return this.identCustName;
	}

	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}

	public String getIdentDesc() {
		return this.identDesc;
	}

	public void setIdentDesc(String identDesc) {
		this.identDesc = identDesc;
	}

	public Date getIdentEffectiveDate() {
		return this.identEffectiveDate;
	}

	public void setIdentEffectiveDate(Date identEffectiveDate) {
		this.identEffectiveDate = identEffectiveDate;
	}

	public Date getIdentExpiredDate() {
		return this.identExpiredDate;
	}

	public void setIdentExpiredDate(Date identExpiredDate) {
		this.identExpiredDate = identExpiredDate;
	}

	public Timestamp getIdentModifiedTime() {
		return this.identModifiedTime;
	}

	public void setIdentModifiedTime(Timestamp identModifiedTime) {
		this.identModifiedTime = identModifiedTime;
	}

	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentOrg() {
		return this.identOrg;
	}

	public void setIdentOrg(String identOrg) {
		this.identOrg = identOrg;
	}

	public BigDecimal getIdentPeriod() {
		return this.identPeriod;
	}

	public void setIdentPeriod(BigDecimal identPeriod) {
		this.identPeriod = identPeriod;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIdentValidFlag() {
		return this.identValidFlag;
	}

	public void setIdentValidFlag(String identValidFlag) {
		this.identValidFlag = identValidFlag;
	}

	public BigDecimal getIdentValidPeriod() {
		return this.identValidPeriod;
	}

	public void setIdentValidPeriod(BigDecimal identValidPeriod) {
		this.identValidPeriod = identValidPeriod;
	}

	public String getIsOpenAccIdent() {
		return this.isOpenAccIdent;
	}

	public void setIsOpenAccIdent(String isOpenAccIdent) {
		this.isOpenAccIdent = isOpenAccIdent;
	}

	public String getIsOpenAccIdentLn() {
		return this.isOpenAccIdentLn;
	}

	public void setIsOpenAccIdentLn(String isOpenAccIdentLn) {
		this.isOpenAccIdentLn = isOpenAccIdentLn;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getOpenAccIdentModifiedFlag() {
		return this.openAccIdentModifiedFlag;
	}

	public void setOpenAccIdentModifiedFlag(String openAccIdentModifiedFlag) {
		this.openAccIdentModifiedFlag = openAccIdentModifiedFlag;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public Date getVerifyDate() {
		return this.verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getVerifyEmployee() {
		return this.verifyEmployee;
	}

	public void setVerifyEmployee(String verifyEmployee) {
		this.verifyEmployee = verifyEmployee;
	}

	public String getVerifyResult() {
		return this.verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

}