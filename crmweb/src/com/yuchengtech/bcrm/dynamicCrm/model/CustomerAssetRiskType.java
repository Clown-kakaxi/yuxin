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
 * 资产配置区间表
 * @author liliang5
 *
 */
@Entity
@Table(name="OCRM_F_CI_CUST_ASSET_RISK_TYPE")
public class CustomerAssetRiskType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_ASSET_RISK_TYPE_RISK_TYPE_ID_GENERATOR", sequenceName="SEQUENCE_ATTRI_CONF" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_ASSET_RISK_TYPE_RISK_TYPE_ID_GENERATOR")
	@Column(name="RISK_TYPE_ID")
	private String riskTypeId;
	
	@Column(name="RISK_TYPE_NAME")
	private String riskTypeName;

	public String getRiskTypeId() {
		return riskTypeId;
	}

	public void setRiskTypeId(String riskTypeId) {
		this.riskTypeId = riskTypeId;
	}

	public String getRiskTypeName() {
		return riskTypeName;
	}

	public void setRiskTypeName(String riskTypeName) {
		this.riskTypeName = riskTypeName;
	}
	
	

}
