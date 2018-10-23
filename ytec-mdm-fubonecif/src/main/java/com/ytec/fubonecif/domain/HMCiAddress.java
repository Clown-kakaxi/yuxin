package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiAddress entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_ADDRESS")
public class HMCiAddress implements java.io.Serializable {

	// Fields

	private HMCiAddressId id;

	// Constructors

	/** default constructor */
	public HMCiAddress() {
	}

	/** full constructor */
	public HMCiAddress(HMCiAddressId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "addrId", column = @Column(name = "ADDR_ID", nullable = false, scale = 0)),
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", scale = 0)),
			@AttributeOverride(name = "addrType", column = @Column(name = "ADDR_TYPE", length = 20)),
			@AttributeOverride(name = "addr", column = @Column(name = "ADDR", length = 200)),
			@AttributeOverride(name = "contmethInfo", column = @Column(name = "CONTMETH_INFO", length = 100)),
			@AttributeOverride(name = "zipcode", column = @Column(name = "ZIPCODE", length = 32)),
			@AttributeOverride(name = "countryOrRegion", column = @Column(name = "COUNTRY_OR_REGION", length = 20)),
			@AttributeOverride(name = "adminZone", column = @Column(name = "ADMIN_ZONE", length = 20)),
			@AttributeOverride(name = "areaCode", column = @Column(name = "AREA_CODE", length = 20)),
			@AttributeOverride(name = "provinceCode", column = @Column(name = "PROVINCE_CODE", length = 20)),
			@AttributeOverride(name = "cityCode", column = @Column(name = "CITY_CODE", length = 20)),
			@AttributeOverride(name = "countyCode", column = @Column(name = "COUNTY_CODE", length = 20)),
			@AttributeOverride(name = "townCode", column = @Column(name = "TOWN_CODE", length = 20)),
			@AttributeOverride(name = "townName", column = @Column(name = "TOWN_NAME", length = 80)),
			@AttributeOverride(name = "streetName", column = @Column(name = "STREET_NAME", length = 80)),
			@AttributeOverride(name = "villageNo", column = @Column(name = "VILLAGE_NO", length = 20)),
			@AttributeOverride(name = "villageName", column = @Column(name = "VILLAGE_NAME", length = 80)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM", length = 11)),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false, length = 11)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiAddressId getId() {
		return this.id;
	}

	public void setId(HMCiAddressId id) {
		this.id = id;
	}

}