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
 * MPubBranchInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_PUB_BRANCH_INFO")
public class MPubBranchInfo implements java.io.Serializable {

	// Fields

	private String branchNo;
	private String farenNo;
	private String branchName;
	private String branchShName;
	private String fenhangNo;
	private String branchType;
	private String branchKind;
	private BigDecimal branchLevel;
	private String validFlag;
	private Date startDate;
	private Date endDate;
	private String countryOrRegion;
	private String areaCode;
	private String provinceCode;
	private String cityCode;
	private String countyCode;
	private String branchAddress;
	private String branchZipcode;
	private String branchTel;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MPubBranchInfo() {
	}

	/** minimal constructor */
	public MPubBranchInfo(String branchNo) {
		this.branchNo = branchNo;
	}

	/** full constructor */
	public MPubBranchInfo(String branchNo, String farenNo, String branchName,
			String branchShName, String fenhangNo, String branchType,
			String branchKind, BigDecimal branchLevel, String validFlag,
			Date startDate, Date endDate, String countryOrRegion,
			String areaCode, String provinceCode, String cityCode,
			String countyCode, String branchAddress, String branchZipcode,
			String branchTel, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.branchNo = branchNo;
		this.farenNo = farenNo;
		this.branchName = branchName;
		this.branchShName = branchShName;
		this.fenhangNo = fenhangNo;
		this.branchType = branchType;
		this.branchKind = branchKind;
		this.branchLevel = branchLevel;
		this.validFlag = validFlag;
		this.startDate = startDate;
		this.endDate = endDate;
		this.countryOrRegion = countryOrRegion;
		this.areaCode = areaCode;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.countyCode = countyCode;
		this.branchAddress = branchAddress;
		this.branchZipcode = branchZipcode;
		this.branchTel = branchTel;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "BRANCH_NO", unique = true, nullable = false, length = 20)
	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	@Column(name = "FAREN_NO", length = 20)
	public String getFarenNo() {
		return this.farenNo;
	}

	public void setFarenNo(String farenNo) {
		this.farenNo = farenNo;
	}

	@Column(name = "BRANCH_NAME", length = 40)
	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "BRANCH_SH_NAME", length = 20)
	public String getBranchShName() {
		return this.branchShName;
	}

	public void setBranchShName(String branchShName) {
		this.branchShName = branchShName;
	}

	@Column(name = "FENHANG_NO", length = 20)
	public String getFenhangNo() {
		return this.fenhangNo;
	}

	public void setFenhangNo(String fenhangNo) {
		this.fenhangNo = fenhangNo;
	}

	@Column(name = "BRANCH_TYPE", length = 20)
	public String getBranchType() {
		return this.branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	@Column(name = "BRANCH_KIND", length = 20)
	public String getBranchKind() {
		return this.branchKind;
	}

	public void setBranchKind(String branchKind) {
		this.branchKind = branchKind;
	}

	@Column(name = "BRANCH_LEVEL", precision = 22, scale = 0)
	public BigDecimal getBranchLevel() {
		return this.branchLevel;
	}

	public void setBranchLevel(BigDecimal branchLevel) {
		this.branchLevel = branchLevel;
	}

	@Column(name = "VALID_FLAG", length = 1)
	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "COUNTRY_OR_REGION", length = 20)
	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	@Column(name = "AREA_CODE", length = 20)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "PROVINCE_CODE", length = 20)
	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	@Column(name = "CITY_CODE", length = 20)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "COUNTY_CODE", length = 20)
	public String getCountyCode() {
		return this.countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	@Column(name = "BRANCH_ADDRESS", length = 200)
	public String getBranchAddress() {
		return this.branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	@Column(name = "BRANCH_ZIPCODE", length = 20)
	public String getBranchZipcode() {
		return this.branchZipcode;
	}

	public void setBranchZipcode(String branchZipcode) {
		this.branchZipcode = branchZipcode;
	}

	@Column(name = "BRANCH_TEL", length = 20)
	public String getBranchTel() {
		return this.branchTel;
	}

	public void setBranchTel(String branchTel) {
		this.branchTel = branchTel;
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