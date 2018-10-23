package com.yuchengtech.bcrm.sales.model;

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
 * The persistent class for the OCRM_F_MK_ACTI_PRODUCT database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MK_ACTI_PRODUCT")
public class OcrmFMkActiProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MK_ACTI_PRODUCT_AIMPRODID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MK_ACTI_PRODUCT_AIMPRODID_GENERATOR")
	@Column(name="AIM_PROD_ID")
	private Long aimProdId;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="MKT_ACTI_ID")
	private BigDecimal mktActiId;

	@Column(name="PRODUCT_ID")
	private BigDecimal productId;

	@Column(name="PRODUCT_NAME")
	private String productName;

	public Long getAimProdId() {
		return aimProdId;
	}

	public void setAimProdId(Long aimProdId) {
		this.aimProdId = aimProdId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public BigDecimal getMktActiId() {
		return mktActiId;
	}

	public void setMktActiId(BigDecimal mktActiId) {
		this.mktActiId = mktActiId;
	}

	public BigDecimal getProductId() {
		return productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}