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
 * The persistent class for the OCRM_F_SE_GOODS_HIS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_SE_GOODS_HIS")
public class OcrmFSeGoodsHis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_SE_GOODS_HIS_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_SE_GOODS_HIS_ID_GENERATOR")
	private Long id;

	@Column(name="COMP_ACTI")
	private String compActi;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

    @Temporal( TemporalType.DATE)
	@Column(name="GIVE_DATE")
	private Date giveDate;

	@Column(name="GIVE_NUMBER")
	private BigDecimal giveNumber;

	@Column(name="GIVE_REASON")
	private String giveReason;

	@Column(name="GIVE_STATE")
	private String giveState;

	@Column(name="GOODS_ID")
	private String goodsId;

	@Column(name="GOODS_NAME")
	private String goodsName;

	@Column(name="GOODS_NUMBER")
	private BigDecimal goodsNumber;

	@Column(name="NEED_SCORE")
	private BigDecimal needScore;

	@Column(name="OPARTER_ID")
	private String oparterId;

	@Column(name="ORG_ID")
	private String orgId;

	
	@Column(name="REMARK")
	private String remark;
	
    public OcrmFSeGoodsHis() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Date getGiveDate() {
		return this.giveDate;
	}

	public void setGiveDate(Date giveDate) {
		this.giveDate = giveDate;
	}

	public BigDecimal getGiveNumber() {
		return this.giveNumber;
	}

	public void setGiveNumber(BigDecimal giveNumber) {
		this.giveNumber = giveNumber;
	}

	public String getGiveReason() {
		return this.giveReason;
	}

	public void setGiveReason(String giveReason) {
		this.giveReason = giveReason;
	}

	public String getGiveState() {
		return this.giveState;
	}

	public void setGiveState(String giveState) {
		this.giveState = giveState;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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

	public BigDecimal getNeedScore() {
		return this.needScore;
	}

	public void setNeedScore(BigDecimal needScore) {
		this.needScore = needScore;
	}

	public String getOparterId() {
		return this.oparterId;
	}

	public void setOparterId(String oparterId) {
		this.oparterId = oparterId;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}