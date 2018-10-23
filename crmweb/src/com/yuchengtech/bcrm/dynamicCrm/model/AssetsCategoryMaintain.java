package com.yuchengtech.bcrm.dynamicCrm.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  Created by Tracy on 16/1/18.
 */
@Entity
@Table(name="OCRM_F_CI_CUST_ASSET_CATL")
public class AssetsCategoryMaintain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_ASSET_CATL_ASSET_ID_GENERATOR", sequenceName="SEQUENCE_ATTRI_CONF" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_ASSET_CATL_ASSET_ID_GENERATOR")


	@Column(name="ASSET_ID")
	private String assetId;

	@Column(name="ASSET_NAME")
	private String assetName;

	@Column(name="ASSET_PARENT")
	private String assetParent;

	@Column(name="ASSET_LEVEL")
	private String assetLevel;

	@Column(name="ASSET_ORDER")
	private String assetOrder;




	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetParent() {
		return assetParent;
	}

	public void setAssetParent(String assetParent) {
		this.assetParent = assetParent;
	}


	public String getAssetLevel() {
		return assetLevel;
	}

	public void setAssetLevel(String assetLevel) {
		this.assetLevel = assetLevel;
	}

	public String getAssetOrder() {
		return assetOrder;
	}

	public void setAssetOrder(String assetOrder) {
		this.assetOrder = assetOrder;
	}


}
