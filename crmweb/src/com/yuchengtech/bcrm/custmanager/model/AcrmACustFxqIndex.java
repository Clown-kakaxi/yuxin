package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_A_CUST_FXQ_INDEX database table.
 * 
 */
@Entity
@Table(name="ACRM_F_SYS_CUST_FXQ_INDEX")
public class AcrmACustFxqIndex implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_SYS_CUST_FXQ_INDEX_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_SYS_CUST_FXQ_INDEX_ID_GENERATOR")
	private Long id;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="FXQ006")
	private String fxq006;

	@Column(name="FXQCODE")
	private String fxqCode;
	
	@Column(name="FXQ007")
	private String fxq007;

	@Column(name="FXQ008")
	private String fxq008;

	@Column(name="FXQ009")
	private String fxq009;

	@Column(name="FXQ010")
	private String fxq010;

	@Column(name="FXQ011")
	private String fxq011;

	@Column(name="FXQ012")
	private String fxq012;

	@Column(name="FXQ013")
	private String fxq013;

	@Column(name="FXQ014")
	private String fxq014;

	@Column(name="FXQ015")
	private String fxq015;

	@Column(name="FXQ016")
	private String fxq016;

	@Column(name="FXQ021")
	private String fxq021;

	@Column(name="FXQ022")
	private String fxq022;

	@Column(name="FXQ023")
	private String fxq023;

	@Column(name="FXQ024")
	private String fxq024;

	@Column(name="FXQ025")
	private String fxq025;

	@Column(name="FXQ026")
	private String fxq026;

    public AcrmACustFxqIndex() {
    }

	public String getFxqCode() {
		return fxqCode;
	}

	public void setFxqCode(String fxqCode) {
		this.fxqCode = fxqCode;
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

	public String getFxq006() {
		return this.fxq006;
	}

	public void setFxq006(String fxq006) {
		this.fxq006 = fxq006;
	}

	public String getFxq007() {
		return this.fxq007;
	}

	public void setFxq007(String fxq007) {
		this.fxq007 = fxq007;
	}

	public String getFxq008() {
		return this.fxq008;
	}

	public void setFxq008(String fxq008) {
		this.fxq008 = fxq008;
	}

	public String getFxq009() {
		return this.fxq009;
	}

	public void setFxq009(String fxq009) {
		this.fxq009 = fxq009;
	}

	public String getFxq010() {
		return this.fxq010;
	}

	public void setFxq010(String fxq010) {
		this.fxq010 = fxq010;
	}

	public String getFxq011() {
		return this.fxq011;
	}

	public void setFxq011(String fxq011) {
		this.fxq011 = fxq011;
	}

	public String getFxq012() {
		return this.fxq012;
	}

	public void setFxq012(String fxq012) {
		this.fxq012 = fxq012;
	}

	public String getFxq013() {
		return this.fxq013;
	}

	public void setFxq013(String fxq013) {
		this.fxq013 = fxq013;
	}

	public String getFxq014() {
		return this.fxq014;
	}

	public void setFxq014(String fxq014) {
		this.fxq014 = fxq014;
	}

	public String getFxq015() {
		return this.fxq015;
	}

	public void setFxq015(String fxq015) {
		this.fxq015 = fxq015;
	}

	public String getFxq016() {
		return this.fxq016;
	}

	public void setFxq016(String fxq016) {
		this.fxq016 = fxq016;
	}

	public String getFxq021() {
		return this.fxq021;
	}

	public void setFxq021(String fxq021) {
		this.fxq021 = fxq021;
	}

	public String getFxq022() {
		return this.fxq022;
	}

	public void setFxq022(String fxq022) {
		this.fxq022 = fxq022;
	}

	public String getFxq023() {
		return this.fxq023;
	}

	public void setFxq023(String fxq023) {
		this.fxq023 = fxq023;
	}

	public String getFxq024() {
		return this.fxq024;
	}

	public void setFxq024(String fxq024) {
		this.fxq024 = fxq024;
	}

	public String getFxq025() {
		return this.fxq025;
	}

	public void setFxq025(String fxq025) {
		this.fxq025 = fxq025;
	}

	public String getFxq026() {
		return fxq026;
	}

	public void setFxq026(String fxq026) {
		this.fxq026 = fxq026;
	}
	

}