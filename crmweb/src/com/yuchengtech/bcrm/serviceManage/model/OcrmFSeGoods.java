package com.yuchengtech.bcrm.serviceManage.model;

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
 * The persistent class for the OCRM_F_SE_GOODS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_SE_GOODS")
public class OcrmFSeGoods implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_SE_GOODS_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_SE_GOODS_ID_GENERATOR")
	private Long id;

	@Column(name="COMP_ACTI")
	private String compActi;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_ID")
	private String createId;

	@Column(name="CUST_LEVEL")
	private String custLevel;

	@Column(name="GOODS_NAME")
	private String goodsName;

	@Column(name="GOODS_NUMBER")
	private BigDecimal goodsNumber;

	@Column(name="GOODS_PRICE")
	private BigDecimal goodsPrice;

	@Column(name="GOODS_STATE")
	private String goodsState;

	@Column(name="ORG_ID")
	private String orgId;
	
	
	@Column(name="GOODS_SCORE")
	private BigDecimal goodsScore;

    public OcrmFSeGoods() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getGoodsScore() {
		return goodsScore;
	}

	public void setGoodsScore(BigDecimal goodsScore) {
		this.goodsScore = goodsScore;
	}

	public String getCompActi() {
		return this.compActi;
	}

	public void setCompActi(String compActi) {
		this.compActi = compActi;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCustLevel() {
		return this.custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsNumber() {
		return this.goodsNumber;
	}

	public void setGoodsNumber(BigDecimal goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public BigDecimal getGoodsPrice() {
		return this.goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsState() {
		return this.goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}