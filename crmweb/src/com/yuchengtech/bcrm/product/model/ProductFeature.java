package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the OCRM_F_PD_PROD_FEATURE database table.
 * 
 */
@Entity
@Table(name = "OCRM_F_PD_PROD_FEATURE")
public class ProductFeature implements Serializable {

	/**
	 * FDM(基础数据层)，归属于产品管理的第二主题，数据为产品特征信息
	 */
	private static final long serialVersionUID = -2750979657439666677L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	/**特征id*/
	@Column(name = "FEATURE_ID", nullable = false)
	private Long featureId;

	/** 特征名称 */
	@Column(name = "FEATURE_NAME", nullable = false)
	private String featureName;

	@Column(name = "FEATURE_STATUS",length=30)
	private String featureStatus;
	
	/** 特征分类标识 ID*/
	@Column(name = "FEATURE_CATL_ID", nullable = false)
	private Long featureCatlId;
	
	@Column(name = "FEATURE_CATE_NAME",length =100)
	private String featureCateName;
	

	/** 特征描述 */
	@Column(name = "FEATURE_DESC", length = 500)
	private String featureDescription;

	// bi-directional many-to-one association to ProductFeatureValue
//	@OneToMany(mappedBy = "ocrmFPdProdFeature")
//	private Set<ProductFeatureValue> ocrmFPdFeatVals;



//	public Set<ProductFeatureValue> getOcrmFPdFeatVals() {
//		return this.ocrmFPdFeatVals;
//	}
//
//	public void setOcrmFPdFeatVals(Set<ProductFeatureValue> ocrmFPdFeatVals) {
//		this.ocrmFPdFeatVals = ocrmFPdFeatVals;
//	}
	/**
	 * @return the featureCatlId
	 */
	public Long getFeatureCatlId() {
		return featureCatlId;
	}

	/**
	 * @return the featureId
	 */
	public Long getFeatureId() {
		return featureId;
	}

	/**
	 * @param featureId the featureId to set
	 */
	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	/**
	 * @return the featureName
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * @param featureName the featureName to set
	 */
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	/**
	 * @return the featureStatus
	 */
	public String getFeatureStatus() {
		return featureStatus;
	}

	/**
	 * @param featureStatus the featureStatus to set
	 */
	public void setFeatureStatus(String featureStatus) {
		this.featureStatus = featureStatus;
	}

	/**
	 * @param featureCatlId the featureCatlId to set
	 */
	public void setFeatureCatlId(Long featureCatlId) {
		this.featureCatlId = featureCatlId;
	}
	
	/**
	 * @return the featureCateName
	 */
	public String getFeatureCateName() {
		return featureCateName;
	}

	/**
	 * @param featureCateName the featureCateName to set
	 */
	public void setFeatureCateName(String featureCateName) {
		this.featureCateName = featureCateName;
	}
	public void setFeatureDescription(String featureDescription) {
		this.featureDescription = featureDescription;
	}

	public String getFeatureDescription() {
		return featureDescription;
	}

}