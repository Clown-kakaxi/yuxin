package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 客户群信息表
 */
@Entity
@Table(name = "OCRM_F_PD_PROD_TC_SET")
public class TargetCus implements Serializable {

	private static final long serialVersionUID = -3071512732613148823L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Long id;

	/** 产品id */
	@Column(name = "PRODUCT_ID", length = 30)
	private String productId;

	/** 公式 */
	@Column(name = "COMBINATION", length = 400)
	private String combination;


	/** 公式 含义*/
	@Column(name = "COMBINATION_MEAN", length = 1000)
	private String combinationMean;
	/**维护人 */
	@Column(name = "UPDATE_USER", length = 50)
	private String updateUser;
	
	/**维护人 机构*/
	@Column(name = "UPDATE_ORG", length = 50)
	private String updateOrg;
	
	/** 维护时间 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}

	public String getCombinationMean() {
		return combinationMean;
	}

	public void setCombinationMean(String combinationMean) {
		this.combinationMean = combinationMean;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateOrg() {
		return updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
