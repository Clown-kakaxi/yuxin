package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_PD_PROD_CATL database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_CATL")
public class ProductCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_PD_PROD_CATL_MKT_MATERIAL_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_PD_PROD_CATL_MKT_MATERIAL_ID_GENERATOR")
	@Column(name="CATL_CODE", unique=true, nullable=false, length=20)
	private String catlCode;

	@Column(name="CATL_LEVEL", length=1)
	private String catlLevel;

	@Column(name="CATL_NAME", length=50)
	private String catlName;

	@Column(name="CATL_ORDER", precision=22)
	private Long catlOrder;

	@Column(name="CATL_PARENT", length=20)
	private String catlParent;
	
	@Column(name="VIEW_DETAIL", length=50)
	private String viewDetail;
	
	@Column(name="PROD_VIEW", length=50)
	private String prodView;

//	@Column(name="IS_LEAF", length=13)
//	private String isLeaf;

//	@Column(nullable=false, length=1)
//	private String tjkj;

    public ProductCategory() {
    }

	public String getProdView() {
		return prodView;
	}

	public void setProdView(String prodView) {
		this.prodView = prodView;
	}

	public String getCatlCode() {
		return this.catlCode;
	}

	public void setCatlCode(String catlCode) {
		this.catlCode = catlCode;
	}

	public String getCatlLevel() {
		return this.catlLevel;
	}

	public void setCatlLevel(String catlLevel) {
		this.catlLevel = catlLevel;
	}

	public String getCatlName() {
		return this.catlName;
	}

	public void setCatlName(String catlName) {
		this.catlName = catlName;
	}	

	public Long getCatlOrder() {
		return catlOrder;
	}

	public void setCatlOrder(Long catlOrder) {
		this.catlOrder = catlOrder;
	}

	public String getCatlParent() {
		return this.catlParent;
	}

	public void setCatlParent(String catlParent) {
		this.catlParent = catlParent;
	}

	public String getViewDetail() {
		return viewDetail;
	}

	public void setViewDetail(String viewDetail) {
		this.viewDetail = viewDetail;
	}
	

//	public String getIsLeaf() {
//		return this.isLeaf;
//	}
//
//	public void setIsLeaf(String isLeaf) {
//		this.isLeaf = isLeaf;
//	}
//
//	public String getTjkj() {
//		return this.tjkj;
//	}
//
//	public void setTjkj(String tjkj) {
//		this.tjkj = tjkj;
//	}

}