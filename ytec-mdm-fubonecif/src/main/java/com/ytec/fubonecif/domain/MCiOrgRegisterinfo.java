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
 * MCiOrgRegisterinfo entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "M_CI_ORG_REGISTERINFO")
public class MCiOrgRegisterinfo implements java.io.Serializable {

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
	//add by liuming 20170524
	private String regCodeType;//登记注册号类型(信贷)

	// Constructors

	/** default constructor */
	public MCiOrgRegisterinfo() {
	}

	/** minimal constructor */
	public MCiOrgRegisterinfo(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiOrgRegisterinfo(String custId, String registerNo,
			String registerType, String registerName, String registerStat,
			Date registerDate, Date setupDate, BigDecimal businessLimit,
			Date endDate, String regOrg, String auditCon, Date auditDate,
			Date auditEndDate, String registerCapitalCurr,
			Double registerCapital, String registerComposing,
			String registerNationCode, String registerArea,
			String registerAddr, String registerZipcode, String registerEnAddr,
			String businessScope, String factCapitalCurr, Double factCapital,
			String apprOrg, String apprDocNo, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,String regCodeType) {
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
		//add by liuming 20170524
		this.regCodeType = regCodeType; 
	}

	// Property accessors
	@Id
	@Column(name = "CUST_ID", unique = true, nullable = false, length = 20)
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
	
	@Column(name = "REG_CODE_TYPE", length = 20)
	public String getRegCodeType() {
		return regCodeType;
	}

	public void setRegCodeType(String regCodeType) {
		this.regCodeType = regCodeType;
	}

}