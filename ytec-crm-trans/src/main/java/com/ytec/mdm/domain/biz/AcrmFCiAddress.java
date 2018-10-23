package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ACRM_F_CI_ADDRESS database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ADDRESS")
public class AcrmFCiAddress implements Serializable {
	@Id
//	@SequenceGenerator(name="ACRM_F_CI_ADDRESS_ADDRID_GENERATOR", sequenceName="SEQ_ADDR_ID")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_ADDRESS_ADDRID_GENERATOR")
	@Column(name="ADDR_ID")
	private long addrId;

	private String addr;

	@Column(name="ADDR_TYPE")
	private String addrType;

	@Column(name="ADMIN_ZONE")
	private String adminZone;

	@Column(name="AREA_CODE")
	private String areaCode;

	@Column(name="CITY_CODE")
	private String cityCode;

	@Column(name="CONTMETH_INFO")
	private String contmethInfo;

	@Column(name="COUNTRY_OR_REGION")
	private String countryOrRegion;

	@Column(name="COUNTY_CODE")
	private String countyCode;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="EN_ADDR")
	private String enAddr;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="PROVINCE_CODE")
	private String provinceCode;

	@Column(name="STREET_NAME")
	private String streetName;

	@Column(name="TOWN_CODE")
	private String townCode;

	@Column(name="TOWN_NAME")
	private String townName;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="VILLAGE_NAME")
	private String villageName;

	@Column(name="VILLAGE_NO")
	private String villageNo;

	private String zipcode;

    public AcrmFCiAddress() {
    }
    
	public long getAddrId() {
		return this.addrId;
	}

	public void setAddrId(long addrId) {
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

	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
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

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getEnAddr() {
		return this.enAddr;
	}

	public void setEnAddr(String enAddr) {
		this.enAddr = enAddr;
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

	public String getVillageNo() {
		return this.villageNo;
	}

	public void setVillageNo(String villageNo) {
		this.villageNo = villageNo;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}