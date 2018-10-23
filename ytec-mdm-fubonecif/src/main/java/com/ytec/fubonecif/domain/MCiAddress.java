package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiAddress entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ADDRESS")
public class MCiAddress implements java.io.Serializable {

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

	// Constructors

	/** default constructor */
	public MCiAddress() {
	}

	/** minimal constructor */
	public MCiAddress(String addrId) {
		this.addrId = addrId;
	}

	/** full constructor */
	public MCiAddress(String addrId, String custId, String addrType,
			String addr, String enAddr, String contmethInfo, String zipcode,
			String countryOrRegion, String adminZone, String areaCode,
			String provinceCode, String cityCode, String countyCode,
			String townCode, String townName, String streetName,
			String villageNo, String villageName, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
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
	}

	// Property accessors
	@Id
	@Column(name = "ADDR_ID", unique = true, nullable = false, length = 20)
	public String getAddrId() {
		return this.addrId;
	}

	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}

	@Column(name = "CUST_ID", length = 20)
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

	@Column(name = "CONTMETH_INFO", length = 20)
	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	@Column(name = "ZIPCODE", length = 20)
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

}