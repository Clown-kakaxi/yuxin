package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
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
 * The persistent class for the ACRM_F_CI_ORG_REGISTERINFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ORG_REGISTERINFO")
public class AcrmFCiOrgRegisterinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="APPR_DOC_NO")
	private String apprDocNo;

	@Column(name="APPR_ORG")
	private String apprOrg;

	@Column(name="AUDIT_CON")
	private String auditCon;

	@Temporal(TemporalType.DATE)
	@Column(name="AUDIT_DATE")
	private Date auditDate;

	@Temporal(TemporalType.DATE)
	@Column(name="AUDIT_END_DATE")
	private Date auditEndDate;

	@Column(name="BUSINESS_LIMIT")
	private BigDecimal businessLimit;

	@Column(name="BUSINESS_SCOPE")
	private String businessScope;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="FACT_CAPITAL")
	private BigDecimal factCapital;

	@Column(name="FACT_CAPITAL_CURR")
	private String factCapitalCurr;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="REG_ORG")
	private String regOrg;

	@Column(name="REGISTER_ADDR")
	private String registerAddr;

	@Column(name="REGISTER_AREA")
	private String registerArea;

	@Column(name="REGISTER_CAPITAL")
	private BigDecimal registerCapital;

	@Column(name="REGISTER_CAPITAL_CURR")
	private String registerCapitalCurr;

	@Column(name="REGISTER_COMPOSING")
	private String registerComposing;

	@Temporal(TemporalType.DATE)
	@Column(name="REGISTER_DATE")
	private Date registerDate;

	@Column(name="REGISTER_EN_ADDR")
	private String registerEnAddr;

	@Column(name="REGISTER_NAME")
	private String registerName;

	@Column(name="REGISTER_NATION_CODE")
	private String registerNationCode;

	@Column(name="REGISTER_NO")
	private String registerNo;

	@Column(name="REGISTER_STAT")
	private String registerStat;

	@Column(name="REGISTER_TYPE")
	private String registerType;

	@Column(name="REGISTER_ZIPCODE")
	private String registerZipcode;

	@Temporal(TemporalType.DATE)
	@Column(name="SETUP_DATE")
	private Date setupDate;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	public AcrmFCiOrgRegisterinfo() {
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getApprDocNo() {
		return this.apprDocNo;
	}

	public void setApprDocNo(String apprDocNo) {
		this.apprDocNo = apprDocNo;
	}

	public String getApprOrg() {
		return this.apprOrg;
	}

	public void setApprOrg(String apprOrg) {
		this.apprOrg = apprOrg;
	}

	public String getAuditCon() {
		return this.auditCon;
	}

	public void setAuditCon(String auditCon) {
		this.auditCon = auditCon;
	}

	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Date getAuditEndDate() {
		return this.auditEndDate;
	}

	public void setAuditEndDate(Date auditEndDate) {
		this.auditEndDate = auditEndDate;
	}

	public BigDecimal getBusinessLimit() {
		return this.businessLimit;
	}

	public void setBusinessLimit(BigDecimal businessLimit) {
		this.businessLimit = businessLimit;
	}

	public String getBusinessScope() {
		return this.businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getFactCapital() {
		return this.factCapital;
	}

	public void setFactCapital(BigDecimal factCapital) {
		this.factCapital = factCapital;
	}

	public String getFactCapitalCurr() {
		return this.factCapitalCurr;
	}

	public void setFactCapitalCurr(String factCapitalCurr) {
		this.factCapitalCurr = factCapitalCurr;
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

	public String getRegOrg() {
		return this.regOrg;
	}

	public void setRegOrg(String regOrg) {
		this.regOrg = regOrg;
	}

	public String getRegisterAddr() {
		return this.registerAddr;
	}

	public void setRegisterAddr(String registerAddr) {
		this.registerAddr = registerAddr;
	}

	public String getRegisterArea() {
		return this.registerArea;
	}

	public void setRegisterArea(String registerArea) {
		this.registerArea = registerArea;
	}

	public BigDecimal getRegisterCapital() {
		return this.registerCapital;
	}

	public void setRegisterCapital(BigDecimal registerCapital) {
		this.registerCapital = registerCapital;
	}

	public String getRegisterCapitalCurr() {
		return this.registerCapitalCurr;
	}

	public void setRegisterCapitalCurr(String registerCapitalCurr) {
		this.registerCapitalCurr = registerCapitalCurr;
	}

	public String getRegisterComposing() {
		return this.registerComposing;
	}

	public void setRegisterComposing(String registerComposing) {
		this.registerComposing = registerComposing;
	}

	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getRegisterEnAddr() {
		return this.registerEnAddr;
	}

	public void setRegisterEnAddr(String registerEnAddr) {
		this.registerEnAddr = registerEnAddr;
	}

	public String getRegisterName() {
		return this.registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterNationCode() {
		return this.registerNationCode;
	}

	public void setRegisterNationCode(String registerNationCode) {
		this.registerNationCode = registerNationCode;
	}

	public String getRegisterNo() {
		return this.registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getRegisterStat() {
		return this.registerStat;
	}

	public void setRegisterStat(String registerStat) {
		this.registerStat = registerStat;
	}

	public String getRegisterType() {
		return this.registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getRegisterZipcode() {
		return this.registerZipcode;
	}

	public void setRegisterZipcode(String registerZipcode) {
		this.registerZipcode = registerZipcode;
	}

	public Date getSetupDate() {
		return this.setupDate;
	}

	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}