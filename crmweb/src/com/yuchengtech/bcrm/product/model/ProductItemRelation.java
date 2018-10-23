package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_PD_PROD_ITEM_REL database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_ITEM_REL")
public class ProductItemRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=20)
	private String id;

	@Column(nullable=false, length=180)
	private String key;

	@Column(name="PROD_CLASS_NO", length=18)
	private String productClassNo;

	@Column(name="PROD_CLASS_SB", nullable=false, length=4)
	private String productClassSb;

	@Column(name="PRODUCT_ID", length=30)
	private String productId;

	@Column(name="REL_DESC", length=200)
	private String relDesc;

	@Column(name="REL_TYPE", nullable=false, length=16)
	private String relType;

	@Column(length=1)
	private String tjkj;

    public ProductItemRelation() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

	public String getProductClassNo() {
		return productClassNo;
	}

	public void setProductClassNo(String productClassNo) {
		this.productClassNo = productClassNo;
	}

	public String getProductClassSb() {
		return productClassSb;
	}

	public void setProductClassSb(String productClassSb) {
		this.productClassSb = productClassSb;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRelDesc() {
		return this.relDesc;
	}

	public void setRelDesc(String relDesc) {
		this.relDesc = relDesc;
	}

	public String getRelType() {
		return this.relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public String getTjkj() {
		return this.tjkj;
	}

	public void setTjkj(String tjkj) {
		this.tjkj = tjkj;
	}

}