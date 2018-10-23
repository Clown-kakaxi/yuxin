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
 * The persistent class for the OCRM_F_PD_AIM_CUST database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_AIM_CUST")
public class OcrmFPdAimCust implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_PD_AIM_CUST_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_PD_AIM_CUST_ID_GENERATOR")
	private long id;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

    @Temporal( TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Column(name="PRODUCT_ID")
	private String productId;

    public OcrmFPdAimCust() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}