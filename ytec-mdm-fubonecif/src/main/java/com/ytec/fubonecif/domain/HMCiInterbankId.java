package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiInterbankId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiInterbankId implements java.io.Serializable {

	// Fields

	private BigDecimal custId;
	private String finaCustType;
	private String bankNo;
	private String bankName;
	private String bankType;
	private String orgCode;
	private String busiLicNo;
	private String finaOrgType;
	private String finaLicNo;
	private String finaOrgCode;
	private String nationalTax;
	private String localTax;
	private String legalRepr;
	private String zoneCode;
	private String zipCode;
	private String address;
	private String tel;
	private String creditLevel;
	private Date creditLevelPeriod;
	private String modernPaySysNo;
	private String exchangeNo;
	private String swift;
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
	public HMCiInterbankId() {
	}

	/** minimal constructor */
	public HMCiInterbankId(BigDecimal custId, Timestamp hisOperTime) {
		this.custId = custId;
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiInterbankId(BigDecimal custId, String finaCustType,
			String bankNo, String bankName, String bankType, String orgCode,
			String busiLicNo, String finaOrgType, String finaLicNo,
			String finaOrgCode, String nationalTax, String localTax,
			String legalRepr, String zoneCode, String zipCode, String address,
			String tel, String creditLevel, Date creditLevelPeriod,
			String modernPaySysNo, String exchangeNo, String swift,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.custId = custId;
		this.finaCustType = finaCustType;
		this.bankNo = bankNo;
		this.bankName = bankName;
		this.bankType = bankType;
		this.orgCode = orgCode;
		this.busiLicNo = busiLicNo;
		this.finaOrgType = finaOrgType;
		this.finaLicNo = finaLicNo;
		this.finaOrgCode = finaOrgCode;
		this.nationalTax = nationalTax;
		this.localTax = localTax;
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "CUST_ID", nullable = false, scale = 0)
	public BigDecimal getCustId() {
		return this.custId;
	}

	public void setCustId(BigDecimal custId) {
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

	@Column(name = "ORG_CODE", length = 32)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "BUSI_LIC_NO", length = 32)
	public String getBusiLicNo() {
		return this.busiLicNo;
	}

	public void setBusiLicNo(String busiLicNo) {
		this.busiLicNo = busiLicNo;
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

	@Column(name = "NATIONAL_TAX", length = 32)
	public String getNationalTax() {
		return this.nationalTax;
	}

	public void setNationalTax(String nationalTax) {
		this.nationalTax = nationalTax;
	}

	@Column(name = "LOCAL_TAX", length = 32)
	public String getLocalTax() {
		return this.localTax;
	}

	public void setLocalTax(String localTax) {
		this.localTax = localTax;
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

	@Column(name = "ZIP_CODE", length = 32)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "ADDRESS", length = 80)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "TEL", length = 32)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "CREDIT_LEVEL_PERIOD", length = 7)
	public Date getCreditLevelPeriod() {
		return this.creditLevelPeriod;
	}

	public void setCreditLevelPeriod(Date creditLevelPeriod) {
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
		if (!(other instanceof HMCiInterbankId))
			return false;
		HMCiInterbankId castOther = (HMCiInterbankId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getFinaCustType() == castOther.getFinaCustType()) || (this
						.getFinaCustType() != null
						&& castOther.getFinaCustType() != null && this
						.getFinaCustType().equals(castOther.getFinaCustType())))
				&& ((this.getBankNo() == castOther.getBankNo()) || (this
						.getBankNo() != null
						&& castOther.getBankNo() != null && this.getBankNo()
						.equals(castOther.getBankNo())))
				&& ((this.getBankName() == castOther.getBankName()) || (this
						.getBankName() != null
						&& castOther.getBankName() != null && this
						.getBankName().equals(castOther.getBankName())))
				&& ((this.getBankType() == castOther.getBankType()) || (this
						.getBankType() != null
						&& castOther.getBankType() != null && this
						.getBankType().equals(castOther.getBankType())))
				&& ((this.getOrgCode() == castOther.getOrgCode()) || (this
						.getOrgCode() != null
						&& castOther.getOrgCode() != null && this.getOrgCode()
						.equals(castOther.getOrgCode())))
				&& ((this.getBusiLicNo() == castOther.getBusiLicNo()) || (this
						.getBusiLicNo() != null
						&& castOther.getBusiLicNo() != null && this
						.getBusiLicNo().equals(castOther.getBusiLicNo())))
				&& ((this.getFinaOrgType() == castOther.getFinaOrgType()) || (this
						.getFinaOrgType() != null
						&& castOther.getFinaOrgType() != null && this
						.getFinaOrgType().equals(castOther.getFinaOrgType())))
				&& ((this.getFinaLicNo() == castOther.getFinaLicNo()) || (this
						.getFinaLicNo() != null
						&& castOther.getFinaLicNo() != null && this
						.getFinaLicNo().equals(castOther.getFinaLicNo())))
				&& ((this.getFinaOrgCode() == castOther.getFinaOrgCode()) || (this
						.getFinaOrgCode() != null
						&& castOther.getFinaOrgCode() != null && this
						.getFinaOrgCode().equals(castOther.getFinaOrgCode())))
				&& ((this.getNationalTax() == castOther.getNationalTax()) || (this
						.getNationalTax() != null
						&& castOther.getNationalTax() != null && this
						.getNationalTax().equals(castOther.getNationalTax())))
				&& ((this.getLocalTax() == castOther.getLocalTax()) || (this
						.getLocalTax() != null
						&& castOther.getLocalTax() != null && this
						.getLocalTax().equals(castOther.getLocalTax())))
				&& ((this.getLegalRepr() == castOther.getLegalRepr()) || (this
						.getLegalRepr() != null
						&& castOther.getLegalRepr() != null && this
						.getLegalRepr().equals(castOther.getLegalRepr())))
				&& ((this.getZoneCode() == castOther.getZoneCode()) || (this
						.getZoneCode() != null
						&& castOther.getZoneCode() != null && this
						.getZoneCode().equals(castOther.getZoneCode())))
				&& ((this.getZipCode() == castOther.getZipCode()) || (this
						.getZipCode() != null
						&& castOther.getZipCode() != null && this.getZipCode()
						.equals(castOther.getZipCode())))
				&& ((this.getAddress() == castOther.getAddress()) || (this
						.getAddress() != null
						&& castOther.getAddress() != null && this.getAddress()
						.equals(castOther.getAddress())))
				&& ((this.getTel() == castOther.getTel()) || (this.getTel() != null
						&& castOther.getTel() != null && this.getTel().equals(
						castOther.getTel())))
				&& ((this.getCreditLevel() == castOther.getCreditLevel()) || (this
						.getCreditLevel() != null
						&& castOther.getCreditLevel() != null && this
						.getCreditLevel().equals(castOther.getCreditLevel())))
				&& ((this.getCreditLevelPeriod() == castOther
						.getCreditLevelPeriod()) || (this
						.getCreditLevelPeriod() != null
						&& castOther.getCreditLevelPeriod() != null && this
						.getCreditLevelPeriod().equals(
								castOther.getCreditLevelPeriod())))
				&& ((this.getModernPaySysNo() == castOther.getModernPaySysNo()) || (this
						.getModernPaySysNo() != null
						&& castOther.getModernPaySysNo() != null && this
						.getModernPaySysNo().equals(
								castOther.getModernPaySysNo())))
				&& ((this.getExchangeNo() == castOther.getExchangeNo()) || (this
						.getExchangeNo() != null
						&& castOther.getExchangeNo() != null && this
						.getExchangeNo().equals(castOther.getExchangeNo())))
				&& ((this.getSwift() == castOther.getSwift()) || (this
						.getSwift() != null
						&& castOther.getSwift() != null && this.getSwift()
						.equals(castOther.getSwift())))
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
				+ (getFinaCustType() == null ? 0 : this.getFinaCustType()
						.hashCode());
		result = 37 * result
				+ (getBankNo() == null ? 0 : this.getBankNo().hashCode());
		result = 37 * result
				+ (getBankName() == null ? 0 : this.getBankName().hashCode());
		result = 37 * result
				+ (getBankType() == null ? 0 : this.getBankType().hashCode());
		result = 37 * result
				+ (getOrgCode() == null ? 0 : this.getOrgCode().hashCode());
		result = 37 * result
				+ (getBusiLicNo() == null ? 0 : this.getBusiLicNo().hashCode());
		result = 37
				* result
				+ (getFinaOrgType() == null ? 0 : this.getFinaOrgType()
						.hashCode());
		result = 37 * result
				+ (getFinaLicNo() == null ? 0 : this.getFinaLicNo().hashCode());
		result = 37
				* result
				+ (getFinaOrgCode() == null ? 0 : this.getFinaOrgCode()
						.hashCode());
		result = 37
				* result
				+ (getNationalTax() == null ? 0 : this.getNationalTax()
						.hashCode());
		result = 37 * result
				+ (getLocalTax() == null ? 0 : this.getLocalTax().hashCode());
		result = 37 * result
				+ (getLegalRepr() == null ? 0 : this.getLegalRepr().hashCode());
		result = 37 * result
				+ (getZoneCode() == null ? 0 : this.getZoneCode().hashCode());
		result = 37 * result
				+ (getZipCode() == null ? 0 : this.getZipCode().hashCode());
		result = 37 * result
				+ (getAddress() == null ? 0 : this.getAddress().hashCode());
		result = 37 * result
				+ (getTel() == null ? 0 : this.getTel().hashCode());
		result = 37
				* result
				+ (getCreditLevel() == null ? 0 : this.getCreditLevel()
						.hashCode());
		result = 37
				* result
				+ (getCreditLevelPeriod() == null ? 0 : this
						.getCreditLevelPeriod().hashCode());
		result = 37
				* result
				+ (getModernPaySysNo() == null ? 0 : this.getModernPaySysNo()
						.hashCode());
		result = 37
				* result
				+ (getExchangeNo() == null ? 0 : this.getExchangeNo()
						.hashCode());
		result = 37 * result
				+ (getSwift() == null ? 0 : this.getSwift().hashCode());
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