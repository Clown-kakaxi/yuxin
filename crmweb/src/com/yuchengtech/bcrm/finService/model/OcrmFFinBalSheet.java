package com.yuchengtech.bcrm.finService.model;

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
 * The persistent class for the OCRM_F_FIN_BAL_SHEET database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FIN_BAL_SHEET")
public class OcrmFFinBalSheet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_FIN_BAL_SHEET_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_FIN_BAL_SHEET_ID_GENERATOR")
	private Long id;

	@Column(name="AMOUNT_VALUE")
	private BigDecimal amountValue;

	@Column(name="ASSET_DEBT_TYPE")
	private String assetDebtType;

	@Column(name="ASSETS_TYPE")
	private String assetsType;

	@Column(name="BELONG_TYPE")
	private String belongType;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="CUST_ID")
	private String custId;

    public OcrmFFinBalSheet() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmountValue() {
		return this.amountValue;
	}

	public void setAmountValue(BigDecimal amountValue) {
		this.amountValue = amountValue;
	}

	public String getAssetDebtType() {
		return this.assetDebtType;
	}

	public void setAssetDebtType(String assetDebtType) {
		this.assetDebtType = assetDebtType;
	}

	public String getAssetsType() {
		return this.assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

	public String getBelongType() {
		return this.belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

}