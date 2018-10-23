package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_PRODUCT_WILL database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_PRODUCT_WILL_TEMP")
public class AcrmFCiProductWillTemp implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
	@Column(name="CUST_ID")
	private String custId;

    @Column(name="COLLATERAL")
    private String collateral;
    
	@Column(name="FINANCIAL_PRODUCTS")
	private String financialProducts;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LOAN_TYPE")
	private String loanType;

	@Column(name="PRODUCT_TYPE")
	private String productType;

	private String state;

    public AcrmFCiProductWillTemp() {
    }

	public String getCollateral() {
		return this.collateral;
	}

	public void setCollateral(String collateral) {
		this.collateral = collateral;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getFinancialProducts() {
		return this.financialProducts;
	}

	public void setFinancialProducts(String financialProducts) {
		this.financialProducts = financialProducts;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLoanType() {
		return this.loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}