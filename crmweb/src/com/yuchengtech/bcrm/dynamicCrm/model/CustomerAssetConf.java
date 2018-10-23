package com.yuchengtech.bcrm.dynamicCrm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 资产配置表
 * @author liliang5
 *
 */
@Entity
@Table(name="OCRM_F_CI_CUST_ASSET_CONF")
public class CustomerAssetConf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_ASSET_CONF_ID_GENERATOR", sequenceName="SEQUENCE_ATTRI_CONF" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_ASSET_CONF_ID_GENERATOR")
	@Column(name="ID")
	private String id;
	
	@Column(name="RISK_TYPE_ID")
	private String riskTypeId;
	
	@Column(name="RECOMMEND_PRO")
	private String recommendPro;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getRecommendPro() {
		return recommendPro;
	}

	public void setRecommendPro(String recommendPro) {
		this.recommendPro = recommendPro;
	}

	public String getRiskTypeId() {
		return riskTypeId;
	}

	public void setRiskTypeId(String riskTypeId) {
		this.riskTypeId = riskTypeId;
	}


}
