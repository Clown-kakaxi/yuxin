package com.yuchengtech.bcrm.finService.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_FIN_ANA_ADVISE_VISTA database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FIN_ANA_ADVISE_VISTA")
public class OcrmFFinAnaAdviseVista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_FIN_ANA_ADVISE_VISTA_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_FIN_ANA_ADVISE_VISTA_ID_GENERATOR")
	private Long id;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CXBLDP_ADVISE")
	private String cxbldpAdvise;

	@Column(name="FZSRBLDP_ADVISE")
	private String fzsrbldpAdvise;

	@Column(name="FZZZCBLDP_ADVISE")
	private String fzzzcbldpAdvise;

	@Column(name="LCCJLDP_ADVISE")
	private String lccjldpAdvise;

	@Column(name="LDXBLDP_ADVISE")
	private String ldxbldpAdvise;

	@Column(name="SYLDP_ADVISE")
	private String syldpAdvise;

	@Column(name="TZYJZCBLDPL_ADVISE")
	private String tzyjzcbldplAdvise;

	@Column(name="XFLDP_ADVISE")
	private String xfldpAdvise;

	@Column(name="ZHDP_ADVISE")
	private String zhdpAdvise;

    public OcrmFFinAnaAdviseVista() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCxbldpAdvise() {
		return this.cxbldpAdvise;
	}

	public void setCxbldpAdvise(String cxbldpAdvise) {
		this.cxbldpAdvise = cxbldpAdvise;
	}

	public String getFzsrbldpAdvise() {
		return this.fzsrbldpAdvise;
	}

	public void setFzsrbldpAdvise(String fzsrbldpAdvise) {
		this.fzsrbldpAdvise = fzsrbldpAdvise;
	}

	public String getFzzzcbldpAdvise() {
		return this.fzzzcbldpAdvise;
	}

	public void setFzzzcbldpAdvise(String fzzzcbldpAdvise) {
		this.fzzzcbldpAdvise = fzzzcbldpAdvise;
	}

	public String getLccjldpAdvise() {
		return this.lccjldpAdvise;
	}

	public void setLccjldpAdvise(String lccjldpAdvise) {
		this.lccjldpAdvise = lccjldpAdvise;
	}

	public String getLdxbldpAdvise() {
		return this.ldxbldpAdvise;
	}

	public void setLdxbldpAdvise(String ldxbldpAdvise) {
		this.ldxbldpAdvise = ldxbldpAdvise;
	}

	public String getSyldpAdvise() {
		return this.syldpAdvise;
	}

	public void setSyldpAdvise(String syldpAdvise) {
		this.syldpAdvise = syldpAdvise;
	}

	public String getTzyjzcbldplAdvise() {
		return this.tzyjzcbldplAdvise;
	}

	public void setTzyjzcbldplAdvise(String tzyjzcbldplAdvise) {
		this.tzyjzcbldplAdvise = tzyjzcbldplAdvise;
	}

	public String getXfldpAdvise() {
		return this.xfldpAdvise;
	}

	public void setXfldpAdvise(String xfldpAdvise) {
		this.xfldpAdvise = xfldpAdvise;
	}

	public String getZhdpAdvise() {
		return this.zhdpAdvise;
	}

	public void setZhdpAdvise(String zhdpAdvise) {
		this.zhdpAdvise = zhdpAdvise;
	}

}