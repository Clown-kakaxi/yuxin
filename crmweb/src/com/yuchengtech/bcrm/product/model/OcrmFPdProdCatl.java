package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_PD_PROD_CATL database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_CATL")
public class OcrmFPdProdCatl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="CATL_CODE")
	private Long catlCode;

	@Column(name="CATL_BUS_ID")
	private String catlBusId;

	@Column(name="CATL_LEVEL")
	private Integer catlLevel;

	@Column(name="CATL_NAME")
	private String catlName;

	@Column(name="CATL_ORDER")
	private Integer catlOrder;

	@Column(name="CATL_PARENT")
	private Long catlParent;

	@Column(name="VIEW_DETAIL")
	private String viewDetail;
	
	@Column(name="PROD_VIEW")
	private String prodView;

    public OcrmFPdProdCatl() {
    }

	public String getProdView() {
		return prodView;
	}

	public void setProdView(String prodView) {
		this.prodView = prodView;
	}

	public Long getCatlCode() {
		return this.catlCode;
	}

	public void setCatlCode(Long catlCode) {
		this.catlCode = catlCode;
	}

	public String getCatlBusId() {
		return this.catlBusId;
	}

	public void setCatlBusId(String catlBusId) {
		this.catlBusId = catlBusId;
	}

	public Integer getCatlLevel() {
		return this.catlLevel;
	}

	public void setCatlLevel(Integer catlLevel) {
		this.catlLevel = catlLevel;
	}

	public String getCatlName() {
		return this.catlName;
	}

	public void setCatlName(String catlName) {
		this.catlName = catlName;
	}

	public Integer getCatlOrder() {
		return this.catlOrder;
	}

	public void setCatlOrder(Integer catlOrder) {
		this.catlOrder = catlOrder;
	}

	public Long getCatlParent() {
		return this.catlParent;
	}

	public void setCatlParent(Long catlParent) {
		this.catlParent = catlParent;
	}

	public String getViewDetail() {
		return this.viewDetail;
	}

	public void setViewDetail(String viewDetail) {
		this.viewDetail = viewDetail;
	}

}