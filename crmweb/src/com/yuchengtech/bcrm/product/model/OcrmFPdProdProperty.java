package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_PD_PROD_PROPERTY database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_PROPERTY")
public class OcrmFPdProdProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	/*@Id
	@SequenceGenerator(name="OCRM_F_PD_PROD_PROPERTY_PRODUCTID_GENERATOR", sequenceName="COMMONSEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_PD_PROD_PROPERTY_PRODUCTID_GENERATOR")
	@Column(name="PRODUCT_ID")*/
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "PRODUCT_ID", nullable = false)
	private String productId;						//产品id

	@Column(name="PRODUCT_PROPERTY_NAME")
	private String productPropertyName;				//特征项名称

	@Column(name="PRODUCT_PROPERTY_TYPE")			//特征项类型
	private String productPropertyType;

	@Column(name="PRODUCT_PROPERTY_DESC")			//特征项描述
	private String productPropertyDesc;


	public OcrmFPdProdProperty() {
    }
	
    public String getProductPropertyDesc() {
		return productPropertyDesc;
	}

	public void setProductPropertyDesc(String productPropertyDesc) {
		this.productPropertyDesc = productPropertyDesc;
	}


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductPropertyName() {
		return productPropertyName;
	}

	public void setProductPropertyName(String productPropertyName) {
		this.productPropertyName = productPropertyName;
	}

	public String getProductPropertyType() {
		return productPropertyType;
	}

	public void setProductPropertyType(String productPropertyType) {
		this.productPropertyType = productPropertyType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	

	

}