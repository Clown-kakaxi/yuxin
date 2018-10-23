package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiInterbank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_INTERBANK")
public class MCiInterbank implements java.io.Serializable {

	// Fields

	private String custId;
	private String finaCustType;
	private String bankNo;
	private String bankName;
	private String bankType;
	private String finaOrgType;
	private String finaLicNo;
	private String finaOrgCode;
	private String legalRepr;
	private String zoneCode;
	private String zipCode;
	private String address;
	private String tel;
	private String creditLevel;
	private BigDecimal creditLevelPeriod;
	private String modernPaySysNo;
	private String exchangeNo;
	private String swift;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiInterbank() {
	}

	/** minimal constructor */
	public MCiInterbank(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiInterbank(String custId, String finaCustType, String bankNo,
			String bankName, String bankType, String finaOrgType,
			String finaLicNo, String finaOrgCode, String legalRepr,
			String zoneCode, String zipCode, String address, String tel,
			String creditLevel, BigDecimal creditLevelPeriod,
			String modernPaySysNo, String exchangeNo, String swift,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custId = custId;
		this.finaCustType = finaCustType;
		this.bankNo = bankNo;
		this.bankName = bankName;
		this.bankType = bankType;
		this.finaOrgType = finaOrgType;
		this.finaLicNo = finaLicNo;
		this.finaOrgCode = finaOrgCode;
		this.legalRepr = legalRepr;
		this.zoneCode = zoneCode;
		this.zipCode = zipCode;
		this.address = address;
		this.tel = tel;
		this.creditLevel = creditLevel;
		this.creditLevelPeriod = creditLevelPeriod;
		this.modernPaySysNo = modernPaySysNo;
		this.exchangeNo = exchangeNo;
		this.swift = swift;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
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

	@Column(name = "FINA_CUST_TYPE", length = 20)
	public String getFinaCustType() {
		return this.finaCustType;
	}

	public void setFinaCustType(String finaCustType) {
		this.finaCustType = finaCustType;
	}

	@Column(name = "BANK_NO", length = 20)
	public String getBankNo() {
		return this.bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	@Column(name = "BANK_NAME", length = 60)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_TYPE", length = 20)
	public String getBankType() {
		return this.bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	@Column(name = "FINA_ORG_TYPE", length = 20)
	public String getFinaOrgType() {
		return this.finaOrgType;
	}

	public void setFinaOrgType(String finaOrgType) {
		this.finaOrgType = finaOrgType;
	}

	@Column(name = "FINA_LIC_NO", length = 32)
	public String getFinaLicNo() {
		return this.finaLicNo;
	}

	public void setFinaLicNo(String finaLicNo) {
		this.finaLicNo = finaLicNo;
	}

	@Column(name = "FINA_ORG_CODE", length = 32)
	public String getFinaOrgCode() {
		return this.finaOrgCode;
	}

	public void setFinaOrgCode(String finaOrgCode) {
		this.finaOrgCode = finaOrgCode;
	}

	@Column(name = "LEGAL_REPR", length = 80)
	public String getLegalRepr() {
		return this.legalRepr;
	}

	public void setLegalRepr(String legalRepr) {
		this.legalRepr = legalRepr;
	}

	@Column(name = "ZONE_CODE", length = 20)
	public String getZoneCode() {
		return this.zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	@Column(name = "ZIP_CODE", length = 20)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "CREDIT_LEVEL", length = 80)
	public String getCreditLevel() {
		return this.creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	@Column(name = "CREDIT_LEVEL_PERIOD", precision = 22, scale = 0)
	public BigDecimal getCreditLevelPeriod() {
		return this.creditLevelPeriod;
	}

	public void setCreditLevelPeriod(BigDecimal creditLevelPeriod) {
		this.creditLevelPeriod = creditLevelPeriod;
	}

	@Column(name = "MODERN_PAY_SYS_NO", length = 32)
	public String getModernPaySysNo() {
		return this.modernPaySysNo;
	}

	public void setModernPaySysNo(String modernPaySysNo) {
		this.modernPaySysNo = modernPaySysNo;
	}

	@Column(name = "EXCHANGE_NO", length = 32)
	public String getExchangeNo() {
		return this.exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	@Column(name = "SWIFT", length = 32)
	public String getSwift() {
		return this.swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
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