package com.yuchengtech.bob.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the SE_ORG_INFO database table.
 * 
 */
@Entity
@Table(name="SE_ORG_INFO")
public class SeOrgInfo implements Serializable {

	private static final long serialVersionUID = -8950490286717941573L;

	/**主键*/
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)	
	@Column(name = "ID", nullable = false)
	private long id;

	/**机构号*/
	@Column(name = "AGECODE", length = 500)
	private String agencyCode;

	/**机构名称*/
	@Column(name = "AGENAME", length = 500)
	private String agencyName;

	/**客户名称*/
	@Column(name = "CUSTNAME", length = 500)
	private String customerName;

	/**组织机构代码*/
	@Column(name = "ORGCODE", length = 500)
	private String orgnizationCode;

	/**设定时间*/
	@Column(name = "SETDATE", length = 500)
	private String setDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrgnizationCode() {
		return orgnizationCode;
	}

	public void setOrgnizationCode(String orgnizationCode) {
		this.orgnizationCode = orgnizationCode;
	}

	public String getSetDate() {
		return setDate;
	}

	public void setSetDate(String setDate) {
		this.setDate = setDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}