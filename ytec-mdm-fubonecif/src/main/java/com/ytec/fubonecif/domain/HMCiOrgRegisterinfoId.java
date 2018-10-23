package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiOrgRegisterinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgRegisterinfoId implements java.io.Serializable {

	// Fields

	private String custId;
	private String registerNo;
	private String registerType;
	private String registerName;
	private String registerStat;
	private Date registerDate;
	private Date setupDate;
	private BigDecimal businessLimit;
	private Date endDate;
	private String regOrg;
	private String auditCon;
	private Date auditDate;
	private Date auditEndDate;
	private String registerCapitalCurr;
	private Double registerCapital;
	private String registerComposing;
	private String registerNationCode;
	private String registerArea;
	private String registerAddr;
	private String registerZipcode;
	private String registerEnAddr;
	private String businessScope;
	private String factCapitalCurr;
	private Double factCapital;
	private String apprOrg;
	private String apprDocNo;
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
	public HMCiOrgRegisterinfoId() {
	}

	/** minimal constructor */
	public HMCiOrgRegisterinfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgRegisterinfoId(String custId, String registerNo,
			String registerType, String registerName, String registerStat,
			Date registerDate, Date setupDate, BigDecimal businessLimit,
			Date endDate, String regOrg, String auditCon, Date auditDate,
			Date auditEndDate, String registerCapitalCurr,
			Double registerCapital, String registerComposing,
			String registerNationCode, String registerArea,
			String registerAddr, String registerZipcode, String registerEnAddr,
			String businessScope, String factCapitalCurr, Double factCapital,
			String apprOrg, String apprDocNo, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.custId = custId;
		this.registerNo = registerNo;
		this.registerType = registerType;
		this.registerName = registerName;
		this.registerStat = registerStat;
		this.registerDate = registerDate;
		this.setupDate = setupDate;
		this.businessLimit = businessLimit;
		this.endDate = endDate;
		this.regOrg = regOrg;
		this.auditCon = auditCon;
		this.auditDate = auditDate;
		this.auditEndDate = auditEndDate;
		this.registerCapitalCurr = registerCapitalCurr;
		this.registerCapital = registerCapital;
		this.registerComposing = registerComposing;
		this.registerNationCode = registerNationCode;
		this.registerArea = registerArea;
		this.registerAddr = registerAddr;
		this.registerZipcode = registerZipcode;
		this.registerEnAddr = registerEnAddr;
		this.businessScope = businessScope;
		this.factCapitalCurr = factCapitalCurr;
		this.factCapital = factCapital;
		this.apprOrg = apprOrg;
		this.apprDocNo = apprDocNo;
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

	@Column(name = "REGISTER_NO", length = 20)
	public String getRegisterNo() {
		return this.registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	@Column(name = "REGISTER_TYPE", length = 20)
	public String getRegisterType() {
		return this.registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	@Column(name = "REGISTER_NAME", length = 80)
	public String getRegisterName() {
		return this.registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	@Column(name = "REGISTER_STAT", length = 20)
	public String getRegisterStat() {
		return this.registerStat;
	}

	public void setRegisterStat(String registerStat) {
		this.registerStat = registerStat;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REGISTER_DATE", length = 7)
	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SETUP_DATE", length = 7)
	public Date getSetupDate() {
		return this.setupDate;
	}

	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	@Column(name = "BUSINESS_LIMIT", precision = 22, scale = 0)
	public BigDecimal getBusinessLimit() {
		return this.businessLimit;
	}

	public void setBusinessLimit(BigDecimal businessLimit) {
		this.businessLimit = businessLimit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "REG_ORG", length = 80)
	public String getRegOrg() {
		return this.regOrg;
	}

	public void setRegOrg(String regOrg) {
		this.regOrg = regOrg;
	}

	@Column(name = "AUDIT_CON", length = 200)
	public String getAuditCon() {
		return this.auditCon;
	}

	public void setAuditCon(String auditCon) {
		this.auditCon = auditCon;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "AUDIT_DATE", length = 7)
	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "AUDIT_END_DATE", length = 7)
	public Date getAuditEndDate() {
		return this.auditEndDate;
	}

	public void setAuditEndDate(Date auditEndDate) {
		this.auditEndDate = auditEndDate;
	}

	@Column(name = "REGISTER_CAPITAL_CURR", length = 20)
	public String getRegisterCapitalCurr() {
		return this.registerCapitalCurr;
	}

	public void setRegisterCapitalCurr(String registerCapitalCurr) {
		this.registerCapitalCurr = registerCapitalCurr;
	}

	@Column(name = "REGISTER_CAPITAL", precision = 17)
	public Double getRegisterCapital() {
		return this.registerCapital;
	}

	public void setRegisterCapital(Double registerCapital) {
		this.registerCapital = registerCapital;
	}

	@Column(name = "REGISTER_COMPOSING", length = 60)
	public String getRegisterComposing() {
		return this.registerComposing;
	}

	public void setRegisterComposing(String registerComposing) {
		this.registerComposing = registerComposing;
	}

	@Column(name = "REGISTER_NATION_CODE", length = 20)
	public String getRegisterNationCode() {
		return this.registerNationCode;
	}

	public void setRegisterNationCode(String registerNationCode) {
		this.registerNationCode = registerNationCode;
	}

	@Column(name = "REGISTER_AREA", length = 20)
	public String getRegisterArea() {
		return this.registerArea;
	}

	public void setRegisterArea(String registerArea) {
		this.registerArea = registerArea;
	}

	@Column(name = "REGISTER_ADDR", length = 200)
	public String getRegisterAddr() {
		return this.registerAddr;
	}

	public void setRegisterAddr(String registerAddr) {
		this.registerAddr = registerAddr;
	}

	@Column(name = "REGISTER_ZIPCODE", length = 20)
	public String getRegisterZipcode() {
		return this.registerZipcode;
	}

	public void setRegisterZipcode(String registerZipcode) {
		this.registerZipcode = registerZipcode;
	}

	@Column(name = "REGISTER_EN_ADDR")
	public String getRegisterEnAddr() {
		return this.registerEnAddr;
	}

	public void setRegisterEnAddr(String registerEnAddr) {
		this.registerEnAddr = registerEnAddr;
	}

	@Column(name = "BUSINESS_SCOPE")
	public String getBusinessScope() {
		return this.businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	@Column(name = "FACT_CAPITAL_CURR", length = 20)
	public String getFactCapitalCurr() {
		return this.factCapitalCurr;
	}

	public void setFactCapitalCurr(String factCapitalCurr) {
		this.factCapitalCurr = factCapitalCurr;
	}

	@Column(name = "FACT_CAPITAL", precision = 17)
	public Double getFactCapital() {
		return this.factCapital;
	}

	public void setFactCapital(Double factCapital) {
		this.factCapital = factCapital;
	}

	@Column(name = "APPR_ORG", length = 80)
	public String getApprOrg() {
		return this.apprOrg;
	}

	public void setApprOrg(String apprOrg) {
		this.apprOrg = apprOrg;
	}

	@Column(name = "APPR_DOC_NO", length = 80)
	public String getApprDocNo() {
		return this.apprDocNo;
	}

	public void setApprDocNo(String apprDocNo) {
		this.apprDocNo = apprDocNo;
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
		if (!(other instanceof HMCiOrgRegisterinfoId))
			return false;
		HMCiOrgRegisterinfoId castOther = (HMCiOrgRegisterinfoId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getRegisterNo() == castOther.getRegisterNo()) || (this
						.getRegisterNo() != null
						&& castOther.getRegisterNo() != null && this
						.getRegisterNo().equals(castOther.getRegisterNo())))
				&& ((this.getRegisterType() == castOther.getRegisterType()) || (this
						.getRegisterType() != null
						&& castOther.getRegisterType() != null && this
						.getRegisterType().equals(castOther.getRegisterType())))
				&& ((this.getRegisterName() == castOther.getRegisterName()) || (this
						.getRegisterName() != null
						&& castOther.getRegisterName() != null && this
						.getRegisterName().equals(castOther.getRegisterName())))
				&& ((this.getRegisterStat() == castOther.getRegisterStat()) || (this
						.getRegisterStat() != null
						&& castOther.getRegisterStat() != null && this
						.getRegisterStat().equals(castOther.getRegisterStat())))
				&& ((this.getRegisterDate() == castOther.getRegisterDate()) || (this
						.getRegisterDate() != null
						&& castOther.getRegisterDate() != null && this
						.getRegisterDate().equals(castOther.getRegisterDate())))
				&& ((this.getSetupDate() == castOther.getSetupDate()) || (this
						.getSetupDate() != null
						&& castOther.getSetupDate() != null && this
						.getSetupDate().equals(castOther.getSetupDate())))
				&& ((this.getBusinessLimit() == castOther.getBusinessLimit()) || (this
						.getBusinessLimit() != null
						&& castOther.getBusinessLimit() != null && this
						.getBusinessLimit()
						.equals(castOther.getBusinessLimit())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this
						.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate()
						.equals(castOther.getEndDate())))
				&& ((this.getRegOrg() == castOther.getRegOrg()) || (this
						.getRegOrg() != null
						&& castOther.getRegOrg() != null && this.getRegOrg()
						.equals(castOther.getRegOrg())))
				&& ((this.getAuditCon() == castOther.getAuditCon()) || (this
						.getAuditCon() != null
						&& castOther.getAuditCon() != null && this
						.getAuditCon().equals(castOther.getAuditCon())))
				&& ((this.getAuditDate() == castOther.getAuditDate()) || (this
						.getAuditDate() != null
						&& castOther.getAuditDate() != null && this
						.getAuditDate().equals(castOther.getAuditDate())))
				&& ((this.getAuditEndDate() == castOther.getAuditEndDate()) || (this
						.getAuditEndDate() != null
						&& castOther.getAuditEndDate() != null && this
						.getAuditEndDate().equals(castOther.getAuditEndDate())))
				&& ((this.getRegisterCapitalCurr() == castOther
						.getRegisterCapitalCurr()) || (this
						.getRegisterCapitalCurr() != null
						&& castOther.getRegisterCapitalCurr() != null && this
						.getRegisterCapitalCurr().equals(
								castOther.getRegisterCapitalCurr())))
				&& ((this.getRegisterCapital() == castOther
						.getRegisterCapital()) || (this.getRegisterCapital() != null
						&& castOther.getRegisterCapital() != null && this
						.getRegisterCapital().equals(
								castOther.getRegisterCapital())))
				&& ((this.getRegisterComposing() == castOther
						.getRegisterComposing()) || (this
						.getRegisterComposing() != null
						&& castOther.getRegisterComposing() != null && this
						.getRegisterComposing().equals(
								castOther.getRegisterComposing())))
				&& ((this.getRegisterNationCode() == castOther
						.getRegisterNationCode()) || (this
						.getRegisterNationCode() != null
						&& castOther.getRegisterNationCode() != null && this
						.getRegisterNationCode().equals(
								castOther.getRegisterNationCode())))
				&& ((this.getRegisterArea() == castOther.getRegisterArea()) || (this
						.getRegisterArea() != null
						&& castOther.getRegisterArea() != null && this
						.getRegisterArea().equals(castOther.getRegisterArea())))
				&& ((this.getRegisterAddr() == castOther.getRegisterAddr()) || (this
						.getRegisterAddr() != null
						&& castOther.getRegisterAddr() != null && this
						.getRegisterAddr().equals(castOther.getRegisterAddr())))
				&& ((this.getRegisterZipcode() == castOther
						.getRegisterZipcode()) || (this.getRegisterZipcode() != null
						&& castOther.getRegisterZipcode() != null && this
						.getRegisterZipcode().equals(
								castOther.getRegisterZipcode())))
				&& ((this.getRegisterEnAddr() == castOther.getRegisterEnAddr()) || (this
						.getRegisterEnAddr() != null
						&& castOther.getRegisterEnAddr() != null && this
						.getRegisterEnAddr().equals(
								castOther.getRegisterEnAddr())))
				&& ((this.getBusinessScope() == castOther.getBusinessScope()) || (this
						.getBusinessScope() != null
						&& castOther.getBusinessScope() != null && this
						.getBusinessScope()
						.equals(castOther.getBusinessScope())))
				&& ((this.getFactCapitalCurr() == castOther
						.getFactCapitalCurr()) || (this.getFactCapitalCurr() != null
						&& castOther.getFactCapitalCurr() != null && this
						.getFactCapitalCurr().equals(
								castOther.getFactCapitalCurr())))
				&& ((this.getFactCapital() == castOther.getFactCapital()) || (this
						.getFactCapital() != null
						&& castOther.getFactCapital() != null && this
						.getFactCapital().equals(castOther.getFactCapital())))
				&& ((this.getApprOrg() == castOther.getApprOrg()) || (this
						.getApprOrg() != null
						&& castOther.getApprOrg() != null && this.getApprOrg()
						.equals(castOther.getApprOrg())))
				&& ((this.getApprDocNo() == castOther.getApprDocNo()) || (this
						.getApprDocNo() != null
						&& castOther.getApprDocNo() != null && this
						.getApprDocNo().equals(castOther.getApprDocNo())))
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
				+ (getRegisterNo() == null ? 0 : this.getRegisterNo()
						.hashCode());
		result = 37
				* result
				+ (getRegisterType() == null ? 0 : this.getRegisterType()
						.hashCode());
		result = 37
				* result
				+ (getRegisterName() == null ? 0 : this.getRegisterName()
						.hashCode());
		result = 37
				* result
				+ (getRegisterStat() == null ? 0 : this.getRegisterStat()
						.hashCode());
		result = 37
				* result
				+ (getRegisterDate() == null ? 0 : this.getRegisterDate()
						.hashCode());
		result = 37 * result
				+ (getSetupDate() == null ? 0 : this.getSetupDate().hashCode());
		result = 37
				* result
				+ (getBusinessLimit() == null ? 0 : this.getBusinessLimit()
						.hashCode());
		result = 37 * result
				+ (getEndDate() == null ? 0 : this.getEndDate().hashCode());
		result = 37 * result
				+ (getRegOrg() == null ? 0 : this.getRegOrg().hashCode());
		result = 37 * result
				+ (getAuditCon() == null ? 0 : this.getAuditCon().hashCode());
		result = 37 * result
				+ (getAuditDate() == null ? 0 : this.getAuditDate().hashCode());
		result = 37
				* result
				+ (getAuditEndDate() == null ? 0 : this.getAuditEndDate()
						.hashCode());
		result = 37
				* result
				+ (getRegisterCapitalCurr() == null ? 0 : this
						.getRegisterCapitalCurr().hashCode());
		result = 37
				* result
				+ (getRegisterCapital() == null ? 0 : this.getRegisterCapital()
						.hashCode());
		result = 37
				* result
				+ (getRegisterComposing() == null ? 0 : this
						.getRegisterComposing().hashCode());
		result = 37
				* result
				+ (getRegisterNationCode() == null ? 0 : this
						.getRegisterNationCode().hashCode());
		result = 37
				* result
				+ (getRegisterArea() == null ? 0 : this.getRegisterArea()
						.hashCode());
		result = 37
				* result
				+ (getRegisterAddr() == null ? 0 : this.getRegisterAddr()
						.hashCode());
		result = 37
				* result
				+ (getRegisterZipcode() == null ? 0 : this.getRegisterZipcode()
						.hashCode());
		result = 37
				* result
				+ (getRegisterEnAddr() == null ? 0 : this.getRegisterEnAddr()
						.hashCode());
		result = 37
				* result
				+ (getBusinessScope() == null ? 0 : this.getBusinessScope()
						.hashCode());
		result = 37
				* result
				+ (getFactCapitalCurr() == null ? 0 : this.getFactCapitalCurr()
						.hashCode());
		result = 37
				* result
				+ (getFactCapital() == null ? 0 : this.getFactCapital()
						.hashCode());
		result = 37 * result
				+ (getApprOrg() == null ? 0 : this.getApprOrg().hashCode());
		result = 37 * result
				+ (getApprDocNo() == null ? 0 : this.getApprDocNo().hashCode());
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