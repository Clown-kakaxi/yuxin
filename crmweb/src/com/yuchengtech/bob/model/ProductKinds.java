package com.yuchengtech.bob.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the OCRM_F_PD_PROD_KIND database table.
 * 
 */
@Entity
@Table(name = "OCRM_F_PD_PROD_KIND")
public class ProductKinds implements Serializable {

	/**
	 * FDM层(基础数据层)，归属于产品管理的第二主题，数据为大类产品汇总关系信息
	 */
	private static final long serialVersionUID = 5770353719893894016L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	/**产品类别标识*/
	@Column(name = "CATL_CODE", nullable = false)
	private Long catlCode;

	/** 节点级别 */
	@Column(name = "CATL_LEVEL", precision = 22)
	private Long catlLevel;

	/** 产品类别名称 */
	@Column(name = "CATL_NAME", length = 100)
	private String catlName;

	/** 节点顺序 */
	@Column(name = "CATL_ORDER", precision = 22)
	private Long catlOrder;

	/** 上级节点 */
	@Column(name = "CATL_PARENT", precision = 22)
	private Long catlParent;
	
	@Column(name = "CALT_PARENT_NAME")
	private String caltParentName;
	
	/** 是否为叶子节点 */
	@Column(name = "IS_LEAF")
	private String  isLeaf;

	/** 大类产品序列 */
	@Column(name = "PROD_CATL_SEQ", length = 32)
	private String productCatlSequence;

	/** 产品展示页面 */
	@Column(name = "PROD_SHOW_URL", length = 50)
	private String productShowUrl;

// bi-directional many-to-one association to Product
//	@OneToMany(mappedBy = "ocrmFPdProdKind")
//	private Set<Product> ocrmFPdProducts;
	public String getCatlName() {
		return this.catlName;
	}

	public void setCatlName(String catlName) {
		this.catlName = catlName;
	}

//	public Set<Product> getOcrmFPdProducts() {
//		return this.ocrmFPdProducts;
//	}
//
//	public void setOcrmFPdProducts(Set<Product> ocrmFPdProducts) {
//		this.ocrmFPdProducts = ocrmFPdProducts;
//	}

	public void setCatlCode(Long catlCode) {
		this.catlCode = catlCode;
	}

	public Long getCatlCode() {
		return catlCode;
	}
	
	/**
	 * @return the catlLevel
	 */
	public Long getCatlLevel() {
		return catlLevel;
	}

	/**
	 * @param catlLevel the catlLevel to set
	 */
	public void setCatlLevel(Long catlLevel) {
		this.catlLevel = catlLevel;
	}

	/**
	 * @return the catlOrder
	 */
	public Long getCatlOrder() {
		return catlOrder;
	}

	/**
	 * @param catlOrder the catlOrder to set
	 */
	public void setCatlOrder(Long catlOrder) {
		this.catlOrder = catlOrder;
	}

	/**
	 * @return the catlParent
	 */
	public Long getCatlParent() {
		return catlParent;
	}

	/**
	 * @param catlParent the catlParent to set
	 */
	public void setCatlParent(Long catlParent) {
		this.catlParent = catlParent;
	}

	/**
	 * @return the caltParentName
	 */
	public String getCaltParentName() {
		return caltParentName;
	}

	/**
	 * @param caltParentName the caltParentName to set
	 */
	public void setCaltParentName(String caltParentName) {
		this.caltParentName = caltParentName;
	}

	/**
	 * @return the isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}

	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public void setProductCatlSequence(String productCatlSequence) {
		this.productCatlSequence = productCatlSequence;
	}

	public String getProductCatlSequence() {
		return productCatlSequence;
	}

	public void setProductShowUrl(String productShowUrl) {
		this.productShowUrl = productShowUrl;
	}

	public String getProductShowUrl() {
		return productShowUrl;
	}

}