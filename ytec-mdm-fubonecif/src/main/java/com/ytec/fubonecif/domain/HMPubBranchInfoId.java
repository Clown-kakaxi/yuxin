package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMPubBranchInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMPubBranchInfoId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMPubBranchInfoId() {
	}

	/** minimal constructor */
	public HMPubBranchInfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMPubBranchInfoId(String branchNo, String farenNo,
			String branchName, String branchShName, String fenhangNo,
			String branchType, String branchKind, BigDecimal branchLevel,
			String validFlag, Date startDate, Date endDate,
			String countryOrRegion, String areaCode, String provinceCode,
			String cityCode, String countyCode, String branchAddress,
			String branchZipcode, String branchTel, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "BRANCH_NO", length = 20)
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
		if (!(other instanceof HMPubBranchInfoId))
			return false;
		HMPubBranchInfoId castOther = (HMPubBranchInfoId) other;

		return ((this.getBranchNo() == castOther.getBranchNo()) || (this
				.getBranchNo() != null
				&& castOther.getBranchNo() != null && this.getBranchNo()
				.equals(castOther.getBranchNo())))
				&& ((this.getFarenNo() == castOther.getFarenNo()) || (this
						.getFarenNo() != null
						&& castOther.getFarenNo() != null && this.getFarenNo()
						.equals(castOther.getFarenNo())))
				&& ((this.getBranchName() == castOther.getBranchName()) || (this
						.getBranchName() != null
						&& castOther.getBranchName() != null && this
						.getBranchName().equals(castOther.getBranchName())))
				&& ((this.getBranchShName() == castOther.getBranchShName()) || (this
						.getBranchShName() != null
						&& castOther.getBranchShName() != null && this
						.getBranchShName().equals(castOther.getBranchShName())))
				&& ((this.getFenhangNo() == castOther.getFenhangNo()) || (this
						.getFenhangNo() != null
						&& castOther.getFenhangNo() != null && this
						.getFenhangNo().equals(castOther.getFenhangNo())))
				&& ((this.getBranchType() == castOther.getBranchType()) || (this
						.getBranchType() != null
						&& castOther.getBranchType() != null && this
						.getBranchType().equals(castOther.getBranchType())))
				&& ((this.getBranchKind() == castOther.getBranchKind()) || (this
						.getBranchKind() != null
						&& castOther.getBranchKind() != null && this
						.getBranchKind().equals(castOther.getBranchKind())))
				&& ((this.getBranchLevel() == castOther.getBranchLevel()) || (this
						.getBranchLevel() != null
						&& castOther.getBranchLevel() != null && this
						.getBranchLevel().equals(castOther.getBranchLevel())))
				&& ((this.getValidFlag() == castOther.getValidFlag()) || (this
						.getValidFlag() != null
						&& castOther.getValidFlag() != null && this
						.getValidFlag().equals(castOther.getValidFlag())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this
						.getStartDate() != null
						&& castOther.getStartDate() != null && this
						.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this
						.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate()
						.equals(castOther.getEndDate())))
				&& ((this.getCountryOrRegion() == castOther
						.getCountryOrRegion()) || (this.getCountryOrRegion() != null
						&& castOther.getCountryOrRegion() != null && this
						.getCountryOrRegion().equals(
								castOther.getCountryOrRegion())))
				&& ((this.getAreaCode() == castOther.getAreaCode()) || (this
						.getAreaCode() != null
						&& castOther.getAreaCode() != null && this
						.getAreaCode().equals(castOther.getAreaCode())))
				&& ((this.getProvinceCode() == castOther.getProvinceCode()) || (this
						.getProvinceCode() != null
						&& castOther.getProvinceCode() != null && this
						.getProvinceCode().equals(castOther.getProvinceCode())))
				&& ((this.getCityCode() == castOther.getCityCode()) || (this
						.getCityCode() != null
						&& castOther.getCityCode() != null && this
						.getCityCode().equals(castOther.getCityCode())))
				&& ((this.getCountyCode() == castOther.getCountyCode()) || (this
						.getCountyCode() != null
						&& castOther.getCountyCode() != null && this
						.getCountyCode().equals(castOther.getCountyCode())))
				&& ((this.getBranchAddress() == castOther.getBranchAddress()) || (this
						.getBranchAddress() != null
						&& castOther.getBranchAddress() != null && this
						.getBranchAddress()
						.equals(castOther.getBranchAddress())))
				&& ((this.getBranchZipcode() == castOther.getBranchZipcode()) || (this
						.getBranchZipcode() != null
						&& castOther.getBranchZipcode() != null && this
						.getBranchZipcode()
						.equals(castOther.getBranchZipcode())))
				&& ((this.getBranchTel() == castOther.getBranchTel()) || (this
						.getBranchTel() != null
						&& castOther.getBranchTel() != null && this
						.getBranchTel().equals(castOther.getBranchTel())))
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
				+ (getBranchNo() == null ? 0 : this.getBranchNo().hashCode());
		result = 37 * result
				+ (getFarenNo() == null ? 0 : this.getFarenNo().hashCode());
		result = 37
				* result
				+ (getBranchName() == null ? 0 : this.getBranchName()
						.hashCode());
		result = 37
				* result
				+ (getBranchShName() == null ? 0 : this.getBranchShName()
						.hashCode());
		result = 37 * result
				+ (getFenhangNo() == null ? 0 : this.getFenhangNo().hashCode());
		result = 37
				* result
				+ (getBranchType() == null ? 0 : this.getBranchType()
						.hashCode());
		result = 37
				* result
				+ (getBranchKind() == null ? 0 : this.getBranchKind()
						.hashCode());
		result = 37
				* result
				+ (getBranchLevel() == null ? 0 : this.getBranchLevel()
						.hashCode());
		result = 37 * result
				+ (getValidFlag() == null ? 0 : this.getValidFlag().hashCode());
		result = 37 * result
				+ (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37 * result
				+ (getEndDate() == null ? 0 : this.getEndDate().hashCode());
		result = 37
				* result
				+ (getCountryOrRegion() == null ? 0 : this.getCountryOrRegion()
						.hashCode());
		result = 37 * result
				+ (getAreaCode() == null ? 0 : this.getAreaCode().hashCode());
		result = 37
				* result
				+ (getProvinceCode() == null ? 0 : this.getProvinceCode()
						.hashCode());
		result = 37 * result
				+ (getCityCode() == null ? 0 : this.getCityCode().hashCode());
		result = 37
				* result
				+ (getCountyCode() == null ? 0 : this.getCountyCode()
						.hashCode());
		result = 37
				* result
				+ (getBranchAddress() == null ? 0 : this.getBranchAddress()
						.hashCode());
		result = 37
				* result
				+ (getBranchZipcode() == null ? 0 : this.getBranchZipcode()
						.hashCode());
		result = 37 * result
				+ (getBranchTel() == null ? 0 : this.getBranchTel().hashCode());
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