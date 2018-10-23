package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_AG_AGREEMENT database table.
 * 
 */
@Entity
@Table(name="ACRM_F_AG_AGREEMENT")
public class AcrmFAgAgreement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_AG_AGREEMENT_AGREEMENTID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_AG_AGREEMENT_AGREEMENTID_GENERATOR")
	@Column(name="AGREEMENT_ID",unique=true, nullable=false)
	private Long agreementId;

	@Column(name="AGREEMENT_NAME")
	private String agreementName;

	private BigDecimal bal;

	@Column(name="CRM_DT")
	private String crmDt;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME_CN")
	private String custNameCn;

	@Column(name="CYNO")
	private String cyno;

	@Column(name="DP_TYPE")
	private String dpType;

    @Temporal( TemporalType.DATE)
	@Column(name="ENT_DATE")
	private Date entDate;

	@Column(name="HANDLE_USER")
	private String handleUser;

	@Column(name="ITEM")
	private String item;

	@Column(name="ORG_NO")
	private String orgNo;

	@Column(name="PRODUCT_ID")
	private String productId;

	@Column(name="RATE")
	private BigDecimal rate;

	@Column(name="SBIT")
	private String sbit;

	@Column(name="SSIT")
	private String ssit;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

    @Column(name="STATUS")
	private String status;

	@Column(name="SYS_NO")
	private String sysNo;

	private BigDecimal type;

	public Long getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Long agreementId) {
		this.agreementId = agreementId;
	}

	public String getAgreementName() {
		return agreementName;
	}

	public void setAgreementName(String agreementName) {
		this.agreementName = agreementName;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	public String getCrmDt() {
		return crmDt;
	}

	public void setCrmDt(String crmDt) {
		this.crmDt = crmDt;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustNameCn() {
		return custNameCn;
	}

	public void setCustNameCn(String custNameCn) {
		this.custNameCn = custNameCn;
	}

	public String getCyno() {
		return cyno;
	}

	public void setCyno(String cyno) {
		this.cyno = cyno;
	}

	public String getDpType() {
		return dpType;
	}

	public void setDpType(String dpType) {
		this.dpType = dpType;
	}

	public Date getEntDate() {
		return entDate;
	}

	public void setEntDate(Date entDate) {
		this.entDate = entDate;
	}

	public String getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getSbit() {
		return sbit;
	}

	public void setSbit(String sbit) {
		this.sbit = sbit;
	}

	public String getSsit() {
		return ssit;
	}

	public void setSsit(String ssit) {
		this.ssit = ssit;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSysNo() {
		return sysNo;
	}

	public void setSysNo(String sysNo) {
		this.sysNo = sysNo;
	}

	public BigDecimal getType() {
		return type;
	}

	public void setType(BigDecimal type) {
		this.type = type;
	}
}