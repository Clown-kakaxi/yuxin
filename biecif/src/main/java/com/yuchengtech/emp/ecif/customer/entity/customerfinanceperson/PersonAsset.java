package com.yuchengtech.emp.ecif.customer.entity.customerfinanceperson;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.math.BigDecimal;


/**
 * The persistent class for the PERSON_ASSETS database table.
 * 
 */
@Entity
@Table(name="M_CI_PER_ASSETS")
public class PersonAsset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PERSON_ASSETS_ID", unique=true, nullable=false)
	private Long personAssetsId;

//	@Column(name="ASSETS_BELONG", length=60)
//	private String assetsBelong;

	@Column(name="ASSETS_NAME", length=80)
	private String assetsName;

	@Column(name="ASSETS_VALUE", precision=17, scale=2)
	private BigDecimal assetsOriginalValue;

	@Column(name="ASSETS_TYPE", length=20)
	private String assetsType;

	public String getAssetsDesc() {
		return assetsDesc;
	}

	public void setAssetsDesc(String assetsDesc) {
		this.assetsDesc = assetsDesc;
	}

	public String getSrcSerialNo() {
		return srcSerialNo;
	}

	public void setSrcSerialNo(String srcSerialNo) {
		this.srcSerialNo = srcSerialNo;
	}

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="ASSETS_DESC", length=80)
	private String assetsDesc;

	@Column(name="SRC_SERIAL_NO", length=80)
	private String srcSerialNo;
	
    public PersonAsset() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPersonAssetsId() {
		return this.personAssetsId;
	}

	public void setPersonAssetsId(Long personAssetsId) {
		this.personAssetsId = personAssetsId;
	}

//	public String getAssetsBelong() {
//		return this.assetsBelong;
//	}
//
//	public void setAssetsBelong(String assetsBelong) {
//		this.assetsBelong = assetsBelong;
//	}

	public String getAssetsName() {
		return this.assetsName;
	}

	public void setAssetsName(String assetsName) {
		this.assetsName = assetsName;
	}

	public BigDecimal getAssetsOriginalValue() {
		return this.assetsOriginalValue;
	}

	public void setAssetsOriginalValue(BigDecimal assetsOriginalValue) {
		this.assetsOriginalValue = assetsOriginalValue;
	}

	public String getAssetsType() {
		return this.assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

}