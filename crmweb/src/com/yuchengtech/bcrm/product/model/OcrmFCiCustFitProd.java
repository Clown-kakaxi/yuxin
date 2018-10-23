package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;
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
 * The persistent class for the OCRM_F_CI_CUST_FIT_PROD database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CUST_FIT_PROD")
public class OcrmFCiCustFitProd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_FIT_PROD_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_FIT_PROD_ID_GENERATOR")
	@Column(name = "ID",unique=true, nullable=false)
	private Long id;
	
	@Column(name="CUST_ID")
	private String custId;
	
	
	@Column(name="PROD_ID")
	private String prodId;
	
	@Column(name="PROD_NAME")
	private String prodName;
	
	

    @Temporal( TemporalType.DATE)
	@Column(name="CTR_DATE")
	private Date ctrDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}


	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}


	public Date getCtrDate() {
		return ctrDate;
	}

	public void setCtrDate(Date ctrDate) {
		this.ctrDate = ctrDate;
	}

}