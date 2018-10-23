package com.yuchengtech.emp.ecif.customer.entity.customerrisk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the MENDINFO database table.
 * 
 */
@Entity
@Table(name="MENDINFO")
public class Mendinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MEND_INFO_ID", unique=true, nullable=false)
	private Long mendInfoId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="DATASRCDESC",length=60)
	private String datasrcdesc;

	@Column(name="DEALINFO",length=100)
	private String dealinfo;

	@Column(name="MENDDATE",length=20)
	private String menddate;

	@Column(name="MENDDESC",length=100)
	private String menddesc;

	@Column(name="PLACE",length=40)
	private String place;

    public Mendinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getMendInfoId() {
		return this.mendInfoId;
	}

	public void setMendInfoId(Long mendInfoId) {
		this.mendInfoId = mendInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDatasrcdesc() {
		return this.datasrcdesc;
	}

	public void setDatasrcdesc(String datasrcdesc) {
		this.datasrcdesc = datasrcdesc;
	}

	public String getDealinfo() {
		return this.dealinfo;
	}

	public void setDealinfo(String dealinfo) {
		this.dealinfo = dealinfo;
	}

	public String getMenddate() {
		return this.menddate;
	}

	public void setMenddate(String menddate) {
		this.menddate = menddate;
	}

	public String getMenddesc() {
		return this.menddesc;
	}

	public void setMenddesc(String menddesc) {
		this.menddesc = menddesc;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}