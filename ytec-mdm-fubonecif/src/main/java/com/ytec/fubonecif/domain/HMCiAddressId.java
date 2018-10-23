package com.ytec.fubonecif.domain;


import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiAddressId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiAddressId implements java.io.Serializable {

	// Fields
	private String addrId;
	private String custId;
	private String addrType;
	private String addr;
	private String enAddr;
	private String contmethInfo;
	private String zipcode;
	private String countryOrRegion;
	private String adminZone;
	private String areaCode;
	private String provinceCode;
	private String cityCode;
	private String countyCode;
	private String townCode;
	private String townName;
	private String streetName;
	private String villageNo;
	private String villageName;
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
	public HMCiAddressId() {
	}

	/** minimal constructor */
	public HMCiAddressId(String addrId, Timestamp hisOperTime) {
		this.addrId = addrId;
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiAddressId(String addrId, String custId, String addrType,
			String addr,String enAddr, String contmethInfo, String zipcode,
			String countryOrRegion, String adminZone, String areaCode,
			String provinceCode, String cityCode, String countyCode,
			String townCode, String townName, String streetName,
			String villageNo, String villageName, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.addrId = addrId;
		this.custId = custId;
		this.addrType = addrType;
		this.addr = addr;
		this.enAddr = enAddr;
		this.contmethInfo = contmethInfo;
		this.zipcode = zipcode;
		this.countryOrRegion = countryOrRegion;
		this.adminZone = adminZone;
		this.areaCode = areaCode;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.countyCode = countyCode;
		this.townCode = townCode;
		this.townName = townName;
		this.streetName = streetName;
		this.villageNo = villageNo;
		this.villageName = villageName;
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

	@Column(name = "ADDR_ID", nullable = false, scale = 0)
	public String getAddrId() {
		return this.addrId;
	}

	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}

	@Column(name = "CUST_ID", scale = 0)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "ADDR_TYPE", length = 20)
	public String getAddrType() {
		return this.addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	@Column(name = "ADDR", length = 200)
	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	@Column(name = "EN_ADDR")
	public String getEnAddr() {
		return this.enAddr;
	}

	public void setEnAddr(String enAddr) {
		this.enAddr = enAddr;
	}

	@Column(name = "CONTMETH_INFO", length = 100)
	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	@Column(name = "ZIPCODE", length = 32)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "COUNTRY_OR_REGION", length = 20)
	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	@Column(name = "ADMIN_ZONE", length = 20)
	public String getAdminZone() {
		return this.adminZone;
	}

	public void setAdminZone(String adminZone) {
		this.adminZone = adminZone;
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

	@Column(name = "TOWN_CODE", length = 20)
	public String getTownCode() {
		return this.townCode;
	}

	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}

	@Column(name = "TOWN_NAME", length = 80)
	public String getTownName() {
		return this.townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	@Column(name = "STREET_NAME", length = 80)
	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	@Column(name = "VILLAGE_NO", length = 20)
	public String getVillageNo() {
		return this.villageNo;
	}

	public void setVillageNo(String villageNo) {
		this.villageNo = villageNo;
	}

	@Column(name = "VILLAGE_NAME", length = 80)
	public String getVillageName() {
		return this.villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
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
		if (!(other instanceof HMCiAddressId))
			return false;
		HMCiAddressId castOther = (HMCiAddressId) other;

		return ((this.getAddrId() == castOther.getAddrId()) || (this
				.getAddrId() != null
				&& castOther.getAddrId() != null && this.getAddrId().equals(
				castOther.getAddrId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getAddrType() == castOther.getAddrType()) || (this
						.getAddrType() != null
						&& castOther.getAddrType() != null && this
						.getAddrType().equals(castOther.getAddrType())))
				&& ((this.getAddr() == castOther.getAddr()) || (this.getAddr() != null
						&& castOther.getAddr() != null && this.getAddr()
						.equals(castOther.getAddr())))
				&& ((this.getContmethInfo() == castOther.getContmethInfo()) || (this
						.getContmethInfo() != null
						&& castOther.getContmethInfo() != null && this
						.getContmethInfo().equals(castOther.getContmethInfo())))
				&& ((this.getZipcode() == castOther.getZipcode()) || (this
						.getZipcode() != null
						&& castOther.getZipcode() != null && this.getZipcode()
						.equals(castOther.getZipcode())))
				&& ((this.getCountryOrRegion() == castOther
						.getCountryOrRegion()) || (this.getCountryOrRegion() != null
						&& castOther.getCountryOrRegion() != null && this
						.getCountryOrRegion().equals(
								castOther.getCountryOrRegion())))
				&& ((this.getAdminZone() == castOther.getAdminZone()) || (this
						.getAdminZone() != null
						&& castOther.getAdminZone() != null && this
						.getAdminZone().equals(castOther.getAdminZone())))
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
				&& ((this.getTownCode() == castOther.getTownCode()) || (this
						.getTownCode() != null
						&& castOther.getTownCode() != null && this
						.getTownCode().equals(castOther.getTownCode())))
				&& ((this.getTownName() == castOther.getTownName()) || (this
						.getTownName() != null
						&& castOther.getTownName() != null && this
						.getTownName().equals(castOther.getTownName())))
				&& ((this.getStreetName() == castOther.getStreetName()) || (this
						.getStreetName() != null
						&& castOther.getStreetName() != null && this
						.getStreetName().equals(castOther.getStreetName())))
				&& ((this.getVillageNo() == castOther.getVillageNo()) || (this
						.getVillageNo() != null
						&& castOther.getVillageNo() != null && this
						.getVillageNo().equals(castOther.getVillageNo())))
				&& ((this.getVillageName() == castOther.getVillageName()) || (this
						.getVillageName() != null
						&& castOther.getVillageName() != null && this
						.getVillageName().equals(castOther.getVillageName())))
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
				+ (getAddrId() == null ? 0 : this.getAddrId().hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37 * result
				+ (getAddrType() == null ? 0 : this.getAddrType().hashCode());
		result = 37 * result
				+ (getAddr() == null ? 0 : this.getAddr().hashCode());
		result = 37
				* result
				+ (getContmethInfo() == null ? 0 : this.getContmethInfo()
						.hashCode());
		result = 37 * result
				+ (getZipcode() == null ? 0 : this.getZipcode().hashCode());
		result = 37
				* result
				+ (getCountryOrRegion() == null ? 0 : this.getCountryOrRegion()
						.hashCode());
		result = 37 * result
				+ (getAdminZone() == null ? 0 : this.getAdminZone().hashCode());
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
		result = 37 * result
				+ (getTownCode() == null ? 0 : this.getTownCode().hashCode());
		result = 37 * result
				+ (getTownName() == null ? 0 : this.getTownName().hashCode());
		result = 37
				* result
				+ (getStreetName() == null ? 0 : this.getStreetName()
						.hashCode());
		result = 37 * result
				+ (getVillageNo() == null ? 0 : this.getVillageNo().hashCode());
		result = 37
				* result
				+ (getVillageName() == null ? 0 : this.getVillageName()
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