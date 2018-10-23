package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 产品对照关系model
 * @author ZSXIN
 *
 */
@Entity
@Table(name="OCRM_F_PD_PROD_ITEM_REL")
public class OcrmFPdProdItemRel implements Serializable {
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;//主键值

	private String key;//关键字

	@Column(name="PRODUCT_ID")//产品id
	private String productId;

	@Column(name="REL_DESC")
	private String relDesc;//描述

	@Column(name="REL_TYPE")
	private String relType;//对照类型

    public OcrmFPdProdItemRel() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
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

}