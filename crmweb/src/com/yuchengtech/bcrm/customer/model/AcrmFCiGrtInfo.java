package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_GRT_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_GRT_INFO")
public class AcrmFCiGrtInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="ACCOUNT_AMT")
	private BigDecimal accountAmt;

	@Column(name="AREA_LOCATION")
	private String areaLocation;

	@Column(name="CN_CONT_NO")
	private String cnContNo;

	@Column(name="CONT_CATEGORY")
	private String contCategory;

	@Column(name="CONT_ID")
	private String contId;

	@Column(name="CONT_TYPE")
	private String contType;

	private String currency;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_MANAGER_ID")
	private String custManagerId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="GAGE_TYPE")
	private String gageType;

	@Column(name="GUAR_CONT_CN_NO")
	private String guarContCnNo;

	@Column(name="GUAR_CONT_NO")
	private String guarContNo;

	@Column(name="GUAR_WAY")
	private String guarWay;

	@Column(name="GUARANTEE_CUST_ID")
	private String guaranteeCustId;

	@Column(name="GUARANTEE_IDENT_ID")
	private String guaranteeIdentId;

	@Column(name="GUARANTEE_IDENT_TYPE")
	private String guaranteeIdentType;

	@Column(name="GUARANTEE_NAME")
	private String guaranteeName;

	@Column(name="GUARANTY_END_DATE")
	private String guarantyEndDate;

	@Column(name="GUARANTY_ID")
	private String guarantyId;

	@Column(name="GUARANTY_START_DATE")
	private String guarantyStartDate;

	@Column(name="GUARANTY_STATE")
	private String guarantyState;

	@Column(name="GUARANTY_TYPE")
	private String guarantyType;

	@Column(name="HYC_SHOW")
	private String hycShow;

	@Column(name="IDENT_ID")
	private String identId;

	@Column(name="LOAN_CARD_NO")
	private String loanCardNo;

    public AcrmFCiGrtInfo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAccountAmt() {
		return this.accountAmt;
	}

	public void setAccountAmt(BigDecimal accountAmt) {
		this.accountAmt = accountAmt;
	}

	public String getAreaLocation() {
		return this.areaLocation;
	}

	public void setAreaLocation(String areaLocation) {
		this.areaLocation = areaLocation;
	}

	public String getCnContNo() {
		return this.cnContNo;
	}

	public void setCnContNo(String cnContNo) {
		this.cnContNo = cnContNo;
	}

	public String getContCategory() {
		return this.contCategory;
	}

	public void setContCategory(String contCategory) {
		this.contCategory = contCategory;
	}

	public String getContId() {
		return this.contId;
	}

	public void setContId(String contId) {
		this.contId = contId;
	}

	public String getContType() {
		return this.contType;
	}

	public void setContType(String contType) {
		this.contType = contType;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustManagerId() {
		return this.custManagerId;
	}

	public void setCustManagerId(String custManagerId) {
		this.custManagerId = custManagerId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getGageType() {
		return this.gageType;
	}

	public void setGageType(String gageType) {
		this.gageType = gageType;
	}

	public String getGuarContCnNo() {
		return this.guarContCnNo;
	}

	public void setGuarContCnNo(String guarContCnNo) {
		this.guarContCnNo = guarContCnNo;
	}

	public String getGuarContNo() {
		return this.guarContNo;
	}

	public void setGuarContNo(String guarContNo) {
		this.guarContNo = guarContNo;
	}

	public String getGuarWay() {
		return this.guarWay;
	}

	public void setGuarWay(String guarWay) {
		this.guarWay = guarWay;
	}

	public String getGuaranteeCustId() {
		return this.guaranteeCustId;
	}

	public void setGuaranteeCustId(String guaranteeCustId) {
		this.guaranteeCustId = guaranteeCustId;
	}

	public String getGuaranteeIdentId() {
		return this.guaranteeIdentId;
	}

	public void setGuaranteeIdentId(String guaranteeIdentId) {
		this.guaranteeIdentId = guaranteeIdentId;
	}

	public String getGuaranteeIdentType() {
		return this.guaranteeIdentType;
	}

	public void setGuaranteeIdentType(String guaranteeIdentType) {
		this.guaranteeIdentType = guaranteeIdentType;
	}

	public String getGuaranteeName() {
		return this.guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	public String getGuarantyEndDate() {
		return this.guarantyEndDate;
	}

	public void setGuarantyEndDate(String guarantyEndDate) {
		this.guarantyEndDate = guarantyEndDate;
	}

	public String getGuarantyId() {
		return this.guarantyId;
	}

	public void setGuarantyId(String guarantyId) {
		this.guarantyId = guarantyId;
	}

	public String getGuarantyStartDate() {
		return this.guarantyStartDate;
	}

	public void setGuarantyStartDate(String guarantyStartDate) {
		this.guarantyStartDate = guarantyStartDate;
	}

	public String getGuarantyState() {
		return this.guarantyState;
	}

	public void setGuarantyState(String guarantyState) {
		this.guarantyState = guarantyState;
	}

	public String getGuarantyType() {
		return this.guarantyType;
	}

	public void setGuarantyType(String guarantyType) {
		this.guarantyType = guarantyType;
	}

	public String getHycShow() {
		return this.hycShow;
	}

	public void setHycShow(String hycShow) {
		this.hycShow = hycShow;
	}

	public String getIdentId() {
		return this.identId;
	}

	public void setIdentId(String identId) {
		this.identId = identId;
	}

	public String getLoanCardNo() {
		return this.loanCardNo;
	}

	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}

}