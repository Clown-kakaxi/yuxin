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
 * 客户属性表
 * @author liliang5
 *
 */
@Entity
@Table(name="OCRM_F_CI_CUST_ATTRI_CONF")
public class CustomerAttriConf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_ATTRI_CONF_ATTRI_ID_GENERATOR", sequenceName="SEQUENCE_ATTRI_CONF" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_ATTRI_CONF_ATTRI_ID_GENERATOR")	
	@Column(name="ATTRI_ID")
	private String attriId;
	
	@Column(name="ATTRI_NAME")
	private String attriName;
	
	@Column(name="UP_ATTRI_ID")
	private String upAttriId;
	
	@Column(name="ATTRI_LEVEL")
	private String attriLevel;
	
	@Column(name="ATTRI_STATE")
	private String attriState;
	
	

	public String getAttriId() {
		return attriId;
	}

	public void setAttriId(String attriId) {
		this.attriId = attriId;
	}

	public String getAttriName() {
		return attriName;
	}

	public void setAttriName(String attriName) {
		this.attriName = attriName;
	}

	public String getUpAttriId() {
		return upAttriId;
	}

	public void setUpAttriId(String upAttriId) {
		this.upAttriId = upAttriId;
	}

	public String getAttriLevel() {
		return attriLevel;
	}

	public void setAttriLevel(String attriLevel) {
		this.attriLevel = attriLevel;
	}

	public String getAttriState() {
		return attriState;
	}

	public void setAttriState(String attriState) {
		this.attriState = attriState;
	}


}
