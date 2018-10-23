package com.yuchengtech.emp.ecif.customer.entity.customercontact;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the ADDRESS database table.
 * 
 */
@Entity
@Table(name="M_CI_ADDRESS")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ADDR_ID", unique=true, nullable=false)
	private Long addrId;

	@Column(length=200)
	private String addr;

	@Column(name="ADDR_TYPE", length=20)
	private String addrType;

	@Column(name="ADMIN_ZONE", length=20)
	private String adminZone;

	@Column(name="AREA_CODE", length=20)
	private String areaCode;

	@Column(name="CITY_CODE", length=20)
	private String cityCode;

	@Column(name="COUNTRY_OR_REGION", length=20)
	private String countryOrRegion;

	@Column(name="COUNTY_CODE", length=20)
	private String countyCode;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="PROVINCE_CODE", length=20)
	private String provinceCode;

	@Column(name="STREET_NAME", length=80)
	private String streetName;

	@Column(name="TOWN_CODE", length=20)
	private String townCode;

	@Column(name="TOWN_NAME", length=80)
	private String townName;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="VILLAGE_NAME", length=80)
	private String villageName;
	
	@Column(name="CONTMETH_INFO", length=100)
	private String contmethInfo;

	@Column(length=32)
	private String zipcode;

    public Address() {
    }

	public Long getAddrId() {
		return this.addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAddrType() {
		return this.addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	public String getAdminZone() {
		return this.adminZone;
	}

	public void setAdminZone(String adminZone) {
		this.adminZone = adminZone;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	public String getCountyCode() {
		return this.countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
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

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getTownCode() {
		return this.townCode;
	}

	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}

	public String getTownName() {
		return this.townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getVillageName() {
		return this.villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getContmethInfo() {
		return contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

}