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
 * 方案信息表
 * @author liliang5
 *
 */
@Entity
@Table(name="OCRM_F_CI_CUST_SCHEME")
public class CustomerScheme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_SCHEME_SCHEME_ID_GENERATOR", sequenceName="SEQUENCE_ATTRI_CONF" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_SCHEME_SCHEME_ID_GENERATOR")
	@Column(name="SCHEME_ID")
	private String schemeId;
	
	@Column(name="SCHEME_NAME")
	private String schemeName;
	
	@Column(name="SCHEME_STATE")
	private String schemeState;

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSchemeState() {
		return schemeState;
	}

	public void setSchemeState(String schemeState) {
		this.schemeState = schemeState;
	}

}
