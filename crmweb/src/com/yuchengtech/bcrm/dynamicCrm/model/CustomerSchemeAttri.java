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
 * 方案属性表
 * @author liliang5
 *
 */
@Entity
@Table(name="OCRM_F_CI_CUST_SCHEME_ATTRI")
public class CustomerSchemeAttri implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_SCHEME_ATTRI_SCHEME_ATTRI_ID_GENERATOR", sequenceName="SEQUENCE_ATTRI_CONF" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_SCHEME_ATTRI_SCHEME_ATTRI_ID_GENERATOR")	
	@Column(name="SA_ID")
	private String saId;
	
	@Column(name="SCHEME_ID")
	private String schemeId;
	
	@Column(name="ATTRI_ID")
	private String attriId;
	
	@Column(name="SCHEME_ATTRI_WEIGHT")
	private String schemeAttriWeight;
	
	@Column(name="INDEX_ID")
	private String indexId;

	public String getSaId() {
		return saId;
	}

	public void setSaId(String saId) {
		this.saId = saId;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getAttriId() {
		return attriId;
	}

	public void setAttriId(String attriId) {
		this.attriId = attriId;
	}

	public String getSchemeAttriWeight() {
		return schemeAttriWeight;
	}

	public void setSchemeAttriWeight(String schemeAttriWeight) {
		this.schemeAttriWeight = schemeAttriWeight;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

}
