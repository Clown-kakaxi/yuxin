package com.yuchengtech.bcrm.customer.customerView.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ACRM_F_CI_PERSON_ADDITIONAL database table.
 * 
 */
@Entity
@Table(name="/ACRM_F_CI_PERSON_ADDITIONAL")
public class AcrmFCiPersonAdditional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID")
	private String custId;
	
	@Column(name="HOME_STYLE")
	private String homeStyle;
	
	@Column(name="PROFESSION_STYLE")
	private String professionStyle;
	
	@Column(name="PROFESSION_STYLE_REMARK")
	private String professionStyleRemark;
	
	@Column(name="IF_UPDATE_MAIL")
	private String ifUpdateMail;

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getHomeStyle() {
		return homeStyle;
	}

	public void setHomeStyle(String homeStyle) {
		this.homeStyle = homeStyle;
	}

	public String getProfessionStyle() {
		return professionStyle;
	}

	public void setProfessionStyle(String professionStyle) {
		this.professionStyle = professionStyle;
	}

	public String getProfessionStyleRemark() {
		return professionStyleRemark;
	}

	public void setProfessionStyleRemark(String professionStyleRemark) {
		this.professionStyleRemark = professionStyleRemark;
	}

	public String getIfUpdateMail() {
		return ifUpdateMail;
	}

	public void setIfUpdateMail(String ifUpdateMail) {
		this.ifUpdateMail = ifUpdateMail;
	}
}
