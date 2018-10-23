package com.yuchengtech.bcrm.dynamicCrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="OCRM_F_CI_CUST_ATTRI_ITEM")
public class CustomerAttriItem {

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_ATTRI_SCORE_ID_GENERATOR", sequenceName="ID_LOOKUP" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_ATTRI_SCORE_ID_GENERATOR")
	@Column(name="ID")
	private String id;

	@Column(name="ATTRI_ID")
	private String attriId;

	@Column(name="INDEX_VALUE")
	private String indexValue;

	@Column(name="INDEX_VALUE_NAME")
	private String indexValueName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttriId() {
		return attriId;
	}

	public void setAttriId(String attriId) {
		this.attriId = attriId;
	}

	public String getIndexValue() {
		return indexValue;
	}

	public void setIndexValue(String indexValue) {
		this.indexValue = indexValue;
	}

	public String getIndexValueName() {
		return indexValueName;
	}

	public void setIndexValueName(String indexValueName) {
		this.indexValueName = indexValueName;
	}
	

}
